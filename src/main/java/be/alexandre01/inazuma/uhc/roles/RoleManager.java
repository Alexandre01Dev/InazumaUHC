package be.alexandre01.inazuma.uhc.roles;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.roles.listeners.DropRoleItemEvent;
import be.alexandre01.inazuma.uhc.roles.listeners.InteractItemEvent;
import be.alexandre01.inazuma.uhc.roles.listeners.PlayerInstantDeath;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
@Data
public class RoleManager {
    private int i = 0;

    private ArrayList<Role> totalRole;
    private final HashMap<UUID,Role> roles;
    private final HashMap<Role,List<UUID>> uuids;
    private final HashMap<Class<?>,Role> classes;
    public HashMap<RoleCategory,Role> rolesByCat;
    public HashMap<Class<?>,RoleCategory> roleCategories;

    public RoleManager(){
        uuids = new HashMap<>();
        roles = new HashMap<>();
        classes = new HashMap<>();
        roleCategories = new HashMap<>();
        rolesByCat = new HashMap<>();
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

    public Role addRole(UUID uuid){
        if(totalRole.size() < i+1){
            i = 0;
        }
        Role role = totalRole.get(i);
        addRole(uuid,role);
        i++;

        return role;
    }
    public void addRole(UUID uuid, Role role){
        Player player = Bukkit.getPlayer(uuid);
        if(player == null)
            return;

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
            role.getLoad().a(player);
        }

        if(!role.listeners.isEmpty()){
            if(!role.isListenerDeployed){
                role.deployListeners();
            }
        }

        if(!role.getCommands().isEmpty()){
            role.loadCommands();
        }


    }
    public void distributeRoles(ArrayList<Player> players){
        System.out.println("distribute role");
        ArrayList<Player> p = new ArrayList<>(players);
        Collections.shuffle(p);

        System.out.println(p.size());
        totalRole = new ArrayList<>(Role.getRoles());
        Collections.shuffle(totalRole);
        System.out.println(totalRole.size());
        for(Player player: p){
            if(getRole(player) != null){
                continue;
            }
            if(totalRole.size() < i+1){
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
