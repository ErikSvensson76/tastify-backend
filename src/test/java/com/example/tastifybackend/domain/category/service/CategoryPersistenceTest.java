package com.example.tastifybackend.domain.category.service;

import com.example.tastifybackend.domain.category.Category;
import com.example.tastifybackend.domain.category.dto.CategoryInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
class CategoryPersistenceTest {

    @Autowired
    TestEntityManager em;
    @Autowired
    CategoryPersistence underTest;


    @Test
    void save_create() {
        String expectedValue = "test";
        CategoryInput categoryInput = CategoryInput.builder().value(expectedValue).build();

        Category actual = underTest.save(categoryInput);
        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertEquals(expectedValue, actual.getValue());
    }

    @Test
    void save_get(){
        em.persist(Category.builder().value("Test1").build());
        CategoryInput categoryInput = CategoryInput.builder().value("test1").build();

        Category actual = underTest.save(categoryInput);
        assertNotNull(actual);
        assertEquals("Test1", actual.getValue());
    }

    @Test
    void save_update(){
        Category source = em.persist(Category.builder().value("test").build());
        String expected = source.getValue() + "1";
        CategoryInput categoryInput = CategoryInput.builder()
                .value(source.getValue()+"1")
                .id(source.getId())
                .build();

        Category actual = underTest.save(categoryInput);
        assertNotNull(actual);
        assertEquals(expected, actual.getValue());
    }

    @Test
    void saveAll() {
        List<CategoryInput> persistedInputs = Stream.of(Category.builder().value("test1").build(), Category.builder().value("test2").build())
                .map(em::persist)
                .map(category -> CategoryInput.builder().id(category.getId()).value(category.getValue()).build()).toList();
        List<CategoryInput> newInputs = List.of(CategoryInput.builder().value("test3").build(), CategoryInput.builder().value("test4").build());

        List<CategoryInput> inData = Stream.of(persistedInputs, newInputs)
                .flatMap(Collection::stream)
                .toList();

        List<Category> actual = underTest.saveAll(inData);
        assertEquals(4, actual.size());
    }
}