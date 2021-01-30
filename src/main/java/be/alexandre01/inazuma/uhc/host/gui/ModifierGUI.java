package be.alexandre01.inazuma.uhc.host.gui;

import be.alexandre01.inazuma.uhc.host.option.HostOption;
import be.alexandre01.inazuma.uhc.host.option.VarType;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ModifierGUI extends TGUI{
    Inventory inv;

    HostOption hostOption;
    public ModifierGUI(HostOption hostOption){
        super(Type.MODIFIERS);
        inv = Bukkit.createInventory(null,18,hostOption.getVarName());
        this.hostOption = hostOption;

        if(hostOption.getVarType().equals(VarType.INTEGER)){
            int h = (int) hostOption.getValue();
            ItemBuilder m = new ItemBuilder(Material.WOOL);

            m.setWoolColor(DyeColor.RED);
            m.setName("-");
            m.setLore(""+h);
            inv.setItem(4,m.toItemStack());
            ItemBuilder p = new ItemBuilder(Material.WOOL);
            p.setWoolColor(DyeColor.GREEN);
            p.setName("+");
            inv.setItem(7,p.toItemStack());
        }
        if(hostOption.getVarType().equals(VarType.BOOLEAN)){
            boolean h = (boolean) hostOption.getValue();
            ItemBuilder m = new ItemBuilder(Material.WOOL);

            m.setWoolColor(DyeColor.RED);
            m.setName("Désactivé");
            m.setLore(""+h);
            inv.setItem(4,m.toItemStack());
            ItemBuilder p = new ItemBuilder(Material.WOOL);
            p.setWoolColor(DyeColor.GREEN);
            p.setName("Activé");
            inv.setItem(7,p.toItemStack());
        }
        ItemBuilder last = new ItemBuilder(Material.ARROW);
        last.setName("Précédent");
        inv.setItem(17-8,last.toItemStack());
        setLastArrow(17-8);
        setInv(inv);
    }


    @Override
    public Inventory getInv() {
        return inv;
    }

    @Override
    public void setInv(Inventory inv) {
        this.inv = inv;
    }

    public HostOption getHostOption() {
        return hostOption;
    }

    public void setRange(int range){

    }

    public void setHostOption(HostOption hostOption) {
        this.hostOption = hostOption;
    }
}
