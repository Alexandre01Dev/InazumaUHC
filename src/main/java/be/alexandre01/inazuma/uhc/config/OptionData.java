package be.alexandre01.inazuma.uhc.config;

import be.alexandre01.inazuma.uhc.config.yaml.YamlUtils;
import be.alexandre01.inazuma.uhc.utils.CastObject;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;

public class OptionData {
    ConfigurationSection c;
    ConfigurationSection s;
    HashMap<String,OptionData> o;
    HashMap<String, OptionModifier> currentObject;
    OptionModifier m;
    String k;
    YamlUtils y;
    public OptionData(ConfigurationSection s,String key, YamlUtils y) {
        this.c = s.getConfigurationSection(key);
        this.s = s;
        this.y = y;
        this.k = key;
        this.o = new HashMap<>();
        this.currentObject = new HashMap<>();
        findSubOption();
    }

    private void findSubOption(){
        System.out.println("findSub "+c);
        if (c != null) {
            for(String k : c.getKeys(false)){
                if(c.getConfigurationSection(k) != null){
                    ConfigurationSection cs = c.getConfigurationSection(k);
                    if(cs != null){
                        o.put(k,new OptionData(c,k,y));
                        continue;
                    }
                    currentObject.put(k,new OptionModifier(c,k,y));
                }
            }
        }

        m = new OptionModifier(s,k,y);
        System.out.println(m.get());
    }

    public OptionData set(Object value, String... strings){
        if(strings.length == 1){
            if(strings[0].contains(".")){
                c.set(strings[0],value);
                y.save();
                return this;
            }
            return this;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length-1; i++) {
           sb.append(strings[i]);
           if(i != strings.length-1)
               sb.append(".");
        }

        c.set(sb.toString(),value);
        y.save();
        return this;
    }
    public OptionData set(Object value){
        m.set(value);
        return this;
    }

    public boolean contains(String... strings){
        if(strings.length == 1){
            if(strings[0].contains(".")){
                return c.contains(strings[0]);
            }
            return false;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length-1; i++) {
            sb.append(strings[i]);
            if(i != strings.length-1)
                sb.append(".");
        }

        return c.contains(sb.toString());
    }

    public OptionModifier get(String... strings){
        if(strings.length == 1){
            if(strings[0].contains(".")){
                String[] parts = strings[0].split("\\.");

                for (int i = 0; i < parts.length-1; i++) {
                    getSection(parts[i]);
                }
                return currentObject.get(parts[parts.length-1]);
            }
            return this.currentObject.get(strings[0]);
        }

        for (int i = 0; i < strings.length-1; i++) {
            getSection(strings[i]);
        }
        return currentObject.get(strings[strings.length-1]);
    }
    public OptionModifier get(){
        return m;
    }
    public OptionData getSection(String... sections){
        if(sections.length == 1){
            if(sections[0].contains(".")){
                String[] parts = sections[0].split("\\.");

                for (int i = 0; i < parts.length-1; i++) {
                    getSection(parts[i]);
                }
                return o.get(parts[parts.length-1]);
            }
            return this.o.get(sections[0]);
        }

        for (int i = 0; i < sections.length-1; i++) {
          getSection(sections[i]);
        }
        return o.get(sections[sections.length-1]);
    }

    public OptionData set(String value, String... strings){

        return this;
    }
}
