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
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;

import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.client.render.RenderInfo;
import danger.orespawn.entity.ai.GenericTargetSorter;

public class Kraken extends EntityMob {

    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private ChunkCoordinates currentFlightTarget = null;
    private EntityLivingBase caught = null;
    private int newtarget = 0;
    private int release = 0;
    private int weather_set = 10;
    private int long_enough = 3600;
    private int call_reinforcements = 0;
    private boolean hit_by_player = false;
    private int straight_down = 1;
    private int hurt_timer = 0;

    public Kraken(World world) {
        super(world);
        if (OreSpawnMain.PlayNicely == 0) {
            this.setSize(4.0F, 15.0F);
        } else {
            this.setSize(1.3333334F, 5.0F);
        }

        this.getNavigator()
            .setAvoidsWater(false);
        this.experienceValue = 500;
        this.fireResistance = 120;
        this.isImmuneToFire = true;
        this.TargetSorter = new GenericTargetSorter(this);
        this.renderdata = new RenderInfo();
        this.tasks.addTask(1, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(0.37F);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(OreSpawnMain.Kraken_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
        this.dataWatcher.addObject(21, OreSpawnMain.PlayNicely);
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

    public int getPlayNicely() {
        return this.dataWatcher.getWatchableObjectInt(21);
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.Kraken_stats.health;
    }

    public int getKrakenHealth() {
        return (int) this.getHealth();
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
        return OreSpawnMain.Kraken_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
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

    public void onUpdate() {
        super.onUpdate();
        if (!this.isDead) {
            if (this.currentFlightTarget == null) {
                this.currentFlightTarget = new ChunkCoordinates(
                    (int) this.posX,
                    (int) (this.posY - 10.0),
                    (int) this.posZ);
            } else if (this.posY < this.currentFlightTarget.posY) {
                this.motionY *= 0.72;
            } else {
                this.motionY *= 0.5;
            }

            if (this.weather_set > 0 && OreSpawnMain.PlayNicely == 0) {
                this.weather_set--;
                if (this.weather_set == 0 && !this.worldObj.isRemote) {
                    WorldInfo worldinfo = this.worldObj.getWorldInfo();
                    if (!this.worldObj.isRaining()) {
                        worldinfo.setRainTime(300);
                        worldinfo.setThunderTime(300);
                        worldinfo.setRaining(true);
                        worldinfo.setThundering(true);
                    } else {
                        worldinfo.setRainTime(300);
                        worldinfo.setThunderTime(300);
                    }

                    this.weather_set = 100;
                }
            }
        }
    }

    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger("LongEnough", this.long_enough);
    }

    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        this.long_enough = tag.getInteger("LongEnough");
    }

    protected String getLivingSound() {
        return this.rand.nextInt(5) == 0 ? "orespawn:kraken_living" : null;
    }

    protected String getHurtSound() {
        return null;
    }

    protected String getDeathSound() {
        return "orespawn:alo_death";
    }

    protected float getSoundVolume() {
        return 2.0F;
    }

    protected float getSoundPitch() {
        return 1.0F;
    }

    protected Item getDropItem() {
        return Items.quartz;
    }

    private ItemStack dropItemRand(Item index, int n) {
        EntityItem entityItem = null;
        ItemStack is = new ItemStack(index, n, 0);
        entityItem = new EntityItem(
            this.worldObj,
            this.posX + OreSpawnMain.OreSpawnRand.nextInt(8) - OreSpawnMain.OreSpawnRand.nextInt(8),
            this.posY + 1.0,
            this.posZ + OreSpawnMain.OreSpawnRand.nextInt(8) - OreSpawnMain.OreSpawnRand.nextInt(8),
            is);
        if (entityItem != null) {
            this.worldObj.spawnEntityInWorld(entityItem);
        }

        return is;
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        ItemStack is = null;
        this.dropItemRand(OreSpawnMain.MyKrakenTooth, 1);
        this.dropItemRand(Items.item_frame, 1);
        int n = 120 + this.worldObj.rand.nextInt(160);

        for (int i = 0; i < n; i++) {
            this.dropItemRand(Items.dye, 1);
        }

        int i = 5 + this.worldObj.rand.nextInt(10);

        for (int i2 = 0; i2 < i; i2++) {
            int roll = this.worldObj.rand.nextInt(53);
            switch (roll) {
                case 0:
                    is = this.dropItemRand(OreSpawnMain.MyUltimateSword, 1);
                    break;
                case 1:
                    is = this.dropItemRand(Items.diamond, 1);
                    break;
                case 2:
                    is = this.dropItemRand(Item.getItemFromBlock(Blocks.diamond_block), 1);
                    break;
                case 3:
                    is = this.dropItemRand(Items.diamond_sword, 1);
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
                    is = this.dropItemRand(Items.diamond_shovel, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 5:
                    is = this.dropItemRand(Items.diamond_pickaxe, 1);
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
                    is = this.dropItemRand(Items.diamond_axe, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 7:
                    is = this.dropItemRand(Items.diamond_hoe, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 8:
                    is = this.dropItemRand(Items.diamond_helmet, 1);
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
                    is = this.dropItemRand(Items.diamond_chestplate, 1);
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
                    is = this.dropItemRand(Items.diamond_leggings, 1);
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
                    is = this.dropItemRand(Items.diamond_boots, 1);
                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.featherFalling, 5 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }
                    break;
                case 12:
                    is = this.dropItemRand(OreSpawnMain.MyUltimateBow, 1);
                    break;
                case 13:
                    is = this.dropItemRand(OreSpawnMain.MyUltimateAxe, 1);
                    break;
                case 14:
                    is = this.dropItemRand(Items.iron_ingot, 1);
                    break;
                case 15:
                    is = this.dropItemRand(OreSpawnMain.MyUltimatePickaxe, 1);
                    break;
                case 16:
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
                case 17:
                    is = this.dropItemRand(Items.iron_shovel, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 18:
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
                case 19:
                    is = this.dropItemRand(Items.iron_axe, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 20:
                    is = this.dropItemRand(Items.iron_hoe, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 21:
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
                        is.addEnchantment(Enchantment.respiration, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.aquaAffinity, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 22:
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
                case 23:
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
                case 24:
                    is = this.dropItemRand(Items.iron_boots, 1);
                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.featherFalling, 5 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }
                    break;
                case 25:
                    is = this.dropItemRand(OreSpawnMain.MyUltimateShovel, 1);
                    break;
                case 26:
                    this.dropItemRand(Item.getItemFromBlock(Blocks.iron_block), 1);
                    break;
                case 27:
                    is = this.dropItemRand(Items.gold_nugget, 1);
                    break;
                case 28:
                    is = this.dropItemRand(Items.gold_ingot, 1);
                    break;
                case 29:
                    is = this.dropItemRand(Items.golden_carrot, 1);
                    break;
                case 30:
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
                case 31:
                    is = this.dropItemRand(Items.golden_shovel, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 32:
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
                case 33:
                    is = this.dropItemRand(Items.golden_axe, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 34:
                    is = this.dropItemRand(Items.golden_hoe, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 35:
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
                        is.addEnchantment(Enchantment.respiration, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.aquaAffinity, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 36:
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
                case 37:
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
                case 38:
                    is = this.dropItemRand(Items.golden_boots, 1);
                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.featherFalling, 5 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }
                    break;
                case 39:
                    this.dropItemRand(Items.golden_apple, 1);
                    break;
                case 40:
                    this.dropItemRand(Item.getItemFromBlock(Blocks.gold_block), 1);
                    break;
                case 41:
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
                case 42:
                    is = this.dropItemRand(OreSpawnMain.MyExperienceSword, 1);
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
                case 43:
                    is = this.dropItemRand(OreSpawnMain.ExperienceHelmet, 1);
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
                        is.addEnchantment(Enchantment.respiration, 1 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.aquaAffinity, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 44:
                    is = this.dropItemRand(OreSpawnMain.ExperienceBody, 1);
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
                case 45:
                    is = this.dropItemRand(OreSpawnMain.ExperienceLegs, 1);
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
                case 46:
                    is = this.dropItemRand(OreSpawnMain.ExperienceBoots, 1);
                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.featherFalling, 5 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }
                    break;
                case 47:
                    is = this.dropItemRand(OreSpawnMain.MyAmethystSword, 1);
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
                case 48:
                    is = this.dropItemRand(OreSpawnMain.MyAmethystShovel, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 49:
                    is = this.dropItemRand(OreSpawnMain.MyAmethystPickaxe, 1);
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
                case 50:
                    is = this.dropItemRand(OreSpawnMain.MyAmethystAxe, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 51:
                    is = this.dropItemRand(OreSpawnMain.MyAmethystHoe, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 52:
                    is = this.dropItemRand(Item.getItemFromBlock(OreSpawnMain.MyBlockAmethystBlock), 1);
            }
        }
    }

    public boolean interact(EntityPlayer player) {
        return false;
    }

    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        } else if (this.long_enough <= 0) {
            return true;
        } else if (this.posY > 150.0 && this.getHealth() < this.mygetMaxHealth() / 2) {
            return true;
        } else if (this.posY > 180.0 && this.long_enough <= 0) {
            this.setDead();
            return true;
        } else {
            return false;
        }
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.worldObj.rayTraceBlocks(
            Vec3.createVectorHelper(this.posX, this.posY + 0.75, this.posZ),
            Vec3.createVectorHelper(pX, pY, pZ),
            false) == null;
    }

    protected void updateAITasks() {
        int xdir = 1;
        int zdir = 1;
        int keep_trying = 50;
        if (!this.isDead) {
            super.updateAITasks();
            if (this.hurt_timer > 0) {
                this.hurt_timer--;
            }

            if (this.long_enough > 0) {
                this.long_enough--;
            }

            this.dataWatcher.updateObject(21, OreSpawnMain.PlayNicely);
            if (this.worldObj.rand.nextInt(400) == 1 && OreSpawnMain.PlayNicely == 0) {
                this.worldObj
                    .addWeatherEffect(new EntityLightningBolt(this.worldObj, this.posX, this.posY - 16.0, this.posZ));
            }

            if (this.currentFlightTarget == null) {
                this.currentFlightTarget = new ChunkCoordinates((int) this.posX, (int) this.posY, (int) this.posZ);
            }

            if (this.newtarget != 0 || this.rand.nextInt(250) == 1
                || this.currentFlightTarget.getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ)
                    < 9.1F) {
                this.newtarget = 0;

                int ground_dist;
                for (ground_dist = 0; ground_dist < 31; ground_dist++) {
                    Block bid = this.worldObj.getBlock((int) this.posX, (int) this.posY - ground_dist, (int) this.posZ);
                    if (bid != Blocks.air) {
                        this.straight_down = 0;
                        break;
                    }
                }

                ground_dist = 20 - ground_dist;

                for (Block bid = Blocks.stone; bid != Blocks.air && keep_trying != 0; keep_trying--) {
                    zdir = this.worldObj.rand.nextInt(6) + 12;
                    xdir = this.worldObj.rand.nextInt(6) + 12;
                    if (this.worldObj.rand.nextInt(2) == 0) {
                        zdir = -zdir;
                    }

                    if (this.worldObj.rand.nextInt(2) == 0) {
                        xdir = -xdir;
                    }

                    if (this.straight_down != 0) {
                        xdir = 0;
                        zdir = 0;
                    }

                    this.currentFlightTarget.set(
                        (int) this.posX + xdir,
                        (int) this.posY + ground_dist + this.rand.nextInt(9) - 6,
                        (int) this.posZ + zdir);
                    bid = this.worldObj.getBlock(
                        this.currentFlightTarget.posX,
                        this.currentFlightTarget.posY,
                        this.currentFlightTarget.posZ);
                    if (bid == Blocks.air && !this.canSeeTarget(
                        this.currentFlightTarget.posX,
                        this.currentFlightTarget.posY,
                        this.currentFlightTarget.posZ)) {
                        bid = Blocks.stone;
                    }
                }

                if (this.long_enough <= 0 || this.posY < 200.0 && this.getHealth() < this.mygetMaxHealth() / 4) {
                    this.currentFlightTarget.set(
                        this.currentFlightTarget.posX,
                        this.currentFlightTarget.posY + 30,
                        this.currentFlightTarget.posZ);
                    if (this.hit_by_player && this.call_reinforcements == 0
                        && this.getHealth() < this.mygetMaxHealth() / 8
                        && this.posY > 130.0) {
                        this.call_reinforcements = 1;

                        for (int i = 0; i < 10; i++) {
                            EntityCreature newent = (EntityCreature) spawnCreature(
                                this.worldObj,
                                "The Kraken",
                                this.posX + this.worldObj.rand.nextInt(10) - this.worldObj.rand.nextInt(10),
                                170.0,
                                this.posZ + this.worldObj.rand.nextInt(10) - this.worldObj.rand.nextInt(10));
                        }
                    }
                }
            } else if (this.caught == null && this.worldObj.rand.nextInt(8) == 1 && OreSpawnMain.PlayNicely == 0) {
                EntityPlayer target = null;
                target = (EntityPlayer) this.worldObj
                    .findNearestEntityWithinAABB(EntityPlayer.class, this.boundingBox.expand(25.0, 40.0, 25.0), this);
                if (target != null) {
                    if (!target.capabilities.isCreativeMode) {
                        if (this.getEntitySenses()
                            .canSee(target)) {
                            this.currentFlightTarget.set((int) target.posX, (int) target.posY + 15, (int) target.posZ);
                            this.attackWithSomething(target);
                        }
                    } else {
                        target = null;
                    }
                }

                if (target == null && this.worldObj.rand.nextInt(2) == 0) {
                    EntityLivingBase e = null;
                    e = this.findSomethingToAttack();
                    if (e != null) {
                        this.currentFlightTarget.set((int) e.posX, (int) e.posY + 15, (int) e.posZ);
                        this.attackWithSomething(e);
                    }
                }
            }

            if (this.caught != null) {
                if (!this.caught.isDead) {
                    this.currentFlightTarget.set((int) this.posX, 200, (int) this.posZ);
                    if (this.posY > 190.0) {
                        this.release = 1;
                    }

                    this.caught.motionX = this.motionX;
                    this.caught.motionZ = this.motionZ;
                    this.caught.motionY = this.motionY;
                    this.caught.posX = this.posX;
                    if (this.posY - this.caught.posY > 16.0) {
                        this.caught.motionY += 0.25;
                    }

                    this.caught.posY = this.posY - 15.0;
                    this.caught.posZ = this.posZ;
                    this.caught.rotationYaw = this.rotationYaw;
                    if (this.worldObj.rand.nextInt(50) == 1) {
                        this.attackEntityAsMob(this.caught);
                    }

                    if (this.release != 0 || this.worldObj.rand.nextInt(250) == 1) {
                        this.caught = null;
                        this.newtarget = 1;
                        this.release = 0;
                        this.setAttacking(0);
                    }
                } else {
                    this.caught = null;
                    this.newtarget = 1;
                    this.release = 0;
                    this.setAttacking(0);
                }
            }

            double dx2 = this.currentFlightTarget.posX + 0.3 - this.posX;
            double dy = this.currentFlightTarget.posY + 0.1 - this.posY;
            double dz2 = this.currentFlightTarget.posZ + 0.3 - this.posZ;
            this.motionX = this.motionX + (Math.signum(dx2) * 0.45 - this.motionX) * 0.15;
            this.motionY = this.motionY + (Math.signum(dy) * 0.70999 - this.motionY) * 0.202;
            this.motionZ = this.motionZ + (Math.signum(dz2) * 0.45 - this.motionZ) * 0.15;
            float f = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
            float f2 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
            this.moveForward = 0.4F;
            if (Math.abs(this.motionX) + Math.abs(this.motionZ) < 0.15) {
                f2 = 0.0F;
            }

            this.rotationYaw += f2 / 5.0F;
            double obstruction_factor = 0.0;
            double dx = 0.0;
            double dz = 0.0;
            int dist = 10;

            for (int k = -20; k < 18; k += 2) {
                for (int i = 1; i < dist; i += 2) {
                    dx = i * Math.cos(Math.toRadians(this.rotationYaw + 90.0F));
                    dz = i * Math.sin(Math.toRadians(this.rotationYaw + 90.0F));
                    Block bid = this.worldObj
                        .getBlock((int) (this.posX + dx), (int) this.posY + k, (int) (this.posZ + dz));
                    if (bid != Blocks.air) {
                        obstruction_factor += 0.1;
                    }
                }
            }

            this.motionY += obstruction_factor * 0.08;
            this.posY += obstruction_factor * 0.08;
            if (this.posY > 256.0 && !this.isNoDespawnRequired()) {
                this.setDead();
            }
        }
    }

    private void attackWithSomething(EntityLivingBase caught2) {
        if (this.caught == null) {
            double dist = (this.posX - caught2.posX) * (this.posX - caught2.posX);
            dist += (this.posZ - caught2.posZ) * (this.posZ - caught2.posZ);
            dist += (this.posY - caught2.posY - 15.0) * (this.posY - caught2.posY - 15.0);
            if (dist < 30.0) {
                this.caught = caught2;
                this.release = 0;
                this.setAttacking(1);
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
            } else if (target instanceof EntityPlayer) {
                EntityPlayer p = (EntityPlayer) target;
                return p.capabilities.isCreativeMode ? false : !p.capabilities.isFlying;
            } else if (!target.onGround && !target.isInWater()) {
                return false;
            } else if (target instanceof EntitySquid) {
                return false;
            } else if (target instanceof AttackSquid) {
                return false;
            } else if (target instanceof Kraken) {
                return false;
            } else if (target instanceof Spyro) {
                return false;
            } else if (target instanceof Dragon) {
                Dragon c = (Dragon) target;
                return c.riddenByEntity == null;
            } else if (target instanceof Cephadrome) {
                Cephadrome c = (Cephadrome) target;
                return c.riddenByEntity == null;
            } else if (target instanceof Leon) {
                Leon c = (Leon) target;
                return c.riddenByEntity == null;
            } else if (target instanceof ThePrinceTeen) {
                ThePrinceTeen c = (ThePrinceTeen) target;
                return c.riddenByEntity == null;
            } else if (target instanceof ThePrinceAdult) {
                ThePrinceAdult c = (ThePrinceAdult) target;
                return c.riddenByEntity == null;
            } else if (target instanceof EntityChicken) {
                return false;
            } else if (target instanceof Chipmunk) {
                return false;
            } else {
                return target instanceof StinkBug ? false : !(target instanceof Mothra);
            }
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(20.0, 40.0, 20.0));
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

    public void onStruckByLightning(EntityLightningBolt lightning) {}

    public boolean attackEntityFrom(DamageSource source, float amount) {
        Entity e = source.getEntity();
        boolean ret = false;
        if (this.currentFlightTarget != null && e != null
            && e instanceof EntityPlayer
            && this.getHealth() > this.mygetMaxHealth() / 4) {
            this.hit_by_player = true;
            this.currentFlightTarget.set((int) e.posX, (int) e.posY + 15, (int) e.posZ);
        }

        if (this.hurt_timer > 0) {
            return false;
        } else {
            this.hurt_timer = 30;
            ret = super.attackEntityFrom(source, amount);
            if (this.worldObj.rand.nextInt(2) == 1) {
                this.release = 1;
            }

            return ret;
        }
    }

    public final int getAttacking() {
        return this.dataWatcher.getWatchableObjectByte(20);
    }

    public final void setAttacking(int attacking) {
        this.dataWatcher.updateObject(20, (byte) attacking);
    }

    protected void fall(float distance) {}

    protected void updateFallState(double distanceFallen, boolean onGround) {}

    public boolean getCanSpawnHere() {
        if (this.posY < 50.0) {
            return false;
        } else {
            for (int k = -1; k < 2; k++) {
                for (int j = -1; j < 1; j++) {
                    for (int i = 1; i < 6; i++) {
                        Block bid = this.worldObj
                            .getBlock((int) this.posX + j, (int) this.posY + i, (int) this.posZ + k);
                        if (bid != Blocks.air && bid != Blocks.tallgrass) {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    }
}
