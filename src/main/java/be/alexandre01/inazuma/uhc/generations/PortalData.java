package be.alexandre01.inazuma.uhc.generations;

import org.bukkit.Location;

public class PortalData {
    public Location dLoc;
    public Location oLoc;
    public boolean b;
    public PortalData(Location location, boolean b){
        this.dLoc = location;
        this.b = b;
    }

    public void setOtherLocation(Location oLocation){
        this.oLoc = oLocation;
    }


}
