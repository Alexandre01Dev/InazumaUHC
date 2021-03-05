package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.custom_events.player.PlayerInstantDeathEvent;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.utils.Tracker;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.sound.midi.Track;
import java.util.List;

public class Darren extends Role implements Listener {
    private boolean markDead = false;
    private Player tracked = null;
    private boolean revenge = false;

    public Darren(String name) {
        super("Darren LaChance");
        setRoleToSpoil(Mark.class);
        setRoleCategory(Raimon.class);

        onLoad(new load() {
            @Override
            public void a(Player player) {
                    player.setMaxHealth(24);
                    player.setHealth(24);
            }
        });

        addListener(this);
        addCommand("mark", new command() {
            @Override
            public void a(String[] args, Player player) {
                if(!markDead){
                    player.sendMessage(Preset.instance.p.prefixName()+" Vous ne pouvez pas utiliser cette commande pour le moment");
                    return;
                }

                if(args.length == 0){
                    player.sendMessage(Preset.instance.p.prefixName()+" Veuillez mettre §a/mark accept §7ou §a/mark refuse");
                    return;
                }

                if(args[0].equalsIgnoreCase("accept")){

                    return;
                }
                if (args[0].equalsIgnoreCase("refuse")) {

                    Tracker tracker = Tracker.getOrCreate();
                    if(tracker.equals(player)){
                        player.sendMessage(Preset.instance.p.prefixName()+" Tu ne vas pas te tracker toi même là ... C'est pas un peu chaud là ? Tu es juste un assasin de coéquipier. ");
                        player.sendMessage(Preset.instance.p.prefixName()+" Fêtons ça avec un feu d'artifice !");
                        Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
                        FireworkMeta fwm = fw.getFireworkMeta();

                        fwm.setPower(2);
                        fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());

                        fw.setFireworkMeta(fwm);
                        fw.detonate();
                    }
                    if(tracked != null){
                        tracker.setTargetToPlayer(player,tracked);
                        player.setMaxHealth(player.getMaxHealth()-4);
                        tracked.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 60*5*20, 0,false,false), true);
                        player.sendMessage(Preset.instance.p.prefixName()+" Tu as perdu 2 §ccoeurs§7... Mais en echange son assasin est devenu plus faible, bat-le pour devenir plus fort. ");
                        revenge = true;
                    }else {
                        player.sendMessage(Preset.instance.p+" Coups dûr ! Tu viens d'apprendre que Mark est mort tout seul...");
                    }

                    return;
                }

                player.sendMessage(Preset.instance.p.prefixName()+" Veuillez mettre §a/mark accept §7ou §a/mark refuse");
            }
        });


    }

    @EventHandler
    public void onDeath(PlayerInstantDeathEvent event){
        Player player = event.getPlayer();
        Player killer = event.getKiller();
        Role role = inazumaUHC.rm.getRole(player);

        //MARK DEATH ✝
        if(role.getClass() == Mark.class){
            markDead = true;
            tracked = killer;
            for(Player players : getPlayers()){
                BaseComponent b = new TextComponent(role.getRoleCategory().getPrefixColor()+role.getName()+"§7 vient de mourir.\n");
                b.addExtra("§7Souhaite tu le remplacer ");
                BaseComponent yes = new TextComponent("§a[OUI]");
                yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/mark accept"));
                b.addExtra(yes);
                b.addExtra(" §7ou ");
                BaseComponent no = new TextComponent("§a[OUI]");
                no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/mark refuse"));

                b.addExtra(no);

                players.spigot().sendMessage(b);
            }
        }
        //REVENGE
        if(markDead && getPlayers().contains(player) && player.equals(tracked)){
            Tracker tracker = Tracker.getOrCreate();
            for(Player players : getPlayers()){
                tracker.removeTargetToPlayer(players);
            }
            player.sendMessage(Preset.instance.p.prefixName()+" Tu as tué §cl'assassin §7, tu reçois tes 2 §ccoeurs §7 ainsi qu'un effet de résistance permanent ! ");
            player.setMaxHealth(player.getMaxHealth()+4);
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0,false,false), true);
        }
    }



}
