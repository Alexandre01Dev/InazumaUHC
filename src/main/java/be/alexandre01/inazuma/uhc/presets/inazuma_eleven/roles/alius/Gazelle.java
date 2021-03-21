package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.alius;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.managers.chat.Chat;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Alius;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.custom_events.EpisodeChangeEvent;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon.Axel;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon.Hurley;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon.Shawn;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon.William;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import be.alexandre01.inazuma.uhc.utils.CustomComponentBuilder;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import be.alexandre01.inazuma.uhc.utils.StringUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Random;

public class Gazelle extends Role implements Listener {
    private int i = 6;
    public Gazelle(IPreset preset) {
        super("Gazelle",preset);

        addDescription("§8- §7Votre objectif est de gagner avec §5§ll'§5§lAcadémie §5§lAlius");
        addDescription("§8- §7Vous possédez l’effet §6§l§4§lForce 1 §7ainsi que §6§lHaste 1§7.");
        addDescription(" ");
        CustomComponentBuilder c = new CustomComponentBuilder("");
        addDescription("§8- §7Vous disposez d'une commande qui est le §5/inachat§7 qui vous permettre de dialoguer avec votre frère §cTorch§7, mais attention §c§lByron§7 peut lire vos messages...");
        addDescription(" ");
        c.append("§8- §7Vous disposez également d'une épée nommé ");

        BaseComponent fire_swordButton = new TextComponent("§b§lImpact§7-§b§lNordique §7*§8Curseur§7*");

        BaseComponent fire_swordDesc = new TextComponent();
        fire_swordDesc.addExtra("§e- §9Utilisation 8 coups par §eEpisode\n");
        fire_swordDesc.addExtra("§e- §9Donne aujoueur frappé §8§lSlowness 2 pendant §a5 secondes");
        fire_swordButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,fire_swordDesc.getExtra().toArray(new BaseComponent[0])));
        c.append(fire_swordButton);
        addDescription(c);
        addDescription(" ");
        addDescription("§8- §7Les attaques de §cTorch§7, §6Axel§7 et §6Shawn§7 ne vous atteignent pas.");
        setRoleToSpoil(Xavier.class);
        setRoleCategory(Alius.class);
        addListener(this);
        if(inazumaUHC.cm.getChat("InaChat") == null){
            for(Role role : Role.getRoles()){
                if(role.getClass() == Torch.class){
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
        RoleItem roleItem = new RoleItem();
        ItemBuilder itemBuilder = new ItemBuilder(Material.DIAMOND_SWORD);

        itemBuilder.setName("§b§lImpact§7-§b§lNordique");
        itemBuilder.addEnchant(Enchantment.DAMAGE_ALL,2);
        itemBuilder.setUnbreakable();
        roleItem.setItemstack(itemBuilder.toItemStack());
        addRoleItem(roleItem);

        addCommand("inachat", new command() {
            @Override
            public void a(String[] args, Player player) {
                if(args.length == 0){
                    player.sendMessage(Preset.instance.p.prefixName()+"§c Veuillez mettre des arguments à votre message.");
                    return;
                }
                StringBuilder s = new StringBuilder();
                StringBuilder sPlayer = new StringBuilder();
                for (String m : args){
                    sPlayer.append(m).append(" ");
                    String playerName = player.getName().substring(0,player.getName().length()-2);
                    if(m.toLowerCase().contains(playerName.toLowerCase())){
                        ArrayList<Player> rem = new ArrayList<>(inazumaUHC.getRemainingPlayers());
                        rem.removeAll(inazumaUHC.rm.getRole(Gazelle.class).getPlayers());
                        rem.removeAll(inazumaUHC.rm.getRole(Torch.class).getPlayers());

                        if((rem.size() > 10)){
                            m = rem.get( new Random().nextInt( inazumaUHC.getRemainingPlayers().size())).getName();
                        }else {
                            String[] x = {"bizarre","un peu bête","Torch","Gazelle","rien du tout","idiot"};
                            m = x[new Random().nextInt(x.length)];
                        }

                    }
                    s.append(m).append(" ");
                }
                player.sendMessage(inazumaUHC.cm.getChat("InaChat").generateMessage(player.getUniqueId(),sPlayer.toString()));
                inazumaUHC.cm.getChat("InaChat").sendMessageExceptUUID(player.getUniqueId(),s.toString(),player.getUniqueId());
            }
        });
        onLoad(new load() {
            @Override
            public void a(Player player) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0,false,false), true);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0,false,false), true);
                    Chat chat = inazumaUHC.cm.getChat("InaChat");
                if(chat != null){
                    chat.add(player.getUniqueId(),"§bGazelle");
                }
            }
        });
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player){
            Player player = (Player) event.getDamager();
            Player p = (Player) event.getEntity();
            Role role = inazumaUHC.rm.getRole(player);

            if(role.getClass().equals(Gazelle.class)){
                if(!isValidItem(player.getItemInHand()))
                    return;
                if(getRoleItems().containsKey(player.getItemInHand().getItemMeta().getDisplayName())){
                    if(this.i != 0){
                        if( !inazumaUHC.rm.getRole(p).getClass().equals(Torch.class) && !inazumaUHC.rm.getRole(p).getClass().equals(Axel.class) && !inazumaUHC.rm.getRole(p).getClass().equals(Shawn.class)){
                        p.removePotionEffect(PotionEffectType.SLOW);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*5, 1,true,false));

                        i--;
                        if(i == 0){
                            player.sendMessage(Preset.instance.p.prefixName()+" Tu viens d'user ton épée.");
                        }
                    }
                }
            }
        }
        }
    }

    @EventHandler
    public void onEpisodeChanged(EpisodeChangeEvent event){
        for(Player player : getPlayers()){
            player.sendMessage(Preset.instance.p.prefixName()+" Ton épée c'est réparé comme par magie.");
        }
        this.i = 6;
    }
}
