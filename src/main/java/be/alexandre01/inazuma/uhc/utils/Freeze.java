package be.alexandre01.inazuma.uhc.utils;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class Freeze {
    int i;
    public Freeze(int i){
        this.npcs = new ArrayList<>();
        this.freezedPlayers = new HashMap<>();
        this.playerNPC = new HashMap<>();
        this.i = i;
    }
    private HashMap<Player,NPC> playerNPC;
    private HashMap<Location,Player> freezedPlayers;
    private ArrayList<NPC> npcs;
    private onStop onStop;

    public Freeze.onStop getOnStop() {
        return onStop;
    }

    public void setOnStop(Freeze.onStop onStop) {
        this.onStop = onStop;
    }

    public void freezePlayer(Player player){
        Location loc = player.getLocation();
        loc.setY(player.getLocation().getY()+0.01);
        freezedPlayers.put(loc,player);

        player.teleport(loc);

        player.setAllowFlight(true);
        player.setFlying(true);
        player.setFlySpeed(0);
        EntityPlayer playerNMS = ((CraftPlayer) player).getHandle();
        GameProfile profile = playerNMS.getProfile();
        Property property = profile.getProperties().get("textures").iterator().next();
        String texture = property.getValue();
        String signature = property.getSignature();

        NPC npc = new NPC(player,player.getDisplayName(),player.getLocation());
        npc.changeSkin(texture,signature);

        npc.addToTablist();
        npc.rmvFromTablist();

        npc.equip(0, CraftItemStack.asNMSCopy(player.getItemInHand()));
        npc.equip(1,CraftItemStack.asNMSCopy(player.getInventory().getBoots()));
        npc.equip(2,CraftItemStack.asNMSCopy(player.getInventory().getLeggings()));
        npc.equip(3,CraftItemStack.asNMSCopy(player.getInventory().getChestplate()));
        npc.equip(4,CraftItemStack.asNMSCopy(player.getInventory().getHelmet()));

        if(player.isSneaking()){
            npc.setCrouch();
        }

        if(playerNMS.isBurning()){
            npc.setOnFire();
        }

        npc.spawn();
        npc.setCamera(player,true);
        playerNPC.put(player,npc);


    }

    public void launchTimer(){
        for(Player player : freezedPlayers.values()){
            for(Player opposant : freezedPlayers.values()){
                if(player != opposant){
                    EntityPlayer playerNMS = ((CraftPlayer) opposant).getHandle();
                    GameProfile profile = playerNMS.getProfile();
                    Property property = profile.getProperties().get("textures").iterator().next();
                    String texture = property.getValue();
                    String signature = property.getSignature();

                    NPC npc = new NPC(player,opposant.getDisplayName(),opposant.getLocation());
                    npc.changeSkin(texture,signature);

                    npc.addToTablist();
                    npc.rmvFromTablist();

                    npc.equip(0, CraftItemStack.asNMSCopy(player.getItemInHand()));
                    npc.equip(1,CraftItemStack.asNMSCopy(player.getInventory().getBoots()));
                    npc.equip(2,CraftItemStack.asNMSCopy(player.getInventory().getLeggings()));
                    npc.equip(3,CraftItemStack.asNMSCopy(player.getInventory().getChestplate()));
                    npc.equip(4,CraftItemStack.asNMSCopy(player.getInventory().getHelmet()));

                    if(opposant.isSneaking()){
                        npc.setCrouch();
                    }

                    if(playerNMS.isBurning()){
                        npc.setOnFire();
                    }

                    npc.spawn();
                    npcs.add(npc);
                }
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                onStop.a();
                for(NPC npc : npcs){
                    npc.destroy();
                }

                for(Player player : freezedPlayers.values()){
                    NPC npc = playerNPC.get(player);
                    npc.setCamera(player, false);
                    npc.destroy();
                    player.setFlying(false);
                    player.setAllowFlight(false);
                    player.setFlySpeed(0.2f);
                }
            }
        }.runTaskLaterAsynchronously(InazumaUHC.get,i*20L);
    }

    public interface onStop{
        public void a();
    }
}
