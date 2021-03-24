package be.alexandre01.inazuma.uhc.utils;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Golem;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.entity.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class CustomSkeleton extends EntitySkeleton {
	private InazumaUHC i;



	private PathfinderGoalArrowAttack a;
	public CustomSkeleton(World world) {
		super(world);
		int t;
		try{
			t = Integer.parseInt(world.getGameRules().get("randomTickSpeed"));
		}catch(Exception e){
			t = 1;
		}
		a =  new PathfinderGoalArrowAttack(this, 1.0D, 20*(t/4), 60, 15.0F);
		List goalB = (List)getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
		List goalC = (List)getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
		List targetB = (List)getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
		List targetC = (List)getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();

		this.goalSelector.a(1, new PathfinderGoalFloat(this));
		this.goalSelector.a(2, new PathfinderGoalRestrictSun(this));
		this.goalSelector.a(3, new PathfinderGoalFleeSun(this, 1.0D));
		this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0D));
		this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
		this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, HumanEntity.class, true));
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityChicken.class, true));
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityIronGolem.class, true));
		setPrivateField("a",EntitySkeleton.class, this,a);
	}

	public static CustomSkeleton spawn( Location loc) {
		World w = ((CraftWorld) loc.getWorld()).getHandle();
		CustomSkeleton f = new CustomSkeleton(w);
		f.setPosition(loc.getX(), loc.getY(), loc.getZ());
		w.addEntity(f, CreatureSpawnEvent.SpawnReason.CUSTOM);
		f.setEquipment(0,new ItemStack(Items.BOW));
		//f.setPrivateField("a",EntitySkeleton.class, f,);
		return f;
	}

	public static void registerEntity() {
		try {
			List<Map<?, ?>> dataMap = new ArrayList<Map<?, ?>>();
			for (Field f : EntityTypes.class.getDeclaredFields()){
				if (f.getType().getSimpleName().equals(Map.class.getSimpleName())){
					f.setAccessible(true);
					dataMap.add((Map<?, ?>) f.get(null));
				}
			}

			if (dataMap.get(2).containsKey(51)){
				dataMap.get(0).remove("CustomSkeleton");
				dataMap.get(2).remove(51);
			}
			Method a = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
			a.setAccessible(true);
			a.invoke(null, CustomSkeleton.class, "CustomSkeleton", 51);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Object getPrivateField(String fieldName, Class clazz, Object a) {
		Object o = null;
		Field field;
		try {
			field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			o = field.get(a);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return o;
	}

	private void setPrivateField(String fieldName, Class clazz, Object a, Object e) {
		Field field;
		try {
			field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(a, e);
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			ex.printStackTrace();
		}
	}

}

