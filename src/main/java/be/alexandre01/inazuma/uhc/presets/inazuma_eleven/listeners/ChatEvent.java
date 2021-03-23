package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.listeners;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.state.State;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    @EventHandler
    public void onChating(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        String message = e.getMessage();

        if (!GameState.get().contains(State.PREPARING)){

            if (!player.hasPermission("uhc.chatmessage")){
                e.setCancelled(true);
                player.sendMessage(Preset.instance.p.prefixName() + "Le chat est §c§ldésactivé");
            }


        }
    }

}
