package be.alexandre01.inazuma.uhc.host.gui.menus;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.host.HostButton;
import be.alexandre01.inazuma.uhc.host.gui.WorkingPlace;
import be.alexandre01.inazuma.uhc.modules.Module;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import be.alexandre01.inazuma.uhc.utils.fastinv.FastInv;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MDJ extends WorkingPlace {

    public MDJ(FastInv fastInv) {
        super(fastInv);
        setColorVariations(ColorVariante.Green,ColorVariante.DarkGreen);
        readyToSetClickableItems();
        setSeparation(1,ColorVariante.Cyan);

        int p = 0;
        for(Class<?> preset : Preset.instance.getPresets()){
            Module module = Preset.instance.modules.get(preset);

            HostButton s = new HostButton(module.getMaterial(),module.getModuleName(), HostButton.Type.DIRECT);
            if(Preset.instance.p.getClass() == preset){
                s.setEnchant(true);
            }


            StringBuilder sb = new StringBuilder();
            int i = 0;
            for(String m : module.getAuthors()){
                sb.append(m);
                if(module.getAuthors().length-2 == i){
                    sb.append(" & ");
                }else {
                    sb.append(", ");
                }
            }

            s.setAction(player -> {
                    Role.clear();
                    Preset.instance.set(module);
                    InazumaUHC.get.onLoadPreset();
                    GameState.get().setTo(GameState.get().getState());

            });
            p++;
            addButton(s);
    }
    }
}
