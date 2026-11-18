package danger.orespawn.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.MyEntityAIFollowOwner;
import danger.orespawn.entity.ai.MyEntityAIWander;

public class Hydrolisc extends EntityTameable {

    private float moveSpeed = 0.25F;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public Hydrolisc(World world) {
        super(world);
        this.setSize(0.5F, 0.5F);
        this.fireResistance = 100;
        this.getNavigator()
            .setAvoidsWater(false);
        this.setSitting(false);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMate(this, 1.0));
        this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityMob.class, 8.0F, 1.0, 1.4F));
        this.tasks.addTask(3, new MyEntityAIFollowOwner(this, 1.2F, 10.0F, 2.0F));
        this.tasks.addTask(4, new EntityAITempt(this, 1.25, Items.fish, false));
        this.tasks.addTask(5, new EntityAIPanic(this, 1.5));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new MyEntityAIWander(this, 1.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(9, new EntityAIMoveIndoors(this));
        this.experienceValue = 5;
    }

    protected void entityInit() {
        super.entityInit();
        this.setSitting(false);
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
            .setBaseValue(1.0);
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
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

    protected void fall(float distance) {
        float i = MathHelper.ceiling_float_int(distance - 3.0F);
        if (i > 0.0F) {
            if (i > 3.0F) {
                this.playSound("damage.fallbig", 1.0F, 1.0F);
            } else {
                this.playSound("damage.fallsmall", 1.0F, 1.0F);
            }

            if (i > 2.0F) {
                i = 2.0F;
            }

            this.attackEntityFrom(DamageSource.fall, i);
        }
    }

    public int getTotalArmorValue() {
        return 10;
    }

    protected void updateAITick() {
        super.updateAITick();
        if (!this.isDead) {
            if (this.worldObj.rand.nextInt(200) == 1) {
                this.setRevengeTarget(null);
            }

            if (!this.isSitting() && (this.worldObj.rand.nextInt(20) == 0 && this.getHydroHealth() < this.getMaxHealth()
                || this.worldObj.rand.nextInt(100) == 0)) {
                this.closest = 99999;
                this.tx = this.ty = this.tz = 0;

                for (int i = 1; i < 11; i++) {
                    int j = i;
                    if (i > 4) {
                        j = 4;
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
                        .tryMoveToXYZ(this.tx, this.ty - 1, this.tz, 1.0);
                    if (this.isInWater()) {
                        this.heal(1.0F);
                        this.playSound("splash", 1.0F, this.worldObj.rand.nextFloat() * 0.2F + 0.9F);
                    }
                }
            }

            if (this.worldObj.rand.nextInt(10) == 0 && this.isTamed()) {
                EntityLivingBase e = this.getOwner();
                if (e != null && e.getHealth() < e.getMaxHealth() && this.getHydroHealth() > 20) {
                    e.heal(1.0F);
                    this.heal(-1.0F);
                }
            }
        }
    }

    public boolean isAIEnabled() {
        return true;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public int mygetMaxHealth() {
        return 100;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.isInWater()) {
            this.motionY += 0.04;
        }
    }

    public int getHydroHealth() {
        return (int) this.getHealth();
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
            && player.getDistanceSqToEntity(this) < 16.0) {
                if (!this.isTamed()) {
                    if (!this.worldObj.isRemote) {
                        if (this.rand.nextInt(2) == 0) {
                            this.setTamed(true);
                            this.func_152115_b(
                                player.getUniqueID()
                                    .toString());
                            this.playTameEffect(true);
                            this.worldObj.setEntityState(this, (byte) 7);
                            this.heal(this.getMaxHealth() - this.getHealth());
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

                    if (this.getMaxHealth() > this.getHealth()) {
                        this.heal(this.getMaxHealth() - this.getHealth());
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
                && player.getDistanceSqToEntity(this) < 16.0
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
                    if (this.isTamed() && player.getDistanceSqToEntity(this) < 16.0 && this.func_152114_e(player)) {
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

    public boolean isWheat(ItemStack stack) {
        return stack != null && stack.getItem() == Items.fish;
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return "orespawn:cryo_hurt";
    }

    protected String getDeathSound() {
        return "orespawn:cryo_death";
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    protected Item getDropItem() {
        return Items.fish;
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        int roll = 0;
        if (this.isTamed()) {
            roll = this.rand.nextInt(5);
            roll += 2;

            for (int i = 0; i < roll; i++) {
                this.dropItem(Items.fish, 1);
            }
        }
    }

    protected float getSoundPitch() {
        return this.isChild() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1.5F
            : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1.0F;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = false;
        float p2 = amount;
        if (amount > 10.0F) {
            p2 = 10.0F;
        }

        return super.attackEntityFrom(source, p2);
    }

    protected boolean canDespawn() {
        return false;
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.spawnBabyAnimal(entityageable);
    }

    public Hydrolisc spawnBabyAnimal(EntityAgeable entityAgeable) {
        return new Hydrolisc(this.worldObj);
    }

    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == OreSpawnMain.MyCrystalApple;
    }
}
