package com.example.tastifybackend.domain.category;

import com.example.tastifybackend.domain.audit.AuditEntity;
import com.example.tastifybackend.domain.recipe.Recipe;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Category extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pk_recipe_category", updatable = false)
    private String id;
    @Column(name = "category_value", unique = true)
    private String value;

    @ManyToMany(
            cascade = {CascadeType.DETACH, CascadeType.REFRESH},
            fetch = FetchType.LAZY,
            mappedBy = "categories"
    )
    private Set<Recipe> recipes;

    public Set<Recipe> getRecipes() {
        if(recipes == null) recipes = new HashSet<>();
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        if(recipes == null) recipes = new HashSet<>();
        if(recipes.isEmpty()){
            if(this.recipes != null){
                this.recipes.forEach(recipe -> recipe.getCategories().remove(this));
            }
        }else{
            recipes.forEach(recipe -> recipe.getCategories().add(this));
        }
        this.recipes = recipes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(value, category.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
