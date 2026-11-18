package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.GenericTargetSorter;

public class LeafMonster extends EntityMob {

    private GenericTargetSorter TargetSorter = null;
    private float moveSpeed = 0.25F;

    public LeafMonster(World world) {
        super(world);
        this.setSize(1.0F, 2.5F);
        this.getNavigator()
            .setAvoidsWater(true);
        this.experienceValue = 5;
        this.fireResistance = 0;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.35F));
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
            .setBaseValue(OreSpawnMain.LeafMonster_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
    }

    public final int getAttacking() {
        return this.dataWatcher.getWatchableObjectByte(20);
    }

    public final void setAttacking(int attacking) {
        this.dataWatcher.updateObject(20, (byte) attacking);
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.LeafMonster_stats.health;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.LeafMonster_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    protected void fall(float distance) {
        float i = MathHelper.ceiling_float_int(distance - 3.0F);
        if (i > 0.0F) {
            if (i > 2.0F) {
                this.playSound("damage.fallbig", 1.0F, 1.0F);
                i = 2.0F;
            } else {
                this.playSound("damage.fallsmall", 1.0F, 1.0F);
            }

            this.attackEntityFrom(DamageSource.fall, i);
        }
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
        if (this.getAttacking() == 0) {
            int px = (int) this.posX;
            int py = (int) this.posY;
            int pz = (int) this.posZ;
            this.posX = px;
            this.posY = py;
            this.posZ = pz;
            if (this.posX > 0.0) {
                this.posX += 0.5;
            }

            if (this.posZ > 0.0) {
                this.posZ += 0.5;
            }

            if (this.posX < 0.0) {
                this.posX -= 0.5;
            }

            if (this.posZ < 0.0) {
                this.posZ -= 0.5;
            }

            this.rotationPitch = 0.0F;
            px = (int) this.rotationYawHead;
            px /= 90;
            this.rotationYaw = this.rotationYawHead = px * 90;
        }
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return "orespawn:leaves_hit";
    }

    protected String getDeathSound() {
        return "orespawn:leaves_death";
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
            return Item.getItemFromBlock(Blocks.log);
        } else {
            return i == 1 ? Item.getItemFromBlock(Blocks.leaves) : Items.rotten_flesh;
        }
    }

    protected void updateAITasks() {
        super.updateAITasks();
        if (!this.isDead) {
            if (this.worldObj.rand.nextInt(100) == 1) {
                this.setRevengeTarget(null);
            }

            if (this.worldObj.rand.nextInt(4) == 1) {
                EntityLivingBase e = this.findSomethingToAttack();
                if (e != null) {
                    this.faceEntity(e, 10.0F, 10.0F);
                    this.setAttacking(1);
                    this.getNavigator()
                        .tryMoveToEntityLiving(e, 1.25);
                    if (this.getDistanceSqToEntity(e) < 5.0
                        && (this.rand.nextInt(8) == 0 || this.rand.nextInt(10) == 1)) {
                        this.attackEntityAsMob(e);
                    }
                } else {
                    this.setAttacking(0);
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
            } else if (target instanceof EntityAnt) {
                return true;
            } else if (target instanceof EntityButterfly) {
                return true;
            } else if (target instanceof EntityLunaMoth) {
                return true;
            } else {
                if (target instanceof EntityPlayer) {
                    EntityPlayer p = (EntityPlayer) target;
                    if (!p.capabilities.isCreativeMode) {
                        return true;
                    }
                }

                return false;
            }
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(4.0, 6.0, 4.0));
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
                        if (s != null && s.equals("Leaf Monster")) {
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
            if (this.worldObj.provider.dimensionId == OreSpawnMain.DimensionID4) {
                if (this.posY > 20.0) {
                    return false;
                }
            } else if (this.posY < 50.0) {
                return false;
            }

            return this.findBuddies() <= 4;
        }
    }

    private int findBuddies() {
        List entities = this.worldObj
            .getEntitiesWithinAABB(LeafMonster.class, this.boundingBox.expand(20.0, 10.0, 20.0));
        return entities.size();
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }
}
