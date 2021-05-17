package be.alexandre01.inazuma.uhc.host;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.config.LanguageData;
import be.alexandre01.inazuma.uhc.config.Messages;
import be.alexandre01.inazuma.uhc.host.gui.WorkingPlace;
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
    private HashMap<Player, WorkingPlace> workingPlaces = new HashMap<>();
    @Getter
    private static Host instance;
    private WorkingPlace home;
    public void Host(){
        instance = this;
        FastInv inv = new FastInv(9*6,"Menu de Host");
         home = new WorkingPlace(inv);

    }

    public void createHostPanel(Player player){
        workingPlaces.put(player,home);
    }
}