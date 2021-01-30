package be.alexandre01.inazuma.uhc.host.gui;

import be.alexandre01.inazuma.uhc.host.option.VarType;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class TGUI {
    private Inventory inv;
    private int lastArrow = -1;
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

    public int getLastArrow() {
        return lastArrow;
    }

    public void setLastArrow(int lastArrow) {
        this.lastArrow = lastArrow;
    }

    public static enum Type{
        HOME,OPTIONS,MODIFIERS;
    }
}
