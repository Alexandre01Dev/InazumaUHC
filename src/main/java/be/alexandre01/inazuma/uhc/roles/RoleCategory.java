package be.alexandre01.inazuma.uhc.roles;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class RoleCategory {
    String name;
    @Getter @Setter
    String[] deathMessage = {"§e%player%§7 est §cmort(e)§7","§7 Son rôle était §f: %role%"};
    String prefixColor;
    String winMessage;
    private ArrayList<Role> roles = new ArrayList<>();

    public RoleCategory(String name, String prefixColor, String winMessage){
        this.name = name;
        this.prefixColor = prefixColor;
        this.winMessage = winMessage;
        if(InazumaUHC.get.rm == null){
            InazumaUHC.get.rm = new RoleManager();
        }
        InazumaUHC.get.rm.addRoleCategory(this);
    }

    public void addRole(Role role){
        this.roles.add(role);
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void remRole(Role role){
        this.roles.remove(role);
    }

    public String getName() {
        return name;
    }

    public String getPrefixColor() {
        return prefixColor;
    }

    public String getWinMessage(){return winMessage;}
}
