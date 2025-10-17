package sk.waybehind.cookbook_app.implementation.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import sk.waybehind.cookbook_app.domain.Recipe;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RecipeRowMapper implements RowMapper<Recipe> {
    @Override
    public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Recipe recipe = new Recipe();

        recipe.setId(rs.getInt("id"));
        recipe.setTitle(rs.getString("title"));
        recipe.setDescription(rs.getString("description"));

        final int prepTimeMinutes = rs.getInt("prep_time_minutes");
        recipe.setPrepTimeMinutes(rs.wasNull() ? null : prepTimeMinutes);

        recipe.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        recipe.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

        return recipe;
    }
}
