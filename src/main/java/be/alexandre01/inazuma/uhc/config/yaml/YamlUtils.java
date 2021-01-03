package be.alexandre01.inazuma.uhc.config.yaml;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class YamlUtils {
    private Plugin plugin;
    private File confDataFile;
    private YamlConfiguration confDataConfig;
    private Utf8YamlConfiguration utf8;
    private String pathName = null;
    public static Charset UTF8_CHARSET = Charset.forName("UTF-8");

    public YamlUtils(Plugin plugin, String name,String pathName) {
        this.pathName = pathName;
        this.plugin = plugin;
        confDataFile = new File(plugin.getDataFolder(), name);
        confDataConfig = YamlConfiguration.loadConfiguration(confDataFile);
        utf8 = new Utf8YamlConfiguration();
        loadYml();
    }
    public YamlUtils(Plugin plugin, String name) {
        this.plugin = plugin;
        confDataFile = new File(plugin.getDataFolder(), name);
        confDataConfig = YamlConfiguration.loadConfiguration(confDataFile);
        utf8 = new Utf8YamlConfiguration();
        loadYml();
    }
    public FileConfiguration getConfig(){
        return confDataConfig;
    }
    public File getFile(){
        return confDataFile;
    }
    public void save(){
        try {
            confDataConfig.save(confDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadYml() {
        if(!confDataFile.exists()){
            if(pathName != null){
                if(!new File(plugin.getDataFolder()+pathName).exists()){
                    confDataFile.getParentFile().mkdirs();
                    plugin.saveResource(confDataFile.getName(), false);
                }
            }else {
                confDataFile.getParentFile().mkdirs();
                plugin.saveResource(confDataFile.getName(), false);
            }
        }
        confDataConfig= new YamlConfiguration();
        try {
            if(pathName != null){
                confDataFile.renameTo(new File(plugin.getDataFolder()+ pathName));
                confDataFile.delete();
                confDataFile = new File(plugin.getDataFolder()+pathName);
            }
            InputStream inputstream = new FileInputStream(confDataFile);

            utf8.load(inputstream);

            confDataConfig = utf8;



        } catch (Exception e) {
            System.out.println("ERROR LOADING FILE");
        }
    }

}