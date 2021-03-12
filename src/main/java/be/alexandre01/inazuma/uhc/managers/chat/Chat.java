package be.alexandre01.inazuma.uhc.managers.chat;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import lombok.Builder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
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
        sendMessageExceptUUID(uuid,message);
    }
    public void sendMessageExceptUUID(UUID uuid,String message,UUID... pUuids){
        for(UUID uuids : interlocuters.keySet()){
            Player p = Bukkit.getPlayer(uuids);
            if(Arrays.asList(pUuids).contains(uuids)){
                continue;
            }
            if(p == null)
                continue;

            p.sendMessage("§7["+chatName+prefixColor+"§7] §e| " + prefixColor+interlocuters.get(uuid)+" "+separator + messageColor+ message);
        }

        for(Chat chat : accessors){
            for(UUID uuids : chat.interlocuters.keySet()){

                Player p = Bukkit.getPlayer(uuids);

                if(p == null)
                    continue;

                p.sendMessage("§7["+chatName+prefixColor+"§7] §e| " + prefixColor+interlocuters.get(uuid)+" "+separator + messageColor+ message);
            }
        }

        for(Player player : InazumaUHC.get.spectatorManager.getPlayers()){
            player.sendMessage("§7["+chatName+prefixColor+"§7] §e| " + prefixColor+interlocuters.get(uuid)+" "+separator + messageColor+ message);
        }
    }
    public String generateMessage(UUID uuid,String message){
        return   "§7["+chatName+prefixColor+"§7] §e| " + prefixColor+interlocuters.get(uuid)+" "+separator + messageColor+ message;
    }

}
