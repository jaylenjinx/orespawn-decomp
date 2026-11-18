package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.ai.MyEntityAIWander;

public class Peacock extends EntityAnimal {

    private float moveSpeed = 0.38F;
    int my_blink = 0;
    int blinkcount = 0;
    int blinker = 0;
    private GenericTargetSorter TargetSorter = null;

    public Peacock(World world) {
        super(world);
        this.setSize(0.65F, 1.2F);
        this.fireResistance = 100;
        this.experienceValue = 8;
        this.my_blink = 20 + this.rand.nextInt(50);
        this.blinkcount = 0;
        this.blinker = 0;
        this.TargetSorter = new GenericTargetSorter(this);
        this.getNavigator()
            .setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMate(this, 1.0));
        this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityMob.class, 8.0F, 1.0, 1.4F));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityPlayer.class, 12.0F, 1.2F, 1.6F));
        this.tasks.addTask(4, new EntityAIPanic(this, 1.5));
        this.tasks.addTask(5, new MyEntityAIWander(this, 1.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        if (OreSpawnMain.PlayNicely == 0) {
            this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, Termite.class, 6, true));
        }
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
            .setBaseValue(4.0);
    }

    protected void entityInit() {
        super.entityInit();
    }

    public int getBlink() {
        return this.blinker;
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
        this.blinkcount++;
        if (this.blinkcount > this.my_blink) {
            this.blinkcount = 0;
            if (this.blinker > 0) {
                this.blinker = 0;
                this.my_blink = 50 + this.worldObj.rand.nextInt(300);
            } else {
                this.blinker = 1;
                this.my_blink = 25 + this.worldObj.rand.nextInt(100);
            }
        }
    }

    public boolean getCanSpawnHere() {
        for (int k = -1; k < 1; k++) {
            for (int j = -1; j < 1; j++) {
                for (int i = 1; i < 3; i++) {
                    Block bid = this.worldObj.getBlock((int) this.posX + j, (int) this.posY + i, (int) this.posZ + k);
                    if (bid != Blocks.air) {
                        return false;
                    }
                }
            }
        }

        long t = this.worldObj.getWorldTime();
        t %= 24000L;
        if (t > 12000L) {
            return false;
        } else {
            return this.posY < 50.0 || this.posY > 100.0 ? false : this.findBuddies() <= 2;
        }
    }

    public boolean isAIEnabled() {
        return true;
    }

    public boolean canBreatheUnderwater() {
        return false;
    }

    public int mygetMaxHealth() {
        return 15;
    }

    protected String getLivingSound() {
        return this.worldObj.rand.nextInt(8) != 1 ? null : "orespawn:peacocklive";
    }

    protected String getHurtSound() {
        return "orespawn:peacockhit";
    }

    protected String getDeathSound() {
        return "orespawn:peacockdead";
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    protected Item getDropItem() {
        return OreSpawnMain.MyRawPeacock;
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        this.dropItem(OreSpawnMain.MyRawPeacock, 1);
        if (this.worldObj.rand.nextInt(3) == 1) {
            this.dropItem(OreSpawnMain.MyRawPeacock, 1);
        }

        if (this.worldObj.rand.nextInt(2) == 1) {
            this.dropItem(OreSpawnMain.MyPeacockFeather, 1);
        }
    }

    public boolean attackEntityAsMob(Entity target) {
        return target.attackEntityFrom(DamageSource.causeMobDamage(this), 6.0F);
    }

    private ItemStack LayAnEgg(Item index, int n) {
        EntityItem entityItem = null;
        ItemStack is = new ItemStack(index, n, 0);
        entityItem = new EntityItem(
            this.worldObj,
            this.posX + OreSpawnMain.OreSpawnRand.nextInt(2) - OreSpawnMain.OreSpawnRand.nextInt(2),
            this.posY + 1.0,
            this.posZ + OreSpawnMain.OreSpawnRand.nextInt(2) - OreSpawnMain.OreSpawnRand.nextInt(2),
            is);
        if (entityItem != null) {
            this.worldObj.spawnEntityInWorld(entityItem);
        }

        return is;
    }

    protected void updateAITasks() {
        if (this.worldObj.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
        }

        super.updateAITasks();
        if (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) {
            if (this.worldObj.rand.nextInt(10) == 1) {
                EntityLivingBase e = this.findSomethingToAttack();
                if (e != null) {
                    if (this.getDistanceSqToEntity(e) < 4.0) {
                        this.attackEntityAsMob(e);
                    } else {
                        this.getNavigator()
                            .tryMoveToEntityLiving(e, 1.2);
                    }
                }
            }

            if (this.worldObj.rand.nextInt(5000) == 1) {
                this.LayAnEgg(OreSpawnMain.PeacockEgg, 1 + this.worldObj.rand.nextInt(3));
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
        } else {
            return !this.getEntitySenses()
                .canSee(target) ? false : target instanceof Termite;
        }
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(10.0, 2.0, 10.0));
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

    protected boolean canDespawn() {
        if (this.isChild()) {
            this.func_110163_bv();
            return false;
        } else {
            return !this.isNoDespawnRequired();
        }
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.spawnBabyAnimal(entityageable);
    }

    public Peacock spawnBabyAnimal(EntityAgeable entityAgeable) {
        return new Peacock(this.worldObj);
    }

    public boolean isWheat(ItemStack stack) {
        return stack != null && stack.getItem() == Items.apple;
    }

    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == OreSpawnMain.MyCrystalApple;
    }

    private int findBuddies() {
        List entities = this.worldObj.getEntitiesWithinAABB(Peacock.class, this.boundingBox.expand(16.0, 10.0, 16.0));
        return entities.size();
    }
}
