package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.client.render.RenderInfo;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.ai.MyEntityAIFollowOwner;
import danger.orespawn.entity.ai.MyEntityAIWanderALot;
import danger.orespawn.entity.projectile.WaterBall;

public class WaterDragon extends EntityTameable {

    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private int stream_count = 0;
    private int hurt_timer = 0;
    private float moveSpeed = 0.25F;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public WaterDragon(World world) {
        super(world);
        this.moveSpeed = 0.25F;
        this.setSize(1.25F, 1.9F);
        this.getNavigator()
            .setAvoidsWater(false);
        this.experienceValue = 100;
        this.fireResistance = 3;
        this.isImmuneToFire = true;
        this.TargetSorter = new GenericTargetSorter(this);
        this.renderdata = new RenderInfo();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMate(this, 1.0));
        this.tasks.addTask(2, new MyEntityAIFollowOwner(this, 2.0F, 10.0F, 2.0F));
        this.tasks.addTask(3, new EntityAITempt(this, 1.2F, Items.fish, false));
        this.tasks.addTask(4, new MyEntityAIWanderALot(this, 16, 1.0));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
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
            .setBaseValue(OreSpawnMain.WaterDragon_stats.attack);
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

    public boolean interact(EntityPlayer player) {
        ItemStack currentItem = player.inventory.getCurrentItem();
        if (currentItem != null && currentItem.stackSize <= 0) {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
            currentItem = null;
        }

        if (super.interact(player)) {
            return true;
        } else if (currentItem != null && currentItem.getItem() == Items.fish
            && player.getDistanceSqToEntity(this) < 25.0) {
                if (!this.isTamed()) {
                    if (!this.worldObj.isRemote) {
                        if (this.rand.nextInt(3) == 0) {
                            this.setTamed(true);
                            this.func_152115_b(
                                player.getUniqueID()
                                    .toString());
                            this.playTameEffect(true);
                            this.worldObj.setEntityState(this, (byte) 7);
                            this.heal(this.mygetMaxHealth() - this.getHealth());
                        } else {
                            this.playTameEffect(false);
                            this.worldObj.setEntityState(this, (byte) 6);
                        }
                    }
                } else if (this.func_152114_e(player)) {
                    if (this.worldObj.isRemote) {
                        this.playTameEffect(true);
                        this.worldObj.setEntityState(this, (byte) 7);
                    }

                    if (this.mygetMaxHealth() > this.getHealth()) {
                        this.heal(this.mygetMaxHealth() - this.getHealth());
                    }
                }

                if (!player.capabilities.isCreativeMode) {
                    currentItem.stackSize--;
                    if (currentItem.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                    }
                }

                return true;
            } else if (this.isTamed() && currentItem != null
                && currentItem.getItem() == Item.getItemFromBlock(Blocks.deadbush)
                && player.getDistanceSqToEntity(this) < 25.0
                && this.func_152114_e(player)) {
                    if (!this.worldObj.isRemote) {
                        this.setTamed(false);
                        this.func_152115_b("");
                        this.playTameEffect(false);
                        this.worldObj.setEntityState(this, (byte) 6);
                    }

                    if (!player.capabilities.isCreativeMode) {
                        currentItem.stackSize--;
                        if (currentItem.stackSize <= 0) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                        }
                    }

                    return true;
                } else if (this.isTamed() && currentItem != null
                    && currentItem.getItem() == Items.name_tag
                    && player.getDistanceSqToEntity(this) < 16.0
                    && this.func_152114_e(player)) {
                        this.setCustomNameTag(currentItem.getDisplayName());
                        if (!player.capabilities.isCreativeMode) {
                            currentItem.stackSize--;
                            if (currentItem.stackSize <= 0) {
                                player.inventory
                                    .setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                            }
                        }

                        return true;
                    } else
                    if (this.isTamed() && this.func_152114_e(player) && player.getDistanceSqToEntity(this) < 25.0) {
                        if (!this.isSitting()) {
                            this.setSitting(true);
                        } else {
                            this.setSitting(false);
                        }

                        return true;
                    } else {
                        return false;
                    }
    }

    protected boolean canDespawn() {
        if (this.isChild()) {
            this.func_110163_bv();
            return false;
        } else {
            return this.isNoDespawnRequired() ? false : !this.isTamed();
        }
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.WaterDragon_stats.health;
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
        return OreSpawnMain.WaterDragon_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.isInWater()) {
            this.moveSpeed = 0.55F;
        } else {
            this.moveSpeed = 0.25F;
        }
    }

    public int getWaterDragonHealth() {
        return (int) this.getHealth();
    }

    public int getAttackStrength(Entity entity) {
        int n = 4;
        if (this.worldObj.difficultySetting == EnumDifficulty.EASY) {
            n = 6;
            if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL) {
                n = 8;
            } else if (this.worldObj.difficultySetting == EnumDifficulty.HARD) {
                n = 10;
            }
        }

        return n;
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return "orespawn:waterdragon_hurt";
    }

    protected String getDeathSound() {
        return "orespawn:waterdragon_death";
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
        this.dropItemRand(OreSpawnMain.MyWaterDragonScale, 1);
        this.dropItemRand(Items.item_frame, 1);
        int n = 9 + this.worldObj.rand.nextInt(6);

        for (int i = 0; i < n; i++) {
            this.dropItemRand(Items.fish, 1);
        }

        int roll = this.worldObj.rand.nextInt(20);
        switch (roll) {
            case 0:
                is = this.dropItemRand(OreSpawnMain.MyUltimateAxe, 1);
                break;
            case 1:
                is = this.dropItemRand(Items.iron_ingot, 1);
                break;
            case 2:
                is = this.dropItemRand(OreSpawnMain.MyUltimatePickaxe, 1);
                break;
            case 3:
                is = this.dropItemRand(Items.iron_sword, 1);
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
                is = this.dropItemRand(Items.iron_shovel, 1);
                if (this.worldObj.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                }
                break;
            case 5:
                is = this.dropItemRand(Items.iron_pickaxe, 1);
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
                is = this.dropItemRand(Items.iron_axe, 1);
                if (this.worldObj.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                }
                break;
            case 7:
                is = this.dropItemRand(Items.iron_hoe, 1);
                if (this.worldObj.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                }

                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                }
                break;
            case 8:
                is = this.dropItemRand(Items.iron_helmet, 1);
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
                is = this.dropItemRand(Items.iron_chestplate, 1);
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
                is = this.dropItemRand(Items.iron_leggings, 1);
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
                is = this.dropItemRand(Items.iron_boots, 1);
                if (this.worldObj.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.featherFalling, 5 + this.worldObj.rand.nextInt(5));
                }

                if (this.worldObj.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                }
                break;
            case 12:
                is = this.dropItemRand(OreSpawnMain.MyUltimateShovel, 1);
                break;
            case 13:
                this.dropItemRand(Item.getItemFromBlock(Blocks.iron_block), 1);
            case 14:
        }
    }

    public boolean attackEntityAsMob(Entity target) {
        boolean flag = target
            .attackEntityFrom(DamageSource.causeMobDamage(this), OreSpawnMain.WaterDragon_stats.attack);
        if (!flag) {
            return false;
        } else {
            if (target != null && target instanceof EntityLivingBase) {
                double ks = 1.1;
                double inair = 0.14;
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
        if (source.getDamageType()
            .equals("cactus")) {
            return false;
        } else {
            Entity e = source.getEntity();
            if (e != null && e instanceof WaterDragon) {
                return false;
            } else if (e != null && e instanceof AttackSquid) {
                return false;
            } else if (e != null && e instanceof WaterBall) {
                return false;
            } else {
                if (this.hurt_timer <= 0) {
                    ret = super.attackEntityFrom(source, amount);
                    this.hurt_timer = 10;
                }

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
            if (this.hurt_timer > 0) {
                this.hurt_timer--;
            }

            if (!this.isInWater() && this.worldObj.rand.nextInt(25) == 0 && !this.isSitting()) {
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
                    if (this.worldObj.rand.nextInt(50) == 1) {
                        this.heal(-1.0F);
                    }

                    if (this.getHealth() <= 0.0F) {
                        this.setDead();
                        return;
                    }
                }
            }

            if (this.worldObj.rand.nextInt(200) == 0) {
                this.setAttackTarget(null);
            }

            if (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && this.worldObj.rand.nextInt(5) == 1) {
                EntityLivingBase e = this.findSomethingToAttack();
                if (e != null) {
                    this.faceEntity(e, 10.0F, 10.0F);
                    if (this.getDistanceSqToEntity(e) < (4.0F + e.width / 2.0F) * (4.0F + e.width / 2.0F)) {
                        this.setAttacking(1);
                        if (this.worldObj.rand.nextInt(4) == 0 || this.worldObj.rand.nextInt(5) == 1) {
                            this.attackEntityAsMob(e);
                        }
                    } else {
                        this.getNavigator()
                            .tryMoveToEntityLiving(e, 1.0);
                        this.watercanon(e);
                    }
                } else {
                    this.setAttacking(0);
                }
            }

            if (this.worldObj.rand.nextInt(100) == 1 && this.isInWater() && this.getHealth() < this.mygetMaxHealth()) {
                this.playSound("splash", 1.5F, this.worldObj.rand.nextFloat() * 0.2F + 0.9F);
                this.heal(1.0F);
            }
        }
    }

    private void watercanon(EntityLivingBase e) {
        double yoff = 1.75;
        double xzoff = 1.5;
        if (this.stream_count > 0) {
            this.setAttacking(2);
            if (this.rand.nextInt(15) == 1) {
                EntitySmallFireball fireball = new EntitySmallFireball(
                    this.worldObj,
                    this,
                    e.posX - this.posX,
                    e.posY + 0.75 - (this.posY + yoff),
                    e.posZ - this.posZ);
                fireball.setLocationAndAngles(
                    this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYawHead)),
                    this.posY + yoff,
                    this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYawHead)),
                    this.rotationYaw,
                    this.rotationPitch);
                this.worldObj.playSoundAtEntity(
                    this,
                    "random.bow",
                    0.75F,
                    1.0F / (this.getRNG()
                        .nextFloat() * 0.4F + 0.8F));
                this.worldObj.spawnEntityInWorld(fireball);
            }

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
            double dx = e.posX - waterBall.posX;
            double dy = e.posY + 0.25 - waterBall.posY;
            double dz = e.posZ - waterBall.posZ;
            float f = MathHelper.sqrt_double(dx * dx + dz * dz) * 0.2F;
            waterBall.setThrowableHeading(dx, dy + f, dz, 1.4F, 5.0F);
            this.worldObj.playSoundAtEntity(
                this,
                "random.bow",
                0.75F,
                1.0F / (this.getRNG()
                    .nextFloat() * 0.4F + 0.8F));
            this.worldObj.spawnEntityInWorld(waterBall);
            this.stream_count--;
        } else {
            this.setAttacking(0);
        }

        if (this.stream_count <= 0 && this.rand.nextInt(4) == 1) {
            this.stream_count = 8;
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
            } else if (target instanceof WaterDragon) {
                return false;
            } else if (target instanceof EntityMob) {
                return true;
            } else if (this.isTamed()) {
                return false;
            } else if (target instanceof EntityPlayer) {
                EntityPlayer p = (EntityPlayer) target;
                return !p.capabilities.isCreativeMode;
            } else {
                return MyUtils.isAttackableNonMob(target);
            }
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else if (this.isChild()) {
            return null;
        } else {
            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(14.0, 4.0, 14.0));
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

    public int getAttacking() {
        return this.dataWatcher.getWatchableObjectByte(20);
    }

    public void setAttacking(int attacking) {
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
                        if (s != null && s.equals("Water Dragon")) {
                            return true;
                        }
                    }
                }
            }
        }

        WaterDragon target = null;
        if (this.posY < 50.0) {
            return false;
        } else if (!this.worldObj.isDaytime()) {
            return false;
        } else {
            target = (WaterDragon) this.worldObj
                .findNearestEntityWithinAABB(WaterDragon.class, this.boundingBox.expand(16.0, 5.0, 16.0), this);
            return target == null;
        }
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.spawnBabyAnimal(entityageable);
    }

    public WaterDragon spawnBabyAnimal(EntityAgeable entityAgeable) {
        WaterDragon w = new WaterDragon(this.worldObj);
        if (this.isTamed()) {
            this.func_152115_b(this.func_152113_b());
            w.setTamed(true);
        }

        return w;
    }

    public boolean isWheat(ItemStack stack) {
        return stack != null && stack.getItem() == Items.fish;
    }

    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == OreSpawnMain.MyCrystalApple;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }
}
