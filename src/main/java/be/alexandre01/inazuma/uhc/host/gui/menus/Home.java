package be.alexandre01.inazuma.uhc.host.gui.menus;

import be.alexandre01.inazuma.uhc.host.HostButton;
import be.alexandre01.inazuma.uhc.host.gui.WorkingPlace;
import be.alexandre01.inazuma.uhc.utils.fastinv.FastInv;
import org.bukkit.Material;
import org.bukkit.Sound;

public class Home extends WorkingPlace {
    public Home(FastInv inv) {
        super(inv);
        readyToSetClickableItems();
        setSeparation(1,ColorVariante.Cyan);
        //Buttons
        HostButton lobby = new HostButton(Material.ITEM_FRAME,"Gestion de votre Lobby", HostButton.Type.REDIRECTION);
        lobby.playSound(Sound.CLICK,1f,1.4f);
        addButton(lobby);

        HostButton pregen = new HostButton(Material.GRASS,"Prégeneration", HostButton.Type.REDIRECTION);
        pregen.playSound(Sound.CLICK,1f,1.2f);
        addButton(pregen);

        HostButton mdj= new HostButton(Material.BEACON,"Modes de jeux", HostButton.Type.REDIRECTION);
        mdj.playSound(Sound.CLICK,1f,1.1f);
        addButton(mdj);

        HostButton scenarios= new HostButton(Material.REDSTONE_TORCH_ON,"Scénarios", HostButton.Type.REDIRECTION);
        scenarios.playSound(Sound.CLICK,1f,1.0f);
        addButton(scenarios);

    }
}
