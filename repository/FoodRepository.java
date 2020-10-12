package repository;

import entitys.Food;

import java.io.IOException;
import java.util.List;

public interface FoodRepository {

    List<Food> getAll() throws IOException;

    List<Food> getFoodBytype(Food.FoodType type) throws Exception;

    Food findFoodByName(String name) throws IOException;

    Food findFoodByName(String name, boolean caseSensitive) throws IOException;

    public Food findFoodById(int id) throws Exception;

    public Food saveFood(Food food) throws Exception;



}
