package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
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
import net.minecraft.entity.monster.EntityEnderman;
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
import danger.orespawn.client.render.RenderInfo;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.ai.MyEntityAIWanderALot;

public class TrooperBug extends EntityMob {

    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private int force_sync = 50;
    private int hurt_timer = 0;
    private float moveSpeed = 0.4F;

    public TrooperBug(World world) {
        super(world);
        this.setSize(3.0F, 3.5F);
        this.getNavigator()
            .setAvoidsWater(true);
        this.experienceValue = 150;
        this.fireResistance = 100;
        this.isImmuneToFire = false;
        this.TargetSorter = new GenericTargetSorter(this);
        this.renderdata = new RenderInfo();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMoveThroughVillage(this, 0.9F, false));
        this.tasks.addTask(2, new MyEntityAIWanderALot(this, 14, 1.0));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
        if (this.renderdata == null) {
            this.renderdata = new RenderInfo();
        }

        this.renderdata.rf1 = 0.0F;
        this.renderdata.rf2 = 0.0F;
        this.renderdata.rf3 = 0.0F;
        this.renderdata.rf4 = 0.0F;
        this.renderdata.ri1 = 0;
        this.renderdata.ri2 = 0;
        this.renderdata.ri3 = 0;
        this.renderdata.ri4 = 0;
        this.force_sync = 50;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(OreSpawnMain.TrooperBug_stats.attack);
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
        if (this.isAirBorne) {
            this.getNavigator()
                .setPath(null, 0.0);
        }
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.TrooperBug_stats.health;
    }

    public RenderInfo getRenderInfo() {
        return this.renderdata;
    }

    public void setRenderInfo(RenderInfo r) {
        this.renderdata.rf1 = r.rf1;
        this.renderdata.rf2 = r.rf2;
        this.renderdata.rf3 = r.rf3;
        this.renderdata.rf4 = r.rf4;
        this.renderdata.ri1 = r.ri1;
        this.renderdata.ri2 = r.ri2;
        this.renderdata.ri3 = r.ri3;
        this.renderdata.ri4 = r.ri4;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.TrooperBug_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    protected void jump() {
        this.motionY++;
        this.posY++;
        float f = 0.2F + Math.abs(this.worldObj.rand.nextFloat() * 0.45F);
        this.motionX = this.motionX - f * Math.sin(Math.toRadians(this.rotationYawHead));
        this.motionZ = this.motionZ + f * Math.cos(Math.toRadians(this.rotationYawHead));
        this.isAirBorne = true;
    }

    protected void jumpAtEntity(EntityLivingBase e) {
        this.motionY++;
        this.posY++;
        float f = 0.3F + Math.abs(this.worldObj.rand.nextFloat() * 0.25F);
        float d = (float) Math.atan2(e.posX - this.posX, e.posZ - this.posZ);
        this.motionX = this.motionX + f * Math.sin(d);
        this.motionZ = this.motionZ + f * Math.cos(d);
        this.isAirBorne = true;
    }

    public int getTrooperBugHealth() {
        return (int) this.getHealth();
    }

    protected String getLivingSound() {
        return this.rand.nextInt(4) == 0 ? "orespawn:clatter" : null;
    }

    protected String getHurtSound() {
        return "orespawn:crunch";
    }

    protected String getDeathSound() {
        return "orespawn:emperorscorpion_death";
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
        this.dropItemRand(OreSpawnMain.MyJumpyBugScale, 1);
        this.dropItemRand(Items.item_frame, 1);
        int i = 2 + this.worldObj.rand.nextInt(5);

        for (int i2 = 0; i2 < i; i2++) {
            this.dropItemRand(OreSpawnMain.MyAmethyst, 1);
        }

        i = 1 + this.worldObj.rand.nextInt(5);

        for (int i2 = 0; i2 < i; i2++) {
            int roll = this.worldObj.rand.nextInt(14);
            switch (roll) {
                case 0:
                case 1:
                case 12:
                default:
                    break;
                case 2: {
                    ItemStack is = this.dropItemRand(Item.getItemFromBlock(OreSpawnMain.MyBlockAmethystBlock), 1);
                    break;
                }
                case 3:
                    ItemStack stack = this.dropItemRand(OreSpawnMain.MyAmethystSword, 1);
                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack.addEnchantment(Enchantment.sharpness, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack.addEnchantment(Enchantment.baneOfArthropods, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack.addEnchantment(Enchantment.knockback, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack.addEnchantment(Enchantment.looting, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(2) == 1) {
                        stack.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack.addEnchantment(Enchantment.fireAspect, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack.addEnchantment(Enchantment.sharpness, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 4:
                    ItemStack stack2 = this.dropItemRand(OreSpawnMain.MyAmethystShovel, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        stack2.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack2.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 5:
                    ItemStack stack3 = this.dropItemRand(OreSpawnMain.MyAmethystPickaxe, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        stack3.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack3.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack3.addEnchantment(Enchantment.fortune, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 6:
                    ItemStack stack4 = this.dropItemRand(OreSpawnMain.MyAmethystAxe, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        stack4.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack4.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 7:
                    ItemStack stack5 = this.dropItemRand(OreSpawnMain.MyAmethystHoe, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        stack5.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack5.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 8:
                    ItemStack stack6 = this.dropItemRand(OreSpawnMain.AmethystHelmet, 1);
                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack6.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack6.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack6.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack6.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(2) == 1) {
                        stack6.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack6.addEnchantment(Enchantment.respiration, 1 + this.worldObj.rand.nextInt(2));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack6.addEnchantment(Enchantment.aquaAffinity, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 9:
                    ItemStack stack7 = this.dropItemRand(OreSpawnMain.AmethystBody, 1);
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
                    break;
                case 10:
                    ItemStack stack8 = this.dropItemRand(OreSpawnMain.AmethystLegs, 1);
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
                case 11: {
                    ItemStack is = this.dropItemRand(OreSpawnMain.AmethystBoots, 1);
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

    public void initCreature() {}

    public boolean interact(EntityPlayer player) {
        return false;
    }

    public boolean attackEntityAsMob(Entity target) {
        double ks = 1.8;
        double inair = 0.2;
        int n = 6;
        if (!super.attackEntityAsMob(target)) {
            return false;
        } else {
            if (target != null && target instanceof EntityLivingBase) {
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
        if (this.hurt_timer > 0) {
            return false;
        } else {
            if (!source.getDamageType()
                .equals("cactus")
                && !source.getDamageType()
                    .equals("fall")) {
                ret = super.attackEntityFrom(source, amount);
                this.hurt_timer = 20;
                Entity e = source.getEntity();
                if (e != null && e instanceof EntityLiving) {
                    this.setAttackTarget((EntityLiving) e);
                    this.setTarget(e);
                    this.getNavigator()
                        .tryMoveToEntityLiving((EntityLiving) e, 1.2);
                    ret = true;
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

            if (this.worldObj.rand.nextInt(5) == 0) {
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
                    if (this.worldObj.rand.nextInt(10) == 1 && !this.isAirBorne) {
                        this.jumpAtEntity(e);
                    } else if (this.getDistanceSqToEntity(e) < (5.0F + e.width / 2.0F) * (5.0F + e.width / 2.0F)) {
                        this.setAttacking(1);
                        if (this.worldObj.rand.nextInt(6) == 0 || this.worldObj.rand.nextInt(7) == 1) {
                            this.attackEntityAsMob(e);
                            if (!this.worldObj.isRemote) {
                                if (this.worldObj.rand.nextInt(3) == 1) {
                                    this.worldObj.playSoundAtEntity(e, "orespawn:scorpion_attack", 1.4F, 1.0F);
                                } else {
                                    this.worldObj.playSoundAtEntity(e, "orespawn:clatter", 1.0F, 1.0F);
                                }
                            }
                        }
                    } else if (!this.isAirBorne) {
                        this.getNavigator()
                            .tryMoveToEntityLiving(e, 1.2);
                    }

                    if (this.worldObj.rand.nextInt(30) == 1) {
                        EntityCreature newent = (EntityCreature) spawnCreature(
                            this.worldObj,
                            "Spit Bug",
                            (this.posX + e.posX) / 2.0 + this.worldObj.rand.nextInt(5) - this.worldObj.rand.nextInt(5),
                            (this.posY + e.posY) / 2.0 + 1.01,
                            (this.posZ + e.posZ) / 2.0 + this.worldObj.rand.nextInt(5) - this.worldObj.rand.nextInt(5));
                    }
                } else {
                    this.setAttacking(0);
                }
            }

            if (this.worldObj.rand.nextInt(150) == 1 && this.getHealth() < this.mygetMaxHealth()) {
                this.heal(1.0F);
            }
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
            } else if (target instanceof Hydrolisc) {
                return false;
            } else if (target instanceof EnderReaper) {
                return false;
            } else if (target instanceof EnderKnight) {
                return false;
            } else if (target instanceof EntityEnderman) {
                return false;
            } else if (target instanceof EntityCreeper) {
                return false;
            } else if (target instanceof TrooperBug) {
                return false;
            } else if (target instanceof SpitBug) {
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
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(12.0, 7.0, 12.0));
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
                        if (s != null && s.equals("Jumpy Bug")) {
                            return true;
                        }
                    }
                }
            }
        }

        if (!this.isValidLightLevel()) {
            return false;
        } else if (this.worldObj.isDaytime() && this.worldObj.rand.nextInt(20) > 1) {
            return false;
        } else {
            for (int i = -2; i < 2; i++) {
                for (int j = -2; j < 2; j++) {
                    for (int ix = 1; ix < 5; ix++) {
                        Block bid = this.worldObj
                            .getBlock((int) this.posX + j, (int) this.posY + ix, (int) this.posZ + i);
                        if (bid != Blocks.air) {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    }
}
