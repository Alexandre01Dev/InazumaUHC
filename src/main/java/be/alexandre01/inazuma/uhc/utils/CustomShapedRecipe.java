package be.alexandre01.inazuma.uhc.utils;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.CraftingManager;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ShapedRecipes;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CustomShapedRecipe extends ShapedRecipe {
    public CustomShapedRecipe(ItemStack itemStack) {
        super(itemStack);
    }
    public ShapedRecipes test(net.minecraft.server.v1_8_R3.ItemStack var1, Object... var2){
        String var3 = "";
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        if (var2[var4] instanceof String[]) {
            String[] var11 = (String[])((String[])((String[])var2[var4++]));

            for(int var8 = 0; var8 < var11.length; ++var8) {
                String var9 = var11[var8];
                ++var6;
                var5 = var9.length();
                var3 = var3 + var9;
            }
        } else {
            while(var2[var4] instanceof String) {
                String var7 = (String)var2[var4++];
                ++var6;
                var5 = var7.length();
                var3 = var3 + var7;
            }
        }

        HashMap var12;
        for(var12 = new HashMap(); var4 < var2.length; var4 += 2) {
            Character var13 = (Character)var2[var4];
            net.minecraft.server.v1_8_R3.ItemStack var15 = null;
            if (var2[var4 + 1] instanceof Item) {
                var15 = new net.minecraft.server.v1_8_R3.ItemStack((Item)var2[var4 + 1]);
            } else if (var2[var4 + 1] instanceof Block) {
                var15 = new net.minecraft.server.v1_8_R3.ItemStack((Block)var2[var4 + 1], 1, 32767);
            } else if (var2[var4 + 1] instanceof net.minecraft.server.v1_8_R3.ItemStack) {
                var15 = (net.minecraft.server.v1_8_R3.ItemStack)var2[var4 + 1];
            }

            var12.put(var13, var15);
        }

        net.minecraft.server.v1_8_R3.ItemStack[] var14 = new net.minecraft.server.v1_8_R3.ItemStack[var5 * var6];

        for(int var16 = 0; var16 < var5 * var6; ++var16) {
            char var10 = var3.charAt(var16);
            if (var12.containsKey(var10)) {
                var14[var16] = ((net.minecraft.server.v1_8_R3.ItemStack)var12.get(var10)).cloneItemStack();
            } else {
                var14[var16] = null;
            }
        }

        ShapedRecipes var17 = new ShapedRecipes(var5, var6, var14, var1);
        CraftingManager.getInstance().recipes.add(var17);
        return var17;
    }
    @Override
    public ShapedRecipe shape(String... var1) {
        System.out.println("SHAPE");
        Validate.notNull(var1, "Must provide a shape");
        Validate.isTrue(var1.length > 0 && var1.length < 4, "Crafting recipes should be 1, 2, 3 rows, not ", (long)var1.length);
        String[] var2 = var1;
        int var3 = var1.length;

        int var4;
        for(var4 = 0; var4 < var3; ++var4) {
            String var5 = var2[var4];
            Validate.notNull(var5, "Shape cannot have null rows");
            Validate.isTrue(var5.length() > 0 && var5.length() < 4, "Crafting rows should be 1, 2, or 3 characters, not ", (long)var5.length());
        }
        String[] rows = new String[var1.length];

        for(int var11 = 0; var11 < var1.length; ++var11) {
            rows[var11] = var1[var11];
        }
        setPrivateField("rows",ShapedRecipe.class,this,rows);
        HashMap var12 = new HashMap();
        Map<Character,ItemStack> ingredients = (Map<Character, ItemStack>) getPrivateField("ingredients",ShapedRecipe.class,this);
        String[] var13 = var1;
        var4 = var1.length;

        for(int var14 = 0; var14 < var4; ++var14) {
            String var6 = var13[var14];
            char[] var7 = var6.toCharArray();
            int var8 = var7.length;


            for(int var9 = 0; var9 < var8; ++var9) {
                Character var10 = var7[var9];
                var12.put(var10, ingredients.get(var10));
            }
        }
        setPrivateField("ingredients",ShapedRecipe.class,this,var12);
        return this;
    }

    public ShapedRecipe setIngredient(char var1, ItemStack var2) {
        Map<Character,ItemStack> ingredients = (Map<Character, ItemStack>) getPrivateField("ingredients",ShapedRecipe.class,this);
        Validate.isTrue(ingredients.containsKey(var1), "Symbol does not appear in the shape:", (long)var1);

        ingredients.put(var1, var2);
        setPrivateField("ingredients",ShapedRecipe.class,this,ingredients);
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
