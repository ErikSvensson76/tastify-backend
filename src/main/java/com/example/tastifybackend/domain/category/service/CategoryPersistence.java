package com.example.tastifybackend.domain.category.service;

import com.example.tastifybackend.domain.category.Category;
import com.example.tastifybackend.domain.category.dto.CategoryInput;
import com.example.tastifybackend.domain.category.repository.CategoryRepository;
import com.example.tastifybackend.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = RuntimeException.class)
public class CategoryPersistence implements CategoryPersistenceService{

    private final CategoryRepository categoryRepository;

    @Override
    public Category save(CategoryInput categoryInput) {
        Optional<Category> categoryOptional = categoryRepository.findByValue(categoryInput.getValue());
        return categoryOptional.orElseGet(() -> categoryRepository.save(Category.builder()
                .value(categoryInput.getValue())
                .build()
        ));
    }

    @Override
    public List<Category> saveAll(List<CategoryInput> categoryInputs) {
        return categoryInputs.stream()
                .map(this::save)
                .toList();
    }

    @Override
    public void delete(String id) {
        Category category = categoryRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Error getting Category id: " + id));
        category.setRecipes(null);
        categoryRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<String> ids) {
        List<Category> categories = categoryRepository.findAllById(ids);
        categories.forEach(category -> category.setRecipes(null));

        categoryRepository.deleteAll(categories);
    }


}
