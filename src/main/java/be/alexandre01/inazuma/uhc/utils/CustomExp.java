package be.alexandre01.inazuma.uhc.utils;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CustomExp extends EntityExperienceOrb {
	private InazumaUHC i;
	public CustomExp(InazumaUHC i, World world) {
		super(world);
		this.i = i;
	}

	public static CustomExp spawn(InazumaUHC i, Location loc) {
		World w = ((CraftWorld) loc.getWorld()).getHandle();
		CustomExp f = new CustomExp(i, w);
		f.setPosition(loc.getX(), loc.getY(), loc.getZ());
		w.addEntity(f, CreatureSpawnEvent.SpawnReason.CUSTOM);
		return f;
	}

	public static void registerEntity() {
		try {
			Method a = net.minecraft.server.v1_8_R3.EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
			a.setAccessible(true);
			a.invoke(a, CustomExp.class, "CustomExp", 300);
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

	@Override
	public void t_() {
		EntityHuman prevTarget = (EntityHuman) getPrivateField("targetPlayer", EntityExperienceOrb.class, this);
		EntityHuman targetPlayer = (EntityHuman) getPrivateField("targetPlayer", EntityExperienceOrb.class, this);
		int targetTime = (int) getPrivateField("targetTime", EntityExperienceOrb.class, this);
		if (this.c > 0) {
			--this.c;
		}

		this.lastX = this.locX;
		this.lastY = this.locY;
		this.lastZ = this.locZ;
		this.motY -= 0.029999999329447746D;
		if (this.world.getType(new BlockPosition(this)).getBlock().getMaterial() == Material.LAVA) {
			this.motY = 0.20000000298023224D;
			this.motX = (double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
			this.motZ = (double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
			this.makeSound("random.fizz", 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
		}

		this.j(this.locX, (this.getBoundingBox().b + this.getBoundingBox().e) / 2.0D, this.locZ);
		double d0 = 8.0D;
		if (targetTime < this.a - 20 + this.getId() % 100) {
			if (targetPlayer == null || targetPlayer.h(this) > d0 * d0) {
				targetPlayer = this.world.findNearbyPlayer(this, d0);
				setPrivateField("targetPlayer", EntityExperienceOrb.class, this, targetPlayer);
			}


			targetTime = this.a;
			setPrivateField("targetTime", EntityExperienceOrb.class, this, targetTime);
		}

		if (targetPlayer != null && targetPlayer.isSpectator()) {
			targetPlayer = null;
			setPrivateField("targetPlayer", EntityExperienceOrb.class, this, null);
		} else if (targetPlayer != null && i.spectatorManager.getPlayers().contains(Bukkit.getPlayer(targetPlayer.getUniqueID()))) {
			targetPlayer = null;
			setPrivateField("targetPlayer", EntityExperienceOrb.class, this, null);
		}

		if (targetPlayer != null) {
			boolean cancelled = false;
			if (targetPlayer != prevTarget) {
				EntityTargetLivingEntityEvent event = CraftEventFactory.callEntityTargetLivingEvent(this, targetPlayer, EntityTargetEvent.TargetReason.CLOSEST_PLAYER);
				EntityLiving target = event.getTarget() == null ? null : ((CraftLivingEntity) event.getTarget()).getHandle();
				targetPlayer = target instanceof EntityHuman ? (EntityHuman) target : null;
				cancelled = event.isCancelled();
			}

			if (!cancelled && targetPlayer != null) {
				double d1 = (targetPlayer.locX - this.locX) / d0;
				double d2 = (targetPlayer.locY + (double) targetPlayer.getHeadHeight() - this.locY) / d0;
				double d3 = (targetPlayer.locZ - this.locZ) / d0;
				double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
				double d5 = 1.0D - d4;
				if (d5 > 0.0D) {
					d5 *= d5;
					this.motX += d1 / d4 * d5 * 0.1D;
					this.motY += d2 / d4 * d5 * 0.1D;
					this.motZ += d3 / d4 * d5 * 0.1D;
				}
			}

			this.move(this.motX, this.motY, this.motZ);
			float f = 0.98F;
			if (this.onGround) {
				f = this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(this.getBoundingBox().b) - 1, MathHelper.floor(this.locZ))).getBlock().frictionFactor * 0.98F;
			}

			this.motX *= (double) f;
			this.motY *= 0.9800000190734863D;
			this.motZ *= (double) f;
			if (this.onGround) {
				this.motY *= -0.8999999761581421D;
			}

			++this.a;
			++this.b;
		}
		if (this.b >= 6000) {
			this.die();
		}
	}

	@Override
	public void d(EntityHuman entityhuman) {
		if (i.spectatorManager.getPlayers().contains(Bukkit.getPlayer(entityhuman.getUniqueID()))) {
			setPrivateField("targetPlayer", EntityExperienceOrb.class, this, null);
			return;
		}
		if (!this.world.isClientSide && this.c == 0 && entityhuman.bp == 0) {
			entityhuman.bp = 2;
			this.world.makeSound(entityhuman, "random.orb", 0.1F, 0.5F * ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.8F));
			entityhuman.receive(this, 1);
			entityhuman.giveExp(CraftEventFactory.callPlayerExpChangeEvent(entityhuman, this.value).getAmount());
			this.die();
		}
	}
}
