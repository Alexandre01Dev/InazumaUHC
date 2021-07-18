package be.alexandre01.inazuma.uhc.scenarios;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.scenarios.betazombie.BetaZombie;
import be.alexandre01.inazuma.uhc.scenarios.cancelenchant.CancelEnchant;
import be.alexandre01.inazuma.uhc.scenarios.cateyes.CatEyes;
import be.alexandre01.inazuma.uhc.scenarios.cutclean.Cutclean;
import be.alexandre01.inazuma.uhc.scenarios.diamondlimit.DiamondLimit;
import be.alexandre01.inazuma.uhc.scenarios.hasteyboys.HasteyBoys;
import be.alexandre01.inazuma.uhc.scenarios.merite.Merite;
import be.alexandre01.inazuma.uhc.scenarios.rodless.RodLess;
import be.alexandre01.inazuma.uhc.scenarios.timber.Timber;
import be.alexandre01.inazuma.uhc.scenarios.trashpotion.TrashPotion;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Scenario {
    String name;
    String description;
    ItemStack itemStack;
    InazumaUHC inazumaUHC = InazumaUHC.get;
    public static boolean deployed = false;
    private static HashMap<Class<?>,Scenario> scenarios = new HashMap<>();


    ArrayList<Listener> listeners = new ArrayList<>();
    load load = null;

    public static void initialize(){
        //SCENARIOS
        scenarios.put(BetaZombie.class,new BetaZombie());
        scenarios.put(CatEyes.class,new CatEyes());
        scenarios.put(HasteyBoys.class,new HasteyBoys());
        scenarios.put(Cutclean.class,new Cutclean());
        scenarios.put(Timber.class,new Timber());
        scenarios.put(RodLess.class,new RodLess());
        scenarios.put(TrashPotion.class,new TrashPotion());
        scenarios.put(DiamondLimit.class,new DiamondLimit());
        scenarios.put(CancelEnchant.class,new CancelEnchant());
        scenarios.put(Merite.class,new Merite());
    }
    public Scenario(String name, String description) {
        this.name = name;
        this.description = description;
        itemStack = new ItemStack(Material.COBBLESTONE);
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void addListener(Listener... listeners){
        this.listeners.addAll(Arrays.asList(listeners));
    }

    public void deployListener(){
        if(!listeners.isEmpty()){
            for(Listener listener : listeners){
                inazumaUHC.lm.addListener(listener);
            }
        }
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public load getLoad(){
        return load;
    }

    public void onLoad(load load){
        this.load = load;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

    public static HashMap<Class<?>,Scenario> getScenarios() {
        return scenarios;
    }

    public interface load{
        void a();
    }
}
