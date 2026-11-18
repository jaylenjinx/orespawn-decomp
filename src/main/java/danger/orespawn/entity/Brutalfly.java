package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.projectile.BetterFireball;

public class Brutalfly extends EntityMob {

    private ChunkCoordinates currentFlightTarget = null;
    private int lastX = 0;
    private int lastZ = 0;
    private int lastY = 0;
    private int stuck_count = 0;
    private int wing_sound = 0;
    private int health_ticker = 100;
    private GenericTargetSorter TargetSorter = null;
    private float moveSpeed = 0.35F;

    public Brutalfly(World world) {
        super(world);
        this.setSize(5.0F, 2.0F);
        this.getNavigator()
            .setAvoidsWater(true);
        this.experienceValue = 100;
        this.isImmuneToFire = true;
        this.fireResistance = 500;
        this.TargetSorter = new GenericTargetSorter(this);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(OreSpawnMain.Brutalfly_stats.attack);
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    protected void entityInit() {
        super.entityInit();
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.Brutalfly_stats.defense;
    }

    public int getBrutalflyHealth() {
        return (int) this.getHealth();
    }

    protected float getSoundVolume() {
        return 1.5F;
    }

    protected float getSoundPitch() {
        return 1.0F;
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return null;
    }

    protected String getDeathSound() {
        return "random.explode";
    }

    public boolean canBePushed() {
        return true;
    }

    protected void collideWithEntity(Entity entity) {}

    protected void collideWithNearbyEntities() {}

    public int mygetMaxHealth() {
        return OreSpawnMain.Brutalfly_stats.health;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onUpdate() {
        super.onUpdate();
        this.motionY *= 0.6;
        this.wing_sound++;
        if (this.wing_sound > 30) {
            if (!this.worldObj.isRemote) {
                this.worldObj.playSoundAtEntity(this, "orespawn:MothraWings", 1.0F, 1.0F);
            }

            this.wing_sound = 0;
        }

        this.health_ticker--;
        if (this.health_ticker <= 0) {
            if (this.getHealth() < this.mygetMaxHealth()) {
                this.heal(1.0F);
            }

            this.health_ticker = 100;
        }
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.worldObj.rayTraceBlocks(
            Vec3.createVectorHelper(this.posX, this.posY + 0.75, this.posZ),
            Vec3.createVectorHelper(pX, pY, pZ),
            false) == null;
    }

    protected void updateAITasks() {
        int xdir = 1;
        int zdir = 1;
        int keep_trying = 30;
        int shoot = 3;
        if (!this.isDead) {
            super.updateAITasks();
            if (this.lastX == (int) this.posX && this.lastY == (int) this.posY && this.lastZ == (int) this.posZ) {
                this.stuck_count++;
            } else {
                this.stuck_count = 0;
                this.lastX = (int) this.posX;
                this.lastY = (int) this.posY;
                this.lastZ = (int) this.posZ;
            }

            if (this.worldObj.difficultySetting == EnumDifficulty.HARD) {
                shoot = 2;
            }

            if (this.currentFlightTarget == null) {
                this.currentFlightTarget = new ChunkCoordinates((int) this.posX, (int) this.posY, (int) this.posZ);
            }

            if (this.stuck_count > 30 || this.worldObj.rand.nextInt(200) == 0
                || this.currentFlightTarget.getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ)
                    < 9.0F) {
                int down = 0;
                int dist = 20;

                for (int i = -5; i <= 5; i += 5) {
                    for (int j = -5; j <= 5; j += 5) {
                        for (int k = 1; k < 20; k++) {
                            Block bid = this.worldObj
                                .getBlock((int) this.posX + j, (int) this.posY - k, (int) this.posZ + i);
                            if (bid != Blocks.air) {
                                if (k < dist) {
                                    dist = k;
                                }
                                break;
                            }
                        }
                    }
                }

                if (dist > 10) {
                    down = dist - 10 + 1;
                }

                for (Block bid = Blocks.stone; bid != Blocks.air && keep_trying != 0; keep_trying--) {
                    int n = 1;
                    int n2 = 1;
                    if (this.worldObj.rand.nextInt(2) == 0) {
                        n = -1;
                    }

                    if (this.worldObj.rand.nextInt(2) == 0) {
                        n2 = -1;
                    }

                    int newz = this.rand.nextInt(20) + 8;
                    newz *= n2;
                    int newx = this.rand.nextInt(20) + 8;
                    newx *= n;
                    this.currentFlightTarget.set(
                        (int) this.posX + newx,
                        (int) this.posY + this.worldObj.rand.nextInt(7) - 1 - down,
                        (int) this.posZ + newz);
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

                this.stuck_count = 0;
            }

            if (this.worldObj.rand.nextInt(6) == 0) {
                EntityPlayer target = null;
                target = (EntityPlayer) this.worldObj
                    .findNearestEntityWithinAABB(EntityPlayer.class, this.boundingBox.expand(30.0, 20.0, 30.0), this);
                if (target != null) {
                    if (!target.capabilities.isCreativeMode) {
                        if (this.getEntitySenses()
                            .canSee(target)) {
                            this.currentFlightTarget.set((int) target.posX, (int) target.posY + 4, (int) target.posZ);
                            if (this.rand.nextInt(shoot) == 0) {
                                this.attackWithSomething(target);
                            }
                        }
                    } else {
                        target = null;
                    }
                }

                if (target == null && this.worldObj.rand.nextInt(3) == 0) {
                    EntityLivingBase e = null;
                    e = this.findSomethingToAttack();
                    if (e != null) {
                        this.currentFlightTarget.set((int) e.posX, (int) e.posY + 5, (int) e.posZ);
                        if (this.getDistanceSqToEntity(e) > 25.0) {
                            if (this.worldObj.rand.nextInt(shoot) == 0) {
                                this.attackWithSomething(e);
                            }
                        } else {
                            this.attackEntityAsMob(e);
                        }
                    }
                }
            }

            double dx = this.currentFlightTarget.posX + 0.5 - this.posX;
            double dy = this.currentFlightTarget.posY + 0.1 - this.posY;
            double dz = this.currentFlightTarget.posZ + 0.5 - this.posZ;
            this.motionX = this.motionX + (Math.signum(dx) * 0.5 - this.motionX) * 0.30001;
            this.motionY = this.motionY + (Math.signum(dy) * 0.7 - this.motionY) * 0.20001;
            this.motionZ = this.motionZ + (Math.signum(dz) * 0.5 - this.motionZ) * 0.30001;
            float f = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
            float f2 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
            this.moveForward = 1.0F;
            this.rotationYaw += f2 / 8.0F;
        }
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void fall(float distance) {}

    protected void updateFallState(double distanceFallen, boolean onGround) {}

    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = false;
        Entity e = source.getEntity();
        if (e != null && e instanceof Brutalfly) {
            return false;
        } else {
            ret = super.attackEntityFrom(source, amount);
            if (e != null && this.currentFlightTarget != null) {
                this.currentFlightTarget.set((int) e.posX, (int) e.posY + 2, (int) e.posZ);
            }

            return ret;
        }
    }

    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
    }

    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
    }

