package entitys;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private Integer id;
    private String name;
    // food and count
    private List<FoodWithCount> foods;

    public Recipe(Integer id, String name, List<FoodWithCount> foods) {
        this.id = id;
        this.name = name;
        this.foods = foods;
    }
    public Recipe(String name, List<FoodWithCount> foods) {
        this(null, name, foods);

    }
    public Recipe(Integer id, String name) {
        this(id, name, new ArrayList<>());
    }

    public Recipe(String name) {
        this(null, name);
    }



    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<FoodWithCount> getFoods() {
        return foods;
    }

    public void setFoods(List<FoodWithCount> foods) {
        this.foods = foods;
    }

    @Override
    public String toString() {

        return String.format("Recipe[%s]: %s",name,foods);
    }
}
