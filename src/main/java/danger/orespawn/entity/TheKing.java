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
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
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
import danger.orespawn.entity.projectile.IceBall;
import danger.orespawn.entity.projectile.ThunderBolt;

public class TheKing extends EntityMob {

    private ChunkCoordinates currentFlightTarget = null;
    private GenericTargetSorter TargetSorter = null;
    private EntityLivingBase rt = null;
    private double attdam = 250.0;
    private int hurt_timer = 0;
    private int homex = 0;
    private int homez = 0;
    private int stream_count = 0;
    private int stream_count_l = 0;
    private int stream_count_i = 0;
    private int ticker = 0;
    private int player_hit_count = 0;
    private int backoff_timer = 0;
    private int guard_mode = 0;
    private volatile int head_found = 0;
    private int wing_sound = 0;
    private int large_unknown_detected = 0;
    private int isEnd = 0;
    private int endCounter = 0;

    public TheKing(World world) {
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
        this.attdam = OreSpawnMain.TheKing_stats.attack;
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(this.attdam);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
        this.dataWatcher.addObject(21, OreSpawnMain.PlayNicely);
        this.dataWatcher.addObject(22, this.isEnd);
    }

    public int getPlayNicely() {
        return this.dataWatcher.getWatchableObjectInt(21);
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

    public final int getAttacking() {
        return this.dataWatcher.getWatchableObjectByte(20);
    }

    public final void setAttacking(int attacking) {
        this.dataWatcher.updateObject(20, (byte) attacking);
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
        return OreSpawnMain.TheKing_stats.health;
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
        Item it = null;
        Block bl = null;
        spawnCreature(this.worldObj, "The Prince", this.posX, this.posY + 10.0, this.posZ);
        this.dropItemRand(OreSpawnMain.RoyalBody, 1);
        this.dropItemRand(OreSpawnMain.RoyalHelmet, 1);
        this.dropItemRand(OreSpawnMain.RoyalLegs, 1);
        this.dropItemRand(OreSpawnMain.RoyalBoots, 1);
        this.dropItemRand(OreSpawnMain.MyRoyal, 1);
        Iterator ilist = Item.itemRegistry.iterator();
        int icount = 0;

        while (ilist.hasNext()) {
            it = (Item) ilist.next();
            if (it != null) {
                icount++;
            }
        }

        int j = 0;

        while (j < 150) {
            int k = 1 + this.worldObj.rand.nextInt(icount);

            for (Iterator iterator = Item.itemRegistry.iterator(); k > 0 && iterator.hasNext(); k--) {
                it = (Item) iterator.next();
            }

            if (it != null) {
                j++;
                this.dropItemRand(it, 1);
            }
        }

        Iterator blist = Block.blockRegistry.iterator();
        int bcount = 0;

        while (blist.hasNext()) {
            bl = (Block) blist.next();
            if (bl != null) {
                bcount++;
            }
        }

        j = 0;

        while (j < 150) {
            int k = 1 + this.worldObj.rand.nextInt(bcount);

            for (Iterator iterator = Block.blockRegistry.iterator(); k > 0 && iterator.hasNext(); k--) {
                bl = (Block) iterator.next();
            }

            if (bl != null) {
                j++;
                this.dropItemRand(Item.getItemFromBlock(bl), 1);
            }
        }
    }

    protected boolean isAIEnabled() {
        return true;
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
        if (this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() * 2 / 3) {
            this.attdam = OreSpawnMain.TheKing_stats.attack * 2;
        }

        if (this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() / 2) {
            this.attdam = OreSpawnMain.TheKing_stats.attack * 4;
        }

        if (this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() / 4) {
            this.attdam = OreSpawnMain.TheKing_stats.attack * 8;
        }

        if (this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() / 8) {
            this.attdam = OreSpawnMain.TheKing_stats.attack * 16;
        }

        if (this.worldObj.isRemote) {
            float f = 7.0F;
            this.isEnd = this.dataWatcher.getWatchableObjectInt(22);
            if (this.isEnd != 0 && this.worldObj.rand.nextInt(3) == 1) {
                for (int i = 0; i < 10; i++) {
                    this.worldObj.spawnParticle(
                        "fireworksSpark",
                        this.posX - f * Math.sin(Math.toRadians(this.rotationYaw)),
                        this.posY + 14.0,
                        this.posZ + f * Math.cos(Math.toRadians(this.rotationYaw)),
                        (this.worldObj.rand.nextGaussian() - this.worldObj.rand.nextGaussian()) / 4.0
                            + this.motionX * 6.0,
                        (this.worldObj.rand.nextGaussian() - this.worldObj.rand.nextGaussian()) / 4.0,
                        (this.worldObj.rand.nextGaussian() - this.worldObj.rand.nextGaussian()) / 4.0
                            + this.motionZ * 6.0);
                }
            }
        }
    }

    public boolean attackEntityAsMob(Entity target) {
        if (target != null && target instanceof EntityLivingBase) {
            float s = target.height * target.width;
            if (s > 30.0F && !MyUtils.isRoyalty(target)
                && !(target instanceof Godzilla)
                && !(target instanceof GodzillaHead)
                && !(target instanceof PitchBlack)
                && !(target instanceof Kraken)) {
                EntityLivingBase e = (EntityLivingBase) target;
                e.setHealth(e.getHealth() / 2.0F);
                e.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.attdam * 10.0F);
                this.large_unknown_detected = 1;
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
            double ks = 3.3;
            double inair = 0.25;
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

    private void msgToPlayers(String s) {
        List entities = this.worldObj
            .getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(80.0, 64.0, 80.0));
        Collections.sort(entities, this.TargetSorter);
        Iterator iterator = entities.iterator();
        Entity entity = null;
        EntityPlayer player = null;

        while (iterator.hasNext()) {
            entity = (Entity) iterator.next();
            player = (EntityPlayer) entity;
            player.addChatComponentMessage(new ChatComponentText(s));
        }
    }

    private EntityPlayer findNearestPlayer() {
        List entities = this.worldObj
            .getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(80.0, 64.0, 80.0));
        Collections.sort(entities, this.TargetSorter);
        Iterator iterator = entities.iterator();
        Entity entity = null;
        EntityPlayer player = null;

        while (iterator.hasNext()) {
            entity = (Entity) iterator.next();
            if (entity instanceof EntityPlayer) {
                player = (EntityPlayer) entity;
            }

            if (player != null) {
                break;
            }
        }

        return player;
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
        float f3 = 0.0F;
        float f4 = 0.0F;
        EntityPlayer p = null;
        if (!this.isDead) {
            super.updateAITasks();
            this.dataWatcher.updateObject(22, this.isEnd);
            this.dataWatcher.updateObject(21, OreSpawnMain.PlayNicely);
            if (this.isEnd == 1) {
                this.endCounter++;
                this.noClip = true;
                this.motionX = 0.0;
                this.motionY = 0.0;
                this.motionZ = 0.0;
                this.hurt_timer = 10;
                if (!this.isDead) {
                    p = this.findNearestPlayer();
                    if (p != null) {
                        this.faceEntity(p, 10.0F, 10.0F);
                        p.motionX = 0.0;
                        p.motionY = 0.0;
                        p.motionZ = 0.0;
                        double dd0 = this.posX - p.posX;
                        double dd1 = this.posZ - p.posZ;
                        float f2 = (float) (Math.atan2(dd1, dd0) * 180.0 / Math.PI) - 90.0F;
                        p.rotationYaw = f2;
                        p.setHealth(1.0F);
                    }

                    if (this.endCounter == 10) {
                        this.msgToPlayers(
                            "The King: Enough of this charade. I am done. You have shown me what I wanted to know.");
                    } else if (this.endCounter == 80) {
                        this.msgToPlayers(
                            "The King: That's right my little pet. It has all been a game. You never killed me. You can't.");
                    } else if (this.endCounter == 160) {
                        this.msgToPlayers(
                            "The King: I am the one. The only. The many. I exist within both space and time. Everywhere and always.");
                    } else if (this.endCounter == 240) {
                        this.msgToPlayers(
                            "The King: I used you to learn your ways, and I have reached my conclusion on your species.");
                    } else if (this.endCounter == 300) {
                        this.msgToPlayers("The King: You have 10 seconds to run...");
                    } else if (this.endCounter == 320) {
                        this.msgToPlayers("9.");
                    } else if (this.endCounter == 340) {
                        this.msgToPlayers("8.");
                    } else if (this.endCounter == 360) {
                        this.msgToPlayers("7.");
                    } else if (this.endCounter == 380) {
                        this.msgToPlayers("6.");
                    } else if (this.endCounter == 400) {
                        this.msgToPlayers("5.");
                    } else if (this.endCounter == 420) {
                        this.msgToPlayers("4.");
                    } else if (this.endCounter == 440) {
                        this.msgToPlayers("3.");
                    } else if (this.endCounter == 460) {
                        this.msgToPlayers("2.");
                    } else if (this.endCounter == 480) {
                        this.msgToPlayers("1.");
                    } else if (this.endCounter == 500) {
                        this.msgToPlayers("The King: Prepare to die!");
                        this.isEnd = 2;
                    }
                }
            } else {
                if (this.isEnd == 2) {
                    this.hurt_timer = 10;
                    this.player_hit_count = 0;
                    this.stream_count = 10;
                    this.stream_count_l = 10;
                    this.stream_count_i = 10;
                    attrand = 3;
                    this.guard_mode = 0;
                    this.large_unknown_detected = 1;
                    if (this.backoff_timer > 0) {
                        this.backoff_timer--;
                    }
                }

                if (this.hurt_timer > 0) {
                    this.hurt_timer--;
                }

                if (this.homex == 0 && this.homez == 0 || this.guard_mode == 0) {
                    this.homex = (int) this.posX;
                    this.homez = (int) this.posZ;
                }

                this.ticker++;
                if (this.ticker > 30000) {
                    this.ticker = 0;
                }

                if (this.ticker % 80 == 0) {
                    this.stream_count = 10;
                }

                if (this.ticker % 90 == 0) {
                    this.stream_count_l = 5;
                }

                if (this.ticker % 70 == 0) {
                    this.stream_count_i = 8;
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

                    for (int i = -5; i <= 5; i += 5) {
                        for (int j = -5; j <= 5; j += 5) {
                            Block bid = this.worldObj.getBlock(this.homex + j, (int) this.posY, this.homez + i);
                            if (bid != Blocks.air) {
                                for (int k = 1; k < 20; k++) {
                                    bid = this.worldObj.getBlock(this.homex + j, (int) this.posY + k, this.homez + i);
                                    dist++;
                                    if (bid == Blocks.air) {
                                        break;
                                    }
                                }
                            } else {
                                for (int kx = 1; kx < 20; kx++) {
                                    bid = this.worldObj.getBlock(this.homex + j, (int) this.posY - kx, this.homez + i);
                                    dist--;
                                    if (bid != Blocks.air) {
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
                } else if (this.worldObj.rand.nextInt(attrand) == 0) {
                    e = this.rt;
                    if (OreSpawnMain.PlayNicely != 0) {
                        e = null;
                    }

                    if (e != null && (e instanceof TheKing || e instanceof KingHead)) {
                        this.rt = null;
                        e = null;
                    }

                    if (e != null) {
                        float d1 = (float) (e.posX - this.homex);
                        float d2 = (float) (e.posZ - this.homez);
                        d1 = (float) Math.sqrt(d1 * d1 + d2 * d2);
                        if (e.isDead || this.worldObj.rand.nextInt(250) == 1 || d1 > 128.0F && this.guard_mode == 1) {
                            e = null;
                            this.rt = null;
                        }

                        if (e != null && !this.MyCanSee(e)) {
                            e = null;
                        }
                    }

                    f = this.findSomethingToAttack();
                    if (this.head_found == 0) {
                        EntityLiving living = (EntityLiving) spawnCreature(
                            this.worldObj,
                            "KingHead",
                            this.posX,
                            this.posY + 20.0,
                            this.posZ);
                    }

                    if (e == null) {
                        e = f;
                    }

                    if (e != null) {
                        this.setAttacking(1);
                        if (this.backoff_timer == 0) {
                            int dist = (int) (e.posY + e.height / 2.0F + 1.0);
                            if (dist > 230) {
                                dist = 230;
                            }

                            this.currentFlightTarget.set((int) e.posX, dist, (int) e.posZ);
                            if (this.worldObj.rand.nextInt(70) == 1) {
                                this.backoff_timer = 80 + this.worldObj.rand.nextInt(80);
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

                                for (int i = -5; i <= 5; i += 5) {
                                    for (int jx = -5; jx <= 5; jx += 5) {
                                        Block bid = this.worldObj
                                            .getBlock((int) e.posX + jx, (int) this.posY, (int) e.posZ + i);
                                        if (bid != Blocks.air) {
                                            for (int kxx = 1; kxx < 20; kxx++) {
                                                bid = this.worldObj.getBlock(
                                                    (int) e.posX + jx,
                                                    (int) this.posY + kxx,
                                                    (int) e.posZ + i);
                                                distx++;
                                                if (bid == Blocks.air) {
                                                    break;
                                                }
                                            }
                                        } else {
                                            for (int kxxx = 1; kxxx < 20; kxxx++) {
                                                bid = this.worldObj.getBlock(
                                                    (int) e.posX + jx,
                                                    (int) this.posY - kxxx,
                                                    (int) e.posZ + i);
                                                distx--;
                                                if (bid != Blocks.air) {
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
                                    OreSpawnMain.TheKing_stats.attack / 4,
                                    0);
                            }

                            this.attackEntityAsMob(e);
                        }

                        double dx = this.posX + 20.0 * Math.sin(Math.toRadians(this.rotationYawHead));
                        double dz = this.posZ - 20.0 * Math.cos(Math.toRadians(this.rotationYawHead));
                        if (this.worldObj.rand.nextInt(3) == 1) {
                            this.doJumpDamage(dx, this.posY + 10.0, dz, 15.0, OreSpawnMain.TheKing_stats.attack / 2, 1);
                        }

                        if (this.getHorizontalDistanceSqToEntity(e) > 900.0) {
                            which = this.worldObj.rand.nextInt(3);
                            if (which == 0) {
                                if (this.stream_count > 0) {
                                    this.setAttacking(1);
                                    rr = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                                    rhdir = Math.toRadians((this.rotationYawHead + 90.0F) % 360.0F);
                                    rdd = Math.abs(rr - rhdir) % (pi * 2.0);
                                    if (rdd > pi) {
                                        rdd -= pi * 2.0;
                                    }

                                    rdd = Math.abs(rdd);
                                    if (rdd < 0.5) {
                                        this.firecanon(e);
                                    }
                                }
                            } else if (which == 1) {
                                if (this.stream_count_l > 0) {
                                    this.setAttacking(1);
                                    rr = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                                    rhdir = Math.toRadians((this.rotationYawHead + 90.0F) % 360.0F);
                                    rdd = Math.abs(rr - rhdir) % (pi * 2.0);
                                    if (rdd > pi) {
                                        rdd -= pi * 2.0;
                                    }

                                    rdd = Math.abs(rdd);
                                    if (rdd < 0.5) {
                                        this.firecanonl(e);
                                    }
                                }
                            } else if (this.stream_count_i > 0) {
                                this.setAttacking(1);
                                rr = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                                rhdir = Math.toRadians((this.rotationYawHead + 90.0F) % 360.0F);
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
                    } else {
                        this.setAttacking(0);
                        this.stream_count = 10;
                        this.stream_count_l = 5;
                        this.stream_count_i = 8;
                    }
                }

                if (this.getAttacking() != 0 && this.isEnd == 2) {
                    double xzoff = 10.0;
                    double yoff = 14.0;
                    Entity ppwr = spawnCreature(
                        this.worldObj,
                        "PurplePower",
                        this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw)),
                        this.posY + yoff,
                        this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)));
                    if (ppwr != null) {
                        PurplePower pwr = (PurplePower) ppwr;
                        pwr.motionX = this.motionX * 3.0;
                        pwr.motionZ = this.motionZ * 3.0;
                        pwr.setPurpleType(10);
                    }
                }

                dx2 = this.currentFlightTarget.posX + 0.5 - this.posX;
                dy = this.currentFlightTarget.posY + 0.1 - this.posY;
                dz2 = this.currentFlightTarget.posZ + 0.5 - this.posZ;
                this.motionX = this.motionX + (Math.signum(dx2) * 0.7 - this.motionX) * 0.35;
                this.motionY = this.motionY + (Math.signum(dy) * 0.69999 - this.motionY) * 0.3;
                this.motionZ = this.motionZ + (Math.signum(dz2) * 0.7 - this.motionZ) * 0.35;
                f3 = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
                f4 = MathHelper.wrapAngleTo180_float(f3 - this.rotationYaw);
                this.moveForward = 1.0F;
                this.rotationYaw += f4 / 8.0F;
                if (this.worldObj.rand.nextInt(30) == 1 && this.getHealth() < this.mygetMaxHealth()) {
                    this.heal(5.0F);
                    if (this.large_unknown_detected != 0) {
                        this.heal(200.0F);
                    }
                }

                if (this.player_hit_count < 10 && this.getHealth() < 2000.0F) {
                    this.heal(2000.0F - this.getHealth());
                }
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

            this.stream_count_l--;
        }
    }

    private void firecanoni(EntityLivingBase e) {
        double yoff = 14.0;
        double xzoff = 32.0;
        double dx = 0.0;
        double dy = 0.0;
        double dz = 0.0;
        float f = 0.0F;
        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
        if (this.stream_count_i > 0) {
            this.worldObj.playSoundAtEntity(
                this,
                "random.bow",
                1.0F,
                1.0F / (this.getRNG()
                    .nextFloat() * 0.4F + 0.8F));

            for (int i = 0; i < 5; i++) {
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

            this.stream_count_i--;
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
                Entity e = source.getEntity();
                if (e != null && e instanceof EntityLivingBase) {
                    EntityLivingBase enl = (EntityLivingBase) e;
                    float s = enl.height * enl.width;
                    if (s > 30.0F && !MyUtils.isRoyalty(enl)
                        && !(enl instanceof Godzilla)
                        && !(enl instanceof GodzillaHead)
                        && !(enl instanceof PitchBlack)
                        && !(enl instanceof Kraken)) {
                        dm /= 10.0F;
                        this.hurt_timer = 50;
                        this.large_unknown_detected = 1;
                    }

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

    public boolean getCanSpawnHere() {
        return true;
    }

    public int getTotalArmorValue() {
        if (this.large_unknown_detected != 0) {
            return 25;
        } else if (this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() * 2 / 3) {
            return OreSpawnMain.TheKing_stats.defense + 1;
        } else if (this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() / 2) {
            return OreSpawnMain.TheKing_stats.defense + 2;
        } else {
            return this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() / 4
                ? OreSpawnMain.TheKing_stats.defense + 3
                : OreSpawnMain.TheKing_stats.defense;
        }
    }

    public void onStruckByLightning(EntityLightningBolt lightning) {}

    public void initCreature() {}

    public boolean MyCanSee(EntityLivingBase e) {
        double xzoff = 22.0;
        int nblks = 20;
        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
        float startx = (float) cx;
        float starty = (float) (this.posY + this.height * 7.0F / 8.0F);
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
            if (bid != Blocks.flowing_water && bid != Blocks.water
                && bid != Blocks.leaves
                && bid != Blocks.vine
                && bid != Blocks.air) {
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
        } else if (target instanceof KingHead) {
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
            } else {
                if (this.isEnd == 2) {
                    if (target instanceof EntityPlayer) {
                        EntityPlayer p = (EntityPlayer) target;
                        if (p.capabilities.isCreativeMode) {
                            return false;
                        }

                        return true;
                    }

                    if (target instanceof Girlfriend) {
                        return true;
                    }

                    if (target instanceof Boyfriend) {
                        return true;
                    }

                    if (target instanceof EntityVillager) {
                        return true;
                    }
                }

                if (!this.MyCanSee(target)) {
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
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            this.head_found = 1;
            return null;
        } else {
            if (this.isEnd == 2) {
                List var5p = this.worldObj
                    .getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(80.0, 64.0, 80.0));
                Collections.sort(var5p, this.TargetSorter);
                Iterator var2p = var5p.iterator();
                Entity var3p = null;
                EntityLivingBase var4p = null;
                EntityLivingBase retp = null;
                this.head_found = 1;

                while (var2p.hasNext()) {
                    var3p = (Entity) var2p.next();
                    var4p = (EntityLivingBase) var3p;
                    if (this.isSuitableTarget(var4p, false)) {
                        return var4p;
                    }
                }
            }

            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(80.0, 64.0, 80.0));
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
        }
    }

    public void setGuardMode(int i) {
        this.guard_mode = i;
    }

    public void setFree() {
        this.isEnd = 1;
    }

    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger("KingHomeX", this.homex);
        tag.setInteger("KingHomeZ", this.homez);
        tag.setInteger("GuardMode", this.guard_mode);
        tag.setInteger("PlayerHits", this.player_hit_count);
        tag.setInteger("IsEnd", this.isEnd);
        tag.setInteger("EndCounter", this.endCounter);
    }

    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        this.homex = tag.getInteger("KingHomeX");
        this.homez = tag.getInteger("KingHomeZ");
        this.guard_mode = tag.getInteger("GuardMode");
        this.player_hit_count = tag.getInteger("PlayerHits");
        this.isEnd = tag.getInteger("IsEnd");
        this.endCounter = tag.getInteger("EndCounter");
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
