package be.alexandre01.inazuma.uhc.roles;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects.Episode;
import be.alexandre01.inazuma.uhc.timers.utils.DateBuilderTimer;
import be.alexandre01.inazuma.uhc.timers.utils.MSToSec;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.ArrayList;
import java.util.Date;

public class RoleItem {
    private ItemStack itemStack;
    private RightClick rightClick;
    private Role LinkedRole;
    private RightClickOnPlayer rightClickOnPlayer = null;
    private Tuple<Integer,RightClickOnPlayer> rightClickOnPlayerFarTuple = null;
    private LeftClick leftClick;
    private VerificationOnRightClick verificationOnRightClick = null;
    private VerificationOnLeftClick verificationOnLeftClick = null;
    private VerificationOnRightClickOnPlayer verificationOnRightClickOnPlayer = null;
    private int slot = 8;


    public RoleItem(){
        verificationOnLeftClick = new VerificationOnLeftClick() {
            @Override
            public boolean verification(Player player) {
                return true;
            }
        };


        verificationOnRightClick = new VerificationOnRightClick() {
            @Override
            public boolean verification(Player player) {
                return true;
            }
        };

        verificationOnRightClick = new VerificationOnRightClick() {
            @Override
            public boolean verification(Player player) {
                return true;
            }
        };
    }
    public int getSlot() {
        return slot;
    }

    public Role getLinkedRole() {
        return LinkedRole;
    }

    public void setLinkedRole(Role linkedRole) {
        LinkedRole = linkedRole;
    }

    public VerificationOnRightClick getVerificationOnRightClick() {
        return verificationOnRightClick;
    }

    public ArrayList<VerificationGeneration> generateMultipleVerification(Tuple<VerificationType,Integer>... verificationTypes){
        ArrayList<VerificationGeneration> verificationGenerations = new ArrayList<>();
        for(Tuple t : verificationTypes){
            verificationGenerations.add(initAutoVerification((VerificationType) t.a(),(Integer) t.b()));
        }
        return verificationGenerations;
    }

    public ArrayList<VerificationGeneration> generateMultipleVerification(ArrayList<VerificationGeneration> verificationGenerations,Tuple<VerificationType,Integer>... verificationTypes){
        for(Tuple t : verificationTypes){
            verificationGenerations.add(initAutoVerification((VerificationType) t.a(),(Integer) t.b()));
        }
        return verificationGenerations;
    }

    public ArrayList<VerificationGeneration> generateVerification(Tuple<VerificationType,Integer> verificationTypes){
        ArrayList<VerificationGeneration> verificationGenerations = new ArrayList<>();

            verificationGenerations.add(initAutoVerification((VerificationType) verificationTypes.a(),(Integer) verificationTypes.b()));
         return verificationGenerations;
    }

    public ArrayList<VerificationGeneration> generateVerification(ArrayList<VerificationGeneration> verificationGenerations, Tuple<VerificationType,Integer> verificationTypes){
        verificationGenerations.add(initAutoVerification((VerificationType) verificationTypes.a(),(Integer) verificationTypes.b()));
        return verificationGenerations;
    }

    public void setVerificationOnRightClick(VerificationOnRightClick verificationOnRightClick) {
        this.verificationOnRightClick = verificationOnRightClick;
    }

    public void deployVerificationsOnRightClick(ArrayList<VerificationGeneration> verificationGenerations) {
        verificationOnRightClick = new VerificationOnRightClick() {
            @Override
            public boolean verification(Player player) {
                for(VerificationGeneration v : verificationGenerations){
                    if(!(v.verification(player)))
                        return false;
                }
                return true;
            }
        };
    }

    public void deployVerificationsOnLeftClick(ArrayList<VerificationGeneration> verificationGenerations) {
        verificationOnLeftClick = new VerificationOnLeftClick() {
            @Override
            public boolean verification(Player player) {
                for(VerificationGeneration v : verificationGenerations){
                    if(!(v.verification(player)))
                        return false;
                }
                return false;
            }
        };
    }

    public void deployVerificationsOnRightClickOnPlayer(ArrayList<VerificationGeneration> verificationGenerations) {
        verificationOnRightClickOnPlayer = new VerificationOnRightClickOnPlayer() {
            @Override
            public boolean verification(Player player,Player target) {
                for(VerificationGeneration v : verificationGenerations){
                    if(!(v.verification(player)))
                        return false;
                }
                return false;
            }
        };
    }
    public VerificationOnLeftClick getVerificationOnLeftClick() {
        return verificationOnLeftClick;
    }

