package be.alexandre01.inazuma.uhc.utils;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.lang.reflect.Field;

public class CustomShapedRecipe extends ShapedRecipe {
    public CustomShapedRecipe(ItemStack itemStack) {
        super(itemStack);
    }

    @Override
    public ShapedRecipe setIngredient(char var1, Material var2, int var3) {
        Validate.isTrue(this.getIngredientMap().containsKey(var1), "Symbol does not appear in the shape:", (long)var1);
        if (var3 == -1) {
            var3 = 32767;
        }

        this.getIngredientMap().put(var1, new ItemStack(var2, 1, (short)var3));
        return this;
    }

    public ShapedRecipe setIngredient(char var1, ItemStack var2) {
        Validate.isTrue(this.getIngredientMap().containsKey(var1), "Symbol does not appear in the shape:", (long)var1);
        this.getIngredientMap().put(var1, var2);
        return this;
    }

    private Object getPrivateField(String fieldName, Class clazz, Object a) {
        Object o = null;
        Field field;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            o = field.get(a);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return o;
    }

    private void setPrivateField(String fieldName, Class clazz, Object a, Object e) {
        Field field;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(a, e);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }
}
