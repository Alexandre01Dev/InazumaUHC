package be.alexandre01.inazuma.uhc.host.option;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.host.gui.ModifierGUI;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.Arrays;

public class HostOption {
    private Object value;
    private boolean isModifiable = true;
    private String description;
    private VarType varType;
    private IPreset preset;
    private String varName;
    int range = 1;
    private int[] minAndMax = {0,9999};
    private ModifierGUI modifierGUI = null;
    private int[] minAndMaxBypass = {0,9999};
    private ItemStack itemStack = new ItemStack(Material.WOOD);

    public HostOption(Object value,String varName){
        preset = Preset.instance.p;
        this.value = value;
        this.varName = varName;
    }
    public void setModifiable(boolean b){
        this.isModifiable = b;
    }

    public void setItemStack(ItemStack it){
        this.itemStack= it;
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(varName);
        itemStack.setItemMeta(itemMeta);
    }

    public void setDescription(String description) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(Arrays.asList(description));
        itemStack.setItemMeta(itemMeta);
        this.description = description;
    }

    public boolean isModifiable() {
        return isModifiable;
    }

    public String getDescription() {
        return description;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }


    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        Class c = null;
        try {
            c = Class.forName("be.alexandre01.inazuma.uhc.presets."+preset.getPackageName()+"."+preset.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(c != null){
            try {
                for(Field f : c.getDeclaredFields()){
                    System.out.println("DeclaredF > "+f.getName());
                }
                for(Field f : c.getFields()){
                    System.out.println("F > "+f.getName());
                }
                Field field = c.getField(varName);
                field.set(preset,value);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        this.value = value;
    }

    public VarType getVarType() {
        return varType;
    }

    public void setMinAndMax(int[] minAndMax) {
        this.minAndMax = minAndMax;
    }

    public void setVarType(VarType varType) {
        this.varType = varType;
    }

    public ModifierGUI getModifierGUI() {
        return modifierGUI;
    }

    public void setModifierGUI(ModifierGUI modifierGUI) {
        this.modifierGUI = modifierGUI;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public IPreset getPreset() {
        return preset;
    }

    public String getVarName() {
        return varName;
    }

    public int[] getMinAndMax() {
        return minAndMax;
    }

    public int[] getMinAndMaxBypass() {
        return minAndMaxBypass;
    }
}
