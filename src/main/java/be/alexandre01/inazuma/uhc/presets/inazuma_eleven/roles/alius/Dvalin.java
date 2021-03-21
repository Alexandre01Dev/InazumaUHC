package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.alius;

import be.alexandre01.inazuma.uhc.managers.damage.DamageManager;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Alius;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon.Jude;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import be.alexandre01.inazuma.uhc.utils.CustomComponentBuilder;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import be.alexandre01.inazuma.uhc.utils.PatchedEntity;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
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

        addDescription("§8- §7Votre objectif est de gagner avec §5§ll'§5§lAcadémie §5§lAlius");
        CustomComponentBuilder c = new CustomComponentBuilder("");
        addDescription("§8- §7Vous disposez du §d§lCollier§7§l-§5§lAlius§7 qui vous donnera §b§lSpeed 1§7 pendant §a1 minute 30§7.");
        addDescription(" ");
        addDescription("§8- §7Des l'annonce des rôles, vous avez un choix, qui est d'être §c§lAttaquant§7 ou §b§lDéfenseur§7.");
        addDescription(" ");
        c.append("§8- §7Si vous devenez : ");

        BaseComponent defButton = new TextComponent("§7[§b§lDEFENSEUR§7] §7*§8Curseur§7*");

        BaseComponent defDesc = new TextComponent();
        defDesc.addExtra("§e- §6§lRésistance 1\n");
        defDesc.addExtra("§e- §9Pouvoir attirer le joueur de votre choix via le Gungnir §9[§515 blocks§7-§5Cooldown de §a8 minutes§7]");
        defButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,defDesc.getExtra().toArray(new BaseComponent[0])));
        c.append(defButton);
        addDescription(c);
        addDescription(" ");

        CustomComponentBuilder d = new CustomComponentBuilder("");
        addDescription(" ");
        d.append("§8- §7Ou alors devenir : ");

        BaseComponent attakButton = new TextComponent("§7[§c§lATTAQUANT§7] §7*§8Curseur§7*");

        BaseComponent attakDesc = new TextComponent();
        attakDesc.addExtra("§e- §4§lForce 1\n");
        attakDesc.addExtra("§e- §9Vous renvoyez au tireur la flèche qu'il a voulue vous tirer dessus. (Passif)");
        attakButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,attakDesc.getExtra().toArray(new BaseComponent[0])));
        d.append(attakButton);
        addDescription(d);


        setRoleCategory(Alius.class);

        RoleItem colierAllius = new RoleItem();
        ItemBuilder itemBuilder = new ItemBuilder(Material.NETHER_STAR).setName("§d§lCollier§7§l-§5§lAlius");
        colierAllius.setItemstack(itemBuilder.toItemStack());
        colierAllius.deployVerificationsOnRightClick(colierAllius.generateVerification(new Tuple<>(RoleItem.VerificationType.EPISODES,1)));
        colierAllius.setRightClick(player -> {
            Jude.collierAlliusNotif(player.getLocation());
            player.sendMessage(Preset.instance.p.prefixName()+" Vous rentrez en résonance avec la §8§lpierre§7§l-§5§lalius.");
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60*2*20, 0,false,false), true);
        });
        addRoleItem(colierAllius);
        onLoad(player -> {
            Bukkit.getScheduler().runTaskLater(inazumaUHC, () -> {
                BaseComponent b = new TextComponent(Preset.instance.p.prefixName()+ "Début du match, quel poste voulez-vous obtenir ? \n");
                b.addExtra("");
                BaseComponent yes = new TextComponent("§a[§cATTAQUANT§a] ");
                yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/dvalin gungnir"));
                b.addExtra(yes);
                b.addExtra(" §7ou ");
                BaseComponent no = new TextComponent("§a[§bDEFENSEUR§a] ");
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
                    player.sendMessage(Preset.instance.p.prefixName()+" Veuillez mettre §a/dvalin accept §7ou §a/dvalin refuse");
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
                player.sendMessage(Preset.instance.p.prefixName()+" Veuillez mettre §a/dvalin accept §7ou §a/dvalin refuse");
            }
        });


    }
    private void troueDeVer(Player player){
        player.sendMessage(Preset.instance.p.prefixName()+" Tu viens de choisir ton passif ! Le Trou De Ver !");
        inazumaUHC.dm.addEffectPourcentage(player, DamageManager.EffectType.RESISTANCE,1,110);
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0,false,false), true);
        RoleItem roleItem = new RoleItem();
        roleItem.setItemstack(new ItemBuilder(Material.BEACON).setName("§2Trou §2De §2Ver").toItemStack());

        roleItem.deployVerificationsOnRightClickOnPlayer(roleItem.generateVerification(new Tuple<>(RoleItem.VerificationType.COOLDOWN,60*10)));
        roleItem.setRightClickOnPlayer(15,new RoleItem.RightClickOnPlayer() {
            @Override
            public void execute(Player player, Player rightClicked) {
                player.sendMessage("Tu viens d'utiliser ton trou de ver sur "+ rightClicked.getName());
                rightClicked.setVelocity(player.getLocation().subtract(rightClicked.getLocation()).toVector().normalize().multiply(player.getLocation().distance(rightClicked.getLocation())/1.5D));
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
