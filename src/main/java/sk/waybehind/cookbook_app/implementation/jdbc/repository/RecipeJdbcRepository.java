package sk.waybehind.cookbook_app.implementation.jdbc.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sk.waybehind.cookbook_app.api.exception.InternalErrorException;
import sk.waybehind.cookbook_app.api.exception.RecipeNotFoundException;
import sk.waybehind.cookbook_app.domain.Recipe;
import sk.waybehind.cookbook_app.implementation.jdbc.mapper.RecipeRowMapper;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class RecipeJdbcRepository {
    private static final Logger logger = LoggerFactory.getLogger(RecipeJdbcRepository.class);

    private static final String GET_ALL = "SELECT * FROM recipe";
    private static final String GET_BY_TITLE = "SELECT * FROM recipe WHERE LOWER(title) LIKE LOWER(?) ORDER BY created_at DESC";
    private static final String GET_BY_ID = "SELECT * FROM recipe WHERE id = ?";
    private static final String CREATE = "INSERT INTO recipe (title, description, prep_time_minutes) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE recipe SET title = ?, description = ?, prep_time_minutes = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
    private static final String DELETE = "DELETE FROM recipe WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final RecipeRowMapper recipeRowMapper;

    public RecipeJdbcRepository(JdbcTemplate jdbcTemplate, RecipeRowMapper recipeRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.recipeRowMapper = recipeRowMapper;
    }

    public List<Recipe> getAllRecipes() {
        logger.debug("Getting all recipes");
        return jdbcTemplate.query(GET_ALL, recipeRowMapper);

    }

    public List<Recipe> getRecipeByTitle(String searchText) {
        logger.debug("Getting recipes with title: {}", searchText);
        return jdbcTemplate.query(GET_BY_TITLE, recipeRowMapper, "%" + searchText + "%");

    }

    public Recipe getRecipeById(int id) {
        logger.debug("Getting recipe with id: {}", id);

        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, recipeRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Recipe with id {} not foud", id);
            throw new RecipeNotFoundException("Recipe with id: " + id + " was not found");
        }
    }

    public Recipe createRecipe(String title, String description, Integer prepTimeMinutes) {
        logger.debug("Creating new recipe with title: {}", title);

        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            if (prepTimeMinutes == null) {
                preparedStatement.setObject(3, null);
            } else {
                preparedStatement.setInt(3, prepTimeMinutes);
            }

            return preparedStatement;
        }, keyHolder);

        if (keyHolder.getKeys() == null) {
            logger.error("KeyHolder was null while creating new recipe");
            throw new InternalErrorException("Error while creating recipe");
        }

        final Number id = (Number) keyHolder.getKeys().get("ID");
        return this.getRecipeById(id.intValue());
    }

    public void updateRecipe(String title, String description, Integer prepTimeMinutes, int id) {
        logger.debug("Updating recipe with id: {}", id);

        getRecipeById(id);

        jdbcTemplate.update(UPDATE, title, description, prepTimeMinutes, id);
    }

    public void deleteRecipe(int id) {
        logger.debug("Deleting recipe with id: {}", id);

        getRecipeById(id);

        jdbcTemplate.update(DELETE, id);
    }
}
