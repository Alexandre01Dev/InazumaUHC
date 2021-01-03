package be.alexandre01.inazuma.uhc.config;

import be.alexandre01.inazuma.uhc.config.yaml.YamlUtils;
import be.alexandre01.inazuma.uhc.utils.CastObject;
import org.bukkit.configuration.ConfigurationSection;

public class OptionModifier extends CastObject {
    private ConfigurationSection c;
    private String n;
    private YamlUtils y;
    public OptionModifier(ConfigurationSection c, String n, YamlUtils y) {
        super(c.get(n));
        this.c = c;
        this.n = n;
        this.y = y;
    }

    public OptionModifier set(Object value){
        c.set(n,value);
        y.save();
        super.setObject(value);
        return this;
    }
}
