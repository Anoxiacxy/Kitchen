package repository;

import entitys.Recipe;

import java.util.List;

public interface RecipeRepository {
    Recipe findByName(String name) throws Exception;

    Recipe findByName(String name, boolean caseSensitive) throws Exception;

    List<Recipe> getAll() throws Exception;

    public Recipe findById(int id) throws Exception;
    public Recipe save(Recipe recipe) throws Exception;
}
