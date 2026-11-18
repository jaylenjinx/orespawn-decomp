package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
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

import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.ai.MyEntityAIWanderALot;

public class Molenoid extends EntityMob {

    private GenericTargetSorter TargetSorter = null;
    private float moveSpeed = 0.35F;

    public Molenoid(World world) {
        super(world);
        this.setSize(3.9F, 2.6F);
        this.getNavigator()
            .setAvoidsWater(true);
        this.experienceValue = 40;
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
            .setBaseValue(OreSpawnMain.Molenoid_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.Molenoid_stats.health;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.Molenoid_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    protected String getLivingSound() {
        return this.rand.nextInt(3) == 0 ? "orespawn:molenoid_living" : null;
    }

    protected String getHurtSound() {
        return "orespawn:molenoid_hit";
    }

    protected String getDeathSound() {
        return "orespawn:molenoid_death";
    }

    protected float getSoundVolume() {
        return 1.1F;
    }

    protected float getSoundPitch() {
        return 1.0F;
    }

    protected Item getDropItem() {
        return Items.beef;
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
        this.dropItemRand(OreSpawnMain.MolenoidNose, 1);
        this.dropItemRand(Items.item_frame, 1);

        for (int i = 0; i < 10; i++) {
            this.dropItemRand(Items.gold_nugget, 1);
        }

        for (int i = 0; i < 6; i++) {
            this.dropItemRand(Items.beef, 1);
        }
    }

    public void initCreature() {}

    public boolean interact(EntityPlayer player) {
        return false;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        return source.getDamageType()
            .equals("inWall") ? false : super.attackEntityFrom(source, amount);
    }

    public boolean attackEntityAsMob(Entity target) {
        if (!super.attackEntityAsMob(target)) {
            return false;
        } else {
            if (target != null && target instanceof EntityLivingBase) {
                double ks = 0.8;
                double inair = 0.1;
                float f3 = (float) Math.atan2(target.posZ - this.posZ, target.posX - this.posX);
                if (target.isDead || target instanceof EntityPlayer) {
                    inair *= 2.0;
                }

                target.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }

            return true;
        }
    }

    protected void updateAITasks() {
        EntityLivingBase e = null;
        if (!this.isDead) {
            super.updateAITasks();
            if (this.worldObj.rand.nextInt(4) == 0) {
                e = this.findSomethingToAttack();
                if (e != null) {
                    this.faceEntity(e, 10.0F, 10.0F);
                    if (this.getDistanceSqToEntity(e) < (6.0F + e.width / 2.0F) * (6.0F + e.width / 2.0F)) {
                        this.setAttacking(1);
                        if (!(this.getDistanceSqToEntity(e) < 16.0)
                            || this.worldObj.rand.nextInt(4) != 0 && this.worldObj.rand.nextInt(5) != 1) {
                            if (OreSpawnMain.PlayNicely == 0) {
                                int j = 1 + this.worldObj.rand.nextInt(4);

                                for (int k = 0; k < j; k++) {
                                    double dx = e.posX;
                                    double dz = e.posZ;
                                    dx += (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 2.0;
                                    dz += (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 2.0;

                                    for (int i = 4; i > -3; i--) {
                                        if (this.worldObj.getBlock((int) dx, (int) e.posY + i + 1, (int) dz)
                                            == Blocks.air
                                            && this.worldObj.getBlock((int) dx, (int) e.posY + i, (int) dz)
                                                != Blocks.air) {
                                            this.worldObj.setBlock(
                                                (int) dx,
                                                (int) e.posY + i + 1,
                                                (int) dz,
                                                OreSpawnMain.MyMoleDirtBlock);
                                            break;
                                        }
                                    }
                                }
                            }
                        } else {
                            this.attackEntityAsMob(e);
                        }
                    } else {
                        this.getNavigator()
                            .tryMoveToEntityLiving(e, 1.25);
                    }
                } else {
                    this.setAttacking(0);
                }
            }

            if (!this.worldObj.isRemote) {
                if (this.worldObj.rand.nextInt(2) == 0) {
                    double spd = 0.0;
                    spd = this.motionX * this.motionX + this.motionZ * this.motionZ;
                    spd = Math.sqrt(spd);
                    if (spd > this.moveSpeed) {
                        spd = this.moveSpeed;
                    }

                    int odds = (int) (100.0 * spd / this.moveSpeed);
                    if (odds > 0 && this.worldObj.rand.nextInt(100) < odds && OreSpawnMain.PlayNicely == 0) {
                        double dx = this.posX + 6.0 * Math.sin(Math.toRadians(this.rotationYawHead));
                        double dz = this.posZ - 6.0 * Math.cos(Math.toRadians(this.rotationYawHead));
                        dx += (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 3.0;
                        dz += (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 3.0;

                        for (int ix = 4; ix > -4; ix--) {
                            if (this.worldObj.getBlock((int) dx, (int) this.posY + ix + 1, (int) dz) == Blocks.air
                                && this.worldObj.getBlock((int) dx, (int) this.posY + ix, (int) dz) != Blocks.air) {
                                this.worldObj.setBlock(
                                    (int) dx,
                                    (int) this.posY + ix + 1,
                                    (int) dz,
                                    OreSpawnMain.MyMoleDirtBlock);
                                break;
                            }
                        }
                    }
                }

                double dx = this.posX - 3.0 * Math.sin(Math.toRadians(this.rotationYawHead));
                double dz = this.posZ + 3.0 * Math.cos(Math.toRadians(this.rotationYawHead));
                dx += (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 3.0;
                dz += (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 3.0;
                int dir = 1;
                if (e != null) {
                    if ((int) e.posY > (int) this.posY) {
                        dir = 2;
                    }

                    if ((int) e.posY < (int) this.posY) {
                        dir = 0;
                    }
                }

                if (OreSpawnMain.PlayNicely == 0) {
                    for (int ixx = dir; ixx < dir + 3; ixx++) {
                        Block bid = this.worldObj.getBlock((int) dx, (int) this.posY + ixx, (int) dz);
                        if ((bid == Blocks.dirt || bid == Blocks.grass
                            || bid == Blocks.gravel
                            || bid == Blocks.sand
                            || bid == Blocks.leaves) && this.worldObj.getGameRules()
                                .getGameRuleBooleanValue("mobGriefing")) {
                            this.worldObj.setBlock((int) dx, (int) this.posY + ixx, (int) dz, Blocks.air);
                        }

                        if (bid == OreSpawnMain.MyMoleDirtBlock) {
                            this.worldObj.setBlock((int) dx, (int) this.posY + ixx, (int) dz, Blocks.air);
                        }
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
        } else if (!this.MyCanSee(target)) {
            return false;
        } else if (target instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) target;
            return !p.capabilities.isCreativeMode;
        } else if (target instanceof Molenoid) {
            return false;
        } else {
            return target instanceof EntityMob ? true : MyUtils.isAttackableNonMob(target);
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
                if (this.isSuitableTarget(target, false)) {
                    return target;
                }
            }

            return null;
        }
    }

    public final int getAttacking() {
        return this.dataWatcher.getWatchableObjectByte(20);
    }

    public final void setAttacking(int attacking) {
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
                        if (s != null && s.equals("Molenoid")) {
                            return true;
                        }
                    }
                }
            }
        }

        if (!this.isValidLightLevel()) {
            return false;
        } else if (this.posY < 50.0) {
            return false;
        } else if (this.worldObj.isDaytime()) {
            return false;
        } else {
            for (int i = -1; i < 1; i++) {
                for (int j = -1; j < 1; j++) {
                    for (int ix = 1; ix < 4; ix++) {
                        Block bid = this.worldObj
                            .getBlock((int) this.posX + j, (int) this.posY + ix, (int) this.posZ + i);
                        if (bid != Blocks.air) {
                            return false;
                        }
                    }
                }
            }

            Molenoid target = null;
            target = (Molenoid) this.worldObj
                .findNearestEntityWithinAABB(Molenoid.class, this.boundingBox.expand(16.0, 8.0, 16.0), this);
            return target == null;
        }
    }

    public boolean MyCanSee(EntityLivingBase e) {
        double xzoff = 2.0;
        int nblks = 10;
        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
        float startx = (float) cx;
        float starty = (float) (this.posY + 1.0);
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
            if (bid != Blocks.air && bid != OreSpawnMain.MyMoleDirtBlock
                && bid != Blocks.dirt
                && bid != Blocks.grass
                && bid != Blocks.tallgrass
                && bid != Blocks.sand
                && bid != Blocks.gravel) {
                return false;
            }
        }

        return true;
    }
}
