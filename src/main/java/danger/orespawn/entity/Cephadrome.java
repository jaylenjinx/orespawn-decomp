package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.client.render.RenderInfo;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.ai.MyEntityAIWanderALot;

public class Cephadrome extends EntityCreature {

    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private double boatYawHead;
    private int damage_counter = 100;
    private int updateit = 1;
    private int color = 1;
    private int playing = 0;
    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private int hurt_timer = 0;
    private int wasfed;
    private int shouldattack = 0;
    private int wing_sound = 0;
    private int hit_by_player = 0;
    private int badmood = 0;
    private float moveSpeed = 0.25F;

    public Cephadrome(World world) {
        super(world);
        this.setSize(2.5F, 2.25F);
        this.getNavigator()
            .setAvoidsWater(true);
        this.experienceValue = 200;
        this.fireResistance = 100;
        this.isImmuneToFire = false;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new MyEntityAIWanderALot(this, 16, 1.0));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 9.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.riddenByEntity = null;
        this.TargetSorter = new GenericTargetSorter(this);
        this.renderdata = new RenderInfo();
    }

    public Cephadrome(World world, double x, double y, double z) {
        this(world);
        this.setPosition(x, y + this.yOffset, z);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
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
            .setBaseValue(70.0);
    }

    public boolean shouldRiderSit() {
        return true;
    }

    public int getTrackingRange() {
        return 128;
    }

    public int getUpdateFrequency() {
        return 10;
    }

    public boolean sendsVelocityUpdates() {
        return true;
    }

    protected void fall(float distance) {}

    protected void updateFallState(double distanceFallen, boolean onGround) {}

    protected boolean canTriggerWalking() {
        return true;
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
        this.dataWatcher.addObject(21, (byte) 0);
        this.setActivity(0);
        this.setAttacking(0);
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
    }

    public int mygetMaxHealth() {
        return 300;
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
        return 16;
    }

    protected void jump() {
        super.jump();
        this.motionY += 0.1;
    }

    public boolean isAIEnabled() {
        return true;
    }

    public String getLivingSound() {
        return this.getActivity() != 1 && this.rand.nextInt(6) == 1 ? "orespawn:MothraWings" : null;
    }

    protected String getHurtSound() {
        return "orespawn:alo_hurt";
    }

    protected String getDeathSound() {
        return "orespawn:alo_death";
    }

    protected float getSoundVolume() {
        return 1.5F;
    }

    public float getSoundPitch() {
        return 1.0F;
    }

    public boolean canBePushed() {
        return false;
    }

    public double getMountedYOffset() {
        return 2.5;
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
        int i = 4 + this.worldObj.rand.nextInt(6);

        for (int i2 = 0; i2 < i; i2++) {
            this.dropItemRand(OreSpawnMain.UraniumNugget, 1);
        }

        i = 4 + this.worldObj.rand.nextInt(6);

        for (int i2 = 0; i2 < i; i2++) {
            this.dropItemRand(OreSpawnMain.TitaniumNugget, 1);
        }

        i = 1 + this.worldObj.rand.nextInt(5);

        for (int i2 = 0; i2 < i; i2++) {
            int roll = this.worldObj.rand.nextInt(20);
            switch (roll) {
                case 0: {
                    ItemStack is = this.dropItemRand(OreSpawnMain.MyRubySword, 1);
                    break;
                }
                case 1:
                    ItemStack stack = this.dropItemRand(Items.diamond, 1);
                    break;
                case 2:
                    ItemStack stack2 = this.dropItemRand(OreSpawnMain.MyThunderStaff, 1);
                    break;
                case 3:
                    ItemStack stack3 = this.dropItemRand(OreSpawnMain.MyRubySword, 1);
                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack3.addEnchantment(Enchantment.sharpness, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack3.addEnchantment(Enchantment.baneOfArthropods, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack3.addEnchantment(Enchantment.knockback, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack3.addEnchantment(Enchantment.looting, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(2) == 1) {
                        stack3.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack3.addEnchantment(Enchantment.fireAspect, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack3.addEnchantment(Enchantment.sharpness, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 4:
                    ItemStack stack4 = this.dropItemRand(OreSpawnMain.MyRubyShovel, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        stack4.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack4.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 5:
                    ItemStack stack5 = this.dropItemRand(OreSpawnMain.MyRubyPickaxe, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        stack5.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack5.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack5.addEnchantment(Enchantment.fortune, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 6:
                    ItemStack stack6 = this.dropItemRand(OreSpawnMain.MyRubyAxe, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        stack6.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack6.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 7:
                    ItemStack stack7 = this.dropItemRand(OreSpawnMain.MyRubyHoe, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        stack7.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack7.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 8:
                    ItemStack stack8 = this.dropItemRand(OreSpawnMain.RubyHelmet, 1);
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

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack8.addEnchantment(Enchantment.respiration, 1 + this.worldObj.rand.nextInt(2));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack8.addEnchantment(Enchantment.aquaAffinity, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 9:
                    ItemStack stack9 = this.dropItemRand(OreSpawnMain.RubyBody, 1);
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
                case 10:
                    ItemStack stack10 = this.dropItemRand(OreSpawnMain.RubyLegs, 1);
                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack10.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack10.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack10.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack10.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(2) == 1) {
                        stack10.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }
                    break;
                case 11:
                    ItemStack stack11 = this.dropItemRand(OreSpawnMain.RubyBoots, 1);
                    if (this.worldObj.rand.nextInt(6) == 1) {
                        stack11.addEnchantment(Enchantment.featherFalling, 5 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(2) == 1) {
                        stack11.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }
                    break;
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17: {
                    ItemStack is = this.dropItemRand(OreSpawnMain.MyRuby, 1);
                }
            }
        }
    }

    public int getCephadromeHealth() {
        return (int) this.getHealth();
    }

    public boolean attackEntityAsMob(Entity target) {
        double ks = 2.5;
        double inair = 0.35;
        float iskraken = 1.0F;
        boolean ret = false;
        if (target != null && target instanceof EntityDragon) {
            EntityDragon dr = (EntityDragon) target;
            DamageSource source = null;
            source = DamageSource.setExplosionSource(null);
            source.setExplosion();
            if (this.worldObj.rand.nextInt(6) == 1) {
                dr.attackEntityFromPart(dr.dragonPartHead, source, 70.0F);
            } else {
                dr.attackEntityFromPart(dr.dragonPartBody, source, 70.0F);
            }

            ret = true;
        } else if (target != null && target instanceof EntityLivingBase) {
            if (target instanceof Kraken) {
                iskraken = 1.5F;
            }

            ret = target.attackEntityFrom(DamageSource.causeMobDamage(this), iskraken * 70.0F);
            float f3 = (float) Math.atan2(target.posZ - this.posZ, target.posX - this.posX);
            if (target.isDead || target instanceof EntityPlayer) {
                inair *= 2.0;
            }

            target.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
        }

        return ret;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = false;
        if (this.hurt_timer > 0) {
            return false;
        } else {
            if (!source.getDamageType()
                .equals("cactus")) {
                ret = super.attackEntityFrom(source, amount);
                this.hurt_timer = 25;
                Entity e = source.getEntity();
                if (e != null && e instanceof EntityLivingBase) {
                    this.setAttackTarget((EntityLivingBase) e);
                    this.setTarget(e);
                    this.getNavigator()
                        .tryMoveToEntityLiving((EntityLivingBase) e, 1.2);
                    ret = true;
                }

                if (e != null && e instanceof EntityPlayer && this.getHealth() < this.getMaxHealth() * 9.0F / 10.0F) {
                    this.hit_by_player = 1;
                }
            }

            return ret;
        }
    }

    public double getHorizontalDistanceSqToEntity(Entity entity) {
        double d0 = this.posX - entity.posX;
        double d2 = this.posZ - entity.posZ;
        return d0 * d0 + d2 * d2;
    }

    public void updateAITasks() {
        EntityLivingBase e = null;
        double maxdist = 10.0;
        if (!this.isDead) {
            if (this.updateit > 0) {
                this.updateit--;
            }

            if (this.hurt_timer > 0) {
                this.hurt_timer--;
            }

            if (this.updateit <= 0 && !this.worldObj.isRemote) {
                this.updateit = 30;
                if (this.riddenByEntity != null) {
                    this.setActivity(1);
                } else {
                    this.setActivity(0);
                }
            }

            if (this.worldObj.rand.nextInt(100) == 1 && this.getHealth() < this.mygetMaxHealth()) {
                this.heal(2.0F);
            }

            if (this.getActivity() == 0) {
                super.updateAITasks();
            }

            if (this.worldObj.rand.nextInt(7) == 1 && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) {
                e = this.getAttackTarget();
                if (e != null && !e.isEntityAlive()) {
                    this.setAttackTarget(null);
                    e = null;
                }

                if (e == null) {
                    e = this.findSomethingToAttack();
                }

                if (e != null) {
                    if (this.getActivity() == 0) {
                        this.getNavigator()
                            .tryMoveToEntityLiving(e, 1.7);
                        maxdist = 6.0;
                    }

                    this.faceEntity(e, 10.0F, 10.0F);
                    this.setAttacking(1);
                    if (this.getDistanceSqToEntity(e) < (maxdist + e.width / 2.0F) * (maxdist + e.width / 2.0F)) {
                        this.attackEntityAsMob(e);
                    } else if (e instanceof Kraken && this.getHorizontalDistanceSqToEntity(e)
                        < (maxdist + e.width / 2.0F) * (maxdist + e.width / 2.0F)) {
                            this.attackEntityAsMob(e);
                        }
                } else if (this.getAttacking() != 0) {
                    this.setAttacking(0);
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
            } else if (target instanceof Cephadrome) {
                return false;
            } else if (target instanceof EntityMob) {
                return true;
            } else if (target instanceof Mothra) {
                return true;
            } else if (target instanceof Leon) {
                EntityTameable et = (EntityTameable) target;
                return !et.isTamed();
            } else if (target instanceof GammaMetroid) {
                EntityTameable et = (EntityTameable) target;
                return !et.isTamed();
            } else if (target instanceof WaterDragon) {
                EntityTameable et = (EntityTameable) target;
                return !et.isTamed();
            } else if (target instanceof EntityDragon) {
                return true;
            } else if (target instanceof EntityPlayer) {
                EntityPlayer p = (EntityPlayer) target;
                if (p.capabilities.isCreativeMode) {
                    return false;
                } else if (this.hit_by_player != 0) {
                    return true;
                } else if (this.badmood != 0) {
                    return true;
                } else if (this.shouldattack > 0) {
                    this.shouldattack = 0;
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(16.0, 20.0, 16.0));
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
                        if (s != null && s.equals("Cephadrome")) {
                            this.badmood = 1;
                            return true;
                        }
                    }
                }
            }
        }

        if (!this.worldObj.isDaytime()) {
            return false;
        } else if (this.posY < 50.0) {
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

            Cephadrome target = null;
            target = (Cephadrome) this.worldObj
                .findNearestEntityWithinAABB(Cephadrome.class, this.boundingBox.expand(16.0, 6.0, 16.0), this);
            return target == null;
        }
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch,
        int posRotationIncrements) {
        super.setPositionAndRotation2(x, y, z, yaw, pitch, posRotationIncrements);
        this.boatPosRotationIncrements = posRotationIncrements;
        this.boatX = x;
        this.boatY = y;
        this.boatZ = z;
        this.boatYaw = yaw;
        this.boatPitch = pitch;
        this.boatYawHead = yaw;
    }

    @SideOnly(Side.CLIENT)
    public void setVelocity(double vx, double vy, double vz) {
        super.setVelocity(vx, vy, vz);
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
        if (this.getActivity() == 1) {
            this.wing_sound++;
            if (this.wing_sound > 22) {
                if (!this.worldObj.isRemote) {
                    this.worldObj.playSoundAtEntity(this, "orespawn:MothraWings", 0.5F, 1.0F);
                }

                this.wing_sound = 0;
            }
        }

        if (OreSpawnMain.PlayNicely == 0) {
            this.wasfed = 1;
        }
    }

    public void onLivingUpdate() {
        List list = null;
        Entity listEntity = null;
        double d6 = this.rand.nextFloat() * 2.0F - 1.0F;
        double d7 = (this.rand.nextInt(2) * 2 - 1) * 0.7;
        double obstruction_factor = 0.0;
        double relative_g = 0.0;
        double max_speed = 1.15;
        double gh = 1.0;
        double rt = 0.0;
        double pi = 3.1415926545;
        double deltav = 0.0;
        int dist = 2;
        if (this.getActivity() == 0) {
            super.onLivingUpdate();
        } else if (this.isDead) {
            super.onLivingUpdate();
            return;
        }

        if (!this.isDead) {
            if (this.worldObj.isRemote) {
                if (this.boatPosRotationIncrements > 0 && this.getActivity() != 0) {
                    double d4 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
                    double d5 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
                    double d11 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
                    this.setPosition(d4, d5, d11);
                    this.rotationPitch = (float) (this.rotationPitch
                        + (this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
                    double d10 = MathHelper.wrapAngleTo180_double(this.boatYaw - this.rotationYaw);
                    if (this.riddenByEntity != null) {
                        d10 = MathHelper
                            .wrapAngleTo180_double((double) this.riddenByEntity.rotationYaw - this.rotationYaw);
                    }

                    this.rotationYaw = (float) (this.rotationYaw + d10 / this.boatPosRotationIncrements);
                    this.setRotation(this.rotationYaw, this.rotationPitch);
                    this.boatPosRotationIncrements--;
                }
            } else if (this.getActivity() != 0) {
                if (this.riddenByEntity != null) {
                    EntityPlayer pp = (EntityPlayer) this.riddenByEntity;
                    if (this.motionX < -2.0) {
                        this.motionX = -2.0;
                    }

                    if (this.motionX > 2.0) {
                        this.motionX = 2.0;
                    }

                    if (this.motionZ < -2.0) {
                        this.motionZ = -2.0;
                    }

                    if (this.motionZ > 2.0) {
                        this.motionZ = 2.0;
                    }

                    double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                    gh = 1.55;
                    Block bid = this.worldObj
                        .getBlock((int) this.posX, (int) ((float) this.posY - (float) gh), (int) this.posZ);
                    if (bid != Blocks.air) {
                        this.motionY += 0.07;
                        this.posY += 0.1;
                    } else {
                        this.motionY -= 0.018;
                    }

                    obstruction_factor = 0.0;
                    int n = 2;
                    n += (int) (velocity * 6.0);

                    for (int k = 1; k < n; k++) {
                        for (int i = 1; i < n * 2; i++) {
                            double dx = i * Math.cos(Math.toRadians(this.rotationYaw + 90.0F));
                            double dz = i * Math.sin(Math.toRadians(this.rotationYaw + 90.0F));
                            bid = this.worldObj
                                .getBlock((int) (this.posX + dx), (int) this.posY - k, (int) (this.posZ + dz));
                            if (bid != Blocks.air) {
                                obstruction_factor += 0.04;
                            }
                        }
                    }

                    this.motionY += obstruction_factor * 0.09;
                    this.posY += obstruction_factor * 0.09;
                    if (this.motionY > 2.0) {
                        this.motionY = 2.0;
                    }

                    double d4 = this.riddenByEntity.rotationYaw;
                    d4 %= 360.0;

                    while (d4 < 0.0) {
                        d4 += 360.0;
                    }

                    double d5 = this.rotationYaw;
                    d5 %= 360.0;

                    while (d5 < 0.0) {
                        d5 += 360.0;
                    }

                    relative_g = (d4 - d5) % 180.0;

                    while (relative_g < 0.0) {
                        relative_g += 180.0;
                    }

                    if (relative_g > 90.0) {
                        relative_g -= 180.0;
                    }

                    if (velocity > 0.1) {
                        d4 = 1.5 - velocity;
                        d4 = Math.abs(d4);
                        if (d4 < 0.01) {
                            d4 = 0.01;
                        }

                        if (d4 > 0.9) {
                            d4 = 0.9;
                        }

                        this.rotationYaw = this.riddenByEntity.rotationYaw + (float) (relative_g * d4);
                    } else {
                        this.rotationYaw = this.riddenByEntity.rotationYaw;
                    }

                    relative_g = Math.abs(relative_g) * velocity;
                    if (relative_g > 50.0) {
                        relative_g = 0.0;
                    }

                    if (this.motionY > 0.0) {
                        this.rotationPitch = 360.0F - 2.0F * (float) velocity;
                    } else {
                        this.rotationPitch = 2.0F * (float) velocity;
                    }

                    this.setRotation(this.rotationYaw, this.rotationPitch);
                    double newvelocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                    double rr = Math.atan2(this.riddenByEntity.motionZ, this.riddenByEntity.motionX);
                    double rhm = Math.atan2(this.motionZ, this.motionX);
                    double rhdir = Math.toRadians((this.riddenByEntity.rotationYaw + 90.0F) % 360.0F);
                    rt = 0.0;
                    pi = 3.1415926545;
                    deltav = 0.0;
                    double im = pp.moveForward;
                    if (OreSpawnMain.flyup_keystate != 0) {
                        this.motionY += 0.04;
                        this.motionY += velocity * 0.05;
                    }

                    double rdv = Math.abs(rhm - rhdir) % (pi * 2.0);
                    if (rdv > pi) {
                        rdv -= pi * 2.0;
                    }

                    rdv = Math.abs(rdv);
                    if (Math.abs(newvelocity) < 0.01) {
                        rdv = 0.0;
                    }

                    if (rdv > 1.5) {
                        newvelocity = -newvelocity;
                    }

                    if (Math.abs(im) > 0.001F) {
                        if (im > 0.0) {
                            deltav = 0.03;
                            if (max_speed > 0.85) {
                                deltav += 0.05;
                            }
                        } else {
                            max_speed = 0.35;
                            deltav = -0.03;
                        }

                        newvelocity += deltav;
                        if (newvelocity >= 0.0) {
                            if (newvelocity > max_speed) {
                                newvelocity = max_speed;
                            }

                            this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
                            this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
                        } else {
                            if (newvelocity < -max_speed) {
                                newvelocity = -max_speed;
                            }

                            newvelocity = -newvelocity;
                            this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0F)) * newvelocity;
                            this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0F)) * newvelocity;
                        }
                    } else if (newvelocity >= 0.0) {
                        this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
                        this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
                    } else {
                        this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0F)) * (newvelocity * -1.0);
                        this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0F)) * (newvelocity * -1.0);
                    }
                }

                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.985;
                this.motionY *= 0.94;
                this.motionZ *= 0.985;
                if (!this.worldObj.isRemote) {
                    list = this.worldObj
                        .getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(2.25, 2.0, 2.25));
                    if (list != null && !list.isEmpty()) {
                        for (int l = 0; l < list.size(); l++) {
                            listEntity = (Entity) list.get(l);
                            if (listEntity != this.riddenByEntity && !listEntity.isDead && listEntity.canBePushed()) {
                                listEntity.applyEntityCollision(this);
                            }
                        }
                    }
                }

                if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                    this.riddenByEntity = null;
                }
            }

            if (this.getActivity() == 1) {
                this.updateAITasks();
            }
        }
    }

    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            float f = 0.75F;
            this.riddenByEntity.setPosition(
                this.posX - f * Math.sin(Math.toRadians(this.rotationYaw)),
                this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(),
                this.posZ + f * Math.cos(Math.toRadians(this.rotationYaw)));
        }
    }

    protected void playTameEffect(boolean success) {
        String s = "heart";
        if (!success) {
            s = "smoke";
        }

        for (int i = 0; i < 20; i++) {
            double d0 = this.rand.nextGaussian() * 0.08;
            double d1 = this.rand.nextGaussian() * 0.08;
            double d2 = this.rand.nextGaussian() * 0.08;
            this.worldObj.spawnParticle(
                s,
                this.posX + (this.rand.nextFloat() - this.rand.nextFloat()) * 2.5F,
                this.posY + 0.5 + this.rand.nextFloat() * 1.5,
                this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()) * 2.5F,
                d0,
                d1,
                d2);
        }
    }

    public boolean interact(EntityPlayer player) {
        ItemStack currentItem = player.inventory.getCurrentItem();
        if (currentItem != null && currentItem.stackSize <= 0) {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
            currentItem = null;
        }

        if (currentItem != null
            && (currentItem.getItem() == Items.beef || currentItem.getItem() == Items.chicken
                || currentItem.getItem() == Items.porkchop)
            && player.getDistanceSqToEntity(this) < 25.0) {
            if (!this.worldObj.isRemote) {
                this.heal(this.mygetMaxHealth() - this.getHealth());
            }

            this.wasfed = 1;
            this.shouldattack = 0;
            this.playTameEffect(true);
            if (!player.capabilities.isCreativeMode) {
                currentItem.stackSize--;
                if (currentItem.stackSize <= 0) {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                    ItemStack stack = null;
                }
            }

            return false;
        } else if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer
            && this.riddenByEntity != player) {
                return true;
            } else {
                if (currentItem == null && player.getDistanceSqToEntity(this) < 25.0 && !this.worldObj.isRemote) {
                    if (this.wasfed == 0) {
                        this.getNavigator()
                            .tryMoveToEntityLiving(player, 1.2);
                        this.shouldattack = 1;
                        return false;
                    }

                    player.mountEntity(this);
                    this.wasfed = 0;
                }

                return true;
            }
    }

    public int getAttacking() {
        return this.dataWatcher.getWatchableObjectByte(20);
    }

    public void setAttacking(int attacking) {
        if (this.worldObj == null || !this.worldObj.isRemote) {
            this.dataWatcher.updateObject(20, (byte) attacking);
        }
    }

    public int getActivity() {
        return this.dataWatcher.getWatchableObjectByte(21);
    }

    public void setActivity(int activity) {
        if (this.worldObj == null || !this.worldObj.isRemote) {
            this.dataWatcher.updateObject(21, (byte) activity);
        }
    }

    protected boolean canDespawn() {
        return this.isNoDespawnRequired() ? false : this.riddenByEntity == null;
    }

    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger("CephaWasFed", this.wasfed);
        tag.setInteger("CephaAttacking", this.getAttacking());
        tag.setInteger("CephaActivity", this.getActivity());
        tag.setInteger("CephaHitByPlayer", this.hit_by_player);
        tag.setInteger("CephaBadMood", this.badmood);
    }

    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        this.wasfed = tag.getInteger("CephaWasFed");
        this.hit_by_player = tag.getInteger("CephaHitByPlayer");
        this.badmood = tag.getInteger("CephaBadMood");
        this.setAttacking(tag.getInteger("CephaAttacking"));
        this.setActivity(tag.getInteger("CephaActivity"));
    }
}
