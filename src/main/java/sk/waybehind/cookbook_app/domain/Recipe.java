package sk.waybehind.cookbook_app.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Recipe {
    private Integer id;
    private String title;
    private String description;
    private Integer prepTimeMinutes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Recipe() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrepTimeMinutes() {
        return prepTimeMinutes;
    }

    public void setPrepTimeMinutes(Integer prepTimeMinutes) {
        this.prepTimeMinutes = prepTimeMinutes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(id, recipe.id) && Objects.equals(title, recipe.title) && Objects.equals(description, recipe.description) && Objects.equals(prepTimeMinutes, recipe.prepTimeMinutes) && Objects.equals(createdAt, recipe.createdAt) && Objects.equals(updatedAt, recipe.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, prepTimeMinutes, createdAt, updatedAt);
    }
}
