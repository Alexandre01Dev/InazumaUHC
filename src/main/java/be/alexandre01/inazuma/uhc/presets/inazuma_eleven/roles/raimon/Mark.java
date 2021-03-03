package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.custom_events.player.PlayerInstantDeathEvent;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Alius;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.utils.CustomComponentBuilder;
import be.alexandre01.inazuma.uhc.utils.PlayerUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Mark extends Role implements Listener {

    public Mark() {
        super("Mark Evans");
        setRoleCategory(Raimon.class);
       // setRoleToSpoil(Victoria);
        addListener(this);
        addDescription("§8- §7Votre objectif est de gagner avec §6§lRaimon");
        addDescription("§8- §7Vous possédez l’effet §6§lRésistance 1§7.");
        addDescription("§8- §7A chaque mort d'un joueur de §6§lRaimon§7, vous perdrez §c§l0.5 §4❤§7 permanent.");
        addDescription("§8- §7A votre mort, chaque joueur de §6§lRaimon§7 auront §8Faiblesse 1 §7pendant §a 2 minutes§7.");
        CustomComponentBuilder c = new CustomComponentBuilder("");
        c.append("§8- §7Vous avez une commande nommée ");

        BaseComponent corruptButton = new TextComponent("§5/corrupt §7*§8Curseur§7*");

        BaseComponent corruptDesc = new TextComponent();
        corruptDesc.addExtra("§e- §9Utilisation 2 fois uniquement\n");
        corruptDesc.addExtra("§e- §9Savoir le nombre(s) de joueur(s) de l'§5§lAcadémie §5§lAlius §9[§525 blocks§9]");
        corruptButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,corruptDesc.getExtra().toArray(new BaseComponent[0])));
        c.append(corruptButton);
        addDescription(c);
        addDescription("§8- §7Si §5Bellatrix§7 accepte de remplacer §5Xavier§7, vous aurez son pseudo");

        onLoad(new load() {
            @Override
            public void a(Player player) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0,false,false), true);
            }
        });

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


    }
    @EventHandler
    public void onPlayerDeath(PlayerInstantDeathEvent event){
        if(inazumaUHC.rm.getRole(event.getPlayer()).getRoleCategory().getClass().equals(Raimon.class)){
            for(Player player : inazumaUHC.rm.getRole(event.getPlayer().getUniqueId()).getPlayers()){
                if(player.getMaxHealth() > 10){
                    player.setMaxHealth(player.getMaxHealth()-1);
                    player.sendMessage(Preset.instance.p.prefixName()+" Un joueur de &6Raimon&7 vient de mourir, vous perdez donc §4❤§7 permanent.");
                }
            }
        }
        if(getPlayers().contains(event.getPlayer())){
            for(Role role : inazumaUHC.rm.getRoleCategory(Raimon.class).getRoles()){
                role.getPlayers().forEach(p -> {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,60*2,0),true);
                });
            }
        }
    }
}
