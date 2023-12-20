package com.example.tastifybackend.domain.category.service;

import com.example.tastifybackend.domain.category.Category;
import com.example.tastifybackend.domain.category.dto.CategoryInput;

import java.util.List;

public interface CategoryPersistenceService {
    Category save(CategoryInput categoryInput);
    List<Category> saveAll(List<CategoryInput> categoryInputs);
    void delete(String id);
    void deleteAll(List<String> ids);
}
