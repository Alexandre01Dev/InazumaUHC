package be.alexandre01.inazuma.uhc.teams;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.generations.Plateform;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class Team {
    private HashMap<UUID, Player> players;
    private Plateform plateform;
    private IPreset p;
    private Location l;
    public Team(){
        players =  new HashMap<>();
        p = Preset.instance.p;
    }

    public void addPlayer(Player player){
        players.put(player.getUniqueId(),player);
    }
    public void rmvPlayer(Player player){
        players.put(player.getUniqueId(),player);
    }

    public HashMap<UUID, Player> getPlayers() {
        return players;
    }

    public void teleport(){
        Random rand1 = new Random();
        int size = p.getBorderSize(World.Environment.NORMAL);
        int x = rand1.nextInt(size - ((-size) + 1)) + (-size);
        Random rand2 = new Random();
        int z = rand2.nextInt(size - ((-size) + 1)) + (-size);


        plateform = new Plateform(Plateform.PlateformType.SQUARE,x,120,z);
        plateform.setSquare(3,3);
        plateform.addRegisterPlateform();
        plateform.spawn();

        World w = InazumaUHC.get.worldGen.defaultWorld;
        this.l = new Location(w,x,121.001,z);
        for(Player player : getPlayers().values()){
            player.teleport(l);
        }

    }

    public Plateform getPlateform() {
        return plateform;
    }


    public Location getLocation() {
        return l;
    }
}
