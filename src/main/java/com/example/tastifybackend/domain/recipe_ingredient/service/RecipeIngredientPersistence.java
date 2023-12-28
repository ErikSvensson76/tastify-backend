package com.example.tastifybackend.domain.recipe_ingredient.service;

import com.example.tastifybackend.domain.recipe_ingredient.RecipeIngredient;
import com.example.tastifybackend.domain.recipe_ingredient.dto.RecipeIngredientInput;
import com.example.tastifybackend.domain.recipe_ingredient.repository.RecipeIngredientRepository;
import com.example.tastifybackend.exception.EntityNotFoundException;
import com.example.tastifybackend.misc.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = RuntimeException.class)
public class RecipeIngredientPersistence implements RecipeIngredientPersistenceService {

    private final RecipeIngredientRepository recipeIngredientRepository;

    @Override
    public RecipeIngredient save(RecipeIngredientInput input) {
        if(input == null) throw new IllegalArgumentException("RecipeIngredientInput input was null");
        return input.getId() == null ? create(input) : update(input);
    }

    public RecipeIngredient create(RecipeIngredientInput input){
        return recipeIngredientRepository.save(RecipeIngredient.builder()
            .ingredient(Util.makeFirstLetterUppercase(input.getIngredient()))
            .measurement(input.getMeasurement())
            .amount(input.getAmount())
            .build());
    }

    public RecipeIngredient update(RecipeIngredientInput input){
        RecipeIngredient toUpdate = recipeIngredientRepository.findById(input.getId())
            .orElseThrow(() -> new EntityNotFoundException("Error getting RecipeIngredient id: " + input.getId()));
        toUpdate.setIngredient(Util.makeFirstLetterUppercase(input.getIngredient()));
        toUpdate.setAmount(input.getAmount());
        toUpdate.setMeasurement(input.getMeasurement());
        return recipeIngredientRepository.save(toUpdate);
    }

    @Override
    public List<RecipeIngredient> saveAll(List<RecipeIngredientInput> inputs) {
        return inputs.stream()
            .map(this::save)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        recipeIngredientRepository.findById(id)
          .map(toDelete -> {
              Optional.ofNullable(toDelete.getRecipe())
                  .ifPresent(recipe -> recipe.removeIngredients(toDelete));
              return toDelete;
          }).ifPresent(recipeIngredientRepository::delete);
    }

    @Override
    public void deleteAll(List<String> ids) {
        ids.forEach(recipeIngredientRepository::deleteById);
    }
}
