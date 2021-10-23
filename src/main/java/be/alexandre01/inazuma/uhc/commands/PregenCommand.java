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

public class PregenCommand implements CommandExecutor{


    public PregenCommand(){

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("pregen")){
                if(GameState.get().contains(State.PREPARING)){
                    if(InazumaUHC.get.worldGen.isGenerating()){
                        player.sendMessage("§7La §cprégénération §7est entrain de s'effectuer.");
                        return true;
                    }
                    InazumaUHC.get.lm.removeListener(InventoryClickEvent.class);
                    InazumaUHC.get.lm.removeListener(InventoryCloseEvent.class);

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
            }
            else{
                player.sendMessage("§7Vous avez déja fait la §cprégénération§7 de la map !");
            }
        }
        return false;
    }
}

