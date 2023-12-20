package com.example.tastifybackend;

import com.example.tastifybackend.domain.category.Category;
import com.example.tastifybackend.domain.category.dto.CategoryInput;
import com.example.tastifybackend.domain.recipe.Recipe;
import com.example.tastifybackend.domain.recipe.RecipeStatus;
import com.example.tastifybackend.domain.recipe.dto.RecipeInput;
import com.example.tastifybackend.domain.recipe_ingredient.RecipeIngredient;
import com.example.tastifybackend.domain.recipe_ingredient.dto.RecipeIngredientInput;
import com.example.tastifybackend.domain.recipe_instruction.RecipeInstruction;
import com.example.tastifybackend.domain.recipe_instruction.dto.RecipeInstructionInput;
import com.example.tastifybackend.domain.recipe_review.RecipeReview;
import com.example.tastifybackend.domain.recipe_review.dto.RecipeReviewInput;
import net.datafaker.Faker;

import java.math.BigDecimal;

public class FakerGenerator {

    private static final FakerGenerator INSTANCE = new FakerGenerator();

    public static FakerGenerator getInstance(){
        return INSTANCE;
    }

    private final Faker faker = new Faker();

    private FakerGenerator(){}



    public RecipeIngredient randomRecipeIngredient(){
        return RecipeIngredient.builder()
                .ingredient(faker.food().ingredient())
                .measurement(faker.food().measurement())
                .amount(BigDecimal.valueOf(faker.number().numberBetween(1, 100)))
                .build();
    }

    public Category randomCategory(){
        return Category.builder()
                .value(faker.lorem().characters(3, 20))
                .build();
    }

    public RecipeInstruction randomInstruction(){
        return RecipeInstruction.builder()
                .instruction(faker.lorem().sentence(5))
                .build();
    }

    public RecipeReview randomRecipeReview(){
        return RecipeReview.builder()
                .comment(faker.lorem().sentence(5))
                .score(faker.random().nextInt(1, 5))
                .build();
    }

    public Recipe randomRecipe(){
        RecipeStatus recipeStatus = faker.random().nextBoolean() ? RecipeStatus.PUBLIC : RecipeStatus.PRIVATE;
        return Recipe.builder()
                .status(recipeStatus)
                .description(faker.lorem().sentence(10))
                .recipeName(faker.food().dish())
                .build();
    }

    public RecipeInput randomRecipeInput(){
        RecipeStatus recipeStatus = faker.random().nextBoolean() ? RecipeStatus.PUBLIC : RecipeStatus.PRIVATE;
        return RecipeInput.builder()
                .recipeName(faker.food().dish())
                .description(faker.lorem().sentence(10))
                .status(recipeStatus)
                .build();
    }

    public RecipeInstruction randomRecipeInstruction(){
        return RecipeInstruction.builder()
                .instruction(faker.lorem().sentence(5))
                .build();
    }

    public RecipeIngredientInput randomRecipeIngredientInput(){
        return RecipeIngredientInput.builder()
                .amount(BigDecimal.valueOf(faker.number().numberBetween(1, 100)))
                .measurement(faker.food().measurement())
                .ingredient(faker.food().ingredient())
                .build();
    }

    public CategoryInput randomCategoryInput(){
        return CategoryInput.builder()
                .value(faker.lorem().characters(3, 10))
                .build();
    }

    public RecipeInstructionInput randomRecipeInstructionInput(){
        return RecipeInstructionInput.builder()
                .instruction(faker.lorem().sentence(4))
                .build();
    }

    public RecipeReviewInput randomRecipeReviewInput(){
        return RecipeReviewInput.builder()
                .score(faker.random().nextInt(1, 5))
                .comment(faker.lorem().sentence(10))
                .build();
    }

}
