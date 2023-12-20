package com.example.tastifybackend.domain.recipe.service;

import com.example.tastifybackend.domain.recipe.Recipe;
import com.example.tastifybackend.domain.recipe.dto.RecipeInput;

import java.util.List;

public interface RecipePersistenceService {
    Recipe save(RecipeInput recipeInput);
    List<Recipe> saveAll(List<RecipeInput> recipeInputs);
    void delete(String id);
    void deleteAll(List<String> ids);
}
