package be.alexandre01.inazuma.uhc.roles;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class RoleCategory {
    String category;
    private ArrayList<Role> roles = new ArrayList<>();
    public RoleCategory(String category){
        this.category = category;
    }

    public void addRole(Role role){
        this.roles.add(role);
    }

    public void remRole(Role role){

    }
}
