package be.alexandre01.inazuma.uhc.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class InvSeeCommand extends Command {

    Inventory inventory;
    Player target;

    public InvSeeCommand(String s) {
        super(s);
        super.setPermission("uhc.invsee");
    }

    @Override
    public boolean execute(CommandSender sender, String msg, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("command.invsee")) {
                if (args.length == 0) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cMerci de spéficiez le pseudo du joueur !"));
                } else if (args.length == 1) {
                    target = Bukkit.getPlayer(args[0]);
                    inventory = Bukkit.createInventory(null, 54, "§6Inventaire de §c§l" + target.getName());

                    ItemStack health = new ItemStack(Material.GOLDEN_APPLE);
                    ItemMeta healthMeta = health.getItemMeta();
                    healthMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lVie"));
                    ArrayList<String> healthLore = new ArrayList<String>();
                    healthLore.add(ChatColor.translateAlternateColorCodes('&', "&c" + Math.round(target.getHealth()) + " &rCoeurs"));
                    healthMeta.setLore(healthLore);
                    health.setItemMeta(healthMeta);
                    inventory.setItem(51, health);

                    ItemStack hunger = new ItemStack(Material.COOKED_BEEF);
                    ItemMeta hungerMeta = hunger.getItemMeta();
                    hungerMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lFaim"));
                    ArrayList<String> hungerLore = new ArrayList<String>();
                    hungerLore.add(ChatColor.translateAlternateColorCodes('&', "&e" + target.getFoodLevel() + " &rNourritures"));
                    hungerMeta.setLore(hungerLore);
                    hunger.setItemMeta(hungerMeta);
                    inventory.setItem(52, hunger);

                    ItemStack xp = new ItemStack(Material.EXP_BOTTLE);
                    ItemMeta xpMeta = xp.getItemMeta();
                    xpMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lExperience"));
                    ArrayList<String> xpLore = new ArrayList<String>();
                    xpLore.add(ChatColor.translateAlternateColorCodes('&', "&a" +"&rLevel &a" + target.getLevel() + "&r"));
                    xpMeta.setLore(xpLore);
                    xp.setItemMeta(xpMeta);
                    inventory.setItem(53, xp);

                    ItemStack stainedGlass = new ItemStack(Material.STAINED_GLASS_PANE);
                    ItemMeta stainedGlassMeta = stainedGlass.getItemMeta();
                    stainedGlassMeta.setDisplayName(" ");
                    stainedGlass.setItemMeta(stainedGlassMeta);


                    for (int i = 0; i < 9; i++) {
                        inventory.setItem(36 + i, stainedGlass);
                    }
                    inventory.setItem(49, stainedGlass);
                    inventory.setItem(50, stainedGlass);

                    for (int i = 0; i < 9; i++) {
                        inventory.setItem(27 + i, target.getInventory().getItem(i));
                    }

                    ItemStack[] armorContent = target.getInventory().getArmorContents();
                    for (int i = 0; i < 4; i++) {
                        inventory.setItem(45 + i, armorContent[3 - i]);
                    }

                    player.openInventory(inventory);
                } else if (args.length == 2) {
                    if (player != null && target != null) {
                        player.openInventory(inventory);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6" + player.getName() + " &anow sees&6 " + target.getName() + "'s &ainventory"));
                    } else {
                        player.sendMessage(ChatColor.RED + "Un de ses joueurs, n'est pas en ligne !");
                    }
                }
            }
        } else {
            if (args.length == 0 || args.length == 1) {
                sender.sendMessage("Spécifiez le nom du joueur !");
            }
            if (args.length == 2) {
                Player player = Bukkit.getPlayer(args[1]);
                Player target = Bukkit.getPlayer(args[0]);
                if (player != null && target != null) {
                    player.openInventory(inventory);
                    sender.sendMessage(player.getName() + " now sees " + target.getName() + "'s inventory");
                } else {
                    sender.sendMessage("Un de ses joueurs, n'est pas en ligne !");
                }

            }
        }
        return true;
    }


    @EventHandler
    public void invsee(InventoryClickEvent event) {
        if (event.getInventory().getName().startsWith("§6Inventaire de §c§l")) {
            ItemStack clickedItem = event.getCurrentItem();
            Material[] materials = {Material.GOLDEN_APPLE, Material.COOKED_BEEF, Material.EXP_BOTTLE, Material.STAINED_GLASS_PANE};
            if (clickedItem != null) {
                if (Arrays.asList(materials).contains(clickedItem.getType())) {
                    event.setCancelled(true);
                }
            }
        }
    }
}

