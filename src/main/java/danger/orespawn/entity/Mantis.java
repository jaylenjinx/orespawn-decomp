package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.GenericTargetSorter;

public class Mantis extends EntityMob {

    private ChunkCoordinates currentFlightTarget = null;
    private GenericTargetSorter TargetSorter = null;
    private int stuck_count = 0;
    private int lastX = 0;
    private int lastZ = 0;
    private Entity rt = null;

    public Mantis(World world) {
        super(world);
        this.setSize(2.5F, 3.25F);
        this.getNavigator()
            .setAvoidsWater(false);
        this.experienceValue = 100;
        this.isImmuneToFire = false;
        this.fireResistance = 5;
        this.TargetSorter = new GenericTargetSorter(this);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(0.32F);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(OreSpawnMain.Mantis_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public final int getAttacking() {
        return this.dataWatcher.getWatchableObjectByte(20);
    }

    public final void setAttacking(int attacking) {
        this.dataWatcher.updateObject(20, (byte) attacking);
    }

    protected float getSoundVolume() {
        return 0.35F;
    }

    protected float getSoundPitch() {
        return 1.0F;
    }

    protected String getLivingSound() {
        return "orespawn:Beebuzz";
    }

    protected String getHurtSound() {
        return "orespawn:dragonfly_hurt";
    }

    protected String getDeathSound() {
        return "orespawn:alo_death";
    }

    public boolean canBePushed() {
        return true;
    }

    protected void collideWithEntity(Entity entity) {}

    public int mygetMaxHealth() {
        return OreSpawnMain.Mantis_stats.health;
    }

    protected Item getDropItem() {
        return Item.getItemFromBlock(Blocks.yellow_flower);
    }

    private void dropItemRand(Item index, int n) {
        EntityItem entityItem = new EntityItem(
            this.worldObj,
            this.posX + OreSpawnMain.OreSpawnRand.nextInt(5) - OreSpawnMain.OreSpawnRand.nextInt(5),
            this.posY + 1.0,
            this.posZ + OreSpawnMain.OreSpawnRand.nextInt(5) - OreSpawnMain.OreSpawnRand.nextInt(5),
            new ItemStack(index, n, 0));
        this.worldObj.spawnEntityInWorld(entityItem);
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        this.dropItemRand(OreSpawnMain.MyMantisClaw, 1);
        this.dropItemRand(OreSpawnMain.MyMantisClaw, 1);
        this.dropItemRand(Items.item_frame, 1);
        int n = 2 + this.worldObj.rand.nextInt(10);

        for (int i = 0; i < n; i++) {
            this.dropItemRand(Items.gold_nugget, 1);
        }

        n = 1 + this.worldObj.rand.nextInt(3);

        for (int i = 0; i < n; i++) {
            this.dropItemRand(OreSpawnMain.UraniumNugget, 1);
        }

        n = 1 + this.worldObj.rand.nextInt(3);

        for (int i = 0; i < n; i++) {
            this.dropItemRand(OreSpawnMain.TitaniumNugget, 1);
        }

        n = 2 + this.worldObj.rand.nextInt(3);

        for (int i = 0; i < n; i++) {
            this.dropItemRand(Items.diamond, 1);
        }
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onUpdate() {
        super.onUpdate();
        this.motionY *= 0.6;
        if (this.isInWater() && this.worldObj.rand.nextInt(20) == 1) {
            this.attackEntityAsMob(this);
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
        int keep_trying = 50;
        if (!this.isDead) {
            super.updateAITasks();
            if (this.lastX == (int) this.posX && this.lastZ == (int) this.posZ) {
                this.stuck_count++;
            } else {
                this.stuck_count = 0;
                this.lastX = (int) this.posX;
                this.lastZ = (int) this.posZ;
            }

            if (this.currentFlightTarget == null) {
                this.currentFlightTarget = new ChunkCoordinates((int) this.posX, (int) this.posY, (int) this.posZ);
            }

            if (this.stuck_count <= 50 && this.rand.nextInt(300) != 0
                && !(this.currentFlightTarget.getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ)
                    < 2.1F)) {
                if (this.rand.nextInt(8) == 0) {
                    EntityLivingBase e = null;
                    e = (EntityLivingBase) this.rt;
                    if (e != null && e.isDead) {
                        e = null;
                    }

                    if (e == null) {
                        e = this.findSomethingToAttack();
                    }

                    if (e != null) {
                        this.setAttacking(1);
                        this.currentFlightTarget.set((int) e.posX, (int) e.posY + 1, (int) e.posZ);
                        if (this.getDistanceSqToEntity(e) < (5.0F + e.width / 2.0F) * (5.0F + e.width / 2.0F)) {
                            this.attackEntityAsMob(e);
                        }
                    } else {
                        this.setAttacking(0);
                    }
                }
            } else {
                Block bid = Blocks.stone;

                for (this.stuck_count = 0; bid != Blocks.air && keep_trying != 0; keep_trying--) {
                    zdir = this.rand.nextInt(9) + 4;
                    xdir = this.rand.nextInt(9) + 4;
                    if (this.rand.nextInt(2) == 0) {
                        zdir = -zdir;
                    }

                    if (this.rand.nextInt(2) == 0) {
                        xdir = -xdir;
                    }

                    this.currentFlightTarget.set(
                        (int) this.posX + xdir,
                        (int) this.posY + this.rand.nextInt(6) - 3,
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

            double dx = this.currentFlightTarget.posX + 0.5 - this.posX;
            double dy = this.currentFlightTarget.posY + 0.1 - this.posY;
            double dz = this.currentFlightTarget.posZ + 0.5 - this.posZ;
            this.motionX = this.motionX + (Math.signum(dx) * 0.5 - this.motionX) * 0.30000000149011613;
            this.motionY = this.motionY + (Math.signum(dy) * 0.7F - this.motionY) * 0.20000000149011612;
            this.motionZ = this.motionZ + (Math.signum(dz) * 0.5 - this.motionZ) * 0.30000000149011613;
            float f = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
            float f2 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
            this.moveForward = 1.0F;
            this.rotationYaw += f2 / 4.0F;
            if (this.worldObj.rand.nextInt(100) == 1) {
                this.heal(1.0F);
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
        boolean ret = super.attackEntityFrom(source, amount);
        Entity e = source.getEntity();
        if (e != null && e instanceof EntityLivingBase && this.currentFlightTarget != null) {
            this.rt = e;
            this.currentFlightTarget.set((int) e.posX, (int) e.posY, (int) e.posZ);
        }

        return ret;
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
                        if (s != null && s.equals("Mantis")) {
                            return true;
                        }
                    }
                }
            }
        }

        for (int i = -2; i < 2; i++) {
            for (int j = -2; j < 2; j++) {
                for (int ix = 1; ix < 6; ix++) {
                    Block bid = this.worldObj.getBlock((int) this.posX + j, (int) this.posY + ix, (int) this.posZ + i);
                    if (bid != Blocks.air) {
                        return false;
                    }
                }
            }
        }

        if (this.worldObj.provider.dimensionId == OreSpawnMain.DimensionID6 && this.worldObj.rand.nextInt(6) != 0) {
            return false;
        } else if (this.posY < 50.0) {
            return false;
        } else if (!this.worldObj.isDaytime()) {
            return false;
        } else {
            Mantis target = null;
            target = (Mantis) this.worldObj
                .findNearestEntityWithinAABB(Mantis.class, this.boundingBox.expand(32.0, 16.0, 32.0), this);
            return target == null;
        }
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.Mantis_stats.defense;
    }

    public void initCreature() {}

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
            } else if (target.isInWater()) {
                return false;
            } else if (target instanceof EntityPlayer) {
                EntityPlayer p = (EntityPlayer) target;
                return !p.capabilities.isCreativeMode;
            } else if (target instanceof Mantis) {
                return false;
            } else if (target instanceof Irukandji) {
                return false;
            } else if (target instanceof Skate) {
                return false;
            } else if (target instanceof Flounder) {
                return false;
            } else if (target instanceof Whale) {
                return false;
            } else if (target instanceof EntitySquid) {
                return false;
            } else if (target instanceof WaterDragon) {
                return false;
            } else if (target instanceof AttackSquid) {
                return false;
            } else if (target instanceof TerribleTerror) {
                return false;
            } else if (target instanceof LurkingTerror) {
                return false;
            } else if (target instanceof CloudShark) {
                return false;
            } else if (target instanceof Rotator) {
                return false;
            } else if (target instanceof Bee) {
                return false;
            } else if (target instanceof Mothra) {
                return false;
            } else if (target instanceof EntityMob) {
                return true;
            } else if (target instanceof EntityButterfly) {
                return true;
            } else if (target instanceof Cockateil) {
                return true;
            } else if (target instanceof Fairy) {
                return true;
            } else {
                if (target instanceof EntityPlayer) {
                    EntityPlayer p = (EntityPlayer) target;
                    if (!p.capabilities.isCreativeMode) {
                        return true;
                    }
                }

                return MyUtils.isAttackableNonMob(target);
            }
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(16.0, 8.0, 16.0));
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
