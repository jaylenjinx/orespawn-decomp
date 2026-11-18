package danger.orespawn.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import danger.orespawn.MyUtils;

public class ThunderBolt extends EntityThrowable {

    public ThunderBolt(World world) {
        super(world);
    }

    public ThunderBolt(World world, EntityLivingBase player) {
        super(world, player);
    }

    public ThunderBolt(World world, EntityLivingBase living, int n) {
        super(world, living);
    }

    public ThunderBolt(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    protected void onImpact(MovingObjectPosition hit) {
        if (hit.entityHit != null) {
            float f = 40.0F;
            if (MyUtils.isRoyalty(hit.entityHit)) {
                this.setDead();
                return;
            }

            hit.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), f / 2.0F);
            hit.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.getThrower()), f / 2.0F);
            hit.entityHit.setFire(1);
        }

        int mx = 20;

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
        if (!this.worldObj.isRemote) {
            this.worldObj.createExplosion(
                this,
                this.posX,
                this.posY,
                this.posZ,
                3.0F,
                this.worldObj.getGameRules()
                    .getGameRuleBooleanValue("mobGriefing"));
        }

        this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, this.posX, this.posY + 1.0, this.posZ));
        this.setDead();
    }

    public void onUpdate() {
        super.onUpdate();
        int mx = 4;

        for (int i = 0; i < mx; i++) {
            this.worldObj.spawnParticle(
                "fireworksSpark",
                this.posX,
                this.posY,
                this.posZ,
                this.worldObj.rand.nextGaussian() / 10.0,
                this.worldObj.rand.nextGaussian() / 10.0,
                this.worldObj.rand.nextGaussian() / 10.0);
        }
    }
}
