package be.alexandre01.inazuma.uhc.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class PatchedEntity {
    public static void damage(LivingEntity entity, int damage){
        EntityDamageEvent entityDamageEvent = new EntityDamageEvent(entity, EntityDamageEvent.DamageCause.CUSTOM,damage);
        Bukkit.getPluginManager().callEvent(entityDamageEvent);
        if(!entityDamageEvent.isCancelled()){
            entity.damage(damage);
        }
    }
    public static void damage(LivingEntity entity, int damage, EntityDamageEvent.DamageCause damageCause){
        EntityDamageEvent entityDamageEvent = new EntityDamageEvent(entity, damageCause,damage);
        Bukkit.getPluginManager().callEvent(entityDamageEvent);
        if(!entityDamageEvent.isCancelled()){
            entity.damage(damage);
        }
    }
}
