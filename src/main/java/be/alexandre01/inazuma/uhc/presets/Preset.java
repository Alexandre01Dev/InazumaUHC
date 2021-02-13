package be.alexandre01.inazuma.uhc.presets;

import be.alexandre01.inazuma.uhc.InazumaUHC;

import java.util.HashMap;

public class Preset {
    public static Preset instance;

    public HashMap<String, IPreset> presets;
    public IPreset p;
    public PresetData pData;
    public Preset(IPreset iPreset){
       Preset.instance = this;
       this.p = iPreset;
       this.pData = (PresetData) iPreset;
       if(!InazumaUHC.get.isHosted){
           configurate();
       }
    }

    public void set(IPreset iPreset){
        p = iPreset;
        configurate();
    }

    public void configurate(){
        InazumaUHC.get.worldGen.gen();
    }

}
