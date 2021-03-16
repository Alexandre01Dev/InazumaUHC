package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects;

import be.alexandre01.inazuma.uhc.listeners.host.InventoryClick;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BallonInv implements Listener{
    private Inventory inventory = Bukkit.createInventory(null,  9*3,"§eBallons");
    public BallonInv(){
        Material type;
        ItemStack magentaPane = new ItemBuilder(Material.STAINED_GLASS_PANE).setWoolColor(DyeColor.MAGENTA).setName(" ").toItemStack();
        ItemStack violetPane = new ItemBuilder(Material.STAINED_GLASS_PANE).setWoolColor(DyeColor.PURPLE).setName(" ").toItemStack();
        ItemStack bluePane = new ItemBuilder(Material.STAINED_GLASS_PANE).setWoolColor(DyeColor.BLUE).setName(" ").toItemStack();
        ItemStack blackPane = new ItemBuilder(Material.STAINED_GLASS_PANE).setWoolColor(DyeColor.BLACK).setName(" ").toItemStack();
        ItemStack barrier = new ItemBuilder(Material.BARRIER).setName("Vide").toItemStack();

        for (int i = 1; i < 7; i++) {
          if(i%2 == 0){
              inventory.setItem(i-1,magentaPane);
            continue;
         }
            inventory.setItem(i-1,violetPane);
        }

        for (int i = 7; i < 10; i++) {
            if(i%2 == 0){
                inventory.setItem(i-1,blackPane);
                 continue;
            }
            inventory.setItem(i-1,bluePane);
        }

        for (int i = 10; i < 16; i++) {
            if(i%2 == 0){
                inventory.setItem(i-1,magentaPane);
                continue;
            }
            inventory.setItem(i-1,barrier);
        }

        for (int i = 16; i < 19; i++) {
            if(i%2 == 0){
                inventory.setItem(i-1,blackPane);
                continue;
            }
            inventory.setItem(i-1,barrier);
        }

        for (int i = 19; i < 25; i++) {
            if(i%2 == 0){
                inventory.setItem(i-1,magentaPane);
                continue;
            }
            inventory.setItem(i-1,violetPane);
        }

        for (int i = 25; i < 28; i++) {
            if(i%2 == 0){
                inventory.setItem(i-1,blackPane);
                continue;
            }
            inventory.setItem(i-1,bluePane);
        }
    }
    public Inventory toInventory(){
        return inventory;
    }


    @EventHandler
    public void onClickInv(InventoryClickEvent event){
        if(event.getClickedInventory().getName().equals(inventory.getName())){
            event.setCancelled(true);
        }
    }

}
