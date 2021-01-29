package be.alexandre01.inazuma.uhc.host.gui;

import be.alexandre01.inazuma.uhc.host.Host;
import be.alexandre01.inazuma.uhc.host.option.HostContainer;
import be.alexandre01.inazuma.uhc.host.option.HostOption;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class HostOptionsGui extends TGUI{
    private Inventory inv;
    private int precedent = -1;
    private HostContainer hostContainer;
    private ArrayList<ModifierGUI> modifierGUIS = new ArrayList<>();

    public HostContainer getHostContainer() {
        return hostContainer;
    }

    public HostOptionsGui(HostContainer hostContainer){
        super(Type.OPTIONS);
        this.hostContainer = hostContainer;
        this.inv = Bukkit.createInventory(null,18,hostContainer.getName());
        int i = 0;
        for(HostOption hostOption : hostContainer.getHostOptions()){
            ModifierGUI modifierGUI = new ModifierGUI(hostOption);
            hostOption.setModifierGUI(modifierGUI);
            modifierGUIS.add(modifierGUI);
            inv.setItem(i,hostOption.getItemStack());
            i++;
        }
        ItemBuilder last = new ItemBuilder(Material.ARROW);
        last.setName("Précédent");
        inv.setItem(17-8,last.toItemStack());
        precedent = 17-8;

        setInv(inv);
    }

    public Inventory getInv() {
        return inv;
    }
}