    public boolean getCanSpawnHere() {
        for (int k = -2; k <= 2; k++) {
            for (int j = -2; j <= 2; j++) {
                for (int i = 1; i < 4; i++) {
                    Block bid = this.worldObj.getBlock((int) this.posX + j, (int) this.posY + i, (int) this.posZ + k);
                    if (bid == Blocks.mob_spawner) {
                        TileEntityMobSpawner tileentitymobspawner = null;
                        tileentitymobspawner = (TileEntityMobSpawner) this.worldObj
                            .getTileEntity((int) this.posX + j, (int) this.posY + i, (int) this.posZ + k);
                        String s = tileentitymobspawner.func_145881_a()
                            .getEntityNameToSpawn();
                        if (s != null && s.equals("Brutalfly")) {
                            return true;
                        }
                    }
                }
            }
        }

        if (this.posY < 70.0) {
            return false;
        } else if (!this.isValidLightLevel()) {
            return false;
        } else if (this.worldObj.isDaytime()) {
            return false;
        } else {
            for (int i = -4; i < 4; i++) {
                for (int j = -3; j < 3; j++) {
                    for (int ix = 1; ix < 10; ix++) {
                        Block bid = this.worldObj
                            .getBlock((int) this.posX + j, (int) this.posY + ix, (int) this.posZ + i);
                        if (bid != Blocks.air) {
                            return false;
                        }
                    }
                }
            }

            Brutalfly target = null;
            target = (Brutalfly) this.worldObj
                .findNearestEntityWithinAABB(Brutalfly.class, this.boundingBox.expand(64.0, 32.0, 64.0), this);
            return target == null;
        }
    }

    public void initCreature() {}

