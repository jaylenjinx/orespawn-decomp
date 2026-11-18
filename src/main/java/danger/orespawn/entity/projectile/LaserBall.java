package danger.orespawn.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.Dragon;
import danger.orespawn.entity.GiantRobot;
import danger.orespawn.entity.Robot2;
import danger.orespawn.entity.Robot3;
import danger.orespawn.entity.Robot4;
import danger.orespawn.entity.Robot5;
import danger.orespawn.entity.SpitBug;
import danger.orespawn.entity.TrooperBug;

public class LaserBall extends EntityThrowable {

    private float my_rotation = 0.0F;
    private int my_index = 81;
    private int is_special = 0;
    private int is_iceball = 0;
    private int is_acid = 0;
    private int is_irukandji = 0;
    private int ticksalive = 0;

    public LaserBall(World world) {
        super(world);
    }

    public LaserBall(World world, int n) {
        super(world);
    }

    public LaserBall(World world, EntityLivingBase living) {
        super(world, living);
    }

    public LaserBall(World world, EntityLivingBase living, int n) {
        super(world, living);
    }

    public LaserBall(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public int getLaserBallIndex() {
        return this.my_index;
    }

    public void setSpecial() {
        this.is_special = 1;
    }

    public void setIceBall() {
        this.is_iceball = 1;
    }

    public void setAcid() {
        this.is_acid = 1;
    }

    public void setIrukandji() {
        this.is_irukandji = 1;
        this.is_acid = 1;
    }

    protected void onImpact(MovingObjectPosition hit) {
        if (hit.entityHit != null) {
            float amount = 16.0F;
            if (this.is_irukandji != 0) {
                hit.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 100.0F);
                this.setDead();
                return;
            }

            if (this.is_acid != 0) {
                if (hit.entityHit instanceof TrooperBug) {
                    this.setDead();
                    return;
                }

                if (hit.entityHit instanceof SpitBug) {
                    this.setDead();
                    return;
                }
            }

            if (this.is_iceball == 0 && this.is_acid == 0) {
                if (hit.entityHit instanceof Robot2) {
                    this.setDead();
                    return;
                }

                if (hit.entityHit instanceof Robot3) {
                    this.setDead();
                    return;
                }

                if (hit.entityHit instanceof Robot4) {
                    this.setDead();
                    return;
                }

                if (hit.entityHit instanceof Robot5) {
                    this.setDead();
                    return;
                }

                if (hit.entityHit instanceof GiantRobot) {
                    this.setDead();
                    return;
                }
            }

            if (hit.entityHit instanceof Dragon && this.is_acid == 0) {
                Dragon d = (Dragon) hit.entityHit;
                if (d.riddenByEntity != null) {
                    this.setDead();
                    return;
                }

                if (d.getDragonType() != 0 && this.is_iceball != 0) {
                    this.setDead();
                    return;
                }
            }

            if (hit.entityHit instanceof EntityPlayer && this.is_acid == 0) {
                EntityPlayer dx = (EntityPlayer) hit.entityHit;
                if (dx.ridingEntity != null) {
                    this.setDead();
                    return;
                }
            }

            hit.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), amount);
            if (this.is_iceball == 0) {
                hit.entityHit.setFire(1);
            }
        } else if (this.is_irukandji != 0 && !this.worldObj.isRemote) {
            this.dropItem(OreSpawnMain.MyIrukandji, 1);
        }

        if (this.is_acid == 0) {
            int mx = 10;
            if (this.is_special != 0) {
                mx = 20;
            }

            for (int i = 0; i < mx; i++) {
                this.worldObj.spawnParticle(
                    "smoke",
                    this.posX + this.rand.nextFloat() - this.rand.nextFloat(),
                    this.posY + this.rand.nextFloat() - this.rand.nextFloat(),
                    this.posZ + this.rand.nextFloat(),
                    0.0,
                    0.0,
                    0.0);
                this.worldObj.spawnParticle(
                    "largesmoke",
                    this.posX + this.rand.nextFloat() - this.rand.nextFloat(),
                    this.posY + this.rand.nextFloat() - this.rand.nextFloat(),
                    this.posZ + this.rand.nextFloat() - this.rand.nextFloat(),
                    0.0,
                    0.0,
                    0.0);
                this.worldObj.spawnParticle(
                    "fireworksSpark",
                    this.posX,
                    this.posY,
                    this.posZ,
                    this.worldObj.rand.nextGaussian(),
                    this.worldObj.rand.nextGaussian(),
                    this.worldObj.rand.nextGaussian());
            }

            this.playSound("random.explode", 0.5F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F);
            if (!this.worldObj.isRemote && (this.is_special != 0 || this.is_iceball != 0)) {
                this.worldObj.createExplosion(
                    this,
                    this.posX,
                    this.posY,
                    this.posZ,
                    3.0F,
                    this.worldObj.getGameRules()
                        .getGameRuleBooleanValue("mobGriefing"));
            }
        }

        this.setDead();
    }

    public void onUpdate() {
        this.ticksalive++;
        if (this.ticksalive > 200) {
            this.setDead();
        } else {
            super.onUpdate();
            this.my_rotation += 50.0F;

            while (this.my_rotation > 360.0F) {
                this.my_rotation -= 360.0F;
            }

            this.rotationPitch = this.prevRotationPitch = this.my_rotation;
            if (this.is_acid == 0) {
                int mx = 4;
                if (this.is_special != 0) {
                    mx = 10;
                }

                if (this.is_iceball != 0 && this.is_special == 0) {
                    mx = 2;
                }

                for (int i = 0; i < mx; i++) {
                    this.worldObj.spawnParticle(
                        "fireworksSpark",
                        this.posX,
                        this.posY,
                        this.posZ,
                        this.worldObj.rand.nextGaussian() / 2.0,
                        this.worldObj.rand.nextGaussian() / 2.0,
                        this.worldObj.rand.nextGaussian() / 2.0);
                    if (this.is_iceball == 0) {
                        this.worldObj.spawnParticle(
                            "reddust",
                            this.posX,
                            this.posY,
                            this.posZ,
                            this.worldObj.rand.nextGaussian() / 10.0,
                            this.worldObj.rand.nextGaussian() / 10.0,
                            this.worldObj.rand.nextGaussian() / 10.0);
                    }
                }
            }
        }
    }
}
