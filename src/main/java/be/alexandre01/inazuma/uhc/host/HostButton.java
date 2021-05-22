package be.alexandre01.inazuma.uhc.host;

import be.alexandre01.inazuma.uhc.host.gui.WorkingPlace;

import be.alexandre01.inazuma.uhc.utils.SoundProperty;
import be.alexandre01.inazuma.uhc.utils.fastinv.FastInv;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
@Getter @Setter
public class HostButton {
    private ItemStack itemStack;
    private SoundProperty sound = null;
    private Type type;
    private action action;
    private WorkingPlace redirection;
    private HashMap<WorkingPlace,Integer> locations = new HashMap<>();

    public HostButton(ItemStack itemStack){
        this.itemStack = itemStack;
    }
    public HostButton(Material material, String name,Type type){
        this.itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        this.type = type;
    }
    public HostButton(Material material,String name,boolean isEnchanted,Type type){
        this.itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        this.type = type;
        setEnchant(isEnchanted);
    }
    public void setEnchant(boolean b){
        if(b){
            addGlow(itemStack);
        }
    }
    public void addOnWorkingPlace(WorkingPlace workingPlace, int slot){
        locations.put(workingPlace,slot);
    }
    public enum Type{
        REDIRECTION,OPTION,DIRECT,NONE;
    }

    public void playSound(Sound sound,float v1, float v2){
        this.sound = new SoundProperty(sound,v1,v2);
    }
    public void playSound(SoundProperty soundProperty){
        this.sound = soundProperty;
    }
    public void addLore(String... lore){

    }
    public interface action{
        public void onClick(Player player);
    }

    public enum Lores{
        RIGHTCLICK("§eVeuillez faire un click §9droit"),
        LEFTCLICK("§eVeuillez faire un click §cgauche"),
        MIDDLECLICK("§eVeuillez faire un click §dmolette"),
        DROPCLICK("§eVeuillez faire la touche de drop (a)");


        @Override
        public String toString() {
            return super.toString();
        }

        String s;
        Lores(String s){
            this.s = s;
        }
    }
    private void addGlow(org.bukkit.inventory.ItemStack stack) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
        NBTTagCompound compound = nmsStack.getTag();

        // Initialize the compound if we need to
        if (compound == null) {
            compound = new NBTTagCompound();
            nmsStack.setTag(compound);
        }

        // Empty enchanting compound
        compound.set("ench", new NBTTagList());

        itemStack = CraftItemStack.asBukkitCopy(nmsStack);
    }

    public void setRedirection(Class<?> redirection){
        Host host = Host.getInstance();
        if(host.getMenu(redirection) != null){
            this.redirection = Host.getInstance().getMenu(redirection);
            return;
        }
        try {
            this.redirection = (WorkingPlace) redirection.getDeclaredConstructor(FastInv.class).newInstance(Host.getInstance().getDefaultInv());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Host.getInstance().addMenu(this.redirection);
    }

}
