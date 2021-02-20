package be.alexandre01.inazuma.uhc.utils;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.inventory.ItemStack;

public class WeaponItem {
    public double damage;
    public double sharpness;
    public ItemStack itemStack;

    public WeaponItem(ItemStack it){
        this.itemStack = it;
        switch (it.getType()){
            case WOOD_SWORD:
                damage = 4;
                break;
            case STONE_SWORD:
                damage = 5;
                break;
            case IRON_SWORD:
                damage= 6;
                break;
            case GOLD_SWORD:
                damage = 4;
                break;
            case DIAMOND_SWORD:
                damage = 7;
                break;
            case WOOD_AXE:
                damage = 3;
                break;
            case GOLD_AXE:
                damage = 3;
                break;
            case STONE_AXE:
                damage = 4;
                break;
            case IRON_AXE:
                damage = 5;
                break;
            case DIAMOND_AXE:
                damage = 6;
                break;
            case WOOD_PICKAXE:
                damage = 2;
                break;
            case STONE_PICKAXE:
                damage = 3;
                break;
            case IRON_PICKAXE:
                damage = 4;
                break;
            case GOLD_PICKAXE:
                damage = 2;
                break;
            case DIAMOND_PICKAXE:
                damage = 5;
                break;
            case WOOD_SPADE:
                damage = 1;
                break;
            case STONE_SPADE:
                damage = 2;
                break;
            case GOLD_SPADE:
                damage = 1;
                break;
            case IRON_SPADE:
                damage = 3;
                break;
            case DIAMOND_SPADE:
                damage = 4;
                break;
            default:
                damage = 0;
                break;

        }
        sharpness = sharpnessCalc();
    }

    public double getDamage(){
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getSharpness() {
        return sharpness;
    }

    public void setSharpness(double sharpness) {
        this.sharpness = sharpness;
    }

    public double sharpnessCalc(){
        if(!itemStack.containsEnchantment(Enchantment.DAMAGE_ALL)){
            return 0;
        }

        return 0.5 * itemStack.getEnchantmentLevel(Enchantment.DAMAGE_ALL) + 0.5;
    }

}
