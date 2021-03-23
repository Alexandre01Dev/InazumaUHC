package be.alexandre01.inazuma.uhc.utils;



import be.alexandre01.inazuma.uhc.InazumaUHC;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity.PacketPlayOutEntityLook;
import net.minecraft.server.v1_8_R3.WorldSettings.EnumGamemode;
import org.bukkit.Bukkit;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.joor.Reflect.onClass;

public class NPC extends Reflections {

    private int entityID;
    private Location location;
    private GameProfile gameprofile;
    private Player player;
    private EntityPlayer entityPlayer;
    public NPC(Player p, String name, Location location){
        //entityID = (int)Math.ceil(Math.random() * 1000) + 2000;
        System.out.println(entityID);
        gameprofile = new GameProfile(UUID.randomUUID(), name);
        player = p;
       // changeSkin("");
        this.location = location.clone();
    }

    public void changeSkin(String texture, String signature){
        gameprofile.getProperties().put("textures", new Property("textures", texture, signature));
    }


    public void animation(int animation){
        PacketPlayOutAnimation packet = new PacketPlayOutAnimation();
        setValue(packet, "a", entityID);
        setValue(packet, "b", (byte)animation);
        sendPacket(player , packet);
    }

    public void status(int status){
        PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus();
        setValue(packet, "a", entityID);
        setValue(packet, "b", (byte)status);
        sendPacket(player,packet);
    }

    public void equip(int slot,ItemStack itemstack){
        PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment();
        setValue(packet, "a", entityID);
        setValue(packet, "b", slot);
        setValue(packet, "c", itemstack);
        sendPacket(player,packet);
    }



    public void sleep(boolean state){
        if(state){
            Location bedLocation = new Location(player.getWorld(), location.getX(), 2, location.getZ());
            PacketPlayOutBed packet = new PacketPlayOutBed();
            setValue(packet, "a", entityID);
            setValue(packet, "b", new BlockPosition(bedLocation.getX(),bedLocation.getY(),bedLocation.getZ()));

            for(Player pl : Bukkit.getOnlinePlayers()){
                pl.sendBlockChange(bedLocation, Material.BED_BLOCK, (byte)0);
            }

            sendPacket(player,packet);
            teleport(location.clone().add(0,0.3,0));
        }else{
            animation(2);
            teleport(location.clone().subtract(0,0.3,0));
        }
    }
            public void follow(){


            }

