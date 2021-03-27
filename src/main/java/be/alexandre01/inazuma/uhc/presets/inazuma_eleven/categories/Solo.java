package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories;

import be.alexandre01.inazuma.uhc.roles.RoleCategory;

public class Solo extends RoleCategory {
    public Solo(String category, String prefixColor) {
        super(category, prefixColor);
        setDeathMessage(new String[]{"§4L'ange déchu§7 s'est abattu sur §cl'envoyé§7 des §6Dieux","§e%player% §7était §f: %role%"});
    }
}
