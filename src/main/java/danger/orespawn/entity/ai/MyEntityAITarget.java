package danger.orespawn.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.Mothra;

public abstract class MyEntityAITarget extends EntityAIBase {

    protected EntityLiving taskOwner;
    protected float targetDistance;
    protected boolean shouldCheckSight;
    private boolean nearbyOnly;
    private int targetSearchStatus = 0;
    private int targetSearchDelay = 0;
    private int field_75298_g = 0;

    public MyEntityAITarget(EntityLiving living, float f, boolean flag) {
        this(living, f, flag, false);
    }

    public MyEntityAITarget(EntityLiving living, float targetDistance2, boolean shouldCheckSight2,
        boolean nearbyOnly2) {
        this.taskOwner = living;
        this.targetDistance = targetDistance2;
        this.shouldCheckSight = shouldCheckSight2;
        this.nearbyOnly = nearbyOnly2;
    }

    public boolean continueExecuting() {
        EntityLivingBase attackTarget = this.taskOwner.getAttackTarget();
        if (attackTarget == null) {
            return false;
        } else if (!attackTarget.isEntityAlive()) {
            this.taskOwner.setAttackTarget(null);
            return false;
        } else if (this.taskOwner.getDistanceSqToEntity(attackTarget) > this.targetDistance * this.targetDistance) {
            return false;
        } else if (this.taskOwner instanceof EntityTameable && ((EntityTameable) this.taskOwner).isTamed()
            && attackTarget instanceof EntityTameable
            && ((EntityTameable) attackTarget).isTamed()) {
                return false;
            } else {
                if (this.shouldCheckSight) {
                    if (this.taskOwner.getEntitySenses()
                        .canSee(attackTarget)) {
                        this.field_75298_g = 0;
                    } else if (++this.field_75298_g > 60) {
                        return false;
                    }
                }

                return true;
            }
    }

    public void startExecuting() {
        this.targetSearchStatus = 0;
        this.targetSearchDelay = 0;
        this.field_75298_g = 0;
    }

    public void resetTask() {
        this.taskOwner.setAttackTarget((EntityLiving) null);
    }

    protected boolean isSuitableTarget(EntityLivingBase target, boolean checkSight) {
        if (target == null) {
            return false;
        } else if (target == this.taskOwner) {
            return false;
        } else if (!target.isEntityAlive()) {
            return false;
        } else {
            if (this.taskOwner instanceof EntityTameable && ((EntityTameable) this.taskOwner).isTamed()) {
                if (target instanceof EntityTameable && ((EntityTameable) target).isTamed()) {
                    return false;
                }

                if (target == ((EntityTameable) this.taskOwner).getOwner()) {
                    return false;
                }
            }

            if (target instanceof EntityPlayer) {
                return OreSpawnMain.valentines_day != 0;
            } else if (target instanceof EntityPigZombie) {
                return false;
            } else if (target instanceof EntityEnderman) {
                return false;
            } else if (target instanceof Mothra) {
                return true;
            } else if (this.shouldCheckSight && !this.taskOwner.getEntitySenses()
                .canSee(target)) {
                    return false;
                } else if (target instanceof EntityCreeper) {
                    return true;
                } else if (target instanceof EntityGhast) {
                    return true;
                } else {
                    if (this.nearbyOnly) {
                        if (--this.targetSearchDelay <= 0) {
                            this.targetSearchStatus = 0;
                        }

                        if (this.targetSearchStatus == 0) {
                            this.targetSearchStatus = this.canEasilyReach(target) ? 1 : 2;
                        }

                        if (this.targetSearchStatus == 2) {
                            return false;
                        }
                    }

                    return true;
                }
        }
    }

    private boolean canEasilyReach(EntityLivingBase living) {
        this.targetSearchDelay = 10 + this.taskOwner.getRNG()
            .nextInt(5);
        PathEntity pathToEntityLiving = this.taskOwner.getNavigator()
            .getPathToEntityLiving(living);
        if (pathToEntityLiving == null) {
            return false;
        } else {
            PathPoint finalPathPoint = pathToEntityLiving.getFinalPathPoint();
            if (finalPathPoint == null) {
                return false;
            } else {
                int n = finalPathPoint.xCoord - MathHelper.floor_double(living.posX);
                int n2 = finalPathPoint.zCoord - MathHelper.floor_double(living.posZ);
                return n * n + n2 * n2 <= 2.25;
            }
        }
    }
}
