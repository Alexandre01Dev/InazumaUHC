package be.alexandre01.inazuma.uhc.host.gui;

import be.alexandre01.inazuma.uhc.host.Host;
import be.alexandre01.inazuma.uhc.host.option.HostButton;
import be.alexandre01.inazuma.uhc.host.option.HostContainer;
import be.alexandre01.inazuma.uhc.host.option.HostOption;
import be.alexandre01.inazuma.uhc.host.option.VarType;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class HostOptionsGui extends TGUI{
    private Inventory inv;
    private int precedent = -1;
    private HostContainer hostContainer;
    private ArrayList<ModifierGUI> modifierGUIS = new ArrayList<>();
    private HashMap<HostOption,Integer > hostOptions = new HashMap<>();
    public HostContainer getHostContainer() {
        return hostContainer;
    }

    public HostOptionsGui(HostContainer hostContainer){
        super(Type.OPTIONS);

        this.hostContainer = hostContainer;
        this.inv = Bukkit.createInventory(null,45,hostContainer.getName());

        for(Integer n : hostContainer.getHostOptions().keySet()){
            HostButton hostButton = hostContainer.getHostOptions().get(n);
            if(hostButton instanceof HostOption){
                HostOption hostOption = (HostOption) hostButton;
                if(!hostOption.getVarType().equals(VarType.FASTBOOL)){
                    ModifierGUI modifierGUI = new ModifierGUI(hostOption);
                    hostOption.setModifierGUI(modifierGUI);
                    modifierGUIS.add(modifierGUI);
                    hostOptions.put(hostOption,n);
                    hostOption.addHostOption(this);
                    inv.setItem(n,hostOption.getItemStack());

                }else {
                    hostOptions.put(hostOption,n);
                    hostOption.addHostOption(this);
                    inv.setItem(n,hostOption.getItemStack());

                }
            }
            if(hostButton instanceof HostContainer){
                inv.setItem(n,((HostContainer) hostButton).getItemStack());
            }

        }
        ItemBuilder last = new ItemBuilder(Material.ARROW);
        last.setName("Précédent");
        inv.setItem(44-8,last.toItemStack());
        setLastArrow(44-8);

        setInv(inv);
    }


    public Inventory getInv() {
        return inv;
    }

    public HashMap<HostOption, Integer> getHostOptions() {
        return hostOptions;
    }
    public void update(int i, ItemStack itemStack){
        inv.setItem(i,itemStack);
    }
}
