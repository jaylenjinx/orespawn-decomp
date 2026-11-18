package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.GenericTargetSorter;

public class Vortex extends EntityMob {

    private ChunkCoordinates currentFlightTarget = null;
    private GenericTargetSorter TargetSorter = null;
    private int winded = 0;
    private int busy_fighting = 0;
    private int was_spawnered = 0;

    public Vortex(World world) {
        super(world);
        this.setSize(2.0F, 4.0F);
        this.experienceValue = 200;
        this.isImmuneToFire = true;
        this.fireResistance = 250;
        this.TargetSorter = new GenericTargetSorter(this);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(0.35F);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(OreSpawnMain.Vortex_stats.attack);
    }

    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        } else {
            return this.busy_fighting != 0 ? false : this.was_spawnered == 0;
        }
    }

    protected float getSoundVolume() {
        return 0.75F;
    }

    protected float getSoundPitch() {
        return 1.0F;
    }

    protected String getLivingSound() {
        return "orespawn:vortexlive";
    }

    protected String getHurtSound() {
        return null;
    }

    protected String getDeathSound() {
        return "orespawn:vortexlive";
    }

    public boolean canBePushed() {
        return true;
    }

    protected void collideWithEntity(Entity entity) {}

    public int mygetMaxHealth() {
        return OreSpawnMain.Vortex_stats.health;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onUpdate() {
        EntityLivingBase e = null;
        super.onUpdate();
        this.motionY *= 0.6;
        this.busy_fighting = 0;
        e = this.findSomethingToAttack();
        if (e != null) {
            this.busy_fighting = 1;
            if (this.worldObj.isRemote) {
                for (int i = 0; i < 20; i++) {
                    double d = this.worldObj.rand.nextDouble() * 3.5;
                    d *= d;
                    double dir = this.worldObj.rand.nextDouble() * 2.0 * Math.PI;
                    dir -= Math.PI;
                    double dx = Math.cos(dir) * d / 2.0;
                    double dz = Math.sin(dir) * d / 2.0;
                    dir++;
                    this.worldObj.spawnParticle(
                        "smoke",
                        this.posX + dx,
                        this.posY + 0.75 + d,
                        this.posZ + dz,
                        Math.cos(dir) * this.worldObj.rand.nextFloat() / 4.0,
                        this.worldObj.rand.nextFloat() / 2.0F,
                        Math.sin(dir) * this.worldObj.rand.nextFloat() / 4.0);
                }
            }
        }

        if (this.worldObj.rand.nextInt(200) == 1) {
            this.heal(1.0F);
        }

        if (!this.isNoDespawnRequired()) {
            if (this.busy_fighting == 0) {
                if (this.was_spawnered == 0) {
                    long t = this.worldObj.getWorldTime();
                    t %= 24000L;
                    if (t < 12000L && this.worldObj.rand.nextInt(500) == 1) {
                        this.setDead();
                    }
                }
            }
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
        EntityLivingBase e = null;
        if (!this.isDead) {
            super.updateAITasks();
            if (this.currentFlightTarget == null) {
                this.currentFlightTarget = new ChunkCoordinates((int) this.posX, (int) this.posY, (int) this.posZ);
            }

            if (this.winded > 0) {
                this.winded--;
            }

            if (this.rand.nextInt(300) == 0
                || this.currentFlightTarget.getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ)
                    < 2.1F) {
                for (Block bid = Blocks.stone; bid != Blocks.air && keep_trying != 0; keep_trying--) {
                    zdir = this.rand.nextInt(14) + 10;
                    xdir = this.rand.nextInt(14) + 10;
                    if (this.rand.nextInt(2) == 0) {
                        zdir = -zdir;
                    }

                    if (this.rand.nextInt(2) == 0) {
                        xdir = -xdir;
                    }

                    this.currentFlightTarget.set(
                        (int) this.posX + xdir,
                        (int) this.posY + this.rand.nextInt(6) - 3,
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
            }

            e = this.findSomethingToAttack();
            if (e != null) {
                this.currentFlightTarget.set((int) e.posX, (int) e.posY, (int) e.posZ);
                double d = this.getDistanceSqToEntity(e);
                if (d < 81.0 && this.winded == 0) {
                    double a = Math.atan2(this.posZ - e.posZ, this.posX - e.posX);
                    double pm = 1.0;
                    if (e instanceof EntityPlayer) {
                        pm = 2.0;
                    }

                    e.addVelocity(
                        Math.cos(a) * (10.0 - Math.sqrt(d)) * 0.1F,
                        (10.0 - Math.sqrt(d)) * 0.05F * pm,
                        Math.sin(a) * (10.0 - Math.sqrt(d)) * 0.1F);
                }

                if (this.getDistanceSqToEntity(e) < (4.0F + e.width / 2.0F) * (4.0F + e.width / 2.0F)
                    && this.rand.nextInt(8) == 2) {
                    this.attackEntityAsMob(e);
                }
            }

            double dx = this.currentFlightTarget.posX + 0.5 - this.posX;
            double dy = this.currentFlightTarget.posY + 0.1 - this.posY;
            double dz = this.currentFlightTarget.posZ + 0.5 - this.posZ;
            this.motionX = this.motionX + (Math.signum(dx) * 0.4 - this.motionX) * 0.2;
            this.motionY = this.motionY + (Math.signum(dy) * 0.7F - this.motionY) * 0.20000000149011612;
            this.motionZ = this.motionZ + (Math.signum(dz) * 0.4 - this.motionZ) * 0.2;
            float f = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
            float f2 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
            this.moveForward = 0.75F;
            this.rotationYaw += f2 / 4.0F;
        }
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    protected void fall(float distance) {}

    protected void updateFallState(double distanceFallen, boolean onGround) {}

    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = false;
        Entity e = source.getEntity();
        ret = super.attackEntityFrom(source, amount);
        if (e != null && this.currentFlightTarget != null) {
            this.currentFlightTarget.set((int) e.posX, (int) e.posY, (int) e.posZ);
        }

        this.winded = 20;
        return ret;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.Vortex_stats.defense;
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
                        if (s != null && s.equals("Vortex")) {
                            this.was_spawnered = 1;
                            return true;
                        }
                    }
                }
            }
        }

        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                for (int ix = 1; ix < 4; ix++) {
                    Block bid = this.worldObj.getBlock((int) this.posX + j, (int) this.posY + ix, (int) this.posZ + i);
                    if (bid != Blocks.air) {
                        return false;
                    }
                }
            }
        }

        if (!this.isValidLightLevel()) {
            return false;
        } else if (this.posY < 50.0) {
            return false;
        } else {
            long t = this.worldObj.getWorldTime();
            t %= 24000L;
            if (t < 12000L) {
                return false;
            } else if (this.worldObj.rand.nextInt(2) != 1) {
                return false;
            } else {
                Vortex target = null;
                target = (Vortex) this.worldObj
                    .findNearestEntityWithinAABB(Vortex.class, this.boundingBox.expand(20.0, 16.0, 20.0), this);
                return target == null;
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
            } else {
                if (target instanceof EntityPlayer) {
                    EntityPlayer p = (EntityPlayer) target;
                    if (p.capabilities.isCreativeMode) {
                        return false;
                    }
                }

                if (target instanceof Vortex) {
                    return false;
                } else if (target instanceof Rotator) {
                    return false;
                } else if (target instanceof Mothra) {
                    return false;
                } else if (target instanceof Brutalfly) {
                    return false;
                } else if (target instanceof Peacock) {
                    return false;
                } else if (target instanceof CrystalCow) {
                    return false;
                } else if (target instanceof Irukandji) {
                    return false;
                } else if (target instanceof Skate) {
                    return false;
                } else if (target instanceof Whale) {
                    return false;
                } else {
                    return target instanceof Flounder ? false : !(target instanceof Urchin);
                }
            }
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(16.0, 10.0, 16.0));
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

    private ItemStack dropItemRand(Item index, int n) {
        EntityItem entityItem = null;
        ItemStack is = new ItemStack(index, n, 0);
        entityItem = new EntityItem(
            this.worldObj,
            this.posX + OreSpawnMain.OreSpawnRand.nextInt(6) - OreSpawnMain.OreSpawnRand.nextInt(6),
            this.posY + 1.0 + this.worldObj.rand.nextInt(10),
            this.posZ + OreSpawnMain.OreSpawnRand.nextInt(6) - OreSpawnMain.OreSpawnRand.nextInt(6),
            is);
        if (entityItem != null) {
            this.worldObj.spawnEntityInWorld(entityItem);
        }

        return is;
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        this.dropItemRand(OreSpawnMain.VortexEye, 1);
        this.dropItemRand(Items.item_frame, 1);
        int i = 5 + this.worldObj.rand.nextInt(7);

        for (int i2 = 0; i2 < i; i2++) {
            int roll = this.worldObj.rand.nextInt(10);
            if (roll == 0) {
                this.dropItemRand(Items.stick, 1);
            }

            if (roll == 1) {
                this.dropItemRand(OreSpawnMain.MyTigersEyeIngot, 1);
            }

            if (roll == 2) {
                this.dropItemRand(OreSpawnMain.MyCrystalPinkIngot, 1);
            }

            if (roll == 3) {
                this.dropItemRand(Items.iron_ingot, 1);
            }

            if (roll == 4) {
                this.dropItemRand(OreSpawnMain.UraniumNugget, 1);
            }

            if (roll == 6) {
                this.dropItemRand(OreSpawnMain.TitaniumNugget, 1);
            }

            if (roll == 7) {
                this.dropItemRand(OreSpawnMain.MyIrukandji, 1);
            }

            if (roll == 8) {
                this.dropItemRand(Item.getItemFromBlock(OreSpawnMain.CrystalCoal), 1);
            }
        }
    }

    protected Item getDropItem() {
        return OreSpawnMain.FairyEgg;
    }
}
