package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.projectile.BetterFireball;
import danger.orespawn.entity.projectile.ThunderBolt;

public class TheQueen extends EntityMob {

    private ChunkCoordinates currentFlightTarget = null;
    private GenericTargetSorter TargetSorter = null;
    private EntityLivingBase rt = null;
    private double attdam = 250.0;
    private int hurt_timer = 0;
    private int homex = 0;
    private int homez = 0;
    private int stream_count = 0;
    private int stream_count_l = 0;
    private int ticker = 0;
    private int player_hit_count = 0;
    private int backoff_timer = 0;
    private int guard_mode = 0;
    private volatile int head_found = 0;
    private int wing_sound = 0;
    private int attack_level = 1;
    private EntityLivingBase ev = null;
    private float evh = 0.0F;
    private int mood = 0;
    private int always_mad = 0;

    public TheQueen(World world) {
        super(world);
        if (OreSpawnMain.PlayNicely == 0) {
            this.setSize(22.0F, 24.0F);
        } else {
            this.setSize(5.5F, 6.0F);
        }

        this.getNavigator()
            .setAvoidsWater(false);
        this.experienceValue = 25000;
        this.isImmuneToFire = true;
        this.fireResistance = 5000;
        this.noClip = true;
        this.TargetSorter = new GenericTargetSorter(this);
        this.renderDistanceWeight = 12.0;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(0.62F);
        this.attdam = OreSpawnMain.TheQueen_stats.attack;
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(this.attdam);
    }

    protected void entityInit() {
        int i = 0;
        super.entityInit();
        this.dataWatcher.addObject(20, i);
        this.dataWatcher.addObject(21, OreSpawnMain.PlayNicely);
        this.dataWatcher.addObject(22, this.mood);
        this.dataWatcher.addObject(23, this.attack_level);
    }

    public int getPlayNicely() {
        return this.dataWatcher.getWatchableObjectInt(21);
    }

    public int getIsHappy() {
        return this.dataWatcher.getWatchableObjectInt(22);
    }

    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderVec3D(Vec3 vec) {
        return true;
    }

    protected boolean canDespawn() {
        return false;
    }

    public int getAttacking() {
        return this.dataWatcher.getWatchableObjectInt(20);
    }

    public void setAttacking(int attacking) {
        this.dataWatcher.updateObject(20, attacking);
    }

    public int getPower() {
        return this.dataWatcher.getWatchableObjectInt(23);
    }

    public void setPower(int n) {
        this.dataWatcher.updateObject(23, n);
    }

    protected float getSoundVolume() {
        return 1.35F;
    }

    protected float getSoundPitch() {
        return 1.0F;
    }

    protected String getLivingSound() {
        return "orespawn:king_living";
    }

    protected String getHurtSound() {
        return "orespawn:king_hit";
    }

    protected String getDeathSound() {
        return "orespawn:trex_death";
    }

    public boolean canBePushed() {
        return false;
    }

    protected void collideWithEntity(Entity entity) {}

    public int mygetMaxHealth() {
        return OreSpawnMain.TheQueen_stats.health;
    }

    protected Item getDropItem() {
        return Item.getItemFromBlock(Blocks.yellow_flower);
    }

