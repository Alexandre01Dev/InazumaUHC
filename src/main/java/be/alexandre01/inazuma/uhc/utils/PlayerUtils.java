package be.alexandre01.inazuma.uhc.utils;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Set;

public class PlayerUtils {
    public static Entity getNearestEntityInSight(Player player, int range) {
        ArrayList<Entity> entities = (ArrayList<Entity>) player.getNearbyEntities(range, range, range);
        ArrayList<Block> sightBlock = (ArrayList<Block>) player.getLineOfSight( (Set<Material>) null, range);
        ArrayList<Location> sight = new ArrayList<Location>();
        for (int i = 0;i<sightBlock.size();i++)
            sight.add(sightBlock.get(i).getLocation());
        for (int i = 0;i<sight.size();i++) {
            for (int k = 0;k<entities.size();k++) {
                if (Math.abs(entities.get(k).getLocation().getX()-sight.get(i).getX())<1.3) {
                    if (Math.abs(entities.get(k).getLocation().getY()-sight.get(i).getY())<1.5) {
                        if (Math.abs(entities.get(k).getLocation().getZ()-sight.get(i).getZ())<1.3) {
                            return entities.get(k);
                        }
                    }
                }
            }
        }
        return null;
    }

    public static Player getNearestPlayerInSight(Player player, int range) {
        ArrayList<Player> players = (ArrayList<Player>) getNearbyPlayersFromPlayer(player,range, range, range);
        ArrayList<Block> sightBlock = (ArrayList<Block>) player.getLineOfSight( (Set<Material>) null, range);
        ArrayList<Location> sight = new ArrayList<Location>();
        for (int i = 0;i<sightBlock.size();i++)
            sight.add(sightBlock.get(i).getLocation());
        for (int i = 0;i<sight.size();i++) {
            for (int k = 0;k<players.size();k++) {
                if (Math.abs(players.get(k).getLocation().getX()-sight.get(i).getX())<1.3) {
                    if (Math.abs(players.get(k).getLocation().getY()-sight.get(i).getY())<1.5) {
                        if (Math.abs(players.get(k).getLocation().getZ()-sight.get(i).getZ())<1.3) {
                            return players.get(k);
                        }
                    }
                }
            }
        }
        return null;
    }
    public static ArrayList<Player> getNearbyPlayers(Location location, int x, int y, int z){
        ArrayList<Player> p = new ArrayList<>();
        InazumaUHC i = InazumaUHC.get;
        for (Entity entity : location.getWorld().getNearbyEntities(location,x,y,z)){
            if(entity instanceof Player){
                Player player = (Player) entity;
                if(!i.spectatorManager.getPlayers().contains(player)){
                    p.add(player);
                }
            }
        }
        return p;
    }

    public static ArrayList<Player> getNearbyPlayersFromPlayer(Player player, int x, int y, int z){
        ArrayList<Player> p = new ArrayList<>();
        InazumaUHC i = InazumaUHC.get;
        for (Entity entity : player.getWorld().getNearbyEntities(player.getLocation(),x,y,z)){
            if(entity instanceof Player){
                Player opposant = (Player) entity;
                if(opposant == player){
                    continue;
                }


                if(!i.spectatorManager.getPlayers().contains(opposant)){
                    p.add(opposant);
                }
            }
        }
        return p;
    }

    public static void sendViewPacket(Player player, Location location){
        EntityPlayer nmsPlayer = ((CraftPlayer)player).getHandle();
        PacketPlayOutNamedEntitySpawn entitySpawnpacket = new PacketPlayOutNamedEntitySpawn(nmsPlayer);
        PacketPlayOutEntityTeleport tpPacket = new PacketPlayOutEntityTeleport(nmsPlayer.getId(), MathHelper.floor(location.getBlockX() * 32.0D),MathHelper.floor(location.getBlockY() * 32.0D),MathHelper.floor(location.getBlockZ() * 32.0D),(byte)((int)(location.getYaw() * 256.0F / 360.0F)),(byte)((int)(location.getPitch() * 256.0F / 360.0F)),nmsPlayer.onGround);
        PacketPlayOutEntityEquipment handPacket = new PacketPlayOutEntityEquipment(nmsPlayer.getId(), 0, null);

        DataWatcher w = new DataWatcher((net.minecraft.server.v1_8_R3.Entity) null);
        w.a(6,(float)20);
        w.a(10,(byte)127);
        w.a(0,(byte)0);
        PacketPlayOutEntityMetadata pMeta = new PacketPlayOutEntityMetadata(nmsPlayer.getId(),w,true);
        for(Player players : Bukkit.getOnlinePlayers()){
            if(players.equals(player))
                continue;

            PlayerConnection connection =  ((CraftPlayer)players).getHandle().playerConnection;
            connection.sendPacket(entitySpawnpacket);
            connection.sendPacket(tpPacket);
            connection.sendPacket(pMeta);
            connection.sendPacket(handPacket);
        }
    }
}
