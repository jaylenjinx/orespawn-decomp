package danger.orespawn.entity.projectile;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import danger.orespawn.MyUtils;
import danger.orespawn.entity.Dragon;
import danger.orespawn.entity.Godzilla;
import danger.orespawn.entity.GodzillaHead;
import danger.orespawn.entity.Kraken;
import danger.orespawn.entity.Mothra;
import danger.orespawn.entity.PitchBlack;

public class BetterFireball extends EntityFireball {

    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private int inTile = 0;
    private boolean inGround = false;
    public EntityLivingBase shootingEntity;
    private int ticksAlive;
    private int ticksInAir = 0;
    public double accelerationX;
    public double accelerationY;
    public double accelerationZ;
    public int field_92012_e = 1;
    private int notme = 0;
    private boolean small = false;

    public BetterFireball(World world) {
        super(world);
        this.setSize(1.0F, 1.0F);
    }

    protected void entityInit() {}

    public BetterFireball(World world, EntityLivingBase living, double x, double y, double z) {
        super(world);
        this.shootingEntity = living;
        this.setSize(1.0F, 1.0F);
        this.setLocationAndAngles(living.posX, living.posY, living.posZ, living.rotationYaw, living.rotationPitch);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        this.motionX = this.motionY = this.motionZ = 0.0;
        double distance = MathHelper.sqrt_double(x * x + y * y + z * z);
        this.accelerationX = x / distance * 0.1;
        this.accelerationY = y / distance * 0.1;
        this.accelerationZ = z / distance * 0.1;
    }

    public void setNotMe() {
        this.notme = 1;
    }

    public void setBig() {
        this.field_92012_e = 2;
    }

    public void setReallyBig() {
        this.field_92012_e = 4;
    }

    public void setSmall() {
        this.small = true;
        this.setSize(0.3125F, 0.3125F);
    }

    public void onUpdate() {
        Vec3 vec = null;
        Vec3 vec2 = null;
        MovingObjectPosition hit = null;
        Entity entity = null;
        List entities = null;
        double d = 0.0;
        Entity e = null;
        float f = 0.3F;
        double d2 = 0.0;
        float distance = 0.0F;
        float motionFactor = 0.0F;
        float f2 = 0.0F;
        if (this.ticksAlive < 600 && this.ticksInAir < 600) {
            if (this.worldObj.isRemote || (this.shootingEntity == null || !this.shootingEntity.isDead)
                && this.worldObj.blockExists((int) this.posX, (int) this.posY, (int) this.posZ)) {
                super.onUpdate();
                this.setFire(1);
                if (this.inGround) {
                    Block block = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
                    if (block != Blocks.air) {
                        this.ticksAlive++;
                    }

                    this.inGround = false;
                    this.motionX = this.motionX * (this.rand.nextFloat() * 0.2F);
                    this.motionY = this.motionY * (this.rand.nextFloat() * 0.2F);
                    this.motionZ = this.motionZ * (this.rand.nextFloat() * 0.2F);
                } else {
                    this.ticksInAir++;
                }

                vec = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
                vec2 = Vec3
                    .createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
                hit = this.worldObj.rayTraceBlocks(vec, vec2, false);
                vec = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
                vec2 = Vec3
                    .createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
                if (hit != null) {
                    vec2 = Vec3.createVectorHelper(hit.hitVec.xCoord, hit.hitVec.yCoord, hit.hitVec.zCoord);
                }

                entity = null;
                entities = this.worldObj.getEntitiesWithinAABBExcludingEntity(
                    this,
                    this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ)
                        .expand(1.0, 1.0, 1.0));
                d = 0.0;

                for (int i = 0; i < entities.size(); i++) {
                    e = (Entity) entities.get(i);
                    if (this.shootingEntity == e) {
                        hit = null;
                        break;
                    }

                    if (e instanceof BetterFireball) {
                        hit = null;
                        break;
                    }

                    if (e instanceof GodzillaHead) {
                        hit = null;
                        break;
                    }

                    if (MyUtils.isRoyalty(e)) {
                        hit = null;
                        break;
                    }

                    if (this.notme != 0 && (e instanceof EntityPlayer || e instanceof Dragon || e instanceof Mothra)) {
                        hit = null;
                        break;
                    }

                    if (e.canBeCollidedWith() && (!e.isEntityEqual(this.shootingEntity) || this.ticksInAir >= 25)) {
                        AxisAlignedBB box = e.boundingBox.expand(f, f, f);
                        MovingObjectPosition hit2 = box.calculateIntercept(vec, vec2);
                        if (hit2 != null) {
                            d2 = vec.distanceTo(hit2.hitVec);
                            if (d2 < d || d == 0.0) {
                                entity = e;
                                d = d2;
                            }
                        }
                    }
                }

                if (entity != null) {
                    hit = new MovingObjectPosition(entity);
                }

                if (hit != null) {
                    this.onImpact(hit);
                }

                this.posX = this.posX + this.motionX;
                this.posY = this.posY + this.motionY;
                this.posZ = this.posZ + this.motionZ;
                distance = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                this.rotationYaw = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) + 90.0F;
                this.rotationPitch = (float) (Math.atan2(distance, this.motionY) * 180.0 / Math.PI) - 90.0F;

                while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
                    this.prevRotationPitch -= 360.0F;
                }

