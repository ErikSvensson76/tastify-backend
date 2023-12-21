package com.example.tastifybackend.domain.recipe_ingredient.service;

import com.example.tastifybackend.FakerGenerator;
import com.example.tastifybackend.domain.recipe.Recipe;
import com.example.tastifybackend.domain.recipe_ingredient.RecipeIngredient;
import com.example.tastifybackend.domain.recipe_ingredient.dto.RecipeIngredientInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase
@Transactional
@DirtiesContext
class RecipeIngredientPersistenceTest {

    @Autowired
    TestEntityManager em;

    @Autowired
    RecipeIngredientPersistence underTest;

    final FakerGenerator faker = FakerGenerator.getInstance();

    @Test
    void save_persist() {
        RecipeIngredientInput input = faker.randomRecipeIngredientInput();
        RecipeIngredient actual = underTest.save(input);

        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertNotNull(actual.getIngredient());
        assertEquals(input.getIngredient(), actual.getIngredient());
        assertEquals(input.getMeasurement(), actual.getMeasurement());
        assertEquals(input.getAmount(), actual.getAmount());
        // Should be null because it's not set yet
        assertNull(actual.getRecipe());
    }

    @Test
    void save_update() {

        RecipeIngredient recipeIngredient = em.persist(RecipeIngredient.builder()
                .amount(BigDecimal.valueOf(2))
                .measurement("dl")
                .ingredient("Mjöl")
                .build()
        );
        assertNotNull(recipeIngredient);


        String id = recipeIngredient.getId();
        BigDecimal amount = BigDecimal.valueOf(3);
        String measurement = "l";
        String ingredient = "Rågmjöl";

        RecipeIngredientInput updateInput = RecipeIngredientInput.builder()
                .id(id)
                .amount(amount)
                .measurement(measurement)
                .ingredient(ingredient)
                .build();

        RecipeIngredient actual = underTest.save(updateInput);
        assertNotNull(actual);
        assertEquals(id, actual.getId());
        assertEquals(amount, actual.getAmount());
        assertEquals(measurement, actual.getMeasurement());
        assertNotNull(actual.getIngredient());
        assertEquals(ingredient, actual.getIngredient());
    }

    @Test
    void saveAll() {
        var toPersist = Stream.generate(faker::randomRecipeIngredientInput).limit(5).toList();
        var toUpdate = Stream.generate(faker::randomRecipeIngredient).limit(5)
                .map(em::persist)
                .map(recipeIngredient -> RecipeIngredientInput.builder()
                        .id(recipeIngredient.getId())
                        .amount(recipeIngredient.getAmount())
                        .ingredient(recipeIngredient.getIngredient())
                        .build()
                ).toList();
        assertNotNull(toPersist);
        assertNotNull(toUpdate);
        List<RecipeIngredientInput> inData = Stream.concat(toPersist.stream(), toUpdate.stream())
                .toList();

        List<RecipeIngredient> actual = underTest.saveAll(inData);
        assertNotNull(actual);
        assertEquals(10, actual.size());
    }

    @Test
    void delete_with_recipe_relation() {
        RecipeIngredient recipeIngredient = faker.randomRecipeIngredient();

        recipeIngredient = em.persist(recipeIngredient);
        Recipe recipe = em.persist(faker.randomRecipe());
        recipe.addIngredients(recipeIngredient);
        recipe = em.persist(recipe);

        underTest.delete(recipeIngredient.getId());
        assertNull(em.find(RecipeIngredient.class, recipeIngredient.getId()));
        assertNotNull(em.find(Recipe.class, recipe.getId()));
        assertEquals(0, recipe.getIngredients().size());
    }

    @Test
    void delete_without_relation() {
        RecipeIngredient recipeIngredient = faker.randomRecipeIngredient();
        recipeIngredient = em.persist(recipeIngredient);

        underTest.delete(recipeIngredient.getId());
        assertNull(em.find(RecipeIngredient.class, recipeIngredient.getId()));
    }
}