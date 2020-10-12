package repository.txt;

import entitys.Food;
import entitys.HistoryFoodPrice;
import repository.FoodRepository;
import repository.HistoryFoodPriceRepository;
import utils.TxtDbFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class TxtHistoryFoodPriceRepository implements HistoryFoodPriceRepository {


    final private static  String[] HEAD = new String[]{"FOOD_ID","PRICE","DATE"};
    private final FoodRepository foodRep;

    public TxtHistoryFoodPriceRepository(FoodRepository foodRep) {
        this.foodRep = foodRep;
    }


    private TxtDbFile get() throws IOException {
        return new TxtDbFile(TxtDbFile.folder + "/FoodPriceHistory.txt",HEAD);
    }

    @Override
    public List<HistoryFoodPrice> getAll(int foodID) throws Exception {
        List<HistoryFoodPrice> ret = new ArrayList<>();

        Food food = this.foodRep.findFoodById(foodID);
        if(food == null){
            return ret;
        }

        try(TxtDbFile txt = get()) {
            for(Map<String,String> row : txt.getAll(t -> t.get("FOOD_ID").equals(foodID+""))) {
                int fId = Integer.parseInt(row.get("FOOD_ID"));
                double price = Double.parseDouble(row.get("PRICE"));
                LocalDateTime date = LocalDateTime.parse(row.get("DATE"),DateTimeFormatter.ISO_DATE_TIME);
                ret.add(new HistoryFoodPrice(food,price,date));
            }
        }
        // sort buy date
        ret.sort(Comparator.comparing(HistoryFoodPrice::getDate));
        return ret;
    }
    @Override
    public HistoryFoodPrice getLastPrice(int foodId) throws Exception {
        List<HistoryFoodPrice> data = getAll(foodId);
        return data.isEmpty() ? null : data.get(data.size()-1);
    }

    @Override
    public void save(HistoryFoodPrice price) throws Exception {
        try(TxtDbFile txt = get()){
            txt.writeRecord(price.getFood().getId()+"",price.getPrice()+"",price.getStrDate());
        }
     }
}
