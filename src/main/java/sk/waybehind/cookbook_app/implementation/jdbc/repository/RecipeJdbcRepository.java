package sk.waybehind.cookbook_app.implementation.jdbc.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sk.waybehind.cookbook_app.domain.Recipe;
import sk.waybehind.cookbook_app.implementation.jdbc.mapper.RecipeRowMapper;

import java.util.List;

@Repository
public class RecipeJdbcRepository {
    private static final Logger logger = LoggerFactory.getLogger(RecipeJdbcRepository.class);

    private static final String GET_ALL = "SELECT * FROM recipe";

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
}
