package danger.orespawn.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import danger.orespawn.MyUtils;

public class IceBall extends LaserBall {

    private int my_index = 84;
    private int icemaker = 0;

    public IceBall(World world) {
        super(world);
        super.setIceBall();
    }

    public IceBall(World world, int n) {
        super(world);
        super.setIceBall();
    }

    public IceBall(World world, EntityLivingBase living) {
        super(world, living);
        super.setIceBall();
    }

    public IceBall(World world, EntityLivingBase living, int n) {
        super(world, living);
        super.setIceBall();
    }

    public IceBall(World world, double x, double y, double z) {
        super(world, x, y, z);
        super.setIceBall();
    }

    public int getIceBallIndex() {
        return this.my_index;
    }

    public void setIceMaker(int i) {
        this.icemaker = i;
    }

    @Override
    protected void onImpact(MovingObjectPosition hit) {
        if (hit.entityHit != null && MyUtils.isRoyalty(hit.entityHit)) {
            this.setDead();
        } else {
            super.onImpact(hit);
            if (this.icemaker != 0) {
                for (int i = 0; i < 5; i++) {
                    int x = this.worldObj.rand.nextInt(4);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        x = -x;
                    }

                    int y = this.worldObj.rand.nextInt(4);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        y = -y;
                    }

                    int z = this.worldObj.rand.nextInt(4);
                    if (this.worldObj.rand.nextInt(2) == 1) {
                        z = -z;
                    }

                    x = (int) (x + hit.hitVec.xCoord);
                    y = (int) (y + hit.hitVec.yCoord);
                    z = (int) (z + hit.hitVec.zCoord);
                    this.worldObj.setBlock(x, y, z, Blocks.ice);
                }
            }

            this.setDead();
        }
    }
}
