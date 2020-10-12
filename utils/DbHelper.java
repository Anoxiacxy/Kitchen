package utils;


import repository.*;
import repository.txt.*;

public class DbHelper {

    private static SequenceRepository seq = new TxtSeqRepository();

    // food
    private static FoodRepository foodRep = new TxtFoodRepository(seq);

    // history price
    private static HistoryFoodPriceRepository historyPriceRep = new TxtHistoryFoodPriceRepository(foodRep);

    // recipe
    private static RecipeFoodRepository ref = new TxtRecipeFoodRepository(foodRep);
    private static RecipeRepository recipeRep = new TxtRecipeRepository(seq, foodRep, ref);

    // storage
    private static StorageRepository storageRep = new TxtStorageRepository(foodRep);


    public DbHelper() {
        //TODO use Mysql
    }

    public FoodRepository getFoodRep() {
        return foodRep;
    }

    public RecipeRepository getRecipeRep() {
        return recipeRep;
    }

    public HistoryFoodPriceRepository getHistoryPriceRep() {
        return historyPriceRep;
    }

    public StorageRepository getStorageRep() {
        return storageRep;
    }
}
