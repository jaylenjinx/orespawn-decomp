package danger.orespawn;

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
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Kyuubi extends EntityMob {

    private GenericTargetSorter TargetSorter = null;
    private float moveSpeed = 0.25F;

    public Kyuubi(World par1World) {
        super(par1World);
        this.setSize(0.5F, 1.25F);
        this.getNavigator()
            .setAvoidsWater(true);
        this.experienceValue = 30;
        this.fireResistance = 1000;
        this.isImmuneToFire = true;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.35F));
        this.tasks.addTask(2, new EntityAIMoveThroughVillage(this, 1.0, false));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.TargetSorter = new GenericTargetSorter(this);
    }

    protected void entityInit() {
        super.entityInit();
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(OreSpawnMain.Kyuubi_stats.attack);
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
        return OreSpawnMain.Kyuubi_stats.health;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.Kyuubi_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.worldObj.rand.nextInt(10) == 1) {
            this.worldObj.spawnParticle("reddust", this.posX, this.posY + 2.0, this.posZ, 0.0, 0.0, 0.0);
            this.worldObj.spawnParticle("lava", this.posX, this.posY + 2.0, this.posZ, 0.0, 0.0, 0.0);
            this.setFire(5);
            if (this.isInWater()) {
                this.attackEntityAsMob(this);
                this.worldObj.spawnParticle("smoke", this.posX, this.posY + 1.75, this.posZ, 0.0, 0.0, 0.0);
                this.worldObj.spawnParticle("largesmoke", this.posX, this.posY + 1.75, this.posZ, 0.0, 0.0, 0.0);
                this.worldObj.spawnParticle("smoke", this.posX, this.posY + 2.0, this.posZ, 0.0, 0.0, 0.0);
                this.worldObj.spawnParticle("largesmoke", this.posX, this.posY + 2.0, this.posZ, 0.0, 0.0, 0.0);
            }
        }
    }

    public int getAttackStrength(Entity par1Entity) {
        return 3;
    }

    protected String getLivingSound() {
        return "orespawn:kyuubi_living";
    }

    protected String getHurtSound() {
        return "orespawn:alo_hurt";
    }

    protected String getDeathSound() {
        return "orespawn:alo_death";
    }

    protected float getSoundVolume() {
        return 0.75F;
    }

    protected float getSoundPitch() {
        return 1.0F;
    }

    protected Item getDropItem() {
        int i = this.worldObj.rand.nextInt(6);
        if (i == 0) {
            return Items.gold_nugget;
        } else if (i == 1) {
            return OreSpawnMain.UraniumNugget;
        } else {
            return i == 2 ? OreSpawnMain.TitaniumNugget : null;
        }
    }

    public void initCreature() {}

    public boolean interact(EntityPlayer par1EntityPlayer) {
        return false;
    }

    protected void updateAITasks() {
        if (!this.isDead) {
            if (this.worldObj.rand.nextInt(200) == 1) {
                this.setRevengeTarget(null);
            }

            super.updateAITasks();
            if (this.worldObj.rand.nextInt(10) == 1) {
                EntityLivingBase e = this.findSomethingToAttack();
                if (e != null) {
                    this.faceEntity(e, 10.0F, 10.0F);
                    this.getNavigator()
                        .tryMoveToEntityLiving(e, 1.25);
                    if (this.getDistanceSqToEntity(e) < 64.0
                        && (this.rand.nextInt(6) == 0 || this.rand.nextInt(8) == 1)) {
                        EntitySmallFireball var2 = new EntitySmallFireball(
                            this.worldObj,
                            this,
                            e.posX - this.posX,
                            e.posY + 0.75 - (this.posY + 1.25),
                            e.posZ - this.posZ);
                        var2.setLocationAndAngles(
                            this.posX,
                            this.posY + 1.25,
                            this.posZ,
                            this.rotationYaw,
                            this.rotationPitch);
                        this.worldObj.playSoundAtEntity(
                            this,
                            "random.bow",
                            0.75F,
                            1.0F / (this.getRNG()
                                .nextFloat() * 0.4F + 0.8F));
                        this.worldObj.spawnEntityInWorld(var2);
                    }
                }
            }
        }
    }

    private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
        if (par1EntityLiving == null) {
            return false;
        } else if (par1EntityLiving == this) {
            return false;
        } else if (!par1EntityLiving.isEntityAlive()) {
            return false;
        } else if (MyUtils.isIgnoreable(par1EntityLiving)) {
            return false;
        } else if (!this.getEntitySenses()
            .canSee(par1EntityLiving)) {
                return false;
            } else if (par1EntityLiving instanceof EntityMob) {
                return false;
            } else if (par1EntityLiving instanceof EntityPigZombie) {
                return false;
            } else {
                if (par1EntityLiving instanceof EntityPlayer) {
                    EntityPlayer p = (EntityPlayer) par1EntityLiving;
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
            List var5 = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(12.0, 4.0, 12.0));
            Collections.sort(var5, this.TargetSorter);
            Iterator var2 = var5.iterator();
            Entity var3 = null;
            EntityLivingBase var4 = null;

            while (var2.hasNext()) {
                var3 = (Entity) var2.next();
                var4 = (EntityLivingBase) var3;
                if (this.isSuitableTarget(var4, false)) {
                    return var4;
                }
            }

            return null;
        }
    }

    public boolean getCanSpawnHere() {
        return true;
    }

    private void dropItemRand(Item index, int par1) {
        EntityItem var3 = new EntityItem(
            this.worldObj,
            this.posX + OreSpawnMain.OreSpawnRand.nextInt(4) - OreSpawnMain.OreSpawnRand.nextInt(4),
            this.posY + 1.0,
            this.posZ + OreSpawnMain.OreSpawnRand.nextInt(4) - OreSpawnMain.OreSpawnRand.nextInt(4),
            new ItemStack(index, par1, 0));
        this.worldObj.spawnEntityInWorld(var3);
    }

    protected void dropFewItems(boolean par1, int par2) {
        for (int var4 = 0; var4 < 10; var4++) {
            this.dropItemRand(Items.coal, 1);
        }

        for (int var41 = 0; var41 < 3; var41++) {
            this.dropItemRand(Item.getItemFromBlock(Blocks.redstone_block), 1);
        }

        for (int var5 = 0; var5 < 4; var5++) {
            this.dropItemRand(Item.getItemFromBlock(Blocks.quartz_block), 1);
        }
    }
}
