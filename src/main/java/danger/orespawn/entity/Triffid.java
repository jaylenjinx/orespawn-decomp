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
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.client.render.RenderInfo;
import danger.orespawn.entity.ai.GenericTargetSorter;

public class Triffid extends EntityMob {

    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private int hurt_timer = 0;
    private float moveSpeed = 0.13F;

    public Triffid(World world) {
        super(world);
        this.setSize(2.0F, 4.0F);
        this.getNavigator()
            .setAvoidsWater(true);
        this.experienceValue = 50;
        this.fireResistance = 75;
        this.isImmuneToFire = false;
        this.TargetSorter = new GenericTargetSorter(this);
        this.renderdata = new RenderInfo();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
        this.dataWatcher.addObject(21, (byte) 0);
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

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(OreSpawnMain.Triffid_stats.attack);
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
        if (this.worldObj.rand.nextInt(100) == 1) {
            int ix = (int) this.posX;
            int iz = (int) this.posZ;

            for (int k = -5; k <= 5; k++) {
                Block bid = this.worldObj.getBlock((int) this.posX, (int) this.posY - 1, (int) this.posZ + k);
                if (bid != Blocks.air) {
                    if (k < 0) {
                        iz--;
                    }

                    if (k > 0) {
                        iz++;
                    }
                }
            }

            for (int i = -5; i <= 5; i++) {
                Block bid = this.worldObj.getBlock((int) this.posX + i, (int) this.posY - 1, (int) this.posZ);
                if (bid != Blocks.air) {
                    if (i < 0) {
                        ix--;
                    }

                    if (i > 0) {
                        ix++;
                    }
                }
            }

            this.getNavigator()
                .tryMoveToXYZ(ix, this.posY, iz, 1.0);
        }

        if (this.hurt_timer <= 0) {
            EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
                this.rotationYaw = (float) Math.toDegrees(Math.atan2(e.posZ - this.posZ, e.posX - this.posX)) - 90.0F;

                while (this.rotationYaw < 0.0F) {
                    this.rotationYaw += 360.0F;
                }
            }
        }
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.Triffid_stats.health;
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
        return OreSpawnMain.Triffid_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.worldObj.isRemote && this.hurt_timer > 0) {
            this.motionX = this.motionZ = 0.0;
        }
    }

    public int getTriffidHealth() {
        return (int) this.getHealth();
    }

    protected String getLivingSound() {
        return "orespawn:triffid_living";
    }

    protected String getHurtSound() {
        return "orespawn:triffid_hit";
    }

    protected String getDeathSound() {
        return "orespawn:triffid_dead";
    }

    protected float getSoundVolume() {
        return 0.75F;
    }

    protected float getSoundPitch() {
        return 1.0F;
    }

    protected Item getDropItem() {
        int i = this.worldObj.rand.nextInt(3);
        return i == 0 ? Items.gold_nugget : null;
    }

    private ItemStack dropItemRand(Item index, int n) {
        EntityItem entityItem = null;
        ItemStack is = new ItemStack(index, n, 0);
        entityItem = new EntityItem(
            this.worldObj,
            this.posX + OreSpawnMain.OreSpawnRand.nextInt(3) - OreSpawnMain.OreSpawnRand.nextInt(3),
            this.posY + 1.0,
            this.posZ + OreSpawnMain.OreSpawnRand.nextInt(3) - OreSpawnMain.OreSpawnRand.nextInt(3),
            is);
        if (entityItem != null) {
            this.worldObj.spawnEntityInWorld(entityItem);
        }

        return is;
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        int i = 4 + this.worldObj.rand.nextInt(6);

        for (int i2 = 0; i2 < i; i2++) {
            this.dropItemRand(OreSpawnMain.GreenGoo, 1);
        }

        this.dropItemRand(Items.item_frame, 1);
    }

    public boolean canBePushed() {
        return false;
    }

    public boolean attackEntityAsMob(Entity target) {
        return super.attackEntityAsMob(target);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = false;
        if (this.hurt_timer <= 0 && this.getOpenClosed() != 0) {
            ret = super.attackEntityFrom(source, amount);
            this.hurt_timer = 300;
            this.setOpenClosed(0);
            this.setAttacking(0);
            return ret;
        } else {
            this.hurt_timer = 300;
            this.setAttacking(0);
            return false;
        }
    }

    protected void updateAITasks() {
        if (!this.isDead) {
            super.updateAITasks();
            if (this.hurt_timer > 0) {
                this.hurt_timer--;
                this.setFire(0);
                this.setOpenClosed(0);
            }

            if (this.worldObj.rand.nextInt(250) == 1 && this.getHealth() < this.mygetMaxHealth()) {
                this.heal(1.0F);
            }

            if (this.worldObj.rand.nextInt(80) == 2 && this.hurt_timer <= 0) {
                if (this.worldObj.rand.nextInt(8) == 1) {
                    this.setOpenClosed(1);
                } else {
                    this.setOpenClosed(0);
                }
            }

            if (this.worldObj.rand.nextInt(10) == 1 && this.hurt_timer <= 0) {
                EntityLivingBase e = this.findSomethingToAttack();
                if (e != null) {
                    this.setOpenClosed(1);
                    if (this.getDistanceSqToEntity(e) < 25.0) {
                        this.rotationYaw = (float) Math.toDegrees(Math.atan2(e.posZ - this.posZ, e.posX - this.posX))
                            - 90.0F;

                        while (this.rotationYaw < 0.0F) {
                            this.rotationYaw += 360.0F;
                        }

                        this.setAttacking(1);
                        this.attackEntityAsMob(e);
                    } else {
                        this.setAttacking(0);
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
        } else if (MyUtils.isIgnoreable(target)) {
            return false;
        } else if (!this.getEntitySenses()
            .canSee(target)) {
                return false;
            } else if (target instanceof EntityCreeper) {
                return false;
            } else if (target instanceof EnderReaper) {
                return false;
            } else if (target instanceof Triffid) {
                return false;
            } else if (target instanceof TerribleTerror) {
                return false;
            } else if (target instanceof LurkingTerror) {
                return false;
            } else if (target instanceof PitchBlack) {
                return false;
            } else if (target instanceof Dragon) {
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
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(10.0, 8.0, 10.0));
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

    public final int getOpenClosed() {
        return this.dataWatcher.getWatchableObjectByte(21);
    }

    public final void setOpenClosed(int n) {
        this.dataWatcher.updateObject(21, (byte) n);
    }

    public boolean getCanSpawnHere() {
        return true;
    }
}
