package be.alexandre01.inazuma.uhc.utils;

import lombok.Data;
import org.bukkit.Sound;
@Data
public class SoundProperty {

    private final Sound sound;
    private final float v1;
    private final float v2;

    public SoundProperty(Sound sound, float v1, float v2){

        this.sound = sound;
        this.v1 = v1;
        this.v2 = v2;
    }
}
