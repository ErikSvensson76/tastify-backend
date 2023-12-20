package com.example.tastifybackend.domain.recipe.service;

import com.example.tastifybackend.FakerGenerator;
import com.example.tastifybackend.domain.category.Category;
import com.example.tastifybackend.domain.category.dto.CategoryInput;
import com.example.tastifybackend.domain.recipe.Recipe;
import com.example.tastifybackend.domain.recipe.RecipeStatus;
import com.example.tastifybackend.domain.recipe.dto.RecipeInput;
import com.example.tastifybackend.domain.recipe_ingredient.dto.RecipeIngredientInput;
import com.example.tastifybackend.domain.recipe_instruction.dto.RecipeInstructionInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
class RecipePersistenceTest {

    @Autowired
    private TestEntityManager em;
    @Autowired
    RecipePersistence underTest;

    final FakerGenerator fakerGenerator = FakerGenerator.getInstance();


    public RecipeInput getRecipeInput(){
        RecipeInput input = fakerGenerator.randomRecipeInput();
        input.setCategories(Stream.generate(fakerGenerator::randomCategoryInput).limit(1).collect(Collectors.toList()));
        input.setIngredients(Stream.generate(fakerGenerator::randomRecipeIngredientInput).limit(1).collect(Collectors.toList()));
        input.setInstructions(Stream.generate(fakerGenerator::randomRecipeInstructionInput).limit(1).collect(Collectors.toList()));
        return input;
    }

    @Test
    void save_persist() {
        RecipeInput input = getRecipeInput();

        Recipe actual = underTest.save(input);
        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertEquals(input.getRecipeName(), actual.getRecipeName());
        assertEquals(input.getStatus(), actual.getStatus());
        assertEquals(input.getDescription(), actual.getDescription());
        assertEquals(1, actual.getInstructions().size());
        assertEquals(0, actual.getReviews().size());
        assertEquals(1, actual.getIngredients().size());
        assertEquals(1, actual.getCategories().size());
    }

    @Test
    void save_update_base_attributes(){
        Recipe persisted = em.persist(fakerGenerator.randomRecipe());

        RecipeInput input = RecipeInput.builder()
                .id(persisted.getId())
                .description("Test description")
                .recipeName("Test name")
                .status(persisted.getStatus() == RecipeStatus.PRIVATE ? RecipeStatus.PUBLIC : RecipeStatus.PRIVATE)
                .ingredients(new ArrayList<>())
                .categories(new ArrayList<>())
                .instructions(new ArrayList<>())
                .build();

        Recipe actual = underTest.save(input);
        assertNotNull(actual);
        assertEquals(persisted.getId(), actual.getId());
        assertEquals("Test description", actual.getDescription());
        assertEquals("Test name", actual.getRecipeName());
    }

    @Test
    void save_update_recipe_instructions(){
        Recipe recipe = em.persist(fakerGenerator.randomRecipe());

        List<RecipeInstructionInput> singleInstruction = Collections.singletonList(fakerGenerator.randomRecipeInstructionInput());
        List<RecipeIngredientInput> singleRecipeIngredient = Collections.singletonList(fakerGenerator.randomRecipeIngredientInput());
        List<CategoryInput> singleCategoryInput = Collections.singletonList(fakerGenerator.randomCategoryInput());

        RecipeInput recipeInput = RecipeInput.builder()
                .id(recipe.getId())
                .status(recipe.getStatus())
                .recipeName(recipe.getRecipeName())
                .description(recipe.getDescription())
                .instructions(singleInstruction)
                .categories(singleCategoryInput)
                .ingredients(singleRecipeIngredient)
                .build();

        Recipe actual = underTest.save(recipeInput);
        assertNotNull(actual);
        assertEquals(1, actual.getInstructions().size());
        assertEquals(1, actual.getCategories().size());
        assertEquals(1, actual.getIngredients().size());

        Category category = actual.getCategories().stream().findAny().orElse(null);
        assertNotNull(category);

        List<CategoryInput> categoryInputs = new ArrayList<>(Arrays.asList(
                CategoryInput.builder().id(category.getId()).value(category.getValue()).build(),
                fakerGenerator.randomCategoryInput()
        ));

        List<RecipeIngredientInput> recipeIngredientInputs = new ArrayList<>(Arrays.asList(
                fakerGenerator.randomRecipeIngredientInput()
        ));


        recipeInput.setInstructions(new ArrayList<>());
        recipeInput.setCategories(categoryInputs);
        recipeInput.setIngredients(recipeIngredientInputs);

        actual = underTest.save(recipeInput);
        em.flush();
        assertNotNull(actual);
    }

    @Test
    void saveAll() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteAll() {
    }
}