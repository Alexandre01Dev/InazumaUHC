package be.alexandre01.inazuma.uhc.scenarios.merite;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MeriteBoostCommand extends Command {

    Merite merite;

    public MeriteBoostCommand(String s, Merite merite) {
        super(s);
        this.merite = merite;
    }

    @Override
    public boolean execute(CommandSender commandSender, String label, String[] args) {
        if(commandSender instanceof Player)
        {
            if(merite.players.contains(commandSender))
            {
                //Faire l'inv pour incrémenter le multiplicateur et gerer le systeme de points pour l'incrémentation
                //GL HF
            }
        }
        return false;
    }
}
