package be.alexandre01.inazuma.uhc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class MCToUTF {
    static HashMap<String, String> c = new HashMap();
    public static void main(String[] args) throws IOException {

        c.put("§4","u00A74");
        c.put("§c","u00A7c");
        c.put("§6","u00A76");
        c.put("§e","u00A7e");
        c.put("§2","u00A72");
        c.put("§a","u00A7a");
        c.put("§b","u00A7b");
        c.put("§3","u00A73");
        c.put("§1","u00A71");
        c.put("§9","u00A79");
        c.put("§d","u00A7d");
        c.put("§5","u00A75");
        c.put("§f","u00A7f");
        c.put("§7","u00A77");
        c.put("§8","u00A78");
        c.put("§0","u00A70");

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
