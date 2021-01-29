package be.alexandre01.inazuma.uhc.host.option;

import be.alexandre01.inazuma.uhc.host.gui.HostOptionsGui;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class HostContainer {
    private ArrayList<HostOption> hostOptions;
    private String name;
    private String description;
    private int slot = 0;
    private ItemStack itemStack = new ItemStack(Material.WOOD);
    private HostOptionsGui hostOptionsGui;

    public HostContainer(String name){
        hostOptions = new ArrayList<>();
        this.name = name;
    }

    public ArrayList<HostOption> getHostOptions() {
        return hostOptions;
    }

    public int getSlot() {
        return slot;
    }

    public void deploy(){
         this.hostOptionsGui = new HostOptionsGui(this);
    }
    public void setSlot(int slot) {
        this.slot = slot;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public HostOptionsGui getHostOptionsGui() {
        return hostOptionsGui;
    }
}
