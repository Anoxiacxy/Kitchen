package repository;

import entitys.FoodWithCount;

import java.util.Collection;

public interface RecipeFoodRepository {
    int deleteByRecipeId(int recipeId) throws Exception;

    void insert(int pId, Collection<FoodWithCount> items) throws Exception;

    void insert(int pId, FoodWithCount... items) throws Exception;

    Collection<FoodWithCount> findFoodId(int recipeId) throws Exception;
}
