package com.example.tastifybackend.domain.recipe_instruction.service;

import com.example.tastifybackend.domain.recipe_instruction.RecipeInstruction;
import com.example.tastifybackend.domain.recipe_instruction.dto.RecipeInstructionInput;

import java.util.List;

public interface RecipeInstructionPersistenceService {
    RecipeInstruction save(RecipeInstructionInput recipeInstructionInput);
    List<RecipeInstruction> saveAll(List<RecipeInstructionInput> recipeInstructionInputs);
    void delete(String id);
    void deleteAll(List<String> ids);
}
