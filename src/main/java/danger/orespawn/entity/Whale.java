package danger.orespawn.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.MyEntityAIWander;

public class Whale extends EntityAnimal {

    private float moveSpeed = 0.35F;
    private int spray = 0;
    private int spray_timer = 0;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public Whale(World world) {
        super(world);
        this.setSize(1.5F, 2.5F);
        this.moveSpeed = 0.35F;
        this.fireResistance = 100;
        this.experienceValue = 40;
        this.getNavigator()
            .setAvoidsWater(false);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMate(this, 1.0));
        this.tasks.addTask(2, new EntityAITempt(this, 1.2F, Items.fish, false));
        this.tasks.addTask(4, new EntityAIPanic(this, 1.5));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 12.0F));
        this.tasks.addTask(6, new MyEntityAIWander(this, 1.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
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
            .setBaseValue(0.0);
    }

    protected void entityInit() {
        super.entityInit();
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
        if (this.spray == 0) {
            if (this.spray_timer > 0) {
                this.spray_timer--;
            }

            if (this.spray_timer == 0) {
                this.spray_timer = 250 + this.worldObj.rand.nextInt(250);
                this.spray = 25 + this.worldObj.rand.nextInt(25);
            }
        }

        if (this.worldObj.isRemote && this.spray > 0) {
            for (int i = 0; i < 20; i++) {
                double d = this.worldObj.rand.nextDouble() * 0.75;
                d *= d;
                double dir = this.worldObj.rand.nextDouble() * 2.0 * Math.PI;
                dir -= Math.PI;
                double dx = Math.cos(dir) * d / 2.0;
                double dz = Math.sin(dir) * d / 2.0;
                dir++;
                if (i < 10) {
                    this.worldObj.spawnParticle(
                        "bubble",
                        this.posX + dx,
                        this.posY + 1.0 + d,
                        this.posZ + dz,
                        Math.cos(dir) * this.worldObj.rand.nextFloat() / 4.0,
                        this.worldObj.rand.nextFloat() * 2.0F,
                        Math.sin(dir) * this.worldObj.rand.nextFloat() / 4.0);
                } else {
                    this.worldObj.spawnParticle(
                        "splash",
                        this.posX + dx,
                        this.posY + 1.0 + d,
                        this.posZ + dz,
                        Math.cos(dir) * this.worldObj.rand.nextFloat() / 4.0,
                        this.worldObj.rand.nextFloat() * 2.0F,
                        Math.sin(dir) * this.worldObj.rand.nextFloat() / 4.0);
                }
            }

            this.spray--;
        }

        if (this.worldObj.rand.nextInt(200) == 1) {
            this.heal(1.0F);
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

    protected String getLivingSound() {
        return "splash";
    }

    protected String getHurtSound() {
        return "orespawn:little_splat";
    }

    protected String getDeathSound() {
        return "orespawn:big_splat";
    }

    protected float getSoundVolume() {
        return 0.9F;
    }

    protected float getSoundPitch() {
        return 0.5F;
    }

    protected Item getDropItem() {
        return Items.fish;
    }

    private void dropItemRand(Item index, int n) {
        EntityItem entityItem = new EntityItem(
            this.worldObj,
            this.posX + OreSpawnMain.OreSpawnRand.nextInt(4) - OreSpawnMain.OreSpawnRand.nextInt(4),
            this.posY + 1.0,
            this.posZ + OreSpawnMain.OreSpawnRand.nextInt(4) - OreSpawnMain.OreSpawnRand.nextInt(4),
            new ItemStack(index, n, 0));
        this.worldObj.spawnEntityInWorld(entityItem);
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        int roll = 0;
        roll = this.rand.nextInt(25);
        roll += 20;

        for (int i = 0; i < roll; i++) {
            this.dropItemRand(Items.fish, 1);
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

    protected void updateAITick() {
        super.updateAITick();
        if (!this.isDead) {
            if (this.worldObj.rand.nextInt(200) == 1) {
                this.setRevengeTarget(null);
            }

            if (!this.isInWater() && this.worldObj.rand.nextInt(20) == 0) {
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
                } else {
                    if (this.worldObj.rand.nextInt(25) == 1) {
                        this.heal(-4.0F);
                    }

                    if (this.getHealth() <= 0.0F) {
                        this.setDead();
                        return;
                    }
                }
            }

            if (this.isInWater() && this.worldObj.rand.nextInt(50) == 0) {
                this.playSound("splash", 1.0F, this.worldObj.rand.nextFloat() * 0.2F + 0.9F);
                this.heal(1.0F);
            }
        }
    }

    private int findBuddies() {
        List entities = this.worldObj.getEntitiesWithinAABB(Whale.class, this.boundingBox.expand(32.0, 8.0, 32.0));
        return entities.size();
    }

    public boolean getCanSpawnHere() {
        if (this.posY < 50.0) {
            return false;
        } else if (!this.worldObj.isDaytime()) {
            return false;
        } else {
            return this.worldObj.rand.nextInt(50) != 1 ? false : this.findBuddies() <= 0;
        }
    }

    protected boolean canDespawn() {
        if (this.isChild()) {
            this.func_110163_bv();
            return false;
        } else {
            return !this.isNoDespawnRequired();
        }
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.spawnBabyAnimal(entityageable);
    }

    public Whale spawnBabyAnimal(EntityAgeable entityAgeable) {
        return new Whale(this.worldObj);
    }

    public boolean isWheat(ItemStack stack) {
        return stack != null && stack.getItem() == Items.fish;
    }

    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == OreSpawnMain.MyCrystalApple;
    }
}
