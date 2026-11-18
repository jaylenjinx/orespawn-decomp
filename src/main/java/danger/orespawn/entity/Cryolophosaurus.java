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

public class Cryolophosaurus extends EntityMob {

    private GenericTargetSorter TargetSorter = null;
    private float moveSpeed = 0.25F;

    public Cryolophosaurus(World world) {
        super(world);
        this.setSize(0.75F, 0.75F);
        this.getNavigator()
            .setAvoidsWater(true);
        this.experienceValue = 10;
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
            .setBaseValue(OreSpawnMain.Cryolophosaurus_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.Cryolophosaurus_stats.health;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.Cryolophosaurus_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
    }

    protected String getLivingSound() {
        return this.rand.nextInt(6) == 0 ? "orespawn:cryo_living" : null;
    }

    protected String getHurtSound() {
        return "orespawn:cryo_hurt";
    }

    protected String getDeathSound() {
        return "orespawn:cryo_death";
    }

    protected float getSoundVolume() {
        return 0.75F;
    }

    protected float getSoundPitch() {
        return 1.0F;
    }

    protected Item getDropItem() {
        int i = this.worldObj.rand.nextInt(10);
        if (i == 0) {
            return Items.chicken;
        } else if (i == 1) {
            return OreSpawnMain.UraniumNugget;
        } else {
            return i == 2 ? OreSpawnMain.TitaniumNugget : null;
        }
    }

    public void initCreature() {}

    public boolean interact(EntityPlayer player) {
        return false;
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
            } else if (target instanceof Alosaurus) {
                return false;
            } else if (target instanceof TRex) {
                return false;
            } else if (target instanceof Cryolophosaurus) {
                return false;
            } else if (target instanceof Ghost) {
                return false;
            } else if (target instanceof GhostSkelly) {
                return false;
            } else if (target instanceof CaveFisher) {
                return false;
            } else if (target instanceof GammaMetroid) {
                return false;
            } else if (target instanceof EntityButterfly) {
                return false;
            } else if (target instanceof Firefly) {
                return false;
            } else if (target instanceof EntityMosquito) {
                return false;
            } else if (target instanceof RockBase) {
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
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(9.0, 2.0, 9.0));
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
        return !this.isValidLightLevel() ? false : !this.worldObj.isDaytime() || !(this.posY > 50.0);
    }
}
