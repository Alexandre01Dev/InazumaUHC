package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.generations.chunks.ChunksGenerator;
import be.alexandre01.inazuma.uhc.listeners.host.InventoryClick;
import be.alexandre01.inazuma.uhc.listeners.host.InventoryClose;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.state.State;
import be.alexandre01.inazuma.uhc.timers.Timer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor{


        public StartCommand(){

        }
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
            if(sender instanceof Player){
                Player player = (Player) sender;
                if(cmd.getName().equalsIgnoreCase("start")){
                    if(GameState.get().contains(State.PREPARING)){
                        if(InazumaUHC.get.worldGen.isGenerating()){
                            player.sendMessage("§7La §cprégénération §7est entrain de s'effectuer.");
                            return true;
                        }
                        InazumaUHC.get.lm.removeListener(InventoryClick.class);
                        InazumaUHC.get.lm.removeListener(InventoryClose.class);

                        player.sendMessage("§7Lancement de la §cprégénération§7.");
                        if(!InazumaUHC.get.loadWorldBefore){
                            InazumaUHC.get.worldGen.gen();
                        }else {
                            ChunksGenerator c = new ChunksGenerator();
                            World world = InazumaUHC.get.worldGen.defaultWorld;
                            c.generate(world.getChunkAt(0,0),(Preset.instance.p.getBorderSize(world.getEnvironment())/16)+InazumaUHC.get.getServer().getViewDistance()+5,true);
                            InazumaUHC.get.worldGen.defaultWorldLoaded();
                        }

                        return true;
                    }
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
                    }
                }


            return false;
        }
    }

