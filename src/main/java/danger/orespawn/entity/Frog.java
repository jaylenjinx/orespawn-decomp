package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.ai.MyEntityAIWander;

public class Frog extends EntityAnimal {

    private GenericTargetSorter TargetSorter = null;
    public double moveSpeed = 0.1F;
    private int singing = 0;
    private int jumpcount = 0;

    public Frog(World world) {
        super(world);
        this.setSize(0.75F, 0.75F);
        this.experienceValue = 5;
        this.TargetSorter = new GenericTargetSorter(this);
        this.getNavigator()
            .setAvoidsWater(false);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.4));
        this.tasks.addTask(2, new MyEntityAIWander(this, 1.0F));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        this.getAttributeMap()
            .registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(0.0);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public int getSinging() {
        return this.dataWatcher.getWatchableObjectByte(20);
    }

    public void setSinging(int n) {
        this.dataWatcher.updateObject(20, (byte) n);
    }

    private void jumpAround() {
        this.motionY = this.motionY + (0.75F + Math.abs(this.worldObj.rand.nextFloat() * 0.55F));
        this.posY += 0.35F;
        float f = 0.7F + Math.abs(this.worldObj.rand.nextFloat() * 0.75F);
        float d = (float) Math.toRadians(this.rotationYaw);
        this.motionX = this.motionX - f * Math.sin(d);
        this.motionZ = this.motionZ + f * Math.cos(d);
        this.isAirBorne = true;
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
        if (!this.worldObj.isRemote) {
            if (this.singing != 0) {
                this.singing--;
                if (this.singing <= 0) {
                    this.setSinging(0);
                }
            }

            if (this.jumpcount > 0) {
                this.jumpcount--;
            }

            if (this.jumpcount == 0 && this.worldObj.rand.nextInt(70) == 1) {
                this.jumpAround();
                this.jumpcount = 50;
            }
        }
    }

    public boolean interact(EntityPlayer player) {
        if (player != null && player.isSneaking() && player.inventory.getCurrentItem() == null) {
            World world = player.worldObj;
            this.setDead();
            player.worldObj.playSoundAtEntity(player, "random.explode", 1.0F, world.rand.nextFloat() * 0.2F + 0.9F);
            if (!world.isRemote) {
                if (world.rand.nextInt(2) == 0) {
                    Boyfriend ent = null;
                    ent = (Boyfriend) spawnCreature(world, "Boyfriend", this.posX, this.posY + 0.01, this.posZ);
                    if (ent != null) {
                        ent.setPrince(1 + world.rand.nextInt(2));
                    }
                } else {
                    Girlfriend ent = null;
                    ent = (Girlfriend) spawnCreature(world, "Girlfriend", this.posX, this.posY + 0.01, this.posZ);
                    if (ent != null) {
                        ent.setPrincess(1 + world.rand.nextInt(2));
                    }
                }
            } else {
                for (int i = 0; i < 16; i++) {
                    world.spawnParticle(
                        "smoke",
                        (float) this.posX + world.rand.nextFloat() - world.rand.nextFloat(),
                        (float) this.posY + world.rand.nextFloat(),
                        (float) this.posZ + world.rand.nextFloat() - world.rand.nextFloat(),
                        0.0,
                        0.0,
                        0.0);
                    world.spawnParticle(
                        "explode",
                        (float) this.posX + world.rand.nextFloat() - world.rand.nextFloat(),
                        (float) this.posY + world.rand.nextFloat(),
                        (float) this.posZ + world.rand.nextFloat() - world.rand.nextFloat(),
                        0.0,
                        0.0,
                        0.0);
                    world.spawnParticle(
                        "reddust",
                        (float) this.posX + world.rand.nextFloat() - world.rand.nextFloat(),
                        (float) this.posY + world.rand.nextFloat(),
                        (float) this.posZ + world.rand.nextFloat() - world.rand.nextFloat(),
                        0.0,
                        0.0,
                        0.0);
                }
            }
        }

        return false;
    }

    public boolean isAIEnabled() {
        return true;
    }

    public int mygetMaxHealth() {
        return 8;
    }

    protected String getLivingSound() {
        if (!this.worldObj.isRemote) {
            if (this.worldObj.rand.nextInt(2) == 0) {
                return null;
            }

            this.singing = 35;
            this.setSinging(this.singing);
        }

        return "orespawn:frog";
    }

    protected String getHurtSound() {
        return "orespawn:scorpion_hit";
    }

    protected String getDeathSound() {
        return "orespawn:big_splat";
    }

    protected float getSoundVolume() {
        return 0.7F;
    }

    protected void fall(float distance) {}

    protected void updateFallState(double distanceFallen, boolean onGround) {}

    protected void playStepSound(int n, int n2, int n3, int n4) {}

    private void dropItemRand(Item index, int n) {
        EntityItem entityItem = new EntityItem(
            this.worldObj,
            this.posX + OreSpawnMain.OreSpawnRand.nextInt(2) - OreSpawnMain.OreSpawnRand.nextInt(2),
            this.posY + 1.0,
            this.posZ + OreSpawnMain.OreSpawnRand.nextInt(2) - OreSpawnMain.OreSpawnRand.nextInt(2),
            new ItemStack(index, n, 0));
        this.worldObj.spawnEntityInWorld(entityItem);
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        for (int i = 0; i < 4; i++) {
            this.dropItemRand(Items.slime_ball, 1);
        }
    }

    public boolean attackEntityAsMob(Entity target) {
        boolean flag = target.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
        if (target.isDead) {
            this.heal(1.0F);
        }

        return flag;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = false;
        ret = super.attackEntityFrom(source, amount);
        if (!this.worldObj.isRemote && this.jumpcount <= 0) {
            this.jumpAround();
            this.jumpcount = 25;
        }

        return ret;
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.worldObj.rayTraceBlocks(
            Vec3.createVectorHelper(this.posX, this.posY + 0.25, this.posZ),
            Vec3.createVectorHelper(pX, pY, pZ),
            false) == null;
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    public EntityAgeable createChild(EntityAgeable partner) {
        return null;
    }

    private int findBuddies() {
        List entities = this.worldObj.getEntitiesWithinAABB(Frog.class, this.boundingBox.expand(20.0, 8.0, 20.0));
        return entities.size();
    }

    public boolean getCanSpawnHere() {
        if (this.posY < 50.0) {
            return false;
        } else if (!this.worldObj.isDaytime()) {
            return false;
        } else {
            return this.worldObj.provider.dimensionId == OreSpawnMain.DimensionID5
                && this.worldObj.rand.nextInt(20) != 1 ? false : this.findBuddies() <= 5;
        }
    }

    protected void updateAITasks() {
        int xdir = 1;
        int zdir = 1;
        int keep_trying = 50;
        if (!this.isDead) {
            super.updateAITasks();
            if (this.rand.nextInt(12) == 0 && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) {
                EntityLivingBase e = null;
                e = this.findSomethingToAttack();
                if (e != null) {
                    this.getNavigator()
                        .tryMoveToEntityLiving(e, 1.25);
                    if (this.getDistanceSqToEntity(e) < 6.0) {
                        this.attackEntityAsMob(e);
                    }
                }
            }
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
        } else if (!this.getEntitySenses()
            .canSee(target)) {
                return false;
            } else if (target instanceof EntityAnt) {
                return true;
            } else if (target instanceof EntityButterfly) {
                return true;
            } else if (target instanceof Cricket) {
                return true;
            } else if (target instanceof EntityMosquito) {
                return true;
            } else {
                return target instanceof Firefly ? true : target instanceof WormSmall;
            }
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(8.0, 3.0, 8.0));
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

    public static Entity spawnCreature(World world, String str, double x, double y, double z) {
        Entity entity = null;
        entity = EntityList.createEntityByName(str, world);
        if (entity != null) {
            entity.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0F, 0.0F);
            world.spawnEntityInWorld(entity);
            ((EntityLiving) entity).playLivingSound();
        }

        return entity;
    }
}
