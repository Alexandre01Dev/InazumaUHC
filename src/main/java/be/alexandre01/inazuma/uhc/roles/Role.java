package be.alexandre01.inazuma.uhc.roles;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.roles.commands.CommandRole;

import be.alexandre01.inazuma.uhc.utils.CustomComponentBuilder;
import lombok.Getter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import net.minecraft.server.v1_8_R3.CraftingManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class Role {
    private final IPreset preset;
    private final String name;
    public boolean isListenerDeployed = false;
    public boolean isCommandDeployed = false;
    public static boolean isDistributed = false;
    private final ArrayList<Class<?>> roleToSpoil = new ArrayList<>();

    @Getter private final HashMap<Recipe,RoleCraft> roleCrafts = new HashMap<>();
    @Getter private final ArrayList<BukkitTask> bukkitTasks = new ArrayList<>();
    @Getter private final HashMap<UUID,BukkitTask> bukkitTaskByPlayers = new HashMap<>();
    private final ArrayList<UUID> players;
    private final ArrayList<Player> onlinePlayers;
    @Getter private final ArrayList<UUID> eliminatedPlayers;
    @Getter private final ArrayList<UUID> initialPlayers;
    private load load;
    private command command;

    private final ArrayList<BaseComponent[]> description;
    protected InazumaUHC inazumaUHC;
    public ArrayList<Listener> listeners = new ArrayList<>();
    private static final HashMap<String,CommandRole> commands = new HashMap<>();
  private final HashMap<String,RoleItem> roleItems = new HashMap<>();
    private static final ArrayList<Role> rolesByInstance = new ArrayList<>();
    private static final ArrayList<Class<?>> rolesClass = new ArrayList<>();
    private RoleCategory roleCategory = null;
    public Role(String name,IPreset iPreset){
        this.players = new ArrayList<>();
        this.initialPlayers = new ArrayList<>();
        this.eliminatedPlayers = new ArrayList<>();
        this.onlinePlayers = new ArrayList<>();
        this.inazumaUHC = InazumaUHC.get;
        this.name = name;
        rolesByInstance.add(this);
        description = new ArrayList<>();
        this.preset = iPreset;
    }
    public static void clear(){
        commands.clear();
        isDistributed = false;
        rolesByInstance.clear();
        rolesClass.clear();
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
    public void spoilRole(Player player){
        RoleManager roleManager = InazumaUHC.get.rm;

            if(getRoleCategory() != null){
                player.sendMessage("§8§m*------§7§m------§7§m-§b§m-----------§7§m-§7§m------§8§m------*");
                BaseComponent b = new TextComponent(Preset.instance.p.prefixName()+ "§7§lVoici votre rôle: "+ getRoleCategory().getPrefixColor()+"§l"+getName());


                player.spigot().sendMessage(b);
                sendDescription(player);
                player.sendMessage("§8§m*------§7§m------§7§m-§b§m-----------§7§m-§7§m------§8§m------*");

            }else {
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
                        player.sendMessage(Preset.instance.p.prefixName()+"§7L'identité de §c§l"+spoiledP.getName() + " §7est :" + role.getRoleCategory().getPrefixColor()+"§l"+getName());
                    }


            }


        }
    }
    public void addPlayer(Player player){
        System.out.println(getName()+" > "+player.getName());
        players.add(player.getUniqueId());
        onlinePlayers.add(player);
        System.out.println("ADD "+player.getName());

        if(!initialPlayers.contains(player.getUniqueId())) {
            System.out.println("NOT CONTAINS !!! "+player.getName());
            initialPlayers.add(player.getUniqueId());
        }
        for (UUID uuid : initialPlayers){
            System.out.println(uuid);
        }

    }
    public void removePlayer(Player player){
        System.out.println(getName()+" > "+player.getName());
        players.remove(player.getUniqueId());
        onlinePlayers.remove(player);
    }
    public ArrayList<Player> getPlayers() {
        return onlinePlayers;
    }

    public void giveItem(Player player){
        Bukkit.getScheduler().scheduleSyncDelayedTask(inazumaUHC,() -> {
            boolean full = false;
            if(!roleItems.isEmpty()){
                for(RoleItem roleItem : roleItems.values()){
                    roleItem.getPlayersHaveItem().add(player);
                    if(!roleItem.getItemStack().hasItemMeta()){
                        if(!roleItem.getItemStack().getItemMeta().hasDisplayName()){
                            ItemMeta itemMeta = roleItem.getItemStack().getItemMeta();
                            itemMeta.setDisplayName(roleItem.getItemStack().getType().toString());
                            roleItem.getItemStack().setItemMeta(itemMeta);
                        }
                    }
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
                    player.getWorld().dropItemNaturally(player.getLocation(),itemStack);
                    full = true;

                }
                if(full){
                    player.sendMessage("§cInventaire est plein lors de la distribution d'un item spécial.");
                    player.sendMessage("§4ATTENTION §cCertain de vos items ont été jeter au sol.");
                }
                player.updateInventory();
            }
        });
    }
    public void giveItem(Player player,RoleItem roleItem){
        Bukkit.getScheduler().scheduleSyncDelayedTask(inazumaUHC,() -> {
            boolean full = false;
            if(!roleItems.isEmpty()){
                if(!roleItem.getItemStack().hasItemMeta()){
                    if(!roleItem.getItemStack().getItemMeta().hasDisplayName()){
                        ItemMeta itemMeta = roleItem.getItemStack().getItemMeta();
                        itemMeta.setDisplayName(roleItem.getItemStack().getType().toString());
                        roleItem.getItemStack().setItemMeta(itemMeta);
                    }
                }
                roleItem.getPlayersHaveItem().add(player);
                    if(hasAvaliableSlot(player)){
                        if(player.getInventory().getItem(roleItem.getSlot()) == null){
                            player.getInventory().setItem(roleItem.getSlot(),roleItem.getItemStack());
                            return;
                        }
                        ItemStack itemStack = player.getInventory().getItem(roleItem.getSlot());
                        player.getInventory().setItem(roleItem.getSlot(),roleItem.getItemStack());
                        player.getInventory().addItem(itemStack);
                        return;
                    }
                    ItemStack itemStack = player.getInventory().getItem(roleItem.getSlot());
                    player.getInventory().setItem(roleItem.getSlot(),roleItem.getItemStack());
                    player.getWorld().dropItemNaturally(player.getLocation(),itemStack);

                    player.sendMessage("§cInventaire est plein lors de la distribution d'un item spécial.");
                    player.sendMessage("§4ATTENTION §cCertain de vos items ont été jeter au sol.");

                player.updateInventory();
            }
        });
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
    public HashMap<String, RoleItem> getRoleItems() {
        return roleItems;
    }

    public void addRoleItem(RoleItem roleItem) {
        if(!roleItem.getRolesToAccess().contains(this.getClass()))
            roleItem.getRolesToAccess().add(this.getClass());

        roleItems.put(roleItem.getItemStack().getItemMeta().getDisplayName(),roleItem);
    }
    public void runBukkitRunnable(BukkitTask bukkitTask){
        bukkitTasks.add(bukkitTask);
    }
    public void addCraft(RoleCraft roleCraft){
        roleCrafts.put(roleCraft.getRecipe(),roleCraft);
    }
    public void addCommand(String name,command command){
        if(commands.containsKey(name)){
            CommandRole commandRole =  commands.get(name);
            commandRole.addRole(this);
            commandRole.addCommand(this,command);

            return;
        }
        CommandRole commandRole = new CommandRole(name,command,this);
        commands.put(name,commandRole);
    }

    public void loadCommands(){
        for(String name : commands.keySet()){
            CommandRole commandRole = commands.get(name);
            InazumaUHC.get.registerCommand(name, commandRole);
        }
        isCommandDeployed = true;

    }
    public static ArrayList<Role> getRoles(){
        return rolesByInstance;
    }
    public static ArrayList<Class<?>> getClassRoles(){
        return rolesClass;
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
    public static void addRoles(Class<?>... roles){
        rolesClass.addAll(Arrays.asList(roles));
    }

    public static void initializeRoles(){
        for(Class<?> c : rolesClass){
            try {
                Role role = (Role) c.getDeclaredConstructor(IPreset.class).newInstance(Preset.instance.p);

                if(InazumaUHC.get.rm != null){
                    InazumaUHC.get.rm.getClasses().put(role.getClass(),role);
                    System.out.println("PUT CLASSES");
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
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

    public boolean isValidItem(ItemStack itemStack){
        if(itemStack == null)
            return false;
        if(itemStack.getItemMeta() == null)
            return false;
        if(itemStack.getItemMeta().getDisplayName() == null)
            return false;

        return true;
    }

    protected IPreset getPreset(){
        return preset;
    }
}
