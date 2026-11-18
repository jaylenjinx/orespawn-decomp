package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.client.render.RenderInfo;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.ai.MyEntityAIFollowOwner;
import danger.orespawn.entity.ai.MyEntityAIWander;
import danger.orespawn.entity.projectile.BetterFireball;
import danger.orespawn.entity.projectile.IceBall;
import danger.orespawn.entity.projectile.WaterBall;

public class Dragon extends EntityTameable {

    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private double boatYawHead;
    private int updateit = 1;
    private int color = 1;
    private int playing = 0;
    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private int hurt_timer = 0;
    private int wing_sound = 0;
    private ChunkCoordinates currentFlightTarget = null;
    private boolean target_in_sight = false;
    private int owner_flying = 0;
    private int flyaway = 0;
    private int stuck_count = 0;
    private int lastX = 0;
    private int lastZ = 0;
    private int unstick_timer = 0;
    private int fireballticker = 0;
    private float moveSpeed = 0.32F;
    private float deltasmooth = 0.0F;
    private int dragontype = 0;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public Dragon(World world) {
        super(world);
        this.setSize(1.5F, 1.25F);
        this.getNavigator()
            .setAvoidsWater(true);
        this.experienceValue = 100;
        this.fireResistance = 1000;
        this.isImmuneToFire = true;
        this.setSitting(false);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new MyEntityAIFollowOwner(this, 1.1F, 12.0F, 2.0F));
        this.tasks.addTask(2, new EntityAITempt(this, 1.25, Items.beef, false));
        this.tasks.addTask(3, new MyEntityAIWander(this, 0.75F));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 9.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.tasks.addTask(6, new EntityAIMoveIndoors(this));
        if (OreSpawnMain.PlayNicely == 0) {
            this.targetTasks.addTask(
                1,
                new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, true, false, IMob.mobSelector));
        }

        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
        this.riddenByEntity = null;
        this.TargetSorter = new GenericTargetSorter(this);
        this.renderdata = new RenderInfo();
    }

    public Dragon(World world, double x, double y, double z) {
        this(world);
        this.setPosition(x, y + this.yOffset, z);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        this.getAttributeMap()
            .registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(35.0);
    }

    public boolean shouldRiderSit() {
        return true;
    }

    public int getTrackingRange() {
        return 128;
    }

    public int getUpdateFrequency() {
        return 10;
    }

    public boolean sendsVelocityUpdates() {
        return true;
    }

    protected void fall(float distance) {}

    protected void updateFallState(double distanceFallen, boolean onGround) {}

    protected boolean canTriggerWalking() {
        return true;
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
        this.dataWatcher.addObject(21, (byte) 0);
        this.dataWatcher.addObject(22, 0);
        this.dataWatcher.addObject(24, 1);
        this.setActivity(0);
        this.setAttacking(0);
        this.setTamed(false);
        if (this.renderdata == null) {
            this.renderdata = new RenderInfo();
        }

        this.renderdata.rf1 = 0.0F;
        this.renderdata.rf2 = 0.0F;
        this.renderdata.rf3 = 0.0F;
        this.renderdata.rf4 = 0.0F;
        this.renderdata.ri1 = 0;
        this.renderdata.ri2 = 0;
        this.renderdata.ri3 = 0;
        this.renderdata.ri4 = 0;
    }

    public int mygetMaxHealth() {
        return 200;
    }

    public int getDragonHealth() {
        return (int) this.getHealth();
    }

    public RenderInfo getRenderInfo() {
        return this.renderdata;
    }

    public void setRenderInfo(RenderInfo r) {
        this.renderdata.rf1 = r.rf1;
        this.renderdata.rf2 = r.rf2;
        this.renderdata.rf3 = r.rf3;
        this.renderdata.rf4 = r.rf4;
        this.renderdata.ri1 = r.ri1;
        this.renderdata.ri2 = r.ri2;
        this.renderdata.ri3 = r.ri3;
        this.renderdata.ri4 = r.ri4;
    }

    public int getTotalArmorValue() {
        return 14;
    }

    protected void jump() {
        super.jump();
        this.motionY += 0.25;
    }

    public boolean isAIEnabled() {
        return true;
    }

    private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {
        int found = 0;

        for (int i = -dy; i <= dy; i++) {
            for (int j = -dz; j <= dz; j++) {
                Block bid = this.worldObj.getBlock(x + dx, y + i, z + j);
                if (bid == Blocks.lava || bid == Blocks.flowing_lava) {
                    int d = dx * dx + j * j + i * i;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x + dx;
                        this.ty = y + i;
                        this.tz = z + j;
                        found++;
                    }
                }

                bid = this.worldObj.getBlock(x - dx, y + i, z + j);
                if (bid == Blocks.lava || bid == Blocks.flowing_lava) {
                    int d = dx * dx + j * j + i * i;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x - dx;
                        this.ty = y + i;
                        this.tz = z + j;
                        found++;
                    }
                }
            }
        }

        for (int i = -dx; i <= dx; i++) {
            for (int j = -dz; j <= dz; j++) {
                Block bidx = this.worldObj.getBlock(x + i, y + dy, z + j);
                if (bidx == Blocks.lava || bidx == Blocks.flowing_lava) {
                    int d = dy * dy + j * j + i * i;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x + i;
                        this.ty = y + dy;
                        this.tz = z + j;
                        found++;
                    }
                }

                bidx = this.worldObj.getBlock(x + i, y - dy, z + j);
                if (bidx == Blocks.lava || bidx == Blocks.flowing_lava) {
                    int d = dy * dy + j * j + i * i;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x + i;
                        this.ty = y - dy;
                        this.tz = z + j;
                        found++;
                    }
                }
            }
        }

        for (int i = -dx; i <= dx; i++) {
            for (int j = -dy; j <= dy; j++) {
                Block bidxx = this.worldObj.getBlock(x + i, y + j, z + dz);
                if (bidxx == Blocks.lava || bidxx == Blocks.flowing_lava) {
                    int d = dz * dz + j * j + i * i;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x + i;
                        this.ty = y + j;
                        this.tz = z + dz;
                        found++;
                    }
                }

                bidxx = this.worldObj.getBlock(x + i, y + j, z - dz);
                if (bidxx == Blocks.lava || bidxx == Blocks.flowing_lava) {
                    int d = dz * dz + j * j + i * i;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x + i;
                        this.ty = y + j;
                        this.tz = z - dz;
                        found++;
                    }
                }
            }
        }

        return found != 0;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public String getLivingSound() {
        if (this.isSitting()) {
            return null;
        } else {
            return this.getAttacking() == 1 && this.riddenByEntity == null ? "orespawn:roar" : null;
        }
    }

    protected String getHurtSound() {
        return "orespawn:alo_hurt";
    }

    protected String getDeathSound() {
        return "orespawn:alo_death";
    }

    protected float getSoundVolume() {
        return 0.6F;
    }

    public float getSoundPitch() {
        return 0.75F;
    }

    public boolean canBePushed() {
        return false;
    }

    public double getMountedYOffset() {
        return 1.3;
    }

    protected Item getDropItem() {
        return Items.beef;
    }

    private ItemStack dropItemRand(Item index, int n) {
        EntityItem entityItem = null;
        ItemStack is = new ItemStack(index, n, 0);
        entityItem = new EntityItem(
            this.worldObj,
            this.posX + OreSpawnMain.OreSpawnRand.nextInt(5) - OreSpawnMain.OreSpawnRand.nextInt(5),
            this.posY + 1.0,
            this.posZ + OreSpawnMain.OreSpawnRand.nextInt(5) - OreSpawnMain.OreSpawnRand.nextInt(5),
            is);
        if (entityItem != null) {
            this.worldObj.spawnEntityInWorld(entityItem);
        }

        return is;
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        int i = 1 + this.worldObj.rand.nextInt(6);

        for (int i2 = 0; i2 < i; i2++) {
            this.dropItemRand(Items.beef, 1);
        }
    }

    public boolean attackEntityAsMob(Entity target) {
        double ks = 1.75;
        double inair = 0.1;
        float iskraken = 1.0F;
        if (target != null && target instanceof EntityLivingBase) {
            if (target instanceof Kraken) {
                iskraken = 2.0F;
            }

            target.attackEntityFrom(DamageSource.causeMobDamage(this), iskraken * 35.0F);
            float f3 = (float) Math.atan2(target.posZ - this.posZ, target.posX - this.posX);
            if (target.isDead || target instanceof EntityPlayer) {
                inair *= 2.0;
            }

            target.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
        }

        return true;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = false;
        Entity e = null;
        if (this.hurt_timer > 0) {
            return false;
        } else if (source.getDamageType()
            .equals("cactus")) {
                return ret;
            } else if (source.getDamageType()
                .equals("inFire")) {
                    return ret;
                } else if (source.getDamageType()
                    .equals("onFire")) {
                        return ret;
                    } else if (source.getDamageType()
                        .equals("lava")) {
                            return ret;
                        } else if (source.getDamageType()
                            .equals("inWall")) {
                                return ret;
                            } else {
                                this.setSitting(false);
                                this.setActivity(1);
                                e = source.getEntity();
                                if (e != null && e instanceof BetterFireball && this.dragontype == 0) {
                                    e.setDead();
                                    return ret;
                                } else if (e != null && e instanceof IceBall && this.dragontype != 0) {
                                    e.setDead();
                                    return ret;
                                } else if (e != null && e instanceof WaterBall && this.dragontype != 0) {
                                    e.setDead();
                                    return ret;
                                } else if (e != null && e instanceof EntitySmallFireball && this.dragontype == 0) {
                                    e.setDead();
                                    return ret;
                                } else if (e != null && e instanceof Dragon) {
                                    return false;
                                } else if (e != null && e instanceof Spyro) {
                                    return false;
                                } else {
                                    ret = super.attackEntityFrom(source, amount);
                                    this.hurt_timer = 20;
                                    if (e != null && e instanceof EntityLivingBase) {
                                        if (this.isTamed() && e instanceof EntityPlayer) {
                                            return false;
                                        }

                                        this.setAttackTarget((EntityLivingBase) e);
                                        this.setTarget(e);
                                        this.getNavigator()
                                            .tryMoveToEntityLiving((EntityLivingBase) e, 1.2);
                                        ret = true;
                                    }

                                    return ret;
                                }
                            }
    }

    public void updateAITasks() {
        EntityLivingBase e = null;
        super.updateAITasks();
        if (!this.isSitting() && this.getActivity() == 0
            && this.riddenByEntity == null
            && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL
            && this.worldObj.rand.nextInt(10) == 1) {
            e = this.findSomethingToAttack();
            if (e != null) {
                this.setActivity(1);
            }
        }
    }

    public void always_do() {
        EntityPlayer p = null;
        if (this.worldObj.rand.nextInt(250) == 1 && this.getHealth() < this.mygetMaxHealth()) {
            this.heal(2.0F);
        }

        if (!this.isSitting()) {
            this.owner_flying = 0;
            if (this.isTamed() && this.getOwner() != null && this.riddenByEntity == null && !this.isSitting()) {
                p = (EntityPlayer) this.getOwner();
                if (p.capabilities.isFlying) {
                    this.owner_flying = 1;
                    this.setActivity(1);
                }
            }

            if (this.isTamed() && this.getOwner() != null && !this.isSitting()) {
                p = (EntityPlayer) this.getOwner();
                if (this.getDistanceSqToEntity(p) > 400.0) {
                    this.setActivity(1);
                }
            }

            if (this.worldObj.rand.nextInt(50) == 1 && !this.isSitting()
                && !this.target_in_sight
                && this.riddenByEntity == null) {
                if (this.worldObj.rand.nextInt(15) == 1) {
                    this.setActivity(1);
                } else {
                    this.setActivity(0);
                }
            }

            if (this.worldObj.rand.nextInt(25) == 0 && !this.target_in_sight && this.riddenByEntity == null) {
                this.closest = 99999;
                this.tx = this.ty = this.tz = 0;

                for (int i = 1; i < 11; i++) {
                    int j = i;
                    if (i > 4) {
                        j = 4;
                    }

                    if (this.scan_it((int) this.posX, (int) this.posY - 1, (int) this.posZ, i, j, i)) {
                        break;
                    }

                    if (i >= 6) {
                        i++;
                    }
                }

                if (this.closest < 99999) {
                    this.setActivity(0);
                    this.getNavigator()
                        .tryMoveToXYZ(this.tx, this.ty - 1, this.tz, 1.0);
                    if (this.handleLavaMovement()) {
                        this.heal(1.0F);
                        this.playSound("splash", 1.0F, this.worldObj.rand.nextFloat() * 0.2F + 0.9F);
                    }
                }
            }
        }
    }

    public void fly_with_rider() {
        EntityLivingBase e = null;
        int freq = 7;
        if (!this.isDead) {
            if (!this.isSitting()) {
                if (!this.worldObj.isRemote) {
                    if (this.worldObj.rand.nextInt(freq) == 1
                        && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) {
                        if (this.worldObj.rand.nextInt(250) == 0) {
                            this.setAttackTarget(null);
                        }

                        e = this.getAttackTarget();
                        if (e != null && !e.isEntityAlive()) {
                            this.setAttackTarget(null);
                            e = null;
                        }

                        if (e == null) {
                            e = this.findSomethingToAttack();
                        }

                        if (e != null) {
                            this.setAttacking(1);
                            if (this.getDistanceSqToEntity(e) < (7.0F + e.width / 2.0F) * (7.0F + e.width / 2.0F)) {
                                this.attackEntityAsMob(e);
                            }

                            return;
                        }

                        this.setAttacking(0);
                    }
                }
            }
        }
    }

    protected void updateAITick() {
        if (this.riddenByEntity == null) {
            super.updateAITick();
        }
    }

    private boolean isSuitableTarget(EntityLivingBase target, boolean checkSight) {
        if (this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) {
            return false;
        } else if (target == null) {
            return false;
        } else if (target == this) {
            return false;
        } else if (!target.isEntityAlive()) {
            return false;
        } else if (!this.getEntitySenses()
            .canSee(target)) {
                return false;
            } else if (target instanceof LurkingTerror) {
                return false;
            } else if (target instanceof EnderReaper) {
                return false;
            } else if (target instanceof TerribleTerror) {
                return false;
            } else if (target instanceof LeafMonster) {
                return false;
            } else if (target instanceof CreepingHorror) {
                return false;
            } else if (target instanceof Triffid) {
                return false;
            } else if (target instanceof EntityMob) {
                return true;
            } else if (target instanceof Mothra) {
                return true;
            } else if (target instanceof Kraken) {
                return true;
            } else {
                return target instanceof EntityPlayer ? false : false;
            }
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(20.0, 20.0, 20.0));
            Collections.sort(entities, this.TargetSorter);
            Iterator iterator = entities.iterator();
            Entity entity = null;
            EntityLivingBase target = null;

            while (iterator.hasNext()) {
                entity = (Entity) iterator.next();
                target = (EntityLivingBase) entity;
                if (this.isSuitableTarget(target, false)) {
                    return target;
                }
            }

            return null;
        }
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return false;
    }

    public boolean getCanSpawnHere() {
        Dragon target = null;
        if (!this.worldObj.isDaytime()) {
            return false;
        } else {
            target = (Dragon) this.worldObj
                .findNearestEntityWithinAABB(Dragon.class, this.boundingBox.expand(16.0, 6.0, 16.0), this);
            if (target != null) {
                return false;
            } else {
                return this.worldObj.provider.dimensionId == OreSpawnMain.DimensionID4 ? true : !(this.posY < 50.0);
            }
        }
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.worldObj.rayTraceBlocks(
            Vec3.createVectorHelper(this.posX, this.posY + 0.75, this.posZ),
            Vec3.createVectorHelper(pX, pY, pZ),
            false) == null;
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch,
        int posRotationIncrements) {
        super.setPositionAndRotation2(x, y, z, yaw, pitch, posRotationIncrements);
        this.boatPosRotationIncrements = posRotationIncrements;
        this.boatX = x;
        this.boatY = y;
        this.boatZ = z;
        this.boatYaw = yaw;
        this.boatPitch = pitch;
        this.boatYawHead = yaw;
    }

    @SideOnly(Side.CLIENT)
    public void setVelocity(double vx, double vy, double vz) {
        super.setVelocity(vx, vy, vz);
    }

    public void onUpdate() {
        EntityLivingBase e = null;
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
        if (this.hurt_timer > 0) {
            this.hurt_timer--;
        }

        if (this.getActivity() == 1) {
            this.wing_sound++;
            if (this.wing_sound > 20) {
                if (!this.worldObj.isRemote) {
                    this.worldObj.playSoundAtEntity(this, "orespawn:MothraWings", 0.5F, 1.0F);
                }

                this.wing_sound = 0;
            }
        }

        if (this.isInWater()) {
            this.motionY += 0.07;
        }

        if (!this.worldObj.isRemote) {
            if (this.getActivity() == 0 && this.isTamed() && this.getOwner() != null && !this.isSitting()) {
                e = this.getOwner();
                if (this.getDistanceSqToEntity(e) > 144.0) {
                    this.setActivity(1);
                }
            }
        }
    }

    private void fly_without_rider() {
        int xdir = 1;
        int zdir = 1;
        int keep_trying = 50;
        int do_new = 0;
        double ox = 0.0;
        double oy = 0.0;
        double oz = 0.0;
        int has_owner = 0;
        EntityLivingBase e = null;
        double speed_factor = 0.5;
        double dx2 = 0.0;
        double dx3 = 0.0;
        double dy = 0.0;
        double yoff = 1.25;
        double xzoff = 2.25;
        double gh = 1.25;
        double obstruction_factor = 0.0;
        double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        EntitySmallFireball sf = null;
        BetterFireball bf = null;
        IceBall ib = null;
        WaterBall wb = null;
        int toofar = 0;
        if (this.currentFlightTarget == null) {
            do_new = 1;
            this.currentFlightTarget = new ChunkCoordinates((int) this.posX, (int) this.posY, (int) this.posZ);
        }

        if (!this.isSitting()) {
            if (this.riddenByEntity == null) {
                if (this.unstick_timer > 0) {
                    this.unstick_timer--;
                }

                if (this.lastX == (int) this.posX && this.lastZ == (int) this.posZ) {
                    this.stuck_count++;
                    if (this.stuck_count > 50) {
                        this.stuck_count = 0;
                        this.unstick_timer = 100;
                        this.target_in_sight = false;
                        this.setAttacking(0);
                        do_new = 1;
                    }
                } else {
                    this.stuck_count = 0;
                    this.lastX = (int) this.posX;
                    this.lastZ = (int) this.posZ;
                }

                if (this.posY < this.currentFlightTarget.posY + 2.0) {
                    this.motionY *= 0.7;
                } else if (this.posY > this.currentFlightTarget.posY - 2.0) {
                    this.motionY *= 0.5;
                } else {
                    this.motionY *= 0.61;
                }

                if (this.worldObj.rand.nextInt(300) == 1) {
                    do_new = 1;
                }

                if (this.isTamed() && this.getOwner() != null) {
                    e = this.getOwner();
                    has_owner = 1;
                    ox = e.posX;
                    oy = e.posY;
                    oz = e.posZ;
                    if (this.getDistanceSqToEntity(e) > 144.0) {
                        toofar = 1;
                        this.target_in_sight = false;
                        this.setAttacking(0);
                        this.flyaway = 0;
                        do_new = 1;
                    }
                }

                if (this.flyaway > 0) {
                    this.flyaway--;
                }

                if (toofar == 0 && this.unstick_timer == 0
                    && this.flyaway == 0
                    && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL
                    && this.worldObj.rand.nextInt(9) == 1) {
                    e = this.findSomethingToAttack();
                    if (e != null) {
                        if (this.isTamed() && this.getHealth() / this.mygetMaxHealth() < 0.25F) {
                            this.setActivity(1);
                            this.setAttacking(0);
                            this.target_in_sight = false;
                            do_new = 0;
                            this.currentFlightTarget.set(
                                (int) (this.posX + (this.posX - e.posX)),
                                (int) (this.posY + 1.0),
                                (int) (this.posZ + (this.posZ - e.posZ)));
                        } else {
                            this.setActivity(1);
                            this.setAttacking(1);
                            this.target_in_sight = true;
                            this.currentFlightTarget.set((int) e.posX, (int) (e.posY + 1.0), (int) e.posZ);
                            do_new = 0;
                            if (this.getDistanceSqToEntity(e) < (5.0F + e.width / 2.0F) * (5.0F + e.width / 2.0F)) {
                                this.attackEntityAsMob(e);
                                this.flyaway = 5 + this.worldObj.rand.nextInt(10);
                                do_new = 1;
                            } else if (this.getDistanceSqToEntity(e) < 256.0 && !this.isInWater()
                                && this.getDragonFire() >= 1) {
                                    double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
                                    double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
                                    if (this.dragontype == 0) {
                                        if (this.getDragonFire() == 1) {
                                            sf = new EntitySmallFireball(
                                                this.worldObj,
                                                this,
                                                e.posX - cx,
                                                e.posY + e.height / 2.0F - (this.posY + yoff),
                                                e.posZ - cz);
                                            sf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0F);
                                            sf.setPosition(cx, this.posY + yoff, cz);
                                            this.worldObj.playSoundAtEntity(
                                                this,
                                                "random.bow",
                                                0.75F,
                                                1.0F / (this.getRNG()
                                                    .nextFloat() * 0.4F + 0.8F));
                                            this.worldObj.spawnEntityInWorld(sf);
                                        } else {
                                            bf = new BetterFireball(
                                                this.worldObj,
                                                this,
                                                e.posX - cx,
                                                e.posY + e.height / 2.0F - (this.posY + yoff),
                                                e.posZ - cz);
                                            bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0F);
                                            bf.setPosition(cx, this.posY + yoff, cz);
                                            this.worldObj.playSoundAtEntity(
                                                this,
                                                "random.fuse",
                                                1.0F,
                                                1.0F / (this.getRNG()
                                                    .nextFloat() * 0.4F + 0.8F));
                                            this.worldObj.spawnEntityInWorld(bf);
                                        }
                                    } else if (this.getDragonFire() == 1) {
                                        wb = new WaterBall(
                                            this.worldObj,
                                            e.posX - this.posX,
                                            e.posY + e.height / 2.0F - (this.posY + yoff),
                                            e.posZ - this.posZ);
                                        wb.setLocationAndAngles(
                                            this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw)),
                                            this.posY + yoff,
                                            this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)),
                                            this.rotationYawHead,
                                            this.rotationPitch);
                                        dx3 = e.posX - wb.posX;
                                        dy = e.posY + 0.25 - wb.posY;
                                        double dz = e.posZ - wb.posZ;
                                        float f = MathHelper.sqrt_double(dx3 * dx3 + dz * dz) * 0.2F;
                                        wb.setThrowableHeading(dx3, dy + f, dz, 1.4F, 5.0F);
                                        this.worldObj.playSoundAtEntity(
                                            this,
                                            "random.bow",
                                            0.75F,
                                            1.0F / (this.getRNG()
                                                .nextFloat() * 0.4F + 0.8F));
                                        this.worldObj.spawnEntityInWorld(wb);
                                    } else {
                                        ib = new IceBall(
                                            this.worldObj,
                                            e.posX - this.posX,
                                            e.posY + e.height / 2.0F - (this.posY + yoff),
                                            e.posZ - this.posZ);
                                        ib.setLocationAndAngles(
                                            this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw)),
                                            this.posY + yoff,
                                            this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)),
                                            this.rotationYawHead,
                                            this.rotationPitch);
                                        ib.setSpecial();
                                        ib.setIceBall();
                                        dx3 = e.posX - ib.posX;
                                        dy = e.posY + 0.25 - ib.posY;
                                        double dz = e.posZ - ib.posZ;
                                        float f = MathHelper.sqrt_double(dx3 * dx3 + dz * dz) * 0.2F;
                                        ib.setThrowableHeading(dx3, dy + f, dz, 1.4F, 5.0F);
                                        this.worldObj.playSoundAtEntity(
                                            this,
                                            "random.fuse",
                                            1.0F,
                                            1.0F / (this.getRNG()
                                                .nextFloat() * 0.4F + 0.8F));
                                        this.worldObj.spawnEntityInWorld(ib);
                                    }
                                }
                        }
                    } else {
                        this.target_in_sight = false;
                        this.flyaway = 0;
                        this.setAttacking(0);
                    }
                }

                if (this.currentFlightTarget.getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ)
                    < 2.1F) {
                    do_new = 1;
                }

                if (do_new != 0 && !this.target_in_sight || do_new != 0 && this.flyaway != 0) {
                    for (Block bid = Blocks.stone; bid != Blocks.air && keep_trying != 0; keep_trying--) {
                        int gox = (int) this.posX;
                        int goy = (int) this.posY;
                        int goz = (int) this.posZ;
                        if (has_owner == 1 && this.unstick_timer == 0) {
                            gox = (int) ox;
                            goy = (int) oy;
                            goz = (int) oz;
                            if (this.owner_flying == 0) {
                                zdir = this.worldObj.rand.nextInt(10) + 4;
                                xdir = this.worldObj.rand.nextInt(10) + 4;
                            } else {
                                zdir = this.worldObj.rand.nextInt(6);
                                xdir = this.worldObj.rand.nextInt(6);
                            }
                        } else {
                            zdir = this.worldObj.rand.nextInt(10) + 16;
                            xdir = this.worldObj.rand.nextInt(10) + 16;
                        }

                        if (this.worldObj.rand.nextInt(2) == 1) {
                            zdir = -zdir;
                        }

                        if (this.worldObj.rand.nextInt(2) == 1) {
                            xdir = -xdir;
                        }

                        this.currentFlightTarget.set(
                            gox + xdir,
                            goy + this.worldObj.rand.nextInt(9 + this.owner_flying * 2) - 4,
                            goz + zdir);
                        bid = this.worldObj.getBlock(
                            this.currentFlightTarget.posX,
                            this.currentFlightTarget.posY,
                            this.currentFlightTarget.posZ);
                        if (bid == Blocks.air && !this.canSeeTarget(
                            this.currentFlightTarget.posX,
                            this.currentFlightTarget.posY,
                            this.currentFlightTarget.posZ)) {
                            bid = Blocks.stone;
                        }
                    }
                }

                obstruction_factor = 0.0;
                int dist = 2;
                dist += (int) (velocity * 4.0);

                for (int k = 1; k < dist; k++) {
                    for (int i = 1; i < dist * 2; i++) {
                        double dx = i * Math.cos(Math.toRadians(this.rotationYaw + 90.0F));
                        double dz = i * Math.sin(Math.toRadians(this.rotationYaw + 90.0F));
                        Block bid = this.worldObj
                            .getBlock((int) (this.posX + dx), (int) this.posY - k, (int) (this.posZ + dz));
                        if (bid != Blocks.air) {
                            obstruction_factor += 0.05;
                        }
                    }
                }

                this.motionY += obstruction_factor * 0.05;
                this.posY += obstruction_factor * 0.05;
                speed_factor = 0.5;
                dx2 = this.currentFlightTarget.posX + 0.5 - this.posX;
                dx3 = this.currentFlightTarget.posY + 0.1 - this.posY;
                dy = this.currentFlightTarget.posZ + 0.5 - this.posZ;
                if (this.owner_flying != 0) {
                    speed_factor = 1.75;
                    if (this.isTamed() && this.getOwner() != null) {
                        e = this.getOwner();
                        if (this.getDistanceSqToEntity(e) > 49.0) {
                            speed_factor = 3.5;
                        }
                    }
                }

                this.motionX = this.motionX + (Math.signum(dx2) - this.motionX) * 0.15 * speed_factor;
                this.motionY = this.motionY + (Math.signum(dx3) - this.motionY) * 0.21 * speed_factor;
                this.motionZ = this.motionZ + (Math.signum(dy) - this.motionZ) * 0.15 * speed_factor;
                float f = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
                float f2 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
                this.moveForward = (float) (0.75 * speed_factor);
                this.rotationYaw += f2 / 4.0F;
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
            }
        }
    }

    public void onLivingUpdate() {
        List list = null;
        Entity listEntity = null;
        double d6 = this.rand.nextFloat() * 2.0F - 1.0F;
        double d7 = (this.rand.nextInt(2) * 2 - 1) * 0.7;
        double obstruction_factor = 0.0;
        double relative_g = 0.0;
        double max_speed = 0.95;
        double gh = 1.0;
        double rt = 0.0;
        double pi = 3.1415926545;
        double deltav = 0.0;
        int dist = 2;
        BetterFireball bf = null;
        if (this.getActivity() == 0) {
            super.onLivingUpdate();
        } else if (this.isDead) {
            super.onLivingUpdate();
            return;
        }

        if (!this.isDead) {
            if (this.worldObj.isRemote) {
                if (this.boatPosRotationIncrements > 0 && this.getActivity() != 0) {
                    double d4 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
                    double d5 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
                    double d11 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
                    this.setPosition(d4, d5, d11);
                    this.rotationPitch = (float) (this.rotationPitch
                        + (this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
                    double d10 = MathHelper.wrapAngleTo180_double(this.boatYaw - this.rotationYaw);
                    if (this.riddenByEntity != null) {
                        d10 = MathHelper
                            .wrapAngleTo180_double((double) this.riddenByEntity.rotationYaw - this.rotationYaw);
                    }

                    this.rotationYaw = (float) (this.rotationYaw + d10 / this.boatPosRotationIncrements);
                    this.setRotation(this.rotationYaw, this.rotationPitch);
                    this.rotationYawHead = this.rotationYaw;
                    this.boatPosRotationIncrements--;
                }
            } else {
                if (this.getActivity() != 0) {
                    if (this.fireballticker > 0) {
                        this.fireballticker--;
                    }

                    if (this.riddenByEntity == null) {
                        this.fly_without_rider();
                    } else {
                        EntityPlayer pp = (EntityPlayer) this.riddenByEntity;
                        if (this.motionX < -2.0) {
                            this.motionX = -2.0;
                        }

                        if (this.motionX > 2.0) {
                            this.motionX = 2.0;
                        }

                        if (this.motionZ < -2.0) {
                            this.motionZ = -2.0;
                        }

                        if (this.motionZ > 2.0) {
                            this.motionZ = 2.0;
                        }

                        double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                        gh = 1.25;
                        Block bid = this.worldObj
                            .getBlock((int) this.posX, (int) ((float) this.posY - (float) gh), (int) this.posZ);
                        if (bid != Blocks.air) {
                            this.motionY += 0.03;
                            this.posY += 0.1;
                        } else {
                            this.motionY -= 0.018;
                        }

                        obstruction_factor = 0.0;
                        int n = 3;
                        n += (int) (velocity * 7.0);

                        for (int k = 1; k < n; k++) {
                            for (int i = 1; i < n * 2; i++) {
                                double dx = i * Math.cos(Math.toRadians(this.rotationYaw + 90.0F));
                                double dz = i * Math.sin(Math.toRadians(this.rotationYaw + 90.0F));
                                bid = this.worldObj
                                    .getBlock((int) (this.posX + dx), (int) this.posY - k, (int) (this.posZ + dz));
                                if (bid != Blocks.air) {
                                    obstruction_factor += 0.05;
                                }
                            }
                        }

                        this.motionY += obstruction_factor * 0.07;
                        this.posY += obstruction_factor * 0.07;
                        if (this.motionY > 2.0) {
                            this.motionY = 2.0;
                        }

                        double d4 = this.riddenByEntity.rotationYaw;
                        d4 %= 360.0;

                        while (d4 < 0.0) {
                            d4 += 360.0;
                        }

                        double d5 = this.rotationYaw;
                        d5 %= 360.0;

                        while (d5 < 0.0) {
                            d5 += 360.0;
                        }

                        relative_g = (d4 - d5) % 180.0;

                        while (relative_g < 0.0) {
                            relative_g += 180.0;
                        }

                        if (relative_g > 90.0) {
                            relative_g -= 180.0;
                        }

                        if (velocity > 0.01) {
                            d4 = 1.85 - velocity;
                            d4 = Math.abs(d4);
                            if (d4 < 0.01) {
                                d4 = 0.01;
                            }

                            if (d4 > 0.9) {
                                d4 = 0.9;
                            }

                            this.rotationYaw = this.riddenByEntity.rotationYaw + (float) (relative_g * d4);
                        } else {
                            this.rotationYaw = this.riddenByEntity.rotationYaw;
                        }

                        relative_g = Math.abs(relative_g) * velocity;
                        if (relative_g > 50.0) {
                            relative_g = 0.0;
                        }

                        this.rotationPitch = 2.0F * (float) velocity;
                        this.setRotation(this.rotationYaw, this.rotationPitch);
                        double newvelocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                        double rr = Math.atan2(this.riddenByEntity.motionZ, this.riddenByEntity.motionX);
                        double rhm = Math.atan2(this.motionZ, this.motionX);
                        double rhdir = Math.toRadians((this.riddenByEntity.rotationYaw + 90.0F) % 360.0F);
                        rt = 0.0;
                        pi = 3.1415926545;
                        deltav = 0.0;
                        float im = pp.moveForward;
                        if (OreSpawnMain.flyup_keystate != 0) {
                            this.motionY += 0.03;
                            this.motionY += velocity * 0.036;
                        }

                        double rdv = Math.abs(rhm - rhdir) % (pi * 2.0);
                        if (rdv > pi) {
                            rdv -= pi * 2.0;
                        }

                        rdv = Math.abs(rdv);
                        if (Math.abs(newvelocity) < 0.01) {
                            rdv = 0.0;
                        }

                        if (rdv > 1.5) {
                            newvelocity = -newvelocity;
                        }

                        if (Math.abs(im) > 0.001F) {
                            if (im > 0.0F) {
                                deltav = 0.025;
                                if (max_speed > 1.0) {
                                    deltav += 0.05;
                                }

                                if (this.deltasmooth < 0.0F) {
                                    this.deltasmooth = 0.0F;
                                }

                                this.deltasmooth = (float) (this.deltasmooth + deltav / 10.0);
                                if (this.deltasmooth > deltav) {
                                    this.deltasmooth = (float) deltav;
                                }
                            } else {
                                max_speed = 0.35;
                                deltav = -0.02;
                                if (this.deltasmooth > 0.0F) {
                                    this.deltasmooth = 0.0F;
                                }

                                this.deltasmooth = (float) (this.deltasmooth + deltav / 10.0);
                                if (this.deltasmooth < deltav) {
                                    this.deltasmooth = (float) deltav;
                                }
                            }

                            newvelocity += this.deltasmooth;
                            if (newvelocity >= 0.0) {
                                if (newvelocity > max_speed) {
                                    newvelocity = max_speed;
                                }

                                this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
                                this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
                            } else {
                                if (newvelocity < -max_speed) {
                                    newvelocity = -max_speed;
                                }

                                newvelocity = -newvelocity;
                                this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0F)) * newvelocity;
                                this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0F)) * newvelocity;
                            }
                        } else if (newvelocity >= 0.0) {
                            this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
                            this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
                        } else {
                            this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0F)) * (newvelocity * -1.0);
                            this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0F)) * (newvelocity * -1.0);
                        }

                        if (this.fireballticker == 0) {
                            double xzoff = 4.0;
                            double yoff = -0.25;
                            if (this.getDragonType() == 0) {
                                if (pp.moveStrafing > 0.001F) {
                                    bf = new BetterFireball(this.worldObj, this, 0.0, 0.0, 0.0);
                                    bf.setNotMe();
                                    bf.setSmall();
                                    double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
                                    double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
                                    bf.setPosition(cx, this.posY + yoff, cz);
                                    cx = Math.cos(Math.toRadians(pp.rotationYawHead + 90.0F));
                                    cz = Math.sin(Math.toRadians(pp.rotationYawHead + 90.0F));
                                    double cy = -Math.sin(Math.toRadians(pp.rotationPitch));
                                    double d3 = MathHelper.sqrt_double(cx * cx + cy * cy + cz * cz);
                                    bf.accelerationX = cx / d3 * 0.15;
                                    bf.accelerationY = cy / d3 * 0.15;
                                    bf.accelerationZ = cz / d3 * 0.15;
                                    bf.motionX = this.motionX;
                                    bf.motionY = this.motionY;
                                    bf.motionZ = this.motionZ;
                                    bf.posX = bf.posX - this.motionX * 9.0;
                                    bf.posY = bf.posY - this.motionY * 9.0;
                                    bf.posZ = bf.posZ - this.motionZ * 9.0;
                                    this.worldObj.playSoundAtEntity(
                                        this,
                                        "random.bow",
                                        0.75F,
                                        1.0F / (this.getRNG()
                                            .nextFloat() * 0.4F + 0.8F));
                                    this.worldObj.spawnEntityInWorld(bf);
                                    this.fireballticker = 10;
                                }

                                if (pp.moveStrafing < -0.001F) {
                                    bf = new BetterFireball(this.worldObj, this, 0.0, 0.0, 0.0);
                                    bf.setNotMe();
                                    double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
                                    double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
                                    bf.setPosition(cx, this.posY + yoff, cz);
                                    cx = Math.cos(Math.toRadians(pp.rotationYawHead + 90.0F));
                                    cz = Math.sin(Math.toRadians(pp.rotationYawHead + 90.0F));
                                    double cy = -Math.sin(Math.toRadians(pp.rotationPitch));
                                    double d3 = MathHelper.sqrt_double(cx * cx + cy * cy + cz * cz);
                                    bf.accelerationX = cx / d3 * 0.1;
                                    bf.accelerationY = cy / d3 * 0.1;
                                    bf.accelerationZ = cz / d3 * 0.1;
                                    bf.motionX = this.motionX;
                                    bf.motionY = this.motionY;
                                    bf.motionZ = this.motionZ;
                                    bf.posX = bf.posX - this.motionX * 9.0;
                                    bf.posY = bf.posY - this.motionY * 9.0;
                                    bf.posZ = bf.posZ - this.motionZ * 9.0;
                                    this.worldObj.playSoundAtEntity(
                                        this,
                                        "random.fuse",
                                        1.0F,
                                        1.0F / (this.getRNG()
                                            .nextFloat() * 0.4F + 0.8F));
                                    this.worldObj.spawnEntityInWorld(bf);
                                    this.fireballticker = 20;
                                }
                            } else {
                                if (pp.moveStrafing > 0.001F) {
                                    double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
                                    double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
                                    WaterBall waterBall = new WaterBall(this.worldObj, cx, this.posY + yoff, cz);
                                    waterBall.setLocationAndAngles(
                                        cx,
                                        this.posY + yoff,
                                        cz,
                                        pp.rotationYaw + 90.0F,
                                        pp.rotationPitch);
                                    double vx = Math.cos(Math.toRadians(pp.rotationYawHead + 90.0F));
                                    double d = -Math.sin(Math.toRadians(pp.rotationPitch));
                                    double vz = Math.sin(Math.toRadians(pp.rotationYawHead + 90.0F));
                                    float f = MathHelper.sqrt_double(vx * vx + vz * vz) * 0.2F;
                                    waterBall.setThrowableHeading(vx, d + f, vz, 1.4F, 5.0F);
                                    waterBall.posX = waterBall.posX - this.motionX * 7.0;
                                    waterBall.posY = waterBall.posY - this.motionY * 7.0;
                                    waterBall.posZ = waterBall.posZ - this.motionZ * 7.0;
                                    this.worldObj.playSoundAtEntity(
                                        this,
                                        "random.bow",
                                        0.75F,
                                        1.0F / (this.getRNG()
                                            .nextFloat() * 0.4F + 0.8F));
                                    this.worldObj.spawnEntityInWorld(waterBall);
                                    this.fireballticker = 5;
                                }

                                if (pp.moveStrafing < -0.001F) {
                                    double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
                                    double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
                                    IceBall iceBall = new IceBall(this.worldObj, cx, this.posY + yoff, cz);
                                    iceBall.setLocationAndAngles(
                                        cx,
                                        this.posY + yoff,
                                        cz,
                                        pp.rotationYaw + 90.0F,
                                        pp.rotationPitch);
                                    iceBall.setSpecial();
                                    iceBall.setIceBall();
                                    double vx = Math.cos(Math.toRadians(pp.rotationYaw + 90.0F));
                                    double d = -Math.sin(Math.toRadians(pp.rotationPitch));
                                    double vz = Math.sin(Math.toRadians(pp.rotationYaw + 90.0F));
                                    float f = MathHelper.sqrt_double(vx * vx + vz * vz) * 0.2F;
                                    iceBall.setThrowableHeading(vx, d + f, vz, 1.4F, 5.0F);
                                    iceBall.posX = iceBall.posX - this.motionX * 7.0;
                                    iceBall.posY = iceBall.posY - this.motionY * 7.0;
                                    iceBall.posZ = iceBall.posZ - this.motionZ * 7.0;
                                    iceBall.motionX *= 2.0;
                                    iceBall.motionY *= 2.0;
                                    iceBall.motionZ *= 2.0;
                                    this.worldObj.playSoundAtEntity(
                                        this,
                                        "fireworks.launch",
                                        0.75F,
                                        1.0F / (this.getRNG()
                                            .nextFloat() * 0.4F + 0.8F));
                                    this.worldObj.spawnEntityInWorld(iceBall);
                                    this.fireballticker = 15;
                                }
                            }
                        }

                        this.moveEntity(this.motionX, this.motionY, this.motionZ);
                        this.motionX *= 0.985;
                        this.motionY *= 0.94;
                        this.motionZ *= 0.985;
                        if (!this.worldObj.isRemote) {
                            list = this.worldObj
                                .getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(2.25, 2.0, 2.25));
                            if (list != null && !list.isEmpty()) {
                                for (int l = 0; l < list.size(); l++) {
                                    listEntity = (Entity) list.get(l);
                                    if (listEntity != this.riddenByEntity && !listEntity.isDead
                                        && listEntity.canBePushed()) {
                                        listEntity.applyEntityCollision(this);
                                    }
                                }
                            }
                        }

                        this.fly_with_rider();
                        if (this.riddenByEntity.isDead) {
                            this.riddenByEntity = null;
                        }
                    }
                }

                this.always_do();
            }
        }
    }

    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            float f = 0.65F;
            this.riddenByEntity.setPosition(
                this.posX - f * Math.sin(Math.toRadians(this.rotationYaw)),
                this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(),
                this.posZ + f * Math.cos(Math.toRadians(this.rotationYaw)));
        }
    }

    protected void playTameEffect(boolean success) {
        String s = "heart";
        if (!success) {
            s = "smoke";
        }

        for (int i = 0; i < 20; i++) {
            double d0 = this.rand.nextGaussian() * 0.08;
            double d1 = this.rand.nextGaussian() * 0.08;
            double d2 = this.rand.nextGaussian() * 0.08;
            this.worldObj.spawnParticle(
                s,
                this.posX + (this.rand.nextFloat() - this.rand.nextFloat()) * 2.5F,
                this.posY + 0.5 + this.rand.nextFloat() * 1.5,
                this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()) * 2.5F,
                d0,
                d1,
                d2);
        }
    }

    public boolean interact(EntityPlayer player) {
        ItemStack currentItem = player.inventory.getCurrentItem();
        if (currentItem != null && currentItem.stackSize <= 0) {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
            currentItem = null;
        }

        if (!this.isTamed()) {
            if (currentItem != null && currentItem.getItem() == Items.beef
                && player.getDistanceSqToEntity(this) < 25.0) {
                if (!this.worldObj.isRemote) {
                    if (this.worldObj.rand.nextInt(5) == 1) {
                        this.setTamed(true);
                        this.func_152115_b(
                            player.getUniqueID()
                                .toString());
                        this.playTameEffect(true);
                        this.worldObj.setEntityState(this, (byte) 7);
                        this.heal(this.mygetMaxHealth() - this.getHealth());
                    } else {
                        this.playTameEffect(false);
                        this.worldObj.setEntityState(this, (byte) 6);
                    }
                }

                if (!player.capabilities.isCreativeMode) {
                    currentItem.stackSize--;
                    if (currentItem.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                    }
                }

                return true;
            }
        } else {
            if (!this.func_152114_e(player)) {
                return false;
            }

            if (currentItem == null && player.getDistanceSqToEntity(this) < 16.0) {
                if (!this.worldObj.isRemote) {
                    player.mountEntity(this);
                    this.setActivity(1);
                    this.setSitting(false);
                }

                return true;
            }

            if (currentItem != null && currentItem.getItem() == Items.beef
                && player.getDistanceSqToEntity(this) < 25.0) {
                if (this.worldObj.isRemote) {
                    this.playTameEffect(true);
                    this.worldObj.setEntityState(this, (byte) 7);
                }

                if (this.mygetMaxHealth() > this.getHealth()) {
                    this.heal(this.mygetMaxHealth() - this.getHealth());
                }

                if (!player.capabilities.isCreativeMode) {
                    currentItem.stackSize--;
                    if (currentItem.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                    }
                }

                return true;
            }

            if (currentItem != null && currentItem.getItem() == Item.getItemFromBlock(Blocks.deadbush)
                && player.getDistanceSqToEntity(this) < 25.0) {
                if (!this.worldObj.isRemote) {
                    this.setTamed(false);
                    this.func_152115_b("");
                    this.playTameEffect(false);
                    this.worldObj.setEntityState(this, (byte) 6);
                }

                if (!player.capabilities.isCreativeMode) {
                    currentItem.stackSize--;
                    if (currentItem.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                    }
                }

                return true;
            }

            if (currentItem != null && currentItem.getItem() == Item.getItemFromBlock(Blocks.ice)
                && player.getDistanceSqToEntity(this) < 25.0
                && this.func_152114_e(player)) {
                if (!this.worldObj.isRemote) {
                    this.playTameEffect(true);
                    this.worldObj.setEntityState(this, (byte) 6);
                    this.setDragonFire(0);
                    player.addChatComponentMessage(new ChatComponentText("Dragon fireballs extinguished."));
                }

                if (!player.capabilities.isCreativeMode) {
                    currentItem.stackSize--;
                    if (currentItem.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                    }
                }

                return true;
            }

            if (currentItem != null && currentItem.getItem() == Items.flint_and_steel
                && player.getDistanceSqToEntity(this) < 25.0
                && this.func_152114_e(player)) {
                if (!this.worldObj.isRemote) {
                    this.playTameEffect(true);
                    this.worldObj.setEntityState(this, (byte) 6);
                    this.setDragonFire(1);
                    player.addChatComponentMessage(new ChatComponentText("Dragon fireballs lit!"));
                }

                if (!player.capabilities.isCreativeMode) {
                    currentItem.stackSize--;
                    if (currentItem.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                    }
                }

                return true;
            }

            if (currentItem != null && currentItem.getItem() == Items.gunpowder
                && player.getDistanceSqToEntity(this) < 25.0
                && this.func_152114_e(player)
                && this.getDragonFire() > 0) {
                if (!this.worldObj.isRemote) {
                    this.playTameEffect(true);
                    this.worldObj.setEntityState(this, (byte) 6);
                    this.setDragonFire(2);
                    player.addChatComponentMessage(new ChatComponentText("Dragon fireballs supercharged!"));
                }

                if (!player.capabilities.isCreativeMode) {
                    currentItem.stackSize--;
                    if (currentItem.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                    }
                }

                return true;
            }

            if (currentItem != null && currentItem.getItem() == Items.snowball
                && player.getDistanceSqToEntity(this) < 25.0) {
                if (this.worldObj.isRemote) {
                    this.playTameEffect(true);
                    this.worldObj.setEntityState(this, (byte) 7);
                }

                this.dragontype = 1;
                this.setDragonType(this.dragontype);
                if (!player.capabilities.isCreativeMode) {
                    currentItem.stackSize--;
                    if (currentItem.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                    }
                }

                return true;
            }

            if (currentItem != null && currentItem.getItem() == Items.coal
                && player.getDistanceSqToEntity(this) < 25.0) {
                if (this.worldObj.isRemote) {
                    this.playTameEffect(true);
                    this.worldObj.setEntityState(this, (byte) 7);
                }

                this.dragontype = 0;
                this.setDragonType(this.dragontype);
                if (!player.capabilities.isCreativeMode) {
                    currentItem.stackSize--;
                    if (currentItem.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                    }
                }

                return true;
            }

            if (currentItem != null && currentItem.getItem() == Items.diamond
                && player.getDistanceSqToEntity(this) < 25.0
                && this.func_152114_e(player)
                && !this.worldObj.isRemote) {
                Entity ent = null;
                Spyro d = null;
                ent = spawnCreature(this.worldObj, "Baby Dragon", this.posX, this.posY, this.posZ);
                if (ent != null) {
                    d = (Spyro) ent;
                    if (this.isTamed()) {
                        d.setTamed(true);
                        d.func_152115_b(
                            player.getUniqueID()
                                .toString());
                    }

                    this.setDead();
                }

                if (!player.capabilities.isCreativeMode) {
                    currentItem.stackSize--;
                    if (currentItem.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                    }
                }

                return true;
            }

            if (this.isTamed() && currentItem != null
                && currentItem.getItem() == Items.name_tag
                && player.getDistanceSqToEntity(this) < 25.0
                && this.func_152114_e(player)) {
                this.setCustomNameTag(currentItem.getDisplayName());
                if (!player.capabilities.isCreativeMode) {
                    currentItem.stackSize--;
                    if (currentItem.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                    }
                }

                return true;
            }

            if (currentItem != null && player.getDistanceSqToEntity(this) < 25.0) {
                if (!this.isSitting()) {
                    this.setSitting(true);
                    this.setActivity(0);
                } else {
                    this.setSitting(false);
                    this.setActivity(0);
                }

                return true;
            }
        }

        return false;
    }

    public boolean isWheat(ItemStack stack) {
        return stack != null && stack.getItem() == Items.beef;
    }

    public int getAttacking() {
        return this.dataWatcher.getWatchableObjectByte(20);
    }

    public void setAttacking(int attacking) {
        if (this.worldObj == null || !this.worldObj.isRemote) {
            this.dataWatcher.updateObject(20, (byte) attacking);
        }
    }

    public int getActivity() {
        return this.dataWatcher.getWatchableObjectByte(21);
    }

    public void setActivity(int activity) {
        if (this.worldObj == null || !this.worldObj.isRemote) {
            this.dataWatcher.updateObject(21, (byte) activity);
        }
    }

    public int getDragonFire() {
        return this.dataWatcher.getWatchableObjectInt(24);
    }

    public void setDragonFire(int n) {
        if (!this.worldObj.isRemote) {
            this.dataWatcher.updateObject(24, n);
        }
    }

    public static Entity spawnCreature(World world, String str, double x, double y, double z) {
        Entity entity = null;
        entity = EntityList.createEntityByName(str, world);
        if (entity != null) {
            entity.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0F, 0.0F);
            world.spawnEntityInWorld(entity);
            ((EntityLiving) entity).playLivingSound();
        }

        return entity;
    }

    public void setDragonType(int n) {
        this.dataWatcher.updateObject(22, n);
    }

    public int getDragonType() {
        return this.dataWatcher.getWatchableObjectInt(22);
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return null;
    }

    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        } else {
            return this.riddenByEntity != null ? false : !this.isTamed();
        }
    }

    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger("DragonAttacking", this.getAttacking());
        tag.setInteger("DragonActivity", this.getActivity());
        tag.setInteger("DragonFire", this.getDragonFire());
        tag.setInteger("DragonType", this.getDragonType());
    }

    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        this.setAttacking(tag.getInteger("DragonAttacking"));
        this.setActivity(tag.getInteger("DragonActivity"));
        this.setDragonFire(tag.getInteger("DragonFire"));
        this.dragontype = tag.getInteger("DragonType");
        this.setDragonType(this.dragontype);
    }
}
