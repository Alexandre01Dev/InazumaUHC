package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.managers.player.PlayerMovementManager;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import be.alexandre01.inazuma.uhc.utils.Freeze;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import be.alexandre01.inazuma.uhc.utils.PatchedEntity;
import org.bukkit.Bukkit;
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
        roleItem.setItemstack(new ItemBuilder(Material.STRING,3).setName("§ePeau de banane").toItemStack());
        roleItem.setPlaceableItem(false);

        RoleItem cadenet = new RoleItem();
        cadenet.setItemstack(new ItemBuilder(Material.STRING,2).setName("§eCasiers Cadenet").toItemStack());

        cadenet.setPlaceableItem(false);


        roleItem.setPlaceBlock(new RoleItem.PlaceBlock() {
            @Override
            public void execute(Player player, BlockPlaceEvent event) {
                ItemStack itemStack = event.getItemInHand();
                Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(InazumaUHC.get, new Runnable() {
                    public void run() {
                        int amount = itemStack.getAmount()-1;
                        if(amount == 0){
                            player.setItemInHand(new ItemStack(Material.AIR));
                            return;
                        }

                        itemStack.setAmount(player.getItemInHand().getAmount()-1);
                    }
                }, 1L);

                event.setCancelled(true);
                PatchedEntity.cancelSound("dig.stone",event.getBlock().getLocation(),player);
                player.sendMessage(Preset.instance.p.prefixName()+" §ePiege posé !");
                inazumaUHC.playerMovementManager.addBlockLocation(event.getBlock().getLocation(), new PlayerMovementManager.action() {
                    @Override
                    public void a(Player player) {
                        if(!getPlayers().contains(player)){
                            player.sendMessage(Preset.instance.p.prefixName()+" §cTu viens de marcher sur une peau de banane.");
                            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3*20, 9,false,false), true);
                            inazumaUHC.playerMovementManager.remBlockLocation(event.getBlock().getLocation());
                        }
                    }
                });

            }
        });
        addRoleItem(roleItem);


    }
}

