package be.alexandre01.inazuma.uhc.scenarios.merite;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.player.PlayerInstantDeathEvent;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class Merite extends Scenario implements Listener {

    HashMap<Player, Float> swordMap = new HashMap<>();
    HashMap<Player, Float> armorMap = new HashMap<>();
    HashMap<Player, Float> bowMap = new HashMap<>();
    HashMap<Player, Float> swordPourcent = new HashMap<>();
    HashMap<Player, Float> armorPourcent = new HashMap<>();
    HashMap<Player, Float> bowPourcent = new HashMap<>();
    HashMap<Player, Integer> points = new HashMap<>();
    ArrayList<Player> players = new ArrayList<>();

    public Merite() {
        super("Merite", "Améliore le stuff");
        onLoad(() -> {
            InazumaUHC.get.registerCommand("boost", new MeriteBoostCommand("",this));
        });
        addListener(this);
        ItemBuilder itemBuilder = new ItemBuilder(Material.BLAZE_POWDER);
        setItemStack(itemBuilder.toItemStack());
    }

    //Au début ajouter tout les players dans toutes les hashmaps et dans players pour les pourcent le float doit etre 1 et pour les autres hashmap le float doit etre 0


    @EventHandler
    public void onCraftingEvent(CraftItemEvent event){
        ItemStack i = event.getCurrentItem();
        if (i.getType().equals(Material.ANVIL)){
            event.setCancelled(true);
            event.getWhoClicked().sendMessage( Preset.instance.p.prefixName() +" §7UHC Merite est activé, vous ne pouvez pas craft cette item.");
        }
        if (i.getType().equals(Material.ENCHANTMENT_TABLE)){
            event.setCancelled(true);
            event.getWhoClicked().sendMessage( Preset.instance.p.prefixName() +" §7UHC Merite est activé, vous ne pouvez pas craft cette item.");
        }

    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){

        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player)
        {
            Player damager = (Player) event.getDamager();
            Player damaged = (Player) event.getEntity();
            if(damager.getItemInHand() != null)
            {
                Material itemInHand = damager.getItemInHand().getType();
                if(itemInHand == Material.DIAMOND_SWORD || itemInHand == Material.IRON_SWORD)
                {
                    addSword(damager, (float) event.getFinalDamage());
                    addArmor(damaged, (float) event.getFinalDamage());
                }
                if(itemInHand == Material.BOW)
                {
                    addBow(damager, (float) event.getFinalDamage());
                    addArmor(damaged, (float) event.getFinalDamage());
                }
            }
        }
    }

    void addSword(Player player, float i)
    {
        if(swordMap.containsKey(player))
        {
            float multiplicator = 1;
            if(swordPourcent.containsKey(player))
            {
                multiplicator = swordPourcent.get(player);
            }
            swordMap.put(player, swordMap.get(player) + (((int)(i*multiplicator*100))/100));
            checkSword(player, player.getItemInHand());

        }
        else {
            player.sendMessage("Vous n'êtes pas dans les joueurs pouvant améliorer le stuff avec les coups");
        }

    }
    void addArmor(Player player, float i)
    {
        if(armorMap.containsKey(player))
        {
            float multiplicator = 1;
            if(armorPourcent.containsKey(player))
            {
                multiplicator = armorPourcent.get(player);
            }
            armorMap.put(player, armorMap.get(player) + (((int)(i*multiplicator*100))/100));
            checkArmor(player, player.getInventory().getArmorContents());

        }
        else {
            player.sendMessage("Vous n'êtes pas dans les joueurs pouvant améliorer le stuff avec les coups");
        }
    }
    void addBow(Player player, float i)
    {
        if(bowMap.containsKey(player))
        {
            float multiplicator = 1;
            if(bowPourcent.containsKey(player))
            {
                multiplicator = bowPourcent.get(player);
            }
            bowMap.put(player, bowMap.get(player) + (((int)(i*multiplicator*100))/100));
            checkBow(player, player.getItemInHand());

        }
        else {
            player.sendMessage("Vous n'êtes pas dans les joueurs pouvant améliorer le stuff avec les coups");
        }
    }

    void checkSword(Player player, ItemStack sword)
    {
        float i = swordMap.get(player);

        if(i > 300)
        {
            ItemMeta meta = sword.getItemMeta();
            if(!meta.getEnchants().containsKey(Enchantment.DAMAGE_ALL))
            {
                meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
                sword.setItemMeta(meta);
            }
        }
        else if(i > 200)
        {
            ItemMeta meta = sword.getItemMeta();
            if(!meta.getEnchants().containsKey(Enchantment.DAMAGE_ALL))
            {
                meta.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
                sword.setItemMeta(meta);
            }
        }
        else if(i > 120)
        {
            ItemMeta meta = sword.getItemMeta();
            if(!meta.getEnchants().containsKey(Enchantment.DAMAGE_ALL))
            {
                meta.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
                sword.setItemMeta(meta);
            }
        }
        else if(i > 80)
        {
            ItemMeta meta = sword.getItemMeta();
            if(!meta.getEnchants().containsKey(Enchantment.DAMAGE_ALL))
            {
                meta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
                sword.setItemMeta(meta);
            }
        }
        else if(i > 40)
        {
            ItemMeta meta = sword.getItemMeta();
            if(!meta.getEnchants().containsKey(Enchantment.DAMAGE_ALL))
            {
                meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                sword.setItemMeta(meta);
            }
        }

    }
    void checkArmor(Player player, ItemStack... armors)
    {
        float i = swordMap.get(player);
        if(i > 300)
        {
            for(ItemStack armor : armors)
            {
                ItemMeta meta = armor.getItemMeta();
                if(!meta.getEnchants().containsKey(Enchantment.DAMAGE_ALL))
                {
                    meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
                    armor.setItemMeta(meta);
                }
            }

        }
        else if(i > 200)
        {
            for(ItemStack armor : armors)
            {
                ItemMeta meta = armor.getItemMeta();
                if(!meta.getEnchants().containsKey(Enchantment.DAMAGE_ALL))
                {
                    meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
                    armor.setItemMeta(meta);
                }
            }
        }
        else if(i > 120)
        {
            for(ItemStack armor : armors)
            {
                ItemMeta meta = armor.getItemMeta();
                if(!meta.getEnchants().containsKey(Enchantment.DAMAGE_ALL))
                {
                    meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
                    armor.setItemMeta(meta);
                }
            }
        }
        else if(i > 80)
        {
            for(ItemStack armor : armors)
            {
                ItemMeta meta = armor.getItemMeta();
                if(!meta.getEnchants().containsKey(Enchantment.DAMAGE_ALL))
                {
                    meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
                    armor.setItemMeta(meta);
                }
            }
        }
        for(ItemStack armor : armors)
        {
            ItemMeta meta = armor.getItemMeta();
            if(!meta.getEnchants().containsKey(Enchantment.DAMAGE_ALL))
            {
                meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                armor.setItemMeta(meta);
            }
        }

    }

    void checkBow(Player player, ItemStack bow)
    {
        float i = swordMap.get(player);
        if(i > 300)
        {
            ItemMeta meta = bow.getItemMeta();
            if(!meta.getEnchants().containsKey(Enchantment.DAMAGE_ALL))
            {
                meta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
                bow.setItemMeta(meta);
            }
        }
        else if(i > 200)
        {
            ItemMeta meta = bow.getItemMeta();
            if(!meta.getEnchants().containsKey(Enchantment.DAMAGE_ALL))
            {
                meta.addEnchant(Enchantment.ARROW_DAMAGE, 4, true);
                bow.setItemMeta(meta);
            }
        }
        else if(i > 120)
        {
            ItemMeta meta = bow.getItemMeta();
            if(!meta.getEnchants().containsKey(Enchantment.DAMAGE_ALL))
            {
                meta.addEnchant(Enchantment.ARROW_DAMAGE, 3, true);
                bow.setItemMeta(meta);
            }
        }
        else if(i > 80)
        {
            ItemMeta meta = bow.getItemMeta();
            if(!meta.getEnchants().containsKey(Enchantment.DAMAGE_ALL))
            {
                meta.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
                bow.setItemMeta(meta);
            }
        }
        else if(i > 40)
        {
            ItemMeta meta = bow.getItemMeta();
            if(!meta.getEnchants().containsKey(Enchantment.DAMAGE_ALL))
            {
                meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                bow.setItemMeta(meta);
            }
        }

    }

    @EventHandler
    void onDeath(PlayerInstantDeathEvent event)
    {
        Player killer = event.getKiller();
        if(points.containsKey(killer))
        {
            points.put(killer, points.get(killer) + 1);
        }
    }
}