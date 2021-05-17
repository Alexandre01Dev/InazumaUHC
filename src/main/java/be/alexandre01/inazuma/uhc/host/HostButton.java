package be.alexandre01.inazuma.uhc.host;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HostButton {
    private ItemStack itemStack;


    public HostButton(ItemStack itemStack){
        this.itemStack = itemStack;
    }
    public enum Type{
        REDIRECTION,OPTION,DIRECT;
    }

    public interface action{
        public void onClick(Player player);
    }
}
