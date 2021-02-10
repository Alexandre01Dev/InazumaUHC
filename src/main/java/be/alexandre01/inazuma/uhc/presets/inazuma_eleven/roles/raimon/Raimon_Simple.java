package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.roles.Role;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Raimon_Simple extends Role {
    public Raimon_Simple(String name, Class c) {
        super(name, c);

        onLoad(new load() {
            @Override
            public void a() {
                for(Player player : getPlayers()){
                    player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE,2));
                }
            }
        });
    }


}
