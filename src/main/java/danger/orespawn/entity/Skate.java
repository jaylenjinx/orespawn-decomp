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
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.ai.MyEntityAIWander;

public class Skate extends EntityMob {

    private GenericTargetSorter TargetSorter = null;
    private EntityLivingBase buddy = null;
    private float moveSpeed = 0.25F;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public Skate(World world) {
        super(world);
        this.setSize(0.75F, 0.25F);
        this.getNavigator()
            .setAvoidsWater(false);
        this.experienceValue = 10;
        this.fireResistance = 3;
        this.isImmuneToFire = false;
        this.TargetSorter = new GenericTargetSorter(this);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new MyEntityAIWander(this, 1.0F));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(OreSpawnMain.Skate_stats.attack);
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
        return OreSpawnMain.Skate_stats.health;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.Skate_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    public int getAttackStrength(Entity entity) {
        return 4;
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return null;
    }

    protected String getDeathSound() {
        return "orespawn:ratdead";
    }

    protected float getSoundVolume() {
        return 0.33F;
    }

    protected float getSoundPitch() {
        return 1.75F;
    }

    protected Item getDropItem() {
        return Items.string;
    }

    public void initCreature() {}

    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = false;
        if (this.isDead) {
            return false;
        } else {
            Entity e = source.getEntity();
            if (e != null && e instanceof Skate) {
                return false;
            } else {
                if (e != null && e instanceof EntityLiving) {
                    if (e instanceof Skate) {
                        return false;
                    }

                    this.setAttackTarget((EntityLiving) e);
                    this.setTarget(e);
                    this.getNavigator()
                        .tryMoveToEntityLiving((EntityLiving) e, 1.2);
                    ret = true;
                }

                return super.attackEntityFrom(source, amount);
            }
        }
    }

    private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {
        int found = 0;

        for (int i = -dy; i <= dy; i++) {
            for (int j = -dz; j <= dz; j++) {
                Block bid = this.worldObj.getBlock(x + dx, y + i, z + j);
                if (bid == Blocks.water || bid == Blocks.flowing_water) {
                    int d = dx * dx + j * j + i * i;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x + dx;
                        this.ty = y + i;
                        this.tz = z + j;
                        found++;
                    }
                }

                bid = this.worldObj.getBlock(x - dx, y + i, z + j);
                if (bid == Blocks.water || bid == Blocks.flowing_water) {
                    int d = dx * dx + j * j + i * i;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x - dx;
                        this.ty = y + i;
                        this.tz = z + j;
                        found++;
                    }
                }
            }
        }

        for (int i = -dx; i <= dx; i++) {
            for (int j = -dz; j <= dz; j++) {
                Block bidx = this.worldObj.getBlock(x + i, y + dy, z + j);
                if (bidx == Blocks.water || bidx == Blocks.flowing_water) {
                    int d = dy * dy + j * j + i * i;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x + i;
                        this.ty = y + dy;
                        this.tz = z + j;
                        found++;
                    }
                }

                bidx = this.worldObj.getBlock(x + i, y - dy, z + j);
                if (bidx == Blocks.water || bidx == Blocks.flowing_water) {
                    int d = dy * dy + j * j + i * i;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x + i;
                        this.ty = y - dy;
                        this.tz = z + j;
                        found++;
                    }
                }
            }
        }

        for (int i = -dx; i <= dx; i++) {
            for (int j = -dy; j <= dy; j++) {
                Block bidxx = this.worldObj.getBlock(x + i, y + j, z + dz);
                if (bidxx == Blocks.water || bidxx == Blocks.flowing_water) {
                    int d = dz * dz + j * j + i * i;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x + i;
                        this.ty = y + j;
                        this.tz = z + dz;
                        found++;
                    }
                }

                bidxx = this.worldObj.getBlock(x + i, y + j, z - dz);
                if (bidxx == Blocks.water || bidxx == Blocks.flowing_water) {
                    int d = dz * dz + j * j + i * i;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x + i;
                        this.ty = y + j;
                        this.tz = z - dz;
                        found++;
                    }
                }
            }
        }

        return found != 0;
    }

    protected void updateAITasks() {
        if (!this.isDead) {
            super.updateAITasks();
            if (!this.isInWater() && this.worldObj.rand.nextInt(10) == 0) {
                this.closest = 99999;
                this.tx = this.ty = this.tz = 0;

                for (int i = 1; i < 12; i++) {
                    int j = i;
                    if (i > 5) {
                        j = 5;
                    }

                    if (this.scan_it((int) this.posX, (int) this.posY - 1, (int) this.posZ, i, j, i)) {
                        break;
                    }

                    if (i >= 5) {
                        i++;
                    }
                }

                if (this.closest < 99999) {
                    this.getNavigator()
                        .tryMoveToXYZ(this.tx, this.ty - 1, this.tz, 1.33);
                } else {
                    if (this.worldObj.rand.nextInt(25) == 1) {
                        this.heal(-1.0F);
                    }

                    if (this.getHealth() <= 0.0F) {
                        this.setDead();
                        return;
                    }
                }
            }

            if (this.worldObj.rand.nextInt(8) == 1) {
                EntityLivingBase e = this.findSomethingToAttack();
                if (e != null) {
                    if (this.getDistanceSqToEntity(e) < 4.0) {
                        this.setAttacking(1);
                        if (this.worldObj.rand.nextInt(4) == 0 || this.worldObj.rand.nextInt(5) == 1) {
                            this.attackEntityAsMob(e);
                        }
                    } else {
                        this.getNavigator()
                            .tryMoveToEntityLiving(e, 1.2);
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
            } else if (target instanceof EntityPlayer) {
                EntityPlayer p = (EntityPlayer) target;
                return !p.capabilities.isCreativeMode;
            } else {
                return false;
            }
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(10.0, 4.0, 10.0));
            Collections.sort(entities, this.TargetSorter);
            Iterator iterator = entities.iterator();
            Entity entity = null;
            EntityLivingBase target = null;
            EntityLivingBase e = this.getAttackTarget();
            if (e != null && e.isEntityAlive()) {
                return e;
            } else {
                this.setAttackTarget(null);

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
    }

    public final int getAttacking() {
        return this.dataWatcher.getWatchableObjectByte(20);
    }

    public final void setAttacking(int attacking) {
        this.dataWatcher.updateObject(20, (byte) attacking);
    }

    private int findBuddies() {
        List entities = this.worldObj.getEntitiesWithinAABB(Skate.class, this.boundingBox.expand(16.0, 8.0, 16.0));
        return entities.size();
    }

    public boolean getCanSpawnHere() {
        if (this.posY < 50.0) {
            return false;
        } else if (!this.worldObj.isDaytime()) {
            return false;
        } else {
            return this.worldObj.rand.nextInt(30) != 1 ? false : this.findBuddies() <= 6;
        }
    }
}
