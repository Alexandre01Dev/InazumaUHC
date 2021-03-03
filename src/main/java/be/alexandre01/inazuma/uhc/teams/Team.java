package be.alexandre01.inazuma.uhc.teams;


import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.generations.Plateform;
import be.alexandre01.inazuma.uhc.generations.chunks.ChunkCoord;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.spectators.BukkitTeamInitializer;
import be.alexandre01.inazuma.uhc.utils.ScoreboardUtil;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import sun.util.resources.cldr.ro.CalendarData_ro_RO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class Team {
    private HashMap<UUID, Player> players;
    private ArrayList<Player> tPlayers;
    private ArrayList<Player> deaths;
    private boolean hasLose = false;
    private Plateform plateform;
    private IPreset p;
    private Location l;

    public Team(){
        players =  new HashMap<>();
        p = Preset.instance.p;
        deaths = new ArrayList<>();
    }

    public void addPlayer(Player player){
        players.put(player.getUniqueId(),player);
    }
    public void rmvPlayer(Player player){
        players.put(player.getUniqueId(),player);
    }

    public void killPlayer(Player player){
        deaths.add(player);
    }

    public boolean isKilled(Player player){
        return deaths.contains(player);
    }

    public HashMap<UUID, Player> getPlayers() {
        return players;
    }

    public ArrayList<Player> getDeaths() {
        return deaths;
    }

    public void teleport(){
        Random rand1 = new Random();
        int size = p.getBorderSize(World.Environment.NORMAL);
        int x = rand1.nextInt(size - ((-size) + 1)) + (-size);
        Random rand2 = new Random();
        int z = rand2.nextInt(size - ((-size) + 1)) + (-size);
        plateform = new Plateform(Plateform.PlateformType.CUBE,x,120,z);
        plateform.setCube(5,6,6);
        plateform.addRegisterPlateform();
        plateform.spawn();

        World w = InazumaUHC.get.worldGen.defaultWorld;
        this.l = new Location(w,x,120,z);

        ArrayList<ChunkCoord> chunkCoords = around(l.getChunk(),Bukkit.getViewDistance());


        for(Player player : getPlayers().values()){
            EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
            PlayerConnection playerConnection = entityPlayer.playerConnection;
            for(ChunkCoord chunkCoord : chunkCoords){
                Chunk c = l.getWorld().getChunkAt(chunkCoord.getX(),chunkCoord.getZ());
                net.minecraft.server.v1_8_R3.Chunk nmsc = ((CraftChunk)c).getHandle();

                PacketPlayOutMapChunk p = new PacketPlayOutMapChunk(nmsc,true,20);
                player.sendMessage("[DEBUG] Send packet >> Chunk (X="+c.getX()+"; Z="+c.getZ()+")");
                playerConnection.sendPacket(p);
            }
            player.sendMessage("[DEBUG] Teleport");
            player.teleport(l);
            player.setWalkSpeed(0.2f);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
        }

        tPlayers = new ArrayList<>(players.values());
        setBukkitTeam();
    }

    public void setBukkitTeam(){
        Scoreboard score = Bukkit.getScoreboardManager().getMainScoreboard();
        for(Player player : players.values()){


            org.bukkit.scoreboard.Team t = BukkitTeamInitializer.setAlive(player);
            ScoreboardUtil.get.addPlayer(score,t,player);

        }

        for(Player players : Bukkit.getOnlinePlayers()){
            players.setScoreboard(score);
        }
    }

    public Plateform getPlateform() {
        return plateform;
    }

    public ArrayList<Player> getAllPlayers() {
        return tPlayers;
    }

    public Location getLocation() {
        return l;
    }

    public void setHasLose(boolean hasLose) {
        this.hasLose = hasLose;
    }

    private ArrayList<ChunkCoord> around(Chunk origin, int radius) {

        org.bukkit.World world =  origin.getWorld();

        int length = (radius * 2) + 1;
        ArrayList<ChunkCoord> chunks = new ArrayList<>(length * length);

        int cX = origin.getX();
        int cZ = origin.getZ();

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                System.out.println(cX + x+">"+cZ + z);
                chunks.add(new ChunkCoord(cX + x, cZ + z));
            }
        }
        return chunks;

    }
}
