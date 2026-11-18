package danger.orespawn.entity;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class KingHead extends EntityLiving {

    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;

    public KingHead(World world) {
        super(world);
        this.setSize(19.9F, 10.0F);
        this.noClip = true;
        this.fireResistance = 10000;
        this.isImmuneToFire = true;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(OreSpawnMain.TheKing_stats.health);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(1.33F);
        this.getAttributeMap()
            .registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(0.0);
    }

    protected boolean canDespawn() {
        return false;
    }

    protected void fall(float distance) {}

    protected void updateFallState(double distanceFallen, boolean onGround) {}

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void entityInit() {
        super.entityInit();
    }

    public boolean canBePushed() {
        return true;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = false;
        if (source.getDamageType()
            .equals("inWall")) {
            return false;
        } else {
            Entity e = source.getEntity();
            if (e == null || !(e instanceof TheKing) && !(e instanceof KingHead)) {
                e = source.getSourceOfDamage();
                if (e == null || !(e instanceof TheKing) && !(e instanceof KingHead)) {
                    List entities = this.worldObj
                        .getEntitiesWithinAABB(TheKing.class, this.boundingBox.expand(48.0, 32.0, 48.0));
                    Iterator iterator = entities.iterator();
                    Entity entity = null;
                    TheKing theKing = null;
                    if (iterator.hasNext()) {
                        entity = (Entity) iterator.next();
                        theKing = (TheKing) entity;
                        ret = theKing.attackEntityFrom(source, amount);
                    }

                    return ret;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    public boolean canBeCollidedWith() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch,
        int posRotationIncrements) {
        if (this.riddenByEntity != null) {
            this.boatPosRotationIncrements = posRotationIncrements + 8;
        } else {
            this.boatPosRotationIncrements = 6;
        }

        this.boatX = x;
        this.boatY = y;
        this.boatZ = z;
        this.boatYaw = yaw;
        this.boatPitch = pitch;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }

    @SideOnly(Side.CLIENT)
    public void setVelocity(double vx, double vy, double vz) {
        this.velocityX = this.motionX = vx;
        this.velocityY = this.motionY = vy;
        this.velocityZ = this.motionZ = vz;
    }

    public void onUpdate() {
        if (!this.isDead) {
            this.isAirBorne = true;
            this.setFire(0);
            if (this.worldObj.isRemote) {
                if (this.boatPosRotationIncrements > 0) {
                    double d4 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
                    double d5 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
                    double d11 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
                    this.setPosition(d4, d5, d11);
                    this.rotationPitch = (float) (this.rotationPitch
                        + (this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
                    double d10 = MathHelper.wrapAngleTo180_double(this.boatYaw - this.rotationYaw);
                    if (this.riddenByEntity != null) {
                        d10 = MathHelper
                            .wrapAngleTo180_double((double) this.riddenByEntity.rotationYaw - this.rotationYaw);
                    }

                    this.rotationYaw = (float) (this.rotationYaw + d10 / this.boatPosRotationIncrements);
                    this.setRotation(this.rotationYaw, this.rotationPitch);
                    this.boatPosRotationIncrements--;
                }
            } else {
                List entities = this.worldObj
                    .getEntitiesWithinAABB(TheKing.class, this.boundingBox.expand(32.0, 32.0, 32.0));
                Iterator iterator = entities.iterator();
                Entity entity = null;
                TheKing theKing = null;
                if (iterator.hasNext()) {
                    entity = (Entity) iterator.next();
                    theKing = (TheKing) entity;
                    this.posY = theKing.posY + 12.0;
                    this.posX = theKing.posX - 30.0 * Math.sin(Math.toRadians(theKing.rotationYawHead));
                    this.posZ = theKing.posZ + 30.0 * Math.cos(Math.toRadians(theKing.rotationYawHead));
                    this.rotationYaw = theKing.rotationYaw;
                    this.rotationYawHead = theKing.rotationYawHead;
                    this.motionX = theKing.motionX;
                    this.motionY = theKing.motionY;
                    this.motionZ = theKing.motionZ;
                    this.setHealth(theKing.getHealth());
                } else {
                    this.setDead();
                }
            }
        }
    }
}