    private void dropItemRand(Item index, int n) {
        EntityItem entityItem = new EntityItem(
            this.worldObj,
            this.posX + OreSpawnMain.OreSpawnRand.nextInt(8) - OreSpawnMain.OreSpawnRand.nextInt(8),
            this.posY + 1.0,
            this.posZ + OreSpawnMain.OreSpawnRand.nextInt(8) - OreSpawnMain.OreSpawnRand.nextInt(8),
            new ItemStack(index, n, 0));
        this.worldObj.spawnEntityInWorld(entityItem);
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        for (int i = 0; i < 20; i++) {
            float f = (this.rand.nextFloat() - 0.5F) * 8.0F;
            float f2 = (this.rand.nextFloat() - 0.5F) * 4.0F;
            float f3 = (this.rand.nextFloat() - 0.5F) * 8.0F;
            this.worldObj
                .spawnParticle("largeexplode", this.posX + f, this.posY + 2.0 + f2, this.posZ + f3, 0.0, 0.0, 0.0);
        }

        for (int i = 0; i < 53; i++) {
            this.dropItemRand(Items.gold_nugget, 1);
        }

        for (int i = 0; i < 20; i++) {
            spawnCreature(this.worldObj, "Butterfly", this.posX + 0.5, this.posY + 1.0, this.posZ + 0.5);
        }
    }

    public static Entity spawnCreature(World world, String str, double x, double y, double z) {
        Entity entity = null;
        if (world == null) {
            return null;
        } else {
            entity = EntityList.createEntityByName(str, world);
            if (entity != null) {
                entity.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0F, 0.0F);
                world.spawnEntityInWorld(entity);
                ((EntityLiving) entity).playLivingSound();
            }

            return entity;
        }
    }

    private void attackWithSomething(EntityLivingBase living) {
        double xzoff = 2.25;
        double yoff = 0.0;
        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
        if (this.worldObj.difficultySetting == EnumDifficulty.EASY) {
            EntitySmallFireball sf = new EntitySmallFireball(
                this.worldObj,
                this,
                living.posX - cx,
                living.posY + 0.55 - (this.posY + yoff),
                living.posZ - cz);
            sf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0F);
            sf.setPosition(cx, this.posY + yoff, cz);
            this.worldObj.playSoundAtEntity(
                this,
                "random.bow",
                0.75F,
                1.0F / (this.getRNG()
                    .nextFloat() * 0.4F + 0.8F));
            this.worldObj.spawnEntityInWorld(sf);
        } else if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL) {
            if (this.worldObj.rand.nextInt(2) == 0) {
                EntitySmallFireball sf = new EntitySmallFireball(
                    this.worldObj,
                    this,
                    living.posX - cx,
                    living.posY + 0.55 - (this.posY + yoff),
                    living.posZ - cz);
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
                BetterFireball bf = new BetterFireball(
                    this.worldObj,
                    this,
                    living.posX - cx,
                    living.posY + 0.55 - (this.posY + yoff),
                    living.posZ - cz);
                bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0F);
                bf.setPosition(cx, this.posY + yoff, cz);
                bf.setNotMe();
                this.worldObj.playSoundAtEntity(
                    this,
                    "random.fuse",
                    1.0F,
                    1.0F / (this.getRNG()
                        .nextFloat() * 0.4F + 0.8F));
                this.worldObj.spawnEntityInWorld(bf);
            }
        } else {
            BetterFireball bf = new BetterFireball(
                this.worldObj,
                this,
                living.posX - cx,
                living.posY + 0.55 - (this.posY + yoff),
                living.posZ - cz);
            bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0F);
            bf.setPosition(cx, this.posY + yoff, cz);
            bf.setNotMe();
            this.worldObj.playSoundAtEntity(
                this,
                "random.fuse",
                1.0F,
                1.0F / (this.getRNG()
                    .nextFloat() * 0.4F + 0.8F));
            this.worldObj.spawnEntityInWorld(bf);
        }

        if (this.getHealth() < this.mygetMaxHealth()) {
            this.heal(1.0F);
        }
    }

    private boolean isSuitableTarget(EntityLivingBase target, boolean checkSight) {
        if (target == null) {
            return false;
        } else if (target == this) {
            return false;
        } else if (!target.isEntityAlive()) {
            return false;
        } else if (target instanceof Brutalfly) {
            return false;
        } else if (target instanceof Mothra) {
            return false;
        } else if (target instanceof Vortex) {
            return false;
        } else if (MyUtils.isIgnoreable(target)) {
            return false;
        } else if (!this.getEntitySenses()
            .canSee(target)) {
                return false;
            } else if (target instanceof EntityMob) {
                return true;
            } else if (target instanceof EntityPlayer) {
                EntityPlayer p = (EntityPlayer) target;
                return !p.capabilities.isCreativeMode;
            } else {
                return false;
            }
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(25.0, 20.0, 25.0));
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
}
