package com.example.tastifybackend.domain.recipe_review.dto;

import com.example.tastifybackend.domain.audit.AuditDto;
import com.example.tastifybackend.domain.recipe.dto.RecipeDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeReviewDto implements Serializable {
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String user;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RecipeDto recipe;
    private Integer score;
    private String comment;
    private AuditDto audit;
}