            public void sit(HashMap<Player,HashMap<NPC,EntityPlayer>> hashmap){
            ArmorStandNMS armorStandNMS = new ArmorStandNMS(player,"chairs",location.clone().add(0,-1.7,0));

                PacketPlayOutAttachEntity sitPlayer = new PacketPlayOutAttachEntity(0,hashmap.get(player).get(this),armorStandNMS.spawn());
                armorStandNMS.setInvisible();
                sendPacket(player,sitPlayer);
            }
    public EntityPlayer spawn(){
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) player.getWorld()).getHandle();
        entityPlayer = new EntityPlayer(nmsServer,nmsWorld,gameprofile,new PlayerInteractManager(nmsWorld));
        PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn(entityPlayer);
        //setValue(packet, "a", entityID);
        setValue(packet, "b", gameprofile.getId());
        setValue(packet, "c", getFixLocation(location.getX()));
        setValue(packet, "d", getFixLocation(location.getY()));
        setValue(packet, "e", getFixLocation(location.getZ()));
        setValue(packet, "f", getFixRotation(location.getYaw()));
        setValue(packet, "g", getFixRotation(location.getPitch()));
        setValue(packet, "h", 0);
        entityPlayer.getBukkitEntity().teleport(location);
        DataWatcher w = new DataWatcher((Entity) null);
        w.a(6,(float)20);
        w.a(10,(byte)127);

        setValue(packet, "i", w);
        entityID = entityPlayer.getBukkitEntity().getEntityId();

        addToTablist();
        sendPacket(player,packet);
        headRotation(location.getYaw(), location.getPitch());
        return entityPlayer;
    }

    public void teleport(Location location){

        PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport();
        setValue(packet, "a", entityID);
        setValue(packet, "b", getFixLocation(location.getX()));
        setValue(packet, "c", getFixLocation(location.getY()));
        setValue(packet, "d", getFixLocation(location.getZ()));
        setValue(packet, "e", getFixRotation(location.getYaw()));
        setValue(packet, "f", getFixRotation(location.getPitch()));

        sendPacket(player,packet);
        headRotation(location.getYaw(), location.getPitch());
        entityPlayer.getBukkitEntity().teleport(location);
        this.location = location.clone();

    }


    public void headRotation(float yaw,float pitch){
        PacketPlayOutEntityLook packet = new PacketPlayOutEntityLook(entityID, getFixRotation(yaw),getFixRotation(pitch) , true);
        PacketPlayOutEntityHeadRotation packetHead = new PacketPlayOutEntityHeadRotation();
        setValue(packetHead, "a", entityID);
        setValue(packetHead, "b", getFixRotation(yaw));


        sendPacket(player,packet);
        sendPacket(player,packetHead);
    }
    public void rotateToPlayer(Player player, Location npcloc){
        teleport(npcloc.setDirection(player.getLocation().subtract(npcloc).toVector()));
    }
    public void rotateToAnotherLoc(Location npcloc, Location loc){
        player.sendMessage(entityPlayer.getBukkitEntity().getLocation().toString());
        teleport(npcloc.setDirection(loc.subtract(npcloc).toVector()));
    }
    public void getLocation(){

    }
    public void destroy(){
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] {entityID});
        rmvFromTablist();
        sendPacket(player,packet);
    }

    public void addToTablist(){
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
        PacketPlayOutPlayerInfo.PlayerInfoData data = new PacketPlayOutPlayerInfo.PlayerInfoData(gameprofile, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
        @SuppressWarnings("unchecked")
        List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
        players.add(data);

        setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
        setValue(packet, "b", players);

        sendPacket(player,packet);
    }

    public void rmvFromTablist(){
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
        PacketPlayOutPlayerInfo.PlayerInfoData data = new PacketPlayOutPlayerInfo.PlayerInfoData(gameprofile, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
        @SuppressWarnings("unchecked")
        List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
        players.add(data);

        setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
        setValue(packet, "b", players);

        sendPacket(player,packet);
    }
    public void setInvisible(){
        DataWatcher w = new DataWatcher((Entity) null);

        w.a(0,(byte)0x20);
        PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(entityID,w,true);
        sendPacket(player,metadataPacket);

    }
    public void setStanding(){
        DataWatcher w = new DataWatcher((Entity) null);

        w.a(0,(byte)0);
        PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(entityID,w,true);
        sendPacket(player,metadataPacket);

    }
    public void setOnFire(){
        DataWatcher w = new DataWatcher((Entity) null);

        w.a(0,(byte)0x01);
        PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(entityID,w,true);
        sendPacket(player,metadataPacket);

    }
    public void setCrouch(){
        DataWatcher w = new DataWatcher((Entity) null);

        w.a(0,(byte)0x02);
        PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(entityID,w,true);
        sendPacket(player,metadataPacket);

    }
    public int getFixLocation(double pos){
        return (int) MathHelper.floor(pos * 32.0D);
    }

    public byte getFixRotation(float yawpitch){
        return (byte) ((int) (yawpitch * 256.0F / 360.0F));
    }
    public int getEntityID(){
        return entityID;
    }

    public void setCamera(Player player , boolean state){
        if(state){
            PacketPlayOutCamera packet = new PacketPlayOutCamera(entityPlayer);
            PatchedEntity.addAuthorizedPacket(packet,1);
            sendPacket(player,packet);
        }else {
            PacketPlayOutCamera packet = new PacketPlayOutCamera(((CraftPlayer)player).getHandle());
            PatchedEntity.addAuthorizedPacket(packet,1);
            sendPacket(player,packet);
        }

    }
}