package entitys;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class Storage {



    private List<FoodWithCount> foods;

    public Storage(List<FoodWithCount> foods) {
        this.foods = foods;
    }


    public List<FoodWithCount> getFoods() {
        foods.sort((o1, o2) -> o2.getCount().compareTo(o1.getCount()));
        return foods;
    }

    public void setFoods(List<FoodWithCount> foods) {
        this.foods = foods;
    }

    public void addFood(Food food, int count){
        for(FoodWithCount fc : foods) {
            if(fc.getFood().equals(food) && fc.getCount()+count >= 0){
                fc.setCount(fc.getCount() + count);
                return ;
            }
        }
        if(count > 0) {
            foods.add(new FoodWithCount(food,count));
        }
    }

    public int getFoodCount(Food food){
        for(FoodWithCount fc : foods) {
            if(fc.getFood().equals(food)){
                return fc.getCount();
            }
        }
        return 0;
    }
}
