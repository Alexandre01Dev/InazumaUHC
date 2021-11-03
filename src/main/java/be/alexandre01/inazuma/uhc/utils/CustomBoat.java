package be.alexandre01.inazuma.uhc.utils;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.vehicle.*;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CustomBoat extends EntityBoat {
		private InazumaUHC i;
		public CustomBoat(World world) {
			super(world);
			this.i = i;

		}

		public static CustomBoat spawn( Location loc) {
			World w = ((CraftWorld) loc.getWorld()).getHandle();
			CustomBoat f = new CustomBoat(w);
			f.setPosition(loc.getX(), loc.getY(), loc.getZ());
			w.addEntity(f, CreatureSpawnEvent.SpawnReason.CUSTOM);
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

				if (dataMap.get(2).containsKey(41)){
					dataMap.get(0).remove("CustomBoat");
					dataMap.get(2).remove(41);
				}
				Method a = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
				a.setAccessible(true);
				a.invoke(null, CustomBoat.class, "CustomBoat", 41);
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
		boolean a = (boolean) getPrivateField("a",  EntityBoat.class,this);
		double b = (double) getPrivateField("b",EntityBoat.class,this);
		int c = (int) getPrivateField("c",EntityBoat.class,this);
		double d = (double) getPrivateField("d",EntityBoat.class,this);
		double e = (double) getPrivateField("e",EntityBoat.class,this);
		double f = (double) getPrivateField("f",EntityBoat.class,this);
		double g = (double) getPrivateField("g",EntityBoat.class,this);
		double h = (double) getPrivateField("h",EntityBoat.class,this);

		System.out.println(h);
		double var1 = this.locX;
		double var3 = this.locY;
		double var5 = this.locZ;
		float var7 = this.yaw;
		float var8 = this.pitch;


		if (this.l() > 0) {
			this.a(this.l() - 1);
		}

		if (this.j() > 0.0F) {
			this.setDamage(this.j() - 1.0F);
		}

		this.lastX = this.locX;
		this.lastY = this.locY;
		this.lastZ = this.locZ;
		byte var9 = 5;
		double var10 = 0.0D;

		for(int var12 = 0; var12 < var9; ++var12) {
			double var13 = this.getBoundingBox().b + (this.getBoundingBox().e - this.getBoundingBox().b) * (double)(var12 + 0) / (double)var9 - 0.125D;
			double var15 = this.getBoundingBox().b + (this.getBoundingBox().e - this.getBoundingBox().b) * (double)(var12 + 1) / (double)var9 - 0.125D;
			AxisAlignedBB var17 = new AxisAlignedBB(this.getBoundingBox().a, var13, this.getBoundingBox().c, this.getBoundingBox().d, var15, this.getBoundingBox().f);
			if (this.world.b(var17, Material.WATER)) {
				var10 += 1.0D / (double)var9;
			}
		}

		double var34 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
		double var14;
		double var16;
		int var18;
		double var19;
		double var21;
		if (var34 > 0.2975D) {
			var14 = Math.cos((double)this.yaw * 3.141592653589793D / 180.0D);
			var16 = Math.sin((double)this.yaw * 3.141592653589793D / 180.0D);

			for(var18 = 0; (double)var18 < 1.0D + var34 * 60.0D; ++var18) {
				var19 = (double)(this.random.nextFloat() * 2.0F - 1.0F);
				var21 = (double)(this.random.nextInt(2) * 2 - 1) * 0.7D;
				double var23;
				double var25;
				if (this.random.nextBoolean()) {
					var23 = this.locX - var14 * var19 * 0.8D + var16 * var21;
					var25 = this.locZ - var16 * var19 * 0.8D - var14 * var21;
					this.world.addParticle(EnumParticle.WATER_SPLASH, var23, this.locY - 0.125D, var25, this.motX, this.motY, this.motZ, new int[0]);
				} else {
					var23 = this.locX + var14 + var16 * var19 * 0.7D;
					var25 = this.locZ + var16 - var14 * var19 * 0.7D;
					this.world.addParticle(EnumParticle.WATER_SPLASH, var23, this.locY - 0.125D, var25, this.motX, this.motY, this.motZ, new int[0]);
				}
			}
		}

		if (this.world.isClientSide && a) {
			if (c > 0) {
				var14 = this.locX + (d - this.locX) / (double)c;
				var16 = this.locY + (e - this.locY) / (double)c;
				var19 = this.locZ + (f - this.locZ) / (double)c;
				var21 = MathHelper.g(g - (double)this.yaw);
				this.yaw = (float)((double)this.yaw + var21 / (double)c);
				this.pitch = (float)((double)this.pitch + (h - (double)this.pitch) / (double)c);
				--c;
				setPrivateField("c",EntityBoat.class,this,c);
				this.setPosition(var14, var16, var19);
				this.setYawPitch(this.yaw, this.pitch);
			} else {
				var14 = this.locX + this.motX;
				var16 = this.locY + this.motY;
				var19 = this.locZ + this.motZ;
				this.setPosition(var14, var16, var19);
				if (this.onGround) {
					this.motX *= 0.5D;
					this.motY *= 0.5D;
					this.motZ *= 0.5D;
				}

				this.motX *= 0.9900000095367432D;
				this.motY *= 0.949999988079071D;
				this.motZ *= 0.9900000095367432D;
			}
		} else {
			if (var10 < 1.0D) {
				var14 = var10 * 2.0D - 1.0D;
				this.motY += 0.03999999910593033D * var14;
			} else {
				if (this.motY < 0.0D) {
					this.motY /= 2.0D;
				}

				this.motY += 0.007000000216066837D;
			}

			if (this.passenger instanceof EntityLiving) {
				EntityLiving var35 = (EntityLiving)this.passenger;
				float var24 = this.passenger.yaw + -var35.aZ * 90.0F;
				this.motX += -Math.sin((double)(var24 * 3.1415927F / 180.0F)) * b * (double)var35.ba * 0.05000000074505806D;
				this.motZ += Math.cos((double)(var24 * 3.1415927F / 180.0F)) * b * (double)var35.ba * 0.05000000074505806D;
			} else if (this.unoccupiedDeceleration >= 0.0D) {
				this.motX *= this.unoccupiedDeceleration;
				this.motZ *= this.unoccupiedDeceleration;
				System.out.println("unoccupiedDeceleration");
				if (this.motX <= 1.0E-5D) {
					this.motX = 0.0D;
				}

				if (this.motZ <= 1.0E-5D) {
					this.motZ = 0.0D;
				}
			}

			var14 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
			if (var14 > 0.35D) {
				var16 = 0.35D / var14;
				this.motX *= var16;
				this.motZ *= var16;
				var14 = 0.35D;
			}

			if (var14 > var34 && b < 0.35D) {
				b += (0.35D - b) / 35.0D;
				setPrivateField("b",EntityBoat.class,this,b);
				if (b > 0.35D) {
					b = 0.35D;
					setPrivateField("b",EntityBoat.class,this,b);
				}
			} else {
				b -= (b - 0.07D) / 35.0D;
				setPrivateField("b",EntityBoat.class,this,b);
				if (b < 0.07D) {
					b = 0.07D;
					setPrivateField("b",EntityBoat.class,this,b);
				}
			}

			for(int var36 = 0; var36 < 4; ++var36) {
				int var37 = MathHelper.floor(this.locX + ((double)(var36 % 2) - 0.5D) * 0.8D);
				var18 = MathHelper.floor(this.locZ + ((double)(var36 / 2) - 0.5D) * 0.8D);

				for(int var40 = 0; var40 < 2; ++var40) {
					int var26 = MathHelper.floor(this.locY) + var40;
					BlockPosition var27 = new BlockPosition(var37, var26, var18);
					Block var28 = this.world.getType(var27).getBlock();

					if (var28 == Blocks.SNOW_LAYER) {
						if (!CraftEventFactory.callEntityChangeBlockEvent(this, var37, var26, var18, Blocks.AIR, 0).isCancelled()) {
						//	this.world.setAir(var27);
						//	this.positionChanged = false;
						}
					} else if (var28 == Blocks.WATERLILY && !CraftEventFactory.callEntityChangeBlockEvent(this, var37, var26, var18, Blocks.AIR, 0).isCancelled()) {
						//this.world.setAir(var27, true);
						//this.positionChanged = false;
					}
				}
			}

			if (this.onGround && !this.landBoats) {
				this.motX *= 0.5D;
				this.motY *= 0.5D;
				this.motZ *= 0.5D;
			}

			this.move(this.motX, this.motY, this.motZ);
			if (this.positionChanged && var34 > 0.2975D) {
				if (!this.world.isClientSide && !this.dead) {
					Vehicle var38 = (Vehicle)this.getBukkitEntity();
					VehicleDestroyEvent var41 = new VehicleDestroyEvent(var38, (org.bukkit.entity.Entity)null);
					this.world.getServer().getPluginManager().callEvent(var41);
					if (!var41.isCancelled()) {
						//this.die();
						if (this.world.getGameRules().getBoolean("doEntityDrops")) {
							//this.breakNaturally();
						}
					}
				}
			} else {
				this.motX *= 0.9900000095367432D;
				this.motY *= 0.949999988079071D;
				this.motZ *= 0.9900000095367432D;
			}

			this.pitch = 0.0F;
			var16 = (double)this.yaw;
			var19 = this.lastX - this.locX;
			var21 = this.lastZ - this.locZ;
			if (var19 * var19 + var21 * var21 > 0.001D) {
				var16 = (double)((float)(MathHelper.b(var21, var19) * 180.0D / 3.141592653589793D));
			}

			double var39 = MathHelper.g(var16 - (double)this.yaw);
			if (var39 > 20.0D) {
				var39 = 20.0D;
			}

			if (var39 < -20.0D) {
				var39 = -20.0D;
			}

			this.yaw = (float)((double)this.yaw + var39);
			this.setYawPitch(this.yaw, this.pitch);
			CraftServer var42 = this.world.getServer();
			CraftWorld var43 = this.world.getWorld();
			Location var44 = new Location(var43, var1, var3, var5, var7, var8);
			Location var29 = new Location(var43, this.locX, this.locY, this.locZ, this.yaw, this.pitch);
			Vehicle var30 = (Vehicle)this.getBukkitEntity();
			var42.getPluginManager().callEvent(new VehicleUpdateEvent(var30));
			if (!var44.equals(var29)) {
				VehicleMoveEvent var31 = new VehicleMoveEvent(var30, var44, var29);
				var42.getPluginManager().callEvent(var31);
			}

			if (!this.world.isClientSide) {
				List var45 = this.world.getEntities(this, this.getBoundingBox().grow(0.20000000298023224D, 0.0D, 0.20000000298023224D));
				if (var45 != null && !var45.isEmpty()) {
					for(int var32 = 0; var32 < var45.size(); ++var32) {
						Entity var33 = (Entity)var45.get(var32);
						if (var33 != this.passenger && var33.ae() && var33 instanceof EntityBoat) {
							//var33.collide(this);
						}
					}
				}

				if (this.passenger != null && this.passenger.dead) {
					this.passenger.vehicle = null;
					this.passenger = null;
				}
			}
		}

		//super.t_();
	}

	@Override
	protected void a(double var1, boolean var3, Block var4, BlockPosition var5) {
		if (var3) {
			if (this.fallDistance > 3.0F) {
				this.e(this.fallDistance, 1.0F);
				if (!this.world.isClientSide && !this.dead) {
					Vehicle var6 = (Vehicle)this.getBukkitEntity();
					VehicleDestroyEvent var7 = new VehicleDestroyEvent(var6, (org.bukkit.entity.Entity)null);
					this.world.getServer().getPluginManager().callEvent(var7);
					if (!var7.isCancelled()) {
						//this.die();
						if (this.world.getGameRules().getBoolean("doEntityDrops")) {
							//this.breakNaturally();
						}
					}
				}

				this.fallDistance = 0.0F;
			}
		} else if (this.world.getType((new BlockPosition(this)).down()).getBlock().getMaterial() != Material.WATER && var1 < 0.0D) {
			//this.fallDistance = (float)((double)this.fallDistance - var1);
		}
	}





	@Override
	protected void checkBlockCollisions() {


	}
	private void recalcPosition() {
		this.locX = (this.getBoundingBox().a + this.getBoundingBox().d) / 2.0D;
		this.locY = this.getBoundingBox().b;
		this.locZ = (this.getBoundingBox().c + this.getBoundingBox().f) / 2.0D;
	}

}
