package com.example.tastifybackend.domain.recipe_ingredient.dto;

import com.example.tastifybackend.validation.markers.OnPost;
import com.example.tastifybackend.validation.markers.OnPut;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class RecipeIngredientInput implements Serializable {
    @NotBlank(groups = OnPut.class)
    private String id;
    private String recipeId;
    @NotNull(groups = {OnPost.class, OnPut.class})
    private String ingredient;
    @NotNull(message = "{NOT_BLANK}", groups = {OnPost.class, OnPut.class})
    private BigDecimal amount;
    @NotBlank(message = "{NOT_BLANK}", groups = {OnPost.class, OnPut.class})
    private String measurement;
}
