package be.alexandre01.inazuma.uhc.roles;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RoleCraft {
    private String[] keys;
    private ItemStack itemStack;
    private Recipe recipe;
    private final ArrayList<Tuple<Character,Material>> ingredients = new ArrayList<>();

    public RoleCraft() {
    }

    public RoleCraft setKeys(String... keys){
        this.keys = keys;
        return this;
    }

    public RoleCraft setItemStack(ItemStack itemStack){
        this.itemStack = itemStack;
        return this;
    }
    public RoleCraft setIngredient(char pos, Material material){
        ingredients.add(new Tuple<>(pos,material));
        return this;
    }
    public void build(){
        ShapedRecipe shapedRecipe = new ShapedRecipe(itemStack);
        shapedRecipe.shape(keys);
        for(Tuple<Character,Material> tuple : ingredients){
            shapedRecipe.setIngredient(tuple.a(),tuple.b());
        }
        this.recipe = shapedRecipe;
        Bukkit.addRecipe(shapedRecipe);
    }
}
