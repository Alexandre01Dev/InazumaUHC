package be.alexandre01.inazuma.uhc.presets;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.modules.Module;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.utils.ObjectUtil;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Preset {
    public static Preset instance;

    public HashMap<String, Class<?>> presets;
    public HashMap<Class<?>, Module> modules = new HashMap<>();
    public HashMap<Class<?>, Module> tempModules = new HashMap<>();
    public Module m;
    public IPreset p;
    public PresetData pData;
    @SneakyThrows
    public Preset(){
       Preset.instance = this;
       this.presets = new HashMap<>();
    }

    @SneakyThrows
    public void set(Module module){
        Role.clear();
        p = (IPreset) module.getPresetClass().getConstructor().newInstance();
        pData = (PresetData) p;
        modules.put(module.getPresetClass(),module);
        this.m = module;
        if(!InazumaUHC.get.isHosted){
            configurate();
        }
    }
    @SneakyThrows
    public void set(Module module,boolean inGame){
        IPreset oldIPreset = p;

        p = (IPreset) module.getPresetClass().getConstructor().newInstance();
        if(inGame)
            ObjectUtil.copyAndPaste(oldIPreset,p,"timers","listeners");

        PresetData oldPresetData = pData;
        pData = (PresetData) p;
        if(inGame)
            ObjectUtil.copyAndPaste(oldPresetData,pData,"timers","listeners");

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
    public void remove(String name,Module m){
        presets.remove(name,m.getPresetClass());
        modules.remove(m.getPresetClass(),m);
    }
    public void configurate(){
        InazumaUHC.get.worldGen.gen();
    }

}
