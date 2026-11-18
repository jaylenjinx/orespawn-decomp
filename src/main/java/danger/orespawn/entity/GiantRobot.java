package danger.orespawn.entity;

import java.util.Collections;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.client.render.RenderGiantRobotInfo;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.ai.MyEntityAIWanderALot;
import danger.orespawn.entity.projectile.LaserBall;

public class GiantRobot extends EntityMob {

    private GenericTargetSorter TargetSorter = null;
    private RenderGiantRobotInfo renderdata = new RenderGiantRobotInfo();
    private int reload_ticker = 0;
    private float moveSpeed = 0.55F;

    public GiantRobot(World world) {
        super(world);
        this.setSize(3.0F, 9.75F);
        this.getNavigator()
            .setAvoidsWater(true);
        this.experienceValue = OreSpawnMain.Jeffery_stats.health / 2;
        this.fireResistance = 40;
        this.isImmuneToFire = true;
        this.TargetSorter = new GenericTargetSorter(this);
        this.renderdata = new RenderGiantRobotInfo();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new MyEntityAIWanderALot(this, 14, 1.0));
        this.tasks.addTask(2, new EntityAIMoveThroughVillage(this, 0.9F, false));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
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
            .setBaseValue(OreSpawnMain.Jeffery_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
        this.initLegData();
    }

    private void initLegData() {
        if (this.renderdata == null) {
            this.renderdata = new RenderGiantRobotInfo();
        }

        this.renderdata.hipydisplayangle = 0.0F;
        this.renderdata.hipxdisplayangle = 0.0F;
        this.renderdata.gpcounter = 2000000;
        this.renderdata.thighdisplayangle[0] = 0.0F;
        this.renderdata.thighdisplayangle[1] = 0.0F;
        this.renderdata.shindisplayangle[0] = 0.0F;
        this.renderdata.shindisplayangle[1] = 0.0F;
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
        return OreSpawnMain.Jeffery_stats.health;
    }

