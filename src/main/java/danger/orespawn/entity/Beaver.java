package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.ai.MyEntityAIWanderALot;

public class Beaver extends EntityAnimal {

    private float moveSpeed = 0.15F;
    private GenericTargetSorter TargetSorter = null;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public Beaver(World world) {
        super(world);
        this.setSize(0.6F, 0.8F);
        this.moveSpeed = 0.2F;
        this.fireResistance = 100;
        this.getNavigator()
            .setAvoidsWater(false);
        this.experienceValue = 5;
        this.TargetSorter = new GenericTargetSorter(this);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMate(this, 1.0));
        this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityMob.class, 8.0F, 1.0, 1.5));
        this.tasks.addTask(4, new EntityAIPanic(this, 1.5));
        this.tasks.addTask(5, new EntityAIAvoidEntity(this, EntityPlayer.class, 8.0F, 1.0, 1.5));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new MyEntityAIWanderALot(this, 10, 1.0));
        this.tasks.addTask(8, new EntityAILookIdle(this));
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

    protected void entityInit() {
        super.entityInit();
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
    }

    public boolean isWood(Block bid) {
        return bid == Blocks.log || bid == OreSpawnMain.MyDT || bid == OreSpawnMain.MySkyTreeLog ? true
            : bid == Blocks.fence || bid == Blocks.fence_gate || bid == Blocks.standing_sign;
    }

    private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {
        int found = 0;

        for (int i = -dy; i <= dy; i++) {
            for (int j = -dz; j <= dz; j++) {
                Block bid = this.worldObj.getBlock(x + dx, y + i, z + j);
                if (this.isWood(bid)) {
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
                if (this.isWood(bid)) {
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
                if (this.isWood(bidx)) {
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
                if (this.isWood(bidx)) {
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
                if (this.isWood(bidxx)) {
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
                if (this.isWood(bidxx)) {
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

    private ItemStack dropItemRand(Item index, int n) {
        EntityItem entityItem = null;
        ItemStack is = new ItemStack(index, n, 0);
        entityItem = new EntityItem(
            this.worldObj,
            this.posX + OreSpawnMain.OreSpawnRand.nextInt(4) - OreSpawnMain.OreSpawnRand.nextInt(4),
            this.posY + 4.0 + this.worldObj.rand.nextInt(4),
            this.posZ + OreSpawnMain.OreSpawnRand.nextInt(4) - OreSpawnMain.OreSpawnRand.nextInt(4),
            is);
        if (entityItem != null) {
            this.worldObj.spawnEntityInWorld(entityItem);
        }

        return is;
    }

    public void breakRecursor(World world, int x, int y, int z, int xf, int yf, int zf, int recursion) {
        int n = 1;
        if (recursion <= 200) {
            for (int i = -n; i <= n; i++) {
                for (int j = -n; j <= n; j++) {
                    for (int k = -n; k <= n; k++) {
                        if ((i != 0 || j != 0 || k != 0) && (x + i != xf || y + j != yf || z + k != zf)
                            && (recursion <= 0 || x + i < xf - n
                                || x + i > xf + n
                                || y + j < yf - n
                                || y + j > yf + n
                                || z + k < zf - n
                                || z + k > zf + n)) {
                            Block block = world.getBlock(x + i, y + j, z + k);
                            if (this.isWood(block)) {
                                world.setBlock(x + i, y + j, z + k, Blocks.air, 0, 2);
                                this.dropItemRand(Item.getItemFromBlock(block), 1);
                                this.breakRecursor(world, x + i, y + j, z + k, x, y, z, recursion + 1);
                            }
                        }
                    }
                }
            }
        }
    }

    protected void updateAITick() {
        if (!this.isDead) {
            if (this.worldObj.rand.nextInt(200) == 1) {
                this.setRevengeTarget(null);
            }

            if ((this.worldObj.rand.nextInt(30) == 0 && this.getBeaverHealth() < this.mygetMaxHealth()
                || this.worldObj.rand.nextInt(350) == 1) && OreSpawnMain.PlayNicely == 0) {
                this.closest = 99999;
                this.tx = this.ty = this.tz = 0;

                for (int i = 1; i < 11; i++) {
                    int j = i;
                    if (i > 2) {
                        j = 2;
                    }

                    if (this.scan_it((int) this.posX, (int) this.posY + 1, (int) this.posZ, i, j, i)) {
                        break;
                    }

                    if (i >= 6) {
                        i++;
                    }
                }

                int recursion = 0;
                if (this.closest < 99999) {
                    this.getNavigator()
                        .tryMoveToXYZ(this.tx, this.ty, this.tz, 1.0);
                    if (this.closest < 12) {
                        if (this.worldObj.getGameRules()
                            .getGameRuleBooleanValue("mobGriefing")) {
                            this.worldObj.setBlock(this.tx, this.ty, this.tz, Blocks.air, 0, 2);
                            this.breakRecursor(
                                this.worldObj,
                                this.tx,
                                this.ty,
                                this.tz,
                                this.tx,
                                this.ty,
                                this.tz,
                                recursion);
                        }

                        this.heal(1.0F);
                        this.playSound("orespawn:chainsaw", 1.0F, this.worldObj.rand.nextFloat() * 0.2F + 0.9F);
                    }
                }
            }

            if (this.worldObj.rand.nextInt(200) == 1) {
                Beaver buddy = this.findBuddy();
                if (buddy != null) {
                    this.getNavigator()
                        .tryMoveToXYZ(buddy.posX, buddy.posY, buddy.posZ, 0.5);
                }
            }

            super.updateAITick();
        }
    }

    private Beaver findBuddy() {
        List entities = this.worldObj.getEntitiesWithinAABB(Beaver.class, this.boundingBox.expand(16.0, 6.0, 16.0));
        Collections.sort(entities, this.TargetSorter);
        Iterator iterator = entities.iterator();
        Entity entity = null;
        Beaver beaver = null;
        if (iterator.hasNext()) {
            entity = (Entity) iterator.next();
            return (Beaver) entity;
        } else {
            return null;
        }
    }

    public boolean isAIEnabled() {
        return true;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public int mygetMaxHealth() {
        return 15;
    }

    public int getBeaverHealth() {
        return (int) this.getHealth();
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return "orespawn:scorpion_hit";
    }

    protected String getDeathSound() {
        return "orespawn:cryo_death";
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    protected Item getDropItem() {
        return Items.porkchop;
    }

    protected float getSoundPitch() {
        return this.isChild() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1.5F
            : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1.0F;
    }

    public boolean getCanSpawnHere() {
        if (this.posY < 50.0) {
            return false;
        } else if (this.posY > 100.0) {
            return false;
        } else {
            Block bid = this.worldObj.getBlock((int) this.posX, (int) this.posY - 1, (int) this.posZ);
            return bid == Blocks.dirt || bid == Blocks.grass || bid == Blocks.tallgrass || bid == Blocks.leaves;
        }
    }

    protected boolean canDespawn() {
        return false;
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.spawnBabyAnimal(entityageable);
    }

    public Beaver spawnBabyAnimal(EntityAgeable entityAgeable) {
        return new Beaver(this.worldObj);
    }

    public boolean isWheat(ItemStack stack) {
        return stack != null && stack.getItem() == Items.apple;
    }

    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == OreSpawnMain.MyCrystalApple;
    }
}
