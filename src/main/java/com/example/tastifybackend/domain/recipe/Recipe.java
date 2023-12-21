package com.example.tastifybackend.domain.recipe;

import com.example.tastifybackend.domain.audit.AuditEntity;
import com.example.tastifybackend.domain.category.Category;
import com.example.tastifybackend.domain.recipe_ingredient.RecipeIngredient;
import com.example.tastifybackend.domain.recipe_instruction.RecipeInstruction;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "recipe")
public class Recipe extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pk_recipe", updatable = false)
    private String id;
    @Column(name = "recipe_name")
    private String recipeName;
    @Column(name = "description", length = 1000)
    private String description;
    @Column(name = "status")
    private RecipeStatus status;

    @ManyToMany(
            cascade = {CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "recipe_category",
            joinColumns = @JoinColumn(name = "fk_recipe"),
            inverseJoinColumns = @JoinColumn(name = "fk_category")
    )
    private Set<Category> categories;
    @OneToMany(
            cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE},
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "recipe"
    )
    private List<RecipeIngredient> ingredients;
    @OneToMany(
            cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.PERSIST},
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "recipe"
    )
    private List<RecipeInstruction> instructions;

    public Set<Category> getCategories() {
        if(this.categories == null) this.categories = new HashSet<>();
        return new HashSet<>(categories);
    }

    public void setCategories(Set<Category> categories){
        if(categories == null) categories = new HashSet<>();
        if(categories.isEmpty()){
            if(this.categories != null){
                this.categories.forEach(category -> category.getRecipes().remove(this));
            }
        }else {
            categories.forEach(category -> category.getRecipes().add(this));
        }
        this.categories = categories;
    }

    public void addCategories(Category...categories){
        if(categories == null) return;
        if(this.categories == null) this.categories = new HashSet<>();
        for(Category category : categories){
            this.categories.add(category);
            category.getRecipes().add(this);
        }
    }

    public void removeCategories(Category...categories){
        if(categories == null) return;
        if(this.categories == null) this.categories = new HashSet<>();
        for(Category category : categories){
            this.categories.remove(category);
            category.getRecipes().remove(this);
        }
    }

    public List<RecipeIngredient> getIngredients() {
        if(this.ingredients == null) this.ingredients = new ArrayList<>();
        return new ArrayList<>(ingredients);
    }

    public void setIngredients(List<RecipeIngredient> ingredients){
        if(this.ingredients == null) this.ingredients = new ArrayList<>();
        if(ingredients == null || ingredients.isEmpty()){
            removeIngredients(this.ingredients.toArray(RecipeIngredient[]::new));
        }else {
            RecipeIngredient [] toRemove = this.ingredients.stream()
                    .filter(recipeIngredient -> !ingredients.contains(recipeIngredient))
                    .toArray(RecipeIngredient[]::new);
            RecipeIngredient [] toAdd = ingredients.stream()
                    .filter(recipeIngredient -> !this.ingredients.contains(recipeIngredient))
                    .toArray(RecipeIngredient[]::new);
            removeIngredients(toRemove);
            addIngredients(toAdd);
        }
    }

    public void addIngredients(RecipeIngredient...ingredients){
        if(ingredients == null) return;
        if(this.ingredients == null) this.ingredients = new ArrayList<>();
        for(RecipeIngredient recipeIngredient : ingredients){
            if(!this.ingredients.contains(recipeIngredient)){
                this.ingredients.add(recipeIngredient);
                recipeIngredient.setRecipe(this);
            }
        }
    }

    public void removeIngredients(RecipeIngredient...ingredients){
        if(ingredients == null) return;
        if(this.ingredients == null) this.ingredients = new ArrayList<>();
        for (RecipeIngredient recipeIngredient : ingredients){
            if(this.ingredients.remove(recipeIngredient)){
                recipeIngredient.setRecipe(null);
            }
        }
    }

    public List<RecipeInstruction> getInstructions() {
        if(instructions == null) instructions = new ArrayList<>();
        return new ArrayList<>(instructions);
    }

    public void setInstructions(List<RecipeInstruction> instructions){
        if(this.instructions == null) this.instructions = new ArrayList<>();
        if(instructions == null || instructions.isEmpty()){
            removeInstructions(this.instructions.toArray(RecipeInstruction[]::new));
        }else {
            RecipeInstruction [] toRemove = this.instructions.stream()
                    .filter(recipeInstruction -> !instructions.contains(recipeInstruction))
                    .toArray(RecipeInstruction[]::new);
            RecipeInstruction [] toAdd = instructions.stream()
                    .filter(recipeInstruction -> !this.instructions.contains(recipeInstruction))
                    .toArray(RecipeInstruction[]::new);
            removeInstructions(toRemove);
            addInstructions(toAdd);
        }
    }

    public void addInstructions(RecipeInstruction...instructions){
        if(instructions == null || instructions.length == 0) return;
        if(this.instructions == null) this.instructions = new ArrayList<>();
        for(RecipeInstruction instruction : instructions){
            if(!this.instructions.contains(instruction)){
                this.instructions.add(instruction);
                instruction.setRecipe(this);
            }
        }
    }

    public void removeInstructions(RecipeInstruction...instructions){
        if(instructions == null || instructions.length == 0) return;
        if(this.instructions == null) this.instructions = new ArrayList<>();
        for(RecipeInstruction instruction : instructions){
            if(this.instructions.remove(instruction)){
                instruction.setRecipe(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(id, recipe.id) && Objects.equals(recipeName, recipe.recipeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipeName);
    }
}
