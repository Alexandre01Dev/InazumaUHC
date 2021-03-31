package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.solo;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.player.PlayerInstantDeathEvent;
import be.alexandre01.inazuma.uhc.managers.chat.Chat;
import be.alexandre01.inazuma.uhc.managers.damage.DamageManager;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Solo;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.alius.Torch;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.solo.listeners.FreezePlayerListener;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import be.alexandre01.inazuma.uhc.utils.*;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
    public Byron(IPreset preset) {
        super("Byron Love",preset);
        addDescription("§8- §7Votre objectif est de gagner §c§lSeul");
        addDescription("§8- §7Vous possédez l’effet §4§lForce 1 et §c§l2 §4❤§7§7 permanent");
        addDescription(" ");
        CustomComponentBuilder c = new CustomComponentBuilder("");
        c.append("§8- §7Vous avez une potion nommée ");

        BaseComponent nectarButton = new TextComponent("§f§lNectar §7§lDivin§8 §7*§8Curseur§7*");

        BaseComponent nectarDesc = new TextComponent();
        nectarDesc.addExtra("§e- §9Utilisation unique\n");
        nectarDesc.addExtra("§e- §9Donne §d§lRégénération 2§7 pendant §a10 secondes\n");
        nectarDesc.addExtra("§e- §9Donne §b§lSpeed 1§7 pendant §a20 secondes");
        nectarButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,nectarDesc.getExtra().toArray(new BaseComponent[0])));
        c.append(nectarButton);
        addDescription(c);

        CustomComponentBuilder celest = new CustomComponentBuilder("");
        celest.append("§8- §7Vous disposez également d'un item nommée \n");
        BaseComponent celestButton = new TextComponent("§7§lInstant Céleste §7*§8Curseur§7*");
        BaseComponent celestDesc = new TextComponent();

        celestDesc.addExtra("§e- §9Utilisation par §eEpisode\n");
        celestDesc.addExtra("§e- §9Fige tous les joueurs autour de vous §9[§525 blocks §9- §a10 sec§9]");


        celestButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, celestDesc.getExtra().toArray(new BaseComponent[0])));

        celest.append(celestButton);
        celest.append("§7.\n");
        addDescription(celest);

        addDescription("§8- §7A chaque §4§lkill§7, vous gagnerez §c§l0.5 §4❤§7 permanent");
        addDescription(" ");
        addDescription("§8- §7Vous pouvez également lire les messages entre §cTorch§7 et §bGazelle§7.");

        setRoleCategory(Solo.class);
        onLoad(new load() {
            @Override
            public void a(Player player) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0,false,false), true);
                    inazumaUHC.dm.addEffectPourcentage(player, DamageManager.EffectType.INCREASE_DAMAGE,1,110);
                    player.setMaxHealth(24);
                    player.setHealth(24);
                Chat chat = inazumaUHC.cm.getChat("InaChat");
                if(chat != null){
                    chat.add(player.getUniqueId(),"Byron");
                }

            }
        });

        if(inazumaUHC.cm.getChat("InaChat") == null){
            for(Role role : Role.getRoles()){
                if(role.getClass() == Byron.class){
                    InazumaUHC.get.cm.addChat("InaChat", Chat.builder()
                            .chatName("§4§lINA§7-§3§lCHAT")
                            .prefixColor("§b§l")
                            .message("§7 ")
                            .separator("§8» ")
                            .build()

                    );
                }
            }
        }
        ItemStack pot1 =  new Potion(PotionType.SPEED, 1).toItemStack(1);
        PotionMeta meta = (PotionMeta) pot1.getItemMeta();
        meta.clearCustomEffects();
        pot1.setItemMeta(meta);
        meta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 15*20, 1,false,false), true);
        meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 20*20, 0,false,false), true);
        meta.setDisplayName("§f§lNectar §7§lDivin");
        meta.setLore(Arrays.asList("Boisson Divine légué par §fDieu§r lui même","cette boisson vous rendra §fimmortel§r durant un certain moment."));
        List<String> potLore = new ArrayList<String>();
        meta.setLore(potLore);
        pot1.setItemMeta(meta);
        RoleItem potion = new RoleItem();
        potion.setItemstack(pot1);
        this.potion = pot1;
        potion.setPlaceableItem(true);
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


        timeStop.deployVerificationsOnRightClick(timeStop.generateVerification(verificationGenerations,new Tuple<>(RoleItem.VerificationType.EPISODES,1)));


        timeStop.setRightClick(new RoleItem.RightClick() {
            int i = 0;
            @Override
            public void execute(Player player) {
                FreezePlayerListener f = new FreezePlayerListener();
                Freeze freeze = new Freeze(10);
                ArrayList<Player> p = new ArrayList<>();
                System.out.println("target");
                player.sendMessage(Preset.instance.p.prefixName()+" Vous venez d'utiliser l'§7§lInstant Céleste§7.");
                player.playSound(player.getLocation(),"instantceleste",5,1);
                for(Player target : PlayerUtils.getNearbyPlayersFromPlayer(player,25,25,25)){
                        freeze.freezePlayer(target);
                        p.add(target);
                        TitleUtils.sendTitle(target,20,20*8,20,"§7§lINSTANT CELESTE§7"," ");
                        target.playSound(player.getLocation(),"instantceleste",5,1);
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
        Player killer = event.getKiller();
        Player killed = event.getPlayer();
        if(killer != null){
            if (inazumaUHC.rm.getRole(killer.getUniqueId()).getClass().equals(Byron.class)){
                killer.sendMessage("§7Vous venez de tuer §4§l" + killed.getName() + " §7vous avez donc gagné §c§l0.5 §4❤§7." );
                killer.setMaxHealth(killer.getMaxHealth()+1);
            }
        }
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        final Player player = e.getPlayer();

        if (e.getItem().getTypeId() == 373) {
            Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(InazumaUHC.get, new Runnable() {
                public void run() {
                    player.sendMessage(Preset.instance.p.prefixName()+" Vous venez de boire votre §f§lNectar §7§lDivin.");
                }
            }, 1L);
        }
    }

}

