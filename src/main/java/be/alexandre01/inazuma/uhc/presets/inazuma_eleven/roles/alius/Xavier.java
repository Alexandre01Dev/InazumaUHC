package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.alius;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.managers.damage.DamageManager;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.InazumaEleven;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Alius;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects.Episode;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon.Jude;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import be.alexandre01.inazuma.uhc.utils.CustomComponentBuilder;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import lombok.Setter;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Xavier extends Role implements Listener {
    private int i = 0;
    private Inventory inventory;
    private int episode;
    @Setter
    private Block block = null;
     @Setter  Location location = null;
    public Xavier(IPreset preset) {
        super("Xavier Foster",preset);
        addDescription("§8- §7Votre objectif est de gagner avec §5§ll'§5§lAcadémie §5§lAlius");
        addDescription("§8- §7Vous possédez l’effet §6§l§4§lForce 1§7.");
        addDescription(" ");
        addDescription("§8- §7Vous disposez du §d§lCollier§7§l-§5§lAlius§7 qui vous donnera §b§lSpeed 1 §7et §6§lRésistance 1§7 (NERF) pendant §a1 minute 30§7.");
        addDescription(" ");
        CustomComponentBuilder c = new CustomComponentBuilder("");
        c.append("§8- §7Vous avez une commande nommée ");

        BaseComponent inaballtpButton = new TextComponent("§5/inaballtp §7(§9Pseudo§7) §7*§8Curseur§7*");

        BaseComponent inaballtpDesc = new TextComponent();
        inaballtpDesc.addExtra("§e- §9Utilisation 2 fois uniquement §7[Cooldown par §eEpisode§7]\n");
        inaballtpDesc.addExtra("§e- §9Téléporte le joueur de votre choix a votre ballon\n");
        inaballtpDesc.addExtra("§e- §cAttention§9, vous devrez trouver §5Janus§9 afin qu'il vous donne les coordonnées du ballon");
        inaballtpButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,inaballtpDesc.getExtra().toArray(new BaseComponent[0])));
        c.append(inaballtpButton);
        addDescription(c);;
        addDescription(" ");
        addDescription("§8- §7Vous pouvez également voir ou se situent les différents ballons de §5Janus§7 avec le §5/inaball§7.");


        addListener(this);
        setRoleToSpoil(Bellatrix.class);
        setRoleToSpoil(Janus.class);
        setRoleCategory(Alius.class);
        onLoad(new load() {
            @Override
            public void a(Player player) {
                    inazumaUHC.dm.addEffectPourcentage(player, DamageManager.EffectType.INCREASE_DAMAGE,1,110);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0,false,false), true);
            }
        });
        inventory = ((InazumaEleven)preset).getBallonInv().toInventory();
        RoleItem roleItem = new RoleItem();
        ItemBuilder itemBuilder = new ItemBuilder(Material.NETHER_STAR).setName("§d§lCollier§7§l-§5§lAlius");
        roleItem.setItemstack(itemBuilder.toItemStack());
        roleItem.deployVerificationsOnRightClick(roleItem.generateVerification(new Tuple<>(RoleItem.VerificationType.EPISODES,1)));
        roleItem.setRightClick(player -> {
            Jude.collierAlliusNotif(player.getLocation());
            player.sendMessage(Preset.instance.p.prefixName()+" Vous rentrez en résonance avec la §8§lpierre§7§l-§5§lalius.");
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60*2*20, 0,false,false), true);
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 90*20, 0,false,false), true);
        });
        addRoleItem(roleItem);
        setRoleToSpoil(Bellatrix.class, Janus.class);
        addCommand("inaball", new command() {
            @Override
            public void a(String[] args, Player player) {
                player.openInventory(inventory);
            }
        });
        addCommand("inaballtp", new command() {
            @Override
            public void a(String[] args, Player player) {
                if(args.length == 0){
                    player.sendMessage(Preset.instance.p.prefixName()+"§c Veuillez mettre /inaballtp [Joueur]");
                    return;
                }
                if(i >= 2){
                    player.sendMessage(Preset.instance.p.prefixName()+ " §cTu ne peux téléporter quelqu'un que 2x en total");

                    return;
                }
                if(Episode.getEpisode() == episode){
                    player.sendMessage(Preset.instance.p.prefixName()+ " §cTu ne peux téléporter quelqu'un que 1x tout les épisodes.");

                    return;
                }

                String arg = args[0];
                Player p = Bukkit.getPlayer(arg);
                if(p == null){
                    player.sendMessage(Preset.instance.p.prefixName()+"§c Le joueur n'existe pas");
                }

                if(!canTeleportPlayer(p)){
                    player.sendMessage(Preset.instance.p.prefixName()+" §cVous ne pouvez pas téléporter le joueur à votre ballon, car celui-ci est obstrué par plus de 3 blocks.");
                }else {
                    player.sendMessage(Preset.instance.p.prefixName()+" §aLe joueur a bien été téléporté.");
                    episode = Episode.getEpisode();
                    i++;
                }
            }
        });
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(inazumaUHC.rm.getRole(player) instanceof Xavier){

        if(event.getClickedInventory() == null){
            return;
        }
        if(!event.getClickedInventory().getName().equals(inventory.getName()))
            return;
        switch (event.getSlot()){
            case 10:
            case 12:
            case 14:
                player.sendMessage(Preset.instance.p.prefixName()+" §cCe ballon est réservé à Janus.");
                break;
            case 16:
                onClick(player);
                break;
        }
        }
    }
    private boolean canTeleportPlayer(Player player){
        Location tpLoc = getTop(location);
        if(tpLoc == null){
            return false;
        }
        player.teleport(tpLoc);
        InazumaUHC.get.invincibilityDamager.addPlayer(player, 1000);
        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1,1);
        if(getPlayers().contains(player)){
            player.sendMessage(Preset.instance.p.prefixName()+ " §cVous vous êtes téléporté a votre ballon");
            return true;
        }
        player.sendMessage(Preset.instance.p.prefixName()+ " §cVous vous êtes téléporté au ballon de Xavier.");
        return true;
    }
    private void onClick(Player player){
        if(Episode.getEpisode() == this.episode){
            player.sendMessage(Preset.instance.p.prefixName()+ " §cTu ne peux te téléporter que tout les épisodes.");

            return;
        }

        if(location != null){
          if(!canTeleportPlayer(player)){
              player.sendMessage(Preset.instance.p.prefixName()+" §cVous ne pouvez pas vous téléportez à votre ballon, car celui-ci est obstrué par plus de 3 blocks.");
              return;
          }else {
              this.episode = Episode.getEpisode();
              i++;
          }
        }
        player.sendMessage(Preset.instance.p.prefixName()+ " §cLe ballon n'existe pas");

    }

    private Location getTop(Location location){
        Location cLoc = location.clone();
        int t = 0;
        int b = 0;
        int a = 0;
        for (int j = 1; j < 255-cLoc.getBlockY(); j++) {
            if(a >= 2){
                cLoc.add(0,-2,0);
                return cLoc;
            }
            cLoc.add(0,j,0);
            if(!cLoc.getWorld().getBlockAt(cLoc).getType().equals(Material.AIR)){
                t++;
                b++;
                a = 0;
                if(t > 2){
                    return null;
                }

            }else {
                a++;
                b++;
            }




        }
        return cLoc;
    }
}
