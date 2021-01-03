package be.alexandre01.inazuma.uhc.config.Exception;

import be.alexandre01.inazuma.uhc.config.Options;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class test {
    public static void main(String[] args){
        InputStream resource = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.yml");

            System.out.println(resource);

    }
}
