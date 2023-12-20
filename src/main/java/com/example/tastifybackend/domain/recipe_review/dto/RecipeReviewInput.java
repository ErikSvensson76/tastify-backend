package com.example.tastifybackend.domain.recipe_review.dto;

import com.example.tastifybackend.validation.markers.OnPost;
import com.example.tastifybackend.validation.markers.OnPut;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class RecipeReviewInput {
    @NotBlank(groups = OnPut.class)
    private String id;
    @NotBlank(message = "{NOT_BLANK}", groups = OnPost.class)
    private String recipeId;
    @NotNull(message = "{NOT_BLANK}", groups = {OnPost.class})
    @Size(message = "{SCORE_RANGE}", min = 1, max = 5, groups = {OnPost.class, OnPut.class})
    private Integer score;
    @Length(message = "{REVIEW_COMMENT_LENGTH}", max = 500, groups = {OnPost.class, OnPut.class})
    private String comment;
}
