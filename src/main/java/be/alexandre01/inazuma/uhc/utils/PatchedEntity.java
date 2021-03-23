package be.alexandre01.inazuma.uhc.utils;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.timers.utils.DateBuilderTimer;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.inventivetalent.packetlistener.PacketListenerAPI;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class PatchedEntity{
    private static ArrayList<Integer> ids = new ArrayList<>();
    private static ArrayList<Integer> lids = new ArrayList<>();
    private static ArrayList<Location> locations = new ArrayList<>();
    private static ArrayList<String> sounds = new ArrayList<>();
    private static HashMap<Packet<?>,Integer> authorizedPackets = new HashMap<>();
    private static PacketHandler packetHandler;
    private static boolean register = false;
    public static void init(){
        packetHandler = new PacketHandler() {
            @Override
            public void onSend(SentPacket sentPacket) {
                if(authorizedPackets.containsKey((Packet<?>) sentPacket.getPacket())){
                    int i = authorizedPackets.get((Packet<?>) sentPacket.getPacket())-1;
                    if(i == 0){
                        authorizedPackets.remove((Packet<?>) sentPacket.getPacket());
                        return;
                    }
                    authorizedPackets.put((Packet<?>) sentPacket.getPacket(),i);


                    return;
                }
                if(sentPacket.getPacketName().equalsIgnoreCase("PacketPlayOutEntityStatus")){
                    Object object1 = sentPacket.getPacketValue("a");
                    Object object2 = sentPacket.getPacketValue("b");
                    if((byte) object2 == (byte) 2){
                        if(ids.contains((Integer) object1)){
                            sentPacket.setCancelled(true);
                        }
                    }
                }

                if(sentPacket.getPacketName().equalsIgnoreCase("PacketPlayInSpectate")){
                    if(sentPacket.getPlayer() != null){
                        System.out.println(sentPacket.getPlayer().getName());

                        if(!InazumaUHC.get.spectatorManager.getPlayers().contains(sentPacket.getPlayer()))
                            sentPacket.setCancelled(true);
                    }
                }
                if(sentPacket.getPacketName().equalsIgnoreCase("PacketPlayOutCamera")){
                    if(sentPacket.getPlayer() != null){
                        System.out.println(sentPacket.getPlayer().getName());

                        if(!InazumaUHC.get.spectatorManager.getPlayers().contains(sentPacket.getPlayer()))
                            sentPacket.setCancelled(true);
                    }
                }
                if(sentPacket.getPacketName().equalsIgnoreCase("PacketPlayOutNamedSoundEffect")){
                   if(sounds.contains(((String) sentPacket.getPacketValue("a")))){

                       for(Location location : locations){
                           if(Math.abs(location.getX()-((int)sentPacket.getPacketValue("b")/8D)) > 2){
                               return;
                           }
                           if(Math.abs(location.getY()-((int)sentPacket.getPacketValue("c")/8D)) > 2){
                               return;
                           }

                           if(Math.abs(location.getZ()-((int)sentPacket.getPacketValue("d")/8D)) > 2){
                               return;
                           }
                           if(!lids.contains(((CraftPlayer)sentPacket.getPlayer()).getHandle().getId())){
                               sentPacket.setCancelled(true);
                           }

                       }
                   }
                }
            }

            @Override
            public void onReceive(ReceivedPacket receivedPacket) {
                if(receivedPacket.getPacketName().equalsIgnoreCase("PacketPlayInSpectate")){
                    if(receivedPacket.getPlayer() != null){
                        System.out.println(receivedPacket.getPlayer().getName());

                        if(!InazumaUHC.get.spectatorManager.getPlayers().contains(receivedPacket.getPlayer()))
                            receivedPacket.setCancelled(true);
                    }
                }
            }

        };

        PacketListenerAPI.addPacketHandler(packetHandler);
        register = true;
    }
    public static void damage(LivingEntity entity, double damage){
       damage(entity,damage, EntityDamageEvent.DamageCause.CUSTOM);
    }
    public static void damage(LivingEntity entity, double damage, EntityDamageEvent.DamageCause damageCause){
        EntityDamageEvent entityDamageEvent = new EntityDamageEvent(entity, damageCause,damage);
        Bukkit.getPluginManager().callEvent(entityDamageEvent);
        if(!entityDamageEvent.isCancelled()){
            entity.damage(damage);
        }
    }


    public static void damage(LivingEntity entity, double damage, EntityDamageEvent.DamageCause damageCause, boolean isSilent){
     damage(entity,damage,damageCause);
     if(isSilent){
         PacketPlayOutAnimation status = new PacketPlayOutAnimation(((CraftEntity)entity).getHandle(), 1);
         for(Player player : Bukkit.getOnlinePlayers()){
             EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
             entityPlayer.playerConnection.sendPacket(status);
         }
     }
    }
    public static void damage(LivingEntity entity, double damage, boolean isSilent){
        if(isSilent){
            if(!register){
                PacketListenerAPI.addPacketHandler(packetHandler);
                register = true;
            }

            int id = ((CraftEntity)entity).getHandle().getId();
            ids.add(id);
            lids.add(id);
            sounds.add("game.player.hurt");
            Location location = entity.getLocation().clone();
            locations.add(location);
            damage(entity,damage, EntityDamageEvent.DamageCause.CUSTOM);

            Bukkit.getScheduler().runTaskLater(InazumaUHC.get, new Runnable() {
                @Override
                public void run() {
                    ids.remove(Integer.valueOf(id));
                    locations.remove(location);
                    lids.remove(Integer.valueOf(id));
                    sounds.remove("game.player.hurt");
                    if(ids.isEmpty() && lids.isEmpty()){
                        PacketListenerAPI.removePacketHandler(packetHandler);
                        register = false;
                    }
                }
            },2);
            return;
        }
        damage(entity,damage, EntityDamageEvent.DamageCause.CUSTOM);
    }

    public static void cancelSound(String s, Location location, LivingEntity... exceptions){
        cancelSound(s,location,2,exceptions);
    }
    public static void cancelSound(String s, Location location, long t, LivingEntity... exceptions){
        if(!register){
            PacketListenerAPI.addPacketHandler(packetHandler);
            register = true;
        }

        sounds.add(s);
        locations.add(location.clone());
         List<Integer> id = Arrays.stream(exceptions).map(Entity::getEntityId).collect(Collectors.toList());
        lids.addAll(id);
        Bukkit.getScheduler().runTaskLater(InazumaUHC.get, new Runnable() {
            @Override
            public void run() {
                locations.remove(location);
                lids.removeAll(id);
                sounds.remove(s);
                if(ids.isEmpty() && lids.isEmpty()){
                    PacketListenerAPI.removePacketHandler(packetHandler);
                    register = false;
                }
            }
        },t);
    }
    public static void setMaxHealthInSilent(LivingEntity entity,double maxHealth){

        if(entity.getHealth() > maxHealth){
            damage(entity,entity.getHealth()-maxHealth,true);
        }
        entity.setMaxHealth(maxHealth);
    }

    public static void setValue(Object obj,String name,Object value){
        try{
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        }catch(Exception e){}
    }

    public static Object getValue(Object obj,String name){
        try{
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj);
        }catch(Exception e){}
        return null;
    }

    public static void addAuthorizedPacket(Packet<?> packet, int life){
        authorizedPackets.put(packet,life);
    }
}
