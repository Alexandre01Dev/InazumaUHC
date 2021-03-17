package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.alius;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.managers.player.PlayerMovementManager;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.InazumaEleven;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Alius;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects.Episode;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import be.alexandre01.inazuma.uhc.utils.CustomHead;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

public class  Janus extends Role implements Listener {
    ArrayList<Location> ballonsLoc = new ArrayList<>();
    Location xavierBall;
    Block xavierBlock;
    HashMap<Block,Location> ballonsBlock = new HashMap<>();
    InazumaEleven inazumaEleven;
    int episode =0;
    Inventory inventory;
    String texture = "ewogICJ0aW1lc3RhbXAiIDogMTYxNTczMzg1MTExMywKICAicHJvZmlsZUlkIiA6ICJhMjk1ODZmYmU1ZDk0Nzk2OWZjOGQ4ZGE0NzlhNDNlZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJWaWVydGVsdG9hc3RpaWUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjhiMjBmMWNmMWQ2YzRmYWJhN2Q1ZGIzY2RlMjkxMTNkZDIwZDA0MDdmNGY3NzkxNTViZmJlYTY4ZGZhNTM1ZiIKICAgIH0KICB9Cn0";
    String textureXavier = "ewogICJ0aW1lc3RhbXAiIDogMTYxNTc0NzUzMzc0NSwKICAicHJvZmlsZUlkIiA6ICI3MmNiMDYyMWU1MTA0MDdjOWRlMDA1OTRmNjAxNTIyZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJNb3M5OTAiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2MyZGM3Mjk0OTQzNTlhZGVjOTNkMGZkZGFmMGVmMzE2OTNjMDdmMjg3NmFkOWM1NzcyNzQ3NDhkNjZmYjczOCIKICAgIH0KICB9Cn0=";
    public Janus(String name, IPreset preset) {
        super(name,preset);
        setRoleCategory(Alius.class);
        onLoad(new load() {
            @Override
            public void a(Player player) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0,false,false), true);
            }
        });
        addCommand("inaball", new command() {
            @Override
            public void a(String[] args, Player player) {
                player.openInventory(inventory);
            }
        });
        inazumaEleven = (InazumaEleven) preset;
        inventory = inazumaEleven.getBallonInv().toInventory();
        addListener(this);
        RoleItem ballons = new RoleItem();
        CustomHead customHead = new CustomHead(texture,"§eBallons");
        ItemStack itemStack = customHead.toItemStack();
        itemStack.setAmount(3);
        ballons.setItemstack(itemStack);
        ballons.setSlot(8);
        ballons.setPlaceBlock(new RoleItem.PlaceBlock() {
            int i = 0;
            @Override
            public void execute(Player player, Block block) {
                if(i >= 3){
                    player.sendMessage(Preset.instance.p.prefixName()+" §c§lBUG ! La limite de ballons à déjà été atteint.");
                    return;
                }


                if(block.getLocation().getBlockY() <= 60){
                    player.sendMessage(Preset.instance.p.prefixName()+" §cVous ne pouvez pas mettre ce block en dessous de la couche 61.");
                    return;
                }
                Location tpLoc = getTop(block.getLocation());
                if(tpLoc == null){
                    player.sendMessage(Preset.instance.p.prefixName()+" §cVous ne pouvez pas mettre ce block en dessous de 3 blocks ou plus.");
                    return;
                }

                block.setType(Material.SKULL);

                CustomHead.toSkull(block,customHead.getTexture());
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
                ballonsLoc.add(i,tpLoc);
                ballonsBlock.put(block,tpLoc);
                ItemStack clone = itemStack.clone();
                ItemMeta cloneMeta = clone.getItemMeta();
                switch (i){
                    case 0:
                        clone.setAmount(1);
                        cloneMeta.setDisplayName("Ballon n°1");
                        clone.setItemMeta(cloneMeta);
                        inventory.setItem(10,clone);
                        break;
                    case 1:
                        clone.setAmount(2);
                        cloneMeta.setDisplayName("Ballon n°2");
                        clone.setItemMeta(cloneMeta);
                        inventory.setItem(12,clone);
                        break;
                    case 2:
                        clone.setAmount(3);
                        cloneMeta.setDisplayName("Ballon n°3");
                        clone.setItemMeta(cloneMeta);
                        inventory.setItem(14,clone);
                        break;
                }
                i++;
                player.sendMessage(Preset.instance.p.prefixName()+" §aBallon posé ! §e| §7X:"+ block.getLocation().getBlockX()+"§8| §7Y:"+block.getLocation().getBlockY()+ "§8| §7Z:"+block.getLocation().getBlockZ() );
            }
        });
        addRoleItem(ballons);

        RoleItem ballonsXavier = new RoleItem();
        CustomHead customHeadXavier = new CustomHead(textureXavier,"§eBallons de Xavier");
        ItemStack itemStackXavier = customHeadXavier.toItemStack();
        itemStack.setAmount(3);
        ballons.setItemstack(itemStack);
        ballons.setSlot(8);
        ballons.setPlaceBlock(new RoleItem.PlaceBlock() {
            int i = 0;
            @Override
            public void execute(Player player, Block block) {
                if(i >= 3){
                    player.sendMessage(Preset.instance.p.prefixName()+" §c§lBUG ! La limite de ballons à déjà été atteint.");
                    return;
                }


                if(block.getLocation().getBlockY() <= 60){
                    player.sendMessage(Preset.instance.p.prefixName()+" §cVous ne pouvez pas mettre ce block en dessous de la couche 61.");
                    return;
                }
                Location tpLoc = getTop(block.getLocation());
                if(tpLoc == null){
                    player.sendMessage(Preset.instance.p.prefixName()+" §cVous ne pouvez pas mettre ce block en dessous de 3 blocks ou plus.");
                    return;
                }

                block.setType(Material.SKULL);

                CustomHead.toSkull(block,customHeadXavier.getTexture());
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
                xavierBall = tpLoc;
                xavierBlock = block;
                ItemStack clone = itemStack.clone();
                ItemMeta cloneMeta = clone.getItemMeta();
                switch (i){
                    case 0:
                        clone.setAmount(1);
                        cloneMeta.setDisplayName("Ballon de Xavier");
                        clone.setItemMeta(cloneMeta);
                        inventory.setItem(16,clone);
                        for(Role role : Role.getRoles()){
                            if(role instanceof Xavier){
                                Xavier xavier = (Xavier) role;
                                xavier.setLocation(xavierBall);
                                xavier.setBlock(xavierBlock);
                            }
                        }
                        break;
                }
                i++;
                player.sendMessage(Preset.instance.p.prefixName()+" §aBallon de Xavier posé ! §e| §7X:"+ block.getLocation().getBlockX()+"§8| §7Y:"+block.getLocation().getBlockY()+ "§8| §7Z:"+block.getLocation().getBlockZ() );
            }
        });
        addRoleItem(ballons);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getClickedInventory() == null){
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if(!event.getClickedInventory().getName().equals(inventory.getName()))
            return;
        switch (event.getSlot()){
            case 10:
                onClick(player,0);
                break;
            case 12:
                onClick(player,1);
                break;
            case 14:
                onClick(player,2);
                break;
            case 16:
                player.sendMessage(Preset.instance.p.prefixName()+" §cCe ballon est réservé à Xavier.");
                break;
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){

       if(ballonsBlock.containsKey(event.getBlock())){
        event.setCancelled(true);
        event.getBlock().setType(Material.AIR);
        for(Player player : getPlayers()){
            player.sendMessage(Preset.instance.p.prefixName()+" §cUn de tes ballons ce sont cassé");

        }
           ItemStack barrier = new ItemBuilder(Material.BARRIER).setName("§cCassé").toItemStack();
        switch (ballonsLoc.indexOf(ballonsBlock.get(event.getBlock()))){
            case 0:
                inventory.setItem(10,barrier);
                break;
            case 1:
                inventory.setItem(12,barrier);
                break;
            case 2:
                inventory.setItem(14,barrier);
                break;


        }
        ballonsLoc.remove(ballonsBlock.get(event.getBlock()));
        ballonsBlock.remove(event.getBlock());
       }

    }
    private void onClick(Player player,int i){
        if(Episode.getEpisode() == this.episode){
            player.sendMessage(Preset.instance.p+ " §cTu ne peux te téléporter que tout les épisodes.");

            return;
        }

        if(ballonsLoc.size() > i){
            Location tpLoc = getTop(ballonsLoc.get(i));
            if(tpLoc == null){
                player.sendMessage(Preset.instance.p.prefixName()+" §cVous ne pouvez pas vous téléportez à votre ballon, car celui-ci est obstrué par plus de 3 blocks.");
                return;
            }
            player.teleport(tpLoc);

            return;
        }
            player.sendMessage(Preset.instance.p.prefixName()+ " §cLe ballon n°"+(i+1)+" n'existe pas");
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
