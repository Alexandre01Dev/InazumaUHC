package be.alexandre01.inazuma.uhc.handler;

import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.PacketPlayInResourcePackStatus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.imanity.imanityspigot.packet.PacketHandler;

public class RessourcePackHandler implements PacketHandler{
    @Override
    public boolean onReceived(Player player, Object packet) {
        if (packet instanceof PacketPlayInResourcePackStatus) {
            PacketPlayInResourcePackStatus p = ((PacketPlayInResourcePackStatus) packet);
            if(p.b == PacketPlayInResourcePackStatus.EnumResourcePackStatus.ACCEPTED){
                Bukkit.broadcastMessage("Le texture pack a été accepté");
            }
            if(p.b == PacketPlayInResourcePackStatus.EnumResourcePackStatus.DECLINED){
                Bukkit.broadcastMessage("Le texture pack a été refusé");
            }
            if(p.b == PacketPlayInResourcePackStatus.EnumResourcePackStatus.SUCCESSFULLY_LOADED){
                Bukkit.broadcastMessage("Le texture pack est bien chargé");
            }
            if(p.b == PacketPlayInResourcePackStatus.EnumResourcePackStatus.FAILED_DOWNLOAD){
                Bukkit.broadcastMessage("Le texture pack a eu des problèmes dans sa vie.");
            }
        }
        return true;
    }
}
