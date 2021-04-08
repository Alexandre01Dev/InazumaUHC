package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.Preset;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class HRTPCommand extends Command {
    public HRTPCommand(String s) {
        super(s);
    }

    @Override
    public boolean execute(CommandSender sender, String msg, String[] args) {
        if(!sender.hasPermission("host.hrtp")){
            return false;
        }
        if(args.length == 0){
            sender.sendMessage("/hrtp [Player]");
        }

        Player player = Bukkit.getPlayer(args[0]);
        if(player == null){
            sender.sendMessage("Le joueur est invalide");
            return false;
        }

        teleportRandomFromRange(player,50,200);
        InazumaUHC.get.noFallDamager.addPlayer(player, 1000*7);
        player.sendMessage(Preset.instance.p.prefixName()+" §eVous avez 7 secondes de no fall damage.");

        return false;
    }


    public void teleportRandomFromRange(Player player,int betweenRange ,int range){
        int rangeX = range;
        int rangeZ = range;

        int bRangeX = betweenRange;
        int bRangeZ = betweenRange;
        Location loc = player.getLocation();
        if(!new Random().nextBoolean()){
            rangeX = rangeX*-1;
            bRangeX = bRangeX*-1;
        }

        if(!new Random().nextBoolean()){
            rangeZ = rangeZ*-1;
            bRangeZ = bRangeZ*-1;
        }

        int xMax = loc.getBlockX()+rangeX;
        int xMin = xMax-bRangeX;
        int zMax = loc.getBlockZ()+rangeZ;
        int zMin = zMax-bRangeZ;



        Random rand1 = new Random();
        int x = rand1.nextInt((xMax - xMin) + 1) + xMin;
        Random rand2 = new Random();
        int z = rand2.nextInt((zMax - zMin) + 1) + zMin;

        World w = player.getWorld();
        Location l = new Location(w,x,121.001,z);

        player.teleport(l);
    }
}
