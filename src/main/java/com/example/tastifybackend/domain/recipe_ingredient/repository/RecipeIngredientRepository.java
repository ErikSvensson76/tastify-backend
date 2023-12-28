package com.example.tastifybackend.domain.recipe_ingredient.repository;

import com.example.tastifybackend.domain.recipe_ingredient.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, String> {

    @Query("SELECT DISTINCT ri.ingredient FROM RecipeIngredient ri ORDER BY ri.ingredient")
    List<String> getAllIngredientNames();

    @Query("SELECT ri FROM RecipeIngredient ri WHERE lower(ri.ingredient) = :ingredient")
    List<RecipeIngredient> getByRecipeIngredientName(@Param("ingredient") String ingredient);

    @Query("SELECT ri FROM RecipeIngredient ri WHERE ri.recipe.id = :recipeId")
    List<RecipeIngredient> getByRecipeId(@Param("recipeId") String recipeId);

}
