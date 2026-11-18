package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraft.world.World;

import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.client.render.RenderInfo;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.ai.MyEntityAIWanderALot;

public class PitchBlack extends EntityMob {

    private ChunkCoordinates currentFlightTarget = null;
    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private float MyMoveSpeed = 0.2F;
    private int damage_ticker = 0;
    private int wing_sound = 0;

    public PitchBlack(World world) {
        super(world);
        this.setSize(2.0F, 3.0F);
        this.getNavigator()
            .setAvoidsWater(false);
        this.experienceValue = 200;
        this.isImmuneToFire = false;
        this.fireResistance = 25;
        this.TargetSorter = new GenericTargetSorter(this);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMoveThroughVillage(this, 1.0, false));
        this.tasks.addTask(2, new MyEntityAIWanderALot(this, 16, 1.0));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.MyMoveSpeed = 0.2F;
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.MyMoveSpeed + 0.1F * this.getPitchBlackScale());
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(this.getPitchBlackScale() * OreSpawnMain.PitchBlack_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
        this.dataWatcher.addObject(21, (byte) 0);
        this.dataWatcher.addObject(22, 0);
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
        float t = 0.5F;
        if (this.worldObj != null) {
            if (this.worldObj.rand.nextInt(4) == 1) {
                t = 1.0F;
            }

            if (this.worldObj.rand.nextInt(8) == 2) {
                t = 2.0F;
            }

            if (this.worldObj.rand.nextInt(32) == 3) {
                t = 3.0F;
            }

            if (this.worldObj.rand.nextInt(64) == 4) {
                t = 4.0F;
            }
        } else {
            if (OreSpawnMain.OreSpawnRand.nextInt(4) == 1) {
                t = 1.0F;
            }

            if (OreSpawnMain.OreSpawnRand.nextInt(8) == 2) {
                t = 2.0F;
            }

            if (OreSpawnMain.OreSpawnRand.nextInt(32) == 3) {
                t = 3.0F;
            }

            if (OreSpawnMain.OreSpawnRand.nextInt(64) == 4) {
                t = 4.0F;
            }
        }

        if (OreSpawnMain.NightmareSize == 1) {
            t = 0.5F;
        }

        if (OreSpawnMain.NightmareSize == 2) {
            t = 1.0F;
        }

        if (OreSpawnMain.NightmareSize == 3) {
            t = 2.0F;
        }

        if (OreSpawnMain.NightmareSize == 4) {
            t = 3.0F;
        }

        if (OreSpawnMain.NightmareSize == 5) {
            t = 4.0F;
        }

        this.setPitchBlackScale(t);
        this.experienceValue = (int) (100.0F * t);
        this.fireResistance = (int) (25.0F * t);
        this.setSize(2.5F * this.getPitchBlackScale(), 3.5F * this.getPitchBlackScale());
    }

    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        this.setPitchBlackScale(tag.getFloat("Fscale"));
        this.setSize(2.5F * this.getPitchBlackScale(), 3.5F * this.getPitchBlackScale());
        this.experienceValue = (int) (100.0F * this.getPitchBlackScale());
        this.fireResistance = (int) (25.0F * this.getPitchBlackScale());
    }

    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setFloat("Fscale", this.getPitchBlackScale());
    }

    public final int getAttacking() {
        return this.dataWatcher.getWatchableObjectByte(20);
    }

    public final void setAttacking(int attacking) {
        this.dataWatcher.updateObject(20, (byte) attacking);
    }

    public final int getActivity() {
        return this.dataWatcher.getWatchableObjectByte(21);
    }

    public final void setActivity(int activity) {
        this.dataWatcher.updateObject(21, (byte) activity);
    }

    public float getPitchBlackScale() {
        int i = this.dataWatcher.getWatchableObjectInt(22);
        float f = i;
        return f / 10.0F;
    }

    public void setPitchBlackScale(float f2) {
        float f = f2 * 10.0001F;
        int i = (int) f;
        this.dataWatcher.updateObject(22, i);
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.PitchBlack_stats.defense + (int) (2.0F * this.getPitchBlackScale());
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

    protected boolean canDespawn() {
        return this.isNoDespawnRequired() ? false : this.worldObj.isDaytime();
    }

    protected float getSoundVolume() {
        return 0.75F;
    }

    protected float getSoundPitch() {
        return 1.0F - 0.7F * (4.0F / this.getPitchBlackScale());
    }

    protected String getLivingSound() {
        return this.worldObj.rand.nextInt(5) != 2 ? null : "orespawn:pitchblack_living";
    }

    protected String getHurtSound() {
        return "orespawn:pitchblack_hit";
    }

    protected String getDeathSound() {
        return "orespawn:pitchblack_dead";
    }

    public int mygetMaxHealth() {
        return (int) (OreSpawnMain.PitchBlack_stats.health * this.getPitchBlackScale());
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onUpdate() {
        this.MyMoveSpeed = 0.2F;
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.MyMoveSpeed + 0.1F * this.getPitchBlackScale());
        super.onUpdate();
        this.setSize(2.5F * this.getPitchBlackScale(), 3.5F * this.getPitchBlackScale());
        this.wing_sound++;
        if (this.wing_sound > 20) {
            if (!this.worldObj.isRemote) {
                this.worldObj.playSoundAtEntity(this, "orespawn:MothraWings", 1.0F, 1.0F);
            }

            this.wing_sound = 0;
        }

        this.motionY *= 0.6;
        if (!this.worldObj.isRemote && this.worldObj.rand.nextInt(250) == 1) {
            this.heal(1.0F + this.getPitchBlackScale());
            if (this.worldObj.rand.nextInt(5) == 0) {
                Block bid = Blocks.air;
                if (this.posY > 10.0) {
                    for (int i = 0; i < 10; i++) {
                        bid = this.worldObj.getBlock((int) this.posX, (int) this.posY - i, (int) this.posZ);
                        if (bid != Blocks.air) {
                            break;
                        }
                    }
                } else {
                    bid = Blocks.stone;
                }

                if (bid != Blocks.air) {
                    Entity e = null;
                    e = this.findSomethingToAttack();
                    if (e == null) {
                        this.setActivity(0);
                    }
                }
            } else {
                this.setActivity(1);
                this.getNavigator()
                    .setPath(null, 0.0);
            }
        }

        if (this.getActivity() == 0 && this.worldObj.rand.nextInt(10) == 1) {
            Entity e = null;
            e = this.findSomethingToAttack();
            if (e != null) {
                this.setActivity(1);
                this.getNavigator()
                    .setPath(null, 0.0);
            }
        }
    }

    public boolean attackEntityAsMob(Entity target) {
        boolean flag = false;
        if (target != null && target instanceof EntityDragon) {
            EntityDragon dr = (EntityDragon) target;
            DamageSource source = null;
            source = DamageSource.setExplosionSource(null);
            source.setExplosion();
            if (this.worldObj.rand.nextInt(8) == 1) {
                dr.attackEntityFromPart(
                    dr.dragonPartHead,
                    source,
                    OreSpawnMain.PitchBlack_stats.attack * this.getPitchBlackScale());
            } else {
                dr.attackEntityFromPart(
                    dr.dragonPartBody,
                    source,
                    OreSpawnMain.PitchBlack_stats.attack * this.getPitchBlackScale());
            }

            flag = true;
        } else {
            flag = target.attackEntityFrom(
                DamageSource.causeMobDamage(this),
                OreSpawnMain.PitchBlack_stats.attack * this.getPitchBlackScale());
            if (flag && target != null && target instanceof EntityLivingBase) {
                double ks = 1.15 * this.getPitchBlackScale();
                double inair = 0.08 * this.getPitchBlackScale();
                float f3 = (float) Math.atan2(target.posZ - this.posZ, target.posX - this.posX);
                if (target.isDead || target instanceof EntityPlayer) {
                    inair *= 2.0;
                }

                target.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
        }

        return flag;
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
        int keep_trying = 50;
        if (this.damage_ticker > 0) {
            this.damage_ticker--;
        }

        if (this.getActivity() == 0) {
            super.updateAITasks();
        } else if (!this.isDead) {
            if (this.currentFlightTarget == null) {
                this.currentFlightTarget = new ChunkCoordinates((int) this.posX, (int) this.posY, (int) this.posZ);
            }

            if (this.getActivity() != 0) {
                if (this.rand.nextInt(150) != 0
                    && !(this.currentFlightTarget.getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ)
                        < 2.1F)) {
                    if (this.rand.nextInt(8) == 0) {
                        Entity e = null;
                        e = this.findSomethingToAttack();
                        if (e != null) {
                            double d1 = 5.0 + e.width / 2.0F;
                            d1 += this.getPitchBlackScale();
                            d1 *= d1;
                            this.setAttacking(1);
                            if (e instanceof EntityDragon && d1 < 100.0) {
                                d1 = 100.0;
                            }

                            if (e instanceof Godzilla && d1 < 100.0) {
                                d1 = 100.0;
                            }

                            if (e instanceof GodzillaHead && d1 < 100.0) {
                                d1 = 100.0;
                            }

                            this.currentFlightTarget.set((int) e.posX, (int) (e.posY + 2.0), (int) e.posZ);
                            if (this.getDistanceSqToEntity(e) < d1) {
                                this.attackEntityAsMob(e);
                            }
                        } else {
                            this.setAttacking(0);
                        }
                    }
                } else {
                    for (Block bid = Blocks.stone; bid != Blocks.air && keep_trying > 0; keep_trying--) {
                        zdir = this.rand.nextInt(20) + 5 * (int) this.getPitchBlackScale();
                        xdir = this.rand.nextInt(20) + 5 * (int) this.getPitchBlackScale();
                        if (this.rand.nextInt(2) == 0) {
                            zdir = -zdir;
                        }

                        if (this.rand.nextInt(2) == 0) {
                            xdir = -xdir;
                        }

                        this.currentFlightTarget.set(
                            (int) this.posX + xdir,
                            (int) this.posY + this.rand.nextInt(11) - 5,
                            (int) this.posZ + zdir);
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

                double dx = this.currentFlightTarget.posX + 0.4 - this.posX;
                double dy = this.currentFlightTarget.posY + 0.1 - this.posY;
                double dz = this.currentFlightTarget.posZ + 0.4 - this.posZ;
                double myspeed = 0.5F + this.getPitchBlackScale() / 10.0F;
                this.motionX = this.motionX + (Math.signum(dx) * myspeed - this.motionX) * 0.33;
                this.motionY = this.motionY + (Math.signum(dy) * 0.7F - this.motionY) * 0.20000000149011612;
                this.motionZ = this.motionZ + (Math.signum(dz) * myspeed - this.motionZ) * 0.33;
                float f = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
                float f2 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
                this.moveForward = 0.1F + (float) myspeed;
                this.rotationYaw += f2 / 5.0F;
            }
        }
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    protected void fall(float distance) {}

    protected void updateFallState(double distanceFallen, boolean onGround) {}

    public boolean doesEntityNotTriggerPressurePlate() {
        return false;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = false;
        if (this.damage_ticker > 0) {
            return ret;
        } else {
            this.damage_ticker = 20;
            ret = super.attackEntityFrom(source, amount);
            Entity e = source.getEntity();
            if (e != null && this.currentFlightTarget != null) {
                this.currentFlightTarget.set((int) e.posX, (int) (e.posY + 2.0), (int) e.posZ);
            }

            this.setActivity(1);
            this.getNavigator()
                .setPath(null, 0.0);
            return ret;
        }
    }

    public boolean getCanSpawnHere() {
        for (int k = -3; k < 3; k++) {
            for (int j = -3; j < 3; j++) {
                for (int i = 0; i < 5; i++) {
                    Block bid = this.worldObj.getBlock((int) this.posX + j, (int) this.posY + i, (int) this.posZ + k);
                    if (bid == Blocks.mob_spawner) {
                        TileEntityMobSpawner tileentitymobspawner = null;
                        tileentitymobspawner = (TileEntityMobSpawner) this.worldObj
                            .getTileEntity((int) this.posX + j, (int) this.posY + i, (int) this.posZ + k);
                        String s = tileentitymobspawner.func_145881_a()
                            .getEntityNameToSpawn();
                        if (s != null && s.equals("Nightmare")) {
                            Float t = this.getPitchBlackScale();
                            if (t > 1.0F) {
                                t = 1.0F;
                            }

                            this.setPitchBlackScale(t);
                            return true;
                        }
                    }
                }
            }
        }

        if (!this.isValidLightLevel()) {
            return false;
        } else if (this.worldObj.isDaytime()) {
            return false;
        } else {
            if (this.worldObj.provider.dimensionId == OreSpawnMain.DimensionID6) {
                PitchBlack target = null;
                target = (PitchBlack) this.worldObj
                    .findNearestEntityWithinAABB(PitchBlack.class, this.boundingBox.expand(16.0, 16.0, 16.0), this);
                if (target != null) {
                    return false;
                }
            }

            if (this.getPitchBlackScale() < 1.1F) {
                return true;
            } else {
                int ix = 1;
                if (this.getPitchBlackScale() > 3.1F) {
                    ix = 2;
                }

                int iy = ix * 3;

                for (int i = -ix; i <= ix; i++) {
                    for (int j = -ix; j <= ix; j++) {
                        for (int ixx = 1; ixx <= iy; ixx++) {
                            Block bid = this.worldObj
                                .getBlock((int) this.posX + j, (int) this.posY + ixx, (int) this.posZ + i);
                            if (bid != Blocks.air) {
                                return false;
                            }
                        }
                    }
                }

                return true;
            }
        }
    }

    private boolean isSuitableTarget(EntityLivingBase target, boolean checkSight) {
        if (target == null) {
            return false;
        } else if (target == this) {
            return false;
        } else if (!(target instanceof EntityLivingBase)) {
            return false;
        } else if (!target.isEntityAlive()) {
            return false;
        } else if (MyUtils.isIgnoreable(target)) {
            return false;
        } else if (!this.getEntitySenses()
            .canSee(target)) {
                return false;
            } else if (target instanceof PitchBlack) {
                return false;
            } else if (target instanceof EnderReaper) {
                return false;
            } else if (target instanceof LeafMonster) {
                return false;
            } else if (target instanceof TerribleTerror) {
                return false;
            } else if (target instanceof LurkingTerror) {
                return false;
            } else if (target instanceof CreepingHorror) {
                return false;
            } else if (target instanceof Island) {
                return false;
            } else if (target instanceof IslandToo) {
                return false;
            } else if (target instanceof Triffid) {
                return false;
            } else {
                if (target instanceof EntityPlayer) {
                    EntityPlayer p = (EntityPlayer) target;
                    if (p.capabilities.isCreativeMode) {
                        return false;
                    }
                }

                return true;
            }
    }

    private Entity findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            double d1 = 16.0 + this.getPitchBlackScale() * 6.0F;
            double d2 = 10.0 + this.getPitchBlackScale() * 4.0F;
            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(d1, d2, d1));
            Collections.sort(entities, this.TargetSorter);
            Iterator iterator = entities.iterator();
            EntityLivingBase target = null;

            while (iterator.hasNext()) {
                target = (EntityLivingBase) iterator.next();
                if (this.isSuitableTarget(target, false)) {
                    return target;
                }
            }

            return null;
        }
    }

    protected Item getDropItem() {
        return OreSpawnMain.MyNightmareScale;
    }

    private ItemStack dropItemRand(Item index, int n) {
        EntityItem entityItem = null;
        ItemStack is = new ItemStack(index, n, 0);
        entityItem = new EntityItem(
            this.worldObj,
            this.posX + OreSpawnMain.OreSpawnRand.nextInt(5) * this.getPitchBlackScale()
                - OreSpawnMain.OreSpawnRand.nextInt(5) * this.getPitchBlackScale(),
            this.posY + 1.0,
            this.posZ + OreSpawnMain.OreSpawnRand.nextInt(5) * this.getPitchBlackScale()
                - OreSpawnMain.OreSpawnRand.nextInt(5) * this.getPitchBlackScale(),
            is);
        if (entityItem != null) {
            this.worldObj.spawnEntityInWorld(entityItem);
        }

        return is;
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        int i = 3 + this.worldObj.rand.nextInt(2 + (int) (5.0F * this.getPitchBlackScale()));

        for (int i2 = 0; i2 < i; i2++) {
            this.dropItemRand(Items.rotten_flesh, 1);
            int j = this.worldObj.rand.nextInt(10);
            if (j == 0) {
                this.dropItemRand(Items.feather, 1);
            }

            if (j == 1) {
                this.dropItemRand(Items.string, 1);
            }

            if (j == 2) {
                this.dropItemRand(Items.flint, 1);
            }

            if (j == 3) {
                this.dropItemRand(Items.beef, 1);
            }
        }

        this.dropItemRand(OreSpawnMain.MyNightmareScale, 1);
        this.dropItemRand(Items.item_frame, 1);
        i = 2 + (int) this.getPitchBlackScale()
            + this.worldObj.rand.nextInt(2 + (int) (5.0F * this.getPitchBlackScale()));

        for (int i2 = 0; i2 < i; i2++) {
            this.dropItemRand(OreSpawnMain.ZooKeeper, 1);
        }
    }
}
