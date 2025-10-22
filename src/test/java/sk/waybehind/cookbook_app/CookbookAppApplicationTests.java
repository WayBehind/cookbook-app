package sk.waybehind.cookbook_app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sk.waybehind.cookbook_app.api.exception.RecipeNotFoundException;
import sk.waybehind.cookbook_app.api.request.CreateRecipeRequest;
import sk.waybehind.cookbook_app.domain.Recipe;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CookbookAppApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void getAll() {
        final ResponseEntity<List<Recipe>> responseEntity = testRestTemplate.exchange(
                "/api/recipes",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Recipe>>() {
                }
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody().size() >= 2);

    }

    @Test
    void createRecipe() {
        final CreateRecipeRequest request = new CreateRecipeRequest(
                "Test Recipe",
                "Test Description",
                10
        );

        final ResponseEntity<Recipe> response = testRestTemplate.postForEntity(
                "/api/recipes",
                request,
                Recipe.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(request.getTitle(), response.getBody().getTitle());
        assertEquals(request.getDescription(), response.getBody().getDescription());
        assertEquals(request.getPrepTimeMinutes(), response.getBody().getPrepTimeMinutes());
    }

    @Test
    void getRecipeById() {
        final CreateRecipeRequest request = new CreateRecipeRequest(
                "Test Recipe",
                "Test Description",
                15
        );

        final ResponseEntity<Recipe> createResponse = testRestTemplate.postForEntity(
                "/api/recipes",
                request,
                Recipe.class
        );

        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        final int newRecipeId = createResponse.getBody().getId();

        final ResponseEntity<Recipe> getResponse = testRestTemplate.getForEntity(
                "/api/recipes/" + newRecipeId,
                Recipe.class
        );

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals(request.getTitle(), createResponse.getBody().getTitle());
        assertEquals(request.getDescription(), createResponse.getBody().getDescription());
        assertEquals(request.getPrepTimeMinutes(), createResponse.getBody().getPrepTimeMinutes());
    }

    @Test
    void getNonexistentRecipe() {
        final ResponseEntity<RecipeNotFoundException> response = testRestTemplate.getForEntity(
                "/api/recipes/999",
                RecipeNotFoundException.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateNonexistentRecipe() {
        final CreateRecipeRequest request = new CreateRecipeRequest(
                "Updated Recipe",
                "Updated Description",
                45
        );

        final ResponseEntity<RecipeNotFoundException> response = testRestTemplate.exchange(
                "/api/recipes/999",
                HttpMethod.POST,
                new HttpEntity<>(request),
                RecipeNotFoundException.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateRecipe() {
        final CreateRecipeRequest request = new CreateRecipeRequest(
                "Updated Recipe",
                "Updated Description",
                45
        );

        final ResponseEntity<Recipe> response = testRestTemplate.exchange(
                "/api/recipes/1",
                HttpMethod.POST,
                new HttpEntity<>(request),
                Recipe.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(request.getTitle(), response.getBody().getTitle());
        assertEquals(request.getDescription(), response.getBody().getDescription());
        assertEquals(request.getPrepTimeMinutes(), response.getBody().getPrepTimeMinutes());


    }
}
