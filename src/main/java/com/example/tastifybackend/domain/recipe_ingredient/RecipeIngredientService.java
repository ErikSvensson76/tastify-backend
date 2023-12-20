package com.example.tastifybackend.domain.recipe_ingredient;

import com.example.tastifybackend.domain.recipe_ingredient.dto.RecipeIngredientDto;
import com.example.tastifybackend.domain.recipe_ingredient.dto.RecipeIngredientInput;
import com.example.tastifybackend.domain.templates.GenericCRUD;

public interface RecipeIngredientService extends GenericCRUD<RecipeIngredientInput, RecipeIngredientDto> {
}
