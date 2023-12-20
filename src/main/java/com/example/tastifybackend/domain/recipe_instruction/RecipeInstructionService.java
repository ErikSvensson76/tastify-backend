package com.example.tastifybackend.domain.recipe_instruction;

import com.example.tastifybackend.domain.recipe_instruction.dto.RecipeInstructionDto;
import com.example.tastifybackend.domain.recipe_instruction.dto.RecipeInstructionInput;
import com.example.tastifybackend.domain.templates.GenericCRUD;

public interface RecipeInstructionService extends GenericCRUD<RecipeInstructionInput, RecipeInstructionDto> {
}
