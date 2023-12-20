package com.example.tastifybackend.domain.recipe_instruction.repository;

import com.example.tastifybackend.domain.recipe_instruction.RecipeInstruction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeInstructionRepository extends JpaRepository<RecipeInstruction, String> {
}
