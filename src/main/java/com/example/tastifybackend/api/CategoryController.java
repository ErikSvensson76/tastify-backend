package com.example.tastifybackend.api;


import com.example.tastifybackend.domain.category.CategoryService;
import com.example.tastifybackend.domain.category.dto.CategoryDto;
import com.example.tastifybackend.domain.category.dto.CategoryInput;
import com.example.tastifybackend.exception.MismatchedIdentifierException;
import com.example.tastifybackend.misc.Util;
import com.example.tastifybackend.validation.markers.OnPost;
import com.example.tastifybackend.validation.markers.OnPut;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    public static final String[] SEARCH = new String[]{"all (default)","value","contains"};

    @GetMapping
    public ResponseEntity<?> get(
            @RequestParam(name = "search", defaultValue = "all") String search,
            @RequestParam(name = "value", defaultValue = "") String value
    ){
        return switch (search.toLowerCase()){
            case "all" -> ResponseEntity.ok(categoryService.findAll());
            case "value" -> ResponseEntity.ok(categoryService.findByValue(value));
            case "contains" -> ResponseEntity.ok(categoryService.findByValueContains(value));
            default -> throw new IllegalArgumentException(
                String.format("InvalidArgument search type not supported, search: %s. Supported values are %s", search, Arrays.toString(SEARCH))
            );
        };
    }

    @PostMapping
    public ResponseEntity<CategoryDto> create(
            @Validated(OnPost.class) @RequestBody CategoryInput input
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.save(input));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(
            @PathVariable("id") String id
    ){
        return ResponseEntity.ok(
                categoryService.findById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(
            @PathVariable("id") String id,
            @Validated(OnPut.class) @RequestBody CategoryInput input
    ){
        if(input == null) throw new IllegalArgumentException("RequestBody CategoryInput input was null");
        if(Util.isNotMatchingId(id, input.getId())){
            throw new MismatchedIdentifierException(
                String.format("MismatchedIdentifierException, PathVariable id: %s does not match %s", id, input.getId())
            );
        }
        return ResponseEntity.ok(categoryService.save(input));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id){
        categoryService.delete(id);
        return ResponseEntity.accepted().build();
    }


}
