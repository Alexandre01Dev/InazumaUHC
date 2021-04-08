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
        Player player;
        try {
            player = Bukkit.getPlayer(args[0]);
        }catch (Exception e){
            sender.sendMessage("Le joueur est invalide");
            return false;
        }

        teleportRandomFromRange(player,50,200);
        InazumaUHC.get.noFallDamager.addPlayer(player, 1000*7);
        player.sendMessage(Preset.instance.p.prefixName()+ " §c§lVous venez d'être téléporté par un modérateur.");
        player.sendMessage(Preset.instance.p.prefixName()+" §eVous avez 7 secondes de no fall damage.");

        return false;
    }


    public void teleportRandomFromRange(Player player,int betweenRange ,int range){
        int size = (int) (player.getWorld().getWorldBorder().getSize()/2);
        int rangeX = range;
        int rangeZ = range;

        int bRangeX = betweenRange;
        int bRangeZ = betweenRange;
        Location loc = player.getLocation();




        int xMax = loc.getBlockX()+rangeX;
        int xMin = xMax-bRangeX;
        int zMax = loc.getBlockZ()+rangeZ;
        int zMin = zMax-bRangeZ;
        System.out.println("Xmax "+xMax+" | Xmin "+ xMin);
        System.out.println("Zmax "+zMax+" | Zmin "+ zMin);


        Random rand1 = new Random();
        int x = rand1.nextInt((xMax-xMin)+1) + xMax;
        System.out.println("RX "+ x);
        if(!new Random().nextBoolean()){
           x = x*-1;
        }
        System.out.println("RXR "+ x);
        if (Math.abs(x) > Math.abs(size)){
            int s = size-20;

            if(!new Random().nextBoolean()){
                s = s*-1;
            }
            System.out.println("more");
            x = s;
        }
        System.out.println("RXF "+ x);
        Random rand2 = new Random();

        int z = rand2.nextInt((zMax-zMin)+1) + zMin;
        System.out.println("RZ "+ z);
        if(!new Random().nextBoolean()){
            z = z*-1;
        }
        System.out.println("RZR "+ x);
        if (Math.abs(z) > Math.abs(size)){
            int s = size-20;

            if(!new Random().nextBoolean()){
                s = s*-1;
            }

            z = s;
            System.out.println("more");
        }
        System.out.println("RZF "+ x);
        World w = player.getWorld();
        Location l = new Location(w,x,121.001,z);

        player.teleport(l);
    }
}
