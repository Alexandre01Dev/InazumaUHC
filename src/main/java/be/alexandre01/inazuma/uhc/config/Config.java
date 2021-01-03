package be.alexandre01.inazuma.uhc.config;


import be.alexandre01.inazuma.uhc.utils.Maven;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

public class Config {

    /*
    Created by Alexandre01
    */

    private Messages messages;
    private Options options;
    private HashMap<String,Options> otherOptions;
    public void load(Plugin plugin) {
        plugin.saveDefaultConfig();
        FileConfigurationOptions opt = plugin.getConfig().options();
        opt.header("# InazumaUHC Config "+ Maven.getVersion()+"\n");
        this.otherOptions = new HashMap<>();
        this.messages = new Messages(plugin);
        this.options = new Options(plugin,"config","opt");
        // Load different files
        otherOptions.put("worlds", new Options(plugin,"worlds",""));
        otherOptions.put("worldsTemp",new Options(plugin,"worldsTemp",""));

    }

    public static String getPath(String path){
        String resourcePath = null;
        if(System.getProperty("os.name").startsWith("Windows")){
            resourcePath = path.replaceAll("/","\\\\");
        }else {
            resourcePath = path;
        }
        return resourcePath;
    }
    public static boolean contains(String path){
        File theDir = new File(getPath(path));
        if (theDir.exists()) {
            return true;
        }
        return false;
    }
    public static void createDir(String path){
        File theDir = new File(getPath(path));

// if the directory does not exist, create it
        if (!theDir.exists()) {
            System.out.println("creating directory... " + theDir.getName());
            boolean result = false;

            try{
                theDir.mkdirs();
                result = true;
            }
            catch(SecurityException se){
                //handle it
            }
            if(result) {
                System.out.println("DIR created");
            }
        }
    }
    public static boolean removeDir(String path){
        while (contains(path)){
            File theDir = new File(getPath(path));

// if the directory does not exist, create it
            if (theDir.exists()) {
                File[] allContents = theDir.listFiles();
                if (allContents != null) {
                    for (File file : allContents) {
                        removeDir(file.getAbsolutePath());
                    }
                }
                return theDir.delete();

            }
        }
        return false;

    }
    public static void copy(File sourceLocation, File targetLocation) throws IOException {
        if (sourceLocation.isDirectory()) {
            copyDirectory(sourceLocation, targetLocation);
        } else {
            copyFile(sourceLocation, targetLocation);
        }
    }

    private static void copyDirectory(File source, File target) throws IOException {
        if (!target.exists()) {
            target.mkdir();
        }

        for (String f : source.list()) {
            copy(new File(source, f), new File(target, f));
        }
    }

    private static void copyFile(File source, File target) throws IOException {
        try (
                InputStream in = new FileInputStream(source);
                OutputStream out = new FileOutputStream(target)
        ) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        }
    }

}


