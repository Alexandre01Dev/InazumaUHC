package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.state.State;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MuteAllCommand extends Command {
    public boolean chat = true;
    public MuteAllCommand(String s) {
        super(s);
        super.setPermission("uhc.muteall");
    }

    @Override
    public boolean execute(CommandSender sender, String msg, String[] args) {
        if(!sender.hasPermission("uhc.heal")){
            sender.sendMessage(super.getPermissionMessage());
            return false;
        }
        if(args.length == 0){
            sender.sendMessage(Preset.instance.p.prefixName()+ " §cFaites /muteall on ou /muteall off");
        }

        if(args[0].equalsIgnoreCase("off")){
            if(!chat){
                sender.sendMessage(Preset.instance.p.prefixName() + "§7le chat est déja §cdésactiver§7.");
                return false;
            }
            Bukkit.broadcastMessage(Preset.instance.p.prefixName() + "§7le chat vient d'être §cdésactiver§7.");
            chat=false;
            return false;
        }
        if(args[0].equalsIgnoreCase("on")){
            if(chat){
                sender.sendMessage(Preset.instance.p.prefixName() + "§7le chat est déja §aactiver§7.");
                return false;
            }
            Bukkit.broadcastMessage(Preset.instance.p.prefixName() + "§7le chat vient d'être §aactiver§7.");
            chat=true;
            return false;
        }
        return false;
    }

    @EventHandler
    public void onChating(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        String message = e.getMessage();

        if (!GameState.get().contains(State.PREPARING) && !GameState.get().contains(State.WAITING)){
            e.setCancelled(true);
            player.sendMessage(Preset.instance.p.prefixName() + "Le chat est §c§ldésactivé");
            return;
        }
        else{
            if(!chat){
                e.setCancelled(true);
                player.sendMessage(Preset.instance.p.prefixName() + "Le chat est §c§ldésactivé");
                return;
            }
        }

    }

}
