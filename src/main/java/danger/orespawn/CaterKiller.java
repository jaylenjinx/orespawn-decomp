package danger.orespawn;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
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
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class CaterKiller extends EntityMob {

    private GenericTargetSorter TargetSorter = null;
    private float moveSpeed = 0.35F;
    int foundmob = 0;
    int ticker = 0;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public CaterKiller(World par1World) {
        super(par1World);
        if (OreSpawnMain.PlayNicely == 0) {
            this.setSize(2.9F, 4.6F);
        } else {
            this.setSize(1.45F, 2.3F);
        }

        this.getNavigator()
            .setAvoidsWater(true);
        this.experienceValue = 200;
        this.fireResistance = 100;
        this.TargetSorter = new GenericTargetSorter(this);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMoveThroughVillage(this, 1.0, false));
        this.tasks.addTask(2, new MyEntityAIWanderALot(this, 16, 1.0));
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
            .setBaseValue(OreSpawnMain.CaterKiller_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
        this.dataWatcher.addObject(21, OreSpawnMain.PlayNicely);
    }

    public int getPlayNicely() {
        return this.dataWatcher.getWatchableObjectInt(21);
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        Entity e = null;
        Boolean ret = super.attackEntityFrom(par1DamageSource, par2);
        e = par1DamageSource.getEntity();
        if (e != null && e instanceof EntityLiving) {
            this.setAttackTarget((EntityLiving) e);
        }

        return ret;
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.CaterKiller_stats.health;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.CaterKiller_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    protected String getLivingSound() {
        return this.rand.nextInt(3) == 0 ? "orespawn:caterkiller_living" : null;
    }

    protected String getHurtSound() {
        return "orespawn:caterkiller_hit";
    }

    protected String getDeathSound() {
        return "orespawn:caterkiller_death";
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

    private ItemStack dropItemRand(Item index, int par1) {
        EntityItem var3 = null;
        ItemStack is = new ItemStack(index, par1, 0);
        var3 = new EntityItem(
            this.worldObj,
            this.posX + OreSpawnMain.OreSpawnRand.nextInt(5) - OreSpawnMain.OreSpawnRand.nextInt(5),
            this.posY + 1.0,
            this.posZ + OreSpawnMain.OreSpawnRand.nextInt(5) - OreSpawnMain.OreSpawnRand.nextInt(5),
            is);
        if (var3 != null) {
            this.worldObj.spawnEntityInWorld(var3);
        }

        return is;
    }

    protected void dropFewItems(boolean par1, int par2) {
        ItemStack is = null;
        this.dropItemRand(OreSpawnMain.CaterKillerJaw, 1);
        this.dropItemRand(Items.item_frame, 1);

        for (int var4 = 0; var4 < 10; var4++) {
            this.dropItemRand(Items.leather, 1);
        }

        for (int var7 = 0; var7 < 6; var7++) {
            this.dropItemRand(Items.beef, 1);
        }

        int i = 1 + this.worldObj.rand.nextInt(5);

        for (int var8 = 0; var8 < i; var8++) {
            int var3 = this.worldObj.rand.nextInt(20);
            switch (var3) {
                case 0:
                    is = this.dropItemRand(OreSpawnMain.MyUltimateSword, 1);
                    break;
                case 1:
                    is = this.dropItemRand(OreSpawnMain.MyRuby, 1);
                    break;
                case 2:
                    is = this.dropItemRand(Item.getItemFromBlock(Blocks.diamond_block), 1);
                    break;
                case 3:
                    is = this.dropItemRand(OreSpawnMain.MyRubySword, 1);
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
                    is = this.dropItemRand(OreSpawnMain.MyRubyShovel, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 5:
                    is = this.dropItemRand(OreSpawnMain.MyRubyPickaxe, 1);
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
                    is = this.dropItemRand(OreSpawnMain.MyRubyAxe, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 7:
                    is = this.dropItemRand(OreSpawnMain.MyRubyHoe, 1);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }

                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
                    }
                    break;
                case 8:
                    is = this.dropItemRand(OreSpawnMain.RubyHelmet, 1);
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
                    is = this.dropItemRand(OreSpawnMain.RubyBody, 1);
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
                    is = this.dropItemRand(OreSpawnMain.RubyLegs, 1);
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
                    is = this.dropItemRand(OreSpawnMain.RubyBoots, 1);
                    if (this.worldObj.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.featherFalling, 5 + this.worldObj.rand.nextInt(5));
                    }

                    if (this.worldObj.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
                    }
                    break;
                case 12:
                    is = this.dropItemRand(OreSpawnMain.MyUltimateBow, 1);
            }
        }

        for (int var9 = 0; var9 < 25; var9++) {
            spawnCreature(this.worldObj, "Butterfly", this.posX, this.posY + 1.0, this.posZ);
        }
    }

    public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = null;
        if (par0World == null) {
            return null;
        } else {
            var8 = EntityList.createEntityByName(par1, par0World);
            if (var8 != null) {
                var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);
                par0World.spawnEntityInWorld(var8);
                ((EntityLiving) var8).playLivingSound();
            }

            return var8;
        }
    }

    public void initCreature() {}

    public boolean interact(EntityPlayer par1EntityPlayer) {
        return false;
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        if (!super.attackEntityAsMob(par1Entity)) {
            return false;
        } else {
            if (par1Entity != null && par1Entity instanceof EntityLivingBase) {
                double ks = 1.2;
                double inair = 0.1;
                float f3 = (float) Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
                if (par1Entity.isDead || par1Entity instanceof EntityPlayer) {
                    inair *= 2.0;
                }

                par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }

            return true;
        }
    }

    private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {
        int found = 0;

        for (int i = -dy; i <= dy; i++) {
            for (int j = -dz; j <= dz; j++) {
                Block bid = this.worldObj.getBlock(x + dx, y + i, z + j);
                if (bid == Blocks.leaves || bid == Blocks.vine
                    || bid == Blocks.log
                    || bid == OreSpawnMain.MyDT
                    || bid == Blocks.log2
                    || bid == Blocks.leaves2
                    || bid == OreSpawnMain.MyAppleLeaves
                    || bid == OreSpawnMain.MyExperienceLeaves
                    || bid == OreSpawnMain.MyScaryLeaves
                    || bid == OreSpawnMain.MyPeachLeaves
                    || bid == OreSpawnMain.MyCherryLeaves) {
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
                if (bid == Blocks.leaves || bid == Blocks.vine
                    || bid == Blocks.log
                    || bid == OreSpawnMain.MyDT
                    || bid == Blocks.log2
                    || bid == Blocks.leaves2
                    || bid == OreSpawnMain.MyAppleLeaves
                    || bid == OreSpawnMain.MyExperienceLeaves
                    || bid == OreSpawnMain.MyScaryLeaves
                    || bid == OreSpawnMain.MyPeachLeaves
                    || bid == OreSpawnMain.MyCherryLeaves) {
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

        for (int var12 = -dx; var12 <= dx; var12++) {
            for (int j = -dz; j <= dz; j++) {
                Block bidx = this.worldObj.getBlock(x + var12, y + dy, z + j);
                if (bidx == Blocks.leaves || bidx == Blocks.vine
                    || bidx == Blocks.log
                    || bidx == OreSpawnMain.MyDT
                    || bidx == Blocks.log2
                    || bidx == Blocks.leaves2
                    || bidx == OreSpawnMain.MyAppleLeaves
                    || bidx == OreSpawnMain.MyExperienceLeaves
                    || bidx == OreSpawnMain.MyScaryLeaves
                    || bidx == OreSpawnMain.MyPeachLeaves
                    || bidx == OreSpawnMain.MyCherryLeaves) {
                    int d = dy * dy + j * j + var12 * var12;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x + var12;
                        this.ty = y + dy;
                        this.tz = z + j;
                        found++;
                    }
                }

                bidx = this.worldObj.getBlock(x + var12, y - dy, z + j);
                if (bidx == Blocks.leaves || bidx == Blocks.vine
                    || bidx == Blocks.log
                    || bidx == OreSpawnMain.MyDT
                    || bidx == Blocks.log2
                    || bidx == Blocks.leaves2
                    || bidx == OreSpawnMain.MyAppleLeaves
                    || bidx == OreSpawnMain.MyExperienceLeaves
                    || bidx == OreSpawnMain.MyScaryLeaves
                    || bidx == OreSpawnMain.MyPeachLeaves
                    || bidx == OreSpawnMain.MyCherryLeaves) {
                    int d = dy * dy + j * j + var12 * var12;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x + var12;
                        this.ty = y - dy;
                        this.tz = z + j;
                        found++;
                    }
                }
            }
        }

        for (int var13 = -dx; var13 <= dx; var13++) {
            for (int j = -dy; j <= dy; j++) {
                Block bidxx = this.worldObj.getBlock(x + var13, y + j, z + dz);
                if (bidxx == Blocks.leaves || bidxx == Blocks.vine
                    || bidxx == Blocks.log
                    || bidxx == OreSpawnMain.MyDT
                    || bidxx == Blocks.log2
                    || bidxx == Blocks.leaves2
                    || bidxx == OreSpawnMain.MyAppleLeaves
                    || bidxx == OreSpawnMain.MyExperienceLeaves
                    || bidxx == OreSpawnMain.MyScaryLeaves
                    || bidxx == OreSpawnMain.MyPeachLeaves
                    || bidxx == OreSpawnMain.MyCherryLeaves) {
                    int d = dz * dz + j * j + var13 * var13;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x + var13;
                        this.ty = y + j;
                        this.tz = z + dz;
                        found++;
                    }
                }

                bidxx = this.worldObj.getBlock(x + var13, y + j, z - dz);
                if (bidxx == Blocks.leaves || bidxx == Blocks.vine
                    || bidxx == Blocks.log
                    || bidxx == OreSpawnMain.MyDT
                    || bidxx == Blocks.log2
                    || bidxx == Blocks.leaves2
                    || bidxx == OreSpawnMain.MyAppleLeaves
                    || bidxx == OreSpawnMain.MyExperienceLeaves
                    || bidxx == OreSpawnMain.MyScaryLeaves
                    || bidxx == OreSpawnMain.MyPeachLeaves
                    || bidxx == OreSpawnMain.MyCherryLeaves) {
                    int d = dz * dz + j * j + var13 * var13;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x + var13;
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
            this.dataWatcher.updateObject(21, OreSpawnMain.PlayNicely);
            if (this.getHealth() + 1.0F < this.getMaxHealth()) {
                this.ticker++;
                if (this.ticker > 2400) {
                    spawnCreature(this.worldObj, "Brutalfly", this.posX, this.posY + 4.0, this.posZ);
                    this.playSound("random.explode", 1.0F, this.worldObj.rand.nextFloat() * 0.2F + 0.9F);

                    for (int i = 0; i < 10; i++) {
                        spawnCreature(
                            this.worldObj,
                            "Butterfly",
                            this.posX,
                            this.posY + 1.0 + this.worldObj.rand.nextInt(4),
                            this.posZ);
                    }

                    this.setDead();
                    return;
                }
            }

            if (this.isInWeb) {
                for (int i = -2; i <= 2; i++) {
                    for (int j = -1; j < 5; j++) {
                        for (int k = -2; k <= 2; k++) {
                            if (this.worldObj.getBlock((int) this.posX + i, (int) this.posY + j, (int) this.posZ + k)
                                == Blocks.web) {
                                this.worldObj.setBlock(
                                    (int) this.posX + i,
                                    (int) this.posY + j,
                                    (int) this.posZ + k,
                                    Blocks.air);
                                this.worldObj.setBlockMetadataWithNotify(
                                    (int) this.posX + i,
                                    (int) this.posY + j,
                                    (int) this.posZ + k,
                                    0,
                                    3);
                            }
                        }
                    }
                }

                this.isInWeb = false;
            }

            if (this.worldObj.rand.nextInt(4) == 0) {
                EntityLivingBase e = this.getAttackTarget();
                if (e != null && !e.isEntityAlive()) {
                    this.setAttackTarget(null);
                    e = null;
                }

                if (this.worldObj.rand.nextInt(200) == 0) {
                    this.setAttackTarget(null);
                }

                if (e == null) {
                    e = this.findSomethingToAttack();
                }

                if (e != null) {
                    this.foundmob = 1;
                    this.faceEntity(e, 10.0F, 10.0F);
                    if (this.getDistanceSqToEntity(e) < (5.0F + e.width / 2.0F) * (5.0F + e.width / 2.0F)) {
                        this.setAttacking(1);
                        if (this.worldObj.rand.nextInt(3) == 0 || this.worldObj.rand.nextInt(4) == 1) {
                            this.attackEntityAsMob(e);
                        }
                    } else {
                        this.setAttacking(0);
                        this.getNavigator()
                            .tryMoveToEntityLiving(e, 1.25);
                        if (this.worldObj.rand.nextInt(4) == 0) {
                            double dx = e.posX;
                            double dz = e.posZ;
                            dx += (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 2.0;
                            dz += (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 2.0;

                            for (int i = 2; i > -2; i--) {
                                if (this.worldObj.getBlock((int) dx, (int) e.posY + i + 1, (int) dz) == Blocks.air
                                    && this.worldObj.getBlock((int) dx, (int) e.posY + i, (int) dz) != Blocks.air) {
                                    this.worldObj.setBlock((int) dx, (int) e.posY + i + 1, (int) dz, Blocks.web);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    this.setAttacking(0);
                    this.foundmob = 0;
                }
            }

            if ((this.worldObj.rand.nextInt(8) == 0 && this.getHealth() < this.mygetMaxHealth()
                || this.worldObj.rand.nextInt(30) == 0) && OreSpawnMain.PlayNicely == 0) {
                this.closest = 99999;
                this.tx = this.ty = this.tz = 0;

                for (int ix = 1; ix < 13; ix++) {
                    int j = ix;
                    if (ix > 9) {
                        j = 9;
                    }

                    if (this.scan_it((int) this.posX, (int) this.posY + 1, (int) this.posZ, ix, j, ix)) {
                        break;
                    }

                    if (ix >= 9) {
                        ix++;
                    }
                }

                if (this.closest < 99999) {
                    if (this.foundmob == 0) {
                        this.getNavigator()
                            .tryMoveToXYZ(this.tx, this.ty, this.tz, 1.0);
                    }

                    if (this.closest < 81) {
                        if (this.worldObj.getGameRules()
                            .getGameRuleBooleanValue("mobGriefing")) {
                            this.worldObj.setBlock(this.tx, this.ty, this.tz, Blocks.air, 0, 2);
                        }

                        this.heal(2.0F);
                        if (this.worldObj.rand.nextInt(20) == 1) {
                            this.playSound("random.burp", 1.0F, this.worldObj.rand.nextFloat() * 0.2F + 0.9F);
                        }
                    }
                }
            }
        }
    }

    private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
        if (par1EntityLiving == null) {
            return false;
        } else if (par1EntityLiving == this) {
            return false;
        } else if (!par1EntityLiving.isEntityAlive()) {
            return false;
        } else if (!this.MyCanSee(par1EntityLiving)) {
            return false;
        } else if (par1EntityLiving instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) par1EntityLiving;
            return !p.capabilities.isCreativeMode;
        } else if (par1EntityLiving instanceof CaterKiller) {
            return false;
        } else {
            return par1EntityLiving instanceof EntityMob ? true : MyUtils.isAttackableNonMob(par1EntityLiving);
        }
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            List var5 = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(20.0, 8.0, 20.0));
            Collections.sort(var5, this.TargetSorter);
            Iterator var2 = var5.iterator();
            Entity var3 = null;
            EntityLivingBase var4 = null;

            while (var2.hasNext()) {
                var3 = (Entity) var2.next();
                var4 = (EntityLivingBase) var3;
                if (this.isSuitableTarget(var4, false)) {
                    return var4;
                }
            }

            return null;
        }
    }

    public final int getAttacking() {
        return this.dataWatcher.getWatchableObjectByte(20);
    }

    public final void setAttacking(int par1) {
        this.dataWatcher.updateObject(20, (byte) par1);
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
                        if (s != null && s.equals("CaterKiller")) {
                            return true;
                        }
                    }
                }
            }
        }

        if (this.posY < 50.0) {
            return false;
        } else if (this.worldObj.rand.nextInt(10) != 0) {
            return false;
        } else if (!this.worldObj.isDaytime()) {
            return false;
        } else {
            for (int var10 = -1; var10 < 2; var10++) {
                for (int j = -1; j < 2; j++) {
                    for (int ix = 1; ix < 5; ix++) {
                        Block bid = this.worldObj
                            .getBlock((int) this.posX + j, (int) this.posY + ix, (int) this.posZ + var10);
                        if (bid != Blocks.air && bid != Blocks.leaves
                            && bid != Blocks.leaves2
                            && bid != Blocks.log
                            && bid != Blocks.log2) {
                            return false;
                        }
                    }
                }
            }

            CaterKiller target = null;
            target = (CaterKiller) this.worldObj
                .findNearestEntityWithinAABB(CaterKiller.class, this.boundingBox.expand(48.0, 16.0, 48.0), this);
            return target == null;
        }
    }

    public boolean MyCanSee(EntityLivingBase e) {
        double xzoff = 2.5;
        int nblks = 10;
        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
        float startx = (float) cx;
        float starty = (float) (this.posY + 3.0);
        float startz = (float) cz;
        float dx = (float) ((e.posX - startx) / 10.0);
        float dy = (float) ((e.posY + e.height / 2.0F - starty) / 10.0);
        float dz = (float) ((e.posZ - startz) / 10.0);
        if (Math.abs(dx) > 1.0) {
            dy /= Math.abs(dx);
            dz /= Math.abs(dx);
            nblks = (int) (nblks * Math.abs(dx));
            if (dx > 1.0F) {
                dx = 1.0F;
            }

            if (dx < -1.0F) {
                dx = -1.0F;
            }
        }

        if (Math.abs(dy) > 1.0) {
            dx /= Math.abs(dy);
            dz /= Math.abs(dy);
            nblks = (int) (nblks * Math.abs(dy));
            if (dy > 1.0F) {
                dy = 1.0F;
            }

            if (dy < -1.0F) {
                dy = -1.0F;
            }
        }

        if (Math.abs(dz) > 1.0) {
            dy /= Math.abs(dz);
            dx /= Math.abs(dz);
            nblks = (int) (nblks * Math.abs(dz));
            if (dz > 1.0F) {
                dz = 1.0F;
            }

            if (dz < -1.0F) {
                dz = -1.0F;
            }
        }

        for (int i = 0; i < nblks; i++) {
            startx += dx;
            starty += dy;
            startz += dz;
            Block bid = this.worldObj.getBlock((int) startx, (int) starty, (int) startz);
            if (bid != Blocks.air && bid != Blocks.web && bid != Blocks.tallgrass && bid != Blocks.leaves) {
                return false;
            }
        }

        return true;
    }
}
