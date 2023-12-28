package com.example.tastifybackend.domain.recipe_ingredient;

import com.example.tastifybackend.domain.EntityToDtoConverter;
import com.example.tastifybackend.domain.recipe_ingredient.dto.RecipeIngredientDto;
import com.example.tastifybackend.domain.recipe_ingredient.dto.RecipeIngredientInput;
import com.example.tastifybackend.domain.recipe_ingredient.repository.RecipeIngredientRepository;
import com.example.tastifybackend.domain.recipe_ingredient.service.RecipeIngredientPersistenceService;
import com.example.tastifybackend.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeIngredientServiceImpl implements RecipeIngredientService {
    private final RecipeIngredientPersistenceService persistenceService;
    private final RecipeIngredientRepository repository;
    private final EntityToDtoConverter converter;

    @Override
    public RecipeIngredientDto save(RecipeIngredientInput input) {
        return converter.recipeIngredientToDto(
                persistenceService.save(input)
        );
    }

    @Override
    public List<RecipeIngredientDto> saveAll(List<RecipeIngredientInput> inputs) {
        return persistenceService.saveAll(inputs).stream()
                .map(converter::recipeIngredientToDto)
                .toList();
    }

    @Override
    public RecipeIngredientDto findById(String id) {
        return repository.findById(id)
                .map(converter::recipeIngredientToDto)
                .orElseThrow(() -> new EntityNotFoundException("RecipeIngredient not found, " + id));
    }

    @Override
    public List<RecipeIngredientDto> findAll() {
        return repository.findAll().stream()
                .map(converter::recipeIngredientToDto)
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

    @Override
    public List<String> getAllDistinctIngredients() {
        return repository.getAllIngredientNames();
    }

    @Override
    public List<RecipeIngredientDto> getByRecipeId(String id) {
        return repository.getByRecipeId(id).stream()
            .map(converter::recipeIngredientToDto)
            .toList();
    }

    @Override
    public List<RecipeIngredientDto> getByIngredientName(String ingredientName) {
        return repository.getByRecipeIngredientName(ingredientName).stream()
            .map(converter::recipeIngredientToDto)
            .toList();
    }
}
