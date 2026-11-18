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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.ai.MyEntityAIWanderALot;

public class Crab extends EntityMob {

    private GenericTargetSorter TargetSorter = null;
    private int hurt_timer = 0;
    private float moveSpeed = 0.55F;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public Crab(World world) {
        super(world);
        this.moveSpeed = 0.55F;
        this.setSize(1.25F, 2.5F);
        this.getNavigator()
            .setAvoidsWater(false);
        this.experienceValue = 150;
        this.fireResistance = 30;
        this.isImmuneToFire = false;
        this.TargetSorter = new GenericTargetSorter(this);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new MyEntityAIWanderALot(this, 16, 1.0));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed * this.getCrabScale());
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(OreSpawnMain.Crab_stats.attack * this.getCrabScale());
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
        this.dataWatcher.addObject(21, 0);
        float t = 0.25F;
        if (this.worldObj != null) {
            if (this.worldObj.rand.nextInt(4) == 1) {
                t = 0.5F;
            }

            if (this.worldObj.rand.nextInt(8) == 2) {
                t = 1.0F;
            }
        } else {
            if (OreSpawnMain.OreSpawnRand.nextInt(4) == 1) {
                t = 0.5F;
            }

            if (OreSpawnMain.OreSpawnRand.nextInt(8) == 2) {
                t = 1.0F;
            }
        }

        this.setCrabScale(t);
        this.experienceValue = (int) (400.0F * t);
        this.fireResistance = (int) (10.0F * t);
        this.setSize(3.75F * this.getCrabScale(), 3.5F * this.getCrabScale());
    }

    public float getCrabScale() {
        int i = this.dataWatcher.getWatchableObjectInt(21);
        float f = i;
        return f / 100.0F;
    }

    public void setCrabScale(float f2) {
        float f = f2 * 100.0F;
        int i = (int) f;
        this.dataWatcher.updateObject(21, i);
    }

    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        this.setCrabScale(tag.getFloat("Fscale"));
        this.setSize(3.75F * this.getCrabScale(), 3.5F * this.getCrabScale());
        this.experienceValue = (int) (400.0F * this.getCrabScale());
        this.fireResistance = (int) (10.0F * this.getCrabScale());
    }

    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setFloat("Fscale", this.getCrabScale());
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public void onUpdate() {
        if (this.isInWater()) {
            this.moveSpeed = 0.95F;
        } else {
            this.moveSpeed = 0.55F;
        }

        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed * this.getCrabScale());
        super.onUpdate();
        this.setSize(2.5F * this.getCrabScale(), 3.5F * this.getCrabScale());
    }

    public int mygetMaxHealth() {
        return (int) (OreSpawnMain.PitchBlack_stats.health * this.getCrabScale());
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.Crab_stats.defense + (int) (2.0F * this.getCrabScale());
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    public int getCrabHealth() {
        return (int) this.getHealth();
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return "orespawn:leaves_hit";
    }

    protected String getDeathSound() {
        return null;
    }

    protected float getSoundVolume() {
        return 0.75F;
    }

    protected float getSoundPitch() {
        return 2.0F - 0.3F * (1.0F / this.getCrabScale());
    }

    protected Item getDropItem() {
        return Items.fish;
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
        int n = 4 + this.worldObj.rand.nextInt(8);
        n = (int) (n * this.getCrabScale());
        if (n < 1) {
            n = 1;
        }

        for (int i = 0; i < n; i++) {
            this.dropItemRand(OreSpawnMain.MyRawCrabMeat, 1);
        }
    }

    public void initCreature() {}

    public boolean interact(EntityPlayer player) {
        return false;
    }

    public boolean attackEntityAsMob(Entity target) {
        boolean flag = target
            .attackEntityFrom(DamageSource.causeMobDamage(this), OreSpawnMain.Crab_stats.attack * this.getCrabScale());
        if (flag && target != null && target instanceof EntityLivingBase) {
            double ks = 1.15 * this.getCrabScale();
            double inair = 0.48 * this.getCrabScale();
            float f3 = (float) Math.atan2(target.posZ - this.posZ, target.posX - this.posX);
            if (target.isDead || target instanceof EntityPlayer) {
                inair *= 2.0;
            }

            target.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
        }

        return flag;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = false;
        if (source.getDamageType()
            .equals("cactus")) {
            return false;
        } else {
            Entity e = source.getEntity();
            if (this.hurt_timer <= 0) {
                ret = super.attackEntityFrom(source, amount);
                this.hurt_timer = 8;
            }

            if (e != null && e instanceof EntityLiving) {
                if (e instanceof Crab) {
                    return false;
                }

                this.setAttackTarget((EntityLiving) e);
                this.setTarget(e);
                this.getNavigator()
                    .tryMoveToEntityLiving((EntityLiving) e, 1.2);
            }

            return ret;
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
            if (this.hurt_timer > 0) {
                this.hurt_timer--;
            }

            if (!this.isInWater() && this.worldObj.rand.nextInt(25) == 0) {
                this.closest = 99999;
                this.tx = this.ty = this.tz = 0;

                for (int i = 1; i < 12; i++) {
                    int j = i;
                    if (i > 10) {
                        j = 10;
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
                    if (this.worldObj.rand.nextInt(100) == 1) {
                        this.heal(-1.0F * this.getCrabScale());
                    }

                    if (this.getHealth() <= 0.0F) {
                        this.setDead();
                        return;
                    }
                }
            }

            if (this.worldObj.rand.nextInt(5) == 1) {
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
                    if (this.getDistanceSqToEntity(e)
                        < (6.0F + e.width / 2.0F) * (6.0F + e.width / 2.0F) * this.getCrabScale()) {
                        this.setAttacking(1);
                        if (this.worldObj.rand.nextInt(4) == 0 || this.worldObj.rand.nextInt(5) == 1) {
                            this.attackEntityAsMob(e);
                            if (!this.worldObj.isRemote) {
                                if (this.worldObj.rand.nextInt(3) == 1) {
                                    this.worldObj.playSoundAtEntity(e, "orespawn:scorpion_attack", 0.75F, 1.5F);
                                } else {
                                    this.worldObj.playSoundAtEntity(e, "orespawn:scorpion_living", 0.75F, 1.5F);
                                }
                            }
                        }
                    } else {
                        this.getNavigator()
                            .tryMoveToEntityLiving(e, 1.0);
                    }
                } else {
                    this.setAttacking(0);
                }
            }

            if (this.worldObj.rand.nextInt(120) == 1 && this.isInWater() && this.getHealth() < this.mygetMaxHealth()) {
                this.playSound("splash", 1.5F, this.worldObj.rand.nextFloat() * 0.2F + 0.9F);
                this.heal(4.0F * this.getCrabScale());
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
            } else if (target instanceof Crab) {
                return false;
            } else if (target instanceof EntityMob) {
                return true;
            } else if (target instanceof Lizard) {
                return true;
            } else if (target instanceof RubberDucky) {
                return true;
            } else if (target instanceof EntityVillager) {
                return true;
            } else if (target instanceof Girlfriend) {
                return true;
            } else {
                return target instanceof Boyfriend ? true : MyUtils.isAttackableNonMob(target);
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
        List entities = this.worldObj.getEntitiesWithinAABB(Crab.class, this.boundingBox.expand(24.0, 8.0, 24.0));
        return entities.size();
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
                        if (s != null && s.equals("Crab")) {
                            this.setCrabScale(0.35F);
                            return true;
                        }
                    }
                }
            }
        }

        if (this.posY < 50.0) {
            return false;
        } else if (!this.worldObj.isDaytime()) {
            return false;
        } else {
            if (this.worldObj.provider.dimensionId == OreSpawnMain.DimensionID5) {
                if (this.worldObj.rand.nextInt(40) != 1) {
                    return false;
                }

                if (this.findBuddies() > 3) {
                    return false;
                }
            }

            return true;
        }
    }

    public boolean canBreatheUnderwater() {
        return true;
    }
}
