package be.alexandre01.inazuma.uhc.presets;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.modules.Module;
import be.alexandre01.inazuma.uhc.roles.Role;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Preset {
    public static Preset instance;

    public HashMap<String, Class<?>> presets;
    public HashMap<Class<?>, Module> modules = new HashMap<>();
    public Module m;
    public IPreset p;
    public PresetData pData;
    @SneakyThrows
    public Preset(Module module){
       Preset.instance = this;
       this.m = module;
       this.presets = new HashMap<>();
       this.p = (IPreset) module.getPresetClass().newInstance();
       this.pData = (PresetData) this.p;
        modules.put(module.getPresetPath().getClass(),module);
       add(module.getModuleName(), module);
       if(!InazumaUHC.get.isHosted){
           configurate();
       }
    }

    @SneakyThrows
    public void set(Module module){
        p = (IPreset) module.getPresetClass().newInstance();
        pData = (PresetData) p;
        modules.put(module.getPresetClass(),module);
        this.m = module;
        if(!InazumaUHC.get.isHosted){
            configurate();
        }
    }

    @SneakyThrows
    public void set(String s){
        p = (IPreset) presets.get(s).newInstance();
        pData = (PresetData) p;
        if(!InazumaUHC.get.isHosted){
            configurate();
        }
    }
    public Collection<Class<?>> getPresets(){
        return presets.values();
    }
        public void add(String name, Module m){
        presets.put(name,m.getPresetClass());
        modules.put(m.getPresetClass(),m);
    }
    public void configurate(){
        InazumaUHC.get.worldGen.gen();
    }

}
