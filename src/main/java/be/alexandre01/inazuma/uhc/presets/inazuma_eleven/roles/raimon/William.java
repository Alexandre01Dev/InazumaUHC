package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.roles.RoleItemUseEvent;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.cusom_events.EpisodeChangeEvent;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;

public class William extends Role implements Listener {
    private ArrayList<Role> raimon = null;
    private ArrayList<Role> usedRole = new ArrayList<>();
    private static William w = null;
    private int episode = 0;

    public William() {
        super("William Glass");
        William w = this;
        setRoleCategory(Raimon.class);

        addDescription("§8- §7Votre objectif est de gagner avec §6§lRaimon");
        addDescription("§8- §7Vous possédez l’effet §8Faiblesse 1§7.");
        addDescription("§8- §7Lorsque n'importe quel joueur utilise son pouvoir, vous aurez un message disant quel camp a utilisé un pouvoir.");
        addDescription("§8- §7Tout les §e2 Episodes§7 vous aurez un pseudo d'un joueur de votre équipe.");

        onLoad(new load() {
            @Override
            public void a() {
                William.w = w;
                for(Player player : getPlayers()){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 0,false,false), true);
                }
            }

        });
        addListener(this);

    }


    @EventHandler
    public void onItemUse(RoleItemUseEvent event){
        Player player = event.getPlayer();

        RoleCategory roleCat  = InazumaUHC.get.rm.getRole(player).getRoleCategory();

        for(Player william : InazumaUHC.get.rm.getRole(William.class).getPlayers()){
            william.sendMessage(Preset.instance.p.prefixName()+"Un pouvoir a été utilisé dans le camp "+ roleCat.getPrefixColor()+roleCat.getName());
        }
    }

    @EventHandler
    public void onEpisodeChangeEvent(EpisodeChangeEvent event){
        raimon = new ArrayList<>(InazumaUHC.get.rm.getRoleCategory(Raimon.class).getRoles());
        for(Role used : usedRole){
            raimon.remove(used);
        }
        Collections.shuffle(raimon);

        if(raimon.isEmpty()){
            return;
        }
        for(Role role : raimon){
            if(role.getClass().equals(this.getClass())){
                raimon.remove(this);
                continue;
            }
            boolean save = false;
            for(Player player : role.getPlayers())
                if(player.isOnline()){
                    save = true;
                    break;
                }
            if(!save){
                raimon.remove(role);
            }
         }

        episode++;
        if(episode == 2){
            if(raimon.get(0) != null){
                for(Player player : getPlayers()){
                    for(Player target : raimon.get(0).getPlayers()){
                        player.sendMessage(Preset.instance.p.prefixName()+" Vous savez désormais que"+ target.getName()+" fait partie de l' "+ raimon.get(0).getRoleCategory().getPrefixColor()+raimon.get(0).getRoleCategory().getName());
                    }
                }
                usedRole.add(raimon.get(0));
            }
            episode = 0;
        }
    }
}
