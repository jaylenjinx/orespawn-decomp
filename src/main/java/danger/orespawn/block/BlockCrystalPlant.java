package danger.orespawn.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class BlockCrystalPlant extends BlockReed {

    public BlockCrystalPlant(int n) {
        float f = 0.375F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        Block bid = world.getBlock(x, y - 1, z);
        return bid == Blocks.air ? false
            : bid == Blocks.grass || bid == Blocks.dirt || bid == Blocks.farmland || bid == OreSpawnMain.CrystalGrass;
    }

    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (world.rand.nextInt(30) == 1) {
            for (int j1 = 0; j1 < 10; j1++) {
                world.spawnParticle(
                    "happyVillager",
                    x + world.rand.nextFloat(),
                    (double) y + world.rand.nextFloat(),
                    z + world.rand.nextFloat(),
                    0.0,
                    0.0,
                    0.0);
            }
        }
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (!world.isRemote) {
            if (world.rand.nextInt(5) == 1) {
                world.setBlock(x, y, z, Blocks.air, 0, 2);
                if (this == OreSpawnMain.MyCrystalPlant) {
                    this.TallCrystalTree(world, x, y, z);
                }

                if (this == OreSpawnMain.MyCrystalPlant2) {
                    this.ScragglyCrystalTreeWithBranches(world, x, y, z);
                }

                if (this == OreSpawnMain.MyCrystalPlant3) {
                    this.TallCrystalTreeBlue(world, x, y, z);
                }
            }
        }
    }

    public Item getItemDropped(int meta, Random rand, int fortune) {
        if (this == OreSpawnMain.MyCrystalPlant) {
            return Item.getItemFromBlock(OreSpawnMain.MyCrystalPlant);
        } else {
            if (this == OreSpawnMain.MyCrystalPlant2) {
                Item.getItemFromBlock(OreSpawnMain.MyCrystalPlant2);
            }

            return Item.getItemFromBlock(OreSpawnMain.MyCrystalPlant3);
        }
    }

    public int quantityDropped(Random rand) {
        return 1;
    }

    public int idPicked(World world, int x, int y, int z) {
        return 0;
    }

    protected int getSeedItem() {
        return 0;
    }

    protected int getCropItem() {
        return 0;
    }

    public void TallCrystalTree(World world, int x, int y, int z) {
        int i = 10 + world.rand.nextInt(12);
        int j = i + world.rand.nextInt(18);

        for (int k = 0; k < i; k++) {
            Block bid = world.getBlock(x, y + k, z);
            if (k >= 1 && bid != Blocks.air
                && bid != OreSpawnMain.MyCrystalTreeLog
                && bid != OreSpawnMain.MyCrystalLeaves) {
                return;
            }

            OreSpawnMain.setBlockFast(world, x, y + k, z, OreSpawnMain.MyCrystalTreeLog, 0, 2);
        }

        y += i - 1;

        for (int i2 = i; i2 < j; i2++) {
            Block bid = world.getBlock(x, ++y, z);
            if (bid != Blocks.air && bid != OreSpawnMain.MyCrystalTreeLog && bid != OreSpawnMain.MyCrystalLeaves) {
                break;
            }

            OreSpawnMain.setBlockFast(world, x, y, z, OreSpawnMain.MyCrystalTreeLog, 0, 2);
            if (i2 % 4 == 0) {
                for (int m = -1; m < 2; m++) {
                    for (int n = -1; n < 2; n++) {
                        if (world.rand.nextInt(2) == 1) {
                            bid = world.getBlock(x + m, y, z + n);
                            if (bid == Blocks.air) {
                                OreSpawnMain.setBlockFast(world, x + m, y, z + n, OreSpawnMain.MyCrystalLeaves, 0, 2);
                            }
                        }
                    }
                }
            }
        }

        y++;

        for (int m = -1; m < 2; m++) {
            for (int nx = -1; nx < 2; nx++) {
                if (world.rand.nextInt(2) == 1) {
                    Block bidx = world.getBlock(x + m, y, z + nx);
                    if (bidx == Blocks.air) {
                        OreSpawnMain.setBlockFast(world, x + m, y, z + nx, OreSpawnMain.MyCrystalTreeLog, 0, 2);
                    }
                }
            }
        }

        for (int i2 = -3; i2 < 4; i2++) {
            for (int nxx = -3; nxx < 4; nxx++) {
                Block bidx = world.getBlock(x + i2, y, z + nxx);
                if (bidx == Blocks.air) {
                    OreSpawnMain.setBlockFast(world, x + i2, y, z + nxx, OreSpawnMain.MyCrystalLeaves, 0, 2);
                }
            }
        }

        y++;

        for (int i2 = -1; i2 < 2; i2++) {
            for (int nxxx = -1; nxxx < 2; nxxx++) {
                Block bidx = world.getBlock(x + i2, y, z + nxxx);
                if (bidx == Blocks.air) {
                    OreSpawnMain.setBlockFast(world, x + i2, y, z + nxxx, OreSpawnMain.MyCrystalLeaves, 0, 2);
                }
            }
        }
    }

    public void makeScragglyCrystalBranch(World world, int x, int y, int z, int len, int biasx, int biasz) {
        for (int k = 0; k < len; k++) {
            int ix = world.rand.nextInt(2) - world.rand.nextInt(2) + biasx;
            int iz = world.rand.nextInt(2) - world.rand.nextInt(2) + biasz;
            if (ix > 1) {
                ix = 1;
            }

            if (ix < -1) {
                ix = -1;
            }

            if (iz > 1) {
                iz = 1;
            }

            if (iz < -1) {
                iz = -1;
            }

            int iy = world.rand.nextInt(3) > 0 ? 1 : 0;
            x += ix;
            z += iz;
            y += iy;
            Block bid = world.getBlock(x, y, z);
            if (bid != Blocks.air && bid != OreSpawnMain.MyCrystalTreeLog && bid != OreSpawnMain.MyCrystalLeaves2) {
                return;
            }

            OreSpawnMain.setBlockFast(world, x, y, z, OreSpawnMain.MyCrystalTreeLog, 0, 2);

            for (int m = -1; m < 2; m++) {
                for (int n = -1; n < 2; n++) {
                    if (world.rand.nextInt(2) == 1) {
                        bid = world.getBlock(x + m, y, z + n);
                        if (bid == Blocks.air) {
                            OreSpawnMain.setBlockFast(world, x + m, y, z + n, OreSpawnMain.MyCrystalLeaves2, 0, 2);
                        }
                    }
                }
            }

            if (world.rand.nextInt(2) == 1) {
                bid = world.getBlock(x, y + 1, z);
                if (bid == Blocks.air) {
                    OreSpawnMain.setBlockFast(world, x, y + 1, z, OreSpawnMain.MyCrystalLeaves2, 0, 2);
                }
            }
        }
    }

    public void ScragglyCrystalTreeWithBranches(World world, int x, int y, int z) {
        int i = 1 + world.rand.nextInt(2);
        int j = i + world.rand.nextInt(8);

        for (int k = 0; k < i; k++) {
            Block bid = world.getBlock(x, y + k, z);
            if (k >= 1 && bid != Blocks.air
                && bid != OreSpawnMain.MyCrystalTreeLog
                && bid != OreSpawnMain.MyCrystalLeaves2) {
                return;
            }

            OreSpawnMain.setBlockFast(world, x, y + k, z, OreSpawnMain.MyCrystalTreeLog, 0, 2);
        }

        y += i - 1;

        for (int i2 = i; i2 < j; i2++) {
            int ix = world.rand.nextInt(2) - world.rand.nextInt(2);
            int iz = world.rand.nextInt(2) - world.rand.nextInt(2);
            int iy = world.rand.nextInt(4) > 0 ? 1 : 0;
            x += ix;
            z += iz;
            y += iy;
            Block bid = world.getBlock(x, y, z);
            if (bid != Blocks.air && bid != OreSpawnMain.MyCrystalTreeLog && bid != OreSpawnMain.MyCrystalLeaves2) {
                break;
            }

            OreSpawnMain.setBlockFast(world, x, y, z, OreSpawnMain.MyCrystalTreeLog, 0, 2);
            if (world.rand.nextInt(4) == 1) {
                this.makeScragglyCrystalBranch(
                    world,
                    x,
                    y,
                    z,
                    world.rand.nextInt(1 + j - i2),
                    world.rand.nextInt(2) - world.rand.nextInt(2),
                    world.rand.nextInt(2) - world.rand.nextInt(2));
            }

            for (int m = -1; m < 2; m++) {
                for (int n = -1; n < 2; n++) {
                    if (world.rand.nextInt(2) == 1) {
                        bid = world.getBlock(x + m, y, z + n);
                        if (bid == Blocks.air) {
                            OreSpawnMain.setBlockFast(world, x + m, y, z + n, OreSpawnMain.MyCrystalLeaves2, 0, 2);
                        }
                    }
                }
            }

            if (world.rand.nextInt(2) == 1) {
                bid = world.getBlock(x, y + 1, z);
                if (bid == Blocks.air) {
                    OreSpawnMain.setBlockFast(world, x, y + 1, z, OreSpawnMain.MyCrystalLeaves2, 0, 2);
                }
            }
        }
    }

    public void TallCrystalTreeBlue(World world, int x, int y, int z) {
        int i = 5 + world.rand.nextInt(6);
        int j = 2 + i + world.rand.nextInt(12);

        for (int k = 0; k < i; k++) {
            Block bid = world.getBlock(x, y + k, z);
            if (k >= 1 && bid != Blocks.air
                && bid != OreSpawnMain.MyCrystalTreeLog
                && bid != OreSpawnMain.MyCrystalLeaves3) {
                return;
            }

            OreSpawnMain.setBlockFast(world, x, y + k, z, OreSpawnMain.MyCrystalTreeLog, 0, 2);
        }

        y += i - 1;

        for (int i2 = i; i2 < j; i2++) {
            Block bid = world.getBlock(x, ++y, z);
            if (bid != Blocks.air && bid != OreSpawnMain.MyCrystalTreeLog && bid != OreSpawnMain.MyCrystalLeaves3) {
                break;
            }

            OreSpawnMain.setBlockFast(world, x, y, z, OreSpawnMain.MyCrystalTreeLog, 0, 2);
            if (i2 % 3 == 0) {
                for (int m = -1; m < 2; m++) {
                    for (int n = -1; n < 2; n++) {
                        if (world.rand.nextInt(2) == 1) {
                            bid = world.getBlock(x + m, y, z + n);
                            if (bid == Blocks.air) {
                                OreSpawnMain.setBlockFast(world, x + m, y, z + n, OreSpawnMain.MyCrystalLeaves3, 0, 2);
                            }
                        }
                    }
                }
            }
        }

        y++;

        for (int m = -1; m < 2; m++) {
            for (int nx = -1; nx < 2; nx++) {
                if (world.rand.nextInt(2) == 1) {
                    Block bidx = world.getBlock(x + m, y, z + nx);
                    if (bidx == Blocks.air) {
                        OreSpawnMain.setBlockFast(world, x + m, y, z + nx, OreSpawnMain.MyCrystalTreeLog, 0, 2);
                    }
                }
            }
        }

        for (int i2 = -3; i2 < 4; i2++) {
            for (int nxx = -3; nxx < 4; nxx++) {
                Block bidx = world.getBlock(x + i2, y, z + nxx);
                if (bidx == Blocks.air) {
                    OreSpawnMain.setBlockFast(world, x + i2, y, z + nxx, OreSpawnMain.MyCrystalLeaves3, 0, 2);
                }
            }
        }

        y++;

        for (int i2 = -1; i2 < 2; i2++) {
            for (int nxxx = -1; nxxx < 2; nxxx++) {
                Block bidx = world.getBlock(x + i2, y, z + nxxx);
                if (bidx == Blocks.air) {
                    OreSpawnMain.setBlockFast(world, x + i2, y, z + nxxx, OreSpawnMain.MyCrystalLeaves3, 0, 2);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
