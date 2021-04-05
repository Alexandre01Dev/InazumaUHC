package be.alexandre01.inazuma.uhc.host;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.config.LanguageData;
import be.alexandre01.inazuma.uhc.config.Messages;
import be.alexandre01.inazuma.uhc.host.gui.HostGUI;
import be.alexandre01.inazuma.uhc.host.gui.TGUI;
import be.alexandre01.inazuma.uhc.host.option.HostButton;
import be.alexandre01.inazuma.uhc.host.option.HostContainer;
import be.alexandre01.inazuma.uhc.host.option.HostOption;
import be.alexandre01.inazuma.uhc.host.option.VarType;
import be.alexandre01.inazuma.uhc.listeners.host.InventoryClick;
import be.alexandre01.inazuma.uhc.listeners.host.InventoryClose;
import be.alexandre01.inazuma.uhc.modules.Module;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.PresetData;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Host {
    public HashMap<UUID, ArrayList<TGUI>> lastTGUI = new HashMap<>();
    public HashMap<UUID, TGUI> currentTGUI = new HashMap<>();
    HostGUI hostGUI;
    Inventory inv;
    PresetData presetData;
    ArrayList<HostOption> hostOptions = new ArrayList<>();
    private HashMap<Integer,HostContainer> hostContainers = new HashMap<>();
    public Host(){
        presetData = Preset.instance.pData;
        LanguageData messages = Messages.getSection("hostOption");
        //ADD PRESET
        HostContainer hostContainer = new HostContainer("Settings");
        hostContainer.setItemStack(new ItemBuilder(Material.REDSTONE_COMPARATOR).setName(hostContainer.getName()).toItemStack());
        hostContainer.setDescription("Les settings");
        hostContainer.setSlot(0);
        hostContainers.put(0,hostContainer);

        //NETHER ACTIVATED
        HostOption nether = new HostOption(presetData.hasNether,"hasNether");
        nether.setModifiable(true);
        nether.setVarType(VarType.FASTBOOL);
        nether.setItemStack(new ItemStack(Material.NETHERRACK));
        nether.setDescription(new String[]{messages.get("hasNether")});
        //Minimum player to Start
        HostOption minPlayerToStart = new HostOption(presetData.minPlayerToStart,"minPlayerToStart");
        minPlayerToStart.setModifiable(true);
        minPlayerToStart.setVarType(VarType.INTEGER);
        minPlayerToStart.setItemStack(new ItemStack(Material.WOOD));
        minPlayerToStart.setName("Joueurs minimum pour commencer la partie");
        minPlayerToStart.setDescription(new String[]{messages.get("minPlayerToStart") });
        minPlayerToStart.setMinAndMax(new int[]{2,50});

        //Total Time
        HostOption totalTime = new HostOption(presetData.totalTime,"totalTime");
        totalTime.setModifiable(true);
        totalTime.setVarType(VarType.INTEGER);
        totalTime.setDescription(new String[]{messages.get("totalTime") });
        totalTime.setItemStack(new ItemStack(Material.GLASS));
        totalTime.setMinAndMax(new int[]{50,90});
        totalTime.setRange(5);

        //Total Player
        HostOption playerSize = new HostOption(presetData.playerSize,"playerSize");
        playerSize.setModifiable(true);
        playerSize.setVarType(VarType.INTEGER);
        playerSize.setDescription(new String[]{messages.get("playerSize")});
        playerSize.setItemStack(new ItemStack(Material.GLASS));
        playerSize.setMinAndMax(new int[]{2,50});
        playerSize.setRange(1);

        //Invisibility
        HostOption invisibilityTimer = new HostOption(presetData.invisibilityTime,"invisibilityTime");
        invisibilityTimer.setModifiable(true);
        invisibilityTimer.setRange(1);
        invisibilityTimer.setVarType(VarType.INTEGER);
        invisibilityTimer.setItemStack(new ItemBuilder(Material.POTION).toItemStack());
        invisibilityTimer.setMinAndMax(new int[]{1,20});

        //Team Size
        HostOption teamSize = new HostOption(presetData.teamSize,"teamSize");
        teamSize.setModifiable(false);

        //teamSize.setDescription(messages.get("teamSize"));
        teamSize.setMinAndMax(new int[]{1,4});
        teamSize.setVarType(VarType.INTEGER);

        ItemBuilder itemBuilder = new ItemBuilder(Material.SKULL_ITEM);
        teamSize.setItemStack(itemBuilder.toItemStack());
        teamSize.setName("Taille des teams");

        //Bordure
        HostOption borderSize = new HostOption(presetData.borderSize,"borderSize");
        borderSize.setModifiable(true);

        borderSize.setMinAndMax(new int[]{50,6000});
        borderSize.setVarType(VarType.INTEGER);
        ItemBuilder borderSizeI = new ItemBuilder(Material.SKULL_ITEM);
        borderSizeI.setName("borderSize");
        borderSize.setRange(100);
        borderSize.setItemStack(borderSizeI.toItemStack());

        //Bordure
        HostOption borderSizeN = new HostOption(presetData.borderSizeNether,"borderSizeNether");
        borderSizeN.setModifiable(true);
        borderSizeN.setVarType(VarType.INTEGER);
        borderSizeN.setMinAndMax(new int[]{1,200});
        borderSize.setRange(50);
        ItemBuilder borderSizeIN = new ItemBuilder(Material.SKULL_ITEM);
        borderSizeIN.setName("Taille de la bordure");
        borderSizeN.setItemStack(borderSizeIN.toItemStack());

        hostContainer.getHostOptions().put(0,nether);
        hostContainer.getHostOptions().put(1,minPlayerToStart);
        hostContainer.getHostOptions().put(2,totalTime);
        hostContainer.getHostOptions().put(3,playerSize);
        hostContainer.getHostOptions().put(4,teamSize);
        hostContainer.getHostOptions().put(5,borderSize);
        hostContainer.getHostOptions().put(6,borderSizeN);
        hostContainer.getHostOptions().put(7,invisibilityTimer);



        hostContainer.deploy();

        HostContainer presets = new HostContainer("§cPresets");
        presets.setItemStack(new ItemBuilder(Material.BEACON).setName(presets.getName()).toItemStack());
        presets.setDescription("Les PRESETS");
        presets.setSlot(2);
        hostContainers.put(2,presets);

        int p = 0;
        for(Class<?> preset : Preset.instance.getPresets()){
            Module module = Preset.instance.modules.get(preset);
            boolean defaultValue = false;
            if(Preset.instance.p.getClass() == preset){
                defaultValue = true;
            }
            HostOption s = new HostOption(defaultValue,"preset");
            s.setVarType(VarType.FASTBOOL);
            s.setModifiable(true);
            s.setName(module.getModuleName());

            StringBuilder sb = new StringBuilder();
            int i = 0;
            for(String m : module.getAuthors()){
                sb.append(m);
                if(module.getAuthors().length-2 == i){
                sb.append(" & ");
                }else {
                    sb.append(", ");
                }
            }
            s.setDescription(new String[]{module.getDescription(),"§bAuteur(s): "+ sb.toString()});
            s.setItemStack(new ItemStack(module.getMaterial()));
            s.setAction(new HostOption.action() {
                @Override
                public void a(Object value) {
                    boolean b = (boolean) value;
                    if(b){
                        for(HostButton button : presets.getHostOptions().values()){
                            HostOption h = (HostOption) button;
                            if(h != s){
                                h.value = false;
                            }
                        }

                        Role.clear();
                        Preset.instance.set(module);


                        InazumaUHC.get.onLoadPreset();
                        GameState.get().setTo(GameState.get().getState());
                    }else {
                        s.value = true;
                    }

                    s.updateItemStack();
                }
            });
            presets.getHostOptions().put(p,s);
            p++;
        }
        presets.deploy();
        HostContainer scenarios = new HostContainer("§bScénarios");
        scenarios.setItemStack(new ItemBuilder(Material.EYE_OF_ENDER).setName(scenarios.getName()).toItemStack());
        scenarios.setDescription("Les scénarios");
        scenarios.setSlot(3);
        hostContainers.put(3,scenarios);

        int n = 0;
        for(Scenario scenario : Scenario.getScenarios().values()){
            boolean defaultValue = false;
            if(presetData.getScenarios().contains(scenario.getClass())){
                defaultValue = true;
            }
                HostOption s = new HostOption(defaultValue,"scenario");
                s.setVarType(VarType.FASTBOOL);
                s.setModifiable(true);
                s.setName(scenario.getName());
                s.setDescription(new String[]{scenario.getDescription()});
                 s.setItemStack(scenario.getItemStack());
                s.setAction(new HostOption.action() {
                    @Override
                    public void a(Object value) {
                        boolean b = (boolean) value;
                        if(b){
                            presetData.getScenarios().add(scenario.getClass());
                        }else {
                            presetData.getScenarios().remove(scenario.getClass());
                        }

                        s.value = value;
                        s.updateItemStack();
                    }
                });
         scenarios.getHostOptions().put(n,s);
         n++;

        }
        scenarios.deploy();
        InazumaUHC.get.lm.addListener(new InventoryClick(this));
        InazumaUHC.get.lm.addListener(new InventoryClose(this));

        this.hostGUI = new HostGUI(this);
        inv = hostGUI.getInv();
    }


    public PresetData getPresetData() {
        return presetData;
    }

    public void setPresetData(PresetData presetData) {
        this.presetData = presetData;
    }

    public ArrayList<HostOption> getHostOptions() {
        return hostOptions;
    }

    public void setHostOptions(ArrayList<HostOption> hostOptions) {
        this.hostOptions = hostOptions;
    }

    public HashMap<Integer, HostContainer> getHostContainers() {
        return hostContainers;
    }

    public void setHostContainers(HashMap<Integer, HostContainer> hostContainers) {
        this.hostContainers = hostContainers;
    }

    public HostGUI getHostGUI() {
        return hostGUI;
    }

    public Inventory getInv() {
        return inv;
    }

    public HashMap<UUID, ArrayList<TGUI>> getLastTGUI() {
        return lastTGUI;
    }
}
