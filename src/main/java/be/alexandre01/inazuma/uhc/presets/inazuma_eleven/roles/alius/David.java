package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.alius;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.managers.damage.DamageManager;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Alius;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.custom_events.EpisodeChangeEvent;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import be.alexandre01.inazuma.uhc.utils.PatchedEntity;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import javax.xml.soap.Text;

public class David extends Role implements Listener {
    boolean hasChoose = false;
    boolean accepted = false;
    boolean refuse = false;
    private BukkitTask bukkitTask;
    public David() {
        super("David Samford");
        setRoleToSpoil(Caleb.class);
        addListener(this);
        setRoleCategory(Alius.class);
        onLoad(new load() {
            @Override
            public void a(Player player) {
                InazumaUHC.get.dm.addEffectPourcentage(player, DamageManager.EffectType.INCREASE_DAMAGE,1,110);
            }
        });
        addCommand("manchot", new command() {
            @Override
            public void a(String[] args, Player player) {
                if(hasChoose){
                    player.sendMessage(Preset.instance.p.prefixName()+" Vous ne pouvez pas utiliser cette commande pour le moment");
                    return;
                }
                if(args.length == 0){
                    player.sendMessage(Preset.instance.p.prefixName()+" Veuillez mettre /manchot §aaccept §7ou §a/manchot §crefuse");
                    return;
                }

                if(args[0].equalsIgnoreCase("accept")){
                    hasChoose = true;
                    accepted = true;
                    bukkitTask.cancel();
                    RoleItem roleItem = new RoleItem();
                    roleItem.deployVerificationsOnRightClick(roleItem.generateVerification(new Tuple<>(RoleItem.VerificationType.EPISODES,1)));


                    ItemBuilder it = new ItemBuilder(Material.NETHER_STAR).setName("§c§lManchot §c§lEmpereur §4§lN°1");

                    roleItem.setItemstack(it.toItemStack());

                    roleItem.setRightClick(new RoleItem.RightClick() {
                        @Override
                        public void execute(Player player) {

                            player.sendMessage(Preset.instance.p.prefixName()+" Vous venez d'utiliser §c§lManchot §c§lEmpereur §4§lN°1");
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2*20*60, 0,false,false), true);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2*20*60, 0,false,false), true);
                        }
                    });

                    addRoleItem(roleItem);

                    giveItem(player);

                    PatchedEntity.setMaxHealthInSilent(player, player.getMaxHealth()-4);
                    player.sendMessage(Preset.instance.p.prefixName()+" Vous venez de recevoir le §c§lManchot §c§lEmpereur §4§lN°1§7");
                    return;
                }
                if (args[0].equalsIgnoreCase("refuse")) {
                    hasChoose = true;
                    bukkitTask.cancel();
                    return;
                }
                player.sendMessage(Preset.instance.p.prefixName()+" Veuillez mettre /manchot §aaccept §7ou §a/manchot §crefuse");
            }
        });
    }




    private void sendRequest(){

        BaseComponent b = new TextComponent(Preset.instance.p.prefixName()+" Voulez-vous recevoir votre ");

        BaseComponent manchot = new TextComponent("§7[§c§lManchot §c§lEmpereur §4§lN°1§7] ? §7*§8Curseur§7*");
        BaseComponent manchotDesc = new TextComponent();
        manchotDesc.addExtra("§e- §9Utilisation par §eEpisode\n");
        manchotDesc.addExtra("§e- §9Perdre immédiatement §c§l3 §4❤§7 permanent\n");
        manchotDesc.addExtra("§e- §9Perdre §c§l0.5 §4❤§7 permanent tous les §eEpisode");
        manchot.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,manchotDesc.getExtra().toArray(new BaseComponent[0])));
        b.addExtra(manchot);

        BaseComponent yes = new TextComponent("§a[Accepter]");
        yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/manchot accept"));
        b.addExtra(yes);
        b.addExtra(" §7ou ");
        BaseComponent no = new TextComponent("§c[Refuser]");
        no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/manchot refuse"));

        b.addExtra(no);

        for(Player player : getPlayers()){
            player.spigot().sendMessage(b);
        }
    }
    @EventHandler
    public void onEpisode(EpisodeChangeEvent event){
        if(bukkitTask != null){
            bukkitTask.cancel();
        }
        if(accepted){
            getPlayers().forEach(player -> {
                if (player.getMaxHealth()>8){
                    player.sendMessage(Preset.instance.p.prefixName()+" Vous avez perdu §c§l0.5 §4❤§7 permanent suite à avoir accepté le §c§lManchot §c§lEmpereur §4§lN°1§7.");
                    PatchedEntity.setMaxHealthInSilent(player, player.getMaxHealth()-1);
                }
            });
            return;
        }

        sendRequest();

        bukkitTask = Bukkit.getScheduler().runTaskLaterAsynchronously(inazumaUHC, () -> {
            hasChoose = true;
            },20*60*5);
        hasChoose = false;
    }
}
