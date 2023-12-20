package com.example.tastifybackend.domain.recipe_ingredient;

import com.example.tastifybackend.domain.audit.AuditEntity;
import com.example.tastifybackend.domain.recipe.Recipe;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pk_recipe_ingredient", updatable = false)
    private String id;
    @ManyToOne(
            cascade = {CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "fk_recipe")
    private Recipe recipe;
    @Column(name = "ingredient_name")
    private String ingredient;
    @Column(name = "amount", precision = 2)
    private BigDecimal amount;
    @Column(name = "measurement")
    private String measurement;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredient that = (RecipeIngredient) o;
        return Objects.equals(id, that.id) && Objects.equals(amount, that.amount) && Objects.equals(measurement, that.measurement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, measurement);
    }
}
