package com.example.tastifybackend.domain.recipe_review.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

@RequiredArgsConstructor
public class AverageReviewScoreStorage {

    private final ConcurrentMap<String, List<Integer>> recipeScoreMap;

    public void updateScore(@NotBlank String recipeId, @NotNull Integer score){
        List<Integer> recipeScores = Optional.ofNullable(recipeScoreMap.get(recipeId))
                .orElse(new ArrayList<>());
        recipeScores.add(score);
        recipeScoreMap.put(recipeId, recipeScores);
    }

    void removeScore(String recipeId, Integer valueToRemove){
        if (valueToRemove == null || valueToRemove < 1 || valueToRemove > 5) return;
        if(recipeScoreMap.get(recipeId) != null){
            int index  =  recipeScoreMap.get(recipeId).indexOf(valueToRemove);
            if(index != -1){
                List<Integer> scoreList = recipeScoreMap.get(recipeId);
                scoreList.remove(index);
                recipeScoreMap.put(recipeId, scoreList);
            }
        }
    }


    public void removeEntry(String recipeId){
        recipeScoreMap.remove(recipeId);
    }

    public BigDecimal getAverageScore(String recipeId){
        Optional<List<Integer>> recipeScores = Optional.ofNullable(recipeScoreMap.get(recipeId));
        if(recipeScores.isPresent()){
            List<Integer> scores = recipeScores.get();
            BigDecimal bigDecimal = BigDecimal.ZERO;
            for(Integer score : scores){
                bigDecimal = bigDecimal.add(BigDecimal.valueOf(score));
            }
            return bigDecimal.divide(BigDecimal.valueOf(scores.size()), 2, RoundingMode.HALF_EVEN);
        }
        return BigDecimal.ZERO;
    }
}
