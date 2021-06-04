package be.alexandre01.inazuma.uhc.host.gui.menus;

import be.alexandre01.inazuma.uhc.host.HostButton;
import be.alexandre01.inazuma.uhc.host.gui.WorkingPlace;
import be.alexandre01.inazuma.uhc.utils.fastinv.FastInv;
import org.bukkit.Material;

public class Pregeneration extends WorkingPlace {
    public Pregeneration(FastInv fastInv) {
        super(fastInv);
        setColorVariations(ColorVariante.Brown,ColorVariante.DarkGreen);
        for (int i = getCase(2,2); i < getCase(8,4); i++) {
            
        }
        readyToSetClickableItems();
        
        HostButton defaultWorld = new HostButton(Material.GRASS,"Monde NORMAL", HostButton.Type.REDIRECTION);
        addButton(defaultWorld,9);
        HostButton netherWorld = new HostButton(Material.NETHERRACK,"Monde NETHER", HostButton.Type.REDIRECTION);
        addButton(netherWorld,11);
        HostButton endWorld = new HostButton(Material.ENDER_STONE,"§cMonde END (non-disponible)", HostButton.Type.NONE);
        addButton(endWorld,13);

    }


}
