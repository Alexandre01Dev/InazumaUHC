package be.alexandre01.inazuma.uhc.roles;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class RoleCategory {
    String category;
    String prefixColor;
    private ArrayList<Role> roles = new ArrayList<>();

    public RoleCategory(String category, String prefixColor){
        this.category = category;
        this.prefixColor = prefixColor;
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

    public String getCategory() {
        return category;
    }

    public String getPrefixColor() {
        return prefixColor;
    }
}
