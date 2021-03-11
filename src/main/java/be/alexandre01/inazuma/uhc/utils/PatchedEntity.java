package be.alexandre01.inazuma.uhc.utils;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class PatchedEntity {
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
        damage(entity,damage, EntityDamageEvent.DamageCause.CUSTOM);
        if(isSilent){
            PacketPlayOutAnimation status = new PacketPlayOutAnimation(((CraftEntity)entity).getHandle(), 1);
            for(Player player : Bukkit.getOnlinePlayers()){
                EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
                entityPlayer.playerConnection.sendPacket(status);
            }
        }
    }

    public static void setMaxHealthInSilent(LivingEntity entity,double maxHealth){
        if(entity.getHealth() > maxHealth){
            damage(entity,entity.getHealth()-maxHealth,true);
        }
        entity.setMaxHealth(maxHealth);
    }
}
