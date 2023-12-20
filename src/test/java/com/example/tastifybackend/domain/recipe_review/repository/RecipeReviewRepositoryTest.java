package com.example.tastifybackend.domain.recipe_review.repository;

import com.example.tastifybackend.domain.recipe.Recipe;
import com.example.tastifybackend.domain.recipe.dto.RecipeAndScores;
import com.example.tastifybackend.domain.recipe_review.RecipeReview;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RecipeReviewRepositoryTest {

    @Autowired
    TestEntityManager em;
    @Autowired
    RecipeReviewRepository underTest;
    private Recipe recipe;


    @BeforeEach
    void setUp() {
        this.recipe  = em.persist(Recipe.builder()
                .recipeName("test")
                .build());
    }

    @Test
    void getAllScoresByRecipeId() {
        var reviews = Stream.of(
            RecipeReview.builder().score(3).recipe(recipe).build(),
            RecipeReview.builder().score(1).recipe(recipe).build(),
            RecipeReview.builder().score(5).recipe(recipe).build(),
            RecipeReview.builder().score(2).recipe(recipe).build()
        ).map(em::persist).toList();

        int expectedSum = 11;

        List<Integer> scores = underTest.getAllScoresByRecipeId(recipe.getId());
        assertFalse(scores.isEmpty());
        assertEquals(reviews.size(), scores.size());
        assertEquals(expectedSum, scores.stream().mapToInt(Integer::intValue).sum());
    }

    @Test
    void getScoresGroupedByRecipeId() {
        Recipe recipe2 = em.persist(Recipe.builder().recipeName("test2").build());

        var reviews = Stream.of(
                RecipeReview.builder().score(3).recipe(recipe).build(),
                RecipeReview.builder().score(1).recipe(recipe).build(),
                RecipeReview.builder().score(5).recipe(recipe2).build(),
                RecipeReview.builder().score(2).recipe(recipe2).build()
        ).map(em::persist).toList();

        List<RecipeAndScores> recipeAndScores = underTest.getScoresGroupedByRecipeId();
        assertFalse(recipeAndScores.isEmpty());
        assertEquals(4, recipeAndScores.size());
    }
}