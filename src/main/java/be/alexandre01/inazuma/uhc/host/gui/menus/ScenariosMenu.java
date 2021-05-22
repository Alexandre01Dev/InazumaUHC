package be.alexandre01.inazuma.uhc.host.gui.menus;

import be.alexandre01.inazuma.uhc.host.HostButton;
import be.alexandre01.inazuma.uhc.host.gui.WorkingPlace;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.PresetData;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import be.alexandre01.inazuma.uhc.utils.fastinv.FastInv;
import org.bukkit.Material;

public class ScenariosMenu extends WorkingPlace {
    public ScenariosMenu(FastInv fastInv) {
        super(fastInv);
        setColorVariations(ColorVariante.Cyan,ColorVariante.Blue);
        readyToSetClickableItems();
      //  setSeparation(1,ColorVariante.Cyan);

        PresetData presetData = Preset.instance.pData;
        for(Scenario scenario : Scenario.getScenarios().values()){
            boolean defaultValue = false;
            if(presetData.getScenarios().contains(scenario.getClass())){
                defaultValue = true;
            }
            HostButton s = new HostButton(scenario.getItemStack(),scenario.getName(), HostButton.Type.DIRECT);
            boolean b = presetData.getScenarios().contains(scenario.getClass());
            s.setValue(b);
            if(b){
                s.setEnchant(true);
            }

            int p = addButton(s);
            s.setAction(player -> {
                boolean v = (boolean) s.getValue();
                if(v){
                    presetData.getScenarios().remove(scenario.getClass());
                    s.setValue(false);
                    s.setEnchant(false);
                    updateItem(p,s);
                }else {
                    presetData.getScenarios().add(scenario.getClass());
                    s.setValue(true);
                    s.setEnchant(true);
                    updateItem(p,s);
                }
            });
        }
    }
}
