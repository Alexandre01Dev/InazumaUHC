package be.alexandre01.inazuma.uhc.worlds;

import net.minecraft.server.v1_8_R3.BiomeBase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;

import java.lang.reflect.Field;

public class WorldUtils {
    public static void patchBiomes() {
        try {
            Field biomesField = BiomeBase.class.getDeclaredField("biomes");
            biomesField.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(biomesField, biomesField.getModifiers() & 0xFFFFFFEF);
            if (biomesField.get((Object)null) instanceof BiomeBase[]) {
                BiomeBase[] biomes = (BiomeBase[])biomesField.get((Object)null);
                biomes[BiomeBase.DEEP_OCEAN.id] = BiomeBase.FOREST;
                biomes[BiomeBase.FROZEN_OCEAN.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.FROZEN_OCEAN.id] = BiomeBase.FOREST;
                biomes[BiomeBase.OCEAN.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.COLD_BEACH.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.BEACH.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.STONE_BEACH.id] = BiomeBase.FOREST;
                biomes[BiomeBase.JUNGLE.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.JUNGLE_EDGE.id] = BiomeBase.FOREST;
                biomes[BiomeBase.JUNGLE_HILLS.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.SWAMPLAND.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.EXTREME_HILLS_PLUS.id] = BiomeBase.PLAINS;
                biomesField.set((Object)null, biomes);
            }
        } catch (NoSuchFieldException|IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Location findBiome(Biome biome, World world, int max) throws Exception {
        for (int i = -max; i < max; i += 16) {
            for (int j = -max; j < max; j += 16) {
                if (world.getBiome(i, j) == biome)
                    return new Location(world, i, 151.0D, j);
            }
        }
        System.out.println("No roofed found, delete the world folder and restart");
        Bukkit.shutdown();
        throw new Exception("No roofed found");
    }
}
