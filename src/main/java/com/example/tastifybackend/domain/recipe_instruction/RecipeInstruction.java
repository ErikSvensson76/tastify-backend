package com.example.tastifybackend.domain.recipe_instruction;

import com.example.tastifybackend.domain.audit.AuditEntity;
import com.example.tastifybackend.domain.recipe.Recipe;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "recipe_instruction")
public class RecipeInstruction extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pk_recipe_instruction", updatable = false)
    private String id;
    @Column(name = "instruction", length = 500)
    private String instruction;
    @ManyToOne(
            cascade = {CascadeType.DETACH, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "fk_recipe")
    private Recipe recipe;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeInstruction that = (RecipeInstruction) o;
        return Objects.equals(id, that.id) && Objects.equals(instruction, that.instruction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, instruction);
    }
}
