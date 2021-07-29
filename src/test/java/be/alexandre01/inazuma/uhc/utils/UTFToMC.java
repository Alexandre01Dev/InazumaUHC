package be.alexandre01.inazuma.uhc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class UTFToMC {
    static HashMap<String, String> c = new HashMap();
    public static void main(String[] args) throws IOException {

        c.put("u00a74","§4");
        c.put("u00a7l","§l");
        c.put("u00a7c","§c");
        c.put("u00a76","§6");
        c.put("u00a7e","§e");
        c.put("u00a72","§2");
        c.put("u00a7a","§a");
        c.put("u00a7b","§b");
        c.put("u00a73","§3");
        c.put("u00a71","§1");
        c.put("u00a79","§9");
        c.put("u00a7d","§d");
        c.put("u00a75","§5");
        c.put("u00a7f","§f");
        c.put("u00a77","§7");
        c.put("u00a78","§8");
        c.put("u00a70","§0");

        System.out.println("Ecris ici >");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));


        String name = reader.readLine();


        System.out.println(convert(name));
    }

    public static String convert(String s){
        for(String k : c.keySet()){
            if(s.contains(k)){
                s = s.replaceAll(k,"\\\\"+c.get(k));
            }
        }

        return s;
    }
}
