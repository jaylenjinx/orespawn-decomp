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
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.GenericTargetSorter;

public class CloudShark extends EntityMob {

    private ChunkCoordinates currentFlightTarget = null;
    private GenericTargetSorter TargetSorter = null;

    public CloudShark(World world) {
        super(world);
        this.setSize(1.0F, 0.75F);
        this.experienceValue = 5;
        this.isImmuneToFire = false;
        this.fireResistance = 5;
        this.TargetSorter = new GenericTargetSorter(this);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(0.3F);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(OreSpawnMain.CloudShark_stats.attack);
    }

    public boolean attackEntityAsMob(Entity target) {
        float f = (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .getAttributeValue();
        return target.attackEntityFrom(DamageSource.causeMobDamage(this), f);
    }

    protected boolean canDespawn() {
        return this.isNoDespawnRequired() ? false : !this.worldObj.isDaytime();
    }

    protected float getSoundVolume() {
        return 0.25F;
    }

    protected float getSoundPitch() {
        return 1.0F;
    }

    protected String getLivingSound() {
        return "splash";
    }

    protected String getHurtSound() {
        return "orespawn:little_splat";
    }

    protected String getDeathSound() {
        return "orespawn:big_splat";
    }

    public boolean canBePushed() {
        return true;
    }

    protected void collideWithEntity(Entity entity) {}

    public int mygetMaxHealth() {
        return OreSpawnMain.CloudShark_stats.health;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onUpdate() {
        super.onUpdate();
        this.motionY *= 0.6;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.CloudShark_stats.defense;
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
        int updown = 0;
        if (!this.isDead) {
            super.updateAITasks();
            if (this.currentFlightTarget == null) {
                this.currentFlightTarget = new ChunkCoordinates((int) this.posX, (int) this.posY, (int) this.posZ);
            }

            if ((int) this.posY < 120) {
                updown = 2;
            }

            if ((int) this.posY > 140) {
                updown = -2;
            }

            if (this.rand.nextInt(300) == 0
                || this.currentFlightTarget.getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ)
                    < 2.1F) {
                for (Block bid = Blocks.stone; bid != Blocks.air && keep_trying != 0; keep_trying--) {
                    zdir = this.rand.nextInt(10) + 8;
                    xdir = this.rand.nextInt(10) + 8;
                    if (this.rand.nextInt(2) == 0) {
                        zdir = -zdir;
                    }

                    if (this.rand.nextInt(2) == 0) {
                        xdir = -xdir;
                    }

                    this.currentFlightTarget.set(
                        (int) this.posX + xdir,
                        (int) this.posY + this.rand.nextInt(5) - 2 + updown,
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

            if (this.rand.nextInt(9) == 2) {
                EntityLivingBase e = null;
                e = this.findSomethingToAttack();
                if (e != null) {
                    this.currentFlightTarget.set((int) e.posX, (int) e.posY, (int) e.posZ);
                    if (this.getDistanceSqToEntity(e) < 9.0) {
                        this.attackEntityAsMob(e);
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
        return true;
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
            } else if (target instanceof RockBase) {
                return false;
            } else if (target instanceof EntityAnt) {
                return false;
            } else if (target instanceof EntityButterfly) {
                return true;
            } else if (target instanceof Cockateil) {
                return true;
            } else if (target instanceof EntityMosquito) {
                return true;
            } else if (target instanceof Firefly) {
                return true;
            } else {
                if (target instanceof EntityPlayer) {
                    EntityPlayer p = (EntityPlayer) target;
                    if (!p.capabilities.isCreativeMode) {
                        return true;
                    }
                }

                return target instanceof GoldFish ? true : target instanceof CliffRacer;
            }
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(12.0, 10.0, 12.0));
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
            return Items.paper;
        } else if (i == 1) {
            return Items.string;
        } else {
            return i == 2 ? Items.bone : null;
        }
    }

    public boolean canBreatheUnderwater() {
        return true;
    }
}
