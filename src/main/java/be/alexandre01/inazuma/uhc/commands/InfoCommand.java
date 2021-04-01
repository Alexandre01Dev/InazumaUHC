package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.utils.fastinv.FastInv;
import be.alexandre01.inazuma.uhc.utils.fastinv.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class InfoCommand extends Command {
    public InfoCommand(String s) {
        super(s);
        super.setPermission("uhc.info");
    }

    @Override
    public boolean execute(CommandSender sender, String msg, String[] args) {
        Player player = (Player)sender;

        if (args.length != 1) {
            sender.sendMessage("pas de truc après le pseudo");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        Role role = InazumaUHC.get.rm.getRole(target);

        if (target == null){
            sender.sendMessage("Joueur nul");
            return false;
        }

        if (!target.isOnline()){
            sender.sendMessage("Joueur pas co");
        }

        FastInv inv = new FastInv(1*9, "Inventaire de" + target.getName());

        Stream<ItemStack> stream = Arrays.stream(target.getInventory().getContents());

        int gapples = (int) stream.filter(itemStack -> itemStack.getType() == Material.GOLDEN_APPLE).count();

        int diamond = (int) stream.filter(itemStack -> itemStack.getType() == Material.DIAMOND_ORE).count();

        int gold = (int) stream.filter(itemStack -> itemStack.getType() == Material.GOLD_ORE).count();

        inv.setItem(0, new ItemBuilder(Material.GOLDEN_APPLE).name("Nombre de gapple").lore("Nombre de pomme d'or :" + gapples).build());
        inv.setItem(1, new ItemBuilder(Material.DIAMOND_ORE).name("Minerais").lore("Nombre de diamant miné :" + diamond , "Nombre d'or' miné :" + gold).build());
        inv.setItem(47, new ItemBuilder(Material.COMMAND).name("§6§lCommandez §6§lun §6§lserveur").lore("§7Commandez un serveur pour lancer une partie").build());


        sender.sendMessage("Ouverture de l'inventaire de " + target.getName());
        return false;
    }
}
