package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.managers.player.PlayerMovementManager;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import be.alexandre01.inazuma.uhc.utils.*;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Scotty extends Role {

    public Scotty(IPreset preset) {
        super("Scotty Banyan",preset);
        addDescription("§8- §7Votre objectif est de gagner avec §6§lRaimon");
        addDescription("§8- §7Vous disposez de §b§lSpeed 1§7");
        addDescription(" ");
        addDescription("§8- §7Etant très malicieux, vous disposez de 2 pièges qui sont les suivants :");
        CustomComponentBuilder c = new CustomComponentBuilder("");
        c.append("§8- §7");

        BaseComponent bananaButton = new TextComponent("§ePeau de banane");

        BaseComponent bananaDesc = new TextComponent();
        bananaDesc.addExtra("§e- §9Utilisation selon le nombre d'§eitem\n");
        bananaDesc.addExtra("§e- §9Donne au joueur marchant dessus §8Blindness 10§7 et §8Slowness 10§7 durant 4 secondes.");
        bananaButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,bananaDesc.getExtra().toArray(new BaseComponent[0])));
        c.append(bananaButton);
        addDescription(c);

        CustomComponentBuilder d = new CustomComponentBuilder("");

        d.append("§8- §7");

        BaseComponent casierButton = new TextComponent("§eCasiers Cadenet");

        BaseComponent casierDesc = new TextComponent();
        casierDesc.addExtra("§e- §9Utilisation selon le nombre d'§eitem\n");
        casierDesc.addExtra("§e- §9Téléporte le joueur marchant dessus pendant 20 secondes.");
        casierButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,casierDesc.getExtra().toArray(new BaseComponent[0])));
        d.append(casierButton);
        addDescription(d);


        setRoleCategory(Raimon.class);
        onLoad(new load() {
            @Override
            public void a(Player player) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0,false,false), true);
            }


        });


        RoleItem roleItem = new RoleItem();
        roleItem.setItemstack(new ItemBuilder(Material.STRING,3).setName("§ePeau de banane").toItemStack());
        roleItem.setPlaceableItem(false);

        RoleItem cadenet = new RoleItem();
        cadenet.setItemstack(new ItemBuilder(Material.STRING,2).setName("§eCasiers Cadenet").toItemStack());

        cadenet.setPlaceableItem(false);


        roleItem.setPlaceBlock(new RoleItem.PlaceBlock() {
            @Override
            public void execute(Player player, Block block) {
                ItemStack itemStack = player.getItemInHand();
                Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(InazumaUHC.get, new Runnable() {
                    public void run() {
                        int amount = itemStack.getAmount()-1;
                        if(amount == 0){
                            player.setItemInHand(new ItemStack(Material.AIR));
                            return;
                        }

                        itemStack.setAmount(itemStack.getAmount()-1);
                        player.setItemInHand(itemStack);
                    }
                }, 1L);


                player.sendMessage(Preset.instance.p.prefixName()+" §aPiege posé ! §e| §7X:"+ block.getLocation().getBlockX()+"§8| §7Y:"+block.getLocation().getBlockY()+ "§8| §7Z:"+block.getLocation().getBlockZ() );
                Block finalBlock = block;
                inazumaUHC.playerMovementManager.addBlockLocation(block.getLocation(), new PlayerMovementManager.action() {
                    @Override
                    public void a(Player player) {
                        if(!getPlayers().contains(player)){
                            player.sendMessage(Preset.instance.p.prefixName()+" §cTu viens de marcher sur une peau de banane.");
                            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 4*20, 9,false,false), true);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 4*20, 9,false,false), true);
                            inazumaUHC.playerMovementManager.remBlockLocation(finalBlock.getLocation());

                            for(Player s : getPlayers()){
                                s.sendMessage(Preset.instance.p.prefixName()+ "Un joueur viens de glisser dans un de tes pièges.");
                            }
                        }
                    }
                });

            }
        });
        addRoleItem(roleItem);


        cadenet.setPlaceBlock(new RoleItem.PlaceBlock() {
            @Override
            public void execute(Player player, Block block) {

                ItemStack itemStack = player.getItemInHand();
                Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(InazumaUHC.get, new Runnable() {
                    public void run() {
                        int amount = itemStack.getAmount()-1;
                        if(amount == 0){
                            player.setItemInHand(new ItemStack(Material.AIR));
                            return;
                        }

                        itemStack.setAmount(itemStack.getAmount()-1);
                        player.setItemInHand(itemStack);
                    }
                }, 1L);


                player.sendMessage(Preset.instance.p.prefixName()+" §aPiege posé ! §e| §7X:"+ block.getLocation().getBlockX()+"§8| §7Y:"+block.getLocation().getBlockY()+ "§8| §7Z:"+block.getLocation().getBlockZ() );
                Block finalBlock = block;
                inazumaUHC.playerMovementManager.addBlockLocation(block.getLocation(), new PlayerMovementManager.action() {
                    @Override
                    public void a(Player player) {
                        if(!getPlayers().contains(player)){
                            player.sendMessage(Preset.instance.p.prefixName()+" §cTu viens d'être enfermé dans un casier'.");

                           addPlayerLock(player);
                            inazumaUHC.playerMovementManager.remBlockLocation(finalBlock.getLocation());

                            for(Player s : getPlayers()){
                                s.sendMessage(Preset.instance.p.prefixName()+ "Un joueur viens d'être enfermé dans un de tes pièges.");
                            }
                        }
                    }
                });

            }
        });
        cadenet.setSlot(7);
        addRoleItem(cadenet);

    }

    private void addPlayerLock(Player player){
        NPC npc = new NPC(player,player.getName(),player.getLocation());
        npc.spawn();
        npc.setCamera(player,true);
        Location pLoc = player.getLocation();
        double y = pLoc.getY();
        pLoc.setY(1);
        player.teleport(pLoc);
        player.playSound(player.getLocation(), Sound.FALL_SMALL,1,1);
        pLoc.setY(y);
        new BukkitRunnable() {
            int sec = 0;
            @Override
            public void run() {
                if(sec == 0){
                    TitleUtils.sendTitle(player,0,30,0,"§eYou got §eS§3c§co§2t§6t§ae§4d","( ﾟ▽ﾟ)/");
                }
                if(sec == 1){
                    TitleUtils.sendTitle(player,0,30,0,"§eYou got §9S§ec§2o§at§4t§2e§bd","\\\\(ﾟ▽ﾟ )");
                }
                if(sec == 2){
                    TitleUtils.sendTitle(player,0,30,0,"§eYou got §3S§5c§7o§bt§at§ee§cd","( ﾟ▽ﾟ)/");
                }
                if(sec == 3){
                    TitleUtils.sendTitle(player,0,30,0,"§eYou got §2S§6c§eo§at§at§9e§rd","\\\\(ﾟ▽ﾟ )");
                }
                if(sec == 4){
                    TitleUtils.sendTitle(player,0,30,0,"§eYou got §9S§ec§2o§at§4t§2e§bd","( ﾟ▽ﾟ)/");
                }
                if(sec == 7){
                    TitleUtils.sendTitle(player,0,30,0,"§eAttends je te libère?","dans 3");
                }
                if(sec == 8){
                    TitleUtils.sendTitle(player,0,30,0,"§eAttends je te libère?","dans 2");
                }
                if(sec == 9){
                    TitleUtils.sendTitle(player,0,30,0,"§eAttends je te libère?","dans 1");
                }
                if(sec == 10){
                    TitleUtils.sendTitle(player,0,30,0,"§eAttends je te libère?","dans 2");
                }
                if(sec == 11){
                    TitleUtils.sendTitle(player,0,30,0,"§eAttends je te libère?","dans 3");
                }
                if(sec == 12){
                    TitleUtils.sendTitle(player,0,30,0,"§f."," ");
                }
                if(sec == 13){
                    TitleUtils.sendTitle(player,0,30,0,"§f.."," ");
                }
                if(sec == 14){
                    TitleUtils.sendTitle(player,0,30,0,"§f..."," ");
                }

                if(sec == 15){
                    TitleUtils.sendTitle(player,0,30,0,"§fToujours bloqué ?"," ");
                }

                if(sec == 17){
                    TitleUtils.sendTitle(player,0,30,0,"§eHmm","ça s'ouvre pas, dommage");
                }
                if(sec == 19){
                    TitleUtils.sendTitle(player,0,30,0,"§cLol","Je m'amuse bien avec toi.");
                }
                if(sec == 20){
                    Bukkit.getScheduler().scheduleSyncDelayedTask(inazumaUHC, new Runnable() {
                        @Override
                        public void run() {
                            player.teleport(pLoc);
                            player.sendMessage(Preset.instance.p.prefixName()+ "§aVous êtes libéré vous même et je sais que vous détestez Scotty dorénavent.");
                            npc.setCamera(player,false);
                            npc.destroy();
                        }
                    });
                    cancel();
                }

                sec++;
            }
        }.runTaskTimerAsynchronously(inazumaUHC,20,20);
    }
}

