package danger.orespawn.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class BlockCrystalTorch extends BlockTorch {

    public BlockCrystalTorch(int n) {
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (world.rand.nextInt(4) == 1) {
            int meta = world.getBlockMetadata(x, y, z);
            double x2 = x + 0.5F;
            double y2 = y + 0.7F;
            double z2 = z + 0.5F;
            double d = 0.213;
            double d2 = 0.271;
            if (meta == 1) {
                world.spawnParticle(
                    "fireworksSpark",
                    x2 - d2,
                    y2 + d,
                    z2,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 8.0F,
                    world.rand.nextFloat() / 8.0F,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 8.0F);
                world.spawnParticle(
                    "flame",
                    x2 - d2,
                    y2 + d,
                    z2,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 60.0F,
                    world.rand.nextFloat() / 10.0F,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 60.0F);
            } else if (meta == 2) {
                world.spawnParticle(
                    "fireworksSpark",
                    x2 + d2,
                    y2 + d,
                    z2,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 8.0F,
                    world.rand.nextFloat() / 8.0F,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 8.0F);
                world.spawnParticle(
                    "flame",
                    x2 + d2,
                    y2 + d,
                    z2,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 60.0F,
                    world.rand.nextFloat() / 10.0F,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 60.0F);
            } else if (meta == 3) {
                world.spawnParticle(
                    "fireworksSpark",
                    x2,
                    y2 + d,
                    z2 - d2,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 8.0F,
                    world.rand.nextFloat() / 8.0F,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 8.0F);
                world.spawnParticle(
                    "flame",
                    x2,
                    y2 + d,
                    z2 - d2,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 60.0F,
                    world.rand.nextFloat() / 10.0F,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 60.0F);
            } else if (meta == 4) {
                world.spawnParticle(
                    "fireworksSpark",
                    x2,
                    y2 + d,
                    z2 + d2,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 8.0F,
                    world.rand.nextFloat() / 8.0F,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 8.0F);
                world.spawnParticle(
                    "flame",
                    x2,
                    y2 + d,
                    z2 + d2,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 60.0F,
                    world.rand.nextFloat() / 10.0F,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 60.0F);
            } else {
                world.spawnParticle(
                    "fireworksSpark",
                    x2,
                    y2,
                    z2,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 8.0F,
                    world.rand.nextFloat() / 8.0F,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 8.0F);
                world.spawnParticle(
                    "flame",
                    x2,
                    y2,
                    z2,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 60.0F,
                    world.rand.nextFloat() / 10.0F,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 60.0F);
            }
        }
    }

    private boolean isCrystalBlock(World world, int x, int y, int z) {
        Block l = world.getBlock(x, y, z);
        if (l == OreSpawnMain.CrystalStone) {
            return true;
        } else if (l == OreSpawnMain.CrystalGrass) {
            return true;
        } else {
            return l == OreSpawnMain.MyCrystalTreeLog ? true : l == OreSpawnMain.CrystalPlanksBlock;
        }
    }

    private boolean isItSolidOnSide(World world, int x, int y, int z, ForgeDirection dir, boolean tf) {
        return this.isCrystalBlock(world, x, y, z) ? true : world.isSideSolid(x, y, z, dir, tf);
    }

    private boolean canPlaceTorchOn(World world, int x, int y, int z) {
        Block l = world.getBlock(x, y, z);
        if (this.isCrystalBlock(world, x, y, z)) {
            return true;
        } else {
            return World.doesBlockHaveSolidTopSurface(world, x, y, z) ? true
                : l != null && l.canPlaceTorchOnTop(world, x, y, z);
        }
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return this.isItSolidOnSide(world, x - 1, y, z, ForgeDirection.EAST, true)
            || this.isItSolidOnSide(world, x + 1, y, z, ForgeDirection.WEST, true)
            || this.isItSolidOnSide(world, x, y, z - 1, ForgeDirection.SOUTH, true)
            || this.isItSolidOnSide(world, x, y, z + 1, ForgeDirection.NORTH, true)
            || this.canPlaceTorchOn(world, x, y - 1, z);
    }

    public int onBlockPlaced(World world, int x, int y, int z, int n, float f, float f2, float f3, int n2) {
        int j1 = n2;
        if (n == 1 && this.canPlaceTorchOn(world, x, y - 1, z)) {
            j1 = 5;
        }

        if (n == 2 && (world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH, true)
            || this.isCrystalBlock(world, x, y, z + 1))) {
            j1 = 4;
        }

        if (n == 3 && (world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH, true)
            || this.isCrystalBlock(world, x, y, z - 1))) {
            j1 = 3;
        }

        if (n == 4
            && (world.isSideSolid(x + 1, y, z, ForgeDirection.WEST, true) || this.isCrystalBlock(world, x + 1, y, z))) {
            j1 = 2;
        }

        if (n == 5
            && (world.isSideSolid(x - 1, y, z, ForgeDirection.EAST, true) || this.isCrystalBlock(world, x - 1, y, z))) {
            j1 = 1;
        }

        return j1;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
