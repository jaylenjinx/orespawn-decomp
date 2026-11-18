package danger.orespawn.dispenser;

import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

import danger.orespawn.entity.projectile.Acid;

public final class MyDispenserBehaviorAcid extends BehaviorProjectileDispense {

    protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition) {
        return new Acid(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
    }
}
