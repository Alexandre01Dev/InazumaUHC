package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.managers.player.PlayerMovementManager;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Scotty extends Role {

    public Scotty() {
        super("Scotty Banyan");
        setRoleCategory(Raimon.class);
        onLoad(new load() {
            @Override
            public void a(Player player) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0,false,false), true);

            }

        });

        RoleItem roleItem = new RoleItem();
        roleItem.setItemstack(new ItemStack(Material.WEB));
        roleItem.setPlaceableItem(false);

        roleItem.setPlaceBlock(new RoleItem.PlaceBlock() {
            @Override
            public void execute(Player player, BlockPlaceEvent event) {
                player.getItemInHand().setType(Material.AIR);

                InazumaUHC.get.playerMovementManager.addBlockLocation(event.getBlock().getLocation(), new PlayerMovementManager.action() {
                    @Override
                    public void a(Player player) {
                        player.sendMessage("PASSE DESSUS !");
                    }
                });
            }
        });
        addRoleItem(roleItem);
    }
}