    public void setVerificationOnLeftClick(VerificationOnLeftClick verificationOnLeftClick) {
        this.verificationOnLeftClick = verificationOnLeftClick;
    }

    public VerificationOnRightClickOnPlayer getVerificationOnRightClickOnPlayer() {
        return verificationOnRightClickOnPlayer;
    }

    public void setVerificationOnRightClickOnPlayer(VerificationOnRightClickOnPlayer verificationOnRightClickOnPlayer) {
        this.verificationOnRightClickOnPlayer = verificationOnRightClickOnPlayer;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setItemstack(ItemStack itemStack){
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public RightClick getRightClick() {
        return rightClick;
    }

    public void setRightClick(RightClick rightClick) {
        this.rightClick = rightClick;
    }

    public LeftClick getLeftClick() {
        return leftClick;
    }

    public Tuple<Integer, RightClickOnPlayer> getRightClickOnPlayerFarTuple() {
        return rightClickOnPlayerFarTuple;
    }

    public RightClickOnPlayer getRightClickOnPlayer() {
        return rightClickOnPlayer;
    }

    public void setLeftClick(LeftClick leftClick) {
        this.leftClick = leftClick;
    }

    public void setRightClickOnPlayer(RightClickOnPlayer rightClickOnPlayer) {
        this.rightClickOnPlayer = rightClickOnPlayer;
    }
    public void setRightClickOnPlayer(int reach,RightClickOnPlayer rightClickOnPlayerFar) {
        this.rightClickOnPlayerFarTuple = new Tuple<>(reach,rightClickOnPlayerFar);
    }

    private VerificationGeneration initAutoVerification(VerificationType type, int value){
        VerificationGeneration v = null;
        if(type.equals(VerificationType.USAGES)){
             v = new VerificationGeneration() {
                int i = 0;
                int iMax = value;
                @Override
                public boolean verification(Player player) {
                    if(i >= value){
                        player.sendMessage(Preset.instance.p.prefixName()+"§c Tu ne peux pas utiliser cette item plus de "+iMax+" fois durant la partie");
                        return false;
                    }
                    i++;
                    return true;
                }
            };
        }
        if(type.equals(VerificationType.EPISODES)){
            v = new VerificationGeneration() {
                int i = -value;
                int episodeLastRange = value;
                @Override
                public boolean verification(Player player) {
                    int currentEpisode = Episode.getEpisode();

                    if(i+episodeLastRange > currentEpisode){
                        player.sendMessage(Preset.instance.p.prefixName()+"§c Tu peux utiliser cette item tout les "+episodeLastRange+" épisodes après son utilisation ");
                        return false;
                    }
                    i = Episode.getEpisode();
                    return true;
                }
            };
        }

        if(type.equals(VerificationType.COOLDOWN)){
            v = new VerificationGeneration() {
                DateBuilderTimer dateBuilderTimer = new DateBuilderTimer(MSToSec.toMili(value));
                String d = dateBuilderTimer.getBuild();
                @Override
                public boolean verification(Player player) {
                    dateBuilderTimer.loadComplexDate();

                    if(dateBuilderTimer.getDate().getTime() > 0){
                        player.sendMessage(Preset.instance.p.prefixName()+"§c Tu peux utiliser cette item tout les "+dateBuilderTimer.getLongBuild()+"  après son utilisation");
                        return false;
                    }

                    dateBuilderTimer = new DateBuilderTimer(MSToSec.toMili(value));
                    return true;
                }
            };
        }
        return v;
    }
    public interface VerificationGeneration{
        boolean verification(Player player);
    }
    public interface VerificationOnRightClick{
        boolean verification(Player player);
    }

    public interface VerificationOnRightClickOnPlayer{
        boolean verification(Player player,Player rightClicked);
    }

    public interface VerificationOnLeftClick{
        boolean verification(Player player);
    }
    public interface RightClick{
         void execute(Player player);
    }

    public interface RightClickOnPlayer{
         void execute(Player player,Player rightClicked);
    }


    public interface LeftClick{
         void execute(Player player);
    }

    public enum VerificationType{
        USAGES,EPISODES,COOLDOWN;
    }
    
}
