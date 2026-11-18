package danger.orespawn.dispenser;

import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

import danger.orespawn.entity.projectile.LaserBall;

public final class MyDispenserBehaviorLaserball extends BehaviorProjectileDispense {

    protected IProjectile getProjectileEntity(World world, IPosition position) {
        return new LaserBall(world, position.getX(), position.getY(), position.getZ());
    }
}
