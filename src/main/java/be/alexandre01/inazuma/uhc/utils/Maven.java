package be.alexandre01.inazuma.uhc.utils;

import java.io.InputStream;
import java.util.Properties;

public class Maven {
    private static boolean loaded = false;
    private static String version;

    private static void load(){
        version = getPrivateVersion();
        loaded = true;
    }

    public static String getVersion() {
        if(!loaded)
            load();
        return version;
    }

    private static String getPrivateVersion() {
        String version = null;

        // try to load from maven properties first
        try {
            Properties p = new Properties();
            InputStream is = Maven.class.getResourceAsStream("/META-INF/maven/be.alexandre01.inazuma.uhc/InazumaUHC/pom.properties");
            if (is != null) {
                p.load(is);
                version = p.getProperty("version", "");
            }
        } catch (Exception e) {
            // ignore
        }

        // fallback to using Java API
        if (version == null) {
            Package aPackage = Maven.class.getPackage();
            if (aPackage != null) {
                version = aPackage.getImplementationVersion();
                if (version == null) {
                    version = aPackage.getSpecificationVersion();
                }
            }
        }

        if (version == null) {
            version = "unknown-version";
        }

        return version;
    }
}
