package com.example.tastifybackend.domain.recipe_instruction.dto;

import com.example.tastifybackend.domain.audit.AuditDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeInstructionDto {
    private String id;
    private String instruction;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String recipeId;
    private AuditDto audit;
}
