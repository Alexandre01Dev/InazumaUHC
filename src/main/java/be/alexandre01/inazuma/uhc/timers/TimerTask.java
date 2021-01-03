package be.alexandre01.inazuma.uhc.timers;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TimerTask implements BukkitTask {
    private static HashMap<Integer,TimerTask> timerTasks = new HashMap<>();
    private int task;
    private boolean isSync;

    public TimerTask(boolean isSync){
        this.isSync = isSync;
        int i = new Random().nextInt((200 - 1) + 1) + 1;
        while (timerTasks.containsKey(i)){
            i = new Random().nextInt((200 - 1) + 1) + 1;
        }
      this.task = i;

    }
    @Override
    public int getTaskId() {
        return this.task;
    }

    @Override
    public Plugin getOwner() {
        return InazumaUHC.get;
    }

    @Override
    public boolean isSync() {
        return isSync;
    }

    @Override
    public void cancel() {

    }
}