    private void dropItemRand(Item index, int n) {
        EntityItem entityItem = new EntityItem(
            this.worldObj,
            this.posX + OreSpawnMain.OreSpawnRand.nextInt(20) - OreSpawnMain.OreSpawnRand.nextInt(20),
            this.posY + 12.0,
            this.posZ + OreSpawnMain.OreSpawnRand.nextInt(20) - OreSpawnMain.OreSpawnRand.nextInt(20),
            new ItemStack(index, n, 0));
        this.worldObj.spawnEntityInWorld(entityItem);
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        this.dropItemRand(OreSpawnMain.MyRoyal, 1);
        this.dropItemRand(OreSpawnMain.ThePrinceEgg, 1);
        spawnCreature(this.worldObj, "The Princess", this.posX, this.posY + 10.0, this.posZ);

        for (int i = 0; i < 56; i++) {
            this.dropItemRand(OreSpawnMain.MyQueenScale, 1);
            this.dropItemRand(Items.beef, 1);
            this.dropItemRand(Items.bone, 1);
            this.dropItemRand(Items.rotten_flesh, 1);
        }
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public boolean isHappy() {
        return this.getIsHappy() == 0;
    }

    public void onUpdate() {
        super.onUpdate();
        this.wing_sound++;
        if (this.wing_sound > 30) {
            if (!this.worldObj.isRemote) {
                this.worldObj.playSoundAtEntity(this, "orespawn:MothraWings", 1.75F, 0.75F);
            }

            this.wing_sound = 0;
        }

        this.noClip = true;
        this.motionY *= 0.6;
        if (this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() * 3 / 4) {
            this.attdam = OreSpawnMain.TheQueen_stats.attack * 20;
        }

        if (this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() / 2) {
            this.attdam = OreSpawnMain.TheQueen_stats.attack * 100;
        }

        if (this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() / 3) {
            this.attdam = OreSpawnMain.TheQueen_stats.attack * 500;
        }

        if (this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() / 4) {
            this.attdam = OreSpawnMain.TheQueen_stats.attack * 1000;
        }

        if (this.worldObj.isRemote && this.getPower() > 800) {
            float f = 7.0F;
            if (this.worldObj.rand.nextInt(4) == 1) {
                for (int i = 0; i < 10; i++) {
                    this.worldObj.spawnParticle(
                        "fireworksSpark",
                        this.posX - f * Math.sin(Math.toRadians(this.rotationYaw)),
                        this.posY + 14.0,
                        this.posZ + f * Math.cos(Math.toRadians(this.rotationYaw)),
                        (this.worldObj.rand.nextGaussian() - this.worldObj.rand.nextGaussian()) / 5.0
                            + this.motionX * 3.0,
                        (this.worldObj.rand.nextGaussian() - this.worldObj.rand.nextGaussian()) / 5.0,
                        (this.worldObj.rand.nextGaussian() - this.worldObj.rand.nextGaussian()) / 5.0
                            + this.motionZ * 3.0);
                }
            }
        }
    }

    public boolean attackEntityAsMob(Entity target) {
        if (target != null && target instanceof EntityLivingBase && !this.worldObj.isRemote) {
            EntityLivingBase e = (EntityLivingBase) target;
            if (!e.isDead) {
                if (this.ev == e) {
                    if (this.evh < e.getHealth()) {
                        e.setHealth(this.evh);
                    }
                } else {
                    this.ev = e;
                }

                if (e.width * e.height > 30.0F) {
                    e.setHealth(e.getHealth() * 3.0F / 4.0F);
                    e.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.attdam);
                }

                this.evh = e.getHealth();
                if (this.evh <= 0.0F) {
                    this.ev.setDead();
                }
            } else {
                this.ev = null;
                this.evh = 0.0F;
            }
        }

        if (target != null && target instanceof EntityDragon) {
            EntityDragon dr = (EntityDragon) target;
            DamageSource source = null;
            source = DamageSource.setExplosionSource(null);
            source.setExplosion();
            if (this.worldObj.rand.nextInt(6) == 1) {
                dr.attackEntityFromPart(dr.dragonPartHead, source, (float) this.attdam);
            } else {
                dr.attackEntityFromPart(dr.dragonPartBody, source, (float) this.attdam);
            }
        }

        boolean flag = target.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.attdam);
        if (flag) {
            double ks = 2.75;
            double inair = 0.2;
            float f3 = (float) Math.atan2(target.posZ - this.posZ, target.posX - this.posX);
            inair += this.worldObj.rand.nextFloat() * 0.25F;
            if (target.isDead || target instanceof EntityPlayer) {
                inair *= 1.5;
            }

            target.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
        }

