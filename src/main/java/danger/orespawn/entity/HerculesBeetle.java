package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
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
import net.minecraft.entity.monster.EntityCreeper;
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

public class HerculesBeetle extends EntityMob {

    private GenericTargetSorter TargetSorter = null;
    private int hurt_timer = 0;
    private float moveSpeed = 0.25F;

    public HerculesBeetle(World world) {
        super(world);
        this.setSize(3.25F, 2.75F);
        this.getNavigator()
            .setAvoidsWater(true);
        this.experienceValue = 200;
        this.fireResistance = 100;
        this.isImmuneToFire = true;
        this.TargetSorter = new GenericTargetSorter(this);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMoveThroughVillage(this, 0.9F, false));
        this.tasks.addTask(2, new MyEntityAIWanderALot(this, 14, 1.0));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(OreSpawnMain.HerculesBeetle_stats.attack);
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
        return OreSpawnMain.HerculesBeetle_stats.health;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.HerculesBeetle_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    protected void jump() {
        super.jump();
        this.motionY += 0.25;
        this.posY += 0.5;
    }

    public int getHerculesBeetleHealth() {
        return (int) this.getHealth();
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return "orespawn:alo_hurt";
    }

    protected String getDeathSound() {
        return "orespawn:hercules_death";
    }

    protected float getSoundVolume() {
        return 1.5F;
    }

    protected float getSoundPitch() {
        return 1.0F;
    }

    protected Item getDropItem() {
        return Items.beef;
    }

