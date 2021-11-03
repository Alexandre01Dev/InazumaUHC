package be.alexandre01.inazuma.uhc.timers.game;

import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.timers.ITimer;
import be.alexandre01.inazuma.uhc.timers.Timer;
import be.alexandre01.inazuma.uhc.timers.utils.DateBuilderTimer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class StabilizationTimer extends Timer {
    IPreset p = Preset.instance.p;
    public StabilizationTimer() {
        super("stabilizationTimer");

            be.alexandre01.inazuma.uhc.InazumaUHC i = be.alexandre01.inazuma.uhc.InazumaUHC.get;
            ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
            super.setOptimisation(false);
            super.setTimer(new ITimer() {
                boolean activate = false;
                DateBuilderTimer dateBuilderTimer;
                @Override
                public void preRun() {
                    for(Player player: Bukkit.getOnlinePlayers()){
                        player.sendMessage("§7Stabilisation du serveur.");
                    }
                }

                @Override
                public void run() {
                    double tps =  MinecraftServer.getServer().recentTps[0];


                    if(tps < 20.5 && tps >= 20|| tps > 19.5 && tps <= 20){
                        System.out.println("Stabilisation du serveur...");
                        if(!activate){
                            dateBuilderTimer = new DateBuilderTimer(3*1000);
                            activate = true;
                        }
                        dateBuilderTimer.loadDate();


                        if(dateBuilderTimer.getDate().getTime() <= 0){
                            cancel();
                        }
                        return;
                    }
                    activate = false;
                }
            });
}
}
