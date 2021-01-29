package be.alexandre01.inazuma.uhc.host.gui;

import be.alexandre01.inazuma.uhc.host.Host;
import be.alexandre01.inazuma.uhc.host.option.HostContainer;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class HostGUI extends TGUI{
    private Inventory inv;
    public HostGUI(Host host){
        super(Type.HOME);
        inv = Bukkit.createInventory(null,18,"HOST");
        for(HostContainer hostContainer : host.getHostContainers().values()){
            inv.setItem(hostContainer.getSlot(),hostContainer.getItemStack());
        }
        setInv(inv);
    }

    public Inventory getInv() {
        return inv;
    }
}
