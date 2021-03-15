package be.alexandre01.inazuma.uhc.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class CustomHead {

    private ItemStack itemStack;
    @Getter @Setter private String texture;
    public CustomHead(String texture,String name){
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullM = (SkullMeta) skull.getItemMeta();
        skullM.setDisplayName(name);
        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", texture));
        Field profileField = null;

        try {
            profileField = skullM.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullM, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        skull.setItemMeta(skullM);
        this.itemStack = skull;
        this.texture = texture;
    }
    public ItemStack toItemStack(){
        return itemStack;
    }

    public static Skull toSkull(Block block,String texture){
        final Skull skull = (Skull)block.getState();
        skull.setSkullType(SkullType.PLAYER);
        skull.setRotation(BlockFace.NORTH_EAST);

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures",texture));
        try {
            Field profileField = skull.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skull, profile);
        }catch (NoSuchFieldException | IllegalAccessException e) { e.printStackTrace(); }
        skull.update(); // so that the result can be seen
        return skull;
    }

}
