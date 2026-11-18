package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.ai.MyEntityAIWanderALot;

public class CreepingHorror extends EntityMob {

    private GenericTargetSorter TargetSorter = null;
    private float moveSpeed = 0.25F;

    public CreepingHorror(World world) {
        super(world);
        this.setSize(0.75F, 0.5F);
        this.getNavigator()
            .setAvoidsWater(true);
        this.experienceValue = 5;
        this.fireResistance = 10;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.35F));
        this.tasks.addTask(2, new EntityAIMoveThroughVillage(this, 1.0, false));
        this.tasks.addTask(3, new MyEntityAIWanderALot(this, 10, 1.0));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.TargetSorter = new GenericTargetSorter(this);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(OreSpawnMain.CreepingHorror_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.CreepingHorror_stats.health;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.CreepingHorror_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
        if (!this.isNoDespawnRequired()) {
            long t = this.worldObj.getWorldTime();
            t %= 24000L;
            if (t <= 11000L) {
                if (this.worldObj.rand.nextInt(500) == 1) {
                    this.setDead();
                }
            }
        }
    }

    protected String getLivingSound() {
        return "orespawn:creepinghorror_living";
    }

    protected String getHurtSound() {
        return "orespawn:creepinghorror_hit";
    }

    protected String getDeathSound() {
        return "orespawn:creepinghorror_dead";
    }

    protected float getSoundVolume() {
        return 0.65F;
    }

    protected float getSoundPitch() {
        return 1.0F;
    }

    protected Item getDropItem() {
        int i = this.worldObj.rand.nextInt(3);
        if (i == 0) {
            return Items.rotten_flesh;
        } else {
            return i == 1 ? Items.bone : Items.string;
        }
    }

    protected void updateAITasks() {
        if (!this.isDead) {
            super.updateAITasks();
            if (this.worldObj.rand.nextInt(200) == 1) {
                this.setRevengeTarget(null);
            }

            if (this.worldObj.rand.nextInt(5) == 1) {
                EntityLivingBase e = this.findSomethingToAttack();
                if (e != null) {
                    this.getNavigator()
                        .tryMoveToEntityLiving(e, 1.25);
                    if (this.getDistanceSqToEntity(e) < 5.0
                        && (this.rand.nextInt(12) == 0 || this.rand.nextInt(14) == 1)) {
                        this.attackEntityAsMob(e);
                    }
                }
            }
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
            } else if (target instanceof CreepingHorror) {
                return false;
            } else if (target instanceof RockBase) {
                return false;
            } else if (target instanceof EnderReaper) {
                return false;
            } else if (target instanceof LeafMonster) {
                return false;
            } else if (target instanceof Dragon) {
                return false;
            } else if (target instanceof TerribleTerror) {
                return false;
            } else if (target instanceof LurkingTerror) {
                return false;
            } else if (target instanceof PitchBlack) {
                return false;
            } else if (target instanceof Firefly) {
                return false;
            } else if (target instanceof Island) {
                return false;
            } else if (target instanceof IslandToo) {
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
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(16.0, 4.0, 16.0));
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
        if (!this.isValidLightLevel()) {
            return false;
        } else {
            return this.worldObj.isDaytime() ? false
                : this.worldObj.provider.dimensionId == OreSpawnMain.DimensionID6 || !(this.posY > 15.0);
        }
    }

    protected boolean canDespawn() {
        return this.isNoDespawnRequired() ? false : this.worldObj.isDaytime();
    }
}
