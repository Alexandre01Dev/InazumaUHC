package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.teams.TeamDeathEvent;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.spectators.SpectatorPlayer;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.state.State;
import be.alexandre01.inazuma.uhc.teams.Team;
import be.alexandre01.inazuma.uhc.teams.TeamManager;
import net.minecraft.server.v1_8_R3.ExceptionPlayerNotFound;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpectatorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(!sender.hasPermission("inazuma.spec")){
            return false;
        }
        if(cmd.getName().equalsIgnoreCase("spectator")){
            if(args.length < 1){
                sender.sendMessage("§c- /spec add Pseudo");
                sender.sendMessage("§c- /spec remove Pseudo");
                sender.sendMessage("§c- /spec list Joueur");
                return true;
            }
            String param = args[0];

            if(param.equalsIgnoreCase("list")){
             return true;
            }

            if(args.length < 2){
                sender.sendMessage("§c- /spec add Pseudo");
                sender.sendMessage("§c- /spec remove Pseudo");
                sender.sendMessage("§c- /spec list Joueur");
                return true;
            }

            String nickname = args[1];
            Player player;
           try {
               player = Bukkit.getPlayer(nickname);
           }catch (NullPointerException e){
               sender.sendMessage("§cLe joueur n'est pas connecté.");
               return false;
           }

           switch (param.toUpperCase()){
               case "ADD":
                   if(GameState.get().contains(State.WAITING) || GameState.get().contains(State.PREPARING)){
                       InazumaUHC.get.teamManager.getSpectators().add(player);
                   }else {
                       TeamManager tM = InazumaUHC.get.teamManager;
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
                       InazumaUHC.get.spectatorManager.addPlayer(player);
                       World world = InazumaUHC.get.worldGen.defaultWorld;
                       player.teleport(world.getSpawnLocation());
                   }

                   break;
               case "REMOVE":
                   InazumaUHC.get.teamManager.getSpectators().remove(player);
                   break;
           }

        }
        return false;
    }
}
