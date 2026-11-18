package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
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
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.ai.MyEntityAIWanderALot;

public class Rat extends EntityMob {

    private GenericTargetSorter TargetSorter = null;
    private float moveSpeed = 0.25F;
    private String myowner = null;

    public Rat(World world) {
        super(world);
        this.setSize(0.25F, 0.5F);
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
            .setBaseValue(OreSpawnMain.Rat_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
    }

    protected boolean canDespawn() {
        return this.isNoDespawnRequired() ? false : this.myowner == null;
    }

    public final int getAttacking() {
        return this.dataWatcher.getWatchableObjectByte(20);
    }

    public final void setAttacking(int attacking) {
        this.dataWatcher.updateObject(20, (byte) attacking);
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.Rat_stats.health;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.Rat_stats.defense;
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

    protected void jump() {
        super.jump();
        this.motionY += 0.25;
        this.posY += 0.25;
    }

    protected String getLivingSound() {
        return "orespawn:ratlive";
    }

    protected String getHurtSound() {
        return "orespawn:rathit";
    }

    protected String getDeathSound() {
        return "orespawn:ratdead";
    }

    protected float getSoundVolume() {
        return 0.45F;
    }

    protected float getSoundPitch() {
        return 1.0F;
    }

    protected Item getDropItem() {
        return Items.rotten_flesh;
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
                    this.setAttacking(1);
                    this.getNavigator()
                        .tryMoveToEntityLiving(e, 1.25);
                    if (this.getDistanceSqToEntity(e) < 4.0
                        && (this.rand.nextInt(8) == 0 || this.rand.nextInt(7) == 1)) {
                        this.attackEntityAsMob(e);
                    }
                } else {
                    this.setAttacking(0);
                    if (this.myowner != null) {
                        EntityPlayer p = this.worldObj.getPlayerEntityByName(this.myowner);
                        if (p != null) {
                            if (this.getDistanceSqToEntity(p) > 64.0) {
                                this.getNavigator()
                                    .tryMoveToEntityLiving(p, 1.75);
                            }

                            if (this.getDistanceSqToEntity(p) > 256.0) {
                                this.setPosition(
                                    p.posX + this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat(),
                                    p.posY,
                                    p.posZ + this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat());
                            }
                        }
                    }
                }
            }

            if (this.worldObj.rand.nextInt(250) == 1) {
                this.heal(1.0F);
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
        } else if (MyUtils.isIgnoreable(target)) {
            return false;
        } else if (!this.getEntitySenses()
            .canSee(target)) {
                return false;
            } else if (target instanceof Irukandji) {
                return false;
            } else if (target instanceof Skate) {
                return false;
            } else if (target instanceof Whale) {
                return false;
            } else if (target instanceof Flounder) {
                return false;
            } else if (target instanceof Rat) {
                return false;
            } else if (target instanceof Ghost) {
                return false;
            } else if (target instanceof GhostSkelly) {
                return false;
            } else if (target instanceof DungeonBeast) {
                return false;
            } else {
                if (target instanceof EntityPlayer) {
                    EntityPlayer p = (EntityPlayer) target;
                    if (p.capabilities.isCreativeMode) {
                        return false;
                    }

                    if (this.myowner != null) {
                        if (this.myowner.equals(
                            p.getUniqueID()
                                .toString())) {
                            return false;
                        }

                        if (OreSpawnMain.RatPlayerFriendly != 0) {
                            return false;
                        }
                    }
                }

                if (this.myowner != null && target instanceof EntityTameable) {
                    EntityTameable e = (EntityTameable) target;
                    if (OreSpawnMain.RatPetFriendly != 0 && e.isTamed()) {
                        return false;
                    }

                    if (e.func_152113_b() != null && this.myowner.equals(e.func_152113_b())) {
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

    public void setOwner(EntityLivingBase e) {
        EntityPlayer p = null;
        if (e != null && e instanceof EntityPlayer) {
            p = (EntityPlayer) e;
            String s = p.getUniqueID()
                .toString();
            if (s != null) {
                this.myowner = s;
            }
        }
    }

    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        if (this.myowner == null) {
            this.myowner = "null";
        }

        tag.setString("MyOwner", this.myowner);
    }

    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        this.myowner = tag.getString("MyOwner");
        if (this.myowner != null && this.myowner.equals("null")) {
            this.myowner = null;
        }
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = false;
        return source.getDamageType()
            .equals("inWall") ? ret : super.attackEntityFrom(source, amount);
    }

    public boolean getCanSpawnHere() {
        int sc = 0;

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
                        if (s != null && s.equals("Rat")) {
                            return true;
                        }
                    }
                }
            }
        }

        if (!this.isValidLightLevel()) {
            return false;
        } else {
            if (this.worldObj.provider.dimensionId == OreSpawnMain.DimensionID5) {
                if (this.posY > 50.0) {
                    return false;
                }

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        Block bid = this.worldObj
                            .getBlock((int) this.posX + j, (int) this.posY + 1, (int) this.posZ + i);
                        if (bid == Blocks.air) {
                            sc++;
                        }
                    }
                }

                if (sc < 4) {
                    return false;
                }
            }

            return this.findBuddies() <= 8;
        }
    }

    private int findBuddies() {
        List entities = this.worldObj.getEntitiesWithinAABB(Rat.class, this.boundingBox.expand(20.0, 10.0, 20.0));
        return entities.size();
    }
}
