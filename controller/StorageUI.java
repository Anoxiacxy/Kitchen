package controller;

import entitys.Food;
import entitys.FoodWithCount;
import entitys.HistoryFoodPrice;
import services.RecipeServices;
import utils.CmdTools;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StorageUI {

    RecipeServices services;

    public StorageUI(RecipeServices service){
        this.services = service;
    }


    private void buyFood() throws Exception {
        String name = CmdTools.inputString("input food name: ");
        Food food = services.searchFood(name);
        if(food == null){
            CmdTools.err("%s not exits, please add it first! \n",name);
            return;
        }
        HistoryFoodPrice lastBuyRecord = services.queryLastPrice(food);
        if(lastBuyRecord != null) {
            CmdTools.info("%s you buy [%s] with price [%.3f]\n",
                    lastBuyRecord.getStrDate(),
                    lastBuyRecord.getFood().getName(),
                    lastBuyRecord.getPrice()
            );
        }

        int count = CmdTools.rangeChoice("input number: ",1,9999);
        double price = CmdTools.inputDouble("input price: ");
        this.services.buy(food,count,price);
    }


    private void showAllFood() throws Exception {
        List<FoodWithCount> all = services.getAllStorage();
        if(all.isEmpty()){
            CmdTools.info("is empty !\n");
        }else {
            all.forEach(c -> {
                CmdTools.info("[%-10s] left %05d\n",c.getFood().getName(),c.getCount());
            });
        }
    }

    private void sortHistoryPrice(List<HistoryFoodPrice> price, boolean ascend) {
        if (price == null || price.size() < 2) {
            return;
        }
        for (int i = 0; i < price.size(); i++) {
            for (int j = 0; j < price.size() - i - 1; j++) {
                if ((!ascend && price.get(j).getDate().compareTo(price.get(j + 1).getDate()) < 0) ||
                        (ascend && price.get(j).getDate().compareTo(price.get(j + 1).getDate()) > 0)
                ) {
                    // swap
                    HistoryFoodPrice tmp = price.get(j);
                    price.set(j, price.get(j + 1));
                    price.set(j + 1, tmp);
                }
            }
        }
    }

    private void priceHistory() throws Exception {
        String name = CmdTools.inputString("input food name: ");
        Food food = services.searchFood(name);
        if(food == null){
            CmdTools.err("%s not exits \n",name);
            return;
        }

        final List<HistoryFoodPrice> price = services.queryPrice(food);
        if(price == null || price.isEmpty()) {
            CmdTools.info("no price info\n");
            return;
        }

        final boolean ascend = CmdTools.yesNoChoice("date ascend ");
        sortHistoryPrice(price,ascend);
        price.forEach(t->{
            CmdTools.info("%s\n",t);
        });
    }




    public void run() throws Exception {
        while (true){
            CmdTools.showMenu("Storage"
                    ,"1. Show all",
                    "2. Buy food",
                    "3. Price History",
                    "4. Back");
            switch (CmdTools.rangeChoice(1,4)){

                case 1:
                    showAllFood();
                    break;
                case 2:
                    buyFood();
                    break;
                case 3:
                    priceHistory();
                    break;
                case 4:
                    return;
            }

        }
    }

}
