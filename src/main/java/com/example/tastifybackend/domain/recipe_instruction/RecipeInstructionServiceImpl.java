package com.example.tastifybackend.domain.recipe_instruction;

import com.example.tastifybackend.domain.EntityToDtoConverter;
import com.example.tastifybackend.domain.recipe_instruction.dto.RecipeInstructionDto;
import com.example.tastifybackend.domain.recipe_instruction.dto.RecipeInstructionInput;
import com.example.tastifybackend.domain.recipe_instruction.repository.RecipeInstructionRepository;
import com.example.tastifybackend.domain.recipe_instruction.service.RecipeInstructionPersistenceService;
import com.example.tastifybackend.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeInstructionServiceImpl implements RecipeInstructionService{

    private final RecipeInstructionPersistenceService persistenceService;
    private final RecipeInstructionRepository repository;
    private final EntityToDtoConverter converter;

    @Override
    public RecipeInstructionDto save(RecipeInstructionInput input) {
        return converter.recipeInstructionToDto(
                persistenceService.save(input)
        );
    }

    @Override
    public List<RecipeInstructionDto> saveAll(List<RecipeInstructionInput> inputs) {
        return persistenceService.saveAll(inputs).stream()
                .map(converter::recipeInstructionToDto)
                .toList();
    }

    @Override
    public RecipeInstructionDto findById(String id) {
        return repository.findById(id)
                .map(converter::recipeInstructionToDto)
                .orElseThrow(() -> new EntityNotFoundException("RecipeInstruction not found, id: " + id));
    }

    @Override
    public List<RecipeInstructionDto> findAll() {
        return repository.findAll().stream()
                .map(converter::recipeInstructionToDto)
                .toList();
    }

    @Override
    public void delete(String id) {
        persistenceService.delete(id);
    }

    @Override
    public void deleteAll(List<String> ids) {
        persistenceService.deleteAll(ids);
    }
}
