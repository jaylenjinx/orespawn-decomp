package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.ai.MyEntityAIWander;

public class SpiderDriver extends EntitySpider {

    private GenericTargetSorter TargetSorter = null;

    public SpiderDriver(World world) {
        super(world);
        this.TargetSorter = new GenericTargetSorter(this);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIPanic(this, 1.5));
        this.tasks.addTask(3, new MyEntityAIWander(this, 0.65F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    protected boolean canDespawn() {
        return this.isNoDespawnRequired() ? false : this.ridingEntity == null;
    }

    public boolean isAIEnabled() {
        return true;
    }

    protected Entity findPlayerToAttack() {
        double d0 = 16.0;
        return this.worldObj.getClosestVulnerablePlayerToEntity(this, d0);
    }

    protected void updateAITasks() {
        if (!this.isDead) {
            super.updateAITasks();
            if (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && this.worldObj.rand.nextInt(5) == 0
                && this.ridingEntity == null) {
                EntityLivingBase e = this.findSpiderRobot();
                if (e != null) {
                    this.faceEntity(e, 10.0F, 10.0F);
                    if (this.getDistanceSqToEntity(e) < (4.0F + e.width / 2.0F) * (4.0F + e.width / 2.0F)) {
                        this.mountEntity(e);
                    } else {
                        this.getNavigator()
                            .tryMoveToEntityLiving(e, 0.55);
                    }
                }
            }

            if (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && this.worldObj.rand.nextInt(4) == 0
                && this.ridingEntity != null) {
                EntityLivingBase e = this.findSomethingToAttack();
                if (e != null) {
                    this.faceEntity(e, 10.0F, 10.0F);
                    if (!(this.getDistanceSqToEntity(e) < (11.0F + e.width / 2.0F) * (11.0F + e.width / 2.0F))
                        && this.ridingEntity instanceof SpiderRobot) {
                        SpiderRobot sp = (SpiderRobot) this.ridingEntity;
                        double d1 = e.posZ - this.posZ;
                        double d2 = e.posX - this.posX;
                        double dd = Math.atan2(d1, d2);
                        sp.goThisWay(0.35 * Math.cos(dd), 0.35 * Math.sin(dd));
                    }
                }
            }
        }
    }

    protected void attackEntity(Entity entity, float f) {
        if (this.attackTime <= 0 && f < 2.0F
            && entity.boundingBox.maxY > this.boundingBox.minY
            && entity.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 16;
            this.attackEntityAsMob(entity);
            if (this.worldObj.rand.nextInt(2) == 0) {
                ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 60, 0));
            }
        }
    }

    public int getTotalArmorValue() {
        return this.ridingEntity != null ? 8 : 20;
    }

    private EntityLivingBase findSpiderRobot() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            List entities = this.worldObj
                .getEntitiesWithinAABB(SpiderRobot.class, this.boundingBox.expand(25.0, 15.0, 25.0));
            Collections.sort(entities, this.TargetSorter);
            Iterator iterator = entities.iterator();
            Entity entity = null;
            EntityLivingBase living = null;

            while (iterator.hasNext()) {
                entity = (Entity) iterator.next();
                living = (EntityLivingBase) entity;
                if (living.riddenByEntity == null) {
                    return living;
                }
            }

            return null;
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
        } else if (MyUtils.isIgnoreable(target)) {
            return false;
        } else if (target instanceof SpiderRobot) {
            return false;
        } else if (target instanceof SpiderDriver) {
            return false;
        } else if (target instanceof EntitySpider) {
            return false;
        } else if (target instanceof EntityCaveSpider) {
            return false;
        } else if (!this.getEntitySenses()
            .canSee(target)) {
                return false;
            } else if (target instanceof EntityPlayer) {
                EntityPlayer p = (EntityPlayer) target;
                return !p.capabilities.isCreativeMode;
            } else {
                return !(this.getDistanceSqToEntity(target) < 36.0);
            }
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(35.0, 15.0, 35.0));
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

    public boolean getCanSpawnHere() {
        SpiderRobot target = null;
        target = (SpiderRobot) this.worldObj
            .findNearestEntityWithinAABB(SpiderRobot.class, this.boundingBox.expand(24.0, 12.0, 24.0), this);
        return target != null ? true : super.getCanSpawnHere();
    }
}
