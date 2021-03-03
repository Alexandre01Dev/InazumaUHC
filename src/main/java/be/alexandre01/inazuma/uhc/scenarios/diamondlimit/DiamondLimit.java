package be.alexandre01.inazuma.uhc.scenarios.diamondlimit;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.utils.CustomExp;
import be.alexandre01.inazuma.uhc.utils.ExperienceManager;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class DiamondLimit extends Scenario implements Listener {
    private HashMap <UUID, Integer> ints;
    public DiamondLimit() {
        super("DiamondLimit", "Définir une limite de diamant");
        addListener(this);
        ItemBuilder itemBuilder = new ItemBuilder(Material.DIAMOND_ORE);
        setItemStack(itemBuilder.toItemStack());
        ints = new HashMap<>();
    }
    @EventHandler
    public void onBreakDiamondOre(BlockBreakEvent e){
        Player player = e.getPlayer();

        if (e.getBlock().getType().equals(Material.DIAMOND_ORE)){

            if (!ints.containsKey(player.getUniqueId())){
                ints.put(player.getUniqueId(), 1);
                return;
            }
            int i = ints.get(player.getUniqueId());
            i++;
            ints.put(player.getUniqueId(), i);

            if (i >= 22){
                e.setCancelled(true);
                e.getBlock().setType(Material.AIR);
                ((ExperienceOrb)CustomExp.spawn(InazumaUHC.getGet(),player.getLocation()).getBukkitEntity()).setExperience(e.getExpToDrop());
                e.getBlock().getWorld().dropItemNaturally(ItemStack(Material.GOLD_INGOT);
            }
        }

    }

}
