package be.alexandre01.inazuma.uhc.host;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.config.LanguageData;
import be.alexandre01.inazuma.uhc.config.Messages;
import be.alexandre01.inazuma.uhc.host.gui.WorkingPlace;
import be.alexandre01.inazuma.uhc.host.gui.menus.Home;
import be.alexandre01.inazuma.uhc.listeners.host.ClickInventory;
import be.alexandre01.inazuma.uhc.listeners.host.CloseInventory;
import be.alexandre01.inazuma.uhc.modules.Module;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.PresetData;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import be.alexandre01.inazuma.uhc.utils.fastinv.FastInv;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
@Getter
public class Host {
    private HashMap<Class<?>,WorkingPlace> menus = new HashMap<>();
    private HashMap<Player, WorkingPlace> workingPlaces = new HashMap<>();
    private HashMap<Player, FastInv> instances = new HashMap<>();
    private FastInv defaultInv;
    @Getter
    private static Host instance;
    private WorkingPlace home;
    public Host(){
        instance = this;
         defaultInv = new FastInv(9*6,"Menu de §bHost");
         home = new Home(defaultInv);
         InazumaUHC.get.lm.addListener(new ClickInventory());
         InazumaUHC.get.lm.addListener(new CloseInventory());

    }

    public void createHostPanel(Player player){
        if(!home.getInvs().containsKey(player)){
            home.addInstance(player);
        }

        FastInv inv = home.getInvs().get(player);
        instances.put(player,inv);
        workingPlaces.put(player,home);
        inv.open(player);
    }

    public void addMenu(WorkingPlace workingPlace){
        menus.put(workingPlace.getClass(),workingPlace);
    }

    public WorkingPlace getMenu(Class<?> classMenu){
        return menus.get(classMenu);
    }
}