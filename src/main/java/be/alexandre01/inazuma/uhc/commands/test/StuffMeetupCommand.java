package be.alexandre01.inazuma.uhc.commands.test;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class StuffMeetupCommand  extends Command {
    public StuffMeetupCommand(String s) {
        super(s);
        super.setPermission("uhc.stuffmeetup");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!sender.hasPermission("uhc.stuffmeetup")){
            sender.sendMessage(super.getPermissionMessage());
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;



            if (!player.hasPermission("uhc.stuffmeetup")) {
                player.sendMessage("§cVous n'avez pas la permission d'exécuter cette commande");
            } else {
                player.hasPermission("uhc.stuffmeetup");

                    for(Player players : Bukkit.getOnlinePlayers()){
                        ItemBuilder helmet = new ItemBuilder(Material.DIAMOND_HELMET);
                        helmet.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

                        ItemBuilder chestplate = new ItemBuilder(Material.DIAMOND_CHESTPLATE);
                        chestplate.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

                        ItemBuilder leggings = new ItemBuilder(Material.IRON_LEGGINGS);
                        leggings.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);

                        ItemBuilder boots = new ItemBuilder(Material.DIAMOND_BOOTS);
                        boots.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

                        ItemBuilder sword = new ItemBuilder(Material.DIAMOND_SWORD);
                        sword.addEnchant(Enchantment.DAMAGE_ALL, 3);

                        ItemBuilder bow = new ItemBuilder(Material.BOW);
                        bow.addEnchant(Enchantment.ARROW_DAMAGE, 2);

                        ItemBuilder arrow = new ItemBuilder(Material.ARROW, 64);

                        ItemBuilder apple = new ItemBuilder(Material.GOLDEN_APPLE, 12);

                        ItemBuilder block = new ItemBuilder(Material.COBBLESTONE, 256);

                        ItemBuilder water = new ItemBuilder(Material.WATER_BUCKET, 2);

                        ItemBuilder lava = new ItemBuilder(Material.LAVA_BUCKET, 2);

                        players.getInventory (). setHelmet (helmet.toItemStack());
                        players.getInventory (). setChestplate (chestplate.toItemStack());
                        players.getInventory (). setLeggings (leggings.toItemStack());
                        players.getInventory (). setBoots (boots.toItemStack());

                        players.getInventory ().addItem (sword.toItemStack());
                        players.getInventory ().addItem (bow.toItemStack());
                        players.getInventory ().addItem (arrow.toItemStack());
                        players.getInventory ().addItem (apple.toItemStack());
                        players.getInventory ().addItem (block.toItemStack());
                        players.getInventory ().addItem (water.toItemStack());
                        players.getInventory ().addItem (lava.toItemStack());

                        players.updateInventory();

                }
                Bukkit.broadcastMessage("GIVE DE STUFF MEETUP");
                }
        }
        return false;
    }
    }


