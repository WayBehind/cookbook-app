package sk.waybehind.cookbook_app.api.request;

public class CreateRecipeRequest {
    private String title;
    private String description;
    private Integer prepTimeMinutes;

    public CreateRecipeRequest() {
    }

    public CreateRecipeRequest(String title, String description, Integer prepTimeMinutes) {
        this.title = title;
        this.description = description;
        this.prepTimeMinutes = prepTimeMinutes;
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
}
