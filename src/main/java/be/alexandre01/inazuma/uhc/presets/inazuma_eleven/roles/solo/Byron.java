package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.solo;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.player.PlayerInstantDeathEvent;
import be.alexandre01.inazuma.uhc.managers.DamageManager;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Solo;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects.Episode;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.solo.listeners.FreezePlayerListener;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import be.alexandre01.inazuma.uhc.utils.Freeze;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import be.alexandre01.inazuma.uhc.utils.PlayerUtils;
import be.alexandre01.inazuma.uhc.utils.TitleUtils;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Byron extends Role implements Listener {
    private ItemStack potion;
    public Byron() {
        super("Byron Love");
        getDescription().add("§8.Voici Votre rôle: §c§lByron Love ");
        getDescription().add("§8.Vous devez gagner tout §c§lseul");
        getDescription().add("§8Vous avez obtenu une potion nommée §f§lNectar §7§lDivin§8 qui n'a q'une seul utilisation qui donne  §d§lRégénération 1§8 et §b§lSpeed 1§8 durant 20 secondes");
        getDescription().add("§8.Vous possédez l’effet §4§lForce 1 et §c§l2 cœurs supplémentaires");
        getDescription().add("§8.Vous avez aussi obtenu une §7Horloge§8 nommée §7§lInstant Céleste§8 qui figera tous les joueurs autour de vous dans un rayon de 25 blocks pendant 10 secondes.");
        getDescription().add("§8.A chaque §4§lkill§8, vous gagnerez §c§l0.5 cœur supplémentaires");

        setRoleCategory(Solo.class);
        onLoad(new load() {
            @Override
            public void a() {
                for(Player player : getPlayers()){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0,false,false), true);
                    inazumaUHC.dm.addEffectPourcentage(player, DamageManager.EffectType.INCREASE_DAMAGE,1,110);
                    player.setMaxHealth(24);
                    player.setHealth(24);
                }
            }
        });

        ItemStack pot1 =  new Potion(PotionType.SPEED, 1).toItemStack(1);
        PotionMeta meta = (PotionMeta) pot1.getItemMeta();
        meta.clearCustomEffects();
        pot1.setItemMeta(meta);
        meta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 10*20, 1,false,false), true);
        meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 20*20, 0,false,false), true);
        meta.setDisplayName("§f§lNectar §7§lDivin");
        meta.setLore(Arrays.asList("Boisson Divine légué par §fDieu§r lui même","cette boisson vous rendra §fimmortel§r durant un certain moment."));
        List<String> potLore = new ArrayList<String>();
        meta.setLore(potLore);
        pot1.setItemMeta(meta);
        RoleItem potion = new RoleItem();
        potion.setItemstack(pot1);
        this.potion = pot1;
        addRoleItem(potion);



        RoleItem timeStop = new RoleItem();
        ItemBuilder t = new ItemBuilder(Material.WATCH).setName("§7§lInstant Céleste");
        timeStop.setItemstack(t.toItemStack());
        timeStop.setSlot(7);
        ArrayList<RoleItem.VerificationGeneration> verificationGenerations = new ArrayList<>();

        verificationGenerations.add(new RoleItem.VerificationGeneration() {
            @Override
            public boolean verification(Player player) {
                if(InazumaUHC.get.lm.listeners.containsKey(FreezePlayerListener.class)){
                    player.sendMessage(Preset.instance.p.prefixName()+" Tu ne peux pas utiliser l'§7§lInstant Céleste§7 en ce moment.");
                    return false;
                }
             return true;
            }
        });

        timeStop.deployVerificationsOnRightClick(timeStop.generateVerification(verificationGenerations,new Tuple<>(RoleItem.VerificationType.USAGES,100)));


        timeStop.setRightClick(new RoleItem.RightClick() {
            int i = 0;
            @Override
            public void execute(Player player) {
                System.out.println("1");
                FreezePlayerListener f = new FreezePlayerListener();
                Freeze freeze = new Freeze(10);
                ArrayList<Player> p = new ArrayList<>();
                System.out.println("target");
                player.sendMessage(Preset.instance.p.prefixName()+" Vous venez d'utiliser l'§7§lInstant Céleste§7.");
                for(Player target : PlayerUtils.getNearbyPlayersFromPlayer(player,25,25,25)){
                    System.out.println("INSTANT");
                        freeze.freezePlayer(target);
                        p.add(target);
                        TitleUtils.sendActionBar(target,"§7§lINSTANT CELESTE§7");
                }
                f.setP(p);
                InazumaUHC.get.lm.addListener(f);
                freeze.setOnStop((() -> {
                    InazumaUHC.get.lm.removeListener(f.getClass());
                }));

                freeze.launchTimer();
                i++;
            }
        });
        addRoleItem(timeStop);
        addListener(this);
    }
    @EventHandler
    public void onKillEvent(PlayerInstantDeathEvent event){
        Player killer = event.getPlayer().getKiller();
        if(killer != null){
            if (inazumaUHC.rm.getRole(killer.getUniqueId()).getClass().equals(Byron.class)){
                killer.setMaxHealth(killer.getMaxHealth()+0.5);
            }
        }
    }


    @EventHandler
    public void onDrinkPotion(PlayerItemConsumeEvent e){
        ItemStack consumed = e.getItem();
        Player p = e.getPlayer();
        if (consumed == potion){
            p.sendMessage(Preset.instance.p.prefixName()+" Vous venez de boire votre §f§lNectar §7§lDivin.");
            potion.setType(Material.AIR);
        }
    }
}

