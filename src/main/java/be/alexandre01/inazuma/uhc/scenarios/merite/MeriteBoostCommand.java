package be.alexandre01.inazuma.uhc.scenarios.merite;

import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class MeriteBoostCommand extends Command {

    Merite merite;

    public MeriteBoostCommand(String s, Merite merite) {
        super(s);
        this.merite = merite;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        sender.sendMessage("t'es un gros fils de pute comme la fibre en belgique");
        if(sender instanceof Player)
        {
            Player player = (Player) sender;
            if(merite.players.contains(sender))
            {
                Inventory inv = Bukkit.createInventory(null, 54, "Boostez votre multiplicateur");

                String s;

                if(merite.getKill(player) == 99)
                    s = "Choix de classe";
                else {
                    s = Float.toString(merite.getKill(player));
                }


                inv.setItem(20, new ItemBuilder(Material.IRON_SWORD).setName("§6Mêlée").setLore("Multiplicateur : " + merite.getSwordPourcent(player)+ " + 0.05 §7(Prix » 1 kill)").toItemStack() );
                inv.setItem(22, new ItemBuilder(Material.IRON_CHESTPLATE).setName("§6Armure").setLore("Multiplicateur : " + merite.getArmorPourcent(player)+ " + 0.05 §7(Prix » 1 kill)").toItemStack());
                inv.setItem(24, new ItemBuilder(Material.BOW).setName("§6Arc").setLore("Multiplicateur : " + merite.getBowPourcent(player)+ " + 0.05 §7(Prix » 1 kill)").toItemStack());
                inv.setItem(49, new ItemBuilder(Material.SKULL_ITEM).setSkullOwner(sender.getName()).setName("§6Stats").setLore("§rMêlée » §7 " + merite.getSword(player) , "§rArmure  » §7 " + merite.getArmor(player) , "§rArc » §7" + merite.getBow(player) , "§rPoint(s) disponible(s) » §7" + s).toItemStack());
                player.openInventory(inv);

            }
        }
    return false;
    }
}
