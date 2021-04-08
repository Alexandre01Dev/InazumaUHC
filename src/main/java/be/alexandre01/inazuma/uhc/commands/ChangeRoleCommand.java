package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.roles.Role;
import lombok.var;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ChangeRoleCommand extends Command {

    public ChangeRoleCommand(String s) {
        super(s);
    }

    @Override
    public boolean execute(CommandSender sender, String msg, String[] args) {

            if(!sender.hasPermission("uhc.changerole")){
                sender.sendMessage(super.getPermissionMessage());
                return false;
            }
            IPreset preset = Preset.instance.p;
            if(args.length == 0){
                sender.sendMessage(preset.prefixName()+" §cFaites /changerole [Role] ([PLAYER])");
                sender.sendMessage("§bVoici la liste des Roles");
                for(Role role : Role.getRoles()){
                    sender.sendMessage("§e- §9"+ role.getClass().getSimpleName());
                }
                return false;
            }

        Collection<Role> r = Role.getRoles().stream().filter(role -> role.getClass().getSimpleName().equalsIgnoreCase(args[0])).collect(Collectors.toList());

            if(r.isEmpty()){
                sender.sendMessage(preset.prefixName()+" §cLe rôle n'a pas été trouvé");
                return false;
            }

        Role role = new ArrayList<>(r).get(0);
        Player player = null;

        if(args.length == 2){
            player = Bukkit.getPlayer(args[1]);
            if(player == null){
                sender.sendMessage(preset.prefixName()+" §cLe joueur n'a pas été trouvé");
                return false;
            }
        }else {
            if(sender instanceof Player){
                player = (Player) sender;
            }
        }
        if(player == null){
            sender.sendMessage(preset.prefixName()+" §cFaites /changerole [Role] [PLAYER]");
            return false;
        }

        for(PotionEffect pE : player.getActivePotionEffects()){
            if(pE.getType().equals(PotionEffectType.NIGHT_VISION))
                continue;
            player.removePotionEffect(pE.getType());
        }

        Role pR = InazumaUHC.get.rm.getRole(player.getPlayer());
        if(pR != null){
            pR.removePlayer(player);
            pR.getInitialPlayers().remove(player.getUniqueId());
            pR.getEliminatedPlayers().remove(player.getUniqueId());

            for (int i = 0; i < player.getInventory().getSize(); i++) {
                ItemStack it = player.getInventory().getItem(i);
                if(it != null){
                    if(pR.getRoleItems().containsKey(it.getItemMeta().getDisplayName())){
                        player.getInventory().setItem(i,null);
                        player.updateInventory();
                    }
                }
            }
        }


        player.setMaxHealth(20);
        InazumaUHC.get.rm.remRole(player.getUniqueId());



        InazumaUHC.get.rm.addRole(player.getUniqueId(),role);
        System.out.println("addRole");
        // role.addPlayer(player);
        if(!role.isListenerDeployed){
            role.deployListeners();
        }
        if(!role.isListenerDeployed){
            role.loadCommands();
        }
        role.giveItem(player);
        player.sendMessage(preset.prefixName()+" §cVous venez de changer de rôle.");
        player.sendMessage(preset.prefixName()+" §cVotre nouveau rôle est "+ role.getName()+".");

        return false;
    }
}
