import controller.CommandUI;
import services.RecipeServices;
import utils.DbHelper;

public class Main{
    public static void main(String[] argv)   {
        new CommandUI(new RecipeServices(new DbHelper())).run();
    }
}
