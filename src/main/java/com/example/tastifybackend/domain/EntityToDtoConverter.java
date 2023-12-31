package com.example.tastifybackend.domain;

import com.example.tastifybackend.domain.audit.AuditDto;
import com.example.tastifybackend.domain.category.Category;
import com.example.tastifybackend.domain.category.dto.CategoryDto;
import com.example.tastifybackend.domain.recipe.Recipe;
import com.example.tastifybackend.domain.recipe.dto.RecipeDto;
import com.example.tastifybackend.domain.recipe_ingredient.RecipeIngredient;
import com.example.tastifybackend.domain.recipe_ingredient.dto.RecipeIngredientDto;
import com.example.tastifybackend.domain.recipe_instruction.RecipeInstruction;
import com.example.tastifybackend.domain.recipe_instruction.dto.RecipeInstructionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Optional;

@Transactional
@Component
@RequiredArgsConstructor
public class EntityToDtoConverter {

    private AuditDto buildAuditDto(Timestamp created, Timestamp lastModified){
        return AuditDto.builder()
                .created(created)
                .lastModified(lastModified)
                .build();
    }

    public CategoryDto categoryToDto(Category category){
        CategoryDto dto = null;
        if(category != null){
            dto = new CategoryDto(
                    category.getId(),
                    category.getValue(),
                    buildAuditDto(category.getCreated(), category.getLastModified())
            );
        }
        return dto;
    }

    public RecipeIngredientDto recipeIngredientToDto(RecipeIngredient recipeIngredient){
        RecipeIngredientDto dto = null;
        if(recipeIngredient != null){
            dto = new RecipeIngredientDto(
                    recipeIngredient.getId(),
                    recipeIngredient.getIngredient(),
                    recipeIngredient.getAmount().setScale(2, RoundingMode.HALF_EVEN),
                    recipeIngredient.getMeasurement(),
                    buildAuditDto(recipeIngredient.getCreated(), recipeIngredient.getLastModified())
            );
        }
        return dto;
    }


    public RecipeInstructionDto recipeInstructionToDto(RecipeInstruction recipeInstruction) {
        RecipeInstructionDto dto = null;
        if(recipeInstruction != null){
            dto = RecipeInstructionDto.builder()
                    .id(recipeInstruction.getId())
                    .instruction(recipeInstruction.getInstruction())
                    .audit(buildAuditDto(recipeInstruction.getCreated(), recipeInstruction.getLastModified()))
                    .build();
        }
        return dto;
    }

    public RecipeDto recipeToDto(Recipe recipe) {
        RecipeDto dto = null;
        if(recipe != null){
            dto = RecipeDto.builder()
                    .id(recipe.getId())
                    .recipeName(recipe.getRecipeName())
                    .description(recipe.getDescription())
                    .status(recipe.getStatus())
                    .audit(buildAuditDto(recipe.getCreated(), recipe.getLastModified()))
                    .build();
        }
        return dto;
    }

    public RecipeDto recipeToDtoFull(Recipe recipe) {
        return Optional.of(recipeToDto(recipe))
                .map(recipeDto -> {
                    recipeDto.setCategories(
                            recipe.getCategories().stream()
                                    .map(this::categoryToDto)
                                    .toList()
                    );
                    recipeDto.setIngredients(
                            recipe.getIngredients().stream()
                                    .map(this::recipeIngredientToDto)
                                    .toList()
                    );
                    recipeDto.setInstructions(
                            recipe.getInstructions().stream()
                                    .map(this::recipeInstructionToDto)
                                    .toList()
                    );
                    return recipeDto;
                })
                .orElse(null);
    }
}
