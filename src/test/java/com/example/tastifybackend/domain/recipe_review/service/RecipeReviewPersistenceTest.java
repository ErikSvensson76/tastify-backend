package com.example.tastifybackend.domain.recipe_review.service;

import com.example.tastifybackend.domain.recipe.Recipe;
import com.example.tastifybackend.domain.recipe_review.RecipeReview;
import com.example.tastifybackend.domain.recipe_review.dto.RecipeReviewInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
class RecipeReviewPersistenceTest {

    @Autowired
    private TestEntityManager em;
    @Autowired
    private RecipeReviewPersistence underTest;



    @Test
    void save_new_recipe_review(){
        Recipe recipe = em.persist(Recipe.builder().recipeName("Test").build());
        RecipeReviewInput input = RecipeReviewInput.builder()
                .recipeId(recipe.getId())
                .score(2)
                .comment("Inte gott")
                .build();

        RecipeReview actual = underTest.save(input);
        assertNotNull(actual);
        assertEquals(recipe.getId(), actual.getRecipe().getId());
        assertNotNull(actual.getId());
        assertEquals(2, actual.getScore());
        assertEquals("Inte gott", actual.getComment());
    }

    @Test
    void save_update_recipe_review(){
        Recipe recipe = em.persist(Recipe.builder().recipeName("Test").build());
        RecipeReview review = em.persist(RecipeReview.builder().score(5).comment("Mycket gott").build());
        recipe.addReviews(review);
        em.merge(recipe);

        String expectedNewComment = "Mycket gott till soppa";
        RecipeReviewInput input = RecipeReviewInput.builder()
                .id(review.getId())
                .recipeId(recipe.getId())
                .comment(expectedNewComment)
                .score(review.getScore())
                .build();

        RecipeReview actual = underTest.save(input);

        assertNotNull(actual);
        assertEquals(review.getId(), actual.getId());
        assertEquals(review.getRecipe().getId(), actual.getRecipe().getId());
        assertEquals(expectedNewComment, actual.getComment());
        assertEquals(review.getScore(), actual.getScore());
    }

}