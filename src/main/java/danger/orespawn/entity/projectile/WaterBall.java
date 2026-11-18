package danger.orespawn.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.AttackSquid;
import danger.orespawn.entity.Dragon;
import danger.orespawn.entity.WaterDragon;

public class WaterBall extends EntityThrowable {

    private float my_rotation = 0.0F;
    private int my_index = 49;

    public WaterBall(World world) {
        super(world);
    }

    public WaterBall(World world, EntityLivingBase living) {
        super(world, living);
    }

    public WaterBall(World worldObj, double d, double e, double f) {
        super(worldObj, d, e, f);
    }

    public int getWaterBallIndex() {
        return this.my_index;
    }

    protected void onImpact(MovingObjectPosition hit) {
        if (hit.entityHit != null) {
            float amount = 2.0F;
            if (hit.entityHit instanceof EntityCreeper) {
                amount = 5.0F;
            }

            if (hit.entityHit instanceof WaterDragon) {
                return;
            }

            if (hit.entityHit instanceof AttackSquid) {
                return;
            }

            if (hit.entityHit instanceof Dragon) {
                Dragon d = (Dragon) hit.entityHit;
                if (d.getDragonType() != 0) {
                    return;
                }
            }

            if (hit.entityHit instanceof EntityPlayer) {
                EntityPlayer d = (EntityPlayer) hit.entityHit;
                if (d.ridingEntity != null) {
                    return;
                }
            }

            hit.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), amount);
            if (this.worldObj.rand.nextInt(10) == 1) {
                hit.entityHit.dropItem(OreSpawnMain.MyWaterBall, 1);
            }

            hit.entityHit.extinguish();
        }

        for (int i = 0; i < 8; i++) {
            this.worldObj.spawnParticle(
                "bubble",
                this.posX + this.rand.nextFloat() - this.rand.nextFloat(),
                this.posY + this.rand.nextFloat() - this.rand.nextFloat(),
                this.posZ + this.rand.nextFloat(),
                0.0,
                0.0,
                0.0);
            this.worldObj.spawnParticle(
                "splash",
                this.posX + this.rand.nextFloat() - this.rand.nextFloat(),
                this.posY + this.rand.nextFloat() - this.rand.nextFloat(),
                this.posZ + this.rand.nextFloat() - this.rand.nextFloat(),
                0.0,
                0.0,
                0.0);
        }

        this.playSound("random.splash", 0.5F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F);
        if (!this.worldObj.isRemote) {
            this.setDead();
        }
    }

    public void onUpdate() {
        super.onUpdate();
        this.my_rotation += 30.0F;

        while (this.my_rotation > 360.0F) {
            this.my_rotation -= 360.0F;
        }

        this.rotationPitch = this.prevRotationPitch = this.my_rotation;
        this.worldObj.spawnParticle("splash", this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
    }
}
