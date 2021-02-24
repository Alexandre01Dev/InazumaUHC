package be.alexandre01.inazuma.uhc.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryContainer {
    private final ItemStack[] contents;
    private final ItemStack boots;
    private final ItemStack leggings;
    private final ItemStack chestplate;
    private final ItemStack helmet;

    public InventoryContainer(PlayerInventory inventory){
        contents = inventory.getContents();
        boots = inventory.getBoots();
        leggings = inventory.getLeggings();
        chestplate = inventory.getChestplate();
        helmet = inventory.getHelmet();
    }

    public void restitutionToPlayer(Player player){
        player.getInventory().clear();

        player.getInventory().setContents(contents);

        player.getInventory().setBoots(boots);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setHelmet(helmet);

        player.updateInventory();
    }
}
