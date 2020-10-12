package repository.txt;

import entitys.Food;
import repository.FoodRepository;
import repository.SequenceRepository;
import utils.CmdTools;
import utils.TxtDbFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TxtFoodRepository implements FoodRepository{

    private static final String[] HEAD = new String[]{"ID", "TYPE", "NAME", "EXP"};
    final private SequenceRepository seq;

    private TxtDbFile get() throws IOException {
        return new TxtDbFile(TxtDbFile.folder + "/Food.txt", HEAD);
    }
    public TxtFoodRepository(SequenceRepository seq) {
        this.seq = seq;
    }

    @Override
    public List<Food> getAll() throws IOException {
        try(TxtDbFile db = get()) {
            List<Food> ret = new ArrayList<>();
            db.getRows().forEach(t->{
                ret.add(mapToFood(t));
            });
            return ret;
        }
    }

    @Override
    public List<Food> getFoodBytype(Food.FoodType type) throws Exception {
        List<Food> ret = new ArrayList<>();
        for(Food f: getAll()){
            if(f.getType() == type){
                ret.add(f);
            }
        }
        return ret;
    }

    @Override
    public Food findFoodByName(String name) throws IOException {
        return findFoodByName(name, false);
    }

    @Override
    public Food findFoodByName(String name, boolean caseSensitive) throws IOException {
        try (TxtDbFile txt = get()) {

            Map<String, String> row = txt.getFirst(t -> {
                if (caseSensitive) {
                    return t.get("NAME").equals(name);
                } else {
                    return t.get("NAME").equalsIgnoreCase(name);
                }
            });
            return mapToFood(row);
        }
    }

    private Food mapToFood(Map<String, String> entity) {
        if (entity == null || entity.isEmpty()) {
            return null;
        }
        return new Food(Integer.parseInt(entity.get("ID")),
                Food.FoodType.valueOf(entity.get("TYPE")),
                entity.get("NAME"),
                Integer.parseInt(entity.get("EXP")));

    }

    @Override
    public Food findFoodById(int id) throws Exception {
        try (TxtDbFile txt = get()) {
            Map<String, String> row = txt.getFirst(t -> t.get("ID").equals(id + ""));
            return mapToFood(row);
        }
    }

    @Override
    public Food saveFood(Food food) throws Exception {
        try (TxtDbFile txt = get()) {
            if (food.getId() == null) {
                food.setId(seq.getNext("FoodSeq"));
                txt.writeRecord(food.getId() + "", food.getType().name(), food.getName(), food.getExpirationDay() + "");
            } else {
                Map<String, String> row = txt.getFirst(t -> t.get("ID").equals(food.getId() + ""));
                if (row != null) {
                    row.put("TYPE", food.getType().name());
                    row.put("NAME", food.getName());
                    row.put("EXP", food.getExpirationDay() + "");
                } else {
                    throw new IllegalArgumentException(String.format("id[%d]不存在", food.getId()));
                }
            }
        }
        return food;
    }


}
