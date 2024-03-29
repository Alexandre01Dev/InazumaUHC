package be.alexandre01.inazuma.uhc.listeners.game;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.config.Options;
import be.alexandre01.inazuma.uhc.custom_events.player.PlayerEliminatedEvent;
import be.alexandre01.inazuma.uhc.custom_events.player.PlayerInstantDeathEvent;
import be.alexandre01.inazuma.uhc.custom_events.teams.TeamDeathEvent;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.spectators.SpectatorPlayer;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.state.State;
import be.alexandre01.inazuma.uhc.teams.Team;
import be.alexandre01.inazuma.uhc.teams.TeamManager;
import be.alexandre01.inazuma.uhc.utils.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permissible;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerEvent implements Listener {
    public ArrayList<Player> players = null;
    InazumaUHC i;
    public PlayerEvent(){
        this.i = InazumaUHC.get;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event){
        //event.setAsync(false);
        if (GameState.get().contains(State.STARTING)){
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER,"§cLa partie est entrain de commencer.");
            return;
        }
        if (GameState.get().contains(State.PREPARING) || GameState.get().contains(State.WAITING)){
            if(i.p.p.getPlayerSize() <= Bukkit.getOnlinePlayers().size()){
            if(!event.getPlayer().hasPermission("uhc.bypass.login")){
                event.getPlayer().sendMessage("§cLe serveur séléctionné à atteint la limite de joueurs maximum");
                event.setKickMessage("§cLe serveur séléctionné à atteint la limite de joueurs maximum");
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER,"§cLe serveur séléctionné à atteint la limite de joueurs maximum");
                return;
            }
            event.setResult(PlayerLoginEvent.Result.ALLOWED);
            }
     }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
       // event.setAsync(false);

        InazumaUHC inazumaUHC = InazumaUHC.get;
        Player player = event.getPlayer();
        World world = null;

        //PATCH PLAYER VIEW
        EntityPlayer nmsPlayer =((CraftPlayer)player).getHandle();


        //PATCH DAMAGE NBT
        for(Object o : nmsPlayer.getAttributeMap().a()){
            if(o instanceof AttributeModifiable){
                AttributeModifiable a = (AttributeModifiable) o;
                if(a.getAttribute().getName().equalsIgnoreCase("generic.maxHealth")){
                    a.setValue(20);
                }
                if(a.getAttribute().getName().equalsIgnoreCase("generic.attackDamage")){
                    a.setValue(1.0D);
                    for(AttributeModifier m : a.c()){
                        nmsPlayer.getAttributeMap().a("generic.attackDamage").c(new AttributeModifier(m.a(),m.b(),0,m.c()));
                    }
                }
            }
        }

        //GAMEMODE
        player.setGameMode(GameMode.ADVENTURE);

        //WALK AND FLY SPEED
        player.setFlySpeed(0.2f);
        player.setFlying(false);

        player.setWalkSpeed(0.2f);
        player.setFlySpeed(0.2f);

        player.setFireTicks(0);

        inazumaUHC.getScoreboardManager().onLogin(player);
        IPreset p = Preset.instance.p;

        if(p.isArrowCalculated()){
            p.getArrows().put(player.getUniqueId(),"§l~");
        }


        if(!GameState.get().contains(State.PLAYING)){
            if(Preset.instance.p.autoJoinWorld()){
                world = Bukkit.getWorld(Options.to("worldsTemp").get("defaultUUID").getString());
            }else {
                world = Bukkit.getWorld("world");
            }
        }else{
            world = Bukkit.getWorld(Options.to("worldsTemp").get("defaultUUID").getString());

            if(InazumaUHC.get.getRejoinManager().isValidPlayer(player)){
                InazumaUHC.get.getRejoinManager().revivePlayer(player);
                player.sendMessage(Preset.instance.p.prefixName()+" §aVous venez de revenir à la vie !");
                return;
            }
            player.sendMessage("§cLa partie a déjà commencé, vous êtes un spéctateur de celle-ci.");
            SpectatorPlayer spectatorPlayer = new SpectatorPlayer(player);
            inazumaUHC.spectatorManager.addPlayer(player);
            spectatorPlayer.setSpectator();

        }

        player.teleport(world.getSpawnLocation());
        if(player.getScoreboard().getTeams() != null){
            for(org.bukkit.scoreboard.Team team : player.getScoreboard().getTeams()){
                team.removePlayer(player);
            }
        }





        PlayerUtils.sendViewPacket(player,world.getSpawnLocation(),10);





        //CLEAR INVENTORY

        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.updateInventory();



        //HEALTH & FOOD & EXP
        player.setMaxHealth(20);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0);
        player.setTotalExperience(0);


        //EFFECT
        for(PotionEffect potionEffect : player.getActivePotionEffects()){
            player.removePotionEffect(potionEffect.getType());
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
       // event.setAsync(false);
        InazumaUHC inazumaUHC = InazumaUHC.get;
        IPreset p = Preset.instance.p;
        Player player = event.getPlayer();
        inazumaUHC.getScoreboardManager().onLogout(player);
        if(p.isArrowCalculated()){
            p.getArrows().remove(player.getUniqueId());
        }

        if(inazumaUHC.teamManager.hasTeam(player)){
            inazumaUHC.teamManager.getTeam(player).rmvPlayer(player);
        }

       if(!InazumaUHC.get.spectatorManager.getPlayers().contains(player) && GameState.get().contains(State.PLAYING)){
           Bukkit.broadcastMessage(Preset.instance.p.prefixName()+" §c§l"+player.getName()+"§7 vient de quitter la partie. Il a 10 minutes pour se reconnecter.");
           inazumaUHC.getRejoinManager().onDisconnect(player);
       }

        if(inazumaUHC.rm != null){
            if(Role.isDistributed){
                Role role = inazumaUHC.rm.getRole(player);
                if(role != null){
                    if(!role.getPlayers().isEmpty()){
                        role.removePlayer(player);
                    }
                }

            }
        }

        InazumaUHC.get.getRemainingPlayers().remove(player);
    }

    @EventHandler
    public void onFoodLevel(FoodLevelChangeEvent event){
        if(!GameState.get().contains(State.PLAYING)){
            event.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onFly(PlayerToggleFlightEvent event){
        if(GameState.get().contains(State.STARTING)){
            Player player = event.getPlayer();
            Location loc = InazumaUHC.get.teamManager.getTeam(player).getLocation();
            Location pLoc = player.getLocation();
            loc.setPitch(pLoc.getPitch());
            loc.setYaw(pLoc.getYaw());
            player.teleport(loc);
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onKicked(PlayerKickEvent event){
        if(GameState.get().contains(State.STARTING)||GameState.get().contains(State.WAITING)){
            if(event.getReason().equalsIgnoreCase("Flying is not enabled on this server")){
                event.setCancelled(true);
            }
        }

    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(GameState.get().contains(State.WAITING)){
            event.setCancelled(true);
        }
        if(GameState.get().contains(State.STARTING)){
            event.setCancelled(true);
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onKill(EntityDamageEvent event){

        if(GameState.get().contains(State.PLAYING)){
            if(event.getEntity() instanceof Player){
                Player player = (Player) event.getEntity();
                if(i.spectatorManager.getPlayers().contains(player)){
                    return;
                }
                if(!Preset.instance.pData.isInvisible){
                    if(player.getHealth()-event.getFinalDamage() <= 0){
                        List<ItemStack> l = new ArrayList<>();
                        l.addAll(Arrays.asList(player.getInventory().getArmorContents()));
                        l.addAll(Arrays.asList(player.getInventory().getContents()));
                        System.out.println("KILLER >> "+i.dm.getKiller(player));
                        PlayerInstantDeathEvent playerInstantDeathEvent = new PlayerInstantDeathEvent(player,l, ExperienceManager.getTotalExperience(player),i.dm.getKiller(player));
                        Bukkit.getPluginManager().callEvent(playerInstantDeathEvent);
                        if(!playerInstantDeathEvent.isCancelled()){
                            for(ItemStack it : playerInstantDeathEvent.getDrops()){
                                if(it != null){
                                    if(!it.getType().equals(Material.AIR))
                                        player.getWorld().dropItemNaturally(player.getLocation(),it);
                                }
                          
                            }

                           ((ExperienceOrb)CustomExp.spawn(i,player.getLocation()).getBukkitEntity()).setExperience(playerInstantDeathEvent.getDroppedExp());

                        }

                            event.setCancelled(true);
                            player.setHealth(player.getMaxHealth());
                            player.resetPlayerTime();
                            player.resetMaxHealth();

                            player.setMaxHealth(2);
                            player.setFoodLevel(20);
                            player.getInventory().clear();

                            player.getInventory().setHelmet(null);
                            player.getInventory().setChestplate(null);
                        player.getInventory().setLeggings(null);
                        player.getInventory().setBoots(null);
                            player.updateInventory();
                            System.out.println("FINALKILL > DETECTED PL");
                            //KILLED
                            player.setGameMode(GameMode.SPECTATOR);
                            InazumaUHC.get.getRemainingPlayers().remove(player);
                            player.getWorld().spigot().strikeLightningEffect(player.getLocation(),true);
                            for(Player p : Bukkit.getOnlinePlayers()){
                                p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER,0.6f,1);
                            }

                            //TEAM VERIFICATION
                            InazumaUHC i = InazumaUHC.get;
                            TeamManager tM = i.teamManager;
                            Team team = tM.getTeam(player);
                            if(!team.isKilled(player)){
                                team.killPlayer(player);

                                if(team.getDeaths().size() >= team.getAllPlayers().size()){
                                    TeamDeathEvent teamDeath = new TeamDeathEvent(team,player);
                                    tM.getTeams().remove(team);
                                    Bukkit.getPluginManager().callEvent(teamDeath);
                                }
                            }
                            //ROLE REMOVE LISTENERS AND COMMANDS
                            Role r = InazumaUHC.get.rm.getRole(player);
                            if(r != null){
                                r.getPlayers().remove(player);
                            }
                            SpectatorPlayer spectatorPlayer = new SpectatorPlayer(player);
                            spectatorPlayer.setSpectator();
                            i.spectatorManager.addPlayer(player);
                            World world = InazumaUHC.get.worldGen.defaultWorld;
                            player.teleport(world.getSpawnLocation());


                        PlayerEliminatedEvent playerEliminatedEvent = new PlayerEliminatedEvent(player,i.dm.getKiller(player));

                        Bukkit.getPluginManager().callEvent(playerEliminatedEvent);
                        }
                    return;
                    }
                event.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void onDamageEntityByEntity(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof  Player && event.getDamager() instanceof Player){
            i.dm.addPlayerDamage((Player) event.getDamager(), (Player) event.getEntity());
        }

    }

  /*  @EventHandler
    public void onSneak(PlayerToggleSneakEvent event){
        if(event.isSneaking()){
            CustomBoat.spawn(event.getPlayer().getLocation());
        }
        }*/

/*    @EventHandler(priority = EventPriority.LOWEST)
    public void onDrop(PlayerDropItemEvent event){
        if(!event.isCancelled()){
            Player player = event.getPlayer();
            EntityPlayer playerNms = ((CraftPlayer) player).getHandle();

            PacketPlayOutAnimation packetPlayOutAnimation = new PacketPlayOutAnimation(playerNms,0);
            for(Entity entity : player.getWorld().getNearbyEntities(player.getLocation(),12,12,12)){
                if(entity instanceof Player){
                        ((CraftPlayer)entity).getHandle().playerConnection.sendPacket(packetPlayOutAnimation);
                }
            }
        }
    }*/
}
