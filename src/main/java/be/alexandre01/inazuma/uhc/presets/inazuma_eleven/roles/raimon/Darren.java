package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.player.PlayerInstantDeathEvent;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Alius;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.alius.Xavier;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.utils.PlayerUtils;
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
import org.bukkit.scheduler.BukkitRunnable;

import javax.sound.midi.Track;
import java.util.List;

public class Darren extends Role implements Listener {
    private boolean markDead = false;
    private Player tracked = null;
    private boolean revenge = false;
    private boolean hasChoose = false;

    public Darren() {
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
                if(!markDead || hasChoose){
                    player.sendMessage(Preset.instance.p.prefixName()+" Vous ne pouvez pas utiliser cette commande pour le moment");
                    return;
                }

                if(args.length == 0){
                    player.sendMessage(Preset.instance.p.prefixName()+" Veuillez mettre §a/mark accept §7ou §a/mark refuse");
                    return;
                }

                if(args[0].equalsIgnoreCase("accept")){
                    hasChoose = true;
                    accept();
                    return;
                }
                if (args[0].equalsIgnoreCase("refuse")) {
                    hasChoose = true;
                    refuse(player);
                    return;
                }
                player.sendMessage(Preset.instance.p.prefixName()+" Veuillez mettre §a/mark accept §7ou §a/mark refuse");
                }
        });


    }
    private void refuse(Player player){
        Tracker tracker = Tracker.getOrCreate();
        if(tracked == null){
            player.sendMessage(Preset.instance.p+" Coups dûr ! Tu viens d'apprendre que Mark est mort tout seul...");
        return;
        }

        if(tracked.equals(player)){
            player.sendMessage(Preset.instance.p.prefixName()+" Tu ne vas pas te tracker toi même  ... C'est pas un peu chaud là ? Tu es juste un assasin de coéquipier. ");
            player.sendMessage(Preset.instance.p.prefixName()+" Fêtons ça avec un feu d'artifice !");
            Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();

            fwm.setPower(1);
            fwm.addEffect(FireworkEffect.builder().flicker(true).trail(true).withColor(Color.LIME).build());

            fw.setFireworkMeta(fwm);
            fw.detonate();
            return;
        }

            tracker.setTargetToPlayer(player,tracked);
            player.setMaxHealth(player.getMaxHealth()-4);
            tracked.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 60*5*20, 0,false,false), true);
            player.sendMessage(Preset.instance.p.prefixName()+" Tu as perdu 2 §ccoeurs§7... Mais en echange son assasin est devenu plus faible, bat-le pour devenir plus fort. ");
            revenge = true;

    }

    private void accept(){
        addCommand("corrupt", new command() {
            public int i = 0;
            @Override
            public void a(String[] args, Player player) {
                if(i > 2){
                    player.sendMessage(Preset.instance.p.prefixName()+" Vous avez dépassé le nombre d'utilisation de cette commande");
                    return;
                }
                int a = 0;
                for(Player p : PlayerUtils.getNearbyPlayersFromPlayer(player,25,25,25)){
                    if(inazumaUHC.rm.getRole(p).getRoleCategory() == null){
                        System.out.println(inazumaUHC.rm.getRole(p).getName());
                        continue;
                    }
                    if(inazumaUHC.rm.getRole(p).getRoleCategory().getClass().equals(Alius.class)){
                        a++;
                    }
                }
                if( a == 0){
                    player.sendMessage(Preset.instance.p.prefixName()+"Il n'y a aucun joueur(s) de l'Académie-Alius autour de vous.");
                }
                if( a > 0){
                    player.sendMessage(Preset.instance.p.prefixName()+"Il y a "+a+" joueur(s) de l'Académie-Alius proche de vous.");
                }
                i++;
            }
        });
        loadCommands();

        Tracker tracker = Tracker.getOrCreate();
        for(Player player : inazumaUHC.rm.getRole(Xavier.class).getPlayers()){
            for(Player d : getPlayers()){
                tracker.setTargetToPlayer(player,d);
            }

        }


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
                BaseComponent no = new TextComponent("§a[NON]");
                no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/mark refuse"));

                b.addExtra(no);

                players.spigot().sendMessage(b);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if(!hasChoose){
                            hasChoose = true;
                            refuse(player);
                        }


                    }
                }.runTaskLaterAsynchronously(inazumaUHC,20*60);
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
