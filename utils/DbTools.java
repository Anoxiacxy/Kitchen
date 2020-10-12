package utils;

import entitys.Food;
import repository.FoodRepository;
import repository.SequenceRepository;
import repository.txt.TxtFoodRepository;
import repository.txt.TxtSeqRepository;

import java.io.File;

public class DbTools {



    public static void initTxtDb() throws Exception {

        SequenceRepository seq = new TxtSeqRepository();
        FoodRepository foodRep = new TxtFoodRepository(seq);

        //clean
        File dir = new File(TxtDbFile.folder);
        File[] subs = dir.listFiles();
        if( subs != null) {
            for(File sub: subs){
                sub.delete();
            }
        }
        // init food
        // meat
        Food pork = new Food(Food.FoodType.meat,"pork",2);
        Food beef = new Food(Food.FoodType.meat,"beef",3);
        Food chicken =  new Food(Food.FoodType.meat,"chicken",5);
        Food fish = new Food(Food.FoodType.meat,"fish",15);
        // vegetable
        Food broccoli = new Food(Food.FoodType.vegetable,"broccoli",7);
        Food cabbage = new Food(Food.FoodType.vegetable,"cabbage",8);
        Food potato = new Food(Food.FoodType.vegetable,"potato",30);
        // condiment
        Food salt = new Food(Food.FoodType.condiment,"salt",365);
        Food sugar = new Food(Food.FoodType.condiment,"sugar",366);
        Food pepper = new Food(Food.FoodType.condiment,"pepper",180);
        // other
        Food water = new Food(Food.FoodType.other, "water",30);
        Food oil = new Food(Food.FoodType.other, "oil",90);

        // init storage
        // init recipe

        foodRep.saveFood(oil);

    }

    public static void main(String[] argv) throws Exception {
        initTxtDb();
    }
}
