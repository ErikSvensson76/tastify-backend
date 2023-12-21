package com.example.tastifybackend.domain.recipe;

import com.example.tastifybackend.FakerGenerator;
import com.example.tastifybackend.domain.category.Category;
import com.example.tastifybackend.domain.recipe_ingredient.RecipeIngredient;
import com.example.tastifybackend.domain.recipe_instruction.RecipeInstruction;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    FakerGenerator fakerGenerator = FakerGenerator.getInstance();

    @Test
    void noNullCollections(){
        Recipe recipe = Recipe.builder().build();
        assertNotNull(recipe.getCategories());
        assertNotNull(recipe.getIngredients());
        assertNotNull(recipe.getInstructions());
    }

    @Test
    void addCategories(){
        Category category = fakerGenerator.randomCategory();
        Recipe recipe = fakerGenerator.randomRecipe();

        assertDoesNotThrow(
                () -> recipe.addCategories(category)
        );

        assertEquals(1, recipe.getCategories().size());
        assertEquals(1, category.getRecipes().size());
    }

    @Test
    void removeCategories(){
        Category category = fakerGenerator.randomCategory();
        Recipe recipe = fakerGenerator.randomRecipe();
        recipe.addCategories(category);

        assertDoesNotThrow(
                () -> recipe.removeCategories(category)
        );

        assertEquals(0, recipe.getCategories().size());
        assertEquals(0, category.getRecipes().size());
    }

    @Test
    void setCategories(){
        Set<Category> categories = Stream.generate(fakerGenerator::randomCategory)
                .limit(5).collect(Collectors.toSet());

        Recipe recipe = fakerGenerator.randomRecipe();
        recipe.setCategories(categories);

        assertEquals(5, recipe.getCategories().size());
        assertTrue(
                recipe.getCategories().stream().allMatch(category -> category.getRecipes().contains(recipe))
        );
    }

    @Test
    void addIngredients(){
        RecipeIngredient recipeIngredient = fakerGenerator.randomRecipeIngredient();

        Recipe recipe = fakerGenerator.randomRecipe();

        assertDoesNotThrow(
                () -> recipe.addIngredients(recipeIngredient)
        );
        assertEquals(1, recipe.getIngredients().size());
        assertNotNull(recipeIngredient.getRecipe());
    }

    @Test
    void removeIngredients(){
        RecipeIngredient recipeIngredient = fakerGenerator.randomRecipeIngredient();

        Recipe recipe = fakerGenerator.randomRecipe();
        recipe.addIngredients(recipeIngredient);

        assertDoesNotThrow(
                () -> recipe.removeIngredients(recipeIngredient)
        );
        assertEquals(0, recipe.getIngredients().size());
        assertNull(recipeIngredient.getRecipe());
    }

    @Test
    void setIngredients(){
        Recipe recipe = fakerGenerator.randomRecipe();
        List<RecipeIngredient> ingredients = Stream.generate(fakerGenerator::randomRecipeIngredient)
                .limit(10).toList();

        recipe.setIngredients(ingredients);
        assertEquals(10, recipe.getIngredients().size());
        assertTrue(
                recipe.getIngredients().stream().allMatch(recipeIngredient -> recipeIngredient.getRecipe().equals(recipe))
        );
    }

    @Test
    void setIngredients_update_with_shorter_list(){
        Recipe recipe = fakerGenerator.randomRecipe();
        List<RecipeIngredient> initialList = Stream.generate(fakerGenerator::randomRecipeIngredient).limit(10)
                        .collect(Collectors.toList());
        recipe.setIngredients(initialList);

        assertEquals(10, recipe.getIngredients().size());

        List<RecipeIngredient> shorterList = initialList.stream()
                .skip(initialList.size() / 2)
                .collect(Collectors.toList());

        assertEquals(5, shorterList.size());

        recipe.setIngredients(shorterList);

        assertEquals(5, recipe.getIngredients().size());
        assertEquals(5, initialList.stream()
                .filter(recipeIngredient -> recipeIngredient.getRecipe() != null)
                .count()
        );
    }

    @Test
    void setIngredients_null(){
        Recipe recipe = fakerGenerator.randomRecipe();
        List<RecipeIngredient> initialList = Stream.generate(fakerGenerator::randomRecipeIngredient).limit(10)
                .collect(Collectors.toList());
        recipe.setIngredients(initialList);

        recipe.setIngredients(null);

        assertEquals(0, recipe.getIngredients().size());
    }

    @Test
    void setIngredients_update_with_longer_list(){
        Recipe recipe = fakerGenerator.randomRecipe();
        List<RecipeIngredient> initialList = Stream.generate(fakerGenerator::randomRecipeIngredient).limit(10)
                .collect(Collectors.toList());
        recipe.setIngredients(initialList);

        assertEquals(10, recipe.getIngredients().size());

        List<RecipeIngredient> longerList = Stream.concat(
                initialList.stream(),
                Stream.generate(fakerGenerator::randomRecipeIngredient).limit(5)
        ).collect(Collectors.toList());

        recipe.setIngredients(longerList);

        assertEquals(15, recipe.getIngredients().size());
    }

    @Test
    void setIngredients_then_shorter_list_combined_with_new_ingredients(){
        Recipe recipe = fakerGenerator.randomRecipe();
        List<RecipeIngredient> initialList = Stream.generate(fakerGenerator::randomRecipeIngredient).limit(2)
                .collect(Collectors.toList());
        recipe.setIngredients(initialList);

        assertEquals(2, recipe.getIngredients().size());

        initialList.remove(0);

        List<RecipeIngredient> changedList = Stream.concat(
                initialList.stream(),
                Stream.generate(fakerGenerator::randomRecipeIngredient).limit(2)
        ).collect(Collectors.toList());

        recipe.setIngredients(changedList);

        assertEquals(3, recipe.getIngredients().size());
    }

    @Test
    void addInstructions(){
        RecipeInstruction instruction = fakerGenerator.randomInstruction();

        Recipe recipe = fakerGenerator.randomRecipe();

        assertDoesNotThrow(
                () -> recipe.addInstructions(instruction)
        );

        assertEquals(1, recipe.getInstructions().size());
        assertNotNull(instruction.getRecipe());
    }

    @Test
    void removeInstructions(){
        RecipeInstruction instruction = fakerGenerator.randomInstruction();

        Recipe recipe = fakerGenerator.randomRecipe();

        recipe.addInstructions(instruction);

        assertDoesNotThrow(
                () -> recipe.removeInstructions(instruction)
        );

        assertEquals(0, recipe.getInstructions().size());
        assertNull(instruction.getRecipe());
    }

}