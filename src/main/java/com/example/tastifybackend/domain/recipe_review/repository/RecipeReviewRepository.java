package com.example.tastifybackend.domain.recipe_review.repository;

import com.example.tastifybackend.domain.recipe.dto.RecipeAndScores;
import com.example.tastifybackend.domain.recipe_review.RecipeReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeReviewRepository extends JpaRepository<RecipeReview, String> {

    @Query("SELECT s.score AS score FROM RecipeReview s WHERE s.recipe.id = :recipeId")
    List<Integer> getAllScoresByRecipeId(@Param("recipeId") String recipeId);

    @Query("SELECT new com.example.tastifybackend.domain.recipe.dto.RecipeAndScores(recipe.id, review.score) " +
            "FROM Recipe recipe, RecipeReview review " +
            "WHERE recipe.id = review.recipe.id")
    List<RecipeAndScores> getScoresGroupedByRecipeId();

}
