package be.alexandre01.inazuma.uhc.config;


import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.config.Exception.DefaultLanguageNotSetException;
import be.alexandre01.inazuma.uhc.config.Exception.LanguageNotFoundException;
import be.alexandre01.inazuma.uhc.config.Exception.OptionMissingException;
import be.alexandre01.inazuma.uhc.config.yaml.YamlUtils;
import be.alexandre01.inazuma.uhc.utils.CastObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Options {
    private File customConfigFile;
    private FileConfiguration customConfig;
    private YamlUtils config;
    private HashMap<String, OptionData> option;
    private static HashMap<String,Options> instances;
    private String fileName = null;
    private String index;
    private HashMap<String, OptionModifier> currentObject;
    //Get config file & init hashmap
    public Options(Plugin plugin,String fileName,String index){
        this.config = new YamlUtils(plugin,fileName+".yml");
        this.option = new HashMap<>();
        this.fileName = fileName;
        this.index = index;
        this.instances = new HashMap<>();
        this.currentObject = new HashMap<>();

        autoLoad();
    }

    //Read config file and distribute language.
    private void autoLoad(){
        //Get different languages on the config
        for(String key : config.getConfig().getConfigurationSection(index).getKeys(false)){
            System.out.println(index+"."+key);
            System.out.println(config.getConfig().getConfigurationSection(index).getConfigurationSection(key));
            this.option.put(key,new OptionData(config.getConfig().getConfigurationSection(index),key,config));
        }

        InputStream resource = InazumaUHC.get.getResource(fileName+".yml");
        BufferedReader b = new BufferedReader(new InputStreamReader(resource));
        System.out.println(b.lines().collect(Collectors.toList()));
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        }else {
                createCustomConfig();
                System.out.println("try");
                System.out.println("file uri");
                YamlConfiguration yml = YamlConfiguration.loadConfiguration(InazumaUHC.get.getResource(fileName+".yml"));

                System.out.println("new yml");
                System.out.println("load yml");
                System.out.println(customConfig);
                System.out.println(customConfig.getStringList(""));

                //VERIFICATION
                for(String key : yml.getConfigurationSection(index).getKeys(true)){
                    System.out.println("ok");
                    boolean check = false;
                    for (String s : config.getConfig().getConfigurationSection(index).getKeys(true)){
                        System.out.println(key+"-"+s);
                        if(key.equals(s)){
                            check = true;
                            System.out.println("capté !");
                            break;
                        }
                    }
                    if(!check){
                     /*   try {
                            System.out.println("wtf -> "+ fileName);
                            throw new OptionMissingException("Options are missing in "+fileName+".yml.");
                        } catch (OptionMissingException e) {
                            e.printStackTrace();
                        }*/
                    }

                }
        }




        instances.put(fileName,this);
    }


    public static Options to(String name){
        return instances.get(name);
    }


    public OptionModifier get(String... index){
        String first = index[0];
      //  index[0] = "";
        if(index.length == 1){
            System.out.println(index[0]);
            for(String key : option.keySet()){
                System.out.println(key);
            }
            return option.get(index[0]).get();
            //return new OptionModifier(new OptionModifier(c,k,y));
        }
 /*       String[] clean = Arrays.stream(index)
                .map(String::trim)
                .filter(Predicate.isEqual("").negate())
                .toArray(String[]::new);

        System.out.println("okOpt");
        for (String c : clean){
            System.out.println(c);
        }*/
        return option.get(first).get(index);
    }


    public OptionData getSection(String... index){
        String first = index[0];
        index[0] = null;
        String[] clean = Arrays.stream(index)
                .map(String::trim)
                .filter(Predicate.isEqual("").negate())
                .toArray(String[]::new);
        System.out.println(clean);
        return option.get(first).getSection(clean);
    }

    public boolean contains(String... index){
        for(String in : index){
            System.out.println(in);
        }
        String first = index[0];
        if(index.length == 1){
            for(String key : option.keySet()){
                System.out.println("Contains >> "+key);

            }
            System.out.println("> "+first+ " > "+option.containsKey(first));
           return option.containsKey(first);
        }
        if(option.containsKey(first))
            return option.get(first).contains(index);
        return false;
    }
    public OptionData set(Object value,String... index){
        String first = index[0];
        if(!option.containsKey(first)){
            index[0] = null;
            config.getConfig().set(first,value);
            config.save();
            option.put(first,new OptionData(config.getConfig().getConfigurationSection(this.index),first,config));
            return option.get(first);
        }

        if(index.length == 1){
            return option.get(first).set(value);
        }



        return option.get(first).set(value,index);
    }
    private void createCustomConfig() {
         customConfigFile = new File(InazumaUHC.get.getDataFolder(), "configTemp.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            InazumaUHC.get.saveResource("configTemp.yml", false);
        }

         customConfig= new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}
