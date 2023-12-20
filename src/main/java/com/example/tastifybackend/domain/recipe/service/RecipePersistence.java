package com.example.tastifybackend.domain.recipe.service;

import com.example.tastifybackend.domain.category.Category;
import com.example.tastifybackend.domain.category.service.CategoryPersistenceService;
import com.example.tastifybackend.domain.recipe.Recipe;
import com.example.tastifybackend.domain.recipe.dto.RecipeInput;
import com.example.tastifybackend.domain.recipe.repository.RecipeRepository;
import com.example.tastifybackend.domain.recipe_ingredient.RecipeIngredient;
import com.example.tastifybackend.domain.recipe_ingredient.service.RecipeIngredientPersistenceService;
import com.example.tastifybackend.domain.recipe_instruction.RecipeInstruction;
import com.example.tastifybackend.domain.recipe_instruction.service.RecipeInstructionPersistenceService;
import com.example.tastifybackend.domain.recipe_review.service.AverageReviewScoreStorage;
import com.example.tastifybackend.exception.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipePersistence implements RecipePersistenceService {

    private final RecipeRepository recipeRepository;
    private final RecipeIngredientPersistenceService recipeIngredientService;
    private final CategoryPersistenceService categoryService;
    private final RecipeInstructionPersistenceService recipeInstructionService;
    private final AverageReviewScoreStorage reviewScoreStorage;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Recipe save(@NotNull RecipeInput recipeInput) {
        if(recipeInput.getId() == null){
            return this.persist(recipeInput);
        }
        return this.update(recipeInput);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Recipe persist(RecipeInput input){
        List<Category> categories = Optional.ofNullable(input.getCategories())
                .map(categoryService::saveAll)
                .orElseGet(ArrayList::new);

        List<RecipeIngredient> recipeIngredients = Optional.ofNullable(input.getIngredients())
                .map(recipeIngredientService::saveAll)
                .orElseGet(ArrayList::new);

        List<RecipeInstruction> recipeInstructions = Optional.ofNullable(input.getInstructions())
                .map(recipeInstructionService::saveAll)
                .orElseGet(ArrayList::new);

        Recipe recipe = Recipe.builder()
                .recipeName(input.getRecipeName())
                .description(input.getDescription())
                .status(input.getStatus())
                .build();

        recipe.setCategories(new HashSet<>(categories));
        recipe.setIngredients(recipeIngredients);
        recipe.setInstructions(recipeInstructions);

        return recipeRepository.save(recipe);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Recipe update(RecipeInput input){
        Recipe toUpdate = recipeRepository.findById(input.getId())
                .orElseThrow(() -> new EntityNotFoundException("Error getting Recipe id: " + input.getId()));

        toUpdate.setRecipeName(input.getRecipeName());
        toUpdate.setDescription(input.getDescription());
        toUpdate.setStatus(input.getStatus());
        List<Category> categories = categoryService.saveAll(input.getCategories());
        toUpdate.setCategories(new HashSet<>(categories));
        List<RecipeIngredient> recipeIngredients = recipeIngredientService.saveAll(input.getIngredients());
        toUpdate.setIngredients(recipeIngredients);
        List<RecipeInstruction> instructions = recipeInstructionService.saveAll(input.getInstructions());
        toUpdate.setInstructions(instructions);
        return recipeRepository.save(toUpdate);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<Recipe> saveAll(List<RecipeInput> recipeInputs) {
        return recipeInputs.stream()
                .map(this::save)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(String id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error getting Recipe id: " + id));
        recipeRepository.delete(recipe);
        reviewScoreStorage.removeEntry(id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteAll(List<String> ids) {
       recipeRepository.findAllById(ids).forEach(recipe -> {
               recipeRepository.delete(recipe);
               reviewScoreStorage.removeEntry(recipe.getId());
       });
    }
}
