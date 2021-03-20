package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.alius;

import be.alexandre01.inazuma.uhc.managers.damage.DamageManager;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Alius;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import be.alexandre01.inazuma.uhc.utils.PatchedEntity;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Dvalin extends Role implements Listener {
    private boolean hasChoose = false;
    public Dvalin(IPreset preset) {
        super("Dvalin",preset);
        setRoleCategory(Alius.class);

        onLoad(player -> {
            Bukkit.getScheduler().runTaskLater(inazumaUHC, () -> {
                BaseComponent b = new TextComponent("Début du match, quel poste voulez-vous obternir ?");
                b.addExtra("");
                BaseComponent yes = new TextComponent("§a[§cATTAQUANT§a]");
                yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/dvalin gungnir"));
                b.addExtra(yes);
                b.addExtra(" §7ou ");
                BaseComponent no = new TextComponent("§a[§bDEFENSEUR§a]");
                no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/dvalin troueDeVer"));

                b.addExtra(no);

                player.spigot().sendMessage(b);
            },20*3);
        });

        addCommand("dvalin", new command() {
            @Override
            public void a(String[] args, Player player) {
                if(hasChoose){
                    player.sendMessage(Preset.instance.p.prefixName()+" Vous ne pouvez pas utiliser cette commande pour le moment");
                    return;
                }

                if(args.length == 0){
                    player.sendMessage(Preset.instance.p.prefixName()+" Veuillez mettre §a/xene accept §7ou §a/xene refuse");
                    return;
                }

                if(args[0].equalsIgnoreCase("troueDeVer")){
                    hasChoose = true;
                    troueDeVer(player);

                    return;
                }
                if (args[0].equalsIgnoreCase("Gungnir")) {
                    hasChoose = true;
                    gungnir(player);

                    return;
                }
                player.sendMessage(Preset.instance.p.prefixName()+" Veuillez mettre §a/xene accept §7ou §a/xene refuse");
            }
        });


    }
    private void troueDeVer(Player player){
        player.sendMessage(Preset.instance.p.prefixName()+" Tu viens de choisir ton passif ! Le Trou De Ver !");
        inazumaUHC.dm.addEffectPourcentage(player, DamageManager.EffectType.RESISTANCE,1,110);
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0,false,false), true);
        RoleItem roleItem = new RoleItem();
        roleItem.setItemstack(new ItemBuilder(Material.STICK).setName("TroueDeVer").toItemStack());

        roleItem.deployVerificationsOnRightClickOnPlayer(roleItem.generateVerification(new Tuple<>(RoleItem.VerificationType.COOLDOWN,60*10)));
        roleItem.setRightClickOnPlayer(15,new RoleItem.RightClickOnPlayer() {
            @Override
            public void execute(Player player, Player rightClicked) {
                player.sendMessage("Tu viens d'utiliser ton trou de ver sur "+ rightClicked.getName());
                rightClicked.setVelocity(player.getLocation().subtract(rightClicked.getLocation()).toVector().normalize().multiply(player.getLocation().distance(rightClicked.getLocation())/2.5D));
            }
        });
        addRoleItem(roleItem);
        giveItem(player);
    }
    private void gungnir(Player player){
        player.sendMessage(Preset.instance.p.prefixName()+" Tu viens de choisir ton passif ! Le Gungnir !");
        inazumaUHC.dm.addEffectPourcentage(player, DamageManager.EffectType.INCREASE_DAMAGE,1,110);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0,false,false), true);
        addListener(this);
        deployListeners();

    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(EntityDamageByEntityEvent e){

        Player dam;

        if(e.getDamager() instanceof Projectile && e.getEntity() instanceof Player){
            Player player = (Player) e.getEntity();
            Projectile pj = (Projectile) e.getDamager();
            if(pj.getShooter() instanceof Player){
                dam = (Player) pj.getShooter();

                if(inazumaUHC.rm.getRole(player) instanceof Dvalin){
                    e.setCancelled(true);
                    PatchedEntity.damage(dam,e.getDamage());
                }
            }
        }
    }
}
