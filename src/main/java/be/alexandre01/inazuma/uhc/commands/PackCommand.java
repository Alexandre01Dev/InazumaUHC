package be.alexandre01.inazuma.uhc.commands;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_8_R3.PacketPlayOutResourcePackSend;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.UnsupportedEncodingException;


public class PackCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("pack")){
                player.sendMessage("§e§lInazuma §lEleven §e§7» " +  " §6§lNotre Pack §7:");
                player.sendMessage("§3§lInazuma §lEleven §7: §cbit.ly/3meCaAo");
                try {
                    byte [] hashArray = "ecd05a79643e27bc6b81d435b59866d7".getBytes("UTF-8");
                  //  PacketPlayOutResourcePackSend p = new PacketPlayOutResourcePackSend("",hashArray);
                    EntityPlayer e = ((CraftPlayer)player).getHandle();
                    e.setResourcePack("https://www.dropbox.com/s/pwo67bn9a1fpdr5/InazumaPack.zip?dl=1","1a3dd1afdd6aa90b2f0d2e88cda479b2c8b15fd8");
                 //   e.playerConnection.sendPacket(p);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        }

        return false;
    }
}
