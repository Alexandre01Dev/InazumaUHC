package be.alexandre01.inazuma.uhc.roles;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.roles.commands.CommandRole;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Role {
    private String name;
    private ArrayList<Class<?>> roleToSpoil = new ArrayList<>();
    private ArrayList<Player> players;
    private load load;
    private command command;

    private ArrayList<String> description;
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
        player.sendMessage("");
        if(description.isEmpty()){
            player.sendMessage("§7Le role n'a pas de description par défaut.");
            return;
        }
        for(String d : description){
            player.sendMessage(d);
        }
    }
    public void spoilRole(){
        RoleManager roleManager = InazumaUHC.get.rm;
        for(Player player : getPlayers()){
            if(getRoleCategory() != null){
                player.sendMessage(Preset.instance.p.prefixName()+ "§8.Voici Votre rôle:"+ getRoleCategory().getPrefixColor()+getName());
                sendDescription(player);
                continue;
            }
            player.sendMessage(Preset.instance.p.prefixName()+" §8.Voici Votre rôle:§a"+getName());
            sendDescription(player);
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

    public void giveItem(){
        for(Player player : players){
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
        public void a();
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

    public ArrayList<String> getDescription() {
        return description;
    }

    public void setDescription(int line,String description) {
       this.description.add(line,description);
    }
}
