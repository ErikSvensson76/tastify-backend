package com.example.tastifybackend.domain.recipe_review;

import com.example.tastifybackend.domain.EntityToDtoConverter;
import com.example.tastifybackend.domain.recipe_review.dto.RecipeReviewDto;
import com.example.tastifybackend.domain.recipe_review.dto.RecipeReviewInput;
import com.example.tastifybackend.domain.recipe_review.repository.RecipeReviewRepository;
import com.example.tastifybackend.domain.recipe_review.service.RecipeReviewPersistenceService;
import com.example.tastifybackend.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeReviewServiceImpl implements RecipeReviewService{

    private final RecipeReviewPersistenceService persistenceService;
    private final RecipeReviewRepository repository;
    private final EntityToDtoConverter converter;

    @Override
    public RecipeReviewDto save(RecipeReviewInput input) {
        return converter.recipeReviewToDto(
                persistenceService.save(input)
        );
    }

    @Override
    public List<RecipeReviewDto> saveAll(List<RecipeReviewInput> inputs) {
        return persistenceService.saveAll(inputs).stream()
                .map(converter::recipeReviewToDto)
                .toList();
    }

    @Override
    public RecipeReviewDto findById(String id) {
        return repository.findById(id)
                .map(converter::recipeReviewToDtoFull)
                .orElseThrow(() -> new EntityNotFoundException("RecipeReview not found, id: " + id));
    }

    @Override
    public List<RecipeReviewDto> findAll() {
        return repository.findAll().stream()
                .map(converter::recipeReviewToDto)
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
