package be.alexandre01.inazuma.uhc.managers.chat;

import java.util.HashMap;

public class ChatManager {
    private HashMap<String,Chat> chats = new HashMap<>();
    private ChatManager(){
        chats = new HashMap<>();
    }
    public Chat addChat(String chatName,Chat chat){
        return chats.put(chatName,chat);
    }
    public Chat getChat(String chat){
        return chats.get(chat);
    }


}
