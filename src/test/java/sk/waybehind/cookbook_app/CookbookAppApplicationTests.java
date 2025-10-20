package sk.waybehind.cookbook_app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
