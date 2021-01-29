package be.alexandre01.inazuma.uhc.roles;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;

public class Role {
    private String name;
    private load load;
    private static HashMap<Class, ArrayList<Role>> rolesByInstance;
    private RoleCategory roleCategory = null;
    public Role(String name,Class c){
        this.name = name;
        if(!rolesByInstance.containsKey(this.getClass())){
            rolesByInstance.put(this.getClass(),new ArrayList<>());
        }
        ArrayList<Role> d = new ArrayList<>(rolesByInstance.get(c));
        d.add(this);
        rolesByInstance.put(this.getClass(),d);

        if(!rolesByInstance.containsKey(c)){
            rolesByInstance.put(c,new ArrayList<>());
        }
        ArrayList<Role> e = new ArrayList<>(rolesByInstance.get(c));
        e.add(this);
        rolesByInstance.put(c,e);
    }

    public void onLoad(load load){
        this.load = load;
    }
    public void setRoleCategory(RoleCategory roleCategory){
        this.roleCategory = roleCategory;
    }
    public void addListener(Listener listener){
        InazumaUHC.get.lm.addListener(listener);
    }

    public String getName() {
        return name;
    }

    public Role.load getLoad() {
        return load;
    }

    public RoleCategory getRoleCategory() {
        return roleCategory;
    }

    public interface load{
        public void a();
    }

    public static ArrayList<Role> getRoles(Class<?> c){
        return rolesByInstance.get(c);
    }
}
