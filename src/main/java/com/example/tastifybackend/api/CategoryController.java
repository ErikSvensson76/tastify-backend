package com.example.tastifybackend.api;


import com.example.tastifybackend.domain.category.CategoryService;
import com.example.tastifybackend.domain.category.dto.CategoryDto;
import com.example.tastifybackend.domain.category.dto.CategoryInput;
import com.example.tastifybackend.validation.markers.OnPost;
import com.example.tastifybackend.validation.markers.OnPut;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> get(
            @RequestParam(name = "search", defaultValue = "all") String search,
            @RequestParam(name = "value", required = false) String value
    ){
        return switch (search){
            case "all" -> ResponseEntity.ok(categoryService.findAll());
            case "value" -> ResponseEntity.ok(categoryService.findByValue(value));
            case "contains" -> ResponseEntity.ok(categoryService.findByValueContains(value));
            default -> throw new IllegalArgumentException("InvalidArgument search type not supported, search: " + search);
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
        if(input.getId() == null){
            input.setId(id);
        }
        return ResponseEntity.ok(categoryService.save(input));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id){
        categoryService.delete(id);
        return ResponseEntity.accepted().build();
    }


}
