package com.example.tastifybackend.domain.recipe_ingredient.repository;

import com.example.tastifybackend.domain.recipe_ingredient.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, String> {
}
