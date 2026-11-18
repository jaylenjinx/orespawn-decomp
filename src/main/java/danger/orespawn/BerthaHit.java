package danger.orespawn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BerthaHit extends EntityThrowable {

    private int hit_type = 0;

    public BerthaHit(World par1World) {
        super(par1World);
    }

    public BerthaHit(World par1World, int par2) {
        super(par1World);
    }

    public BerthaHit(World par1World, EntityLivingBase par2EntityLiving) {
        super(par1World, par2EntityLiving);
        this.setSize(0.33F, 0.33F);
        this.setLocationAndAngles(
            par2EntityLiving.posX,
            par2EntityLiving.posY + par2EntityLiving.getEyeHeight(),
            par2EntityLiving.posZ,
            par2EntityLiving.rotationYaw,
            par2EntityLiving.rotationPitch);
        this.posX = this.posX - MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.posY -= 0.1;
        this.posZ = this.posZ - MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        float f = 0.4F;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)
            * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI)
            * f;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
            * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI)
            * f;
        this.motionY = -MathHelper.sin((this.rotationPitch + this.func_70183_g()) / 180.0F * (float) Math.PI) * f;
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d(), 0.1F);
    }

    public BerthaHit(World par1World, EntityLivingBase par2EntityLiving, int par3) {
        super(par1World, par2EntityLiving);
    }

    public BerthaHit(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }

    public void setHitType(int i) {
        this.hit_type = i;
    }

    protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
        if (!this.isDead) {
            if (par1MovingObjectPosition.entityHit != null && this.getThrower() != null) {
                Entity e = par1MovingObjectPosition.entityHit;
                if (OreSpawnMain.big_bertha_pvp == 0 && e instanceof EntityPlayer || e instanceof Girlfriend
                    || e instanceof Boyfriend) {
                    this.setDead();
                    return;
                }

                if (OreSpawnMain.big_bertha_pvp == 0 && e instanceof EntityTameable) {
                    EntityTameable t = (EntityTameable) e;
                    if (t.isTamed()) {
                        this.setDead();
                        return;
                    }
                }

                if (this.hit_type == 0 && this.getDistanceSqToEntity(this.getThrower()) < 81.0
                    && e != this.getThrower()) {
                    e.attackEntityFrom(
                        DamageSource.causePlayerDamage((EntityPlayer) this.getThrower()),
                        OreSpawnMain.bertha_stats.damage);
                    e.setFire(10);
                    double ks = 2.25;
                    double inair = 0.35;
                    float f3 = (float) Math.atan2(e.posZ - this.getThrower().posZ, e.posX - this.getThrower().posX);
                    if (e.isDead) {
                        inair *= 2.0;
                    }

                    e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                }

                if (this.hit_type == 2 && this.getDistanceSqToEntity(this.getThrower()) < 101.0
                    && e != this.getThrower()) {
                    e.attackEntityFrom(
                        DamageSource.causePlayerDamage((EntityPlayer) this.getThrower()),
                        OreSpawnMain.royal_stats.damage);
                    double ks = 1.5;
                    double inair = 0.25;
                    float f3 = (float) Math.atan2(e.posZ - this.getThrower().posZ, e.posX - this.getThrower().posX);
                    if (e.isDead) {
                        inair *= 2.0;
                    }

                    e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                }

                if (this.hit_type == 3 && this.getDistanceSqToEntity(this.getThrower()) < 64.0
                    && e != this.getThrower()) {
                    e.attackEntityFrom(
                        DamageSource.causePlayerDamage((EntityPlayer) this.getThrower()),
                        OreSpawnMain.hammy_stats.damage);
                    double ks = 1.25;
                    double inair = 0.65;
                    float f3 = (float) Math.atan2(e.posZ - this.getThrower().posZ, e.posX - this.getThrower().posX);
                    if (e.isDead) {
                        inair *= 2.0;
                    }

                    e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                    if (!this.worldObj.isRemote && this.hit_type == 3
                        && this.getDistanceSqToEntity(this.getThrower()) < 64.0) {
                        this.worldObj.newExplosion(
                            (Entity) null,
                            this.posX,
                            this.posY,
                            this.posZ,
                            1.5F,
                            true,
                            this.worldObj.getGameRules()
                                .getGameRuleBooleanValue("mobGriefing"));
                    }
                }
            } else if (!this.worldObj.isRemote && this.hit_type == 3
                && this.getDistanceSqToEntity(this.getThrower()) < 64.0) {
                    this.worldObj.newExplosion(
                        (Entity) null,
                        this.posX,
                        this.posY,
                        this.posZ,
                        2.1F,
                        true,
                        this.worldObj.getGameRules()
                            .getGameRuleBooleanValue("mobGriefing"));
                }

            this.setDead();
        }
    }

    public void onUpdate() {
        super.onUpdate();
    }
}
