package danger.orespawn.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class DeadIrukandji extends LaserBall {

    private int my_index = 86;

    public DeadIrukandji(World world) {
        super(world);
        super.setIrukandji();
    }

    public DeadIrukandji(World world, int n) {
        super(world);
        super.setIrukandji();
    }

    public DeadIrukandji(World world, EntityLivingBase living) {
        super(world, living);
        super.setIrukandji();
    }

    public DeadIrukandji(World world, EntityLivingBase living, int n) {
        super(world, living);
        super.setIrukandji();
    }

    public DeadIrukandji(World world, double x, double y, double z) {
        super(world, x, y, z);
        super.setIrukandji();
    }

    public int getIrukandjiIndex() {
        return this.my_index;
    }
}
