package com.example.tastifybackend.domain.recipe.dto;

import com.example.tastifybackend.domain.audit.AuditDto;
import com.example.tastifybackend.domain.category.dto.CategoryDto;
import com.example.tastifybackend.domain.recipe.RecipeStatus;
import com.example.tastifybackend.domain.recipe_ingredient.dto.RecipeIngredientDto;
import com.example.tastifybackend.domain.recipe_instruction.dto.RecipeInstructionDto;
import com.example.tastifybackend.domain.recipe_review.dto.RecipeReviewDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDto implements Serializable {
    private String id;
    private String recipeName;
    private String description;
    private RecipeStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CategoryDto> categories;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<RecipeInstructionDto> instructions;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<RecipeReviewDto> reviews;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<RecipeIngredientDto> ingredients;
    private AuditDto audit;
    private BigDecimal averageScore;
}
