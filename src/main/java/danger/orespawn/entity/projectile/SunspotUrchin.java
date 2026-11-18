package danger.orespawn.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class SunspotUrchin extends EntityThrowable {

    private float my_rotation = 0.0F;
    private int my_index = 50;

    public SunspotUrchin(World world) {
        super(world);
    }

    public SunspotUrchin(World world, int n) {
        super(world);
    }

    public SunspotUrchin(World world, EntityLivingBase living) {
        super(world, living);
    }

    public SunspotUrchin(World world, EntityLivingBase living, int n) {
        super(world, living);
    }

    public SunspotUrchin(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public int getUrchinIndex() {
        return this.my_index;
    }

    protected void onImpact(MovingObjectPosition hit) {
        if (hit.entityHit != null) {
            float amount = 3.0F;
            if (hit.entityHit instanceof EntityCreeper) {
                amount = 6.0F;
            }

            if (!(hit.entityHit instanceof EntityPlayer)) {
                hit.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), amount);
                if (!hit.entityHit.isImmuneToFire()) {
                    hit.entityHit.setFire(5);
                }
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

        for (int i = 0; i < 5; i++) {
            this.worldObj.spawnParticle(
                "smoke",
                this.posX,
                this.posY,
                this.posZ,
                this.worldObj.rand.nextFloat(),
                this.worldObj.rand.nextFloat(),
                this.worldObj.rand.nextFloat());
            this.worldObj.spawnParticle(
                "reddust",
                this.posX,
                this.posY,
                this.posZ,
                this.worldObj.rand.nextFloat(),
                this.worldObj.rand.nextFloat(),
                this.worldObj.rand.nextFloat());
        }

        if (!this.worldObj.isRemote) {
            this.setDead();
        }
    }

    public void onUpdate() {
        super.onUpdate();
        this.setFire(1);
        this.my_rotation += 30.0F;

        while (this.my_rotation > 360.0F) {
            this.my_rotation -= 360.0F;
        }

        this.rotationPitch = this.prevRotationPitch = this.my_rotation;
        this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
    }
}
