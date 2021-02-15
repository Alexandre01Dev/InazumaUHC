package be.alexandre01.inazuma.uhc.roles;

import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RoleItem {
    private ItemStack itemStack;
    private RightClick rightClick;
    private Role LinkedRole;
    private RightClickOnPlayer rightClickOnPlayer = null;
    private Tuple<Integer,RightClickOnPlayer> rightClickOnPlayerFarTuple = null;
    private LeftClick leftClick;
    private VerificationOnRightClick verificationOnRightClick = null;
    private VerificationOnLeftClick verificationOnLeftClick = null;
    private VerificationOnRightClickOnPlayer verificationOnRightClickOnPlayer = null;
    private int slot = 8;

    public int getSlot() {
        return slot;
    }

    public Role getLinkedRole() {
        return LinkedRole;
    }

    public void setLinkedRole(Role linkedRole) {
        LinkedRole = linkedRole;
    }

    public VerificationOnRightClick getVerificationOnRightClick() {
        return verificationOnRightClick;
    }

    public void setVerificationOnRightClick(VerificationOnRightClick verificationOnRightClick) {
        this.verificationOnRightClick = verificationOnRightClick;
    }

    public VerificationOnLeftClick getVerificationOnLeftClick() {
        return verificationOnLeftClick;
    }

    public void setVerificationOnLeftClick(VerificationOnLeftClick verificationOnLeftClick) {
        this.verificationOnLeftClick = verificationOnLeftClick;
    }

    public VerificationOnRightClickOnPlayer getVerificationOnRightClickOnPlayer() {
        return verificationOnRightClickOnPlayer;
    }

    public void setVerificationOnRightClickOnPlayer(VerificationOnRightClickOnPlayer verificationOnRightClickOnPlayer) {
        this.verificationOnRightClickOnPlayer = verificationOnRightClickOnPlayer;
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

    public Tuple<Integer, RightClickOnPlayer> getRightClickOnPlayerFarTuple() {
        return rightClickOnPlayerFarTuple;
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
    public void setRightClickOnPlayer(int reach,RightClickOnPlayer rightClickOnPlayerFar) {
        this.rightClickOnPlayerFarTuple = new Tuple<>(reach,rightClickOnPlayerFar);
    }

    public interface VerificationOnRightClick{
        boolean verification(Player player);
    }

    public interface VerificationOnRightClickOnPlayer{
        boolean verification(Player player,Player rightClicked);
    }

    public interface VerificationOnLeftClick{
        boolean verification(Player player);
    }
    public interface RightClick{
         void execute(Player player);
    }

    public interface RightClickOnPlayer{
         void execute(Player player,Player rightClicked);
    }


    public interface LeftClick{
         void execute(Player player);
    }

    
}
