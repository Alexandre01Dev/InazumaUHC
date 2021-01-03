package be.alexandre01.inazuma.uhc.config.Exception;


import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.config.Messages;

public class DefaultLanguageNotSetException extends Exception{
    public DefaultLanguageNotSetException(Messages msg){
        super("Default language not found or set, try to find other language");
        if(!msg.getLangs().isEmpty()){
           msg.setDefaultLanguage(msg.getLangs().keySet().stream().findFirst().get());
           return;
        }
        System.out.println("The InazumaUHC plugin has stopped because no language is set in the config.");
        InazumaUHC.get.getPluginLoader().disablePlugin(InazumaUHC.get);
        return;
    }
}
