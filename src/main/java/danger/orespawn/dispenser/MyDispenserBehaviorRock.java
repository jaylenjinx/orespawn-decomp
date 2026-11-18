package danger.orespawn.dispenser;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.projectile.EntityThrownRock;

public final class MyDispenserBehaviorRock extends BehaviorProjectileDispense {

    public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
        World world = source.getWorld();
        IPosition iposition = BlockDispenser.func_149939_a(source);
        EnumFacing enumfacing = BlockDispenser.func_149937_b(source.getBlockMetadata());
        IProjectile iprojectile = this.getProjectileEntity(world, iposition);
        iprojectile.setThrowableHeading(
            enumfacing.getFrontOffsetX(),
            enumfacing.getFrontOffsetY() + 0.1F,
            enumfacing.getFrontOffsetZ(),
            this.func_82500_b(),
            this.func_82498_a());
        EntityThrownRock r = (EntityThrownRock) iprojectile;
        if (stack.getItem() == OreSpawnMain.MySmallRock) {
            r.setRockType(1);
        }

        if (stack.getItem() == OreSpawnMain.MyRock) {
            r.setRockType(2);
        }

        if (stack.getItem() == OreSpawnMain.MyRedRock) {
            r.setRockType(3);
        }

        if (stack.getItem() == OreSpawnMain.MyGreenRock) {
            r.setRockType(4);
        }

        if (stack.getItem() == OreSpawnMain.MyBlueRock) {
            r.setRockType(5);
        }

        if (stack.getItem() == OreSpawnMain.MyPurpleRock) {
            r.setRockType(6);
        }

        if (stack.getItem() == OreSpawnMain.MySpikeyRock) {
            r.setRockType(7);
        }

        if (stack.getItem() == OreSpawnMain.MyTNTRock) {
            r.setRockType(8);
        }

        if (stack.getItem() == OreSpawnMain.MyCrystalRedRock) {
            r.setRockType(9);
        }

        if (stack.getItem() == OreSpawnMain.MyCrystalGreenRock) {
            r.setRockType(10);
        }

        if (stack.getItem() == OreSpawnMain.MyCrystalBlueRock) {
            r.setRockType(11);
        }

        if (stack.getItem() == OreSpawnMain.MyCrystalTNTRock) {
            r.setRockType(12);
        }

        world.spawnEntityInWorld((Entity) iprojectile);
        stack.splitStack(1);
        return stack;
    }

    protected IProjectile getProjectileEntity(World world, IPosition position) {
        return new EntityThrownRock(world, position.getX(), position.getY(), position.getZ());
    }
}
