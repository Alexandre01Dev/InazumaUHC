package be.alexandre01.inazuma.uhc.config.Exception;


import be.alexandre01.inazuma.uhc.config.Messages;

public class LanguageNotFoundException extends Exception{
    public LanguageNotFoundException(String lang,String lastLang, Messages msg) {
        super((String) "Language "+ lang+" cannot be found, replacing by another language");
        if(lastLang!= null){
            msg.setLanguage(lastLang);
            return;
        }
        msg.setLanguage(msg.getDefaultLanguage());
        return;
    }
}
