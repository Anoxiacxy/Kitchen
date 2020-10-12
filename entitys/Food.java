package entitys;

import java.util.Objects;

public class Food {


    public enum FoodType {
        vegetable, meat, condiment, other
    }

    private Integer id;
    private String name;
    private FoodType type;
    private int expirationDay;

    public Food(Integer id , FoodType type,String name,int expirationDay) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.expirationDay = expirationDay;
    }
    public Food(FoodType type,String name, int expiration){
        this(null,type,name,expiration);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FoodType getType() {
        return type;
    }

    public void setType(FoodType type) {
        this.type = type;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getExpirationDay() {
        return expirationDay;
    }

    public void setExpirationDay(int expirationDay) {
        this.expirationDay = expirationDay;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return id.equals(food.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
