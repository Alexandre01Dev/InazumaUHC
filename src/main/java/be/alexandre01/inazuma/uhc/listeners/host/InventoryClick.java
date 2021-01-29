package be.alexandre01.inazuma.uhc.listeners.host;

import be.alexandre01.inazuma.uhc.host.Host;
import be.alexandre01.inazuma.uhc.host.gui.HostOptionsGui;
import be.alexandre01.inazuma.uhc.host.gui.ModifierGUI;
import be.alexandre01.inazuma.uhc.host.gui.TGUI;
import be.alexandre01.inazuma.uhc.host.option.HostOption;
import be.alexandre01.inazuma.uhc.host.option.VarType;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class InventoryClick implements Listener {
    public Host host;

    public InventoryClick(Host host){
        this.host = host;

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        HumanEntity humanEntity = event.getWhoClicked();
        System.out.println(event.getSlot());

        if(host.currentTGUI.containsKey(humanEntity.getUniqueId())){
            event.setCancelled(true);
            TGUI t = null;
            TGUI tgui = host.currentTGUI.get(humanEntity.getUniqueId());
                if(tgui.getType() != TGUI.Type.HOME){
                    if(tgui.getType().equals(TGUI.Type.OPTIONS)){
                        HostOptionsGui gui = (HostOptionsGui) tgui;
                        if(gui.getHostContainer().getHostOptions().size() >= event.getSlot()){
                            HostOption hostOption = gui.getHostContainer().getHostOptions().get(event.getSlot());
                            if(hostOption.isModifiable() || event.getWhoClicked().hasPermission("uhc.host.bypass")){
                                t = hostOption.getModifierGUI();
                            }

                        }
                    }else {
                        if(tgui.getType().equals(TGUI.Type.MODIFIERS)){
                            ModifierGUI modifierGUI = (ModifierGUI) tgui;
                            ItemStack itemStack = event.getCurrentItem();
                            ItemMeta itemMeta = event.getCurrentItem().getItemMeta();
                            if(modifierGUI.getHostOption().getVarType().equals(VarType.INTEGER)){
                                if(itemStack.getType().equals(Material.WOOL) && itemStack.getData().getData() == (byte) 14){
                                    int v = (int) modifierGUI.getHostOption().getValue();
                                    if(v - modifierGUI.getHostOption().getRange() < modifierGUI.getHostOption().getMinAndMax()[0]){
                                        v = modifierGUI.getHostOption().getMinAndMax()[0];
                                    }else {
                                        if(v - modifierGUI.getHostOption().getRange() > modifierGUI.getHostOption().getMinAndMax()[1]){
                                            v = modifierGUI.getHostOption().getMinAndMax()[1];
                                        }else {
                                            v = v-modifierGUI.getHostOption().getRange();
                                        }
                                    }
                                    itemMeta.setLore(Arrays.asList(""+v));
                                    modifierGUI.getHostOption().setValue(v);
                                }
                                if(itemStack.getType().equals(Material.WOOL) && itemStack.getData().getData() == (byte) 13){
                                    int v = (int) modifierGUI.getHostOption().getValue();
                                    if(v + modifierGUI.getHostOption().getRange() < modifierGUI.getHostOption().getMinAndMax()[0]){
                                        v = modifierGUI.getHostOption().getMinAndMax()[0];
                                    }else {
                                        if(v + modifierGUI.getHostOption().getRange() > modifierGUI.getHostOption().getMinAndMax()[1]){
                                            v = modifierGUI.getHostOption().getMinAndMax()[1];
                                        }else {
                                            System.out.println(v+"supreme");
                                            v = v+modifierGUI.getHostOption().getRange();
                                        }
                                    }
                                    itemMeta.setLore(Arrays.asList(""+v));
                                    modifierGUI.getHostOption().setValue(v);
                                }
                            }
                            if(modifierGUI.getHostOption().getVarType().equals(VarType.BOOLEAN)){
                                if(itemStack.getType().equals(Material.WOOL) && itemStack.getData().getData() == (byte) 14){
                                    boolean b = (boolean) modifierGUI.getHostOption().getValue();
                                    itemMeta.setLore(Arrays.asList(""+false));

                                    modifierGUI.getHostOption().setValue(false);
                                }
                                if(itemStack.getType().equals(Material.WOOL) && itemStack.getData().getData() == (byte) 13){
                                    boolean b = (boolean) modifierGUI.getHostOption().getValue();
                                    itemMeta.setLore(Arrays.asList(""+true));
                                    modifierGUI.getHostOption().setValue(true);
                                }
                            }
                            itemStack.setItemMeta(itemMeta);
                        }

                    }

                        if(t != null){
                            if(!host.lastTGUI.containsKey(humanEntity.getUniqueId())){
                                host.lastTGUI.put(humanEntity.getUniqueId(),new ArrayList<>());
                            }
                            ArrayList<TGUI> l = host.lastTGUI.get(humanEntity.getUniqueId());
                            l.add(host.currentTGUI.get(humanEntity.getUniqueId()));
                            host.lastTGUI.put(humanEntity.getUniqueId(),l);


                            event.getWhoClicked().openInventory(t.getInv());
                            host.currentTGUI.put(humanEntity.getUniqueId(),t);
                        }

                    return;

            }
        }
            if(event.getClickedInventory().getType().equals(InventoryType.CHEST)){

        if(host.getHostContainers().containsKey(event.getSlot())){
            TGUI tgui = host.getHostContainers().get(event.getSlot()).getHostOptionsGui();
            event.setCancelled(true);
            if(host.currentTGUI.containsKey(humanEntity.getUniqueId())){
                if(!host.lastTGUI.containsKey(humanEntity.getUniqueId())){
                    host.lastTGUI.put(humanEntity.getUniqueId(),new ArrayList<>());
                }
                ArrayList<TGUI> l = host.lastTGUI.get(humanEntity.getUniqueId());
                l.add(host.currentTGUI.get(humanEntity.getUniqueId()));
                host.lastTGUI.put(humanEntity.getUniqueId(),l);
            }

            event.getWhoClicked().openInventory(tgui.getInv());
            host.currentTGUI.put(humanEntity.getUniqueId(),tgui);
        }
    }
    }
}
