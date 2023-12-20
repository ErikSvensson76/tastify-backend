package com.example.tastifybackend.domain.recipe_ingredient.service;

import com.example.tastifybackend.domain.recipe_ingredient.RecipeIngredient;
import com.example.tastifybackend.domain.recipe_ingredient.dto.RecipeIngredientInput;

import java.util.List;

public interface RecipeIngredientPersistenceService {
    RecipeIngredient save(RecipeIngredientInput input);
    List<RecipeIngredient> saveAll(List<RecipeIngredientInput> inputs);
    void delete(String id);
    void deleteAll(List<String> ids);
}
