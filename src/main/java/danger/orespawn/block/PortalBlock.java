package danger.orespawn.block;

import java.util.Random;

import net.minecraft.block.BlockPortal;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class PortalBlock extends BlockPortal {

    public PortalBlock(int i, int j) {}

    public void updateTick(World world, int x, int y, int z, Random rand) {}

    public boolean tryToCreatePortal(World world, int x, int y, int z) {
        return false;
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, int neighbor) {}

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {}
}
