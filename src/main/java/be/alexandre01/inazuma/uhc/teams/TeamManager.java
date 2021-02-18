package be.alexandre01.inazuma.uhc.teams;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.teams.TeamSafeTeleportEvent;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class TeamManager {
    private int currentTeam = 0;
    public static TeamManager instance;
    private ArrayList<Team> teams;
    private HashMap<UUID,Team> teamByPlayer;
    public Instant lastChunkLoaded;
    private HashMap<Player, ArrayList<Player>> coop;
    public boolean isTeleportVerification = false;
    private InazumaUHC i;
    private IPreset p;
    private boolean isAlone = true;
    public TeamManager(){
        instance = this;
        teams = new ArrayList<>();
        teamByPlayer = new HashMap<>();
        coop = new HashMap<>();
        p = Preset.instance.p;
        i = InazumaUHC.get;
    }
    public void addTeam(Team team){
        teams.add(team);

        for (Player p : team.getPlayers().values()){
            teamByPlayer.put(p.getUniqueId(),team);
            ArrayList<Player> c = new ArrayList<>();
            for (Player o : team.getPlayers().values()){
                if(!p.equals(o)){
                    c.add(o);
                }
                coop.put(p,c);
            }
        }
        if(isAlone){
            if(team.getPlayers().size() > 1){
                isAlone = false;
            }
        }
    }
    public boolean isAlone(){
        return isAlone;
    }
    public boolean isInCoop(Player player,Player target){
        if(coop.get(player).contains(target))
            return true;
        return false;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public HashMap<Player, ArrayList<Player>> getCoop() {
        return coop;
    }

    public void distributeTeamToPlayer(){
        ArrayList<Player> p = new ArrayList<>(Bukkit.getOnlinePlayers());
        Collections.shuffle(p);
        float f =  p.size() / this.p.getTeamSize();
        if(f > (int) f){
            f = (int) f+1;
        }
        System.out.println("size f => "+f );
        System.out.println("size psize/f => "+p.size()/f );
        for (int j = 0; j < f; j++) {
            Team team = new Team();
            for (int k = 0; k < p.size()/f; k++) {
                team.addPlayer(p.get(k));
                p.remove(k);
            }
            addTeam(team);
            System.out.println("new team");

        }
    }
    public void safeTeamTeleport(int i){
        isTeleportVerification = true;
        if(i > getTeams().size()-1){
            isTeleportVerification = false;
            System.out.println("TeleportFinish");
            TeamSafeTeleportEvent teleportEvent = new TeamSafeTeleportEvent(this);
            Bukkit.getPluginManager().callEvent(teleportEvent);
            return;
        }
        Team team = getTeams().get(i);
        System.out.println(team);
        System.out.println(team.getPlayers().values());

        team.teleport();
        lastChunkLoaded = Instant.now();
      new BukkitRunnable(){

          @Override
          public void run() {
              if(lastChunkLoaded != null){
                  System.out.println(Duration.between(lastChunkLoaded, Instant.now()).toMillis());
                 if (Duration.between(lastChunkLoaded, Instant.now()).toMillis() >= 350) {
                      System.out.println("nextTP");
                      InazumaUHC.get.getRemainingPlayers().addAll(team.getPlayers().values());
                      safeTeamTeleport(i+1);
                      for(Player player : Bukkit.getOnlinePlayers()){
                          TitleUtils.sendTitle(player,0,20,10,"§eTÉLÉPORTATION..."," ");
                          TitleUtils.sendActionBar(player,"§eTéléportation en §ccours §e("+ (i+1)+"§c/§e"+ getTeams().size()+")");
                      }
                      cancel();
                  }
              }
          }

      }.runTaskTimer(this.i,0,1);
}
    public boolean hasTeam(Player player){
        if(teamByPlayer.containsKey(player.getUniqueId())){
            return true;
        }
        return false;
    }

    public Team getTeam(Player player){
        return teamByPlayer.get(player.getUniqueId());
    }
}