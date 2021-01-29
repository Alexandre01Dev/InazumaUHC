package be.alexandre01.inazuma.uhc.roles;

import java.util.HashMap;
import java.util.UUID;

public class RoleManager {
    private HashMap<UUID,Role> roles;
    public HashMap<RoleCategory,Role> rolesByCat;

    public RoleManager(){
        roles = new HashMap<>();
    }

    public void addRole(UUID uuid, Role role){
        roles.put(uuid,role);
        if(role.getRoleCategory() != null){
            rolesByCat.put(role.getRoleCategory(),role);
        }
    }

    public void remRole(UUID uuid){
        roles.remove(uuid);
    }
}
