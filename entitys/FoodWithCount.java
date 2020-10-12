package entitys;

public class FoodWithCount {
    final Food food;
    Integer count;


    public FoodWithCount(Food food, Integer count) {
        this.food = food;
        this.count = count;
    }

    public Food getFood() {
        return food;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


    @Override
    public String toString() {
        return String.format("%s x %d",food.getName(),count);
    }
}
