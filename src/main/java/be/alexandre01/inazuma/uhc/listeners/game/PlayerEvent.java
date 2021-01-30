package be.alexandre01.inazuma.uhc.listeners.game;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.config.Options;
import be.alexandre01.inazuma.uhc.custom_events.teams.TeamDeathEvent;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.spectators.SpectatorPlayer;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.state.State;
import be.alexandre01.inazuma.uhc.teams.Team;
import be.alexandre01.inazuma.uhc.teams.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;

public class PlayerEvent implements Listener {
    public ArrayList<Player> players = null;
    InazumaUHC i;
    public PlayerEvent(){
        this.i = InazumaUHC.get;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        InazumaUHC inazumaUHC = InazumaUHC.get;
        Player player = event.getPlayer();
        if(!player.getScoreboard().getTeams().isEmpty()){
            for(org.bukkit.scoreboard.Team team : player.getScoreboard().getTeams()){
                team.removePlayer(player);
            }
        }
        player.setFlySpeed(0.2f);
        player.setFlying(false);
        World world = null;

        //CLEAR INVENTORY
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.getInventory().setArmorContents(new ItemStack[0]);
        player.updateInventory();

        //GAMEMODE
        player.setGameMode(GameMode.ADVENTURE);

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


        inazumaUHC.getScoreboardManager().onLogin(player);
        IPreset p = Preset.instance.p;
        if(p.isArrowCalculated()){
            p.getArrows().put(player.getUniqueId(),"§l~");
        }

        if(!GameState.get().contains(State.PLAYING)){
            if(Preset.instance.p.autoJoinWorld()){
                world = Bukkit.getWorld(Options.to("worldsTemp").get("defaultUUID").getString());
            }else {
                world = Bukkit.getWorlds().get(0);
            }
        }else{
            player.sendMessage("§cLa partie a déjà commencé, vous êtes un spéctateur de celle-ci.");
            SpectatorPlayer spectatorPlayer = new SpectatorPlayer(player);
            inazumaUHC.spectatorManager.addPlayer(player);
            spectatorPlayer.setSpectator();
            world = Bukkit.getWorld(Options.to("worldsTemp").get("defaultUUID").getString());
        }




        //System.out.println(Options.to("worldsTemp").get("defaultUUID").getString());
        player.teleport(world.getSpawnLocation());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
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

   /* @EventHandler(priority = EventPriority.HIGHEST)
    public void onFirstDamage(EntityDamageEvent event){
        if(GameState.get().contains(State.PLAYING)){
            if(players == null){
                players = new ArrayList<>(Bukkit.getOnlinePlayers());
            }
            if(players.isEmpty()){
                return;
            }
            if(event.getEntity() instanceof Player){
                Player player = (Player) event.getEntity();
                if(players.contains(player)){
                    if(event.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
                        event.setCancelled(true);
                        players.remove(player);
                    }

                }
            }

        }
    }*/

    @EventHandler
    public void onKill(EntityDamageEvent event){
        if(GameState.get().contains(State.PLAYING)){

            if(event.getEntity() instanceof Player){

                Player player = (Player) event.getEntity();
                if(i.spectatorManager.getPlayers().contains(player)){
                    return;
                }

                    System.out.println("FINALKILL > DETECTED PL");
                    System.out.println(player.getHealth()-event.getFinalDamage()+"< FINALKILL");
                    System.out.println(player.getHealth()+"< FINALKILLhEALTH");
                    System.out.println(event.getFinalDamage()+"< FINALKILLhEALTH");
                if(!Preset.instance.pData.isInvisible){
                    if(player.getHealth()-event.getFinalDamage() <= 0){

                            event.setCancelled(true);
                            player.setHealth(20);
                            player.setMaxHealth(2);
                            player.setFoodLevel(20);
                            player.getInventory().clear();
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
                            SpectatorPlayer spectatorPlayer = new SpectatorPlayer(player);
                            spectatorPlayer.setSpectator();
                            i.spectatorManager.addPlayer(player);
                            World world = InazumaUHC.get.worldGen.defaultWorld;
                            player.teleport(world.getSpawnLocation());

                        }
                    return;
                    }
                event.setCancelled(true);
            }

        }
    }
}
