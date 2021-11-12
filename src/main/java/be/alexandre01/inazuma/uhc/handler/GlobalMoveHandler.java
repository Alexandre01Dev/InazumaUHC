package be.alexandre01.inazuma.uhc.handler;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.imanity.imanityspigot.ImanitySpigot;
import org.imanity.imanityspigot.movement.MovementHandler;
import org.imanity.imanityspigot.packet.wrappers.MovementPacketWrapper;

import java.util.ArrayList;
import java.util.List;

public class GlobalMoveHandler implements MovementHandler {
    final List<MoveHandler> moveHandlerList = new ArrayList<>();
    boolean isInit = false;


    public void addMoveHandler(MoveHandler moveHandler){
        moveHandlerList.add(moveHandler);
        if(!isInit){
            InazumaUHC.get.getServer().imanity().registerMovementHandler(InazumaUHC.get, this);
            isInit = true;
        }

    }

    public void removeMoveHandler(MoveHandler moveHandler){
        moveHandlerList.remove(moveHandler);
        if (moveHandlerList.isEmpty()){
            InazumaUHC.get.getServer().imanity().unregisterMovementHandler(this);
        }
    }
    @Override
    public void onUpdateLocation(Player player, Location from, Location to, MovementPacketWrapper movementPacketWrapper) {
        if(from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()){
            synchronized (moveHandlerList){
                moveHandlerList.forEach(moveHandler -> moveHandler.onMove(player,to));
            }
        }
    }

    @Override
    public void onUpdateRotation(Player player, Location from, Location to, MovementPacketWrapper movementPacketWrapper) {
    }

    public interface MoveHandler{
        void onMove(Player player,Location location);
    }
}
