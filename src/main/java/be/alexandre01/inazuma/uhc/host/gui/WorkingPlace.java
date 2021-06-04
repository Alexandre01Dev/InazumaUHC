package be.alexandre01.inazuma.uhc.host.gui;

import be.alexandre01.inazuma.uhc.host.HostButton;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import be.alexandre01.inazuma.uhc.utils.SoundProperty;
import be.alexandre01.inazuma.uhc.utils.fastinv.FastInv;
import lombok.Data;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.InventoryUtils;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftShapedRecipe;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.HashMap;
@Data
public class WorkingPlace {
    private static HostButton discord = new HostButton(Material.LAPIS_BLOCK,"§bPublier sue le §9discord", HostButton.Type.DIRECT);
    private static HostButton start = new HostButton(Material.EMERALD_BLOCK,"§aLancement de la §2PREGEN", HostButton.Type.DIRECT);
    private static HostButton saves = new HostButton(Material.ENCHANTED_BOOK,"§eSauvegardes",HostButton.Type.REDIRECTION);
    private static HostButton names = new HostButton(Material.SIGN,"§7Nom de la Partie: Game-?",HostButton.Type.OPTION);
    private static HostButton host = new HostButton(Material.CHEST,"§7Host par un type idiot",HostButton.Type.NONE);
    private static HostButton infos = new HostButton(Material.NAME_TAG,"§7Infos", HostButton.Type.NONE);
    private FastInv inv;
    private SoundProperty soundProperty = new SoundProperty(Sound.BURP,1,1);
    private HashMap<Player,FastInv> invs = new HashMap<>();
    private int beginSlot = 9;
    private int[] ignoredSlots /*needToBeInOrder*/= {getCase(9,2),getCase(1,3),getCase(9,3),getCase(1,4),getCase(9,4)};
    private int endSlot = 36;
    private HashMap<Integer,ItemStack> decorations = new HashMap<>();
    private boolean isBuilded = false;
    private HashMap<Integer,HostButton> hostsButtons = new HashMap<>();
    private Tuple<ColorVariante,ColorVariante> varianteTuple = null;
    public WorkingPlace nextWorkingPlace = null;
    public WorkingPlace(FastInv fastInv){
        this.inv = new FastInv(fastInv.getInventory().getSize(),fastInv.getInventory().getTitle());
    }
    //ADDPLAYER
    public void addInstance(Player player){
        FastInv fastInv = new FastInv(inv.getInventory().getSize(),inv.getInventory().getTitle());
        updateInventory(fastInv);

        invs.put(player,fastInv);
    }

    //ADDPLAYER WITH INVENTORY
    public void addInstance(Player player,FastInv fastInv){
        updateInventory(fastInv);
        invs.put(player,fastInv);
    }
    //RMVPLAYER
    public void rmvInstance(Player player){
        invs.remove(player);
    }


    //UPDATING INVENTORIES
    public void updateItem(int i, HostButton hostButton){
        updateItem(i,hostButton.getItemStack());
    }
    public void updateItem(int i, ItemStack itemStack){
        for(FastInv inv : invs.values()){
            inv.setItem(i,itemStack);
        }
    }
    private void updateInventory(FastInv fastInv){
        int i = 0;
        for(ItemStack itemStack : inv.getInventory().getContents()){
            fastInv.setItem(i,itemStack);
            i++;
        }
    }
    public void updateInventories(){
        for(FastInv inv : invs.values()){
            updateInventory(inv);
        }
    }
    public int actualPlace = 0;


    //OPTIONS
    public int addButton(HostButton hostButton){
        int i =  addButton(hostButton,actualPlace,false);
        actualPlace++;
        return i;
    }
    public void addButton(HostButton hostButton, int i){
        addButton(hostButton,i,false);
    }