    private ItemStack dropItemRand(Item index, int n) {
        EntityItem entityItem = null;
        ItemStack is = new ItemStack(index, n, 0);
        entityItem = new EntityItem(
            this.worldObj,
            this.posX + OreSpawnMain.OreSpawnRand.nextInt(5) - OreSpawnMain.OreSpawnRand.nextInt(5),
            this.posY + 1.0,
            this.posZ + OreSpawnMain.OreSpawnRand.nextInt(5) - OreSpawnMain.OreSpawnRand.nextInt(5),
            is);
        if (entityItem != null) {
            this.worldObj.spawnEntityInWorld(entityItem);
        }

        return is;
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        this.dropItemRand(OreSpawnMain.MyBigHammer, 1);
        this.dropItemRand(Items.item_frame, 1);
        int i = 4 + this.worldObj.rand.nextInt(8);

        for (int i2 = 0; i2 < i; i2++) {
            this.dropItemRand(Items.beef, 1);
        }

        i = 1 + this.worldObj.rand.nextInt(5);

        for (int i2 = 0; i2 < i; i2++) {
            int roll = this.worldObj.rand.nextInt(20);
            switch (roll) {
                case 0:
                case 12:
                default:
                    break;
                case 1: {
                    ItemStack is = this.dropItemRand(Items.diamond, 1);
                    break;
                }
                case 2:
                    ItemStack stack = this.dropItemRand(Item.getItemFromBlock(Blocks.diamond_block), 1);
                    break;
                case 3:
                    ItemStack stack2 = this.dropItemRand(Items.diamond_sword, 1);
                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack2.addEnchantment(Enchantment.sharpness, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack2.addEnchantment(Enchantment.baneOfArthropods, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack2.addEnchantment(Enchantment.knockback, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack2.addEnchantment(Enchantment.looting, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(2) == 1) {
                        stack2.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack2.addEnchantment(Enchantment.fireAspect, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack2.addEnchantment(Enchantment.sharpness, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 4:
                    ItemStack stack3 = this.dropItemRand(Items.diamond_shovel, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        stack3.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack3.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 5:
                    ItemStack stack4 = this.dropItemRand(Items.diamond_pickaxe, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        stack4.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack4.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack4.addEnchantment(Enchantment.fortune, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 6:
                    ItemStack stack5 = this.dropItemRand(Items.diamond_axe, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        stack5.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack5.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 7:
                    ItemStack stack6 = this.dropItemRand(Items.diamond_hoe, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        stack6.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack6.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 8:
                    ItemStack stack7 = this.dropItemRand(Items.diamond_helmet, 1);
                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack7.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack7.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack7.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack7.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(2) == 1) {
                        stack7.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack7.addEnchantment(Enchantment.respiration, 1 + this.worldObj.rand.nextInt(2));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack7.addEnchantment(Enchantment.aquaAffinity, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 9:
                    ItemStack stack8 = this.dropItemRand(Items.diamond_chestplate, 1);
                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack8.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack8.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack8.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack8.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(2) == 1) {
                        stack8.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }
                    break;
                case 10:
                    ItemStack stack9 = this.dropItemRand(Items.diamond_leggings, 1);
                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack9.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack9.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack9.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack9.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(2) == 1) {
                        stack9.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }
                    break;
                case 11: {
                    ItemStack is = this.dropItemRand(Items.diamond_boots, 1);
                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.featherFalling, 5 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }
                }
            }
        }
    }

    public boolean interact(EntityPlayer player) {
        return false;
    }

    public boolean attackEntityAsMob(Entity target) {
        double ks = 0.45;
        double inair = 1.25;
        if (!super.attackEntityAsMob(target)) {
            return false;
        } else {
            if (target != null && target instanceof EntityLivingBase) {
                float f3 = (float) Math.atan2(target.posZ - this.posZ, target.posX - this.posX);
                if (target.isDead || target instanceof EntityPlayer) {
                    inair *= 2.0;
                }

                target.addVelocity(
                    Math.cos(f3) * ks,
                    inair * Math.abs(this.worldObj.rand.nextFloat()),
                    Math.sin(f3) * ks);
            }

            return true;
        }
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = false;
        if (this.hurt_timer > 0) {
            return false;
        } else {
            if (!source.getDamageType()
                .equals("cactus")) {
                ret = super.attackEntityFrom(source, amount);
                this.hurt_timer = 20;
                Entity e = source.getEntity();
                if (e != null && e instanceof EntityLiving) {
                    this.setAttackTarget((EntityLiving) e);
                    this.setTarget(e);
                    this.getNavigator()
                        .tryMoveToEntityLiving((EntityLiving) e, 1.2);
                }
            }

            return ret;
        }
    }

    protected void updateAITasks() {
        EntityLivingBase e = null;
        if (!this.isDead) {
            super.updateAITasks();
            if (this.hurt_timer > 0) {
                this.hurt_timer--;
            }

            if (this.worldObj.rand.nextInt(4) == 0) {
                e = this.getAttackTarget();
                if (e != null && !e.isEntityAlive()) {
                    this.setAttackTarget(null);
                    e = null;
                }

                if (e == null) {
                    e = this.findSomethingToAttack();
                }

                if (e != null) {
                    this.faceEntity(e, 10.0F, 10.0F);
                    if (this.getDistanceSqToEntity(e) < (5.0F + e.width / 2.0F) * (5.0F + e.width / 2.0F)) {
                        this.setAttacking(1);
                        if (this.worldObj.rand.nextInt(3) == 0 || this.worldObj.rand.nextInt(4) == 1) {
                            this.attackEntityAsMob(e);
                            if (!this.worldObj.isRemote) {
                                if (this.worldObj.rand.nextInt(3) == 1) {
                                    this.worldObj.playSoundAtEntity(e, "orespawn:scorpion_attack", 1.4F, 1.0F);
                                } else {
                                    this.worldObj.playSoundAtEntity(e, "orespawn:scorpion_living", 1.0F, 1.0F);
                                }
                            }
                        }
                    } else {
                        this.getNavigator()
                            .tryMoveToEntityLiving(e, 1.2);
                    }
                } else {
                    this.setAttacking(0);
                }
            }

            if (this.worldObj.rand.nextInt(150) == 1 && this.getHealth() < this.mygetMaxHealth()) {
                this.heal(2.0F);
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
            } else if (target instanceof EntityCreeper) {
                return false;
            } else if (target instanceof HerculesBeetle) {
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
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(16.0, 6.0, 16.0));
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
                        if (s != null && s.equals("Hercules Beetle")) {
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
        } else if (this.posY < 50.0) {
            return false;
        } else {
            for (int i = -2; i < 2; i++) {
                for (int j = -2; j < 2; j++) {
                    for (int ix = 2; ix < 5; ix++) {
                        Block bid = this.worldObj
                            .getBlock((int) this.posX + j, (int) this.posY + ix, (int) this.posZ + i);
                        if (bid != Blocks.air) {
                            return false;
                        }
                    }
                }
            }

            HerculesBeetle target = null;
            target = (HerculesBeetle) this.worldObj
                .findNearestEntityWithinAABB(HerculesBeetle.class, this.boundingBox.expand(16.0, 6.0, 16.0), this);
            return target == null;
        }
    }
}
