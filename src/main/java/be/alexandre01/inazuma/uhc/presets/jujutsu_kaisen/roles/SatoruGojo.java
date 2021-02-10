package be.alexandre01.inazuma.uhc.presets.jujutsu_kaisen.roles;

import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SatoruGojo extends Role implements Listener {
    public SatoruGojo() {
        super("Satoru Gojo",SatoruGojo.class);
        RoleItem territory = new RoleItem();
        territory.setItemstack(new ItemStack(Material.COBBLESTONE));
        territory.setRightClickOnPlayer((player, rightClicked) -> {
            player.sendMessage("Tu as click droit sur "+rightClicked.getName());
            rightClicked.sendMessage("Ouille ouille ouille, ça va piquer pour toi mon cher ami");
        });



        addRoleItem(territory);
        setRoleToSpoil(ItadoriYuji.class);
        onLoad(() -> {
            for(Player player : getPlayers()){
                addHearth(player,2);
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1,false,false), true);
            }
        });
    }
}
