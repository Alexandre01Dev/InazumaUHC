package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.utils.CustomComponentBuilder;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Raimon_Simple extends Role {
    public Raimon_Simple(IPreset preset) {
        super("Joueur Raimon",preset);
        setRoleCategory(Raimon.class);

        addDescription("§8- §7Votre objectif est de gagner avec §6§lRaimon");
        addDescription("§8- §7Vous possédez §e2 gapples §7suplémentaires.");

        onLoad(new load() {
            @Override
            public void a(Player player) {
                    player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE,2));
            }
        });
    }
}



