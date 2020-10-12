package controller;

import services.RecipeServices;
import utils.CmdTools;
import utils.DbHelper;

import java.io.IOException;

public class CommandUI {

    final RecipeServices services;

    public CommandUI(RecipeServices services) {
        this.services = services;
    }

    private void doRun() throws Exception {
        CmdTools.showMenu("RecipeDb",
                "1. Food",
                "2. Recipe",
                "3. Storage",
                "4. Quit");

        switch (CmdTools.rangeChoice(4)){
            case 1:
                new FoodUi(services).run();
                break;
            case 2:
                new RecipeUI(services).run();
                break;
            case 3:
                new StorageUI(services).run();
                break;
            case 4:
                CmdTools.info("Bye");
                System.exit(0);
                break;
        }
    }

    public void run()   {
        while (true){
            try {
                doRun();
            } catch (Exception e) {
               CmdTools.err("error %s\n",e.getMessage());
               System.exit(0);
            }
        }
    }
}
