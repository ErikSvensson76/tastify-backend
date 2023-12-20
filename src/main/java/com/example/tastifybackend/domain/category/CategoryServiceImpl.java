package com.example.tastifybackend.domain.category;

import com.example.tastifybackend.domain.EntityToDtoConverter;
import com.example.tastifybackend.domain.category.dto.CategoryDto;
import com.example.tastifybackend.domain.category.dto.CategoryInput;
import com.example.tastifybackend.domain.category.repository.CategoryRepository;
import com.example.tastifybackend.domain.category.service.CategoryPersistenceService;
import com.example.tastifybackend.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService{

    private final CategoryPersistenceService persistenceService;
    private final CategoryRepository repository;
    private final EntityToDtoConverter converter;


    @Override
    public CategoryDto findByValue(String value) {
        return repository.findByValue(value)
                .map(converter::categoryToDto)
                .orElseThrow(() -> new EntityNotFoundException("Category not found, value: " + value));
    }

    @Override
    public List<CategoryDto> findByValueContains(String value) {
        return repository.findByValueContains(value).stream()
                .map(converter::categoryToDto)
                .toList();
    }

    @Override
    public CategoryDto save(CategoryInput input) {
        return converter.categoryToDto(
                persistenceService.save(input)
        );
    }

    @Override
    public List<CategoryDto> saveAll(List<CategoryInput> inputs) {
        return persistenceService.saveAll(inputs).stream()
                .map(converter::categoryToDto)
                .toList();
    }

    @Override
    public CategoryDto findById(String id) {
        return repository.findById(id)
                .map(converter::categoryToDto)
                .orElseThrow(() -> new EntityNotFoundException("Category not found, id: " + id));
    }

    @Override
    public List<CategoryDto> findAll() {
        return repository.findAll().stream()
                .map(converter::categoryToDto)
                .toList();
    }

    @Override
    public void delete(String id) {
        persistenceService.delete(id);
    }

    @Override
    public void deleteAll(List<String> ids) {
        persistenceService.deleteAll(ids);
    }
}
