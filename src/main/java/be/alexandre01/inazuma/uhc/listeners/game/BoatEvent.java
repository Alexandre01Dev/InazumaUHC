package be.alexandre01.inazuma.uhc.listeners.game;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import org.bukkit.Location;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.*;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class BoatEvent implements Listener {
    private HashMap<Vehicle,Vector> lastVector = new HashMap<>();
    private HashMap<Vehicle,Integer> i = new HashMap<>();
    @EventHandler
    public void onVehicleEvent(VehicleDestroyEvent event){
        event.setCancelled(true);
    }
    @EventHandler
    public void onVehicleEnterEvent(VehicleEnterEvent event){
        if(event.getVehicle() instanceof Boat){
            i.put(event.getVehicle(),0);
        }
    }

    @EventHandler
    public void onVehicleEvent(VehicleBlockCollisionEvent event){
       /* Vehicle vehicle = event.getVehicle();
        if(vehicle instanceof Boat){
        for (Entity entity : event.getVehicle().getWorld().getNearbyEntities(event.getVehicle().getLocation(),2,1,2)){
            if(entity instanceof Player){
                Player player = (Player) entity;
                if(player != event.getVehicle().getPassenger()  && InazumaUHC.get.spectatorManager.getPlayers().contains(player)){
                    Location location = player.getLocation();
                    location.add(0,2,0);
                    player.teleport(location);
                    player.setVelocity(event.getVehicle().getVelocity().add(new Vector(0,1,0)));
                }
            }
        }
        }*/
        System.out.println("COLLISION BLOCK");
    }
    @EventHandler
    public void onVehicleEvent(VehicleUpdateEvent event){
      /*  Vehicle vehicle = event.getVehicle();
        if(vehicle instanceof Boat){
            if(event.getVehicle().getPassenger() instanceof Player){
                int l = i.get(vehicle)+1;
                if(l == 2){
                    for (Entity entity : event.getVehicle().getWorld().getNearbyEntities(event.getVehicle().getLocation(),2,1,2)){
                        if(entity instanceof Player){
                            Player player = (Player) entity;
                            if(player != event.getVehicle().getPassenger() && InazumaUHC.get.spectatorManager.getPlayers().contains(player)){
                                Location location = player.getLocation();
                                location.add(0,2,0);
                                player.teleport(location);
                                player.setVelocity(event.getVehicle().getVelocity().add(new Vector(0,2,0)));
                            }
                        }
                    }
                    l = 0;
                }
                i.put(vehicle,l);
                lastVector.put(event.getVehicle(),event.getVehicle().getVelocity());
                System.out.println("VEHICLE UPDATE");
            }
        }*/

    }

}
