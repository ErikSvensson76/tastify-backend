package com.example.tastifybackend.api;

import com.example.tastifybackend.domain.recipe_ingredient.RecipeIngredientService;
import com.example.tastifybackend.domain.recipe_ingredient.dto.RecipeIngredientDto;
import com.example.tastifybackend.domain.recipe_ingredient.dto.RecipeIngredientInput;
import com.example.tastifybackend.exception.MismatchedIdentifierException;
import com.example.tastifybackend.misc.Util;
import com.example.tastifybackend.validation.markers.OnPut;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ingredients")
public class RecipeIngredientController {

  private final RecipeIngredientService recipeIngredientService;

  public static final String[] SEARCH = new String[]{"all (default)","ingredients","name","recipe"};

  @GetMapping
  public ResponseEntity<?> get(
      @RequestParam(name = "search", defaultValue = "all") String search,
      @RequestParam(name = "value", defaultValue = "") String value
  ){
    return switch (search.toLowerCase()){
      case "all" -> ResponseEntity.ok(recipeIngredientService.findAll());
      case "ingredients" -> ResponseEntity.ok(recipeIngredientService.getAllDistinctIngredients());
      case "name" -> ResponseEntity.ok(recipeIngredientService.getByIngredientName(value));
      case "recipe" -> ResponseEntity.ok(recipeIngredientService.getByRecipeId(value));
      default -> throw new IllegalArgumentException(
          String.format("InvalidArgument search type not supported, search: %s. Supported values are %s", search, Arrays.toString(SEARCH))
      );
    };
  }

  @GetMapping("/{id}")
  public ResponseEntity<RecipeIngredientDto> getById(@PathVariable("id") String id){
    return ResponseEntity.ok(recipeIngredientService.findById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<RecipeIngredientDto> update(
      @PathVariable("id") String id,
      @Validated(OnPut.class) @RequestBody RecipeIngredientInput input
      ){
    if(input == null) throw new IllegalArgumentException("RequestBody RecipeIngredientInput input was null");
    if(Util.isNotMatchingId(id, input.getId())){
      throw new MismatchedIdentifierException(
          String.format("MismatchedIdentifierException, PathVariable id : %s does not match %s", id, input.getId())
      );
    }
    return ResponseEntity.ok(recipeIngredientService.save(input));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") String id){
    recipeIngredientService.delete(id);
    return ResponseEntity.accepted().build();
  }

}
