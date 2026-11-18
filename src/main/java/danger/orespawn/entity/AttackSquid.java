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
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.ai.MyEntityAIWanderALot;
import danger.orespawn.entity.projectile.InkSack;
import danger.orespawn.entity.projectile.WaterBall;

public class AttackSquid extends EntityMob {

    private GenericTargetSorter TargetSorter = null;
    private EntityLivingBase buddy = null;
    private float moveSpeed = 0.25F;
    private int wasshot = 0;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public AttackSquid(World world) {
        super(world);
        this.setSize(1.0F, 1.25F);
        this.getNavigator()
            .setAvoidsWater(false);
        this.experienceValue = 15;
        this.fireResistance = 3;
        this.isImmuneToFire = false;
        this.TargetSorter = new GenericTargetSorter(this);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new MyEntityAIWanderALot(this, 16, 1.0));
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
            .setBaseValue(OreSpawnMain.AttackSquid_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public void setWasShot() {
        this.wasshot = 250;
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.AttackSquid_stats.health;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.AttackSquid_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    public int getAttackStrength(Entity entity) {
        return 2;
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return "orespawn:squid_hurt";
    }

    protected String getDeathSound() {
        return "orespawn:squid_death";
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

    protected float getSoundVolume() {
        return 1.0F;
    }

    protected float getSoundPitch() {
        return 1.0F;
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
        int roll = this.worldObj.rand.nextInt(50);
        switch (roll) {
            case 0:
                is = this.dropItemRand(Items.gold_nugget, 1);
                break;
            case 1:
                is = this.dropItemRand(Items.gold_ingot, 1);
                break;
            case 2:
                is = this.dropItemRand(Items.golden_carrot, 1);
                break;
            case 3:
                is = this.dropItemRand(Items.golden_sword, 1);
                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.sharpness, 1 + this.worldObj.rand.nextInt(5));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.baneOfArthropods, 1 + this.worldObj.rand.nextInt(5));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.knockback, 1 + this.worldObj.rand.nextInt(5));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.looting, 1 + this.worldObj.rand.nextInt(5));
                }

