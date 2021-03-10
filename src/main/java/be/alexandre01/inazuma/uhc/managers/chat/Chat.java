package be.alexandre01.inazuma.uhc.managers.chat;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Chat {
    private HashMap<UUID,String> interlocuters;
    private ArrayList<Chat> accessors;
    private String chatName;
    public void Chat(String chatName){
        interlocuters = new HashMap<>();
        accessors = new ArrayList<>();
        this.chatName = chatName;
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

            p.sendMessage("§b["+chatName+"] | "+interlocuters.get(uuid)+" >" + message);
        }

        for(Chat chat : accessors){
            for(UUID uuids : chat.interlocuters.keySet()){
                Player p = Bukkit.getPlayer(uuids);

                if(p == null)
                    continue;

                p.sendMessage("§b["+chatName+"] | "+interlocuters.get(uuid)+" >" + message);
            }
        }

        for(Player player : InazumaUHC.get.spectatorManager.getPlayers()){
            player.sendMessage("§b["+chatName+"] | "+interlocuters.get(uuid)+" >" + message);
        }
    }
}
