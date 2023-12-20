package com.example.tastifybackend.domain.recipe;

import com.example.tastifybackend.domain.EntityToDtoConverter;
import com.example.tastifybackend.domain.recipe.dto.RecipeDto;
import com.example.tastifybackend.domain.recipe.dto.RecipeInput;
import com.example.tastifybackend.domain.recipe.repository.RecipeRepository;
import com.example.tastifybackend.domain.recipe.service.RecipePersistenceService;
import com.example.tastifybackend.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeServiceImpl implements RecipeService {

    private final RecipePersistenceService persistenceService;
    private final RecipeRepository repository;
    private final EntityToDtoConverter converter;

    @Override
    public RecipeDto save(RecipeInput input) {
       return converter.recipeToDtoFull(
               persistenceService.save(input)
       );
    }

    @Override
    public List<RecipeDto> saveAll(List<RecipeInput> inputs) {
        return persistenceService.saveAll(inputs).stream()
                .map(converter::recipeToDtoFull)
                .toList();
    }

    @Override
    public RecipeDto findById(String id) {
        return repository.findById(id)
                .map(converter::recipeToDtoFull)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found, id: " + id));
    }

    @Override
    public List<RecipeDto> findAll() {
        return repository.findAll().stream()
                .map(converter::recipeToDto)
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
