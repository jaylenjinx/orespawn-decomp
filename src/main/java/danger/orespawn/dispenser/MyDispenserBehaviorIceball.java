package danger.orespawn.dispenser;

import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

import danger.orespawn.entity.projectile.IceBall;

public final class MyDispenserBehaviorIceball extends BehaviorProjectileDispense {

    protected IProjectile getProjectileEntity(World world, IPosition position) {
        return new IceBall(world, position.getX(), position.getY(), position.getZ());
    }
}
