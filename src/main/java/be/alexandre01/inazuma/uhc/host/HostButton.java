package be.alexandre01.inazuma.uhc.host;

import be.alexandre01.inazuma.uhc.host.gui.WorkingPlace;

import be.alexandre01.inazuma.uhc.utils.SoundProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
@Getter @Setter
public class HostButton {
    private ItemStack itemStack;
    private SoundProperty sound = null;
    private Type type;
    private action action;
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
        if(isEnchanted)
            itemStack.addEnchantment(Enchantment.DURABILITY,1);
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
}
