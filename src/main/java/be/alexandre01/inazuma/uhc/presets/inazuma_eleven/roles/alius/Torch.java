package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.alius;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.managers.chat.Chat;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Alius;
import be.alexandre01.inazuma.uhc.roles.Role;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Torch  extends Role {
    public Torch() {
        super("Torch");
        setRoleCategory(Alius.class);
        if(inazumaUHC.cm.getChat("InaChat") == null){
            for(Role role : Role.getRoles()){
                if(role.getClass() == Torch.class){
                    InazumaUHC.get.cm.addChat("InaChat", Chat.builder()
                            .chatName("InaChat")
                            .prefixColor("§b")
                            .separator(">")
                            .build()
                    );
                }
            }
        }

        addCommand("inachat", new command() {
            @Override
            public void a(String[] args, Player player) {
                StringBuilder s = new StringBuilder();

                if(args.length == 0){
                    player.sendMessage(Preset.instance.p+"§c Veuillez mettre des arguments à votre message.");
                    return;
                }
                for (String m : args){
                    s.append(m).append(" ");
                }

                inazumaUHC.cm.getChat("InaChat").sendMessage(player.getUniqueId(),s.toString());
            }
        });
        onLoad(new load() {
            @Override
            public void a(Player player) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0,false,false), true);
                player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0,false,false), true);
                Chat chat = inazumaUHC.cm.getChat("InaChat");
                if(chat != null){
                    chat.add(player.getUniqueId(),"§bTorch");
                }
            }
        });
    }
}
