package be.alexandre01.inazuma.uhc.modules;

import be.alexandre01.inazuma.uhc.config.Config;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.jar.JarFile;

public class ModuleLoader {
    private File dir;
    private final ArrayList<Module> modules = new ArrayList<>();

    public ModuleLoader(Plugin plugin){
        try {
            dir = new File(plugin.getDataFolder().getAbsolutePath()+"/modules");
            for(File file : Objects.requireNonNull(dir.listFiles())){
                if(file.isDirectory())
                    continue;
                URLClassLoader child = new URLClassLoader(
                        new URL[] {file.toURI().toURL()},
                        this.getClass().getClassLoader()
                );
                Module module = null;
                YamlConfiguration yamlConfiguration = new YamlConfiguration();
                if(child.getResource("module.yml") != null){
                    System.out.println(child.findResource("module.yml").toExternalForm());
                    File tempFile = File.createTempFile("tmp", ".yml");
                    tempFile.deleteOnExit();
                    try(OutputStream outputStream = new FileOutputStream(tempFile)){
                        //IOUtils.copy(Objects.requireNonNull(), outputStream);
                    } catch (FileNotFoundException e) {
                        // handle exception here
                    } catch (IOException e) {
                        // handle exception here
                    }

                    System.out.println("Tempfile Length>>"+tempFile.length());
                    yamlConfiguration.load(child.getResourceAsStream("module.yml"));
                    for (String k : yamlConfiguration.getKeys(false)){
                        System.out.println("KEY>>"+ k);
                    }

                    module = new Module();
                    module.setPresetPath(yamlConfiguration.getString("module-path"));
                    module.setModuleName(yamlConfiguration.getString("module-name"));

                    module.setVersion(yamlConfiguration.getString("version"));
                    module.setAuthors(yamlConfiguration.getStringList("authors").toArray(new String[0]));

                    module.setDescription(yamlConfiguration.getString("description"));

                    module.setMaterial(Material.getMaterial(yamlConfiguration.getInt("itemId")));

                    module.setChild(child);
                }
                if(module == null)
                    return;

                Class classToLoad = Class.forName(module.getPresetPath(), true, child);
                Object instance = classToLoad.newInstance();
                module.setPresetClass(classToLoad);
                System.out.println(instance.getClass());
                if(instance instanceof IPreset){
                    System.out.println(module.getModuleName());
                    Preset.instance.add(module.getModuleName(), module);
                }
                modules.add(module);

            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
