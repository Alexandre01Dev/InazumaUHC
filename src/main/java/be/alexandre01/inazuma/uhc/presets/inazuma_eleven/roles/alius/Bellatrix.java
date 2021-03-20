package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.alius;

import be.alexandre01.inazuma.uhc.custom_events.player.PlayerInstantDeathEvent;
import be.alexandre01.inazuma.uhc.managers.damage.DamageManager;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Alius;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects.Episode;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon.Mark;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.utils.PlayerUtils;
import be.alexandre01.inazuma.uhc.utils.Tracker;
import lombok.Setter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Bellatrix extends Role implements Listener {
    private boolean xeneDead = false;
    private Player tracked = null;
    private boolean revenge = false;
    private boolean hasChoose = false;
    private Inventory inventory;
    private int episode;
    @Setter
    private Block block = null;
    @Setter
    Location location = null;
    public Bellatrix(IPreset preset) {
        super("Bellatrix",preset);
        setRoleCategory(Alius.class);
        addListener(this);
        onLoad(new load() {
            @Override
            public void a(Player player) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0,false,false), true);
            }
        });

    addCommand("xene", new command() {
        @Override
        public void a(String[] args, Player player) {
            if(!xeneDead || hasChoose){
                player.sendMessage(Preset.instance.p.prefixName()+" Vous ne pouvez pas utiliser cette commande pour le moment");
                return;
            }

            if(args.length == 0){
                player.sendMessage(Preset.instance.p.prefixName()+" Veuillez mettre §a/xene accept §7ou §a/xene refuse");
                return;
            }

            if(args[0].equalsIgnoreCase("accept")){
                hasChoose = true;
                accept();
                announceRole(true);
                return;
            }
            if (args[0].equalsIgnoreCase("refuse")) {
                hasChoose = true;
                refuse(player);
                announceRole(false);
                return;
            }
            player.sendMessage(Preset.instance.p.prefixName()+" Veuillez mettre §a/xene accept §7ou §a/xene refuse");
        }
    });


}
    private void refuse(Player player){
        player.setMaxHealth(player.getMaxHealth()+2);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0,false,false), true);
        inazumaUHC.dm.addEffectPourcentage(player, DamageManager.EffectType.INCREASE_DAMAGE,1,110);
        player.sendMessage(Preset.instance.p.prefixName()+" Tu viens de recevoir 1 §ccoeur §7 et un effet de §c§lFORCE §7. ");
        revenge = true;

    }
    private void announceRole(boolean hasAccept){
        String message;
        if(hasAccept){
            message = Preset.instance.p.prefixName()+" §cBellatrix a §a§laccepté §cde remplacer Xavier !";
        }else{
            message = Preset.instance.p.prefixName()+" §cBellatrix a §c§lrefusé de remplacer Xavier !";
        }
        for(Role role : getRoles()){
                for(Player player: role.getPlayers()){
                    player.sendMessage(message);
                }
        }
    }
    private void accept(){
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

                String arg = args[0];
                Player p = Bukkit.getPlayer(arg);
                if(p == null){
                    player.sendMessage(Preset.instance.p.prefixName()+"§c Le joueur n'existe pas");
                }

                if(!canTeleportPlayer(player)){
                    player.sendMessage(Preset.instance.p.prefixName()+" §cVous ne pouvez pas téléporter le joueur à votre ballon, car celui-ci est obstrué par plus de 3 blocks.");
                }
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
        if(role.getClass() == Xavier.class){
            xeneDead = true;
            tracked = killer;
            for(Player players : getPlayers()){
                BaseComponent b = new TextComponent(role.getRoleCategory().getPrefixColor()+role.getName()+"§7 vient de mourir.\n");
                b.addExtra("§7Souhaite tu le remplacer ");
                BaseComponent yes = new TextComponent("§a[OUI]");
                yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/xene accept"));
                b.addExtra(yes);
                b.addExtra(" §7ou ");
                BaseComponent no = new TextComponent("§a[NON]");
                no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/xene refuse"));

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
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(event.getClickedInventory() == null){
            return;
        }
        if(!event.getClickedInventory().getName().equals(inventory.getName()))
            return;
        switch (event.getSlot()){
            case 10:
                player.sendMessage(Preset.instance.p.prefixName()+" §cCe ballon est réservé à Janus.");
                break;
            case 12:
                player.sendMessage(Preset.instance.p.prefixName()+" §cCe ballon est réservé à Janus.");
                break;
            case 14:
                player.sendMessage(Preset.instance.p.prefixName()+" §cCe ballon est réservé à Janus.");
                break;
            case 16:
                onClick(player);
                break;
        }
    }
    private boolean canTeleportPlayer(Player player){
        Location tpLoc = getTop(location);
        if(tpLoc == null){
            return false;
        }
        player.teleport(tpLoc);
        return true;
    }
    private void onClick(Player player){
        if(Episode.getEpisode() == this.episode){
            player.sendMessage(Preset.instance.p+ " §cTu ne peux te téléporter que tout les épisodes.");

            return;
        }

        if(location != null){
            if(!canTeleportPlayer(player)){
                player.sendMessage(Preset.instance.p.prefixName()+" §cVous ne pouvez pas vous téléportez à votre ballon, car celui-ci est obstrué par plus de 3 blocks.");
            }
        }
        player.sendMessage(Preset.instance.p.prefixName()+ " §cLe ballon n'existe pas");
        this.episode = Episode.getEpisode();
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
