package be.alexandre01.inazuma.uhc.modules;


import be.alexandre01.inazuma.uhc.presets.IPreset;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Material;

import java.net.URLClassLoader;

@Data
public class Module {
     private String presetPath;
     private String moduleName;
     private String[] authors;
     private String version;
     private Material material;
     private String description;
     private URLClassLoader child;
     private Class<?> presetClass;
}
