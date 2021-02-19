package be.alexandre01.inazuma.uhc.managers;

import be.alexandre01.inazuma.uhc.timers.utils.DateBuilderTimer;
import be.alexandre01.inazuma.uhc.timers.utils.MSToSec;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.HashMap;

public class DamageManager {
    private final HashMap<Player/*Killed*/,Tuple<Player,Long> /*Damager*/> playersDamager;
    private final HashMap<Player/*Damager*/,Player/*Killed*/> damagersPlayer;
    public DamageManager(){
        playersDamager = new HashMap<>();
        damagersPlayer = new HashMap<>();
    }

    public void addPlayerDamage(Player player,Player target){
        playersDamager.put(target,new Tuple<>(player,new Date().getTime()));
        damagersPlayer.put(player,target);
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
}