    public RenderGiantRobotInfo getRenderGiantRobotInfo() {
        return this.renderdata;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.Jeffery_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    protected void jump() {
        this.motionY += 0.25;
        super.jump();
    }

    protected String getLivingSound() {
        return this.rand.nextInt(4) == 0 ? "orespawn:robot_living" : null;
    }

    protected String getHurtSound() {
        return "orespawn:robot_hurt";
    }

    protected String getDeathSound() {
        return "orespawn:robot_death";
    }

    protected float getSoundVolume() {
        return 1.0F;
    }

    protected float getSoundPitch() {
        return 1.0F;
    }

    protected Item getDropItem() {
        return Items.iron_ingot;
    }

    private ItemStack dropItemRand(Item index, int n) {
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

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        ItemStack is = null;
        int n = 15 + this.worldObj.rand.nextInt(15);

        for (int i = 0; i < n; i++) {
            this.dropItemRand(OreSpawnMain.MyLaserBall, 4);
        }

        int i = 10 + this.worldObj.rand.nextInt(10);

        for (int i2 = 0; i2 < i; i2++) {
            int roll = this.worldObj.rand.nextInt(12);
            switch (roll) {
                case 0:
                    is = this.dropItemRand(OreSpawnMain.SpiderRobotKit, 1);
                    break;
                case 1:
                    is = this.dropItemRand(OreSpawnMain.AntRobotKit, 1);
                    break;
                case 2:
                    is = this.dropItemRand(OreSpawnMain.MyRayGun, 1);
                    break;
                case 3:
                    is = this.dropItemRand(Item.getItemFromBlock(Blocks.redstone_block), 1);
                    break;
                case 4:
                    is = this.dropItemRand(Item.getItemFromBlock(Blocks.dispenser), 1);
                    break;
                case 5:
                    is = this.dropItemRand(Item.getItemFromBlock(Blocks.sticky_piston), 1);
                    break;
                case 6:
                    is = this.dropItemRand(Item.getItemFromBlock(Blocks.piston), 1);
                    break;
                case 7:
                    is = this.dropItemRand(Item.getItemFromBlock(Blocks.lever), 1);
                    break;
                case 8:
                    is = this.dropItemRand(Item.getItemFromBlock(Blocks.iron_block), 1);
                    break;
                case 9:
                    is = this.dropItemRand(Item.getItemFromBlock(Blocks.detector_rail), 1);
            }
        }
    }

    public boolean interact(EntityPlayer player) {
        return false;
    }

    public boolean attackEntityAsMob(Entity target) {
        double ks = 2.2;
        double inair = 0.25;
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

    protected void updateAITasks() {
        if (!this.isDead) {
            super.updateAITasks();
            if (this.reload_ticker > 0) {
                this.reload_ticker--;
            }

            if (this.worldObj.rand.nextInt(5) == 0) {
                EntityLivingBase e = null;
                if (this.worldObj.rand.nextInt(100) == 1) {
                    this.setAttackTarget(null);
                }

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
                    if (this.getDistanceSqToEntity(e) < 256.0) {
                        double rr = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                        double rhdir = Math.toRadians((this.rotationYawHead + 90.0F) % 360.0F);
                        double pi = 3.1415926545;
                        double rdd = Math.abs(rr - rhdir) % (pi * 2.0);
                        if (rdd > pi) {
                            rdd -= pi * 2.0;
                        }

                        rdd = Math.abs(rdd);
                        if (rdd < 0.5) {
                            if (this.reload_ticker == 0) {
                                double yoff = 10.0;
                                double xzoff = 3.75;
                                LaserBall laserBall = new LaserBall(
                                    this.worldObj,
                                    e.posX - this.posX,
                                    e.posY - (this.posY + yoff),
                                    e.posZ - this.posZ);
                                laserBall.setLocationAndAngles(
                                    this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYawHead)),
                                    this.posY + yoff,
                                    this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYawHead)),
                                    this.rotationYawHead,
                                    this.rotationPitch);
                                double dx = e.posX - laserBall.posX;
                                double dy = e.posY - laserBall.posY;
                                double dz = e.posZ - laserBall.posZ;
                                float f = MathHelper.sqrt_double(dx * dx + dz * dz) * 0.2F;
                                laserBall.setThrowableHeading(dx, dy + f, dz, 2.0F, 4.0F);
                                if (this.getDistanceSqToEntity(e) > 100.0) {
                                    laserBall.setSpecial();
                                    this.reload_ticker = 25;
                                    this.worldObj.playSoundAtEntity(this, "fireworks.launch", 3.5F, 0.5F);
                                } else {
                                    this.reload_ticker = 10;
                                    this.worldObj.playSoundAtEntity(this, "fireworks.launch", 2.5F, 1.0F);
                                }

                                this.worldObj.spawnEntityInWorld(laserBall);
                            }

                            if (this.getDistanceSqToEntity(e) < (8.0F + e.width / 2.0F) * (8.0F + e.width / 2.0F)) {
                                this.setAttacking(1);
                                this.attackEntityAsMob(e);
                            } else {
                                this.setAttacking(0);
                            }
                        }

                        this.getNavigator()
                            .tryMoveToEntityLiving(e, 0.5);
                    } else {
                        this.setAttacking(0);
                    }
                } else {
                    this.setAttacking(0);
                }
            }
        }
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = false;
        if (!source.getDamageType()
            .equals("cactus")) {
            ret = super.attackEntityFrom(source, amount);
        }

        Entity e = source.getEntity();
        if (e != null && e instanceof EntityLiving) {
            this.setAttackTarget((EntityLiving) e);
            this.setTarget(e);
        }

        return ret;
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
            } else if (target instanceof EntityMob) {
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
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(16.0, 12.0, 16.0));
            Collections.sort(entities, this.TargetSorter);

            for (Entity entity : (List<Entity>) entities) {
                EntityLivingBase target = (EntityLivingBase) entity;
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
        if (this.posY < 50.0) {
            return false;
        } else if (this.worldObj.isDaytime()) {
            return false;
        } else {
            for (int k = -1; k < 1; k++) {
                for (int j = -1; j <= 1; j++) {
                    for (int i = 1; i < 6; i++) {
                        Block bid = this.worldObj
                            .getBlock((int) this.posX + j, (int) this.posY + i, (int) this.posZ + k);
                        if (bid != Blocks.air && bid != Blocks.tallgrass) {
                            return false;
                        }
                    }
                }
            }

            return this.isValidLightLevel();
        }
    }
}
