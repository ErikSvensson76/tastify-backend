package com.example.tastifybackend.domain.recipe.repository;

import com.example.tastifybackend.domain.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, String> {
}
