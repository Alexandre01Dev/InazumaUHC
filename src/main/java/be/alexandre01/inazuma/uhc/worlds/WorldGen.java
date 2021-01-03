package be.alexandre01.inazuma.uhc.worlds;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.commands.test.ChunkCommand;
import be.alexandre01.inazuma.uhc.config.Config;
import be.alexandre01.inazuma.uhc.config.Options;
import be.alexandre01.inazuma.uhc.config.yaml.YamlUtils;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import spg.lgdev.config.ImanityWorldConfig;
import spg.lgdev.config.world.OrePopulatorRule;
import spg.lgdev.iSpigot;

import java.util.UUID;

public class WorldGen {
    YamlUtils yml;
    Plugin p;
    Options o;
    String defaultUUID;
    String netherUUID;
    public World defaultWorld;
    public World netherWorld;

    public WorldGen(Plugin plugin){
        this.p = plugin;
        this.o = Options.to("worldsTemp");
    }
    public void gen(){
        ImanityWorldConfig iwc =  iSpigot.INSTANCE.getWorldConfigByName("default");
        iwc.set("generateRate",700);
        iwc.set("caveMinAltitude", 50);
        iwc.caveGenerateRate = 700;
        iwc.caveMinAltitude = 50;
        iwc.largeCaveGenerateRate = 700;
        iwc.caveMaxAltitude = 300;
        iwc.orePopulatorRules.put(57,new OrePopulatorRule(57,20,0,30,10,1));
        iwc.orePopulatorRules.put(42,new OrePopulatorRule(42,20,0,40,10,1));
        iwc.orePopulatorRules.put(21,new OrePopulatorRule(21,40,0,30,10,1));
        iwc.orePopulatorRules.put(173,new OrePopulatorRule(173,80,0,60,10,1));
        iwc.biomePlains = false;
        System.out.println("> GEN1 ");
        for (World ws: Bukkit.getWorlds()
        ) {
            Bukkit.unloadWorld(ws,false);

            Chunk[] chunks = ws.getLoadedChunks();
            for (Chunk chunk : chunks) {
                chunk.unload(false);
            }

            //Config.removeDir(p.getServer().getWorldContainer().getAbsolutePath()+"/"+ws.getName());
        }
        System.out.println("> GEN2 ");
        for (int i = 0; i <  InazumaUHC.get.getServer().getWorlds().size(); i++) {
            InazumaUHC.get.getServer().getWorlds().remove(i);
        }

        if(o.contains("defaultUUID")){
            System.out.println("delete dir default !");
            String path = p.getServer().getWorldContainer().getAbsolutePath();
            String patchPath =   path.substring(0, path.length() - 1);
            System.out.println(o.get("defaultUUID").getString());
            System.out.println(patchPath+ o.get("defaultUUID").getString());
            Config.removeDir(patchPath+ o.get("defaultUUID").getString());
        }
        if(o.contains("netherUUID")){
            System.out.println("delete dir nether !");
            Config.removeDir(p.getServer().getWorldContainer().getAbsolutePath()+"/"+ o.get("netherUUID").getString());
        }
        System.out.println(">FORCEDGEN");
        this.defaultUUID = UUID.randomUUID().toString();
        o.set(defaultUUID,"defaultUUID");

        WorldCreator w = new WorldCreator(defaultUUID);
        w.environment(World.Environment.NORMAL);

        if(Preset.instance.p.getGeneratorSettings(w.environment()) != "")
            w.generatorSettings(Preset.instance.p.getGeneratorSettings(w.environment()));

       this.defaultWorld =  w.createWorld();
    }

    public void defaultWorldLoaded(){
        Preset.instance.p.getPlatform().spawn();



        if(Preset.instance.p.getNether()){
           this.netherUUID = UUID.randomUUID().toString();

           while (defaultUUID == netherUUID){
               this.netherUUID = UUID.randomUUID().toString();
           }
           o.set(netherUUID,"netherUUID");
            WorldCreator w = new WorldCreator(netherUUID);
            w.environment(World.Environment.NETHER);
            w.generatorSettings(Preset.instance.p.getGeneratorSettings(w.environment()));
            this.netherWorld =  w.createWorld();
        }
    }
}
