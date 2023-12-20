package com.example.tastifybackend.domain.recipe;

import com.example.tastifybackend.domain.recipe.dto.RecipeDto;
import com.example.tastifybackend.domain.recipe.dto.RecipeInput;
import com.example.tastifybackend.domain.templates.GenericCRUD;

public interface RecipeService extends GenericCRUD<RecipeInput, RecipeDto> {
}
