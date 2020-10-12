package controller;

import entitys.Food;
import entitys.HistoryFoodPrice;
import services.RecipeServices;
import utils.CmdTools;

import java.io.IOException;
import java.util.List;

public class FoodUi {

    RecipeServices services;

    public FoodUi(RecipeServices service){
        this.services = service;
    }


    private void queryFood() throws IOException {
        String foodName = CmdTools.inputString("input food name:");

        Food food = this.services.searchFood(foodName);
        if(food == null){
            CmdTools.errL(foodName + " not exist");
            return;
        }
        int count = this.services.queryCount(food);
        CmdTools.info("\n%s have %d\n",food,count);
    }

    public void addFood() throws IOException {
        String foodName = CmdTools.inputString("input food name:");
        String foodType = CmdTools.inputString("Input Food Type:",Food.FoodType.values());
        int expDay = CmdTools.rangeChoice("Input exp day[1,3650]:",1,3650);

        if(services.searchFood(foodName) != null){
            CmdTools.err("%s already exists",foodName);
        }else{
            try {
                services.newFood(foodName, Food.FoodType.valueOf(foodType),expDay);
            } catch (Exception e) {
                CmdTools.err("add food [%s] error : %s\n",foodName,e.getMessage());
            }
        }
    }
    private void listFood() throws Exception {
        this.services.getAllFood().forEach(t->{
            CmdTools.info("%s \n",t);
        });
    }

    private void sortFoodByName(List<Food> foods, boolean ascend) {

        for (int i = 0; i < foods.size(); i++) {
            for (int j = 0; j < foods.size() - i - 1; j++) {
                if ((!ascend && foods.get(j).getName().compareTo(foods.get(j + 1).getName()) < 0) ||
                        (ascend && foods.get(j).getName().compareTo(foods.get(j + 1).getName()) > 0)
                ) {
                    // swap
                    Food tmp = foods.get(j);
                    foods.set(j, foods.get(j + 1));
                    foods.set(j + 1, tmp);
                }
            }
        }
    }

    private void listByType() throws Exception {
        String foodType = CmdTools.inputString("Input Food Type:",Food.FoodType.values());
        List<Food> foods = this.services.getFoodByType(Food.FoodType.valueOf(foodType));
        if(foods == null || foods.isEmpty()){
            CmdTools.err("no food found\n");
            return;
        }
        final boolean ascend = CmdTools.yesNoChoice("ascend by name");
        sortFoodByName(foods,ascend);

        foods.forEach(f->{
            CmdTools.info("%s\n",f);
        });

    }


    public void run() throws Exception {
        while (true){
            CmdTools.showMenu("Food",
                    "0. List All",
                    "1. List By Type",
                    "2. Query food",
                    "3. Add food",
                    "4. Return");
            switch (CmdTools.rangeChoice(0,4)){
                case 0:
                    listFood();
                    break;
                case 1:
                    listByType();
                    break;
                case 2:
                    queryFood();
                    break;
                case 3:
                    addFood();
                    break;
                case 4:
                    System.out.println("return to parent");
                    return;
            }
        }
    }





}
