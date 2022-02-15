package be.alexandre01.inazuma.uhc.handler;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class GlobalMoveHandler implements Listener {
    final List<MoveHandler> moveHandlerList = new ArrayList<>();
    boolean isInit = false;


    public void addMoveHandler(MoveHandler moveHandler){
        moveHandlerList.add(moveHandler);
        if(!isInit){
            Bukkit.getPluginManager().registerEvents(this, InazumaUHC.get);
            isInit = true;
        }

    }

    public void removeMoveHandler(MoveHandler moveHandler){
        moveHandlerList.remove(moveHandler);
        if (moveHandlerList.isEmpty()){
            HandlerList.unregisterAll(this);
        }
    }

    public interface MoveHandler{
        void onMove(Player player,Location location);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Location from = e.getFrom();
        Location to = e.getTo();
        if(from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()){
            synchronized (moveHandlerList){
                moveHandlerList.forEach(moveHandler -> moveHandler.onMove(e.getPlayer(),to));
            }
        }
    }
}
