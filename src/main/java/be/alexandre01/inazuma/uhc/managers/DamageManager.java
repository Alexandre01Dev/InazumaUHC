package be.alexandre01.inazuma.uhc.managers;

import be.alexandre01.inazuma.uhc.timers.utils.DateBuilderTimer;
import be.alexandre01.inazuma.uhc.timers.utils.MSToSec;
import net.minecraft.server.v1_8_R3.AttributeModifiable;
import net.minecraft.server.v1_8_R3.AttributeModifier;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class DamageManager {
    private final HashMap<Player/*Killed*/,Tuple<Player,Long> /*Damager*/> playersDamager;
    private final HashMap<Player/*Damager*/,Player/*Killed*/> damagersPlayer;


    private final HashMap<Player, Double> increased_damage;
    private final HashMap<Player, Integer> increased_damage_level;
    private final HashMap<Player, Double> resistance;
    private final HashMap<Player, Integer> resistance_level;
    public DamageManager(){
        increased_damage = new HashMap<>();
        increased_damage_level = new HashMap<>();
        resistance = new HashMap<>();
        resistance_level = new HashMap<>();
        playersDamager = new HashMap<>();
        damagersPlayer = new HashMap<>();
    }

    public void addPlayerDamage(Player player,Player target){
        playersDamager.put(target,new Tuple<>(player,new Date().getTime()));
        damagersPlayer.put(player,target);
    }

    public void addEffectPourcentage(Player player,EffectType effectType,int level, int pourcentage){
        switch (effectType){
            case RESISTANCE:
                resistance.put(player,pourcentage/100D);
                resistance_level.put(player,level);
                break;
            case INCREASE_DAMAGE:
                increased_damage.put(player,pourcentage/100D);
                increased_damage_level.put(player,level);
                break;
        }
    }

    public double getEffectPourcentage(Player player,EffectType effectType){
        switch (effectType){
            case RESISTANCE:
                if(resistance.containsKey(player)){
                    return resistance.get(player);
                }
                return 0.1d;
            case INCREASE_DAMAGE:
                if(increased_damage.containsKey(player)){
                    return increased_damage.get(player);
                }
        }
        return 1;
    }

    public int getLevel(Player player,EffectType effectType){
        switch (effectType){
            case RESISTANCE:
                if(resistance.containsKey(player)){
                    return resistance_level.get(player);
                }
            case INCREASE_DAMAGE:
                if(increased_damage.containsKey(player)){
                    return increased_damage_level.get(player);
                }
        }
        return 1;
    }
    public Player getKiller(Player player){
        if(playersDamager.containsKey(player)){
            Tuple<Player,Long> t = playersDamager.get(player);
            long calc = new Date().getTime()-t.b();
            if(MSToSec.toSec(calc) <= 60){
                return t.a();
            }
        }
        return null;
    }

    public enum EffectType{
        INCREASE_DAMAGE,RESISTANCE;
    }
}
