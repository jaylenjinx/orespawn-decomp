package danger.orespawn.dispenser;

import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

import danger.orespawn.entity.projectile.SunspotUrchin;

public final class MyDispenserBehaviorSunspotUrchin extends BehaviorProjectileDispense {

    protected IProjectile getProjectileEntity(World world, IPosition position) {
        return new SunspotUrchin(world, position.getX(), position.getY(), position.getZ());
    }
}
