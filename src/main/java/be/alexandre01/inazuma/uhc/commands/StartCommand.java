package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.generations.chunks.ChunksGenerator;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.state.State;
import be.alexandre01.inazuma.uhc.timers.Timer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class StartCommand implements CommandExecutor{

        public StartCommand(){

        }
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
            if(sender instanceof Player){
                Player player = (Player) sender;
                if(cmd.getName().equalsIgnoreCase("start")){
                    if(GameState.get().contains(State.WAITING)){
                        player.sendMessage("§7Lancement de la §apartie§7...");
                        player.sendMessage("§7Téléportation des §ejoueurs§7.");
                        for(Timer timer : InazumaUHC.get.tm.timers.values()){
                            if(timer.isRunning){
                                System.out.println(timer.getTimerName());
                                timer.cancel();
                            }
                        }
                        GameState.get().setTo(State.STARTING);
                    }
                    else{
                        if (GameState.get().contains(State.PREPARING)) {
                            player.sendMessage("§7Vous n'avez pas fait la §cprégénération§7 de la map ! (§c/pregen§7)");
                        }
                        else{
                            player.sendMessage("§7La partie est déja en cours !");
                        }
                    }
                    }
                }
            return false;
        }
    }

