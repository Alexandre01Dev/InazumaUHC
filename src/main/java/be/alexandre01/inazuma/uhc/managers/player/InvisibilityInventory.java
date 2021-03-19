package be.alexandre01.inazuma.uhc.managers.player;

import be.alexandre01.inazuma.uhc.utils.Reflections;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutHeldItemSlot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class InvisibilityInventory extends Reflections {
    public void setInventoryInvisibleToOther(Player player){
        ArrayList<PacketPlayOutEntityEquipment> packetPlayOutEntityEquipments = new ArrayList<>();
        org.bukkit.inventory.ItemStack air = new org.bukkit.inventory.ItemStack(Material.AIR);
        ItemStack it = CraftItemStack.asNMSCopy(air);
        for (int i = 0; i < 5; i++) {
            PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment();

            setValue(packet, "a", player.getEntityId());
            setValue(packet, "b", i);
            setValue(packet, "c",it);

            packetPlayOutEntityEquipments.add(packet);
        }


        for(Player p : Bukkit.getOnlinePlayers()){
            if(p != player){
                for(PacketPlayOutEntityEquipment packet : packetPlayOutEntityEquipments){
                    sendPacket(p,packet);
                }
            }
        }
    }

    public void setInventoryInvisibleToOther(Player player,int i){
        org.bukkit.inventory.ItemStack air = new org.bukkit.inventory.ItemStack(Material.AIR);
        ItemStack it = CraftItemStack.asNMSCopy(air);

            PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment();

            setValue(packet, "a", player.getEntityId());
            setValue(packet, "b", i);
            setValue(packet, "c",it);




        for(Player p : Bukkit.getOnlinePlayers()){
            if(p != player){
                    sendPacket(p,packet);
            }
        }
    }

    public void setInventoryToInitialToOther(Player player){
        ArrayList<PacketPlayOutEntityEquipment> packetPlayOutEntityEquipments = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            org.bukkit.inventory.ItemStack item = null;
            switch (i){
                case 0:
                    item = player.getItemInHand();
                    break;
                case 1:
                    item = player.getInventory().getBoots();
                    break;
                case 2:
                    item = player.getInventory().getLeggings();
                    break;
                case 3:
                    item = player.getInventory().getChestplate();
                    break;
                case 4:
                    item = player.getInventory().getHelmet();
                    break;
            }

            ItemStack it = CraftItemStack.asNMSCopy(item);
            PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment();

            setValue(packet, "a", player.getEntityId());
            setValue(packet, "b", i);
            setValue(packet, "c",it);

            packetPlayOutEntityEquipments.add(packet);
        }


        for(Player p : Bukkit.getOnlinePlayers()){
            if(p != player){
                for(PacketPlayOutEntityEquipment packet : packetPlayOutEntityEquipments){
                    sendPacket(p,packet);
                }
            }
        }
    }

}
