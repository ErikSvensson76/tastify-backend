package com.example.tastifybackend.domain.recipe_ingredient;

import com.example.tastifybackend.domain.recipe_ingredient.dto.RecipeIngredientDto;
import com.example.tastifybackend.domain.recipe_ingredient.dto.RecipeIngredientInput;
import com.example.tastifybackend.domain.templates.GenericCRUD;

import java.util.List;

public interface RecipeIngredientService extends GenericCRUD<RecipeIngredientInput, RecipeIngredientDto> {
    List<String> getAllDistinctIngredients();
    List<RecipeIngredientDto> getByRecipeId(String id);
    List<RecipeIngredientDto> getByIngredientName(String ingredientName);
}
