package com.example.tastifybackend.domain.recipe_review;

import com.example.tastifybackend.domain.recipe_review.dto.RecipeReviewDto;
import com.example.tastifybackend.domain.recipe_review.dto.RecipeReviewInput;
import com.example.tastifybackend.domain.templates.GenericCRUD;

public interface RecipeReviewService extends GenericCRUD<RecipeReviewInput, RecipeReviewDto> {
}