        return flag;
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.worldObj.rayTraceBlocks(
            Vec3.createVectorHelper(this.posX, this.posY + 8.75, this.posZ),
            Vec3.createVectorHelper(pX, pY, pZ),
            false) == null;
    }

    private boolean tooFarFromHome() {
        float d1 = (float) (this.posX - this.homex);
        float d2 = (float) (this.posZ - this.homez);
        d1 = (float) Math.sqrt(d1 * d1 + d2 * d2);
        return d1 > 120.0F;
    }

    protected void updateAITasks() {
        int xdir = 1;
        int zdir = 1;
        int attrand = 5;
        int updown = 0;
        int which = 0;
        EntityLivingBase e = null;
        EntityLivingBase f = null;
        double rr = 0.0;
        double rhdir = 0.0;
        double rdd = 0.0;
        double pi = 3.1415926545;
        double dx2 = 0.0;
        double dy = 0.0;
        double dz2 = 0.0;
        float f2 = 0.0F;
        float f3 = 0.0F;
        EntityLiving newent = null;
        double xzoff = 8.0;
        double yoff = 14.0;
        List kinglist = null;
        Iterator iterator = null;
        TheKing theKing = null;
        if (!this.isDead) {
            super.updateAITasks();
            if (this.ev != null) {
                if (this.getDistanceSqToEntity(this.ev) < 2000.0 && !this.ev.isDead) {
                    if (this.evh < this.ev.getHealth()) {
                        this.ev.setHealth(this.evh);
                    } else {
                        this.evh = this.ev.getHealth();
                    }

                    if (this.evh <= 0.0F) {
                        this.ev.setDead();
                    }
                } else {
                    this.ev = null;
                    this.evh = 0.0F;
                }
            }

            if (this.attack_level > 1000) {
                if (this.mood == 1) {
                    int j = 15;
                    if (this.player_hit_count < 10) {
                        j = 45;
                    }

                    for (int i = 0; i < j; i++) {
                        Entity ppwr = spawnCreature(
                            this.worldObj,
                            "PurplePower",
                            this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw)),
                            this.posY + yoff,
                            this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)));
                        if (ppwr != null) {
                            ppwr.motionX = this.motionX * 3.0;
                            ppwr.motionZ = this.motionZ * 3.0;
                        }
                    }
                } else {
                    if (this.worldObj.getGameRules()
                        .getGameRuleBooleanValue("mobGriefing")) {
                        for (int m = 0; m < 25; m++) {
                            int ix = this.worldObj.rand.nextInt(25) - this.worldObj.rand.nextInt(25);
                            int k = this.worldObj.rand.nextInt(25) - this.worldObj.rand.nextInt(25);

                            for (int j = -20; j < 20; j++) {
                                Block bid = this.worldObj
                                    .getBlock((int) this.posX + ix, (int) this.posY + j, (int) this.posZ + k);
                                if (bid == Blocks.grass) {
                                    if (this.worldObj
                                        .getBlock((int) this.posX + ix, (int) this.posY + j + 1, (int) this.posZ + k)
                                        == Blocks.air) {
                                        which = this.worldObj.rand.nextInt(8);
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

                    for (int m = 0; m < 10; m++) {
                        int ix = this.worldObj.rand.nextInt(15) - this.worldObj.rand.nextInt(15);
                        int k = this.worldObj.rand.nextInt(15) - this.worldObj.rand.nextInt(15);
                        int j = this.worldObj.rand.nextInt(20);
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

            if (this.attack_level > 1) {
                this.attack_level--;
            }

            if (this.hurt_timer > 0) {
                this.hurt_timer--;
            }

            if (this.homex == 0 && this.homez == 0 || this.guard_mode == 0) {
                this.homex = (int) this.posX;
                this.homez = (int) this.posZ;
            }

            if (this.getHealth() > this.mygetMaxHealth() - 2 && this.worldObj.rand.nextInt(500) == 1) {
                this.mood = 0;
            }

            if (this.always_mad != 0) {
                this.mood = 1;
            }

            if (this.mood == 0) {
                this.attack_level += 10;
            }

            this.ticker++;
            if (this.ticker > 30000) {
                this.ticker = 0;
            }

            if (this.ticker % 60 == 0) {
                this.stream_count = 10;
            }

            if (this.ticker % 70 == 0) {
                this.stream_count_l = 6;
            }

            if (this.ticker % 10 == 0) {
                this.dataWatcher.updateObject(21, OreSpawnMain.PlayNicely);
                this.dataWatcher.updateObject(22, this.mood);
                this.setPower(this.attack_level);
            }

            if (this.backoff_timer > 0) {
                this.backoff_timer--;
            }

            if (this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() / 2) {
                attrand = 3;
            }

            this.noClip = true;
            if (this.currentFlightTarget == null) {
                this.currentFlightTarget = new ChunkCoordinates((int) this.posX, (int) this.posY, (int) this.posZ);
            }

            if (this.tooFarFromHome() || this.worldObj.rand.nextInt(200) == 0
                || this.currentFlightTarget.getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ)
                    < 9.1F) {
                zdir = this.worldObj.rand.nextInt(120);
                xdir = this.worldObj.rand.nextInt(120);
                if (this.worldObj.rand.nextInt(2) == 0) {
                    zdir = -zdir;
                }

                if (this.worldObj.rand.nextInt(2) == 0) {
                    xdir = -xdir;
                }

                int dist = 0;

                for (int ix = -5; ix <= 5; ix += 5) {
                    for (int j = -5; j <= 5; j += 5) {
                        Block bidx = this.worldObj.getBlock(this.homex + j, (int) this.posY, this.homez + ix);
                        if (bidx != Blocks.air) {
                            for (int k = 1; k < 20; k++) {
                                bidx = this.worldObj.getBlock(this.homex + j, (int) this.posY + k, this.homez + ix);
                                dist++;
                                if (bidx == Blocks.air) {
                                    break;
                                }
                            }
                        } else {
                            for (int kx = 1; kx < 20; kx++) {
                                bidx = this.worldObj.getBlock(this.homex + j, (int) this.posY - kx, this.homez + ix);
                                dist--;
                                if (bidx != Blocks.air) {
                                    break;
                                }
                            }
                        }
                    }
                }

                dist = dist / 9 + 2;
                if ((int) (this.posY + dist) > 230) {
                    dist = 230 - (int) this.posY;
                }

                this.currentFlightTarget.set(this.homex + xdir, (int) (this.posY + dist), this.homez + zdir);
                if (this.mood == 0) {
                    kinglist = this.worldObj
                        .getEntitiesWithinAABB(TheKing.class, this.boundingBox.expand(64.0, 32.0, 64.0));
                    if (kinglist != null) {
                        Collections.sort(kinglist, this.TargetSorter);
                        iterator = kinglist.iterator();
                        if (iterator.hasNext()) {
                            theKing = null;
                            theKing = (TheKing) iterator.next();
                            this.guard_mode = 0;
                            zdir = this.worldObj.rand.nextInt(16);
                            xdir = this.worldObj.rand.nextInt(16);
                            if (this.worldObj.rand.nextInt(2) == 0) {
                                zdir = -zdir;
                            }

                            if (this.worldObj.rand.nextInt(2) == 0) {
                                xdir = -xdir;
                            }

                            this.currentFlightTarget.set(
                                (int) theKing.posX + xdir,
                                (int) (theKing.posY + (this.worldObj.rand.nextInt(8) - this.worldObj.rand.nextInt(8))),
                                (int) theKing.posZ + zdir);
                        }
                    }
                }
            } else if (this.worldObj.rand.nextInt(attrand) == 0) {
                e = this.rt;
                if (OreSpawnMain.PlayNicely != 0 || this.isHappy()) {
                    e = null;
                }

                if (e != null && (e instanceof TheQueen || e instanceof QueenHead)) {
                    this.rt = null;
                    e = null;
                }

                if (e != null) {
                    float d1 = (float) (e.posX - this.homex);
                    float d2 = (float) (e.posZ - this.homez);
                    d1 = (float) Math.sqrt(d1 * d1 + d2 * d2);
                    if (e.isDead || this.worldObj.rand.nextInt(450) == 1 || d1 > 128.0F && this.guard_mode == 1) {
                        e = null;
                        this.rt = null;
                    }

                    if (e != null && !this.MyCanSee(e)) {
                        e = null;
                    }
                }

                f = this.findSomethingToAttack();
                if (this.head_found == 0 && this.mood == 1) {
                    newent = (EntityLiving) spawnCreature(
                        this.worldObj,
                        "QueenHead",
                        this.posX,
                        this.posY + 20.0,
                        this.posZ);
                }

                if (e == null) {
                    e = f;
                }

                if (e != null) {
                    float d1x = e.width * e.height;
                    if (this.attack_level < 1000) {
                        this.attack_level += 15;
                        if (this.getHealth() < this.mygetMaxHealth() / 2) {
                            this.attack_level += 15;
                        }

                        if (d1x > 50.0F) {
                            this.attack_level += 15;
                        }

                        if (d1x > 100.0F) {
                            this.attack_level += 15;
                        }

                        if (d1x > 200.0F) {
                            this.attack_level += 25;
                        }
                    }

                    this.setAttacking(1);
                    if (this.backoff_timer == 0) {
                        int dist = (int) (e.posY + e.height / 2.0F + 1.0);
                        if (dist > 230) {
                            dist = 230;
                        }

                        this.currentFlightTarget.set((int) e.posX, dist, (int) e.posZ);
                        if (this.worldObj.rand.nextInt(50) == 1) {
                            this.backoff_timer = 90 + this.worldObj.rand.nextInt(90);
                        }
                    } else if (this.currentFlightTarget
                        .getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ) < 9.1F) {
                            zdir = this.worldObj.rand.nextInt(20) + 30;
                            xdir = this.worldObj.rand.nextInt(20) + 30;
                            if (this.worldObj.rand.nextInt(2) == 0) {
                                zdir = -zdir;
                            }

                            if (this.worldObj.rand.nextInt(2) == 0) {
                                xdir = -xdir;
                            }

                            int distx = 0;

                            for (int ix = -5; ix <= 5; ix += 5) {
                                for (int jx = -5; jx <= 5; jx += 5) {
                                    Block bidx = this.worldObj
                                        .getBlock((int) e.posX + jx, (int) this.posY, (int) e.posZ + ix);
                                    if (bidx != Blocks.air) {
                                        for (int kxx = 1; kxx < 20; kxx++) {
                                            bidx = this.worldObj
                                                .getBlock((int) e.posX + jx, (int) this.posY + kxx, (int) e.posZ + ix);
                                            distx++;
                                            if (bidx == Blocks.air) {
                                                break;
                                            }
                                        }
                                    } else {
                                        for (int kxxx = 1; kxxx < 20; kxxx++) {
                                            bidx = this.worldObj
                                                .getBlock((int) e.posX + jx, (int) this.posY - kxxx, (int) e.posZ + ix);
                                            distx--;
                                            if (bidx != Blocks.air) {
                                                break;
                                            }
                                        }
                                    }
                                }
                            }

                            distx = distx / 9 + 2;
                            if ((int) (this.posY + distx) > 230) {
                                distx = 230 - (int) this.posY;
                            }

                            this.currentFlightTarget
                                .set((int) e.posX + xdir, (int) (this.posY + distx), (int) e.posZ + zdir);
                        }

                    if (this.getDistanceSqToEntity(e) < 900.0) {
                        if (this.worldObj.rand.nextInt(2) == 1) {
                            this.doJumpDamage(
                                this.posX,
                                this.posY,
                                this.posZ,
                                15.0,
                                OreSpawnMain.TheQueen_stats.attack / 4,
                                0);
                        }

                        this.attackEntityAsMob(e);
                    }

                    double dx = this.posX + 20.0 * Math.sin(Math.toRadians(this.rotationYaw));
                    double dz = this.posZ - 20.0 * Math.cos(Math.toRadians(this.rotationYaw));
                    if (this.worldObj.rand.nextInt(3) == 1) {
                        this.doJumpDamage(dx, this.posY + 10.0, dz, 15.0, OreSpawnMain.TheQueen_stats.attack / 2, 1);
                    }

                    if (this.getHorizontalDistanceSqToEntity(e) > 900.0) {
                        which = this.worldObj.rand.nextInt(2);
                        if (which == 0) {
                            if (this.stream_count > 0) {
                                this.setAttacking(1);
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
                            }
                        } else if (this.stream_count_l > 0) {
                            this.setAttacking(1);
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
                        }
                    }
                } else {
                    this.setAttacking(0);
                    this.stream_count = 10;
                    this.stream_count_l = 6;
                }
            }

            dx2 = this.currentFlightTarget.posX + 0.5 - this.posX;
            dy = this.currentFlightTarget.posY + 0.1 - this.posY;
            dz2 = this.currentFlightTarget.posZ + 0.5 - this.posZ;
            this.motionX = this.motionX + (Math.signum(dx2) * 0.65 - this.motionX) * 0.35;
            this.motionY = this.motionY + (Math.signum(dy) * 0.69999 - this.motionY) * 0.3;
            this.motionZ = this.motionZ + (Math.signum(dz2) * 0.65 - this.motionZ) * 0.35;
            f2 = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
            f3 = MathHelper.wrapAngleTo180_float(f2 - this.rotationYaw);
            this.moveForward = 0.75F;
            this.rotationYaw += f3 / 8.0F;
            if (this.worldObj.rand.nextInt(32) == 1 && this.getHealth() < this.mygetMaxHealth()) {
                this.heal(5.0F);
                if (this.player_hit_count < 10) {
                    this.heal(50.0F);
                }
            }

            if (this.player_hit_count < 10 && this.getHealth() < 2000.0F) {
                this.heal(2000.0F - this.getHealth());
            }
        }
    }

    private double getHorizontalDistanceSqToEntity(Entity e) {
        double d1 = e.posZ - this.posZ;
        double d2 = e.posX - this.posX;
        return d1 * d1 + d2 * d2;
    }

    private void firecanon(EntityLivingBase e) {
        double yoff = 14.0;
        double xzoff = 32.0;
        BetterFireball bf = null;
        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
        if (this.stream_count > 0) {
            bf = new BetterFireball(
                this.worldObj,
                this,
                e.posX - cx,
                e.posY + e.height / 2.0F - (this.posY + yoff),
                e.posZ - cz);
            bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0F);
            bf.setPosition(cx, this.posY + yoff, cz);
            bf.setReallyBig();
            this.worldObj.playSoundAtEntity(
                this,
                "random.fuse",
                1.0F,
                1.0F / (this.getRNG()
                    .nextFloat() * 0.4F + 0.8F));
            this.worldObj.spawnEntityInWorld(bf);

            for (int i = 0; i < 6; i++) {
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

            this.stream_count--;
        }
    }

    private void firecanonl(EntityLivingBase e) {
        double yoff = 14.0;
        double xzoff = 32.0;
        double dx = 0.0;
        double dy = 0.0;
        double dz = 0.0;
        float f = 0.0F;
        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
        if (this.stream_count_l > 0) {
            this.worldObj.playSoundAtEntity(
                this,
                "random.bow",
                1.0F,
                1.0F / (this.getRNG()
                    .nextFloat() * 0.4F + 0.8F));

            for (int i = 0; i < 3; i++) {
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

            this.stream_count_l--;
        }
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    protected void fall(float distance) {}

    protected void updateFallState(double distanceFallen, boolean onGround) {}

    public boolean doesEntityNotTriggerPressurePlate() {
        return false;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = false;
        float dm = amount;
        if (this.hurt_timer > 0) {
            return false;
        } else {
            if (amount > 750.0F) {
                dm = 750.0F;
            }

            if (source.getDamageType()
                .equals("inWall")) {
                return false;
            } else {
                this.mood = 1;
                if (source.isExplosion()) {
                    float s = this.getHealth();
                    s += amount / 2.0F;
                    if (s > this.getMaxHealth()) {
                        s = this.getMaxHealth();
                    }

                    this.setHealth(s);
                    return false;
                } else {
                    Entity e = source.getEntity();
                    if (e != null && e instanceof EntityLivingBase) {
                        if (e instanceof PurplePower) {
                            return false;
                        }

                        float s = e.height * e.width;
                        if (e instanceof EntityMob && s < 3.0F) {
                            e.setDead();
                            return false;
                        }
                    }

                    if (!source.getDamageType()
                        .equals("cactus")) {
                        this.hurt_timer = 20;
                        ret = super.attackEntityFrom(source, dm);
                        if (e != null && e instanceof EntityPlayer) {
                            this.player_hit_count++;
                        }

                        if (e != null && e instanceof EntityLivingBase
                            && this.currentFlightTarget != null
                            && !MyUtils.isRoyalty(e)) {
                            this.rt = (EntityLivingBase) e;
                            int dist = (int) e.posY;
                            if (dist > 230) {
                                dist = 230;
                            }

                            this.currentFlightTarget.set((int) e.posX, dist, (int) e.posZ);
                        }
                    }

                    return ret;
                }
            }
        }
    }

    public boolean getCanSpawnHere() {
        return true;
    }

    public int getTotalArmorValue() {
        if (this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() * 2 / 3) {
            return OreSpawnMain.TheQueen_stats.defense + 2;
        } else if (this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() / 2) {
            return OreSpawnMain.TheQueen_stats.defense + 3;
        } else {
            return this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() / 3
                ? OreSpawnMain.TheQueen_stats.defense + 5
                : OreSpawnMain.TheQueen_stats.defense;
        }
    }

    public void onStruckByLightning(EntityLightningBolt lightning) {}

    public void initCreature() {}

    public boolean MyCanSee(EntityLivingBase e) {
        double xzoff = 10.0;
        int nblks = 20;
        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
        float startx = (float) cx;
        float starty = (float) (this.posY + 14.0);
        float startz = (float) cz;
        float dx = (float) ((e.posX - startx) / 20.0);
        float dy = (float) ((e.posY + e.height / 2.0F - starty) / 20.0);
        float dz = (float) ((e.posZ - startz) / 20.0);
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
            if (bid != Blocks.air) {
                return false;
            }
        }

        return true;
    }

    private boolean isSuitableTarget(EntityLivingBase target, boolean checkSight) {
        if (target == null) {
            return false;
        } else if (target == this) {
            return false;
        } else if (!target.isEntityAlive()) {
            return false;
        } else if (target instanceof QueenHead) {
            this.head_found = 1;
            return false;
        } else if (MyUtils.isRoyalty(target)) {
            return false;
        } else {
            float d1 = (float) (target.posX - this.homex);
            float d2 = (float) (target.posZ - this.homez);
            d1 = (float) Math.sqrt(d1 * d1 + d2 * d2);
            if (d1 > 144.0F) {
                return false;
            } else if (MyUtils.isIgnoreable(target)) {
                return false;
            } else if (!this.getEntitySenses()
                .canSee(target)) {
                    return false;
                } else if (target instanceof EntityPlayer) {
                    EntityPlayer p = (EntityPlayer) target;
                    return !p.capabilities.isCreativeMode;
                } else if (target instanceof EntityHorse) {
                    return true;
                } else if (target instanceof EntityMob) {
                    return true;
                } else {
                    return target instanceof EntityDragon ? true : MyUtils.isAttackableNonMob(target);
                }
        }
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely == 0 && !this.isHappy()) {
            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(80.0, 60.0, 80.0));
            Collections.sort(entities, this.TargetSorter);
            Iterator iterator = entities.iterator();
            Entity entity = null;
            EntityLivingBase target = null;
            EntityLivingBase ret = null;
            this.head_found = 0;

            while (iterator.hasNext()) {
                entity = (Entity) iterator.next();
                target = (EntityLivingBase) entity;
                if (this.isSuitableTarget(target, false) && ret == null) {
                    ret = target;
                }

                if (ret != null && this.head_found != 0) {
                    break;
                }
            }

            return ret;
        } else {
            this.head_found = 1;
            return null;
        }
    }

    public void setGuardMode(int i) {
        this.guard_mode = i;
    }

    public void setBadMood(int i) {
        this.always_mad = i;
    }

    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger("KingHomeX", this.homex);
        tag.setInteger("KingHomeZ", this.homez);
        tag.setInteger("GuardMode", this.guard_mode);
        tag.setInteger("PlayerHits", this.player_hit_count);
        tag.setInteger("MeanMode", this.always_mad);
    }

    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        this.homex = tag.getInteger("KingHomeX");
        this.homez = tag.getInteger("KingHomeZ");
        this.guard_mode = tag.getInteger("GuardMode");
        this.player_hit_count = tag.getInteger("PlayerHits");
        this.always_mad = tag.getInteger("MeanMode");
    }

    public static Entity spawnCreature(World world, String str, double x, double y, double z) {
        Entity entity = null;
        entity = EntityList.createEntityByName(str, world);
        if (entity != null) {
            entity.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0F, 0.0F);
            world.spawnEntityInWorld(entity);
        }

        return entity;
    }

    private EntityLivingBase doJumpDamage(double X, double Y, double Z, double dist, double damage, int knock) {
        AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(X - dist, Y - 10.0, Z - dist, X + dist, Y + 10.0, Z + dist);
        List entities = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, bb);
        Collections.sort(entities, this.TargetSorter);
        Iterator iterator = entities.iterator();
        Entity entity = null;
        EntityLivingBase e = null;

        while (iterator.hasNext()) {
            entity = (Entity) iterator.next();
            e = (EntityLivingBase) entity;
            if (e != null && e != this
                && e.isEntityAlive()
                && !MyUtils.isRoyalty(e)
                && !(e instanceof Ghost)
                && !(e instanceof GhostSkelly)) {
                DamageSource source = null;
                source = DamageSource.setExplosionSource(null);
                source.setExplosion();
                e.attackEntityFrom(source, (float) damage / 2.0F);
                e.attackEntityFrom(DamageSource.fall, (float) damage / 2.0F);
                this.worldObj.playSoundAtEntity(
                    e,
                    "random.explode",
                    0.65F,
                    1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F);
                if (knock != 0) {
                    double ks = 2.75;
                    double inair = 0.65;
                    float f3 = (float) Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                    e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                }
            }
        }

        return null;
    }
}
