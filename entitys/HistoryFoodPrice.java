package entitys;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoryFoodPrice {

    private Food food;
    private Double price;
    private LocalDateTime date;


    public HistoryFoodPrice(Food foodId, Double price, LocalDateTime date) {
        this.food = foodId;
        this.price = price;
        this.date = date;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getStrDate(){
        return getDate().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("%s buy [%s] price:%.2f",
                getStrDate(),getFood().getName(),getPrice());

    }
}