    private int addButton(HostButton hostButton, int i,boolean customPlace){

        if(!customPlace){
            System.out.println("NotCustomPlace "+ i);
            i = setSlot(i,hostButton.getItemStack());
        }else {
            inv.setItem(i,hostButton.getItemStack());
            updateInventories();
        }
        hostsButtons.put(i,hostButton);
        hostButton.addOnWorkingPlace(this,i);
        return i;
    }
    //PRE-CONFIGURATION-UTILS
    public void setColorVariations(ColorVariante principal, ColorVariante secondary){
        varianteTuple = new Tuple<>(principal,secondary);
    }
    //PRE-CONFIGURATION
    private void buildDecoration(){
        if(varianteTuple == null)
            varianteTuple = new Tuple<>(ColorVariante.Magenta,ColorVariante.Purple);

        //DECORATIONS
        ItemStack g_glass = new ItemBuilder(Material.STAINED_GLASS_PANE)
                .setWoolColor(DyeColor.GRAY).setName(" ").toItemStack();

        ItemStack p_glass = new ItemStack(Material.STAINED_GLASS_PANE);

        ItemMeta p_meta = p_glass.getItemMeta();
        p_meta.setDisplayName(" ");
        p_glass.setItemMeta(p_meta);
        p_glass.setDurability(varianteTuple.a().data);

        ItemStack s_glass = new ItemStack(Material.STAINED_GLASS_PANE);

        ItemMeta s_meta = s_glass.getItemMeta();
        s_meta.setDisplayName(" ");
        s_glass.setItemMeta(s_meta);
        s_glass.setDurability(varianteTuple.b().data);


        int[] greyPos = {getCase(5,5),getCase(1,6),getCase(2,6),getCase(4,6),getCase(6,6),getCase(8,6),getCase(9,6)};
        int[] principalPos = {
                /*TOP*/getCase(2,1),getCase(4,1),getCase(6,1),getCase(8,1),
                /*LEFT*/ getCase(1,2),getCase(1,3),getCase(1,4),
                /*RIGHT*/ getCase(9,2),getCase(9,3),getCase(9,4),
                /*BOTTOM*/getCase(2,5),getCase(3,5),getCase(4,5),getCase(6,5),getCase(7,5),getCase(8,5),
        };
        int[] secondaryPos = {getCase(1,1),getCase(1,5),getCase(9,1),getCase(9,5)};


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
    //CALCULS
    public int getCase(int slot, int raw){
        return (slot-1)+((raw-1)*9);
    }
    public int getSlot(int slot, int raw){
        return (slot)+((raw-1)*7);
    }

    //PRE-CONFIGURATION
    private void staticButtons(WorkingPlace workingPlace){
        //nom de la partie
        workingPlace.addButton(names,workingPlace.getCase(3,1),true);

        //host par *****
        workingPlace.addButton(host,workingPlace.getCase(5,1),true);

        //Infos sur la partie
        workingPlace.addButton(infos,workingPlace.getCase(7,1),true);

        //Publication sur le discord
        workingPlace.addButton(discord,workingPlace.getCase(3,6),true);

        //Start
        workingPlace.addButton(start,workingPlace.getCase(5,6),true);

        //Saves
        workingPlace.addButton(saves,workingPlace.getCase(7,6),true);
    }



    //SLOTS
    public int setSlot(int i, ItemStack itemStack){
        int whereToPlace = i+beginSlot;
        System.out.println("Ibegin "+i);
        System.out.println("begin "+whereToPlace);
        for (int l : ignoredSlots){
            System.out.println(l+"Ignored SLOT");
            if(whereToPlace >= l){
                System.out.println(l+"Ignored SLOT CONFIRMED");
                whereToPlace++;
            }
        }

        if(!isBuilded){
            inv.setItem(whereToPlace,itemStack);
            decorations.put(whereToPlace,itemStack);
            return whereToPlace;
        }
        System.out.println(i);
        System.out.println(beginSlot);
        System.out.println(whereToPlace);
        while (inv.getInventory().getItem(whereToPlace) != null || whereToPlace > endSlot){
            System.out.println(whereToPlace);
            if(whereToPlace > endSlot){
                if(nextWorkingPlace == null){
                    nextWorkingPlace = new WorkingPlace(inv);
                    for(Integer d : decorations.keySet()){
                        nextWorkingPlace.setSlot(d,decorations.get(d));
                    }
                    nextWorkingPlace.readyToSetClickableItems();
                }
                System.out.println("ENDSLOT"+ (i-(endSlot-beginSlot)));

                nextWorkingPlace.setSlot((i-(endSlot-beginSlot)),itemStack);
                updateInventories();
                return i-endSlot;
            }
            whereToPlace++;
        }
        inv.setItem(whereToPlace,itemStack);
        updateInventories();
        return whereToPlace; //A FAIRE GAFFE
    }


    public void setSlots(int[] i, ItemStack itemStack){
        for(Integer ints : i){
            setSlot(ints,itemStack);
        }
    }



    //READY
    public void readyToSetClickableItems(){
        buildDecoration();
        staticButtons(this);
        isBuilded = true;
    }




    //SEPARATION
    public void setSeparation(int i,ItemStack itemStack){
        System.out.println("Separation : "+ ((endSlot-beginSlot)-ignoredSlots.length)/(i+1));
        for (int j = 0; j < ((endSlot-beginSlot)-ignoredSlots.length)/(i+1); j++) {

            int k = (j)*(i+1);
            System.out.println("K : "+k);
            if(k > 0){
                System.out.println("KKKK : "+ setSlot(k,itemStack));
            }

        }
    }
    public void setSeparation(int i,ColorVariante colorVariante){
        ItemStack c_glass = new ItemStack(Material.STAINED_GLASS_PANE);

        ItemMeta c_meta = c_glass.getItemMeta();
        c_meta.setDisplayName(" ");
        c_glass.setItemMeta(c_meta);
        c_glass.setDurability(colorVariante.data);

        setSeparation(i,c_glass);
    }

    //GETTER
    public FastInv getInventory(Player player){
        return invs.get(player);
    }
    public enum ColorVariante{
        White((byte)0),
        Gray((byte)8),
        DarkGray((byte)7),
        Black((byte)15),

        Magenta((byte)2),
        Purple((byte)10),

        Green((byte)5),
        DarkGreen((byte)13),

        Yellow((byte)4),
        Orange((byte)1),
        Red((byte)14),
        Pink((byte)6),

        LightBlue((byte) 3),
        Cyan((byte)9),
        Blue((byte)11),

        Brown((byte)12);



        private byte data;
        ColorVariante(byte data){
            this.data = data;
        }
    }

}
