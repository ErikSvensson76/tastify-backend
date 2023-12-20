package com.example.tastifybackend.domain.recipe_review;

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
@Table(name = "recipe_review")
public class RecipeReview extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pk_recipe_review", updatable = false)
    private String id;
    @Column(name = "user_id")
    private String user;
    @ManyToOne(
            cascade = {CascadeType.DETACH, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "fk_recipe")
    private Recipe recipe;
    @Column(name = "score")
    private Integer score;
    @Column(name = "review_comment", length = 500)
    private String comment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeReview that = (RecipeReview) o;
        return Objects.equals(id, that.id) && Objects.equals(score, that.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score);
    }
}
