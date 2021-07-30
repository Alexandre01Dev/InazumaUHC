package be.alexandre01.inazuma.uhc.config;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CustomExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace(System.out);

        if(InazumaUHC.get.debugMode){
            System.out.println("Debug");
            for(Player player : Bukkit.getOnlinePlayers()){
                if(player.hasPermission("debug.view")){
                    player.sendMessage("§cERREUR >> "+e.getMessage()+" §4||§c "+ e.getClass().getSimpleName());
                    for(StackTraceElement s : e.getStackTrace()){
                        player.sendMessage("----->");
                        player.sendMessage("§cERREUR DANS>> §f" +s.getClassName()+":"+s.getMethodName()+":"+s.getLineNumber());
                    }
                    for(StackTraceElement s : e.getCause().getStackTrace()){
                        player.sendMessage("----->");
                        player.sendMessage("§cERREUR DANS>> §f" +s.getClassName()+":"+s.getMethodName()+":"+s.getLineNumber());
                    }
                    for(Throwable tr : e.getSuppressed()){
                        for(StackTraceElement s : tr.getStackTrace()){
                            player.sendMessage("----->");
                            player.sendMessage("§cERREUR DANS>> §f" +s.getClassName()+":"+s.getMethodName()+":"+s.getLineNumber());
                        }
                    }
                }
            }
        }
    }
}
