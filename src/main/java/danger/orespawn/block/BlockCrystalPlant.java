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

    public BlockCrystalPlant(int par1) {
        float var3 = 0.375F;
        this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 1.0F, 0.5F + var3);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        Block bid = par1World.getBlock(par2, par3 - 1, par4);
        return bid == Blocks.air ? false
            : bid == Blocks.grass || bid == Blocks.dirt || bid == Blocks.farmland || bid == OreSpawnMain.CrystalGrass;
    }

    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        if (par1World.rand.nextInt(30) == 1) {
            for (int j1 = 0; j1 < 10; j1++) {
                par1World.spawnParticle(
                    "happyVillager",
                    par2 + par1World.rand.nextFloat(),
                    (double) par3 + par1World.rand.nextFloat(),
                    par4 + par1World.rand.nextFloat(),
                    0.0,
                    0.0,
                    0.0);
            }
        }
    }

    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        if (!par1World.isRemote) {
            if (par1World.rand.nextInt(5) == 1) {
                par1World.setBlock(par2, par3, par4, Blocks.air, 0, 2);
                if (this == OreSpawnMain.MyCrystalPlant) {
                    this.TallCrystalTree(par1World, par2, par3, par4);
                }

                if (this == OreSpawnMain.MyCrystalPlant2) {
                    this.ScragglyCrystalTreeWithBranches(par1World, par2, par3, par4);
                }

                if (this == OreSpawnMain.MyCrystalPlant3) {
                    this.TallCrystalTreeBlue(par1World, par2, par3, par4);
                }
            }
        }
    }

    public Item getItemDropped(int par1, Random par2Random, int par3) {
        if (this == OreSpawnMain.MyCrystalPlant) {
            return Item.getItemFromBlock(OreSpawnMain.MyCrystalPlant);
        } else {
            if (this == OreSpawnMain.MyCrystalPlant2) {
                Item.getItemFromBlock(OreSpawnMain.MyCrystalPlant2);
            }

            return Item.getItemFromBlock(OreSpawnMain.MyCrystalPlant3);
        }
    }

    public int quantityDropped(Random par1Random) {
        return 1;
    }

    public int idPicked(World par1World, int par2, int par3, int par4) {
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

        for (int var14 = i; var14 < j; var14++) {
            Block bid = world.getBlock(x, ++y, z);
            if (bid != Blocks.air && bid != OreSpawnMain.MyCrystalTreeLog && bid != OreSpawnMain.MyCrystalLeaves) {
                break;
            }

            OreSpawnMain.setBlockFast(world, x, y, z, OreSpawnMain.MyCrystalTreeLog, 0, 2);
            if (var14 % 4 == 0) {
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

        for (int var16 = -3; var16 < 4; var16++) {
            for (int nxx = -3; nxx < 4; nxx++) {
                Block bidx = world.getBlock(x + var16, y, z + nxx);
                if (bidx == Blocks.air) {
                    OreSpawnMain.setBlockFast(world, x + var16, y, z + nxx, OreSpawnMain.MyCrystalLeaves, 0, 2);
                }
            }
        }

        y++;

        for (int var17 = -1; var17 < 2; var17++) {
            for (int nxxx = -1; nxxx < 2; nxxx++) {
                Block bidx = world.getBlock(x + var17, y, z + nxxx);
                if (bidx == Blocks.air) {
                    OreSpawnMain.setBlockFast(world, x + var17, y, z + nxxx, OreSpawnMain.MyCrystalLeaves, 0, 2);
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

        for (int var15 = i; var15 < j; var15++) {
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
                    world.rand.nextInt(1 + j - var15),
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

        for (int var14 = i; var14 < j; var14++) {
            Block bid = world.getBlock(x, ++y, z);
            if (bid != Blocks.air && bid != OreSpawnMain.MyCrystalTreeLog && bid != OreSpawnMain.MyCrystalLeaves3) {
                break;
            }

            OreSpawnMain.setBlockFast(world, x, y, z, OreSpawnMain.MyCrystalTreeLog, 0, 2);
            if (var14 % 3 == 0) {
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

        for (int var16 = -3; var16 < 4; var16++) {
            for (int nxx = -3; nxx < 4; nxx++) {
                Block bidx = world.getBlock(x + var16, y, z + nxx);
                if (bidx == Blocks.air) {
                    OreSpawnMain.setBlockFast(world, x + var16, y, z + nxx, OreSpawnMain.MyCrystalLeaves3, 0, 2);
                }
            }
        }

        y++;

        for (int var17 = -1; var17 < 2; var17++) {
            for (int nxxx = -1; nxxx < 2; nxxx++) {
                Block bidx = world.getBlock(x + var17, y, z + nxxx);
                if (bidx == Blocks.air) {
                    OreSpawnMain.setBlockFast(world, x + var17, y, z + nxxx, OreSpawnMain.MyCrystalLeaves3, 0, 2);
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
