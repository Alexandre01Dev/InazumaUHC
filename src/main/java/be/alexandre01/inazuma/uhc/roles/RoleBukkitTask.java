package be.alexandre01.inazuma.uhc.roles;

import lombok.Data;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
@Data
public class RoleBukkitTask implements BukkitTask {
    public onCancel onCancel;

    @Override
    public int getTaskId() {
        return 0;
    }

    @Override
    public Plugin getOwner() {
        return null;
    }

    @Override
    public boolean isSync() {
        return false;
    }

    @Override
    public void cancel() {
       this.onCancel.cancel();
    }

    public interface onCancel{
        public void cancel();
    }
}
