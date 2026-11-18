package danger.orespawn.entity.projectile;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.Boyfriend;
import danger.orespawn.entity.Cephadrome;
import danger.orespawn.entity.Dragon;
import danger.orespawn.entity.Elevator;
import danger.orespawn.entity.Girlfriend;

public class IrukandjiArrow extends EntityArrow {

    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private int inTile = 0;
    private int inData = 0;
    private boolean inGround = false;
    private int ticksInGround;
    private int ticksInAir = 0;
    private int knockbackStrength;

    public IrukandjiArrow(World world) {
        super(world);
    }

    public IrukandjiArrow(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public IrukandjiArrow(World world, EntityLiving living2, EntityLivingBase living, float f, float f2) {
        super(world, living2, living, f, f2);
    }

    public IrukandjiArrow(World world, EntityPlayer player, float f) {
        super(world, player, f);
    }

    protected void entityInit() {
        this.dataWatcher.addObject(16, (byte) 0);
    }

    public void setThrowableHeading(double vx, double vy, double vz, float velocity, float inaccuracy) {
        float distance = MathHelper.sqrt_double(vx * vx + vy * vy + vz * vz);
        vx /= distance;
        vy /= distance;
        vz /= distance;
        vx *= velocity;
        vy *= velocity;
        vz *= velocity;
        this.motionX = vx;
        this.motionY = vy;
        this.motionZ = vz;
        float distance2 = MathHelper.sqrt_double(vx * vx + vz * vz);
        this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(vx, vz) * 180.0 / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(vy, distance2) * 180.0 / Math.PI);
        this.ticksInGround = 0;
    }