                while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
                    this.prevRotationPitch += 360.0F;
                }

                while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
                    this.prevRotationYaw -= 360.0F;
                }

                while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
                    this.prevRotationYaw += 360.0F;
                }

                this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
                this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
                motionFactor = this.getMotionFactor();
                if (this.isInWater()) {
                    for (int i = 0; i < 4; i++) {
                        f2 = 0.25F;
                        this.worldObj.spawnParticle(
                            "bubble",
                            this.posX - this.motionX * f2,
                            this.posY - this.motionY * f2,
                            this.posZ - this.motionZ * f2,
                            this.motionX,
                            this.motionY,
                            this.motionZ);
                    }

                    motionFactor = 0.8F;
                }

                this.motionX = this.motionX + this.accelerationX;
                this.motionY = this.motionY + this.accelerationY;
                this.motionZ = this.motionZ + this.accelerationZ;
                this.motionX *= motionFactor;
                this.motionY *= motionFactor;
                this.motionZ *= motionFactor;
                this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0);
                this.setPosition(this.posX, this.posY, this.posZ);
            } else {
                this.setDead();
            }
        } else {
            this.setDead();
        }
    }

    protected void onImpact(MovingObjectPosition hit) {
        if (!this.worldObj.isRemote) {
            if (hit.entityHit != null) {
                if (hit.entityHit instanceof BetterFireball) {
                    return;
                }

                if (hit.entityHit instanceof Mothra) {
                    return;
                }

                if (this.notme != 0 && (hit.entityHit instanceof Dragon || hit.entityHit instanceof EntityPlayer)) {
                    this.setDead();
                    return;
                }

                Entity e = hit.entityHit;
                if (e instanceof EntityLiving) {
                    EntityLiving el = (EntityLiving) e;
                    if (el.width * el.height > 30.0F && !MyUtils.isRoyalty(el)
                        && !(el instanceof Godzilla)
                        && !(el instanceof GodzillaHead)
                        && !(el instanceof PitchBlack)
                        && !(el instanceof Kraken)) {
                        el.setHealth(el.getHealth() / 2.0F);
                    }
                }

                if (!this.small) {
                    hit.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 10.0F);
                    hit.entityHit.setFire(5);
                } else {
                    hit.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 5.0F);
                    hit.entityHit.setFire(5);
                }
            } else {
                int i = hit.blockX;
                int j = hit.blockY;
                int k = hit.blockZ;
                switch (hit.sideHit) {
                    case 0:
                        j--;
                        break;
                    case 1:
                        j++;
                        break;
                    case 2:
                        k--;
                        break;
                    case 3:
                        k++;
                        break;
                    case 4:
                        i--;
                        break;
                    case 5:
                        i++;
                }

                if (this.worldObj.isAirBlock(i, j, k)) {
                    this.worldObj.setBlock(i, j, k, Blocks.fire);
                }
            }

            if (!this.small) {
                this.worldObj.newExplosion(
                    (Entity) null,
                    this.posX,
                    this.posY,
                    this.posZ,
                    this.field_92012_e,
                    true,
                    this.worldObj.getGameRules()
                        .getGameRuleBooleanValue("mobGriefing"));
            }

            this.setDead();
        }
    }

    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger("ExplosionPower", this.field_92012_e);
    }

    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        if (tag.hasKey("ExplosionPower")) {
            this.field_92012_e = tag.getInteger("ExplosionPower");
        }
    }
}
