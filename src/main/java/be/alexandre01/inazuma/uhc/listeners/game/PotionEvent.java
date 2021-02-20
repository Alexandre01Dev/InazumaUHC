package be.alexandre01.inazuma.uhc.listeners.game;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.managers.DamageManager;
import javafx.scene.layout.Priority;
import net.minecraft.server.v1_8_R3.AttributeModifiable;
import net.minecraft.server.v1_8_R3.AttributeModifier;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class PotionEvent implements Listener {
    private final InazumaUHC inazumaUHC;

    public PotionEvent(){
        this.inazumaUHC = InazumaUHC.get;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageByEntityEvent event){

        System.out.println("DEFAULT base >>" + event.getDamage(EntityDamageEvent.DamageModifier.BASE));
        if(event.getDamager() instanceof Player){
            Player damager = (Player) event.getDamager();

          if(damager.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)){
                double resetDamage = getDamagePoint(damager.getPlayer())+sharpnessCalc(damager.getItemInHand());
                double damageIncreased = resetDamage* (inazumaUHC.dm.getEffectPourcentage(damager, DamageManager.EffectType.INCREASE_DAMAGE));
                System.out.println("RESET base >>" + resetDamage);
                System.out.println("DEFAULT base >>" + damageIncreased);
                System.out.println(damageIncreased);
                try {
                    if(event.getEntity() instanceof Player){
                        Player player = (Player) event.getEntity();
                        System.out.println("DEFAULT RESISTANCE >>" + event.getDamage(EntityDamageEvent.DamageModifier.RESISTANCE));
                        //event.setDamage(EntityDamageEvent.DamageModifier.RESISTANCE,(damageIncreased/(inazumaUHC.dm.getEffectPourcentage(player, DamageManager.EffectType.RESISTANCE))));
                        System.out.println("DEFAULT RESISTANCE >>" + (damageIncreased*(inazumaUHC.dm.getEffectPourcentage(player, DamageManager.EffectType.RESISTANCE))));
                    }
                }catch (Exception e){}
                event.setDamage(EntityDamageEvent.DamageModifier.BASE,damageIncreased);
            }
        }
    }

    private double getDamagePoint(Player player){
        EntityPlayer nmsPlayer =((CraftPlayer)player).getHandle();
        for(Object o : nmsPlayer.getAttributeMap().a()){
            if(o instanceof AttributeModifiable){
                AttributeModifiable a = (AttributeModifiable) o;
                if(a.getAttribute().getName().equalsIgnoreCase("generic.attackDamage")){
                    System.out.println("MODIFIER SIZE >>"+a.c().size());
                    for(AttributeModifier m : a.c()){
                        if(m.b().equalsIgnoreCase("Weapon modifier")){
                            return m.d()+1D;
                        }
                    }
                    return 1D;
                }
            }
        }
        return 1;
    }

    private double sharpnessCalc(ItemStack it){
        if(!it.containsEnchantment(Enchantment.DAMAGE_ALL)){
            return 0;
        }

       return 0.5 * it.getEnchantmentLevel(Enchantment.DAMAGE_ALL) + 0.5;
    }


}
