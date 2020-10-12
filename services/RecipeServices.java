package services;

import entitys.*;
import utils.DbHelper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RecipeServices {

    final DbHelper db;

    public RecipeServices(DbHelper db) {
        this.db = db;
    }

    public Food searchFood(String foodName) throws IOException {
        return db.getFoodRep().findFoodByName(foodName);
    }

    public void newFood(Food food) throws Exception {
        if(searchFood(food.getName())==null){
            db.getFoodRep().saveFood(food);
        }
    }
    public void newFood(String name, Food.FoodType type, int expDay) throws Exception {
        if(searchFood(name)==null){
            db.getFoodRep().saveFood(new Food(type, name, expDay));
        }
    }


    public void newRecipe(Recipe recipe) throws Exception {
        db.getRecipeRep().save(recipe);
    }

    public int queryCount(Food food) {
        try {
            return db.getStorageRep().load().getFoodCount(food);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public HistoryFoodPrice queryLastPrice(Food food) throws Exception {
        return db.getHistoryPriceRep().getLastPrice(food.getId());
    }

    public List<HistoryFoodPrice> queryPrice(Food food) throws Exception {
        return db.getHistoryPriceRep().getAll(food.getId());
    }

    public List<Food> getAllFood() throws IOException {
        return this.db.getFoodRep().getAll();
    }

    public List<Food> getFoodByType(Food.FoodType type) throws Exception {
        return this.db.getFoodRep().getFoodBytype(type);
    }

    public List<FoodWithCount> getAllStorage() throws Exception {
        return this.db.getStorageRep().load().getFoods();
    }

    public List<Recipe> getAllRecipe() throws Exception {
        return db.getRecipeRep().getAll();
    }

    public List<FoodWithCount> getLackFood(Recipe recipe) throws Exception {
        return getLackFood(recipe,1);
    }

    public List<FoodWithCount> getLackFood(Recipe recipe,int num) throws Exception {
        List<FoodWithCount> ret = new ArrayList<>();
        Storage storage = db.getStorageRep().load();
        recipe.getFoods().forEach(t -> {
            int have = storage.getFoodCount(t.getFood());
            if(have < t.getCount() * num) {
                ret.add(new FoodWithCount(t.getFood(),t.getCount() - have));
            }
        });
        return ret;
    }

    public Recipe searchRecipe(String name) throws Exception {
        return db.getRecipeRep().findByName(name);
    }

    public void withDraw(Recipe recipe, int num) throws Exception {
        Storage storage = db.getStorageRep().load();
        recipe.getFoods().forEach(t->{
            storage.addFood(t.getFood(), -num * t.getCount());
        });
        db.getStorageRep().save(storage);
    }

    public Food makeRecipe(Recipe recipe)throws Exception{
        Storage storage = db.getStorageRep().load();

        recipe.getFoods().forEach(t->{
            storage.addFood(t.getFood(), -t.getCount());
        });

        db.getStorageRep().save(storage);
        Food food = searchFood(recipe.getName());
        if(food == null){
            return db.getFoodRep().saveFood(new Food(Food.FoodType.other,recipe.getName(),1));
        }else{
            return food;
        }
    }

    public void addStorage(Food food, int count) throws Exception{
        Storage storage = db.getStorageRep().load();
        storage.addFood(food, count);
        db.getStorageRep().save(storage);
    }

    public void buy(Food food, int count, double price) throws Exception {
        addStorage(food,count);
        db.getHistoryPriceRep().save(new HistoryFoodPrice(food, price, LocalDateTime.now()));
    }


}
