package com.example.tastifybackend.domain.recipe.dto;

import com.example.tastifybackend.domain.category.dto.CategoryInput;
import com.example.tastifybackend.domain.recipe.RecipeStatus;
import com.example.tastifybackend.domain.recipe_ingredient.dto.RecipeIngredientInput;
import com.example.tastifybackend.domain.recipe_instruction.dto.RecipeInstructionInput;
import com.example.tastifybackend.validation.markers.OnPost;
import com.example.tastifybackend.validation.markers.OnPut;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class RecipeInput implements Serializable {
    @NotBlank(message = "{NOT_BLANK}" ,groups = OnPut.class)
    private String id;
    @NotBlank(message = "{NOT_BLANK}", groups = {OnPut.class, OnPost.class})
    private String recipeName;
    @Size(message = "{SIZE_DESCRIPTION}", max = 1000, groups = {OnPut.class, OnPost.class})
    private String description;
    @NotNull(message = "{NOT_BLANK}", groups = {OnPut.class, OnPost.class})
    private RecipeStatus status;
    @NotNull(groups = {OnPut.class, OnPost.class})
    private List<@Valid CategoryInput> categories;
    @NotNull(groups = {OnPut.class, OnPost.class})
    private List<@Valid RecipeIngredientInput> ingredients;
    @NotNull(groups = {OnPut.class, OnPost.class})
    private List<@Valid RecipeInstructionInput> instructions;
}
