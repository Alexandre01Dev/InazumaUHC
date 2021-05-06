package be.alexandre01.inazuma.uhc.worlds;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.config.Config;
import be.alexandre01.inazuma.uhc.config.Options;
import be.alexandre01.inazuma.uhc.config.yaml.YamlUtils;
import be.alexandre01.inazuma.uhc.custom_events.worlds.DefaultWorldLoadedEvent;
import be.alexandre01.inazuma.uhc.generations.NetherPortalsManager;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.spectators.BukkitTeamInitializer;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.plugin.Plugin;
import spg.lgdev.config.ImanityWorldConfig;
import spg.lgdev.iSpigot;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class WorldGen {
    private int worlds = 1;
    YamlUtils yml;
    Plugin p;
    Options o;
    boolean isGenerating = false;
    String defaultUUID;
    String netherUUID;
    public World defaultWorld;
    public World netherWorld;
    private boolean isWorldGen = false;

    public WorldGen(Plugin plugin){
        this.p = plugin;
        this.o = Options.to("worldsTemp");
    }


    public void gen(){
        for(Player player : Bukkit.getOnlinePlayers()){
            player.setAllowFlight(false);
            player.setFlying(false);
        }

        if(!InazumaUHC.get.loadWorldBefore){
            isGenerating = true;
        }

        ImanityWorldConfig iwc =  iSpigot.INSTANCE.getWorldConfigByName("default");

        /*
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
        */
        iwc.sugarCaneHeightMin = 1;
        iwc.sugarCaneHeightMax = 4;
        iwc.sugarCaneRound = 40;
        System.out.println("> GEN1 ");
        if(InazumaUHC.get.unloadWorlds){
            for (World ws: Bukkit.getWorlds()
            ) {
                Bukkit.unloadWorld(ws,false);

                Chunk[] chunks = ws.getLoadedChunks();
                for (Chunk chunk : chunks) {
                    chunk.unload(false);
                }

                //Config.removeDir(p.getServer().getWorldContainer().getAbsolutePath()+"/"+ws.getName());
            }
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
        Bukkit.getServer().getPluginManager().callEvent(new DefaultWorldLoadedEvent());
        isGenerating = true;
        BukkitTeamInitializer.initialize();
        if(Preset.instance.p.getPlatform() != null){
            Preset.instance.p.getPlatform().spawn();
        }



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
        InazumaUHC.get.npm = new NetherPortalsManager();
    }

    public boolean isGenerating() {
        return isGenerating;
    }
}
