package be.alexandre01.inazuma.uhc.modules;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.config.Config;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.PresetData;
import be.alexandre01.inazuma.uhc.presets.normal.scoreboards.GameScoreboard;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleManager;
import be.alexandre01.inazuma.uhc.state.GameState;
import lombok.SneakyThrows;
import lombok.val;
import lombok.var;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SerializationUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarFile;

public class ModuleLoader {
    private File dir;
    private final ArrayList<Module> modules = new ArrayList<>();

    public ModuleLoader(Plugin plugin){
        try {
            String[] dirs = plugin.getConfig().getStringList("config.modules-dirs").toArray(new String[0]);
            for(String dPath : dirs){
                dir = new File(dPath.replaceAll("%plugin_dir%",plugin.getDataFolder().getAbsolutePath()));
                for(File file : Objects.requireNonNull(dir.listFiles())){
                    if(file.isDirectory())
                        continue;
                    CustomClassLoader child = new CustomClassLoader(
                            file.toURI().toURL(),
                            this.getClass().getClassLoader()
                    );
                    /*URLClassLoader child = (URLClassLoader) ClassLoader.getSystemClassLoader();

                    Method m = URLClassLoader.class.getDeclaredMethod("addURL", new  Class[]{URL.class});
                    m.setAccessible(true);
                    m.invoke(child, file.toURI().toURL());
                    String cp = System.getProperty("java.class.path");
                    if (cp != null) {
                        cp += File.pathSeparatorChar + file.getCanonicalPath();
                    } else {
                        cp = file.toURI().getPath();
                    }
                    System.setProperty("java.class.path", cp);
                    */
                    Module module = null;
                    YamlConfiguration yamlConfiguration = new YamlConfiguration();
                    if(child.getResource("module.yml") != null){

                        yamlConfiguration.load(child.getResourceAsStream("module.yml"));
                        for (String k : yamlConfiguration.getKeys(false)){
                            System.out.println("KEY>>"+ k);
                        }

                        module = new Module();
                        module.setFile(file);
                        module.setPresetPath(yamlConfiguration.getString("module-path"));
                        module.setModuleName(yamlConfiguration.getString("module-name"));

                        module.setVersion(yamlConfiguration.getString("version"));
                        module.setAuthors(yamlConfiguration.getStringList("authors").toArray(new String[0]));

                        module.setDescription(yamlConfiguration.getString("description"));

                        module.setMaterial(Material.getMaterial(yamlConfiguration.getInt("itemId")));

                        module.setChild(child);
                    }
                    if(module == null)
                        return;

                    Class classToLoad = Class.forName(module.getPresetPath(), true, child);
                    module.setPresetClass(classToLoad);

                    System.out.println(module.getModuleName());
                    Preset.instance.add(module.getModuleName(), module);

                    modules.add(module);

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public boolean reloadModule(Module module){
        HashMap<Player,Role> classRoles = new HashMap<>();
         boolean r = false;
        System.out.println(module.getModuleName()+" | "+ Arrays.toString(module.getAuthors()));
        if(Preset.instance.m == module){
            System.out.println("hmm reload maybe");
            r = true;
        }
        Module m = new Module();
        if(r){
            for(Role role : Role.getRoles()){
                for(Player player: role.getPlayers()){
                    classRoles.put(player,role);
                    InazumaUHC.get.rm.getRoles().clear();
                    InazumaUHC.get.rm.getClasses().clear();
                    InazumaUHC.get.rm.getRoleCategories().clear();
                }
            }
            Role.clear();
        }
        File file = module.getFile();
        if(module.getFile() == null){
            return false;
        }
        CustomClassLoader child;
        try{

            ((CustomClassLoader)module.getChild()).close();
        System.gc();
        Thread.sleep(3000);
            /*URLClassLoader child = (URLClassLoader) ClassLoader.getSystemClassLoader();

            Method m = URLClassLoader.class.getDeclaredMethod("addURL", new  Class[]{URL.class});
            m.setAccessible(true);
            m.invoke(child, file.toURI().toURL());
            String cp = System.getProperty("java.class.path");
            if (cp != null) {
                cp += File.pathSeparatorChar + file.getCanonicalPath();
            } else {
                cp = file.toURI().getPath();
            }
            System.setProperty("java.class.path", cp);*/
        child = new CustomClassLoader(
               module.getFile().toURI().toURL(),
                this.getClass().getClassLoader()
        );
            Class classToLoad = Class.forName(module.getPresetPath(), true, child);
            module.setPresetClass(classToLoad);

        module.setChild(child);
        modules.remove(module);
        Preset.instance.remove(module.getModuleName(),module);



        YamlConfiguration yamlConfiguration = new YamlConfiguration();

        if(module == null)
            return false;

        System.out.println(module.getModuleName());
        Preset.instance.add(module.getModuleName(), module);

        modules.add(module);

    }catch (Exception e){
            e.printStackTrace();
            return false;
    }

        if(r){
            System.out.println("YES RELOAD");
            for(Object o : Preset.instance.pData.getTimers()){
                System.out.println("TIMERS"+o);
                replacePreset(o);
            }

            for(Object o : Preset.instance.pData.getListeners()){
                replacePreset(o);
            }

            RoleManager roleManager = InazumaUHC.get.rm;
            Preset.instance.set(module);
            InazumaUHC.get.onLoadPreset();


            for(Player player : classRoles.keySet()){
                ArrayList<Class<?>> classes  = new ArrayList<>();
                for(Role role : Role.getRoles()){
                    System.out.println(InazumaUHC.get.lm);
                    System.out.println(InazumaUHC.get.lm.listeners);
                    System.out.println(InazumaUHC.get.lm.listeners.values());
                    classes.clear();
                    for(Listener oL : InazumaUHC.get.lm.listeners.values()){
                        for(Listener l : role.listeners){
                            System.out.println(oL.getClass().getName());
                            System.out.println(l.getClass().getName());
                            if(oL.getClass().getName().equals(l.getClass().getName())){
                                System.out.println("OK");
                                try {
                                    InazumaUHC.get.lm.unregisterListener(oL.getClass());
                                    classes.add(oL.getClass());
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                    for(Class<?> c : classes){
                        InazumaUHC.get.lm.listeners.remove(c);
                    }


                    if(role.getClass().getName().equals(classRoles.get(player).getClass().getName())){
                        var load = role.getLoad();
                        role.onLoad(null);
                        InazumaUHC.get.rm.addRole(player.getUniqueId(),role);
                        System.out.println("addRole");
                        role.onLoad(load);
                       // role.addPlayer(player);
                        if(!role.isListenerDeployed){
                            role.deployListeners();
                        }
                        if(!role.isListenerDeployed){
                            role.loadCommands();
                        }

                    }
                }

            }
            Role.isDistributed = true;



            //SCOREBOARD
            Object o = InazumaUHC.get.getScoreboardManager().scoreboardInitializer;




            if(o!= null){
                try {
                    Class classToLoad = Class.forName(o.getClass().getName(), true, child);
                    Object obj = classToLoad.getDeclaredConstructor(PresetData.class).newInstance(Preset.instance.pData);
                    Method me =  classToLoad.getMethod("setScoreboard");
                    me.invoke(obj);
                    InazumaUHC.get.getScoreboardManager().refreshPlayers(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            //GameState.get().setTo(GameState.get().getState());
        }
        return true;
    }

    private static class CustomClassLoader extends URLClassLoader {
        public CustomClassLoader(URL url,ClassLoader parent) {
            super(new URL[] { url }, parent);
        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve)
                throws ClassNotFoundException {
            try {
                return super.loadClass(name, resolve);
            } catch (ClassNotFoundException e) {
                return Class.forName(name, resolve, InazumaUHC.class.getClassLoader());
            }
        }
        @Override
        public void close() {
            try {
                System.out.println("Custom close");
                Class clazz = java.net.URLClassLoader.class;
                Field ucp = clazz.getDeclaredField("ucp");
                ucp.setAccessible(true);
                Object sunMiscURLClassPath = ucp.get(this);
                Field loaders = sunMiscURLClassPath.getClass().getDeclaredField("loaders");
                loaders.setAccessible(true);
                Object collection = loaders.get(sunMiscURLClassPath);
                for (Object sunMiscURLClassPathJarLoader : ((Collection) collection).toArray()) {
                    try {
                        Field loader = sunMiscURLClassPathJarLoader.getClass().getDeclaredField("jar");
                        loader.setAccessible(true);
                        Object jarFile = loader.get(sunMiscURLClassPathJarLoader);
                        ((JarFile) jarFile).close();
                    } catch (Throwable t) {
                        // if we got this far, this is probably not a JAR loader so skip it
                    }
                }
            } catch (Throwable t) {
                // probably not a SUN VM
            }
            return;
        }
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println(this.toString() + " - CL Finalized.");
        }
    }
    public void replacePreset(Object object){
        for(Field field : object.getClass().getFields()){
            System.out.println("field >> "+field);
        if(field.getType() == IPreset.class){
            try {
                System.out.println("PRESET !");
                field.setAccessible(true);
                field.set(object,Preset.instance.p);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(field.getType() == PresetData.class){
            try {
                System.out.println("PRESETdata !");
                field.setAccessible(true);
                field.set(object,Preset.instance.pData);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
        for(Field field : object.getClass().getDeclaredFields()){
            System.out.println("declared field >> "+field);
            if(field.getType() == IPreset.class){
                try {
                    System.out.println("PRESET !");
                    field.setAccessible(true);
                    field.set(object,Preset.instance.p);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if(field.getType() == PresetData.class){
                try {
                    System.out.println("PRESETdata !");
                    field.setAccessible(true);
                    field.set(object,Preset.instance.pData);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void searchAllObject(Object object){
        for(Field field : object.getClass().getFields()){
            searchForField(field,object);
        }
        for(Field field : object.getClass().getDeclaredFields()){
            searchForField(field,object);
        }
        for( Method method : object.getClass().getMethods()){
            try {
                searchAllObject(method.invoke(object));
            }catch (Exception ignored){

            }
        }

        for( Method method : object.getClass().getMethods()){
            try {
                searchAllObject(method.invoke(object));
            }catch (Exception ignored){

            }
        }
    }

    public void searchForField(Field field,Object object){
        if(field.getType() == List.class){
            try {
                List<?> l = (List<?>) field.get(object);
                for(Object o : l){
                    searchAllObject(o);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(field.getType() == ArrayList.class){
            try {
                ArrayList<?> l = (ArrayList<?>) field.get(object);
                for(Object o : l){
                    searchAllObject(o);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(field.getType() == HashMap.class){
            try {
                HashMap<?,?> l = (HashMap<?,?>) field.get(object);
                for(Object o : l.keySet()){
                    searchAllObject(o);
                }
                for(Object o : l.values()){
                    searchAllObject(o);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(field.getType() == IPreset.class){
            try {
                field.setAccessible(true);
                field.set(object,Preset.instance.p);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(field.getType() == PresetData.class){
            try {
                field.setAccessible(true);
                field.set(object,Preset.instance.pData);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
