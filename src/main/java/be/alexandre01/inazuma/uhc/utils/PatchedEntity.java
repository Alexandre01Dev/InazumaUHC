package be.alexandre01.inazuma.uhc.utils;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class PatchedEntity{
    private static ArrayList<Integer> ids = new ArrayList<>();
    private static PacketHandler packetHandler;
    private static boolean register = false;
    public static void init(){
        packetHandler = new PacketHandler() {
            @Override
            public void onSend(SentPacket sentPacket) {

                if(sentPacket.getPacketName().equalsIgnoreCase("PacketPlayOutEntityStatus")){
                    System.out.println(sentPacket.getPacketName());
                    Object object1 = sentPacket.getPacketValue("a");

                    Object object2 = sentPacket.getPacketValue("b");
                    System.out.println("OBJ1"+object1);
                    System.out.println("OBJ2"+object2);
                    if((byte) object2 == (byte) 2){
                        if(ids.contains((Integer) object1)){
                            sentPacket.setCancelled(true);
                        }
                    }



                }
            }

            @Override
            public void onReceive(ReceivedPacket receivedPacket) {

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
            damage(entity,damage, EntityDamageEvent.DamageCause.CUSTOM);

            Bukkit.getScheduler().runTaskLater(InazumaUHC.get, new Runnable() {
                @Override
                public void run() {
                    ids.remove(Integer.valueOf(id));
                    if(ids.isEmpty()){
                        PacketListenerAPI.removePacketHandler(packetHandler);
                        register = false;
                    }
                }
            },2);
            return;
        }
        damage(entity,damage, EntityDamageEvent.DamageCause.CUSTOM);
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
}
