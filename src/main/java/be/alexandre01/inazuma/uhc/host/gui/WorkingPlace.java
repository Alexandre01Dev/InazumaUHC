package be.alexandre01.inazuma.uhc.host.gui;

import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import be.alexandre01.inazuma.uhc.utils.fastinv.FastInv;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class WorkingPlace {
    private FastInv inv;
    private int beginSlot = 10;
    private int[] ignoredSlots = {17,18,26,27,35};
    private int endSlot = 35;
    private HashMap<Integer,ItemStack> decorations = new HashMap<>();
    private boolean isBuilded = false;
    private Tuple<ColorVariante,ColorVariante> varianteTuple = null;
    public WorkingPlace nextWorkingPlace = null;
    public WorkingPlace(FastInv inv){
        this.inv = inv;
    }

    public void setColorVariations(ColorVariante principal, ColorVariante secondary){
        varianteTuple = new Tuple<>(principal,secondary);
    }
    private void buildDecoration(){
        if(varianteTuple == null)
            varianteTuple = new Tuple<>(ColorVariante.Magenta,ColorVariante.Purple);

        //DECORATIONS
        ItemStack g_glass = new ItemBuilder(Material.STAINED_GLASS_PANE)
                .setWoolColor(DyeColor.GRAY).setName(" ").toItemStack();

        ItemStack p_glass = new ItemStack(Material.STAINED_GLASS_PANE);

        p_glass.getData().setData((byte) varianteTuple.a().data);

        ItemStack s_glass = new ItemStack(Material.STAINED_GLASS_PANE);

        s_glass.getData().setData((byte) varianteTuple.a().data);
        int[] greyPos = {1*6-1,2*6-1,4*6-1,6*6-1,8*6-1,9*6-1,5*5-1};
        int[] principalPos = {
                /*TOP*/2*1-1,4*1-1,6*1-1,8*1-1,
                /*LEFT*/ 1*2-1,1*3-1,1*4-1,
                /*RIGHT*/ 9*2-1,9*3-1,9*4-1,
                /*BOTTOM*/2*6-1,3*6-1,4*6-1,6*6-1,7*6-1,8*6-1,
        };
        int[] secondaryPos = {1*1-1,1*5-1,9*1-1,9*5-1};


        for (int i = 0; i < greyPos.length; i++) {
            inv.setItem(greyPos[i],g_glass);
        }
        for (int i = 0; i < principalPos.length; i++) {
            inv.setItem(principalPos[i],p_glass);
        }
        for (int i = 0; i < secondaryPos.length; i++) {
            inv.setItem(secondaryPos[i],s_glass);
        }
    }

    private void specialButtons(){

    }

    public void setSlot(int i, ItemStack itemStack){
        int whereToPlace = i+beginSlot;
        for (Integer l : ignoredSlots){
            if(whereToPlace >= l)
                whereToPlace++;
        }

        if(!isBuilded){
            inv.setItem(whereToPlace,itemStack);
            decorations.put(whereToPlace,itemStack);
            return;
        }

        while (inv.getInventory().getItem(whereToPlace).getType() != null || whereToPlace > endSlot){
            if(whereToPlace > endSlot){
                if(nextWorkingPlace == null){
                    nextWorkingPlace = new WorkingPlace(inv);
                    for(Integer d : decorations.keySet()){
                        nextWorkingPlace.setSlot(d,decorations.get(d));
                    }
                    nextWorkingPlace.readyToSetClickableItems();
                }

                nextWorkingPlace.setSlot(i-endSlot,itemStack);
                return;
            }
            whereToPlace++;
        }
        inv.setItem(i+beginSlot,itemStack);
    }


    public void setSlots(int[] i, ItemStack itemStack){
        for(Integer ints : i){
            setSlot(ints,itemStack);
        }
    }
    public void readyToSetClickableItems(){
        isBuilded = true;
    }



    public enum ColorVariante{
        White(0),
        Gray(8),
        DarkGray(7),
        Black(15),

        Magenta(10),
        Purple(2),

        Green(5),
        DarkGreen(13),

        Yellow(4),
        Orange(1),
        Red(14),
        Pink(6),

        LightBlue(3),
        Cyan(9),
        Blue(11);

        private int data;
        ColorVariante(int data){
            this.data = data;
        }

    }
}
