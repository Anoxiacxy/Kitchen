package controller;

import entitys.Food;
import entitys.FoodWithCount;
import entitys.Recipe;
import services.RecipeServices;
import utils.CmdTools;

import java.util.ArrayList;
import java.util.List;

public class RecipeUI {

    final RecipeServices services;

    public RecipeUI(RecipeServices services) {
        this.services = services;
    }

    private void listALl() throws Exception {
        List<Recipe> all = this.services.getAllRecipe();
        if(all.isEmpty()) {
            CmdTools.info("no recipe !!\n");
        }else {
            all.forEach(t -> {
                CmdTools.info("%s \n", t);
            });
        }

    }

    private void queryRecipe() throws Exception {

        String name = CmdTools.inputString("input recipe name: ");
        Recipe recipe = this.services.searchRecipe(name);
        if (recipe == null) {
            CmdTools.err("%s not exits \n", name);
        } else {
            CmdTools.info("find %s\n", recipe);
        }

    }

    private void addRecipe() throws Exception {
        String name = CmdTools.inputString("input recipe name: ");
        Recipe recipe = this.services.searchRecipe(name);
        if (recipe != null) {
            CmdTools.err("%s alread exists\n", name);
            return;
        }
        List<FoodWithCount> foods = new ArrayList<>();
        CmdTools.info("add food to [%s] \n",name);
        while (true) {
            String foodName = CmdTools.inputString("input food name: ");
            if (foodName.equalsIgnoreCase("end")) {
                break;
            }
            Food foodAdd = this.services.searchFood(foodName);
            if (foodAdd == null) {
                CmdTools.err("%s not exist \n", foodName);
                if(CmdTools.yesNoChoice("continue add recipe")){
                    continue;
                }else{
                    // go back to menu
                    return;
                }
            }
            boolean alreadyAdd = false;
            for (FoodWithCount f : foods) {
                if (f.getFood().equals(foodAdd)) {
                    alreadyAdd = true;
                    break;
                }
            }
            if (alreadyAdd) {
                CmdTools.err("%d already add\n");
                continue;
            }
            int count = CmdTools.rangeChoice("input food number: ", 1, 100);
            foods.add(new FoodWithCount(foodAdd, count));
            if (!CmdTools.yesNoChoice("continue add food ")) {
                break;
            }
        }
        if (foods.isEmpty()) {
            CmdTools.err("no food add!\n");
        } else {
            services.newRecipe(new Recipe(name,foods));
        }
    }

    private void checkRecipe() throws Exception {
        for(Recipe recipe : this.services.getAllRecipe()){
            List<FoodWithCount> need = this.services.getLackFood(recipe);
            if(!need.isEmpty()){
                need.forEach(n -> {
                    CmdTools.err("%s need %s x %d\n",recipe.getName(),n.getFood().getName(),n.getCount());
                });
            }else{
                CmdTools.info("%s can make\n",recipe.getName());
            }
        }

    }

    private void make() throws Exception {
        String name = CmdTools.inputString("input recipe name: ");
        Recipe recipe = this.services.searchRecipe(name);
        if (recipe == null) {
            CmdTools.err("%s not exists\n", name);
            return;
        }
        List<FoodWithCount> food = this.services.getLackFood(recipe);
        if(!food.isEmpty()){
            CmdTools.err("Food not enough!\n");
            return;
        }
        Food newFood = this.services.makeRecipe(recipe);
        CmdTools.info("you make a [%s]\n",recipe.getName());

        if(CmdTools.yesNoChoice("add it to storage ")){
            this.services.addStorage(newFood,1);
        }
    }


    private void withDraw() throws Exception {
        String name = CmdTools.inputString("input withdarw recipe name: ");
        Recipe recipe = this.services.searchRecipe(name);
        if (recipe == null) {
            CmdTools.err("%s not exists\n", name);
            return;
        }
        int num = CmdTools.rangeChoice("how many ",1,100);
        List<FoodWithCount> food = this.services.getLackFood(recipe,num);
        if(!food.isEmpty()){
            food.forEach(t->{
                CmdTools.err("[%s] not enough!\n",t.getFood().getName());
            });
            return;
        }
        this.services.withDraw(recipe,num);
        CmdTools.info("%d x [%s] withdraw\n",num, name);
    }

    public void run() throws Exception {

        while (true) {
            CmdTools.showMenu("Recipe",
                    "0. List All",
                    "1. Query Recipe",
                    "2. Add Recipe",
                    "3. Check Recipe",
                    "4. Make From Recipe",
                    "5. Withdraw",
                    "6. Back"
            );
            switch (CmdTools.rangeChoice(0, 6)) {
                case 0:
                    listALl();
                    break;
                case 1:
                    queryRecipe();
                    break;
                case 2:
                    addRecipe();
                    break;
                case 3:
                    checkRecipe();
                    break;
                case 4:
                    make();
                    break;
                case 5:
                    withDraw();
                    break;
                case 6:
                    return;
            }
        }
    }
}
