/*package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.utils.Tracker;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;


public class RulesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("rules")){
                rulesinventory(player);
            }
        }
        return false;
    }

    private void rulesinventory(Player player){
        org.bukkit.inventory.Inventory inv = Bukkit.createInventory(null, 54, "§7§lMenu des §c§lRègles");

        ItemStack enchant = new ItemStack(Material.ENCHANTMENT_TABLE);
        ItemMeta enchantMeta = enchant.getItemMeta();
        enchantMeta.setDisplayName("§9§lLimite d'§3§lEnchantement :");
        enchantMeta.setLore(Arrays.asList("§7§lFer :", " §f§lProtection §7§lIII", " §f§lTranchant §7§lIII", "§b§lDiamant :"," §f§lProtection §7§lII", " §f§lTranchant §7§lIII"));
        enchant.setItemMeta(enchantMeta);
        inv.setItem(1, enchant);
        player.openInventory(inv);

    }

    @EventHandler
    public void onClick(InventoryClickEvent event){

        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();

        if(inv.getName().equalsIgnoreCase("§7§lMenu des §c§lRègles")){
            event.setCancelled(true);
        }

    }
}*/