    public void onUpdate() {
        super.onEntityUpdate();
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float distance = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0
                / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, distance) * 180.0
                / Math.PI);
        }

        Block block = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
        if (block != Blocks.air) {
            block.setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
            AxisAlignedBB collisionBoundingBoxFromPool = block
                .getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);
            if (collisionBoundingBoxFromPool != null
                && collisionBoundingBoxFromPool.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ))) {
                this.inGround = true;
            }
        }

        if (this.arrowShake > 0) {
            this.arrowShake--;
        }

        if (this.inGround) {
            Block block2 = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
            int meta = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
            if (block2 != Blocks.air && meta == this.inData) {
                this.ticksInGround++;
                if (this.ticksInGround == 50 && !this.worldObj.isRemote) {
                    this.dropItem(OreSpawnMain.MyIrukandjiArrow, 1);
                    this.setDead();
                }
            } else {
                this.inGround = false;
                this.motionX = this.motionX * (this.rand.nextFloat() * 0.2F);
                this.motionY = this.motionY * (this.rand.nextFloat() * 0.2F);
                this.motionZ = this.motionZ * (this.rand.nextFloat() * 0.2F);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        } else {
            this.ticksInAir++;
            Vec3 vec = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            Vec3 vec2 = Vec3
                .createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition hit = this.worldObj.rayTraceBlocks(vec, vec2, true);
            vec = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            vec2 = Vec3
                .createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            if (hit != null) {
                vec2 = Vec3.createVectorHelper(hit.hitVec.xCoord, hit.hitVec.yCoord, hit.hitVec.zCoord);
            }

            Entity entity = null;
            List entities = this.worldObj.getEntitiesWithinAABBExcludingEntity(
                this,
                this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ)
                    .expand(1.0, 1.0, 1.0));
            double d = 0.0;

            for (int i = 0; i < entities.size(); i++) {
                Entity entity2 = (Entity) entities.get(i);
                if (entity2.canBeCollidedWith() && (entity2 != this.shootingEntity || this.ticksInAir >= 5)
                    && !(entity2 instanceof Elevator)) {
                    if (entity2 instanceof Cephadrome) {
                        Cephadrome c = (Cephadrome) entity2;
                        if (c.riddenByEntity != null) {
                            continue;
                        }
                    }

                    if (entity2 instanceof Dragon) {
                        Dragon c = (Dragon) entity2;
                        if (c.riddenByEntity != null) {
                            continue;
                        }
                    }

                    if (entity2 instanceof EntityHorse) {
                        EntityHorse c = (EntityHorse) entity2;
                        if (c.riddenByEntity != null) {
                            continue;
                        }
                    }

                    float f = 0.3F;
                    AxisAlignedBB box = entity2.boundingBox.expand(f, f, f);
                    MovingObjectPosition hit2 = box.calculateIntercept(vec, vec2);
                    if (hit2 != null) {
                        double d2 = vec.distanceTo(hit2.hitVec);
                        if (d2 < d || d == 0.0) {
                            entity = entity2;
                            d = d2;
                        }
                    }
                }
            }

            if (entity != null) {
                hit = new MovingObjectPosition(entity);
            }

            if (hit != null) {
                if (hit.entityHit != null) {
                    float amount = 100.0F;
                    if (OreSpawnMain.ultimate_sword_pvp == 0) {
                        if (hit.entityHit instanceof EntityPlayer || hit.entityHit instanceof Girlfriend
                            || hit.entityHit instanceof Boyfriend) {
                            EntityLivingBase e = (EntityLivingBase) hit.entityHit;
                            this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                            this.setDead();
                            return;
                        }

                        if (hit.entityHit instanceof EntityTameable) {
                            EntityTameable t = (EntityTameable) hit.entityHit;
                            if (t.isTamed()) {
                                this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                                this.setDead();
                                return;
                            }
                        }
                    }

                    if (this.getIsCritical()) {
                        amount += this.rand.nextInt((int) amount / 2 + 2);
                    }

                    DamageSource source = null;
                    if (this.shootingEntity == null) {
                        source = DamageSource.causeArrowDamage(this, this);
                    } else {
                        source = DamageSource.causeArrowDamage(this, this.shootingEntity);
                    }

                    if (this.isBurning()) {
                        hit.entityHit.setFire(5);
                    }

                    if (hit.entityHit.attackEntityFrom(source, amount)) {
                        if (hit.entityHit instanceof EntityLiving) {
                            EntityLiving entityHit = (EntityLiving) hit.entityHit;
                            if (!this.worldObj.isRemote) {
                                entityHit.setArrowCountInEntity(entityHit.getArrowCountInEntity() + 1);
                            }

                            if (this.knockbackStrength > 0) {
                                float distance = MathHelper
                                    .sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                                if (distance > 0.0F) {
                                    hit.entityHit.addVelocity(
                                        this.motionX * this.knockbackStrength * 0.6F / distance,
                                        0.1,
                                        this.motionZ * this.knockbackStrength * 0.6F / distance);
                                }
                            }

                            if (this.shootingEntity != null && hit.entityHit != this.shootingEntity
                                && hit.entityHit instanceof EntityPlayer
                                && this.shootingEntity instanceof EntityPlayerMP) {
                                ((EntityPlayerMP) this.shootingEntity).playerNetServerHandler
                                    .sendPacket(new S2BPacketChangeGameState(6, 0.0F));
                            }
                        }

                        this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                        this.setDead();
                    } else {
                        this.motionX *= -0.10000000149;
                        this.motionY *= -0.10000000149;
                        this.motionZ *= -0.10000000149;
                        this.rotationYaw += 180.0F;
                        this.prevRotationYaw += 180.0F;
                        this.ticksInAir = 0;
                    }
                } else {
                    this.xTile = hit.blockX;
                    this.yTile = hit.blockY;
                    this.zTile = hit.blockZ;
                    this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
                    this.motionX = (float) (hit.hitVec.xCoord - this.posX);
                    this.motionY = (float) (hit.hitVec.yCoord - this.posY);
                    this.motionZ = (float) (hit.hitVec.zCoord - this.posZ);
                    float distance = MathHelper.sqrt_double(
                        this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX = this.posX - this.motionX / distance * 0.05;
                    this.posY = this.posY - this.motionY / distance * 0.05;
                    this.posZ = this.posZ - this.motionZ / distance * 0.05;
                    this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                    this.inGround = true;
                    this.arrowShake = 7;
                    this.setIsCritical(false);
                }
            }

            if (this.getIsCritical()) {
                for (int i = 0; i < 4; i++) {
                    this.worldObj.spawnParticle(
                        "crit",
                        this.posX + this.motionX * i / 4.0,
                        this.posY + this.motionY * i / 4.0,
                        this.posZ + this.motionZ * i / 4.0,
                        -this.motionX,
                        -this.motionY + 0.2,
                        -this.motionZ);
                }
            }

            this.posX = this.posX + this.motionX;
            this.posY = this.posY + this.motionY;
            this.posZ = this.posZ + this.motionZ;
            float distance = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
            this.rotationPitch = (float) (Math.atan2(this.motionY, distance) * 180.0 / Math.PI);

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
            float motionX = 0.99F;
            float motionY = 0.05F;
            if (this.isInWater()) {
                for (int i = 0; i < 4; i++) {
                    float f = 0.25F;
                    this.worldObj.spawnParticle(
                        "bubble",
                        this.posX - this.motionX * f,
                        this.posY - this.motionY * f,
                        this.posZ - this.motionZ * f,
                        this.motionX,
                        this.motionY,
                        this.motionZ);
                }

                motionX = 0.8F;
            }

            this.motionX *= motionX;
            this.motionY *= motionX;
            this.motionZ *= motionX;
            this.motionY -= motionY;
            this.setPosition(this.posX, this.posY, this.posZ);
            this.func_145775_I();
        }
    }

    public void setKnockbackStrength(int knockbackStrength2) {
        this.knockbackStrength = knockbackStrength2;
    }

    public void setDamage(double d) {}

    public double getDamage() {
        return 100.0;
    }
}
