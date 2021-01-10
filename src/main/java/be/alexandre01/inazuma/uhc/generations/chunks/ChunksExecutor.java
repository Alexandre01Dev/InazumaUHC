package be.alexandre01.inazuma.uhc.generations.chunks;

import be.alexandre01.inazuma.uhc.utils.TitleUtils;
import javassist.bytecode.analysis.Executor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChunksExecutor  {
    private ScheduledExecutorService scheduledExecutorService;
    private ChunksGenerator c;
    private int totalChunks;
    private int actualChunk;
    private int lastCalculateChunks = 0;
    private int totalCalculateChunks = 0;
    private int chunkSpeed;
    public ChunksExecutor(ChunksGenerator c){
        super();
        scheduledExecutorService = Executors.newScheduledThreadPool(8);
        this.c = c;
        this.totalChunks = c.getTotalChunk();
        this.actualChunk = c.getActualChunk();
        this.chunkSpeed = 0;
        System.out.println("chunkgen");
    }

    public void schedule(){
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                totalCalculateChunks ++;
                int pourcentage = (c.getTotalChunk() - c.getChunksCord().size()) * 100 / c.getTotalChunk();
                int r =  c.getCalculateChunks()/totalCalculateChunks;//c.getCalculateChunks()-lastCalculateChunks;//c.getTotalChunk() - c.getCalculateChunks();
                System.out.println("SCHEDULE>>"+r);
                lastCalculateChunks = c.getCalculateChunks();

                String value;
                if(r != 0 && totalCalculateChunks >= 25){
                    int sec  = c.getChunksCord().size()/r;
                    int min = 0;
                    if(sec > 60*5){
                        value = "+"+5 +" minute(s)";
                    }else {
                    if(sec > 60){
                        min = sec/60;
                        value = "~"+min +" minute(s)";
                    }else {
                        if(sec >45){
                            value = "~"+1 +" minute(s)";
                        }else {
                            if(sec > 25){
                                value =  "~"+30 +" seconde(s)";
                            }else {
                                value =  sec +" seconde(s)";
                            }
                        }

                    }

                    }
                }else {
                    value = "~";
                }

                System.out.println("La map "+c.getWorld().getEnvironment().name()+" est gen à " + pourcentage + "% -> ["+ r+"chunks/s]\n"+"Il reste "+ value);
                for(Player player : Bukkit.getOnlinePlayers()){
                    TitleUtils.sendActionBar(player,"§eLa §lmap §eest généré à §l"+ pourcentage+"% §c|§e§l "+value);
                }

                chunkSpeed = r;

            }
        },0,1, TimeUnit.SECONDS);
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }
}
