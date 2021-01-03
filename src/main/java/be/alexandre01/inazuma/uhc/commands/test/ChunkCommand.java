package be.alexandre01.inazuma.uhc.commands.test;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.listeners.game.WorldGenEvent;
import be.alexandre01.inazuma.uhc.utils.NmsUtils;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.joor.Reflect.on;
import static org.joor.Reflect.onClass;

public class ChunkCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            if(command.getName().equalsIgnoreCase("lChunk")){
                if(args.length == 0){
                    WorldGenEvent.g = true;
                    return true;
                }
                Player player = (Player) sender;
                Location loc = new Location(player.getWorld(),Integer.parseInt(args[0]),1,Integer.parseInt(args[1]));
                Chunk c = loc.getChunk();
                for(Chunk chunk : ChunkCommand.around(c,13)){
                    chunk.load(true);
                    CraftChunk chunkNMS = ((CraftChunk) chunk);
                    chunkNMS.load(true);
                    if(!chunk.isLoaded()) chunk.load(true);
                    loadChunkToPlayer(player,chunkNMS.getHandle());
                }
                loc.setY(100);
                player.teleport(loc);

            }

        }
        return false;
    }

    public void loadChunkToPlayer(Player player, net.minecraft.server.v1_8_R3.Chunk chunk){

        Object packet = onClass(NmsUtils.getFullyQualifiedClassName("PacketPlayOutMapChunk")).create(chunk,true,20).get();
        World world;
       /* on(player).call("getHandle")
                .field("playerConnection")
                .call("sendPacket",packet);*/
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet) packet);
    }
    public static Collection<Chunk> around(Chunk origin, int radius) {
            org.bukkit.World world =  origin.getWorld();

            int length = (radius * 2) + 1;
            Set<Chunk> chunks = new HashSet<>(length * length);

            int cX = origin.getX();
            int cZ = origin.getZ();

            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    chunks.add(world.getChunkAt(cX + x, cZ + z));
                }
            }
            return chunks;

        }

}
