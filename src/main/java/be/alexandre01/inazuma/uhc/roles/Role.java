package be.alexandre01.inazuma.uhc.roles;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.roles.commands.CommandRole;

import be.alexandre01.inazuma.uhc.utils.CustomComponentBuilder;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Role {
    private String name;
    public boolean isListenerDeployed = false;
    public static boolean isDistributed = false;
    private ArrayList<Class<?>> roleToSpoil = new ArrayList<>();
    private ArrayList<Player> players;
    private load load;
    private command command;

    private ArrayList<BaseComponent[]> description;
    protected InazumaUHC inazumaUHC;
    public ArrayList<Listener> listeners = new ArrayList<>();
    private HashMap<String,CommandRole> commands;
  private final HashMap<ItemStack,RoleItem> roleItems = new HashMap<>();
    private static ArrayList<Role> rolesByInstance = new ArrayList<>();
    private RoleCategory roleCategory = null;
    public Role(String name){
        this.players = new ArrayList<>();
        this.commands = new HashMap<>();
        this.inazumaUHC = InazumaUHC.get;
        this.name = name;
        rolesByInstance.add(this);
        description = new ArrayList<>();
    }


    public void setRoleToSpoil(Class<?>... c){
        roleToSpoil.addAll(Arrays.asList(c));
    }
    private void sendDescription(Player player){

        if(description.isEmpty()){
            player.sendMessage("§7Le role n'a pas de description par défaut.");
            return;
        }
        for(net.md_5.bungee.api.chat.BaseComponent[] d : description){
            player.spigot().sendMessage(d);
        }
    }
    public void spoilRole(){
        RoleManager roleManager = InazumaUHC.get.rm;
        for(Player player : getPlayers()){
            if(getRoleCategory() != null){
                player.sendMessage("§8§m*------§7§m------§7§m-§b§m-----------§7§m-§7§m------§8§m------*");
                BaseComponent b = new TextComponent(Preset.instance.p.prefixName()+ "§7§lVoici votre rôle: "+ getRoleCategory().getPrefixColor()+"§l"+getName());


                player.spigot().sendMessage(b);
                sendDescription(player);
                player.sendMessage("§8§m*------§7§m------§7§m-§b§m-----------§7§m-§7§m------§8§m------*");
                continue;
            }
            player.sendMessage("§8§m*------§7§m------§7§m-§b§m-----------§7§m-§7§m------§8§m------*");
            BaseComponent b = new TextComponent(Preset.instance.p.prefixName()+" §7§lVoici votre rôle: §a§l"+getName()+"\n");

            player.spigot().sendMessage(b);
            sendDescription(player);
            player.sendMessage("§8§m*------§7§m------§7§m-§b§m-----------§7§m-§7§m------§8§m------*");
        }



            if(!roleToSpoil.isEmpty()){
                for(Class<?> r : roleToSpoil){
                    Role role = roleManager.getRole(r);
                    if(role == null){
                        System.out.println("role null");
                        return;
                    }
                    if(role.getPlayers().isEmpty()){
                        System.out.println("role empty");
                        return;
                    }
                    for(Player spoiledP : role.getPlayers()){
                        for(Player player : getPlayers()){
                        player.sendMessage(Preset.instance.p.prefixName()+"§a"+spoiledP.getName()+" est §a"+role.getName());
                    }
                }
            }


        }
    }
    public void addPlayer(Player player){
        System.out.println(getName()+" > "+player.getName());
        players.add(player);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void giveItem(Player player){
            if(!roleItems.isEmpty()){
                for(RoleItem roleItem : roleItems.values()){
                    if(hasAvaliableSlot(player)){
                        if(player.getInventory().getItem(roleItem.getSlot()) == null){
                            player.getInventory().setItem(roleItem.getSlot(),roleItem.getItemStack());
                            continue;
                        }
                        ItemStack itemStack = player.getInventory().getItem(roleItem.getSlot());
                        player.getInventory().setItem(roleItem.getSlot(),roleItem.getItemStack());
                        player.getInventory().addItem(itemStack);
                        continue;
                    }
                    ItemStack itemStack = player.getInventory().getItem(roleItem.getSlot());
                    player.getInventory().setItem(roleItem.getSlot(),roleItem.getItemStack());
                    player.getWorld().dropItemNaturally(player.getLocation(),itemStack,player);
                    player.sendMessage("§cInventaire est plein lors de la distribution d'un item spécial.");
                    player.sendMessage("§cUn de vos items vient d'être jeter au sol.");
                }
                player.updateInventory();
        }
    }

    public void onLoad(load load){
        this.load = load;
    }
    public void setRoleCategory(Class<?> roleCategory){
        this.roleCategory = InazumaUHC.get.rm.getRoleCategory(roleCategory);
        this.roleCategory.addRole(this);
    }
    public void addListener(Listener listener){
        listeners.add(listener);
    }

    public void deployListeners(){
        for(Listener listener : listeners){
            InazumaUHC.get.lm.addListener(listener);
        }
        isListenerDeployed = true;
    }
    public String getName() {
        return name;
    }

    public Role.load getLoad() {
        return load;
    }

    public RoleCategory getRoleCategory() {
        return roleCategory;
    }

    public interface load{
        public void a(Player player);
    }

    public interface command{
        public void a(String[] args, Player player);
    }
    public HashMap<ItemStack, RoleItem> getRoleItems() {
        return roleItems;
    }

    public void addRoleItem(RoleItem roleItem) {
        roleItems.put(roleItem.getItemStack(),roleItem);
        roleItem.setLinkedRole(this);
    }

    public void addCommand(String name,command command){
        if(commands.containsKey(name)){
            commands.get(name).addRole(this);
        }
        CommandRole commandRole = new CommandRole(name,command,this);
        commands.put(name,commandRole);
    }

    public void loadCommands(){
        for(String name : commands.keySet()){
            CommandRole commandRole = commands.get(name);
            InazumaUHC.get.registerCommand(name, commandRole);
        }
    }
    public static ArrayList<Role> getRoles(){
        return rolesByInstance;
    }


    public void addHearth(Player player,int i){
        player.setMaxHealth(player.getMaxHealth()+(i*2));
        player.setHealth(player.getHealth()+(i*2));
    }

    public void remHearth(Player player,int i){
        player.setHealth(player.getHealth()-(i*2));
        player.setMaxHealth(player.getMaxHealth()-(i*2));
    }
    private boolean hasAvaliableSlot(Player player){
        Inventory inv = player.getInventory();
        for (ItemStack item: inv.getContents()) {
            if(item == null) {
                return true;
            }
        }
        return false;
    }

    public HashMap<String, CommandRole> getCommands() {
        return commands;
    }

    public void addDescription(String s){
        CustomComponentBuilder c = new CustomComponentBuilder("");
        c.append(TextComponent.fromLegacyText(s));
        description.add(c.create());
    }

    public void addDescription(BaseComponent... baseComponents){
        description.add(baseComponents);
    }

    public void addDescription(CustomComponentBuilder componentBuilder){

        description.add(componentBuilder.create());
    }
    public ArrayList<BaseComponent[]> getDescription() {
        return description;
    }

    public void setDescription(int line, BaseComponent[] description) {
       this.description.add(line,description);
    }
}
