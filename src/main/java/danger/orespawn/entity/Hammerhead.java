package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.ai.MyEntityAIWanderALot;

public class Hammerhead extends EntityMob {

    private GenericTargetSorter TargetSorter = null;
    private float moveSpeed = 0.35F;
    private EntityLivingBase rt = null;

    public Hammerhead(World world) {
        super(world);
        this.setSize(3.0F, 5.0F);
        this.getNavigator()
            .setAvoidsWater(true);
        this.experienceValue = 350;
        this.fireResistance = 100;
        this.TargetSorter = new GenericTargetSorter(this);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMoveThroughVillage(this, 1.0, false));
        this.tasks.addTask(2, new MyEntityAIWanderALot(this, 16, 1.0));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(OreSpawnMain.Hammerhead_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.Hammerhead_stats.health;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.Hammerhead_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    protected String getLivingSound() {
        return this.rand.nextInt(3) == 0 ? "orespawn:hammerhead_living" : null;
    }

    protected String getHurtSound() {
        return "orespawn:alo_hurt";
    }

    protected String getDeathSound() {
        return "orespawn:hammerhead_death";
    }

    protected float getSoundVolume() {
        return 1.2F;
    }

    protected float getSoundPitch() {
        return 0.9F;
    }

    protected Item getDropItem() {
        return Items.beef;
    }

    private void dropItemRand(Item index, int n) {
        EntityItem entityItem = new EntityItem(
            this.worldObj,
            this.posX + OreSpawnMain.OreSpawnRand.nextInt(5) - OreSpawnMain.OreSpawnRand.nextInt(5),
            this.posY + 2.0,
            this.posZ + OreSpawnMain.OreSpawnRand.nextInt(5) - OreSpawnMain.OreSpawnRand.nextInt(5),
            new ItemStack(index, n, 0));
        this.worldObj.spawnEntityInWorld(entityItem);
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        for (int i = 0; i < 8; i++) {
            this.dropItemRand(Items.experience_bottle, 1);
        }

        for (int i = 0; i < 10; i++) {
            this.dropItemRand(OreSpawnMain.MyExperienceCatcher, 1);
        }

        for (int i = 0; i < 16; i++) {
            this.dropItemRand(OreSpawnMain.CreeperLauncher, 1);
        }

        for (int i = 0; i < 4; i++) {
            this.dropItemRand(Item.getItemFromBlock(OreSpawnMain.CreeperRepellent), 1);
        }

        for (int i = 0; i < 6; i++) {
            this.dropItemRand(Items.beef, 1);
        }

        for (int i = 0; i < 2; i++) {
            this.dropItemRand(OreSpawnMain.MyExperienceTreeSeed, 1);
        }

        if (this.worldObj.rand.nextInt(3) == 1) {
            this.dropItemRand(OreSpawnMain.MyHammy, 1);
        }
    }

    public void initCreature() {}

    public boolean interact(EntityPlayer player) {
        return false;
    }

    public boolean attackEntityAsMob(Entity target) {
        if (!super.attackEntityAsMob(target)) {
            return false;
        } else {
            if (target != null && target instanceof EntityLivingBase) {
                double ks = 1.1;
                double inair = 0.85;
                float f3 = (float) Math.atan2(target.posZ - this.posZ, target.posX - this.posX);
                if (target.isDead || target instanceof EntityPlayer) {
                    inair *= 2.0;
                }

                target.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }

            return true;
        }
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = false;
        if (!source.getDamageType()
            .equals("cactus")) {
            ret = super.attackEntityFrom(source, amount);
            Entity e = source.getEntity();
            if (e != null && e instanceof EntityLivingBase) {
                this.rt = (EntityLivingBase) e;
            }
        }

        return ret;
    }

    protected void updateAITasks() {
        if (!this.isDead) {
            super.updateAITasks();
            if (this.worldObj.rand.nextInt(3) == 1) {
                EntityLivingBase e = null;
                e = this.rt;
                if (OreSpawnMain.PlayNicely != 0) {
                    e = null;
                }

                if (e != null) {
                    if (e.isDead || this.worldObj.rand.nextInt(250) == 1) {
                        e = null;
                        this.rt = null;
                    }

                    if (e != null && !this.getEntitySenses()
                        .canSee(e)) {
                        e = null;
                    }
                }

                if (e == null) {
                    e = this.findSomethingToAttack();
                }

                if (e != null) {
                    this.faceEntity(e, 10.0F, 10.0F);
                    if (this.getDistanceSqToEntity(e) < (7.0F + e.width / 2.0F) * (7.0F + e.width / 2.0F)) {
                        this.setAttacking(1);
                        if (this.worldObj.rand.nextInt(3) == 1 || this.worldObj.rand.nextInt(4) == 1) {
                            this.attackEntityAsMob(e);
                        }
                    } else {
                        this.getNavigator()
                            .tryMoveToEntityLiving(e, 1.25);
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
            } else if (target instanceof Hammerhead) {
                return false;
            } else if (target instanceof EntityPlayer) {
                EntityPlayer p = (EntityPlayer) target;
                return !p.capabilities.isCreativeMode;
            } else {
                return target instanceof EntityMob ? true : MyUtils.isAttackableNonMob(target);
            }
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(18.0, 9.0, 18.0));
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

    public final int getAttacking() {
        return this.dataWatcher.getWatchableObjectByte(20);
    }

    public final void setAttacking(int attacking) {
        this.dataWatcher.updateObject(20, (byte) attacking);
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
                        if (s != null && s.equals("Hammerhead")) {
                            return true;
                        }
                    }
                }
            }
        }

        if (!this.isValidLightLevel()) {
            return false;
        } else if (this.posY < 50.0) {
            return false;
        } else if (this.worldObj.isDaytime()) {
            return false;
        } else {
            for (int i = -1; i < 1; i++) {
                for (int j = -1; j < 1; j++) {
                    for (int ix = 1; ix < 6; ix++) {
                        Block bid = this.worldObj
                            .getBlock((int) this.posX + j, (int) this.posY + ix, (int) this.posZ + i);
                        if (bid != Blocks.air) {
                            return false;
                        }
                    }
                }
            }

            Hammerhead target = null;
            target = (Hammerhead) this.worldObj
                .findNearestEntityWithinAABB(Hammerhead.class, this.boundingBox.expand(16.0, 8.0, 16.0), this);
            return target == null;
        }
    }
}
