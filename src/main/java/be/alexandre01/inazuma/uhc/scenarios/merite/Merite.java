package be.alexandre01.inazuma.uhc.scenarios.merite;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.player.PlayerInstantDeathEvent;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

import java.nio.BufferUnderflowException;
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
            InazumaUHC.get.registerCommand("mxp", new MeriteBoostCommand("mxp",this));
            for(Player player : Bukkit.getOnlinePlayers())
            {
                swordMap.put(player, 0f);
                armorMap.put(player, 0f);
                bowMap.put(player, 0f);
                swordPourcent.put(player, 1f);
                armorPourcent.put(player, 1f);
                bowPourcent.put(player, 1f);
                points.put(player, 99);
                players.add(player);

                player.getInventory().addItem(new ItemBuilder(Material.DIAMOND_BOOTS).toItemStack(), new ItemBuilder(Material.DIAMOND_SWORD).toItemStack(), new ItemBuilder(Material.DIAMOND_LEGGINGS).toItemStack(),new ItemBuilder(Material.DIAMOND_CHESTPLATE).toItemStack(), new ItemBuilder(Material.DIAMOND_HELMET).toItemStack(), new ItemBuilder(Material.BOW).toItemStack(), new ItemBuilder(Material.ARROW, 128).toItemStack());

                /*Role role = InazumaUHC.get.rm.getRole(player);
                role.addCommand("boost", new Role.command() {
                    @Override
                    public void a(String[] args, Player player) {
                        if(players.contains(player))
                        {
                            Inventory inv = Bukkit.createInventory(null, 54, "Boostez votre multiplicateur");

                            inv.setItem(20, new ItemBuilder(Material.IRON_SWORD).setName("§6Mêlée").setLore("Multiplicateur : " + getSwordPourcent(player)+ " + 0.05 §7(Prix » 1 kill)").toItemStack() );
                            inv.setItem(22, new ItemBuilder(Material.IRON_CHESTPLATE).setName("§6Armure").setLore("Multiplicateur : " + getArmorPourcent(player)+ " + 0.05 §7(Prix » 1 kill)").toItemStack());
                            inv.setItem(24, new ItemBuilder(Material.BOW).setName("§6Arc").setLore("Multiplicateur : " + getBowPourcent(player)+ " + 0.05 §7(Prix » 1 kill)").toItemStack());
                            inv.setItem(49, new ItemBuilder(Material.SKULL_ITEM).setSkullOwner(player.getName()).setName("§6Stats").setLore("Mêlée » §7 " + getSword(player) + "\n§rArmure  » §7 " + getArmor(player) + "\n§rArc » §7" + getBow(player) + "\n§rPoint(s) disponible(s) » §7" + getKill(player)).toItemStack());
                            player.openInventory(inv);

                        }
                    }
                });*/

                player.sendMessage("Choisissez votre premier boost qui sera de 1.25 grâce au /mxp");

            }
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

    public float getSwordPourcent(Player player)
    {
        if(swordPourcent.containsKey(player))
        {
            return swordPourcent.get(player);
        }
        return 0;
    }
    public float getArmorPourcent(Player player)
    {
        if(armorPourcent.containsKey(player))
        {
            return armorPourcent.get(player);
        }
        return 0;
    }
    public float getBowPourcent(Player player)
    {
        if(bowPourcent.containsKey(player))
        {
            return bowPourcent.get(player);
        }
        return 0;
    }
    public float getSword(Player player)
    {
        if(swordMap.containsKey(player))
        {
            return swordMap.get(player);
        }
        return 0;
    }
    public float getArmor(Player player)
    {
        if(armorMap.containsKey(player))
        {
            return armorMap.get(player);
        }
        return 0;
    }
    public float getBow(Player player)
    {
        if(bowMap.containsKey(player))
        {
            return bowMap.get(player);
        }
        return 0;
    }

    public float getKill(Player player)
    {
        if(points.containsKey(player))
        {
            return points.get(player);
        }
        return 0;
    }



    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){


        if(event.getDamager() instanceof Arrow && event.getEntity() instanceof Player)
        {
            Player damaged = (Player) event.getEntity();
            Arrow arrow = (Arrow) event.getDamager();
            if(arrow.getShooter() instanceof Player)
            {
                Player damager = (Player) arrow.getShooter();
                damaged.sendMessage("tu t'es pris une grosse fleche dans tes dents par " + damager.getName());
                addBow(damager, (float) event.getFinalDamage());
                addArmor(damaged, (float) event.getFinalDamage());
            }

        }

        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player)
        {
            Player damager = (Player) event.getDamager();
            Player damaged = (Player) event.getEntity();
            if(damager.getItemInHand() != null)
            {
                Material itemInHand = damager.getItemInHand().getType();
                Bukkit.broadcastMessage(itemInHand.toString());
                if(itemInHand == Material.DIAMOND_SWORD || itemInHand == Material.IRON_SWORD)
                {
                    addSword(damager, (float) event.getFinalDamage());
                    addArmor(damaged, (float) event.getFinalDamage());
                }
            }
        }
    }

    @EventHandler
    void onClick(InventoryClickEvent event)
    {
        ItemStack current = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        if(current == null)
            return;

        if(event.getClickedInventory().getName().equalsIgnoreCase("Boostez votre multiplicateur") && points.containsKey(player))
        {
            switch (current.getItemMeta().getDisplayName())
            {
                case "§6Mêlée" :
                    checkPoints(player, swordPourcent);
                    player.closeInventory();
                    break;

                case "§6Armure" :
                    checkPoints(player, armorPourcent);
                    player.closeInventory();
                    break;

                case "§6Arc" :
                    checkPoints(player, bowPourcent);
                    player.closeInventory();
                    break;

                default: break;
            }

            event.setCancelled(true);
        }
    }

    private void checkPoints(Player player, HashMap<Player, Float> pourcent) {
        if(pourcent.containsKey(player))
        {
            if(points.get(player) == 99)
            {
                pourcent.replace(player, 1.25f);
                points.replace(player, 0);
                return;
            }
            if(points.get(player) > 0)
            {
                pourcent.replace(player, (float) (Math.round((pourcent.get(player) + 0.05f)*100))/100);
                points.replace(player, points.get(player) - 1);
            }
            else {
                player.sendMessage("§cVous n'avez pas de points à utiliser");
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
            int point = (int) (i*multiplicator*100);
            swordMap.replace(player, swordMap.get(player) + Math.round(point)/100);
            checkSword(player, player.getItemInHand());

        }
        else {
            player.sendMessage("Vous n'êtes pas dans les joueurs pouvant améliorer le stuff avec les coups");
        }

    }
    void addArmor(Player player, float i)
    {
        Bukkit.broadcastMessage(player.getName() + " se fait boloss");
        if(armorMap.containsKey(player))
        {
            float multiplicator = 1;
            if(armorPourcent.containsKey(player))
            {
                multiplicator = armorPourcent.get(player);
            }
            int point = (int) (i*multiplicator*100);
            armorMap.replace(player, armorMap.get(player) + point/100);
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
            int point = (int) (i*multiplicator*100);
            bowMap.replace(player, bowMap.get(player) + point/100);
            checkBow(player, player.getItemInHand());

        }
        else {
            player.sendMessage("Vous n'êtes pas dans les joueurs pouvant améliorer le stuff avec les coups");
        }
    }

    void checkSword(Player player, ItemStack sword)
    {
        float i = swordMap.get(player);
        player.sendMessage("check... point = " + i);

        if(i > 300)
        {
            addEnchant(Enchantment.DAMAGE_ALL, 5, sword);
        }
        else if(i > 200)
        {
            addEnchant(Enchantment.DAMAGE_ALL, 4, sword);
        }
        else if(i > 120)
        {
            addEnchant(Enchantment.DAMAGE_ALL, 3, sword);
        }
        else if(i > 80)
        {
            addEnchant(Enchantment.DAMAGE_ALL, 2, sword);
        }
        else if(i > 40)
        {
            addEnchant(Enchantment.DAMAGE_ALL, 1, sword);
        }

    }


    void checkArmor(Player player, ItemStack... armors)
    {
        float i = armorMap.get(player);
        player.sendMessage("check... armor = " + player.getName() + "possede :" + i);
        if(i > 300)
        {
            addEnchant(5, armors);
        }
        else if(i > 200)
        {
            addEnchant(4, armors);
        }
        else if(i > 120)
        {
            addEnchant(3, armors);
        }
        else if(i > 80)
        {
            addEnchant(2, armors);
        }
        else if(i > 40){
            addEnchant(1, armors);
        }

    }

    void checkBow(Player player, ItemStack bow)
    {

        float i = swordMap.get(player);
        player.sendMessage("check... point = " + i);
        if(i > 300)
        {
            addEnchant(Enchantment.ARROW_DAMAGE, 5, bow);
        }
        else if(i > 200)
        {
            addEnchant(Enchantment.ARROW_DAMAGE, 4, bow);
        }
        else if(i > 120)
        {
            addEnchant(Enchantment.ARROW_DAMAGE, 3, bow);
        }
        else if(i > 80)
        {
            addEnchant(Enchantment.ARROW_DAMAGE, 2, bow);
        }
        else if(i > 40)
        {
            addEnchant(Enchantment.ARROW_DAMAGE, 1, bow);
        }

    }

    @EventHandler
    void onDeath(PlayerInstantDeathEvent event)
    {
        Player killer = event.getKiller();
        if(points.containsKey(killer))
        {
            points.replace(killer, points.get(killer) + 1);
        }
    }

    void addEnchant(Enchantment enchantment, int level, ItemStack it)
    {
        ItemMeta meta = it.getItemMeta();
        if (meta.getEnchants().containsKey(enchantment)) {
            meta.removeEnchant(enchantment);
        }
        meta.addEnchant(enchantment, level, true);
        it.setItemMeta(meta);
    }
    void addEnchant(int level, ItemStack... armors)
    {
        for(ItemStack armor : armors)
        {
            addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, level, armor);
        }
    }
}