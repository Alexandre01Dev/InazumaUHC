package be.alexandre01.inazuma.uhc.host;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.config.LanguageData;
import be.alexandre01.inazuma.uhc.config.Messages;
import be.alexandre01.inazuma.uhc.host.gui.HostGUI;
import be.alexandre01.inazuma.uhc.host.gui.TGUI;
import be.alexandre01.inazuma.uhc.host.option.HostContainer;
import be.alexandre01.inazuma.uhc.host.option.HostOption;
import be.alexandre01.inazuma.uhc.host.option.VarType;
import be.alexandre01.inazuma.uhc.listeners.host.InventoryClick;
import be.alexandre01.inazuma.uhc.listeners.host.InventoryClose;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.PresetData;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Host {
    public HashMap<UUID, ArrayList<TGUI>> lastTGUI = new HashMap<>();
    public HashMap<UUID, TGUI> currentTGUI = new HashMap<>();
    IPreset iPreset;
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
        hostContainer.setDescription("Les settings bordel de sushi");
        hostContainer.setSlot(0);
        hostContainers.put(0,hostContainer);

        //NETHER ACTIVATED
        HostOption nether = new HostOption(presetData.hasNether,"hasNether");
        nether.setModifiable(true);
        nether.setVarType(VarType.BOOLEAN);
        nether.setDescription(messages.get("hasNether"));
        nether.setItemStack(new ItemStack(Material.NETHERRACK));

        //Minimum player to Start
        HostOption minPlayerToStart = new HostOption(presetData.minPlayerToStart,"minPlayerToStart");
        minPlayerToStart.setModifiable(true);
        minPlayerToStart.setVarType(VarType.INTEGER);
        minPlayerToStart.setDescription(messages.get("minPlayerToStart"));
        minPlayerToStart.setItemStack(new ItemStack(Material.WOOD));
        minPlayerToStart.setMinAndMax(new int[]{2,50});

        //Total Time
        HostOption totalTime = new HostOption(presetData.totalTime,"totalTime");
        totalTime.setModifiable(true);
        totalTime.setVarType(VarType.INTEGER);
        totalTime.setDescription(messages.get("totalTime"));
        totalTime.setItemStack(new ItemStack(Material.GLASS));
        totalTime.setMinAndMax(new int[]{50,90});
        totalTime.setRange(5);

        //Total Player
        HostOption playerSize = new HostOption(presetData.playerSize,"playerSize");
        playerSize.setModifiable(true);
        playerSize.setVarType(VarType.INTEGER);
        playerSize.setDescription(messages.get("playerSize"));
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
        teamSize.setModifiable(true);
        //teamSize.setDescription(messages.get("teamSize"));
        teamSize.setMinAndMax(new int[]{1,4});
        teamSize.setVarType(VarType.INTEGER);
        ItemBuilder itemBuilder = new ItemBuilder(Material.SKULL_ITEM);
        itemBuilder.setName("Team Size");
        teamSize.setItemStack(itemBuilder.toItemStack());

        //Bordure
        HostOption borderSize = new HostOption(presetData.borderSize,"borderSize");
        borderSize.setModifiable(true);

        borderSize.setMinAndMax(new int[]{1,1000});
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
        borderSizeIN.setName("borderSize");
        borderSizeN.setItemStack(borderSizeIN.toItemStack());

        hostContainer.getHostOptions().add(nether);
        hostContainer.getHostOptions().add(minPlayerToStart);
        hostContainer.getHostOptions().add(totalTime);
        hostContainer.getHostOptions().add(playerSize);
        hostContainer.getHostOptions().add(teamSize);
        hostContainer.getHostOptions().add(borderSize);
        hostContainer.getHostOptions().add(borderSizeN);
        hostContainer.getHostOptions().add(invisibilityTimer);
        hostContainer.deploy();

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

    public Inventory getInv() {
        return inv;
    }

    public HashMap<UUID, ArrayList<TGUI>> getLastTGUI() {
        return lastTGUI;
    }
}
