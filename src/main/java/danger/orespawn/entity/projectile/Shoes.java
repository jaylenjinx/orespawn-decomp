package danger.orespawn.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.Boyfriend;
import danger.orespawn.entity.Girlfriend;

public class Shoes extends EntityThrowable {

    public int ShoeId = 0;
    private float my_rotation = 0.0F;

    public Shoes(World world) {
        super(world);
        this.ShoeId = this.rand.nextInt(4) + 2;
        this.dataWatcher.addObject(20, this.ShoeId);
    }

    public Shoes(World world, int n) {
        super(world);
        this.ShoeId = n;
        this.dataWatcher.addObject(20, this.ShoeId);
    }

    public Shoes(World world, EntityLivingBase living) {
        super(world, living);
        this.ShoeId = this.rand.nextInt(4) + 2;
        this.dataWatcher.addObject(20, this.ShoeId);
    }

    public Shoes(World world, EntityLivingBase living, int n) {
        super(world, living);
        this.ShoeId = n;
        this.dataWatcher.addObject(20, this.ShoeId);
    }

    public Shoes(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.ShoeId = this.rand.nextInt(4) + 2;
        this.dataWatcher.addObject(20, this.ShoeId);
    }

    public int getShoeId() {
        return this.dataWatcher.getWatchableObjectInt(20);
    }

    protected void onImpact(MovingObjectPosition hit) {
        if (hit.entityHit != null) {
            float amount = 2.0F;
            if (this.getShoeId() == 6) {
                amount = 6.0F;
            }

            if (hit.entityHit instanceof EntityCreeper) {
                amount += 4.0F;
            }

            if (hit.entityHit instanceof Girlfriend) {
                amount = 1.0F;
            }

            if (hit.entityHit instanceof Boyfriend) {
                amount = 1.0F;
            }

            if (hit.entityHit instanceof EntityPlayer) {
                amount = 0.0F;
            }

            if (OreSpawnMain.valentines_day != 0) {
                amount = 10.0F;
            }

            hit.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), amount);
        }

        for (int i = 0; i < 4; i++) {
            this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
            this.worldObj.spawnParticle("reddust", this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
        }

        if (!this.worldObj.isRemote) {
            this.setDead();
        }
    }

    public void onUpdate() {
        super.onUpdate();
        this.my_rotation += 20.0F;

        while (this.my_rotation > 360.0F) {
            this.my_rotation -= 360.0F;
        }

        this.rotationPitch = this.prevRotationPitch = this.my_rotation;
    }
}
