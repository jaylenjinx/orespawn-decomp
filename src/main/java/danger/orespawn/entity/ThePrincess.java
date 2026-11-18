package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.ai.MyEntityAIFollowOwner;
import danger.orespawn.entity.ai.MyEntityAIWander;
import danger.orespawn.entity.projectile.BetterFireball;
import danger.orespawn.entity.projectile.IceBall;
import danger.orespawn.entity.projectile.ThunderBolt;

public class ThePrincess extends EntityTameable {

    private ChunkCoordinates currentFlightTarget;
    private GenericTargetSorter TargetSorter = null;
    public int activity = 1;
    private int owner_flying = 0;
    private float moveSpeed = 0.3F;
    private int syncit = 0;
    private int head1ext = 0;
    private int head2ext = 0;
    private int head3ext = 0;
    private int head1dir = 1;
    private int head2dir = 1;
    private int head3dir = 1;
    private int ok_to_grow = 0;
    private int kill_count = 0;
    private int fed_count = 0;
    private int day_count = 0;
    private int is_day = 0;
    private int attack_level = 1;
    private int ticker = 0;

    public ThePrincess(World world) {
        super(world);
        this.setSize(0.75F, 1.25F);
        this.moveSpeed = 0.32F;
        this.fireResistance = 1000;
        this.isImmuneToFire = true;
        this.getNavigator()
            .setAvoidsWater(true);
        this.setSitting(false);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new MyEntityAIFollowOwner(this, 1.15F, 12.0F, 2.0F));
        this.tasks.addTask(3, new EntityAITempt(this, 1.25, Items.beef, false));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityLiving.class, 6.0F));
        this.tasks.addTask(5, new MyEntityAIWander(this, 0.75F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.tasks.addTask(7, new EntityAIMoveIndoors(this));
        this.TargetSorter = new GenericTargetSorter(this);
        this.experienceValue = 50;
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
            .setBaseValue(10.0);
    }

    protected void entityInit() {
        super.entityInit();
        this.activity = 1;
        this.dataWatcher.addObject(22, 0);
        this.dataWatcher.addObject(21, this.activity);
        this.dataWatcher.addObject(20, 1);
        this.dataWatcher.addObject(23, this.attack_level);
        this.setSitting(false);
        this.setTamed(false);
        this.noClip = false;
    }

    public int getPower() {
        return this.dataWatcher.getWatchableObjectInt(23);
    }

    public void setPower(int n) {
        this.dataWatcher.updateObject(23, n);
    }

    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger("SpyroActivity", this.dataWatcher.getWatchableObjectInt(21));
        tag.setInteger("SpyroFire", this.dataWatcher.getWatchableObjectInt(20));
        tag.setInteger("SpyroGrow", this.ok_to_grow);
        tag.setInteger("SpyroKill", this.kill_count);
        tag.setInteger("SpyroFed", this.fed_count);
        tag.setInteger("SpyroDay", this.day_count);
    }

    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        this.activity = tag.getInteger("SpyroActivity");
        this.dataWatcher.updateObject(21, this.activity);
        this.dataWatcher.updateObject(20, tag.getInteger("SpyroFire"));
        this.ok_to_grow = tag.getInteger("SpyroGrow");
        this.kill_count = tag.getInteger("SpyroKill");
        this.fed_count = tag.getInteger("SpyroFed");
        this.day_count = tag.getInteger("SpyroDay");
    }

    public int getActivity() {
        int i = this.dataWatcher.getWatchableObjectInt(21);
        this.activity = i;
        return i;
    }

    public void setActivity(int activity2) {
        this.activity = activity2;
        this.dataWatcher.updateObject(21, 0);
        this.dataWatcher.updateObject(21, activity2);
    }

    public int getSpyroFire() {
        return this.dataWatcher.getWatchableObjectInt(20);
    }

    public void setSpyroFire(int n) {
        this.dataWatcher.updateObject(20, n);
    }

    public int getAttacking() {
        return this.dataWatcher.getWatchableObjectInt(22);
    }

    public void setAttacking(int attacking) {
        this.dataWatcher.updateObject(22, attacking);
    }

    public int getHead1Ext() {
        return this.head1ext;
    }

    public int getHead2Ext() {
        return this.head2ext;
    }

    public int getHead3Ext() {
        return this.head3ext;
    }

    public boolean isAIEnabled() {
        return true;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public int mygetMaxHealth() {
        return 400;
    }

    public boolean interact(EntityPlayer player) {
        ItemStack currentItem = player.inventory.getCurrentItem();
        if (currentItem != null && currentItem.stackSize <= 0) {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
            currentItem = null;
        }

        if (currentItem != null && currentItem.getItem() == Item.getItemFromBlock(Blocks.diamond_block)
            && player.getDistanceSqToEntity(this) < 16.0) {
            if (!this.worldObj.isRemote) {
                this.setTamed(true);
                this.func_152115_b(
                    player.getUniqueID()
                        .toString());
                this.playTameEffect(true);
                this.worldObj.setEntityState(this, (byte) 7);
                this.heal(this.mygetMaxHealth() - this.getHealth());
                this.ok_to_grow = 1;
                this.kill_count = 1000;
                this.fed_count = 1000;
                this.day_count = 1000;
            }

            if (!player.capabilities.isCreativeMode) {
                currentItem.stackSize--;
                if (currentItem.stackSize <= 0) {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                }
            }

            return true;
        } else if (this.isTamed() && currentItem != null
            && this.func_152114_e(player)
            && player.getDistanceSqToEntity(this) < 16.0
            && currentItem.getItem() instanceof ItemFood) {
                if (!this.worldObj.isRemote) {
                    ItemFood item = (ItemFood) currentItem.getItem();
                    if (this.mygetMaxHealth() > this.getHealth()) {
                        this.heal(item.func_150905_g(currentItem) * 10);
                    }

                    this.playTameEffect(true);
                    this.worldObj.setEntityState(this, (byte) 7);
                }

                if (!player.capabilities.isCreativeMode) {
                    currentItem.stackSize--;
                    if (currentItem.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                    }
                }

                return true;
            } else if (this.isTamed() && currentItem != null
                && currentItem.getItem() == Item.getItemFromBlock(Blocks.ice)
                && player.getDistanceSqToEntity(this) < 16.0
                && this.func_152114_e(player)) {
                    if (!this.worldObj.isRemote) {
                        this.playTameEffect(true);
                        this.worldObj.setEntityState(this, (byte) 6);
                        this.setSpyroFire(0);
                        String healthMessage = new String();
                        healthMessage = String.format("Princess fireballs extinguished.");
                        player.addChatComponentMessage(new ChatComponentText(healthMessage));
                    }

                    if (!player.capabilities.isCreativeMode) {
                        currentItem.stackSize--;
                        if (currentItem.stackSize <= 0) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                        }
                    }

                    return true;
                } else if (this.isTamed() && currentItem != null
                    && currentItem.getItem() == Items.flint_and_steel
                    && player.getDistanceSqToEntity(this) < 16.0
                    && this.func_152114_e(player)) {
                        if (!this.worldObj.isRemote) {
                            this.playTameEffect(true);
                            this.worldObj.setEntityState(this, (byte) 6);
                            this.setSpyroFire(1);
                            String healthMessage = new String();
                            healthMessage = String.format("Princess fireballs lit!");
                            player.addChatComponentMessage(new ChatComponentText(healthMessage));
                        }

                        if (!player.capabilities.isCreativeMode) {
                            currentItem.stackSize--;
                            if (currentItem.stackSize <= 0) {
                                player.inventory
                                    .setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
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
                        if (this.isTamed() && player.getDistanceSqToEntity(this) < 16.0 && this.func_152114_e(player)) {
                            if (!this.isSitting()) {
                                this.setSitting(true);
                                this.setActivity(1);
                            } else {
                                this.setSitting(false);
                            }

                            return true;
                        } else {
                            return super.interact(player);
                        }
    }

    public void set_ok_to_grow() {
        this.ok_to_grow = 1;
        this.kill_count = 0;
        this.fed_count = 0;
        this.day_count = 0;
    }

    public boolean isWheat(ItemStack stack) {
        return stack != null && stack.getItem() == Items.beef;
    }

    protected boolean canDespawn() {
        return false;
    }

    protected String getLivingSound() {
        if (this.isSitting()) {
            return null;
        } else {
            return this.getAttacking() == 0 ? null : "orespawn:roar";
        }
    }

    protected String getHurtSound() {
        return "orespawn:duck_hurt";
    }

    protected String getDeathSound() {
        return "orespawn:cryo_death";
    }

    protected float getSoundVolume() {
        return 0.6F;
    }

    public int getTotalArmorValue() {
        return 14;
    }

    protected Item getDropItem() {
        return Items.beef;
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        int roll = 0;
        roll = this.worldObj.rand.nextInt(4);
        roll++;

        for (int i = 0; i < roll; i++) {
            this.dropItem(Items.beef, 1);
        }
    }

    protected float getSoundPitch() {
        return (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F + 1.5F;
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    protected void fall(float distance) {}

    protected void updateFallState(double distanceFallen, boolean onGround) {}

    public boolean doesEntityNotTriggerPressurePlate() {
        return false;
    }

    public boolean getCanSpawnHere() {
        return true;
    }

    public EntityAgeable createChild(EntityAgeable partner) {
        return null;
    }

    public float getAttackStrength(Entity entity) {
        return 9.0F;
    }

    public boolean attackEntityAsMob(Entity target) {
        float attackStrength = this.getAttackStrength(target);
        boolean flag = target.attackEntityFrom(DamageSource.causeMobDamage(this), attackStrength);
        if (target instanceof EntityLivingBase) {
            EntityLivingBase el = (EntityLivingBase) target;
            if (el.getHealth() <= 0.0F) {
                this.kill_count++;
            }
        }

        return flag;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = false;
        if (source.getDamageType()
            .equals("inWall")) {
            return false;
        } else {
            if (!source.getDamageType()
                .equals("cactus")) {
                ret = super.attackEntityFrom(source, amount);
                this.setSitting(false);
                this.setActivity(2);
            }

            return ret;
        }
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.worldObj.rayTraceBlocks(
            Vec3.createVectorHelper(this.posX, this.posY + 0.75, this.posZ),
            Vec3.createVectorHelper(pX, pY, pZ),
            false) == null;
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.getActivity() == 2) {
            this.noClip = true;
        } else {
            this.noClip = false;
        }

        if (this.worldObj.rand.nextInt(10) == 1) {
            int i = this.worldObj.rand.nextInt(3);
            if (i == 0) {
                this.head1dir = 2;
            }

            if (i == 1) {
                this.head1dir = -2;
            }

            if (i == 2) {
                this.head1dir = 0;
            }
        }

        if (this.worldObj.rand.nextInt(10) == 1) {
            int ix = this.worldObj.rand.nextInt(3);
            if (ix == 0) {
                this.head2dir = 2;
            }

            if (ix == 1) {
                this.head2dir = -2;
            }

            if (ix == 2) {
                this.head2dir = 0;
            }
        }

        if (this.worldObj.rand.nextInt(10) == 1) {
            int ixx = this.worldObj.rand.nextInt(3);
            if (ixx == 0) {
                this.head3dir = 2;
            }

            if (ixx == 1) {
                this.head3dir = -2;
            }

            if (ixx == 2) {
                this.head3dir = 0;
            }
        }

        this.head1ext = this.head1ext + this.head1dir;
        if (this.head1ext < 0) {
            this.head1ext = 0;
        }

        if (this.head1ext > 60) {
            this.head1ext = 60;
        }

        this.head2ext = this.head2ext + this.head2dir;
        if (this.head2ext < 0) {
            this.head2ext = 0;
        }

        if (this.head2ext > 60) {
            this.head2ext = 60;
        }

        this.head3ext = this.head3ext + this.head3dir;
        if (this.head3ext < 0) {
            this.head3ext = 0;
        }

        if (this.head3ext > 60) {
            this.head3ext = 60;
        }

        if (this.worldObj.isRemote && this.getPower() > 400) {
            float f = 0.25F;
            if (this.worldObj.rand.nextInt(6) == 1) {
                for (int ixxx = 0; ixxx < 2; ixxx++) {
                    this.worldObj.spawnParticle(
                        "fireworksSpark",
                        this.posX - f * Math.sin(Math.toRadians(this.rotationYaw)),
                        this.posY + 0.4,
                        this.posZ + f * Math.cos(Math.toRadians(this.rotationYaw)),
                        (this.worldObj.rand.nextGaussian() - this.worldObj.rand.nextGaussian()) / 7.0
                            + this.motionX * 3.0,
                        (this.worldObj.rand.nextGaussian() - this.worldObj.rand.nextGaussian()) / 7.0,
                        (this.worldObj.rand.nextGaussian() - this.worldObj.rand.nextGaussian()) / 7.0
                            + this.motionZ * 3.0);
                }
            }
        }
    }

    public void onLivingUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onLivingUpdate();
        if (this.isInWater()) {
            this.motionY += 0.07;
        }

        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new ChunkCoordinates((int) this.posX, (int) this.posY, (int) this.posZ);
        }

        this.syncit++;
        if (this.syncit > 20) {
            this.syncit = 0;
            if (this.worldObj.isRemote) {
                this.getActivity();
            } else {
                int j = this.activity;
                this.setActivity(j);
            }
        }

        if (this.activity == 2) {
            this.motionY *= 0.6;
        }
    }

    protected void updateAITasks() {
        double xzoff = 1.5;
        double yoff = 1.0;
        EntityLiving newent = null;
        if (!this.isDead) {
            if (this.worldObj.rand.nextInt(200) == 1) {
                this.setRevengeTarget(null);
            }

            if (this.activity != 2) {
                super.updateAITasks();
            }

            this.ticker++;
            if (this.ticker % 10 == 0) {
                this.setPower(this.attack_level);
            }

            if (this.worldObj.rand.nextInt(200) == 1 && this.getHealth() < this.mygetMaxHealth()) {
                this.heal(1.0F);
            }

            if (!this.isTamed()) {
                EntityPlayer p = this.worldObj.getClosestPlayerToEntity(this, 10.0);
                if (p != null) {
                    this.setTamed(true);
                    this.func_152115_b(
                        p.getUniqueID()
                            .toString());
                    this.playTameEffect(true);
                    this.worldObj.setEntityState(this, (byte) 7);
                    this.heal(this.mygetMaxHealth() - this.getHealth());
                }
            }

            this.attack_level++;
            if (this.getAttacking() != 0) {
                this.attack_level += 4;
            }

            if (this.getSpyroFire() == 0) {
                this.attack_level = 0;
            }

            if (this.attack_level > 500) {
                if (this.getAttacking() != 0) {
                    int j = 3;

                    for (int i = 0; i < j; i++) {
                        Entity ppwr = spawnCreature(
                            this.worldObj,
                            "PurplePower",
                            this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw)),
                            this.posY + yoff,
                            this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)));
                        if (ppwr != null) {
                            PurplePower p = (PurplePower) ppwr;
                            p.motionX = this.motionX * 3.0;
                            p.motionZ = this.motionZ * 3.0;
                            p.setPurpleType(1 + this.worldObj.rand.nextInt(3));
                        }
                    }
                } else {
                    if (this.worldObj.getGameRules()
                        .getGameRuleBooleanValue("mobGriefing")) {
                        for (int m = 0; m < 5; m++) {
                            int ix = this.worldObj.rand.nextInt(5) - this.worldObj.rand.nextInt(5);
                            int k = this.worldObj.rand.nextInt(5) - this.worldObj.rand.nextInt(5);

                            for (int j = -5; j < 5; j++) {
                                Block bid = this.worldObj
                                    .getBlock((int) this.posX + ix, (int) this.posY + j, (int) this.posZ + k);
                                if (bid == Blocks.grass) {
                                    if (this.worldObj
                                        .getBlock((int) this.posX + ix, (int) this.posY + j + 1, (int) this.posZ + k)
                                        == Blocks.air) {
                                        int which = this.worldObj.rand.nextInt(8);
                                        if (which == 0) {
                                            this.worldObj.setBlock(
                                                (int) this.posX + ix,
                                                (int) this.posY + j + 1,
                                                (int) this.posZ + k,
                                                Blocks.red_flower);
                                        }

                                        if (which == 1) {
                                            this.worldObj.setBlock(
                                                (int) this.posX + ix,
                                                (int) this.posY + j + 1,
                                                (int) this.posZ + k,
                                                Blocks.yellow_flower);
                                        }

                                        if (which == 2) {
                                            this.worldObj.setBlock(
                                                (int) this.posX + ix,
                                                (int) this.posY + j + 1,
                                                (int) this.posZ + k,
                                                OreSpawnMain.MyFlowerBlueBlock);
                                        }

                                        if (which == 3) {
                                            this.worldObj.setBlock(
                                                (int) this.posX + ix,
                                                (int) this.posY + j + 1,
                                                (int) this.posZ + k,
                                                OreSpawnMain.MyFlowerPinkBlock);
                                        }

                                        if (which == 4) {
                                            this.worldObj.setBlock(
                                                (int) this.posX + ix,
                                                (int) this.posY + j + 1,
                                                (int) this.posZ + k,
                                                OreSpawnMain.CrystalFlowerRedBlock);
                                        }

                                        if (which == 5) {
                                            this.worldObj.setBlock(
                                                (int) this.posX + ix,
                                                (int) this.posY + j + 1,
                                                (int) this.posZ + k,
                                                OreSpawnMain.CrystalFlowerGreenBlock);
                                        }

                                        if (which == 6) {
                                            this.worldObj.setBlock(
                                                (int) this.posX + ix,
                                                (int) this.posY + j + 1,
                                                (int) this.posZ + k,
                                                OreSpawnMain.CrystalFlowerBlueBlock);
                                        }

                                        if (which == 7) {
                                            this.worldObj.setBlock(
                                                (int) this.posX + ix,
                                                (int) this.posY + j + 1,
                                                (int) this.posZ + k,
                                                OreSpawnMain.CrystalFlowerYellowBlock);
                                        }
                                    }
                                    break;
                                }

                                if (bid == Blocks.dirt && this.worldObj
                                    .getBlock((int) this.posX + ix, (int) this.posY + j + 1, (int) this.posZ + k)
                                    == Blocks.air) {
                                    this.worldObj.setBlock(
                                        (int) this.posX + ix,
                                        (int) this.posY + j,
                                        (int) this.posZ + k,
                                        Blocks.grass);
                                    break;
                                }

                                if (bid == Blocks.stone && this.worldObj
                                    .getBlock((int) this.posX + ix, (int) this.posY + j + 1, (int) this.posZ + k)
                                    == Blocks.air) {
                                    this.worldObj.setBlock(
                                        (int) this.posX + ix,
                                        (int) this.posY + j + 1,
                                        (int) this.posZ + k,
                                        Blocks.dirt);
                                    break;
                                }

                                if (bid == Blocks.sand && this.worldObj
                                    .getBlock((int) this.posX + ix, (int) this.posY + j + 1, (int) this.posZ + k)
                                    == Blocks.air) {
                                    if (this.worldObj.rand.nextInt(2) == 0) {
                                        this.worldObj.setBlock(
                                            (int) this.posX + ix,
                                            (int) this.posY + j + 1,
                                            (int) this.posZ + k,
                                            Blocks.cactus);
                                    } else {
                                        this.worldObj.setBlock(
                                            (int) this.posX + ix,
                                            (int) this.posY + j,
                                            (int) this.posZ + k,
                                            Blocks.dirt);
                                    }
                                    break;
                                }

                                if (bid == Blocks.lava && this.worldObj
                                    .getBlock((int) this.posX + ix, (int) this.posY + j + 1, (int) this.posZ + k)
                                    == Blocks.air) {
                                    this.worldObj.setBlock(
                                        (int) this.posX + ix,
                                        (int) this.posY + j,
                                        (int) this.posZ + k,
                                        Blocks.water);
                                    break;
                                }

                                if (bid == Blocks.flowing_lava && this.worldObj
                                    .getBlock((int) this.posX + ix, (int) this.posY + j + 1, (int) this.posZ + k)
                                    == Blocks.air) {
                                    this.worldObj.setBlock(
                                        (int) this.posX + ix,
                                        (int) this.posY + j,
                                        (int) this.posZ + k,
                                        Blocks.flowing_water);
                                    break;
                                }

                                if (bid == Blocks.air && j > 0) {
                                    break;
                                }
                            }
                        }
                    }

                    for (int m = 0; m < 2; m++) {
                        int ix = this.worldObj.rand.nextInt(4) - this.worldObj.rand.nextInt(4);
                        int k = this.worldObj.rand.nextInt(4) - this.worldObj.rand.nextInt(4);
                        int j = 1 + this.worldObj.rand.nextInt(4);
                        Block bidx = this.worldObj
                            .getBlock((int) this.posX + ix, (int) this.posY + j, (int) this.posZ + k);
                        if (bidx == Blocks.air) {
                            if (this.worldObj.rand.nextInt(2) == 0) {
                                newent = (EntityLiving) spawnCreature(
                                    this.worldObj,
                                    "Butterfly",
                                    this.posX + ix,
                                    this.posY + j,
                                    this.posZ + k);
                            } else {
                                newent = (EntityLiving) spawnCreature(
                                    this.worldObj,
                                    "Bird",
                                    this.posX + ix,
                                    this.posY + j,
                                    this.posZ + k);
                            }
                        }
                    }
                }

                this.attack_level = 1;
            }

            if (!this.isSitting()) {
                if (this.activity == 0) {
                    this.setActivity(1);
                }

                if (this.worldObj.rand.nextInt(100) == 1) {
                    if (this.worldObj.rand.nextInt(20) == 1) {
                        this.setActivity(2);
                    } else {
                        this.setActivity(1);
                    }
                }

                this.owner_flying = 0;
                if (this.isTamed() && this.getOwner() != null) {
                    EntityPlayer e = (EntityPlayer) this.getOwner();
                    if (e.capabilities.isFlying) {
                        this.owner_flying = 1;
                        this.setActivity(2);
                    }
                }

                if (this.activity == 1 && this.isTamed() && this.getOwner() != null) {
                    EntityLivingBase e = this.getOwner();
                    if (this.getDistanceSqToEntity(e) > 256.0) {
                        this.setActivity(2);
                    }
                }

                this.do_movement();
            } else if (this.isTamed() && this.getOwner() != null) {
                EntityLivingBase e = this.getOwner();
                if (this.getDistanceSqToEntity(e) > 256.0) {
                    this.setSitting(false);
                    this.setActivity(2);
                }
            }

            if (this.is_day == 0) {
                this.is_day = 1;
                if (!this.worldObj.isDaytime()) {
                    this.is_day = -1;
                }
            } else {
                if (this.is_day == -1 && this.worldObj.isDaytime()) {
                    this.day_count++;
                }

                this.is_day = 1;
                if (!this.worldObj.isDaytime()) {
                    this.is_day = -1;
                }
            }
        }
    }

    private void do_movement() {
        int xdir = 1;
        int zdir = 1;
        int keep_trying = 10;
        int do_new = 0;
        double ox = 0.0;
        double oy = 0.0;
        double oz = 0.0;
        int has_owner = 0;
        double rr = 0.0;
        double rhdir = 0.0;
        double rdd = 0.0;
        double pi = 3.1415926545;
        EntityLivingBase e = null;
        if (this.currentFlightTarget == null) {
            do_new = 1;
            this.currentFlightTarget = new ChunkCoordinates((int) this.posX, (int) this.posY, (int) this.posZ);
        }

        if (this.activity == 2 && this.worldObj.rand.nextInt(300) == 0) {
            do_new = 1;
        }

        if (this.isTamed() && this.getOwner() != null) {
            e = this.getOwner();
            has_owner = 1;
            ox = e.posX;
            oy = e.posY + 1.0;
            oz = e.posZ;
            if (this.getDistanceSqToEntity(e) > 100.0) {
                do_new = 1;
            }

            if (this.owner_flying != 0 && this.getDistanceSqToEntity(e) > 36.0) {
                do_new = 1;
            }
        }

        if (this.worldObj.rand.nextInt(7) == 1 && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) {
            e = this.findSomethingToAttack();
            if (e != null) {
                if (this.isTamed() && this.getHealth() / this.mygetMaxHealth() < 0.25F) {
                    this.setActivity(2);
                    this.setAttacking(0);
                    do_new = 0;
                    this.currentFlightTarget.set(
                        (int) (this.posX + (this.posX - e.posX)),
                        (int) (this.posY + 1.0),
                        (int) (this.posZ + (this.posZ - e.posZ)));
                } else {
                    this.setActivity(2);
                    this.setAttacking(1);
                    this.currentFlightTarget.set((int) e.posX, (int) (e.posY + 1.0), (int) e.posZ);
                    do_new = 0;
                    if (this.getDistanceSqToEntity(e) < (3.0F + e.width / 2.0F) * (3.0F + e.width / 2.0F)) {
                        this.attackEntityAsMob(e);
                    } else if (this.getDistanceSqToEntity(e) > 25.0 && this.getDistanceSqToEntity(e) < 144.0
                        && !this.isInWater()
                        && this.getSpyroFire() != 0
                        && (this.worldObj.rand.nextInt(3) == 0 || this.worldObj.rand.nextInt(4) == 1)) {
                            int which = this.worldObj.rand.nextInt(3);
                            if (which == 0) {
                                rr = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                                rhdir = Math.toRadians((this.rotationYaw + 90.0F) % 360.0F);
                                rdd = Math.abs(rr - rhdir) % (pi * 2.0);
                                if (rdd > pi) {
                                    rdd -= pi * 2.0;
                                }

                                rdd = Math.abs(rdd);
                                if (rdd < 0.5) {
                                    this.firecanon(e);
                                }
                            } else if (which == 1) {
                                rr = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                                rhdir = Math.toRadians((this.rotationYaw + 90.0F) % 360.0F);
                                rdd = Math.abs(rr - rhdir) % (pi * 2.0);
                                if (rdd > pi) {
                                    rdd -= pi * 2.0;
                                }

                                rdd = Math.abs(rdd);
                                if (rdd < 0.5) {
                                    this.firecanonl(e);
                                }
                            } else {
                                rr = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                                rhdir = Math.toRadians((this.rotationYaw + 90.0F) % 360.0F);
                                rdd = Math.abs(rr - rhdir) % (pi * 2.0);
                                if (rdd > pi) {
                                    rdd -= pi * 2.0;
                                }

                                rdd = Math.abs(rdd);
                                if (rdd < 0.5) {
                                    this.firecanoni(e);
                                }
                            }
                        }
                }
            } else {
                this.setAttacking(0);
            }
        }

        if (this.activity != 1) {
            if (this.currentFlightTarget.getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ) < 2.1F) {
                do_new = 1;
            }

            if (do_new != 0) {
                for (Block bid = Blocks.stone; bid != Blocks.air && keep_trying != 0; keep_trying--) {
                    int gox = (int) this.posX;
                    int goy = (int) this.posY;
                    int goz = (int) this.posZ;
                    if (has_owner == 1) {
                        gox = (int) ox;
                        goy = (int) oy;
                        goz = (int) oz;
                        if (this.owner_flying == 0) {
                            zdir = this.worldObj.rand.nextInt(4) + 6;
                            xdir = this.worldObj.rand.nextInt(4) + 6;
                        } else {
                            zdir = this.worldObj.rand.nextInt(8);
                            xdir = this.worldObj.rand.nextInt(8);
                        }
                    } else {
                        zdir = this.worldObj.rand.nextInt(5) + 6;
                        xdir = this.worldObj.rand.nextInt(5) + 6;
                    }

                    if (this.worldObj.rand.nextInt(2) == 0) {
                        zdir = -zdir;
                    }

                    if (this.worldObj.rand.nextInt(2) == 0) {
                        xdir = -xdir;
                    }

                    this.currentFlightTarget
                        .set(gox + xdir, goy + (this.worldObj.rand.nextInt(6 + this.owner_flying * 2) - 2), goz + zdir);
                    bid = this.worldObj.getBlock(
                        this.currentFlightTarget.posX,
                        this.currentFlightTarget.posY,
                        this.currentFlightTarget.posZ);
                }
            }

            double speed_factor = 1.0;
            double dx = this.currentFlightTarget.posX + 0.5 - this.posX;
            double dy = this.currentFlightTarget.posY + 0.1 - this.posY;
            double dz = this.currentFlightTarget.posZ + 0.5 - this.posZ;
            if (this.owner_flying != 0) {
                speed_factor = 1.75;
                if (this.isTamed() && this.getOwner() != null) {
                    e = this.getOwner();
                    if (this.getDistanceSqToEntity(e) > 49.0) {
                        speed_factor = 3.5;
                    }
                }
            }

            this.motionX = this.motionX + (Math.signum(dx) * 0.5 - this.motionX) * 0.15 * speed_factor;
            this.motionY = this.motionY + (Math.signum(dy) * 0.7 - this.motionY) * 0.21 * speed_factor;
            this.motionZ = this.motionZ + (Math.signum(dz) * 0.5 - this.motionZ) * 0.15 * speed_factor;
            float f = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
            float f2 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
            this.moveForward = (float) (0.75 * speed_factor);
            this.rotationYaw += f2 / 3.0F;
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
            } else if (MyUtils.isRoyalty(target)) {
                return false;
            } else if (target instanceof EntityMob) {
                return true;
            } else if (target instanceof Mothra) {
                return true;
            } else {
                return target instanceof Dragonfly ? true : target instanceof EntityMosquito;
            }
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(12.0, 6.0, 12.0));
            Collections.sort(entities, this.TargetSorter);
            Iterator iterator = entities.iterator();
            Entity entity = null;
            EntityLivingBase target = null;

            while (iterator.hasNext()) {
                entity = (Entity) iterator.next();
                target = (EntityLivingBase) entity;
                if (this.isSuitableTarget(target, false) && this.canSeeTarget(target.posX, target.posY, target.posZ)) {
                    return target;
                }
            }

            return null;
        }
    }

    private void firecanon(EntityLivingBase e) {
        double yoff = 1.0;
        double xzoff = 3.0;
        BetterFireball bf = null;
        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
        float r1 = 5.0F * (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat());
        float r2 = 3.0F * (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat());
        float r3 = 5.0F * (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat());
        bf = new BetterFireball(
            this.worldObj,
            this,
            e.posX - cx + r1,
            e.posY + e.height / 2.0F - (this.posY + yoff) + r2,
            e.posZ - cz + r3);
        bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0F);
        bf.setPosition(cx, this.posY + yoff, cz);
        bf.setBig();
        if (this.worldObj.rand.nextInt(2) == 1) {
            bf.setSmall();
        }

        this.worldObj.playSoundAtEntity(
            this,
            "random.bow",
            1.0F,
            1.0F / (this.getRNG()
                .nextFloat() * 0.4F + 0.8F));
        this.worldObj.spawnEntityInWorld(bf);
    }

    private void firecanonl(EntityLivingBase e) {
        double yoff = 1.0;
        double xzoff = 3.0;
        double dx = 0.0;
        double dy = 0.0;
        double dz = 0.0;
        float f = 0.0F;
        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
        this.worldObj.playSoundAtEntity(
            this,
            "random.bow",
            1.0F,
            1.0F / (this.getRNG()
                .nextFloat() * 0.4F + 0.8F));
        float r1 = 5.0F * (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat());
        float r2 = 3.0F * (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat());
        float r3 = 5.0F * (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat());
        ThunderBolt lb = new ThunderBolt(this.worldObj, cx, this.posY + yoff, cz);
        lb.setLocationAndAngles(cx, this.posY + yoff, cz, 0.0F, 0.0F);
        dx = e.posX - lb.posX;
        dy = e.posY + 0.25 - lb.posY;
        dz = e.posZ - lb.posZ;
        f = MathHelper.sqrt_double(dx * dx + dz * dz) * 0.2F;
        lb.setThrowableHeading(dx, dy + f, dz, 1.4F, 4.0F);
        lb.motionX *= 3.0;
        lb.motionY *= 3.0;
        lb.motionZ *= 3.0;
        this.worldObj.spawnEntityInWorld(lb);
    }

    private void firecanoni(EntityLivingBase e) {
        double yoff = 1.0;
        double xzoff = 3.0;
        double dx = 0.0;
        double dy = 0.0;
        double dz = 0.0;
        float f = 0.0F;
        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
        this.worldObj.playSoundAtEntity(
            this,
            "random.bow",
            1.0F,
            1.0F / (this.getRNG()
                .nextFloat() * 0.4F + 0.8F));
        float r1 = 5.0F * (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat());
        float r2 = 3.0F * (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat());
        float r3 = 5.0F * (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat());
        IceBall lb = new IceBall(this.worldObj, cx, this.posY + yoff, cz);
        lb.setIceMaker(1);
        lb.setLocationAndAngles(cx, this.posY + yoff, cz, 0.0F, 0.0F);
        dx = e.posX - lb.posX;
        dy = e.posY + 0.25 - lb.posY;
        dz = e.posZ - lb.posZ;
        f = MathHelper.sqrt_double(dx * dx + dz * dz) * 0.2F;
        lb.setThrowableHeading(dx, dy + f, dz, 1.4F, 4.0F);
        lb.motionX *= 3.0;
        lb.motionY *= 3.0;
        lb.motionZ *= 3.0;
        this.worldObj.spawnEntityInWorld(lb);
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
}