                if (this.worldObj.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.fireAspect, 1 + this.worldObj.rand.nextInt(5));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.sharpness, 1 + this.worldObj.rand.nextInt(5));
                }
                break;
            case 4:
                is = this.dropItemRand(Items.golden_shovel, 1);
                if (this.worldObj.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                }
                break;
            case 5:
                is = this.dropItemRand(Items.golden_pickaxe, 1);
                if (this.worldObj.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.fortune, 1 + this.worldObj.rand.nextInt(5));
                }
                break;
            case 6:
                is = this.dropItemRand(Items.golden_axe, 1);
                if (this.worldObj.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                }
                break;
            case 7:
                is = this.dropItemRand(Items.golden_hoe, 1);
                if (this.worldObj.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                }
                break;
            case 8:
                is = this.dropItemRand(Items.golden_helmet, 1);
                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
                }

                if (this.worldObj.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.respiration, 1 + this.worldObj.rand.nextInt(2));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.aquaAffinity, 1 + this.worldObj.rand.nextInt(5));
                }
                break;
            case 9:
                is = this.dropItemRand(Items.golden_chestplate, 1);
                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
                }

                if (this.worldObj.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                }
                break;
            case 10:
                is = this.dropItemRand(Items.golden_leggings, 1);
                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
                }

                if (this.worldObj.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                }
                break;
            case 11:
                is = this.dropItemRand(Items.golden_boots, 1);
                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.featherFalling, 5 + this.worldObj.rand.nextInt(5));
                }

                if (this.worldObj.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                }
                break;
            case 12:
                this.dropItemRand(Items.golden_apple, 1);
                break;
            case 13:
                this.dropItemRand(Item.getItemFromBlock(Blocks.gold_block), 1);
                break;
            case 14:
                EntityItem entityItem = null;
                is = new ItemStack(Items.golden_apple, 1, 1);
                entityItem = new EntityItem(
                    this.worldObj,
                    this.posX + OreSpawnMain.OreSpawnRand.nextInt(3) - OreSpawnMain.OreSpawnRand.nextInt(3),
                    this.posY + 1.0,
                    this.posZ + OreSpawnMain.OreSpawnRand.nextInt(3) - OreSpawnMain.OreSpawnRand.nextInt(3),
                    is);
                if (entityItem != null) {
                    this.worldObj.spawnEntityInWorld(entityItem);
                }
                break;
            case 15:
            case 16:
            case 17:
                this.dropItemRand(Items.dye, 1);
        }

        int i = 1 + this.worldObj.rand.nextInt(3);

        for (int i2 = 0; i2 < i; i2++) {
            this.dropItemRand(Items.fish, 1);
        }
    }

    public void initCreature() {}

    public boolean interact(EntityPlayer player) {
        return false;
    }

    public boolean attackEntityAsMob(Entity target) {
        return super.attackEntityAsMob(target);
    }

    protected void fall(float distance) {
        if (this.wasshot == 0) {
            super.fall(distance);
        }
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = false;
        if (this.isDead) {
            return false;
        } else {
            Entity e = source.getEntity();
            if (e != null && e instanceof AttackSquid) {
                return false;
            } else if (e != null && e instanceof WaterBall) {
                return false;
            } else if (e != null && e instanceof WaterDragon) {
                return false;
            } else {
                if (e != null && e instanceof EntityLiving) {
                    if (e instanceof AttackSquid) {
                        return false;
                    }

                    if (e instanceof WaterDragon) {
                        return false;
                    }

                    this.setAttackTarget((EntityLiving) e);
                    this.setTarget(e);
                    this.getNavigator()
                        .tryMoveToEntityLiving((EntityLiving) e, 1.2);
                    ret = true;
                }

                ret = super.attackEntityFrom(source, amount);
                if ((this.getHealth() <= 0.0F || this.isDead)
                    && this.worldObj.provider.dimensionId != OreSpawnMain.DimensionID5
                    && !this.worldObj.isRemote
                    && e != null
                    && e instanceof EntityPlayer
                    && this.worldObj.rand.nextInt(15) == 1
                    && OreSpawnMain.KrakenEnable != 0
                    && this.wasshot == 0) {
                    int j = 1 + this.worldObj.rand.nextInt(3);

                    for (int i = 0; i < j; i++) {
                        EntityCreature newent = (EntityCreature) spawnCreature(
                            this.worldObj,
                            "The Kraken",
                            this.posX + this.worldObj.rand.nextInt(4) - this.worldObj.rand.nextInt(4),
                            170.0,
                            this.posZ + this.worldObj.rand.nextInt(4) - this.worldObj.rand.nextInt(4));
                    }
                }

                return ret;
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
            if (this.wasshot > 0) {
                this.wasshot--;
                if (this.wasshot == 0) {
                    this.setDead();
                    return;
                }
            }

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

            if (this.worldObj.rand.nextInt(10) == 1) {
                EntityLivingBase e = this.findSomethingToAttack();
                if (e != null) {
                    if (this.getDistanceSqToEntity(e) < 9.0) {
                        this.setAttacking(1);
                        if (this.worldObj.rand.nextInt(4) == 0 || this.worldObj.rand.nextInt(5) == 1) {
                            this.attackEntityAsMob(e);
                        }
                    } else {
                        this.getNavigator()
                            .tryMoveToEntityLiving(e, 1.2);
                        this.watercanon(e);
                    }
                } else {
                    if (this.buddy != null) {
                        this.getNavigator()
                            .tryMoveToEntityLiving(this.buddy, 1.0);
                    }

                    this.setAttacking(0);
                }
            }
        }
    }

    private void watercanon(EntityLivingBase e) {
        double yoff = 1.0;
        double xzoff = 1.2;
        if (this.worldObj.rand.nextInt(5) == 1) {
            if (this.rand.nextInt(3) == 1) {
                InkSack inkSack = new InkSack(
                    this.worldObj,
                    e.posX - this.posX,
                    e.posY + 0.75 - (this.posY + yoff),
                    e.posZ - this.posZ);
                inkSack.setLocationAndAngles(
                    this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYawHead)),
                    this.posY + yoff,
                    this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)),
                    this.rotationYawHead,
                    this.rotationPitch);
                double dx = e.posX - this.posX;
                double dy = e.posY + 0.25 - inkSack.posY;
                double dz = e.posZ - this.posZ;
                float f = MathHelper.sqrt_double(dx * dx + dz * dz) * 0.2F;
                inkSack.setThrowableHeading(dx, dy + f, dz, 1.4F, 5.0F);
                this.worldObj.playSoundAtEntity(
                    this,
                    "random.bow",
                    0.75F,
                    1.0F / (this.getRNG()
                        .nextFloat() * 0.4F + 0.8F));
                this.worldObj.spawnEntityInWorld(inkSack);
            } else {
                WaterBall waterBall = new WaterBall(
                    this.worldObj,
                    e.posX - this.posX,
                    e.posY + 0.75 - (this.posY + yoff),
                    e.posZ - this.posZ);
                waterBall.setLocationAndAngles(
                    this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYawHead)),
                    this.posY + yoff,
                    this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)),
                    this.rotationYawHead,
                    this.rotationPitch);
                double dx = e.posX - this.posX;
                double dy = e.posY + 0.25 - waterBall.posY;
                double dz = e.posZ - this.posZ;
                float f = MathHelper.sqrt_double(dx * dx + dz * dz) * 0.2F;
                waterBall.setThrowableHeading(dx, dy + f, dz, 1.4F, 5.0F);
                this.worldObj.playSoundAtEntity(
                    this,
                    "random.bow",
                    0.75F,
                    1.0F / (this.getRNG()
                        .nextFloat() * 0.4F + 0.8F));
                this.worldObj.spawnEntityInWorld(waterBall);
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
            } else if (target instanceof Girlfriend) {
                return true;
            } else if (target instanceof Boyfriend) {
                return true;
            } else if (target instanceof EntityZombie) {
                return true;
            } else if (target instanceof EntityVillager) {
                return true;
            } else if (target instanceof EntitySpider) {
                return true;
            } else if (target instanceof EntityCaveSpider) {
                return true;
            } else if (target instanceof Ghost) {
                return false;
            } else if (target instanceof GhostSkelly) {
                return false;
            } else if (target instanceof Lizard) {
                return true;
            } else if (target instanceof AttackSquid) {
                if (this.worldObj.rand.nextInt(5) == 1) {
                    this.buddy = target;
                }

                return false;
            } else {
                return this.wasshot != 0;
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

    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger("WasShot", this.wasshot);
    }

    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        this.wasshot = tag.getInteger("WasShot");
    }

    public boolean getCanSpawnHere() {
        super.getCanSpawnHere();
        return this.posY < 50.0 ? false : this.worldObj.isDaytime();
    }
}
