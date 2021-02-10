package be.alexandre01.inazuma.uhc.roles;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RoleItem {
    private ItemStack itemStack;
    private RightClick rightClick;
    private RightClickOnPlayer rightClickOnPlayer;
    private LeftClick leftClick;
    private int slot = 8;

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setItemstack(ItemStack itemStack){
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public RightClick getRightClick() {
        return rightClick;
    }

    public void setRightClick(RightClick rightClick) {
        this.rightClick = rightClick;
    }

    public LeftClick getLeftClick() {
        return leftClick;
    }

    public RightClickOnPlayer getRightClickOnPlayer() {
        return rightClickOnPlayer;
    }

    public void setLeftClick(LeftClick leftClick) {
        this.leftClick = leftClick;
    }

    public void setRightClickOnPlayer(RightClickOnPlayer rightClickOnPlayer) {
        this.rightClickOnPlayer = rightClickOnPlayer;
    }

    public interface RightClick{
         void a(Player player);
    }

    public interface RightClickOnPlayer{
         void a(Player player,Player rightClicked);
    }

    public interface LeftClick{
         void a(Player player);
    }

    
}
