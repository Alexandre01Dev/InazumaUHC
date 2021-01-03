package be.alexandre01.inazuma.uhc.listeners;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ListenersManager {
    public HashMap<Class,Listener> listeners;
    private Plugin pl;
    private PluginManager pluginManager;
    public ListenersManager(){
        this.listeners = new HashMap<>();
        this.pl = InazumaUHC.get;
        this.pluginManager = pl.getServer().getPluginManager();
    }

    public void addListener(Listener listener){
        pluginManager.registerEvents(listener,pl);
        listeners.put(listener.getClass(),listener);
    }

    public void removeListener(Class listener){
        listeners.remove(listener);
    }

    public void unregisterListener(Class listener){
        HandlerList.unregisterAll(listeners.get(listener));
    }
    public void unregisterListener(Listener listener){
        HandlerList.unregisterAll(listener);
    }


    public void searchPresetListener(){
                IPreset p = Preset.instance.p;
                ArrayList<Listener> listeners = p.getListeners();
                if(listeners != null){
                    if(!listeners.isEmpty()){
                        for(Listener l : listeners){
                            addListener(l);
                        }
                    }
            }
    }
    public void automaticFindListener(){
        IPreset p = Preset.instance.p;
        String pack = "be.alexandre01.inazuma.uhc.presets."+ p.getPackageName()+".listeners";
        System.out.println(pack);
            Reflections reflections = new Reflections(pack);

            Set<Class<? extends Object>> allClasses =
                    reflections.getSubTypesOf(Object.class);

            ArrayList<String> classes = null;
            try {
               classes= getClassNamesFromPackage(pack);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            if(classes == null){
                System.out.println("classes = null");
                return;
            }
            System.out.println(classes.size());
            if(!classes.isEmpty()){
                for(String s : classes){

                    try {
                        Class c = Class.forName(pack+"."+s);
                        System.out.println(s);
                        System.out.println(c.getCanonicalName());
                        Listener listener = (Listener) c.newInstance();
                        pluginManager.registerEvents(listener,pl);
                        listeners.put(c,listener);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return;

        }
        System.out.println(p.getName()+" n'a pas de listeners");
    }

    private boolean checkPackage(String pack){
        try {
            System.out.println(pack);
            Class.forName(pack);

            return true;
        } catch(Exception e) {
            return false;
        }
    }
    public static ArrayList<String> getClassNamesFromPackage(String packageName) throws IOException, URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL packageURL;
        ArrayList<String> names = new ArrayList<String>();;

        packageName = packageName.replace(".", "/");
        packageURL = classLoader.getResource(packageName);

        if(packageURL.getProtocol().equals("jar")){
            String jarFileName;
            JarFile jf ;
            Enumeration<JarEntry> jarEntries;
            String entryName;

            // build jar file name, then loop through zipped entries
            jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
            jarFileName = jarFileName.substring(5,jarFileName.indexOf("!"));
            System.out.println(">"+jarFileName);
            jf = new JarFile(jarFileName);
            jarEntries = jf.entries();
            while(jarEntries.hasMoreElements()){
                entryName = jarEntries.nextElement().getName();
                if(entryName.startsWith(packageName) && entryName.length()>packageName.length()+5){
                    entryName = entryName.substring(packageName.length(),entryName.lastIndexOf('.'));
                    names.add(entryName);
                }
            }

            // loop through files in classpath
        }else{
            URI uri = new URI(packageURL.toString());
            File folder = new File(uri.getPath());
            // won't work with path which contains blank (%20)
            // File folder = new File(packageURL.getFile());
            File[] contenuti = folder.listFiles();
            String entryName;
            for(File actual: contenuti){
                entryName = actual.getName();
                entryName = entryName.substring(0, entryName.lastIndexOf('.'));
                names.add(entryName);
            }
        }
        return names;
    }
}
