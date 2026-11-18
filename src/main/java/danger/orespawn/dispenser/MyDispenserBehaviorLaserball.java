package danger.orespawn.dispenser;

import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

import danger.orespawn.entity.projectile.LaserBall;

public final class MyDispenserBehaviorLaserball extends BehaviorProjectileDispense {

    protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition) {
        return new LaserBall(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
    }
}
