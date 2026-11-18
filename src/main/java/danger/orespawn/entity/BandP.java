package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIOpenDoor;
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
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.ai.MyEntityAIWanderALot;

public class BandP extends EntityMob {

    private GenericTargetSorter TargetSorter = null;
    private float moveSpeed = 0.32F;
    private int whatset = 0;
    private int whatami = 0;
    public ItemStack[] MymainInventory = new ItemStack[100];
    int got_stuff = 0;

    public BandP(World world) {
        super(world);
        this.setSize(0.75F, 1.75F);
        this.getNavigator()
            .setAvoidsWater(true);
        this.experienceValue = 1000;
        this.fireResistance = 2;
        this.TargetSorter = new GenericTargetSorter(this);
        this.tasks.addTask(0, new EntityAIMoveThroughVillage(this, 0.5, false));
        this.tasks.addTask(1, new MyEntityAIWanderALot(this, 16, 0.5));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveIndoors(this));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(OreSpawnMain.BandP_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
    }

    protected boolean canDespawn() {
        return this.isNoDespawnRequired() ? false : this.got_stuff == 0;
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
        if (!this.worldObj.isRemote && this.whatset == 0) {
            this.whatset = 1;
            this.whatami = this.worldObj.rand.nextInt(2);
            this.setWhat(this.whatami);
        }
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.BandP_stats.health;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.BandP_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    protected String getLivingSound() {
        return "mob.villager.idle";
    }

    protected String getHurtSound() {
        return "mob.villager.hit";
    }

    protected String getDeathSound() {
        return "mob.villager.death";
    }

    protected float getSoundVolume() {
        return 1.5F;
    }

    protected float getSoundPitch() {
        return 1.0F;
    }

    protected Item getDropItem() {
        return Items.emerald;
    }

    private ItemStack dropItemRand(Item index, int n) {
        EntityItem entityItem = null;
        if (index == null) {
            return null;
        } else {
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
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        int n = 10 + this.worldObj.rand.nextInt(5);

        for (int i = 0; i < n; i++) {
            this.dropItemRand(Items.emerald, 1);
        }

        if (this.getWhat() == 0) {
            n = 2 + this.worldObj.rand.nextInt(3);

            for (int i = 0; i < n; i++) {
                this.dropItemRand(OreSpawnMain.UraniumNugget, 1);
                this.dropItemRand(OreSpawnMain.TitaniumNugget, 1);
            }
        }

        for (int i = 0; i < this.MymainInventory.length; i++) {
            if (this.MymainInventory[i] != null && this.MymainInventory[i].stackSize != 0) {
                ItemStack is = this.dropItemRand(this.MymainInventory[i].getItem(), this.MymainInventory[i].stackSize);
                if (this.MymainInventory[i].stackSize == 1) {
                    is.setItemDamage(this.MymainInventory[i].getItemDamage());
                }
            }
        }
    }

    public boolean interact(EntityPlayer player) {
        return false;
    }

    public boolean attackEntityAsMob(Entity target) {
        return super.attackEntityAsMob(target);
    }

    protected void updateAITasks() {
        if (!this.isDead) {
            super.updateAITasks();
            if (this.worldObj.rand.nextInt(12) == 1) {
                EntityLivingBase e = this.findSomethingToAttack();
                if (e != null) {
                    this.faceEntity(e, 10.0F, 10.0F);
                    if (this.getDistanceSqToEntity(e) < 9.0) {
                        this.attackEntityAsMob(e);
                        if (e instanceof EntityPlayer) {
                            EntityPlayer p = (EntityPlayer) e;
                            int k = -1;
                            int kp = -1;

                            for (int i = 0; i < this.MymainInventory.length; i++) {
                                if (this.MymainInventory[i] == null) {
                                    k = i;
                                    break;
                                }
                            }

                            if (k >= 0) {
                                for (int i = p.inventory.armorInventory.length - 1; i >= 0; i--) {
                                    if (p.inventory.armorInventory[i] != null) {
                                        kp = i;
                                        break;
                                    }
                                }

                                if (kp >= 0) {
                                    this.MymainInventory[k] = p.inventory.armorInventory[kp];
                                    p.inventory.armorInventory[kp] = null;
                                    this.got_stuff++;
                                }

                                if (kp < 0) {
                                    for (int i = p.inventory.mainInventory.length - 1; i >= 0; i--) {
                                        if (p.inventory.mainInventory[i] != null) {
                                            kp = i;
                                            break;
                                        }
                                    }

                                    if (kp >= 0) {
                                        this.MymainInventory[k] = p.inventory.mainInventory[kp];
                                        p.inventory.mainInventory[kp] = null;
                                        this.got_stuff++;
                                    }
                                }
                            }
                        }
                    } else {
                        this.getNavigator()
                            .tryMoveToEntityLiving(e, 1.25);
                    }
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
            } else if (target instanceof EntityVillager) {
                return true;
            } else {
                return target instanceof Girlfriend ? true : target instanceof Boyfriend;
            }
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(20.0, 6.0, 20.0));
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

    public int getWhat() {
        return this.dataWatcher.getWatchableObjectByte(20);
    }

    public void setWhat(int n) {
        this.dataWatcher.updateObject(20, (byte) n);
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
                        if (s != null && s.equals("Criminal")) {
                            return true;
                        }
                    }
                }
            }
        }

        if (!this.worldObj.isDaytime()) {
            return false;
        } else if (this.posY < 50.0) {
            return false;
        } else if (this.posY < 100.0) {
            return false;
        } else {
            BandP target = null;
            target = (BandP) this.worldObj
                .findNearestEntityWithinAABB(BandP.class, this.boundingBox.expand(32.0, 12.0, 32.0), this);
            if (target != null) {
                return false;
            } else {
                EntityVillager target2 = null;
                target2 = (EntityVillager) this.worldObj
                    .findNearestEntityWithinAABB(EntityVillager.class, this.boundingBox.expand(36.0, 12.0, 36.0), this);
                return target2 != null;
            }
        }
    }

    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        if (this.got_stuff != 0) {
            tag.setTag("Inventory", this.writeToNBT(new NBTTagList()));
        }

        tag.setInteger("GotStuff", this.got_stuff);
    }

    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        this.got_stuff = tag.getInteger("GotStuff");
        if (this.got_stuff != 0) {
            NBTTagList nbttaglist = tag.getTagList("Inventory", 10);
            this.readFromNBT(nbttaglist);
        }
    }

    public NBTTagList writeToNBT(NBTTagList tag) {
        for (int i = 0; i < this.MymainInventory.length; i++) {
            if (this.MymainInventory[i] != null) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                this.MymainInventory[i].writeToNBT(nbttagcompound);
                tag.appendTag(nbttagcompound);
            }
        }

        return tag;
    }

    public void readFromNBT(NBTTagList tag) {
        this.MymainInventory = new ItemStack[100];

        for (int i = 0; i < tag.tagCount(); i++) {
            NBTTagCompound nbttagcompound = tag.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot") & 255;
            ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);
            if (itemstack != null && j >= 0 && j < this.MymainInventory.length) {
                this.MymainInventory[j] = itemstack;
            }
        }
    }
}
