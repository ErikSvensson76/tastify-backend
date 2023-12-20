package com.example.tastifybackend.domain.recipe_instruction.service;

import com.example.tastifybackend.domain.recipe_instruction.RecipeInstruction;
import com.example.tastifybackend.domain.recipe_instruction.dto.RecipeInstructionInput;
import com.example.tastifybackend.domain.recipe_instruction.repository.RecipeInstructionRepository;
import com.example.tastifybackend.exception.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = RuntimeException.class)
public class RecipeInstructionPersistence implements RecipeInstructionPersistenceService {

    private final RecipeInstructionRepository repository;

    @Override
    public RecipeInstruction save(@NotNull RecipeInstructionInput recipeInstructionInput) {
        if(recipeInstructionInput.getId() == null){
            return repository.save(
                    RecipeInstruction.builder()
                            .instruction(recipeInstructionInput.getInstruction())
                            .build()
            );
        }
        RecipeInstruction entity = repository.findById(recipeInstructionInput.getId())
                .orElseThrow(() -> new EntityNotFoundException("Error getting RecipeInstruction id: " + recipeInstructionInput.getId()));

        entity.setInstruction(recipeInstructionInput.getInstruction());
        return repository.save(entity);
    }

    @Override
    public List<RecipeInstruction> saveAll(List<RecipeInstructionInput> recipeInstructionInputs) {
        List<RecipeInstruction> instructions = new ArrayList<>();
        recipeInstructionInputs.forEach(recipeInstructionInput -> instructions.add(save(recipeInstructionInput)));
        return instructions;
    }

    @Override
    public void delete(String id) {
        repository.findById(id)
                        .map(toDelete ->{
                            Optional.ofNullable(toDelete.getRecipe())
                                    .ifPresent(recipe -> recipe.removeInstructions(toDelete));
                            return toDelete;
                        }).ifPresent(repository::delete);
    }

    @Override
    public void deleteAll(List<String> ids) {
        repository.deleteAllById(ids);
    }
}
