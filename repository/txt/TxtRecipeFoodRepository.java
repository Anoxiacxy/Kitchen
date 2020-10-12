package repository.txt;

import entitys.FoodWithCount;
import repository.FoodRepository;
import repository.RecipeFoodRepository;
import utils.TxtDbFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TxtRecipeFoodRepository implements RecipeFoodRepository {
    // Recipe Id <=> FoodId
    final private static  String[] HEAD = new String[]{"RECIPE_ID","FOOD_ID","COUNT"};
    final private FoodRepository foodRep;

    public TxtRecipeFoodRepository(FoodRepository foodRep) {
        this.foodRep = foodRep;
    }

    private TxtDbFile get() throws IOException {
        return new TxtDbFile(TxtDbFile.folder + "/recipe_food.txt",HEAD);
    }

    @Override
    public int deleteByRecipeId(int recipeId) throws Exception {
        try(TxtDbFile txt = get()){
            return txt.removeBy(t -> t.get("RECIPE_ID").equals(recipeId + ""));
        }
    }
    @Override
    public void insert(int pId, Collection<FoodWithCount> items) throws Exception{
        try(TxtDbFile txt = get()){
            for(FoodWithCount item : items){
                txt.writeRecord(pId + "",item.getFood().getId() + "",item.getCount() + "");
            }
        }
    }
    @Override
    public void insert(int pId,FoodWithCount ... items) throws Exception{
        try(TxtDbFile txt = get()){
            for(FoodWithCount item : items){
                txt.writeRecord(pId + "",item.getFood().getId() + "",item.getCount() + "");
            }
        }
    }
    @Override
    public Collection<FoodWithCount> findFoodId(int recipeId) throws Exception{
        List<FoodWithCount> ret = new ArrayList<>();
        try(TxtDbFile txt = get()){
            for(Map<String,String> row : txt.getAll(t -> t.get("RECIPE_ID").equals(recipeId + ""))){
                int foodId = Integer.parseInt(row.get("FOOD_ID"));
                int foodCount = Integer.parseInt(row.get("COUNT"));
                ret.add(new FoodWithCount(foodRep.findFoodById(foodId),foodCount));
            }
        }
        return ret;
    }




}
