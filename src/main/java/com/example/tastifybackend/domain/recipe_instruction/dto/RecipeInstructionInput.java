package com.example.tastifybackend.domain.recipe_instruction.dto;

import com.example.tastifybackend.validation.markers.OnPost;
import com.example.tastifybackend.validation.markers.OnPut;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class RecipeInstructionInput implements Serializable {
    @NotBlank(message = "{NOT_BLANK}", groups = OnPut.class)
    private String id;
    @NotBlank(message = "{NOT_BLANK}", groups = {OnPost.class, OnPut.class})
    @Length(message = "{INSTRUCTION_LENGTH}", max = 500, groups = {OnPost.class, OnPut.class})
    private String instruction;
}
