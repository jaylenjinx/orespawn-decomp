package danger.orespawn.entity.projectile;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import danger.orespawn.entity.AttackSquid;
import danger.orespawn.entity.WaterDragon;

public class InkSack extends EntityThrowable {

    private float my_rotation = 0.0F;
    private int my_index = 65;

    public InkSack(World world) {
        super(world);
    }

    public InkSack(World world, int n) {
        super(world);
    }

    public InkSack(World world, EntityLiving living) {
        super(world, living);
    }

    public InkSack(World world, EntityLiving living, int n) {
        super(world, living);
    }

    public InkSack(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public int getInkSackIndex() {
        return this.my_index;
    }

    protected void onImpact(MovingObjectPosition hit) {
        if (hit.entityHit != null) {
            float amount = 1.0F;
            if (hit.entityHit instanceof EntityCreeper) {
                amount = 4.0F;
            }

            if (hit.entityHit instanceof WaterDragon) {
                return;
            }

            if (hit.entityHit instanceof AttackSquid) {
                return;
            }

            hit.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), amount);
            if (hit.entityHit instanceof EntityLivingBase && this.worldObj.rand.nextInt(2) == 0) {
                ((EntityLivingBase) hit.entityHit).addPotionEffect(
                    new PotionEffect(Potion.blindness.id, 100 + 50 * this.worldObj.rand.nextInt(8), 0));
            }
        }

        for (int i = 0; i < 4; i++) {
            this.worldObj.spawnParticle(
                "smoke",
                this.posX + this.rand.nextFloat() - this.rand.nextFloat(),
                this.posY + this.rand.nextFloat() - this.rand.nextFloat(),
                this.posZ + this.rand.nextFloat(),
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
    }
}
