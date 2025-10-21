package sk.waybehind.cookbook_app.implementation.jdbc.service;

import org.springframework.stereotype.Service;
import sk.waybehind.cookbook_app.api.request.CreateRecipeRequest;
import sk.waybehind.cookbook_app.domain.Recipe;
import sk.waybehind.cookbook_app.implementation.jdbc.repository.RecipeJdbcRepository;

import java.util.List;

@Service
public class RecipeService {
    private final RecipeJdbcRepository recipeJdbcRepository;

    public RecipeService(RecipeJdbcRepository recipeJdbcRepository) {
        this.recipeJdbcRepository = recipeJdbcRepository;
    }

    public List<Recipe> getAllRecipes() {
        return recipeJdbcRepository.getAllRecipes();
    }

    public Recipe getRecipeById(int id) {
        return recipeJdbcRepository.getRecipeById(id);
    }

    public Recipe createRecipe(CreateRecipeRequest recipeRequest) {
        return recipeJdbcRepository.createRecipe(
                recipeRequest.getTitle(),
                recipeRequest.getDescription(),
                recipeRequest.getPrepTimeMinutes()
        );

    }
}
