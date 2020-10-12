package repository.txt;

import entitys.Recipe;
import repository.FoodRepository;
import repository.RecipeFoodRepository;
import repository.RecipeRepository;
import repository.SequenceRepository;
import utils.TxtDbFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TxtRecipeRepository implements RecipeRepository {

    private static final String[] HEAD = new String[]{"ID", "NAME"};

    final private SequenceRepository seq;
    final private RecipeFoodRepository refRep;

    private TxtDbFile get() throws IOException {
        return new TxtDbFile(TxtDbFile.folder + "/Recipe.txt", HEAD);
    }

    public TxtRecipeRepository(SequenceRepository seq, FoodRepository foodRep, RecipeFoodRepository refRep) {
        this.seq = seq;
        this.refRep = refRep;
    }
    @Override
    public Recipe findByName(String name) throws Exception {
        return findByName(name, false);
    }
    @Override
    public Recipe findByName(String name, boolean caseSensitive) throws Exception {
        try (TxtDbFile txt = get()) {
            Map<String, String> row = txt.getFirst(t -> {
                if (caseSensitive) {
                    return t.get("NAME").equals(name);
                } else {
                    return t.get("NAME").equalsIgnoreCase(name);
                }
            });
            return mapToRecipe(row);
        }
    }

    @Override
    public List<Recipe> getAll() throws Exception {
        try (TxtDbFile txt = get()) {
            List<Recipe> ret = new ArrayList<>();
            for (Map<String, String> row : txt.getRows()) {
                ret.add(mapToRecipe(row));
            }
            return ret;
        }
    }

    private Recipe mapToRecipe(Map<String, String> row) throws Exception {
        if (row == null) {
            return null;
        }
        int rid = Integer.parseInt(row.get("ID"));
        String name = row.get("NAME");
        return new Recipe(rid, name, new ArrayList<>(refRep.findFoodId(rid)));
    }

    @Override
    public Recipe findById(int id) throws Exception {
        try (TxtDbFile txt = get()) {
            Map<String, String> row = txt.getFirst(t -> t.get("ID").equals(id + ""));
            return mapToRecipe(row);
        }
    }

    @Override
    public Recipe save(Recipe recipe) throws Exception {
        try (TxtDbFile txt = get()) {
            if (recipe.getId() == null) {
                recipe.setId(seq.getNext("RecipeSeq"));
                txt.writeRecord(recipe.getId() + "", recipe.getName());
                refRep.deleteByRecipeId(recipe.getId());
                refRep.insert(recipe.getId(), recipe.getFoods());
                return recipe;
            }
            Map<String, String> row = txt.getFirst(t -> t.get("ID").equals(recipe.getId() + ""));
            if (row == null) {
                throw new IllegalArgumentException(String.format("id[%d]不存在", recipe.getId()));
            }
            row.put("NAME", recipe.getName());

        }
        return recipe;
    }
}












