package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.client.render.RenderInfo;
import danger.orespawn.entity.ai.GenericTargetSorter;

public class LurkingTerror extends EntityMob {

    private ChunkCoordinates currentFlightTarget = null;
    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();

    public LurkingTerror(World world) {
        super(world);
        this.setSize(1.75F, 1.25F);
        this.getNavigator()
            .setAvoidsWater(false);
        this.experienceValue = 20;
        this.isImmuneToFire = false;
        this.fireResistance = 5;
        this.TargetSorter = new GenericTargetSorter(this);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(0.25);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(OreSpawnMain.LurkingTerror_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
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

    protected boolean canDespawn() {
        return this.isNoDespawnRequired() ? false : this.getAttacking() == 0;
    }

    public int getAttacking() {
        return this.dataWatcher.getWatchableObjectByte(20);
    }

    public void setAttacking(int attacking) {
        this.dataWatcher.updateObject(20, (byte) attacking);
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

    protected float getSoundVolume() {
        return 0.55F;
    }

    protected float getSoundPitch() {
        return 1.0F;
    }

    protected String getLivingSound() {
        return "orespawn:lurkinghorror_living";
    }

    protected String getHurtSound() {
        return "orespawn:lurkinghorror_hit";
    }

    protected String getDeathSound() {
        return "orespawn:lurkinghorror_dead";
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.LurkingTerror_stats.health;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.LurkingTerror_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onUpdate() {
        super.onUpdate();
        this.motionY *= 0.6;
    }

    public boolean attackEntityAsMob(Entity target) {
        return target.attackEntityFrom(DamageSource.causeMobDamage(this), 5.0F);
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
        if (!this.isDead) {
            super.updateAITasks();
            if (this.currentFlightTarget == null) {
                this.currentFlightTarget = new ChunkCoordinates((int) this.posX, (int) this.posY, (int) this.posZ);
            }

            if (this.rand.nextInt(120) != 0
                && !(this.currentFlightTarget.getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ)
                    < 2.1F)) {
                if (this.rand.nextInt(9) == 0) {
                    EntityLivingBase e = null;
                    e = this.findSomethingToAttack();
                    if (e != null) {
                        this.setAttacking(1);
                        this.currentFlightTarget.set((int) e.posX, (int) (e.posY + 1.0), (int) e.posZ);
                        if (this.getDistanceSqToEntity(e) < 6.0) {
                            this.attackEntityAsMob(e);
                        }
                    } else {
                        this.setAttacking(0);
                    }
                }
            } else {
                for (Block bid = Blocks.stone; bid != Blocks.air && keep_trying != 0; keep_trying--) {
                    zdir = this.rand.nextInt(10) + 2;
                    xdir = this.rand.nextInt(10) + 2;
                    if (this.rand.nextInt(2) == 0) {
                        zdir = -zdir;
                    }

                    if (this.rand.nextInt(2) == 0) {
                        xdir = -xdir;
                    }

                    this.currentFlightTarget.set(
                        (int) this.posX + xdir,
                        (int) this.posY + this.rand.nextInt(5) - 2,
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
            this.motionX = this.motionX + (Math.signum(dx) * 0.4 - this.motionX) * 0.30000000149011613;
            this.motionY = this.motionY + (Math.signum(dy) * 0.7F - this.motionY) * 0.20000000149011612;
            this.motionZ = this.motionZ + (Math.signum(dz) * 0.4 - this.motionZ) * 0.30000000149011613;
            float f = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
            float f2 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
            this.moveForward = 0.75F;
            this.rotationYaw += f2 / 4.0F;
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
        boolean ret = super.attackEntityFrom(source, amount);
        Entity e = source.getEntity();
        if (e != null && this.currentFlightTarget != null) {
            this.currentFlightTarget.set((int) e.posX, (int) e.posY, (int) e.posZ);
        }

        return ret;
    }

    public boolean getCanSpawnHere() {
        LurkingTerror target = null;

        for (int k = -2; k < 2; k++) {
            for (int j = -2; j < 2; j++) {
                for (int i = 0; i < 5; i++) {
                    Block bid = this.worldObj.getBlock((int) this.posX + j, (int) this.posY + i, (int) this.posZ + k);
                    if (bid == Blocks.mob_spawner) {
                        TileEntityMobSpawner tileentitymobspawner = null;
                        tileentitymobspawner = (TileEntityMobSpawner) this.worldObj
                            .getTileEntity((int) this.posX + j, (int) this.posY + i, (int) this.posZ + k);
                        String s = tileentitymobspawner.func_145881_a()
                            .getEntityNameToSpawn();
                        if (s != null && s.equals("Lurking Terror")) {
                            return true;
                        }
                    }
                }
            }
        }

        if (!this.isValidLightLevel()) {
            return false;
        } else if (!this.worldObj.isDaytime()) {
            return false;
        } else if (this.worldObj.rand.nextInt(2) != 1) {
            return false;
        } else
            if (this.worldObj.provider.dimensionId == OreSpawnMain.DimensionID6 && this.worldObj.rand.nextInt(6) != 0) {
                return false;
            } else {
                target = (LurkingTerror) this.worldObj
                    .findNearestEntityWithinAABB(LurkingTerror.class, this.boundingBox.expand(32.0, 16.0, 32.0), this);
                return target != null ? false : !(this.posY < 10.0);
            }
    }

    private boolean isSuitableTarget(EntityLivingBase target, boolean checkSight) {
        if (target == null) {
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
            } else if (target instanceof RockBase) {
                return false;
            } else if (target instanceof EnderReaper) {
                return false;
            } else if (target instanceof LeafMonster) {
                return false;
            } else if (target instanceof TerribleTerror) {
                return false;
            } else if (target instanceof Mothra) {
                return false;
            } else if (target instanceof CloudShark) {
                return false;
            } else if (target instanceof Rotator) {
                return false;
            } else if (target instanceof Bee) {
                return false;
            } else if (target instanceof Mantis) {
                return false;
            } else if (target instanceof CreepingHorror) {
                return false;
            } else if (target instanceof Triffid) {
                return false;
            } else if (target instanceof PitchBlack) {
                return false;
            } else if (target instanceof Dragon) {
                return false;
            } else if (target instanceof Island) {
                return false;
            } else if (target instanceof IslandToo) {
                return false;
            } else if (target instanceof EntityButterfly) {
                return false;
            } else if (target instanceof Firefly) {
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

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(12.0, 8.0, 12.0));
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

    protected Item getDropItem() {
        int i = this.worldObj.rand.nextInt(3);
        if (i == 0) {
            return Items.beef;
        } else {
            return i == 1 ? Items.flint : Items.feather;
        }
    }
}
