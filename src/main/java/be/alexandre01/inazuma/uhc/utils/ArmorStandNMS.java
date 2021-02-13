package be.alexandre01.inazuma.uhc.utils;


import be.alexandre01.inazuma.uhc.InazumaUHC;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

public class ArmorStandNMS extends Reflections {
    private int entityID;
    private Location location;
    private String asname;
    private Player player;
    private EntityArmorStand as;

    public ArmorStandNMS(Player p, String name, Location location){
        //entityID = (int)Math.ceil(Math.random() * 1000) + 2000;
        System.out.println(entityID);
        asname = name;
        player = p;
        // changeSkin("");
        this.location = location.clone();
    }




    public void status(int status){
        PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus();
        setValue(packet, "a", entityID);
        setValue(packet, "b", (byte)status);
        sendPacket(player,packet);
    }

    public void equip(int slot, ItemStack itemstack){
        PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment();
        setValue(packet, "a", entityID);
        setValue(packet, "b", slot);
        setValue(packet, "c", itemstack);
        sendPacket(player,packet);
    }
    public void setup(boolean arms , boolean baseblate, boolean small){
        DataWatcher w = new DataWatcher((DataWatcher) null);
        byte b1 = 0x00;
        byte b2 = 0x00;
        byte b3 = 0x00;
        if(arms){
            b1 = 0x04;
        }
        if(baseblate){
            b2 = 0x08;

        }
        if(small){
            b3 = 0x01;


        }
        byte b = (byte) (b1 | b2 | b3);
        w.a(10,b);
        PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(as.getId(),w,true);
        sendPacket(player,metadataPacket);

    }
    public void setCamera(Player player , boolean state){
        if(state){
            PacketPlayOutCamera packet = new PacketPlayOutCamera(as);

            sendPacket(player,packet);
        }else {
            PacketPlayOutCamera packet = new PacketPlayOutCamera(((CraftPlayer)player).getHandle());

            sendPacket(player,packet);
        }

    }


    public void setPose(ArmorStand pose,float x,float y,float z){

        DataWatcher w = new DataWatcher((DataWatcher) null);
        int value = 11;
        if(pose.equals(ArmorStand.HEAD)){
            value =11;
        }
        if(pose.equals(ArmorStand.BODY)){
            value=12;
        }
        if(pose.equals(ArmorStand.LEFT_ARM)){
            value=13;
        }
        if(pose.equals(ArmorStand.RIGHT_ARM)){
            value=14;
        }
        if(pose.equals(ArmorStand.LEFT_LEG)){
            value=15;
        }
        if(pose.equals(ArmorStand.RIGHT_LEG)){
            value=16;
        }

        w.a(value,new Vector3f(x,y,z));
        PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(as.getId(),w,true);
        sendPacket(player,metadataPacket);
    }
    public void setPoseToAnotherPose(final ArmorStand pose, final float x1 , final float y1 , final float z1, final float x2 , final float y2 , final float z2, final float speed, final boolean loop){

        new BukkitRunnable() {
            float x = x1;
            float y = y1;
            float z = z1;
            float xmax = x2;
            float ymax = y2;
            float zmax = z2;
            @Override
            public void run() {
                if(x< xmax){
                    x= x+speed;
                    setPose(pose,x,y,z);
                }
                if(x>xmax){
                    x= x-speed;
                    setPose(pose,x,y,z);
                }
                if(y< ymax){
                    y= y+speed;
                    setPose(pose,x,y,z);
                }
                if(y>ymax){
                    y= y-speed;
                    setPose(pose,x,y,z);
                }
                if(z< zmax){
                    z= z+speed;
                    setPose(pose,x,y,z);
                }
                if(z>xmax){
                    z= z-speed;
                    setPose(pose,x,y,z);
                }
                if(x == xmax && y == ymax && z == zmax){
                    if(!loop){
                        cancel();
                    }else{
                        if(xmax == x1 && ymax == y1 && zmax == z1){
                            xmax = x2;
                            ymax = y2;
                            zmax = z2;
                        }else {
                            xmax = x1;
                            ymax = y1;
                            zmax = z1;
                        }

                    }

                }

            }
        }.runTaskTimer(InazumaUHC.get,0,1);
    }
    public void setInvisible(){
        DataWatcher w = new DataWatcher((DataWatcher) null);

        w.a(0,(byte)0x20);
    PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(as.getId(),w,true);
    sendPacket(player,metadataPacket);

}
    public void setNameVisible(EntityArmorStand eas){
        eas.setCustomNameVisible(true);
    }

    public void setName(EntityArmorStand eas,String name){
        eas.setCustomName(name);
    }
    public EntityArmorStand spawn(){
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) player.getWorld()).getHandle();
        EntityArmorStand nmsStand = new EntityArmorStand(nmsWorld);
        nmsStand.setPositionRotation(location.getX(),location.getY(),location.getZ(), location.getYaw(),location.getPitch());
        nmsStand.setCustomName(asname);
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(nmsStand);
        entityID = nmsStand.getBukkitEntity().getEntityId();
        as = nmsStand;
        sendPacket(player,packet);
        return nmsStand;
    }

    public void teleport(Location location){

        PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport();
        setValue(packet, "a", entityID);
        setValue(packet, "b", getFixLocation(location.getX()));
        setValue(packet, "c", getFixLocation(location.getY()));
        setValue(packet, "d", getFixLocation(location.getZ()));
        setValue(packet, "e", getFixRotation(location.getYaw()));
        setValue(packet, "f", getFixRotation(location.getPitch()));
        new EulerAngle(1,1,1);
        sendPacket(player,packet);
        headRotation(location.getYaw(), location.getPitch());

        this.location = location.clone();

    }


    public void headRotation(float yaw,float pitch){
        PacketPlayOutEntity.PacketPlayOutEntityLook packet = new PacketPlayOutEntity.PacketPlayOutEntityLook(entityID, getFixRotation(yaw),getFixRotation(pitch) , true);
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

        teleport(npcloc.setDirection(loc.subtract(npcloc).toVector()));
    }

    public void destroy(){
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] {entityID});
        sendPacket(player,packet);
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
    public EntityArmorStand getAs(){
        return as;
    }


}