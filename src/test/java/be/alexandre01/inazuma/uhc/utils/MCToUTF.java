package be.alexandre01.inazuma.uhc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class MCToUTF {
    static HashMap<String, String> c = new HashMap();
    public static void main(String[] args) throws IOException {

        c.put("§4","u00a74");
        c.put("§l","u00a7l");
        c.put("§c","u00a7c");
        c.put("§6","u00a76");
        c.put("§e","u00a7e");
        c.put("§2","u00a72");
        c.put("§a","u00a7a");
        c.put("§b","u00a7b");
        c.put("§3","u00a73");
        c.put("§1","u00a71");
        c.put("§9","u00a79");
        c.put("§d","u00a7d");
        c.put("§5","u00a75");
        c.put("§f","u00a7f");
        c.put("§7","u00a77");
        c.put("§8","u00a78");
        c.put("§0","u00a70");
        c.put("é","u00e9");

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
