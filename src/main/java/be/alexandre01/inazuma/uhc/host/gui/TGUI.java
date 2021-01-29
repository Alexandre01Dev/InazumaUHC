package be.alexandre01.inazuma.uhc.host.gui;

import be.alexandre01.inazuma.uhc.host.option.VarType;
import org.bukkit.inventory.Inventory;

public class TGUI {
    private Inventory inv;
    private Type type = null;
    private VarType varType = null;
    public TGUI(Type type){
        this.type = type;
    }

    public Inventory getInv() {
        return inv;
    }

    public void setInv(Inventory inv) {
        this.inv = inv;
    }

    public Type getType() {
        return type;
    }



    public static enum Type{
        HOME,OPTIONS,MODIFIERS;
    }
}
