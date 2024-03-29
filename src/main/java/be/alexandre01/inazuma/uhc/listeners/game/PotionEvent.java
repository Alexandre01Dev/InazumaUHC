package be.alexandre01.inazuma.uhc.listeners.game;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.managers.damage.DamageManager;
import be.alexandre01.inazuma.uhc.utils.WeaponItem;
import net.minecraft.server.v1_8_R3.AttributeModifiable;
import net.minecraft.server.v1_8_R3.AttributeModifier;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class PotionEvent implements Listener {
    private final InazumaUHC inazumaUHC;

    public PotionEvent(){
        this.inazumaUHC = InazumaUHC.get;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageByEntityEvent event){
        //System.out.println("DEFAULT base >>" + event.getDamage(EntityDamageEvent.DamageModifier.BASE));
        if(event.getDamager() instanceof Player){
            Player damager = (Player) event.getDamager();
            double resetDamage = getNewDamagePoint(damager);
            double damageIncreased = ((resetDamage) * (inazumaUHC.dm.getEffectPourcentage(damager, DamageManager.EffectType.INCREASE_DAMAGE)))+ +sharpnessCalc(damager.getItemInHand())+critCalc(damager);
            if(event.getEntity() instanceof Player){
                Player player = (Player) event.getEntity();
                damageIncreased = damageIncreased/inazumaUHC.dm.getEffectPourcentage(player, DamageManager.EffectType.RESISTANCE);
                event.setDamage(EntityDamageEvent.DamageModifier.RESISTANCE,0);

            }
            event.setDamage(damageIncreased);






        }
    }


    @EventHandler
    public void onEnchant(EnchantItemEvent event){
        WeaponItem w = inazumaUHC.dm.getOrCreate(event.getItem());
        w.setSharpness(w.sharpnessCalc());
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event){
      inazumaUHC.dm.getOrCreate(event.getItem().getItemStack());
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event){
        inazumaUHC.dm.getOrCreate(event.getCurrentItem());
    }
    private double getDamagePoint(Player player){
        EntityPlayer nmsPlayer =((CraftPlayer)player).getHandle();
                AttributeModifiable a = (AttributeModifiable) nmsPlayer.getAttributeMap().a("generic.attackDamage");
                if(a == null){
                    return 1;
                }

                    for(AttributeModifier m : a.c()){
                        if(m.b().equalsIgnoreCase("Weapon modifier")){
                            return m.d()+1D;
                        }
                    return 1D;
                }

        return 1;
    }
    private double getNewDamagePoint(Player player){
        WeaponItem w = inazumaUHC.dm.getOrCreate(player.getItemInHand());
        return w.damage+w.sharpness+1;
    }

    private double sharpnessCalc(ItemStack it){
        if(!it.containsEnchantment(Enchantment.DAMAGE_ALL)){
            return 0;
        }

       return 0.5 * it.getEnchantmentLevel(Enchantment.DAMAGE_ALL) + 0.5;
    }

    private double critCalc(Player damager){
        if(damager.getFallDistance() > 0.0F && !damager.isOnGround() && !damager.hasPotionEffect(PotionEffectType.BLINDNESS) && damager.getVehicle() == null){
            return 0.25D;
        }
        return 0;
    }


}
