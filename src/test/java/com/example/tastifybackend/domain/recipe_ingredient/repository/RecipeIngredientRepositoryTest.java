package com.example.tastifybackend.domain.recipe_ingredient.repository;

import com.example.tastifybackend.FakerGenerator;
import com.example.tastifybackend.domain.recipe_ingredient.RecipeIngredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext
class RecipeIngredientRepositoryTest {

    @Autowired
    private RecipeIngredientRepository underTest;
    @Autowired
    private TestEntityManager em;

    private final FakerGenerator fakerGenerator = FakerGenerator.getInstance();

    List<RecipeIngredient> getRecipeIngredients(){
        return Stream.generate(fakerGenerator::randomRecipeIngredient)
            .limit(20)
            .collect(Collectors.toList());
    }

    @BeforeEach
    void setUp() {
        getRecipeIngredients()
            .forEach(recipeIngredient -> em.persist(recipeIngredient));
    }

    @Test
    void getAllIngredientNames() {
      List<String> result = underTest.getAllIngredientNames();
      assertNotNull(result);
      assertFalse(result.isEmpty());
      for(String ingredient : result){
        long count = result.stream()
            .filter(i -> i.equals(ingredient))
            .count();
        assertEquals(1, count);
      }

    }
}