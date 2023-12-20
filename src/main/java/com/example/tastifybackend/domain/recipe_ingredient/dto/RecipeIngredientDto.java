package com.example.tastifybackend.domain.recipe_ingredient.dto;

import com.example.tastifybackend.domain.audit.AuditDto;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeIngredientDto implements Serializable {
    private String id;
    private String ingredient;
    private BigDecimal amount;
    private String measurement;
    private AuditDto audit;
}
