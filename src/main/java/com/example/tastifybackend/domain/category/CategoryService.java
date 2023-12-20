package com.example.tastifybackend.domain.category;

import com.example.tastifybackend.domain.category.dto.CategoryDto;
import com.example.tastifybackend.domain.category.dto.CategoryInput;
import com.example.tastifybackend.domain.templates.GenericCRUD;

import java.util.List;

public interface CategoryService extends GenericCRUD<CategoryInput, CategoryDto> {
    CategoryDto findByValue(String value);
    List<CategoryDto> findByValueContains(String value);
}
