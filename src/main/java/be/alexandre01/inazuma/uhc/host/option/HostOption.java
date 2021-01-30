package be.alexandre01.inazuma.uhc.host.option;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.host.gui.HostOptionsGui;
import be.alexandre01.inazuma.uhc.host.gui.ModifierGUI;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HostOption extends HostButton{
    public Object value;
    private boolean isModifiable = true;
    private ArrayList<HostOptionsGui> hostOptionsGuis;
    private String description = null;
    private VarType varType;
    private IPreset preset;
    private String varName;
    private String name;
    private action action;
    int range = 1;
    private int[] minAndMax = {0,9999};
    private ModifierGUI modifierGUI = null;
    private int[] minAndMaxBypass = {0,9999};
    private ItemStack itemStack = new ItemStack(Material.WOOD);

    public HostOption(Object value,String varName){
        super(Type.OPTION);
        hostOptionsGuis = new ArrayList<>();
        preset = Preset.instance.p;
        this.value = value;
        this.varName = varName;
        setupDefaultAction();
    }

    public void setupDefaultAction(){
        HostOption hostOption = this;
        action = new action() {
            @Override
            public void a(Object value) {
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

                hostOption.value = value;
                updateItemStack();
            }
        };
    }


    public void setModifiable(boolean b){
        this.isModifiable = b;
    }

    public void setItemStack(ItemStack it){
        this.itemStack= it;
        updateItemStack();
    }

    public void updateItemStack(){
        System.out.println(itemStack.getItemMeta().getLore());
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(name != null){
            itemMeta.setDisplayName(name);
        }else {
            itemMeta.setDisplayName(varName);
        }

        List<String> l = new ArrayList<>();
        System.out.println("Item >"+varType);
        if(varType.equals(VarType.FASTBOOL)||(varType.equals(VarType.BOOLEAN))){
            if((boolean)value){
                l.add("§aActivé");
            }else {
                l.add("§cDésactivé");
            }
        }
        if(varType.equals(VarType.INTEGER)){
            l.add("§7Valeur : §a"+value);
        }
        if(varType.equals(VarType.STRING)){
            l.add("§7Valeur : §a"+value);
        }

        if(!isModifiable){
            l.add("§cNe peut pas être modifié");
        }

        if(description != null){
            l.add(description);
        }
        itemMeta.setLore(l);
        itemStack.setItemMeta(itemMeta);

        System.out.println(itemStack.getItemMeta().getLore());

        for(HostOptionsGui h : hostOptionsGuis){
            System.out.println("YES");
            h.update(h.getHostOptions().get(this),itemStack);
        }
    }

    public void setDescription(String description) {
        if(itemStack != null){
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> l;
            if(itemMeta.hasLore()){
               l = new ArrayList<>(itemMeta.getLore());
            }else {
                l = new ArrayList<>();
            }

            l.add(description);
            itemMeta.setLore(l);
            itemStack.setItemMeta(itemMeta);
        }
        this.description = description;
    }

    public void setName(String name) {
        if(itemStack != null){
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(name);
            itemStack.setItemMeta(itemMeta);
        }

        this.name = name;
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
        action.a(value);
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

    public void addHostOption(HostOptionsGui hostOptionsGui) {
        hostOptionsGuis.add(hostOptionsGui);
    }

    public void setAction(HostOption.action action) {
        this.action = action;
    }

    public interface action{
        public void a(Object value);
    }
}
