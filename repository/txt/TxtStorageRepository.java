package repository.txt;

import entitys.FoodWithCount;
import entitys.Storage;
import repository.FoodRepository;
import repository.StorageRepository;
import utils.TxtDbFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TxtStorageRepository implements StorageRepository {


    final private static  String[] HEAD = new String[]{"FOOD_ID","COUNT"};
    final private FoodRepository foodRep;

    public TxtStorageRepository(FoodRepository foodRep) {
        this.foodRep = foodRep;
    }


    private TxtDbFile get() throws IOException {
        return new TxtDbFile(TxtDbFile.folder + "/Storage.txt",HEAD);
    }


    @Override
    public void save(Storage storage) throws Exception {
        try (TxtDbFile txt = get()){
            txt.clear();
            storage.getFoods().forEach(foodWithCount -> {
                txt.writeRecord(foodWithCount.getFood().getId()+"",foodWithCount.getCount() + "");
            });
        }
    }

    @Override
    public Storage load() throws Exception{
        List<FoodWithCount> data = new ArrayList<>();
        try (TxtDbFile txt = get()){
            for(Map<String,String> map : txt.getRows()){
                int fid = Integer.parseInt(map.get("FOOD_ID"));
                int count = Integer.parseInt(map.get("COUNT"));
                data.add(new FoodWithCount(foodRep.findFoodById(fid),count));
            }
        }
        return new Storage(data);
    }



}
