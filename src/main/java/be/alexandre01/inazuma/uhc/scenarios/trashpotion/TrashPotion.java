package be.alexandre01.inazuma.uhc.scenarios.trashpotion;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class TrashPotion extends Scenario implements Listener {
    public TrashPotion() {
        super("TrashPotion", "Clear la potion une fois bu.");
        addListener(this);
        ItemBuilder itemBuilder = new ItemBuilder(Material.GLASS_BOTTLE);
        setItemStack(itemBuilder.toItemStack());
    }
    @EventHandler
    public void onDrinkPotion(PlayerItemConsumeEvent e){
            if (e.getItem().getType().equals(Material.POTION)){
                Player player = e.getPlayer();
                ItemStack p = e.getItem();
                System.out.println("Potion bu");
                Potion potion = Potion.fromItemStack(e.getItem());

                Bukkit.getScheduler().runTaskLater(InazumaUHC.get, new BukkitRunnable() {
                    @Override
                    public void run() {
                        for(ItemStack it : player.getInventory().getContents()){
                            if(it != null){
                                System.out.println(it.getType());
                                System.out.println(p.getType());
                                if(it.equals(p)){
                                    System.out.println("TROUVE BORDEL");
                                    for(PotionEffect potionEffect : potion.getEffects()){
                                        player.addPotionEffect(potionEffect);
                                    }
                                    it.setType(Material.AIR);
                                    player.updateInventory();
                                    break;
                                }
                            }
                        }
                    }
                },10);



            }
    }

}