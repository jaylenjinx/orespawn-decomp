package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.GenericTargetSorter;

public class EntityCannonFodder extends EntityTameable {

    String name_one = null;
    String name_two = null;
    private int is_activated = 0;
    private int hat_color = 0;
    private int syncer = 0;
    private int px = 0;
    private int pz = 0;
    private int py = 0;
    private GenericTargetSorter LocalTargetSorter = null;

    public EntityCannonFodder(World world) {
        super(world);
        this.LocalTargetSorter = new GenericTargetSorter(this);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, 0);
        this.dataWatcher.addObject(21, 0);
    }

    public void onUpdate() {
        super.onUpdate();
        this.syncer++;
        if (this.syncer > 5) {
            if (this.worldObj.isRemote) {
                this.is_activated = this.dataWatcher.getWatchableObjectInt(20);
                this.hat_color = this.dataWatcher.getWatchableObjectInt(21);
            } else {
                this.dataWatcher.updateObject(20, this.is_activated);
                this.dataWatcher.updateObject(21, this.hat_color);
            }

            this.syncer = 0;
        }
    }

    public boolean interact(EntityPlayer player) {
        ItemStack currentItem = player.inventory.getCurrentItem();
        if (currentItem != null && currentItem.stackSize <= 0) {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
            currentItem = null;
        }

        if (super.interact(player)) {
            return true;
        } else {
            if (this.name_one != null && this.isTamed()) {
                if (this.name_one.equals(
                    player.getUniqueID()
                        .toString())) {
                    if (this.name_two == null) {
                        this.name_two = this.name_one;
                        this.name_one = player.getUniqueID()
                            .toString();
                        this.func_152115_b(this.name_one);
                        this.is_activated = 2;
                    }
                } else if (this.name_two != null) {
                    if (!this.name_two.equals(
                        player.getUniqueID()
                            .toString())) {
                        return true;
                    }

                    this.name_two = this.name_one;
                    this.name_one = player.getUniqueID()
                        .toString();
                    this.func_152115_b(this.name_one);
                    this.is_activated = 2;
                } else {
                    this.name_two = this.name_one;
                    this.name_one = player.getUniqueID()
                        .toString();
                    this.func_152115_b(this.name_one);
                    this.is_activated = 2;
                }
            }

            if (currentItem != null && currentItem.getItem() == Items.carrot
                && player.getDistanceSqToEntity(this) < 16.0) {
                this.hat_color = 1;
                if (this.name_one == null) {
                    this.name_one = player.getUniqueID()
                        .toString();
                }

                if (this.is_activated == 0) {
                    this.is_activated = 1;
                }

                this.setTamed(true);
                this.func_152115_b(this.name_one);
                this.playTameEffect(true);
                this.heal(this.getMaxHealth() - this.getHealth());
                this.func_110163_bv();
                if (!player.capabilities.isCreativeMode) {
                    currentItem.stackSize--;
                    if (currentItem.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                    }
                }

                return true;
            } else if (currentItem != null && currentItem.getItem() == Items.potato
                && player.getDistanceSqToEntity(this) < 16.0) {
                    this.hat_color = 3;
                    if (this.name_one == null) {
                        this.name_one = player.getUniqueID()
                            .toString();
                    }

                    if (this.is_activated == 0) {
                        this.is_activated = 1;
                    }

                    this.setTamed(true);
                    this.func_152115_b(this.name_one);
                    this.playTameEffect(true);
                    this.heal(this.getMaxHealth() - this.getHealth());
                    this.func_110163_bv();
                    if (!player.capabilities.isCreativeMode) {
                        currentItem.stackSize--;
                        if (currentItem.stackSize <= 0) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                        }
                    }

                    return true;
                } else if (currentItem != null && currentItem.getItem() == OreSpawnMain.MyQuinoa
                    && player.getDistanceSqToEntity(this) < 16.0) {
                        this.hat_color = 2;
                        if (this.name_one == null) {
                            this.name_one = player.getUniqueID()
                                .toString();
                        }

                        if (this.is_activated == 0) {
                            this.is_activated = 1;
                        }

                        this.setTamed(true);
                        this.func_152115_b(this.name_one);
                        this.playTameEffect(true);
                        this.heal(this.getMaxHealth() - this.getHealth());
                        this.func_110163_bv();
                        if (!player.capabilities.isCreativeMode) {
                            currentItem.stackSize--;
                            if (currentItem.stackSize <= 0) {
                                player.inventory
                                    .setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                            }
                        }

                        return true;
                    } else if (currentItem != null && this.is_activated == 2
                        && currentItem.getItem() == OreSpawnMain.MyCornCob
                        && player.getDistanceSqToEntity(this) < 16.0) {
                            String myname = "Ostrich";
                            if (this instanceof Lizard) {
                                myname = "Lizard";
                            }

                            if (this instanceof Chipmunk) {
                                myname = "Chipmunk";
                            }

                            if (this instanceof VelocityRaptor) {
                                myname = "Velocity Raptor";
                            }

                            if (!this.worldObj.isRemote) {
                                Entity newent = spawnCreature(
                                    this.worldObj,
                                    myname,
                                    this.posX + this.worldObj.rand.nextFloat(),
                                    this.posY + 0.01,
                                    this.posZ + this.worldObj.rand.nextFloat());
                                if (newent != null) {
                                    EntityCannonFodder cf = (EntityCannonFodder) newent;
                                    cf.func_152115_b(this.func_152113_b());
                                    cf.setTamed(true);
                                    cf.setStuff(this.hat_color, this.is_activated, this.name_one, this.name_two);
                                }
                            }

                            this.playTameEffect(true);
                            this.worldObj.playSoundAtEntity(player, "random.explode", 0.75F, 2.0F);
                            if (!player.capabilities.isCreativeMode) {
                                currentItem.stackSize--;
                                if (currentItem.stackSize <= 0) {
                                    player.inventory
                                        .setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                                }
                            }

                            return true;
                        } else if (this.is_activated == 2 && player.getDistanceSqToEntity(this) < 16.0) {
                            if (this.isSitting()) {
                                this.setSitting(false);
                                this.playTameEffect(true);
                            } else {
                                this.setSitting(true);
                                this.playTameEffect(false);
                                this.px = (int) this.posX;
                                this.py = (int) this.posY;
                                this.pz = (int) this.posZ;
                            }

                            return true;
                        } else {
                            return false;
                        }
        }
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

    public void setStuff(int hc, int ia, String s1, String s2) {
        this.hat_color = hc;
        this.is_activated = ia;
        this.name_one = s1;
        this.name_two = s2;
        this.func_110163_bv();
    }

    public int getHatColor() {
        return this.hat_color;
    }

    public int get_is_activated() {
        return this.is_activated;
    }

    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        if (this.name_one == null) {
            tag.setString("NameOne", "");
        } else {
            tag.setString("NameOne", this.name_one);
        }

        if (this.name_two == null) {
            tag.setString("NameTwo", "");
        } else {
            tag.setString("NameTwo", this.name_two);
        }

        tag.setInteger("IsActivated", this.is_activated);
        tag.setInteger("HatColor", this.hat_color);
        tag.setInteger("PatrolX", this.px);
        tag.setInteger("PatrolY", this.py);
        tag.setInteger("PatrolZ", this.pz);
    }

    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        this.name_one = tag.getString("NameOne");
        if (this.name_one != null && this.name_one.equals("")) {
            this.name_one = null;
        }

        this.name_two = tag.getString("NameTwo");
        if (this.name_two != null && this.name_two.equals("")) {
            this.name_two = null;
        }

        this.is_activated = tag.getInteger("IsActivated");
        this.hat_color = tag.getInteger("HatColor");
        this.px = tag.getInteger("PatrolX");
        this.py = tag.getInteger("PatrolY");
        this.pz = tag.getInteger("PatrolZ");
        if (this.name_one != null) {
            this.setTamed(true);
            this.func_152115_b(this.name_one);
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
            } else {
                if (this.isSitting()) {
                    double dx = this.px - target.posX;
                    double dy = this.py - target.posY;
                    double dz = this.pz - target.posZ;
                    if (dx * dx + dy * dy + dz * dz > 144.0) {
                        return false;
                    }
                }

                if (target instanceof EntityMob) {
                    return true;
                } else if (target instanceof EntityCannonFodder) {
                    EntityCannonFodder cf = (EntityCannonFodder) target;
                    int i = cf.getHatColor();
                    return i != 0 && i != this.hat_color;
                } else if (target instanceof EntityPlayer) {
                    EntityPlayer p = (EntityPlayer) target;
                    if (p.capabilities.isCreativeMode) {
                        return false;
                    } else {
                        return this.name_one != null && this.name_one.equals(
                            p.getUniqueID()
                                .toString()) ? false
                                    : this.name_two == null || !this.name_two.equals(
                                        p.getUniqueID()
                                            .toString());
                    }
                } else {
                    return false;
                }
            }
    }

    private EntityLivingBase findSomethingToAttack() {
        List entities = this.worldObj
            .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(10.0, 4.0, 10.0));
        Collections.sort(entities, this.LocalTargetSorter);
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

    public int getTotalArmorValue() {
        return this.is_activated == 2 ? 3 : 0;
    }

    public void attackEntityAsFodder(Entity entity, float f) {
        entity.attackEntityFrom(DamageSource.causeMobDamage(this), f);
    }

    protected void updateAITasks() {
        if (!this.isDead) {
            super.updateAITasks();
            if (this.worldObj.rand.nextInt(200) == 1) {
                this.setRevengeTarget(null);
            }

            if (this.is_activated == 2) {
                int pfreq = 5;
                int sfreq = 7;
                float dm = 4.0F;
                if (this instanceof Chipmunk) {
                    dm = 3.0F;
                    sfreq = 6;
                }

                if (this instanceof Lizard) {
                    dm = 6.0F;
                    sfreq = 8;
                }

                if (this instanceof VelocityRaptor) {
                    sfreq = 6;
                    pfreq = 4;
                }

                if (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL
                    && this.worldObj.rand.nextInt(pfreq) == 1) {
                    EntityLivingBase e = this.findSomethingToAttack();
                    if (e != null) {
                        this.getNavigator()
                            .tryMoveToEntityLiving(e, 1.25);
                        if (this.getDistanceSqToEntity(e) < 9.0
                            && (this.rand.nextInt(sfreq + 1) == 0 || this.rand.nextInt(sfreq) == 1)) {
                            this.attackEntityAsFodder(e, dm);
                        }
                    } else if (this.isSitting()) {
                        this.getNavigator()
                            .tryMoveToXYZ(this.px, this.py, this.pz, 0.65F);
                    }
                }

                if (this.worldObj.rand.nextInt(250) == 1) {
                    this.heal(1.0F);
                }
            }
        }
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return null;
    }
}
