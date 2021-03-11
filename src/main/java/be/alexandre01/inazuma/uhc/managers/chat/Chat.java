package be.alexandre01.inazuma.uhc.managers.chat;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import lombok.Builder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Chat {
    private HashMap<UUID,String> interlocuters;
    private ArrayList<Chat> accessors;
    private String chatName = "Chat";
    private String separator = ">";
    private String messageColor = "§b";
    private String prefixColor = "§b";

    @Builder
    public  Chat(String chatName,String separator,String message, String prefixColor){
        interlocuters = new HashMap<>();
        accessors = new ArrayList<>();
        this.chatName = chatName;
        this.separator = separator;
        this.prefixColor = prefixColor;
        this.messageColor = message;
    }



    public void add(UUID uuid, String nickname){
        interlocuters.put(uuid,nickname);
    }

    public void addAccessors(Chat chat){
        accessors.add(chat);
    }

    public void sendMessage(UUID uuid,String message){
        for(UUID uuids : interlocuters.keySet()){
            Player p = Bukkit.getPlayer(uuids);

            if(p == null)
                continue;

            p.sendMessage("§b["+chatName+"] | "+interlocuters.get(uuid)+" > " + message);
        }

        for(Chat chat : accessors){
            for(UUID uuids : chat.interlocuters.keySet()){
                Player p = Bukkit.getPlayer(uuids);

                if(p == null)
                    continue;

                p.sendMessage(prefixColor+"["+chatName+prefixColor+"] §e| " + prefixColor+interlocuters.get(uuid)+" "+separator + messageColor+ message);
            }
        }

        for(Player player : InazumaUHC.get.spectatorManager.getPlayers()){
            player.sendMessage(prefixColor+"["+chatName+prefixColor+"] §e| " + prefixColor+interlocuters.get(uuid)+" "+separator + messageColor+ message);
        }
    }
}
