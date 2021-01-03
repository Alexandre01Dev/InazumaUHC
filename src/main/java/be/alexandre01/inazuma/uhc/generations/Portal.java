package be.alexandre01.inazuma.uhc.generations;

import be.alexandre01.inazuma.uhc.utils.Cuboid;
import org.bukkit.Location;

public class Portal extends Cuboid {
    private boolean used = false;
    public Portal(Location loc1, Location loc2) {
        super(loc1, loc2);
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
