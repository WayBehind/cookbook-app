package sk.waybehind.cookbook_app.implementation.jdbc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.waybehind.cookbook_app.api.request.CreateRecipeRequest;
import sk.waybehind.cookbook_app.domain.Recipe;
import sk.waybehind.cookbook_app.implementation.jdbc.service.RecipeService;

import java.util.List;

@RestController
@RequestMapping("api/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipesByTitle(
            @RequestParam(required = false, defaultValue = "") String searchText
    ) {
        return ResponseEntity.ok(recipeService.getAllRecipesByTitle(searchText));
    }

    @GetMapping("{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable int id) {
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody CreateRecipeRequest recipeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.createRecipe(recipeRequest));
    }

    @PutMapping("{id}")
    public ResponseEntity<Recipe> updateRecipe(
            @PathVariable int id,
            @RequestBody CreateRecipeRequest recipeRequest) {
        return ResponseEntity.ok(recipeService.updateRecipe(id, recipeRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok().build();
    }

}
