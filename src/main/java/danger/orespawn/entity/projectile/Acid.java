package danger.orespawn.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class Acid extends LaserBall {

    private int my_index = 85;

    public Acid(World world) {
        super(world);
        super.setAcid();
    }

    public Acid(World world, int n) {
        super(world);
        super.setAcid();
    }

    public Acid(World world, EntityLivingBase living) {
        super(world, living);
        super.setAcid();
    }

    public Acid(World world, EntityLivingBase living, int n) {
        super(world, living);
        super.setAcid();
    }

    public Acid(World world, double x, double y, double z) {
        super(world, x, y, z);
        super.setAcid();
    }

    public int getAcidIndex() {
        return this.my_index;
    }
}
