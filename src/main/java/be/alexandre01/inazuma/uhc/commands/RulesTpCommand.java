package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.state.State;
import be.alexandre01.inazuma.uhc.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RulesTpCommand extends Command {
    public RulesTpCommand(String s) {
        super(s);
        super.setPermission("uhc.rulestp");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {

        Player player = (Player) commandSender;


        if (GameState.get().contains(State.PREPARING)){


            for(Player pls : Bukkit.getOnlinePlayers()){




            pls.sendMessage(Preset.instance.p.prefixName() + "La sonnerie vient de sonner, vous allez être téléporté au cours de §l§3" + player.getName()+"§6.");
            pls.playSound(player.getLocation(), Sound.NOTE_PLING,10,1);
            pls.setLevel(4);

            pls.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 100,true, false));
            pls.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 120, 100, true, false));
            pls.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 100,true,false));



            Bukkit.getScheduler().runTaskLater(InazumaUHC.get, new Runnable() { // cooldown pour être tp au spawn
                @Override
                public void run() {

                    pls.playSound(player.getLocation(), Sound.NOTE_PLING,5,1);
                    TitleUtils.sendTitle(pls, 20, 30, 20,"§6Merci d'écouter","");
                    pls.setLevel(3);

                }
            }, 20 * 1); // 5 secondes

            Bukkit.getScheduler().runTaskLater(InazumaUHC.get, new Runnable() { // cooldown pour être tp au spawn
                        @Override
                        public void run() {

                            pls.playSound(player.getLocation(), Sound.NOTE_PLING,5,1);

                            pls.setLevel(2);

                            TitleUtils.sendTitle(pls, 20, 30, 20,"§6Attentivement","");

                        }
                    }
                    , 20 * 2);

            Bukkit.getScheduler().runTaskLater(InazumaUHC.get, new Runnable() { // cooldown pour être tp au spawn
                        @Override
                        public void run() {

                            pls.playSound(player.getLocation(), Sound.NOTE_PLING,5,1);

                            pls.setLevel(1);

                            TitleUtils.sendTitle(pls, 20, 30, 20,"§6Les §cREGLES !","");

                        }
                    }
                    , 20 * 3);

            Bukkit.getScheduler().runTaskLater(InazumaUHC.get, new Runnable() { // cooldown pour être tp au spawn
                        @Override
                        public void run() {

                            Location loc = new Location(Bukkit.getWorld("world"), 792.5, 94, 873.5);
                            pls.teleport(loc);
                            pls.setBedSpawnLocation(loc);


                            Location loc1 = new Location(Bukkit.getWorld("world"), 825.5, 91, 873.5,90, 0);
                            player.teleport(loc1);
                            player.setBedSpawnLocation(loc1);

                            TitleUtils.sendTitle(pls, 20, 30, 20,"§6Sinon je te perma !","§6Signée§9 " + player.getName());
                            pls.playSound(player.getLocation(), Sound.LEVEL_UP,5,1);
                            pls.setLevel(0);

                        }
                    }
                    , 20 * 4); // 5 secondes

            Bukkit.getScheduler().runTaskLater(InazumaUHC.get, new Runnable() { // cooldown pour être tp au spawn
                @Override
                public void run() {

                    pls.sendMessage(Preset.instance.p.prefixName() + " Merci de tous attendre devant une chaise, le cours de §l§3" + player.getName()+ " §cva débuter.");

                    }
                }, 20 * 5); // 5 secondes
            }
        }
        else{
            player.sendMessage(Preset.instance.p.prefixName() + " Vous ne pouvez utiliser cette commande en cours de partie.");
        }
            return false;
        }
    }
