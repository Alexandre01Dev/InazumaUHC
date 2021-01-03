package be.alexandre01.inazuma.uhc.config;


import be.alexandre01.inazuma.uhc.config.Exception.DefaultLanguageNotSetException;
import be.alexandre01.inazuma.uhc.config.Exception.LanguageNotFoundException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class Messages {
    private FileConfiguration config;
    private HashMap<String,LanguageData> langs;
    private LanguageData langData;
    private String defaultLanguage;
    private static Messages instance;
    private String language = null;
    //Get config file & init hashmap
    public Messages(Plugin plugin){
         this.config = plugin.getConfig();
         this.langs = new HashMap<>();
         autoLoad();
    }

    //Read config file and distribute language.
    private void autoLoad(){
        //Get different languages on the config
        for(String key : config.getConfigurationSection("language").getKeys(false)){
            LanguageData languageData;
            System.out.println(key);
            languageData = new LanguageData(config.getConfigurationSection("language."+key));
            this.langs.put(key,languageData);
        }
        setDefaultLanguage("en");
        setLanguage("en");
        instance = this;
    }
    //Set default language

    public void setDefaultLanguage(String key){
        if(!langs.containsKey(key)){
            new DefaultLanguageNotSetException(this);
        }
        this.defaultLanguage = language;
    }
    //Set language to use
    public void setLanguage(String key){
        String lastLang = language;
        if(!langs.containsKey(key)){
            new LanguageNotFoundException(key,lastLang,this);
            return;
        }
        this.language = key;
        this.langData = langs.get(key);
    }

    public static Messages getInstance(){
        return instance;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public HashMap<String, LanguageData> getLangs() {
        return langs;
    }

    public static String get(String... index){
        return instance.langData.get(index);
    }

    public static LanguageData getSection(String... index){
        return instance.langData.getSection(index);
    }
}
