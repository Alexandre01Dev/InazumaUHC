package be.alexandre01.inazuma.uhc.roles;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects.Episode;
import be.alexandre01.inazuma.uhc.timers.utils.DateBuilderTimer;
import be.alexandre01.inazuma.uhc.timers.utils.MSToSec;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RoleItem {
    private ItemStack itemStack;
    @Getter boolean isPlaceableItem = false;
    @Getter @Setter private RightClick rightClick;
    private Role LinkedRole;
    @Getter @Setter private RightClickOnPlayer rightClickOnPlayer = null;
    @Getter @Setter private PlaceBlock placeBlock = null;
    @Getter @Setter private Tuple<Integer,RightClickOnPlayer> rightClickOnPlayerFarTuple = null;
    @Getter @Setter private LeftClick leftClick;
    @Getter @Setter private VerificationOnRightClick verificationOnRightClick = null;
    @Getter @Setter private VerificationOnLeftClick verificationOnLeftClick = null;
    @Getter @Setter private VerificationOnRightClickOnPlayer verificationOnRightClickOnPlayer = null;
    @Getter @Setter private VerificationOnPlaceBlock verificationOnPlaceBlock = null;
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
    public void setRightClickOnPlayer(int reach,RightClickOnPlayer rightClickOnPlayerFar) {
        this.rightClickOnPlayerFarTuple = new Tuple<>(reach,rightClickOnPlayerFar);
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
                System.out.println("true true");
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
                return true;
            }
        };
    }

    public void deployVerificationsOnPlaceBlock(ArrayList<VerificationGeneration> verificationGenerations) {
        verificationOnPlaceBlock = new VerificationOnPlaceBlock() {

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



    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setItemstack(ItemStack itemStack){
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
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
                int i = -value-1;
                final int episodeLastRange = value-1;
                @Override
                public boolean verification(Player player) {
                    int currentEpisode = Episode.getEpisode();
                    System.out.println("CALC EPISODE " +i+episodeLastRange);
                    System.out.println("CURRENT "+ currentEpisode);
                    if(i+episodeLastRange >= currentEpisode){
                        player.sendMessage(Preset.instance.p.prefixName()+"§c Tu peux utiliser cette item tout les "+(episodeLastRange+1)+" épisode(s) après son utilisation ");
                        return false;
                    }
                    i = currentEpisode;
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

    public void setPlaceableItem(boolean placeableItem) {
        isPlaceableItem = placeableItem;
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

    public interface VerificationOnPlaceBlock{
        boolean verification(Player player);
    }
    public interface PlaceBlock{
        void execute(Player player, Block b);
    }

    public enum VerificationType{
        USAGES,EPISODES,COOLDOWN;
    }

    public void updateItem(ItemStack newItem){
        for(Player player : getLinkedRole().getPlayers()){

            List<ItemStack> arrayList =  Arrays.asList(player.getInventory().getContents());
            for(ItemStack it : arrayList){
                if(it != null){
                    System.out.println(player+" >> "+ it.getAmount());
                }

            }
            if(arrayList.contains(itemStack)){
                player.getInventory().setItem( arrayList.indexOf(itemStack),newItem);
                player.updateInventory();
                System.out.println(player+" >> "+ newItem.getAmount());
            }
            itemStack = newItem;
        }
    }
}
