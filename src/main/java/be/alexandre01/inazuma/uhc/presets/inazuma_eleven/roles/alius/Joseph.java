package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.alius;

import be.alexandre01.inazuma.uhc.managers.damage.DamageManager;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Alius;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects.Episode;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Joseph extends Role {


    public Joseph() {
        super("Joseph King");
        setRoleCategory(Alius.class);

        RoleItem collierAllius = new RoleItem();

        collierAllius.deployVerificationsOnRightClick(collierAllius.generateVerification(new Tuple<>(RoleItem.VerificationType.EPISODES,1)));

        collierAllius.setRightClick(new RoleItem.RightClick() {
            @Override
            public void execute(Player player) {
                player.sendMessage(Preset.instance.p.prefixName()+" Tu viens d'utiliser ton Collier-Allius");
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60*20, 0,false,false), true);
            }
        });

        ItemBuilder itemBuilder = new ItemBuilder(Material.NETHER_STAR);

        itemBuilder.setName("Collier-Allius");

        collierAllius.setItemstack(itemBuilder.toItemStack());
        addRoleItem(collierAllius);

        addCommand("morsure", new command() {
            int i = 0;
            int lastEpisode = 0;
            @Override
            public void a(String[] args, Player player) {
                i++;
                if(lastEpisode == Episode.getEpisode()){
                    player.sendMessage(Preset.instance.p.prefixName()+" §cTu ne peux utiliser cette commande que tout les épisodes");
                    return;
                }
                switch (i){
                    case 1:
                        addEffectAfter(player,2*20*60,2*60*20,PotionEffectType.WEAKNESS);
                        break;
                    case 2:
                     addEffectAfter(player,2*20*60,5*60*20,PotionEffectType.WEAKNESS);
                        break;
                    case 3:
                        addEffectAfter(player,2*20*60,Integer.MAX_VALUE,PotionEffectType.WEAKNESS);
                        break;
                    default:
                        player.sendMessage(Preset.instance.p.prefixName()+" Tu as déjà atteint la limite d'utilisation");
                        return;
                }
                lastEpisode = Episode.getEpisode();
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2*60*20, 1,false,false), true);
                inazumaUHC.dm.addEffectPourcentage(player, DamageManager.EffectType.RESISTANCE,2,120);
                player.sendMessage(Preset.instance.p.prefixName()+" Tu viens d'utiliser la commande et de recevoir l'effet");
            }
        });


    }
    private void addEffectAfter(Player player,long l, int t, PotionEffectType p){
        Bukkit.getScheduler().runTaskLaterAsynchronously(inazumaUHC, new Runnable() {
            @Override
            public void run() {
                player.addPotionEffect(new PotionEffect(p,t, 0,false,false), true);
            }
        },l);
    }
}
