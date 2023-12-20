package com.example.tastifybackend.domain.recipe_review.service;

import com.example.tastifybackend.domain.recipe.repository.RecipeRepository;
import com.example.tastifybackend.domain.recipe_review.RecipeReview;
import com.example.tastifybackend.domain.recipe_review.dto.RecipeReviewInput;
import com.example.tastifybackend.domain.recipe_review.repository.RecipeReviewRepository;
import com.example.tastifybackend.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeReviewPersistence implements RecipeReviewPersistenceService{

    private final RecipeReviewRepository reviewRepository;
    private final RecipeRepository recipeRepository;

    private final AverageReviewScoreStorage reviewScoreStorage;


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public RecipeReview save(RecipeReviewInput input) {
        if(input.getId() == null){
            RecipeReview review = reviewRepository.save(
                    RecipeReview.builder()
                    .recipe(recipeRepository.findById(input.getRecipeId())
                            .orElseThrow(() ->  new EntityNotFoundException("Recipe not found, id: " + input.getRecipeId())))
                    .score(input.getScore())
                    .comment(input.getComment())
                    .build()
            );
            reviewScoreStorage.updateScore(input.getRecipeId(), review.getScore());
            return review;
        }
        RecipeReview review = reviewRepository.findById(input.getId())
                .orElseThrow(() -> new EntityNotFoundException("RecipeReview not found, id: " + input.getId()));
        review.setComment(input.getComment());
        review = reviewRepository.save(review);
        return review;
    }

    @Override
    public List<RecipeReview> saveAll(List<RecipeReviewInput> inputs) {
        return inputs.stream()
                .map(this::save)
                .toList();
    }

    @Override
    public void delete(String id) {
        reviewRepository.findById(id)
                .map(toDelete -> {
                    Optional.ofNullable(toDelete.getRecipe())
                            .ifPresent(recipe -> {
                                recipe.removeReviews(toDelete);
                                reviewScoreStorage.removeScore(recipe.getId(), toDelete.getScore());
                            });
                    return toDelete;
                }).ifPresent(reviewRepository::delete);
    }

    @Override
    public void deleteAll(List<String> ids) {
        reviewRepository.deleteAllById(ids);
    }

}
