package com.example.tastifybackend.domain.recipe.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeAndScores {
    String recipeId;
    Integer score;
}
