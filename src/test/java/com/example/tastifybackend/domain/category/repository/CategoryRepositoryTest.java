package com.example.tastifybackend.domain.category.repository;

import com.example.tastifybackend.domain.category.Category;
import com.example.tastifybackend.domain.recipe.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext
class CategoryRepositoryTest {

    @Autowired
    TestEntityManager em;
    @Autowired
    CategoryRepository underTest;

    public List<Category> source(){
        return List.of(
                Category.builder().value("test1").build(),
                Category.builder().value("test2").build()
        );
    }

    List<Category> persistedCategories;


    @BeforeEach
    void setUp() {
        persistedCategories = source().stream()
                .map(em::persist)
                .toList();
    }

    @Test
    void findByValue() {
        Category expected = persistedCategories.get(0);

        Optional<Category> response = underTest.findByValue(expected.getValue().toUpperCase());
        assertTrue(response.isPresent(), "No Category with value: " + expected.getValue().toUpperCase()+ " was found");
        Category actual = response.get();
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getValue(), actual.getValue());
        assertEquals(0, actual.getRecipes().size());
    }
}