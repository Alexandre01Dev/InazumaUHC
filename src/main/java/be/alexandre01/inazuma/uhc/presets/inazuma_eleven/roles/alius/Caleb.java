package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.alius;

import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Alius;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.custom_events.EpisodeChangeEvent;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.utils.PatchedEntity;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collections;

public class Caleb extends Role implements Listener {
    private boolean hasChoose = false;
    private Player lastPlayer = null;
    boolean b = false;
    BukkitTask s = null;
    public Caleb(IPreset preset) {
        super("Caleb Stonewall",preset);
        setRoleCategory(Alius.class);
        onLoad(new load() {
            @Override
            public void a(Player player) {

            }
        });

        addListener(this);
        addCommand("power", new command() {
            @Override
            public void a(String[] args, Player player) {
                if(hasChoose){
                    player.sendMessage(Preset.instance.p.prefixName()+" Vous ne pouvez pas utiliser cette commande pour le moment");
                    return;
                }

                if(args.length == 0){
                    player.sendMessage(Preset.instance.p.prefixName()+" Veuillez mettre §a/power accept §7ou §a/mark refuse");
                    return;
                }

                if(args[0].equalsIgnoreCase("accept")){
                    hasChoose = true;
                    acceptA();
                    return;
                }
                if (args[0].equalsIgnoreCase("refuse")) {
                    hasChoose = true;
                    refuse(player);
                    return;
                }
                player.sendMessage(Preset.instance.p.prefixName()+" Veuillez mettre §a/power accept §7ou §a/power refuse");
            }
        });

    }


    private void refuse(Player player){

    }

    private void acceptA(){

        if(!canDistribute()){
            sendRequest(false);
            return;
        }
        Player choosedPlayer = null;

        while (choosedPlayer == null || getPlayers().contains(choosedPlayer) || choosedPlayer == lastPlayer){
         ArrayList<Role> c = new ArrayList<>(inazumaUHC.rm.getRoleCategory(Alius.class).getRoles());
         Collections.shuffle(c);
         if(c.isEmpty()){
             break;
         }
           Role role = c.get(c.size()-1);
            if(role == null)
                 continue;
            ArrayList<Player> p = new ArrayList<>(role.getPlayers());
            Collections.shuffle(p);


            if(p.get(0) != null){
                choosedPlayer = p.get(0);

                PatchedEntity.damage(choosedPlayer,4);
                choosedPlayer.sendMessage(Preset.instance.p.prefixName()+" §5Caleb §7t'a enlevé 2 coeurs non-permanent.");
                for(Player player : getPlayers()){
                    player.sendMessage(Preset.instance.p.prefixName()+" §7tu as enlevé 2 coeurs a un membre de ton équipe.");
                }
            }
        }


    }

    public void acceptB(){

    }

    private boolean canDistribute(){
        ArrayList<Player> r = new ArrayList<>();
        for(Player p : inazumaUHC.getRemainingPlayers()){
            Role role = inazumaUHC.rm.getRole(p);
            if(role.getRoleCategory().getClass().equals(Alius.class) && role != Caleb.this ){
                r.add(p);
            }
        }

        if(r.size() > 1){
            return true;
        }
        return false;
    }
    private void sendRequest(boolean f){
        if(s != null){
            s.cancel();
            s = null;
        }
        if(!f){
            b = true;
            BaseComponent b = new TextComponent(Preset.instance.p.prefixName()+" Voulez-vous recevoir votre force en échange d'un coeur ?");
            BaseComponent yes = new TextComponent("§a[OUI]");
            yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/power accept"));
            b.addExtra(yes);
            b.addExtra(" §7ou ");
            BaseComponent no = new TextComponent("§a[NON]");
            no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/power refuse"));

            b.addExtra(no);

            for(Player player : getPlayers()){
                player.spigot().sendMessage(b);
            }
           return;
        }
        BaseComponent b = new TextComponent(Preset.instance.p.prefixName()+" Voulez-vous recevoir votre force ?");
        BaseComponent yes = new TextComponent("§a[OUI]");
        yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/power accept"));
        b.addExtra(yes);
        b.addExtra(" §7ou ");
        BaseComponent no = new TextComponent("§a[NON]");
        no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/power refuse"));

        b.addExtra(no);

        for(Player player : getPlayers()){
            player.spigot().sendMessage(b);
        }
    }

    @EventHandler
    public void onEpisode(EpisodeChangeEvent event){
        sendRequest(canDistribute());
        for(Player player : getPlayers()){
            player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
            hasChoose = false;


            s = new BukkitRunnable() {
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

}
