package com.example.tastifybackend.config;

import com.example.tastifybackend.domain.recipe.dto.RecipeAndScores;
import com.example.tastifybackend.domain.recipe_review.repository.RecipeReviewRepository;
import com.example.tastifybackend.domain.recipe_review.service.AverageReviewScoreStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Configuration
public class AverageReviewStorageConfig {

    @Bean
    public AverageReviewScoreStorage averageReviewStorage(
            RecipeReviewRepository reviewRepository
    ){
        ConcurrentMap<String, List<Integer>> map = new ConcurrentHashMap<>();
        List<RecipeAndScores> recipeAndScores = reviewRepository.getScoresGroupedByRecipeId();

        for(final String recipeId : recipeAndScores.stream().map(RecipeAndScores::getRecipeId).distinct().toList()){
            List<Integer> scores = recipeAndScores.stream()
                    .filter(ras -> ras.getRecipeId().equals(recipeId))
                    .map(RecipeAndScores::getScore)
                    .toList();
            map.put(recipeId, scores);
        }

        return new AverageReviewScoreStorage(map);
    }

}
