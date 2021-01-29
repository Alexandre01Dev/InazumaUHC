package be.alexandre01.inazuma.uhc.listeners.game;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.config.Options;
import be.alexandre01.inazuma.uhc.generations.chunks.ChunksGenerator;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;

import java.time.Instant;

public class WorldGenEvent implements Listener {
    private InazumaUHC i;
    public WorldGenEvent(){
        this.i = InazumaUHC.get;
    }
    public static boolean g = false;
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){

    }
    @EventHandler
    public void onWorldInit(WorldInitEvent e) {

    }
    @EventHandler
    public void onLoadChunk(ChunkLoadEvent event){
        if(i.teamManager != null){
            if(i.teamManager.isTeleportVerification){
                System.out.println("New Chunk "+event.isNewChunk()+"\nX:"+event.getChunk().getX()+"\nZ"+event.getChunk().getZ());
                i.teamManager.lastChunkLoaded = Instant.now();
            }
        }

        if(g){
            System.out.println("New Chunk "+event.isNewChunk()+"\nX:"+event.getChunk().getX()+"\nZ"+event.getChunk().getZ());
        }

    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event){
        System.out.println(event.getWorld().getName());
        Options o = Options.to("worldsTemp");
        IPreset p = Preset.instance.p;
        if(o.contains("defaultUUID")){
            InazumaUHC.get.worldGen.defaultWorld = event.getWorld();
            System.out.println("> worldName = "+event.getWorld().getName()+"\n> defaultUUID = "+ o.get("defaultUUID").getString());
            if(event.getWorld().getName().equals(o.get("defaultUUID").getString())){

                event.getWorld().setSpawnLocation(0,100,0);
                //ChunkCommand.around(event.getWorld().getChunkAt(0,0),(p.getBorderSize(event.getWorld().getEnvironment())/16)+InazumaUHC.get.getServer().getViewDistance());
                ChunksGenerator c = new ChunksGenerator();
                c.generate(event.getWorld().getChunkAt(0,0),(p.getBorderSize(event.getWorld().getEnvironment())/16)+InazumaUHC.get.getServer().getViewDistance(),true);

                //event.getWorld().getPopulators().add(new OrePopulator(UHC.uhc));
                //event.getWorld().getPopulators().add(new NetherPopulator(UHC.uhc));

                //NameTag.setObjective();
                event.getWorld().setGameRuleValue("randomTickSpeed", p.getRandomTickSpeed(event.getWorld().getEnvironment()));
                event.getWorld().setGameRuleValue("naturalRegeneration", p.getNaturalRegeneration(event.getWorld().getEnvironment()));
                event.getWorld().setGameRuleValue("announceAdvancements", "false");
                event.getWorld().setTime(1000);
                event.getWorld().setPVP(false);
                event.getWorld().setThundering(false);
                event.getWorld().setWeatherDuration(0);
                event.getWorld().setGameRuleValue("reducedDebugInfo", "true");
                event.getWorld().setGameRuleValue("doDaylightCycle", "false");
                // ChunkManager.propageLoadingChunkAt(event.getWorld(),-max,-max,this);
                // new ChunkManager().generateChunks(World.Environment.NORMAL);
                event.getWorld().setSpawnLocation(0,141,0);
                //  setPlatform(event.getWorld(),Material.STAINED_GLASS);
                if(p.getPlatform() != null){
                    event.getWorld().getWorldBorder().setSize(p.getPlatform().size());
                }
                event.getWorld().getWorldBorder().setCenter(0,0);
                //modsManager.setPlayerMods(Mods.STARTING,new StartingMode());
                //mSpigot potch
                // waitingMode = new WaitingMode();
                // Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mobai");
                // modsManager.setPlayerMods(mods,waitingMode);
                InazumaUHC.get.worldGen.defaultWorldLoaded();
                return;
            }
        }

        if (o.contains("netherUUID")) {
            InazumaUHC.get.worldGen.netherWorld = event.getWorld();
            if(event.getWorld().getName().equals(o.get("netherUUID").getString())){
                event.getWorld().setSpawnLocation(0,100,0);
              //ChunkCommand.around(event.getWorld().getChunkAt(0,0),(p.getBorderSize(event.getWorld().getEnvironment())/16)+InazumaUHC.get.getServer().getViewDistance());
                ChunksGenerator c = new ChunksGenerator();
                c.generate(event.getWorld().getChunkAt(0,0),(p.getBorderSize(event.getWorld().getEnvironment())/16)+InazumaUHC.get.getServer().getViewDistance(),true);
                event.getWorld().setGameRuleValue("randomTickSpeed", p.getRandomTickSpeed(event.getWorld().getEnvironment()));
                event.getWorld().setGameRuleValue("naturalRegeneration", p.getNaturalRegeneration(event.getWorld().getEnvironment()));
                event.getWorld().setGameRuleValue("announceAdvancements", "false");
                event.getWorld().getWorldBorder().setSize(p.getBorderSize(World.Environment.NETHER)*2);
                event.getWorld().getWorldBorder().setCenter(0,0);
                event.getWorld().setTime(1000);
                event.getWorld().setPVP(false);
                event.getWorld().setThundering(false);
                event.getWorld().setWeatherDuration(0);
                event.getWorld().setGameRuleValue("reducedDebugInfo", "true");
                event.getWorld().setGameRuleValue("doDaylightCycle", "false");

                return;
            }
        }


    }
}
