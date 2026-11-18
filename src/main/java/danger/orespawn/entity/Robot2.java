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
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.client.render.RenderInfo;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.ai.MyEntityAIWanderALot;

public class Robot2 extends EntityMob {

    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private int just_for_fun = 0;
    private float moveSpeed = 0.3F;

    public Robot2(World world) {
        super(world);
        this.setSize(3.0F, 6.2F);
        this.getNavigator()
            .setAvoidsWater(true);
        this.experienceValue = 100;
        this.fireResistance = 200;
        this.isImmuneToFire = true;
        this.TargetSorter = new GenericTargetSorter(this);
        this.renderdata = new RenderInfo();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new MyEntityAIWanderALot(this, 14, 1.0));
        this.tasks.addTask(2, new EntityAIMoveThroughVillage(this, 0.9F, false));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
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
            .setBaseValue(OreSpawnMain.Robot2_stats.attack);
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
        return OreSpawnMain.Robot2_stats.health;
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
        return OreSpawnMain.Robot2_stats.defense;
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
        return Item.getItemFromBlock(Blocks.iron_block);
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
        int n = 2 + this.worldObj.rand.nextInt(8);

        for (int i = 0; i < n; i++) {
            this.dropItemRand(Item.getItemFromBlock(Blocks.iron_block), 1);
        }

        n = 5 + this.worldObj.rand.nextInt(6);

        for (int i = 0; i < n; i++) {
            this.dropItemRand(Items.iron_ingot, 1);
        }

        int i = 5 + this.worldObj.rand.nextInt(10);

        for (int i2 = 0; i2 < i; i2++) {
            int roll = this.worldObj.rand.nextInt(15);
            switch (roll) {
                case 0:
                    is = this.dropItemRand(Items.redstone, 1);
                    break;
                case 1:
                    is = this.dropItemRand(Items.repeater, 1);
                    break;
                case 2:
                    is = this.dropItemRand(Items.comparator, 1);
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
                    is = this.dropItemRand(Item.getItemFromBlock(Blocks.redstone_block), 1);
                    break;
                case 9:
                    is = this.dropItemRand(Item.getItemFromBlock(Blocks.light_weighted_pressure_plate), 1);
            }
        }
    }

    public boolean interact(EntityPlayer player) {
        return false;
    }

    public boolean attackEntityAsMob(Entity target) {
        return super.attackEntityAsMob(target);
    }

    protected void destroyBlock(EntityLivingBase e) {
        double x = e.posX + this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat();
        double y = e.posY - 1.0;
        double z = e.posZ + this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat();
        Block bid = this.worldObj.getBlock((int) x, (int) y, (int) z);
        if (bid != Blocks.obsidian) {
            if (bid != Blocks.bedrock) {
                if (bid != Blocks.quartz_block) {
                    if (bid != Blocks.mob_spawner) {
                        if (bid != Blocks.redstone_block) {
                            if (bid != Blocks.iron_block) {
                                if (bid != Blocks.chest) {
                                    if (bid != Blocks.air && this.worldObj.getGameRules()
                                        .getGameRuleBooleanValue("mobGriefing")) {
                                        this.worldObj.setBlock((int) x, (int) y, (int) z, Blocks.air);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    protected void destroyNearbyBlocks() {
        for (int i = 0; i < 50; i++) {
            double x = this.posX + this.worldObj.rand.nextFloat() * 6.5 - this.worldObj.rand.nextFloat() * 6.5;
            double y = this.posY + 0.1 + this.worldObj.rand.nextFloat() * 8.5;
            double z = this.posZ + this.worldObj.rand.nextFloat() * 6.5 - this.worldObj.rand.nextFloat() * 6.5;
            Block bid = this.worldObj.getBlock((int) x, (int) y, (int) z);
            if (bid != Blocks.obsidian && bid != Blocks.bedrock
                && bid != Blocks.quartz_block
                && bid != Blocks.mob_spawner
                && bid != Blocks.redstone_block
                && bid != Blocks.iron_block
                && bid != Blocks.chest
                && bid != Blocks.air
                && this.worldObj.getGameRules()
                    .getGameRuleBooleanValue("mobGriefing")) {
                this.worldObj.setBlock((int) x, (int) y, (int) z, Blocks.air);
            }
        }
    }

    protected void updateAITasks() {
        if (!this.isDead) {
            super.updateAITasks();
            if (this.worldObj.rand.nextInt(6) == 1 && OreSpawnMain.PlayNicely == 0) {
                EntityLivingBase e = null;
                if (this.worldObj.rand.nextInt(50) == 1) {
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
                    double rr = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                    double rhdir = Math.toRadians((this.rotationYaw + 90.0F) % 360.0F);
                    double pi = 3.1415926545;
                    double rdd = Math.abs(rr - rhdir) % (pi * 2.0);
                    if (rdd > pi) {
                        rdd -= pi * 2.0;
                    }

                    rdd = Math.abs(rdd);
                    this.faceEntity(e, 10.0F, 10.0F);
                    if (rdd < 1.25) {
                        if (this.getDistanceSqToEntity(e) < (5.0F + e.width / 2.0F) * (5.0F + e.width / 2.0F)) {
                            this.setAttacking(1);
                            if (this.worldObj.rand.nextInt(5) == 0 || this.worldObj.rand.nextInt(6) == 1) {
                                this.attackEntityAsMob(e);

                                for (int i = 0; i < 6; i++) {
                                    this.destroyBlock(e);
                                }
                            }

                            this.destroyNearbyBlocks();
                        }
                    } else {
                        this.setAttacking(0);
                    }

                    this.getNavigator()
                        .tryMoveToEntityLiving(e, 1.0);
                } else {
                    this.setAttacking(0);
                }
            }

            if (this.getAttacking() == 0 && OreSpawnMain.PlayNicely == 0) {
                if (this.worldObj.rand.nextInt(450) == 1) {
                    this.just_for_fun = 50;
                }

                if (this.just_for_fun > 0) {
                    this.just_for_fun--;
                }

                if (this.just_for_fun > 0) {
                    this.setAttacking(1);
                    if (this.worldObj.rand.nextInt(3) == 1) {
                        this.destroyNearbyBlocks();
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
            Entity e = source.getEntity();
            if (e != null && e instanceof EntityLiving) {
                this.setAttackTarget((EntityLiving) e);
                this.setTarget(e);
                this.getNavigator()
                    .tryMoveToEntityLiving((EntityLiving) e, 1.2);
            }

            return ret;
        } else {
            return false;
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
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(14.0, 3.0, 14.0));
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
                        if (s != null && s.equals("Robo-Pounder")) {
                            return true;
                        }
                    }
                }
            }
        }

        if (this.posY < 50.0) {
            return false;
        } else if (this.worldObj.isDaytime()) {
            return false;
        } else {
            for (int i = -1; i < 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    for (int ix = 1; ix < 6; ix++) {
                        Block bid = this.worldObj
                            .getBlock((int) this.posX + j, (int) this.posY + ix, (int) this.posZ + i);
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
