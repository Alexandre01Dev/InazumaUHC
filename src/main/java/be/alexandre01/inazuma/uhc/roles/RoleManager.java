package be.alexandre01.inazuma.uhc.roles;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.roles.listeners.DropRoleItemEvent;
import be.alexandre01.inazuma.uhc.roles.listeners.InteractItemEvent;
import be.alexandre01.inazuma.uhc.roles.listeners.PlayerInstantDeath;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class RoleManager {
    private HashMap<UUID,Role> roles;
    private HashMap<Role,List<UUID>> uuids;
    private HashMap<Class<?>,Role> classes;
    public HashMap<RoleCategory,Role> rolesByCat;
    public HashMap<Class<?>,RoleCategory> roleCategories;

    public RoleManager(){
        roleCategories = new HashMap<>();
        uuids = new HashMap<>();
        roles = new HashMap<>();
        classes = new HashMap<>();
        InazumaUHC.get.lm.addListener(new InteractItemEvent());
        InazumaUHC.get.lm.addListener(new DropRoleItemEvent());
        InazumaUHC.get.lm.addListener(new PlayerInstantDeath());
    }

    public Role getRole(UUID uuid){
        return roles.get(uuid);
    }
    public Role getRole(Player player){
        return roles.get(player.getUniqueId());
    }
    public Role getRole(Class<?> c){
        return classes.get(c);
    }
    public List<UUID> getUUIDS(Role role){
        return uuids.get(role);
    }

    public void addRole(UUID uuid, Role role){
        roles.put(uuid,role);
        classes.put(role.getClass(),role);
        List<UUID> p = new ArrayList<>();
        if(uuids.containsKey(role)){
            p.addAll(uuids.get(role));
        }
        p.add(uuid);
        uuids.put(role,p);
        if(role.getRoleCategory() != null){
            rolesByCat.put(role.getRoleCategory(),role);
        }
        role.addPlayer(Bukkit.getPlayer(uuid));
        if(role.getLoad() != null){
            role.getLoad().a();
        }
        if(!role.getCommands().isEmpty()){
            role.loadCommands();
        }

    }
    public void distributeRole(ArrayList<Player> players){
        System.out.println("distribute role");
        ArrayList<Player> p = new ArrayList<>(players);
        Collections.shuffle(p);

        int i = 0;
        System.out.println(p.size());
        ArrayList<Role> totalRole = new ArrayList<>(Role.getRoles());
        Collections.shuffle(totalRole);
        System.out.println(totalRole.size());
        for(Player player: p){
            if(totalRole.size() < i+1){
                System.out.println("wow");
                i = 0;
            }
            addRole(player.getUniqueId(),totalRole.get(i));
            i++;
        }
    }
    public boolean containsRole(Player player){
        if(roles.containsKey(player.getUniqueId())){
            return true;
        }
        return false;
    }

    public boolean containsRole(UUID uuid){
        if(roles.containsKey(uuid)){
            return true;
        }
        return false;
    }
    public void remRole(UUID uuid){
        roles.remove(uuid);
    }
    public RoleCategory getRoleCategory(Class<?> c) {
        return roleCategories.get(c);
    }
    public HashMap<Class<?>, RoleCategory> getRoleCategories() {
        return roleCategories;
    }

    public void addRoleCategory(RoleCategory r){
        roleCategories.put(r.getClass(),r);
    }
}
