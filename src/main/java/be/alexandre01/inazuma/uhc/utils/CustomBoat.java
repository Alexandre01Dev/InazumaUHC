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
import spg.lgdev.config.iSpigotConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

public class CustomBoat extends EntityBoat {
		private InazumaUHC i;
		public CustomBoat(InazumaUHC i, World world) {
			super(world);
			this.i = i;

		}

		public static CustomBoat spawn(InazumaUHC i, Location loc) {
			World w = ((CraftWorld) loc.getWorld()).getHandle();
			CustomBoat f = new CustomBoat(i, w);
			f.setPosition(loc.getX(), loc.getY(), loc.getZ());
			w.addEntity(f, CreatureSpawnEvent.SpawnReason.CUSTOM);
			return f;
		}

		public static void registerEntity() {
			try {
				Method a = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
				a.setAccessible(true);
				a.invoke(null, CustomBoat.class, "CustomBoat",302);
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
		if (!iSpigotConfig.minimize_packets && var34 > 0.2975D) {
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
	public void move(double var1, double var3, double var5) {
		int h = (int) getPrivateField("h",Entity.class,this);
		if (this.loadChunks) {
			int var7 = (int)var1 >> 4;
			int var8 = (int)var5 >> 4;
			int var9 = (int)this.locX >> 4;
			int var10 = (int)this.locZ >> 4;
			if (var7 != var9 || var8 != var10) {
				this.loadChunks();
			}
		}

		if (this.noclip) {
			this.a(this.getBoundingBox().c(var1, var3, var5));
			this.recalcPosition();
		} else {
			try {
				this.checkBlockCollisions();
			} catch (Throwable var61) {
				CrashReport var63 = CrashReport.a(var61, "Checking entity block collision");
				CrashReportSystemDetails var64 = var63.a("Entity being checked for collision");
				this.appendEntityCrashDetails(var64);
				throw new ReportedException(var63);
			}

			if (var1 == 0.0D && var3 == 0.0D && var5 == 0.0D && this.vehicle == null && this.passenger == null) {
				return;
			}

			this.world.methodProfiler.a("move");
			double var62 = this.locX;
			double var65 = this.locY;
			double var11 = this.locZ;
			if (this.H) {
				this.H = false;
				var1 *= 0.25D;
				var3 *= 0.05000000074505806D;
				var5 *= 0.25D;
				this.motX = 0.0D;
				this.motY = 0.0D;
				this.motZ = 0.0D;
			}

			double var13 = var1;
			double var15 = var3;
			double var17 = var5;
			boolean var19 = this.onGround && this.isSneaking() && (Entity)this instanceof EntityHuman;
			List var20 = this.world.getCubes(this, this.getBoundingBox().a(var1, var3, var5));
			AxisAlignedBB var21 = this.getBoundingBox();

			AxisAlignedBB var22;
			for(Iterator var23 = var20.iterator(); var23.hasNext(); var3 = var22.b(this.getBoundingBox(), var3)) {
				var22 = (AxisAlignedBB)var23.next();
			}

			this.a(this.getBoundingBox().c(0.0D, var3, 0.0D));
			boolean var66 = this.onGround || var15 != var3 && var15 < 0.0D;

			AxisAlignedBB var24;
			Iterator var25;
			for(var25 = var20.iterator(); var25.hasNext(); var1 = var24.a(this.getBoundingBox(), var1)) {
				var24 = (AxisAlignedBB)var25.next();
			}

			this.a(this.getBoundingBox().c(var1, 0.0D, 0.0D));

			for(var25 = var20.iterator(); var25.hasNext(); var5 = var24.c(this.getBoundingBox(), var5)) {
				var24 = (AxisAlignedBB)var25.next();
			}

			this.a(this.getBoundingBox().c(0.0D, 0.0D, var5));
			if (this.S > 0.0F && var66 && (var13 != var1 || var17 != var5)) {
				double var26 = var1;
				double var28 = var3;
				double var30 = var5;
				AxisAlignedBB var32 = this.getBoundingBox();
				this.a(var21);
				var3 = (double)this.S;
				List var33 = this.world.getCubes(this, this.getBoundingBox().a(var13, var3, var17));
				AxisAlignedBB var34 = this.getBoundingBox();
				AxisAlignedBB var35 = var34.a(var13, 0.0D, var17);
				double var36 = var3;

				AxisAlignedBB var38;
				for(Iterator var39 = var33.iterator(); var39.hasNext(); var36 = var38.b(var35, var36)) {
					var38 = (AxisAlignedBB)var39.next();
				}

				var34 = var34.c(0.0D, var36, 0.0D);
				double var78 = var13;

				AxisAlignedBB var41;
				for(Iterator var42 = var33.iterator(); var42.hasNext(); var78 = var41.a(var34, var78)) {
					var41 = (AxisAlignedBB)var42.next();
				}

				var34 = var34.c(var78, 0.0D, 0.0D);
				double var79 = var17;

				AxisAlignedBB var44;
				for(Iterator var45 = var33.iterator(); var45.hasNext(); var79 = var44.c(var34, var79)) {
					var44 = (AxisAlignedBB)var45.next();
				}

				var34 = var34.c(0.0D, 0.0D, var79);
				AxisAlignedBB var80 = this.getBoundingBox();
				double var46 = var3;

				AxisAlignedBB var48;
				for(Iterator var49 = var33.iterator(); var49.hasNext(); var46 = var48.b(var80, var46)) {
					var48 = (AxisAlignedBB)var49.next();
				}

				var80 = var80.c(0.0D, var46, 0.0D);
				double var81 = var13;

				AxisAlignedBB var51;
				for(Iterator var52 = var33.iterator(); var52.hasNext(); var81 = var51.a(var80, var81)) {
					var51 = (AxisAlignedBB)var52.next();
				}

				var80 = var80.c(var81, 0.0D, 0.0D);
				double var82 = var17;

				AxisAlignedBB var54;
				for(Iterator var55 = var33.iterator(); var55.hasNext(); var82 = var54.c(var80, var82)) {
					var54 = (AxisAlignedBB)var55.next();
				}

				var80 = var80.c(0.0D, 0.0D, var82);
				double var83 = var78 * var78 + var79 * var79;
				double var57 = var81 * var81 + var82 * var82;
				if (var83 > var57) {
					var1 = var78;
					var5 = var79;
					var3 = -var36;
					this.a(var34);
				} else {
					var1 = var81;
					var5 = var82;
					var3 = -var46;
					this.a(var80);
				}

				AxisAlignedBB var59;
				for(Iterator var60 = var33.iterator(); var60.hasNext(); var3 = var59.b(this.getBoundingBox(), var3)) {
					var59 = (AxisAlignedBB)var60.next();
				}

				this.a(this.getBoundingBox().c(0.0D, var3, 0.0D));
				if (var26 * var26 + var30 * var30 >= var1 * var1 + var5 * var5) {
					var1 = var26;
					var3 = var28;
					var5 = var30;
					this.a(var32);
				}
			}

			this.world.methodProfiler.b();
			this.world.methodProfiler.a("rest");
			this.recalcPosition();
			this.positionChanged = var13 != var1 || var17 != var5;
			this.E = var15 != var3;
			this.onGround = this.E && var15 < 0.0D;
			this.F = this.positionChanged || this.E;
			int var67 = MathHelper.floor(this.locX);
			int var27 = MathHelper.floor(this.locY - 0.20000000298023224D);
			int var68 = MathHelper.floor(this.locZ);
			BlockPosition var29 = new BlockPosition(var67, var27, var68);
			net.minecraft.server.v1_8_R3.Block var69 = this.world.getType(var29).getBlock();
			if (var69.getMaterial() == Material.AIR) {
				net.minecraft.server.v1_8_R3.Block var31 = this.world.getType(var29.down()).getBlock();
				if (var31 instanceof BlockFence || var31 instanceof BlockCobbleWall || var31 instanceof BlockFenceGate) {
					var69 = var31;
					var29 = var29.down();
				}
			}

			this.a(var3, this.onGround, var69, var29);
			if (var13 != var1) {
				this.motX = 0.0D;
			}

			if (var17 != var5) {
				this.motZ = 0.0D;
			}

			if (var15 != var3) {
				var69.a(this.world, this);
			}

			if (this.positionChanged && this.getBukkitEntity() instanceof Vehicle) {
				/*Vehicle var70 = (Vehicle)this.getBukkitEntity();
				org.bukkit.block.Block var73 = this.world.getWorld().getBlockAt(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
				if (var13 > var1) {
					var73 = var73.getRelative(BlockFace.EAST);
				} else if (var13 < var1) {
					var73 = var73.getRelative(BlockFace.WEST);
				} else if (var17 > var5) {
					var73 = var73.getRelative(BlockFace.SOUTH);
				} else if (var17 < var5) {
					var73 = var73.getRelative(BlockFace.NORTH);
				}

				VehicleBlockCollisionEvent var74 = new VehicleBlockCollisionEvent(var70, var73);
				this.world.getServer().getPluginManager().callEvent(var74);*/
			}

			if (this.s_() && !var19 && this.vehicle == null) {
				double var71 = this.locX - var62;
				double var75 = this.locY - var65;
				double var77 = this.locZ - var11;
				if (var69 != Blocks.LADDER) {
					var75 = 0.0D;
				}

				if (var69 != null && this.onGround) {
				}

				this.M = (float)((double)this.M + (double)MathHelper.sqrt(var71 * var71 + var77 * var77) * 0.6D);
				this.N = (float)((double)this.N + (double)MathHelper.sqrt(var71 * var71 + var75 * var75 + var77 * var77) * 0.6D);
				if (this.N > (float)h && var69.getMaterial() != Material.AIR) {
					h = (int)this.N + 1;
					setPrivateField("h",Entity.class,this,h);
					if (this.V()) {
						float var37 = MathHelper.sqrt(this.motX * this.motX * 0.20000000298023224D + this.motY * this.motY + this.motZ * this.motZ * 0.20000000298023224D) * 0.35F;
						if (var37 > 1.0F) {
							var37 = 1.0F;
						}

						this.makeSound(this.P(), var37, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
					}

					this.a(var29, var69);
					var69.a(this.world, var29, this);
				}
			}

			boolean var72 = this.U();
			if (this.world.e(this.getBoundingBox().shrink(0.001D, 0.001D, 0.001D))) {
				this.burn(1.0F);
				if (!var72) {
					++this.fireTicks;
					if (this.fireTicks <= 0) {
						EntityCombustEvent var76 = new EntityCombustEvent(this.getBukkitEntity(), 8);
						this.world.getServer().getPluginManager().callEvent(var76);
						if (!var76.isCancelled()) {
							this.setOnFire(var76.getDuration());
						}
					} else {
						this.setOnFire(8);
					}
				}
			} else if (this.fireTicks <= 0) {
				this.fireTicks = -this.maxFireTicks;
			}

			if (var72 && this.fireTicks > 0) {
				this.makeSound("random.fizz", 0.7F, 1.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
				this.fireTicks = -this.maxFireTicks;
			}

			this.world.methodProfiler.b();
		}

	}
	private void recalcPosition() {
		this.locX = (this.getBoundingBox().a + this.getBoundingBox().d) / 2.0D;
		this.locY = this.getBoundingBox().b;
		this.locZ = (this.getBoundingBox().c + this.getBoundingBox().f) / 2.0D;
	}

}
