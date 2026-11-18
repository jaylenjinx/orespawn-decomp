package danger.orespawn.dispenser;

import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

import danger.orespawn.entity.projectile.IrukandjiArrow;

public final class MyDispenserBehaviorArrow extends BehaviorProjectileDispense {

    protected IProjectile getProjectileEntity(World world, IPosition position) {
        IrukandjiArrow entityarrow = new IrukandjiArrow(world, position.getX(), position.getY(), position.getZ());
        entityarrow.canBePickedUp = 1;
        return entityarrow;
    }
}
