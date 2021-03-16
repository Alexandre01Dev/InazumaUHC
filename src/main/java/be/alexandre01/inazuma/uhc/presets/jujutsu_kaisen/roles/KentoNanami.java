package be.alexandre01.inazuma.uhc.presets.jujutsu_kaisen.roles;

import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import be.alexandre01.inazuma.uhc.utils.PatchedEntity;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class KentoNanami extends Role {
    public KentoNanami(IPreset preset) {
        super("Kento Nanami",preset);

        RoleItem rayonNoir = new RoleItem();
        rayonNoir.setItemstack(new ItemStack(Material.NETHERRACK));

        rayonNoir.setRightClick(player -> {
            Vector p = player.getLocation().toVector().normalize();
            player.getWorld().playEffect(player.getLocation(), Effect.EXPLOSION,10,30);
            player.getWorld().playSound(player.getLocation(), Sound.EXPLODE,1.5f,1);
            for(Entity entity : player.getWorld().getNearbyEntities(player.getLocation(),8,8,8)){
                if(entity.equals(player)){
                    continue;
                }
                if(entity instanceof LivingEntity){
                    LivingEntity livingEntity = (LivingEntity) entity;
                    PatchedEntity.damage(livingEntity,6);
                    if(livingEntity instanceof Player){
                        ((Player)livingEntity).playSound(livingEntity.getLocation(), Sound.BAT_HURT,0.8f,1f);
                    }
                    Vector v = entity.getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(4);
                    v = v.add(new Vector(0,1,0));
                    if(v.getBlockY() >= 1){
                        v =  v.setY(1);
                    entity.setVelocity(v.clone());
                        System.out.println(p.clone().toString());
                    p = p.add(v.clone().multiply(-1));
                        System.out.println(p.clone().toString());
                }
                }
                }
                 System.out.println(p.clone().toString());
                player.setVelocity(p.clone());
            });
            addRoleItem(rayonNoir);
        }

}
