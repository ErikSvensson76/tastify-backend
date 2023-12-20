package com.example.tastifybackend.domain.category.repository;

import com.example.tastifybackend.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
    @Query("SELECT c " +
            "FROM Category c " +
            "WHERE LOWER(c.value) = LOWER(:value)")
    Optional<Category> findByValue(@Param("value") String value);

    @Query("SELECT c " +
            "FROM Category c " +
            "WHERE LOWER(c.value) LIKE LOWER(CONCAT('%',:value,'%'))")
    List<Category> findByValueContains(@Param("value") String value);
}
