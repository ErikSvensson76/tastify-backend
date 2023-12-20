package com.example.tastifybackend.domain.recipe_review.service;

import com.example.tastifybackend.domain.recipe_review.RecipeReview;
import com.example.tastifybackend.domain.recipe_review.dto.RecipeReviewInput;

import java.util.List;

public interface RecipeReviewPersistenceService {
    RecipeReview save(RecipeReviewInput input);
    List<RecipeReview> saveAll(List<RecipeReviewInput> inputs);
    void delete(String id);
    void deleteAll(List<String> ids);
}
