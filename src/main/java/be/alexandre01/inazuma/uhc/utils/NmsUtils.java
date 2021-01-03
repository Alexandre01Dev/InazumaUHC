package be.alexandre01.inazuma.uhc.utils;

import org.bukkit.Bukkit;

import static org.joor.Reflect.*;

public class NmsUtils {
    private static final String nmsPackageName = on(Bukkit.getServer()).call("getHandle")
            .type()
            .getPackage()
            .getName();

    public static String getFullyQualifiedClassName(String className) {
        return nmsPackageName + "." + className;
    }

}
