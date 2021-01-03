package be.alexandre01.inazuma.uhc.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;

public class LanguageData {
    private ConfigurationSection yaml;
    private HashMap<String,String> msgs;
    private HashMap<String,LanguageData> index;
    public LanguageData(ConfigurationSection yaml){
        this.yaml = yaml;
        this.msgs = new HashMap<>();
        this.index = new HashMap<>();
        read();
    }

    private void read(){
        for(String key : this.yaml.getKeys(true)){
            if(key.contains(".")){
                this.index.put(key.split("\\.")[0],new LanguageData(this.yaml.getConfigurationSection(key.split("\\.")[0])));
                continue;
            }
            this.msgs.put(key,this.yaml.getString(key));
        }
    }

    public String get(String... strings){
        if(strings.length == 1){
            if(strings[0].contains(".")){
                String[] parts = strings[0].split("\\.");
                LanguageData languageData = this;
                for (int i = 0; i < parts.length-1; i++) {
                    languageData = getSection(parts[i]);
                }
                return languageData.getMsgs().get(parts[parts.length-1]);
            }
            return this.msgs.get(strings[0]);
        }
        LanguageData languageData = this;
        for (int i = 0; i < strings.length-1; i++) {
            languageData = getSection(strings[i]);
        }
        return languageData.getMsgs().get(strings[strings.length-1]);
    }

    public LanguageData getSection(String... sections){
        if(sections.length == 1){
            if(sections[0].contains(".")){
                String[] parts = sections[0].split("\\.");
                LanguageData languageData = this;
                for (int i = 0; i < parts.length-1; i++) {
                    languageData = getSection(parts[i]);
                }
                return languageData.index.get(parts[parts.length-1]);
            }
            return this.index.get(sections[0]);
        }
        LanguageData languageData = this;
        for (int i = 0; i < sections.length-1; i++) {
            languageData = getSection(sections[i]);
        }
        return languageData.getIndex().get(sections[sections.length-1]);
    }
    private HashMap<String, String> getMsgs() {
        return msgs;
    }

    private HashMap<String, LanguageData> getIndex() {
        return index;
    }
}
