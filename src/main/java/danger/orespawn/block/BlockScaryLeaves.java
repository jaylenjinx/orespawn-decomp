package danger.orespawn.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class BlockScaryLeaves extends BlockLeaves {

    private IIcon scary_solid = null;
    private IIcon generic_solid = null;

    public BlockScaryLeaves(int n) {}

    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(Item.getItemFromBlock(this), 1, 0));
    }

    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance, int fortune) {
        if (!world.isRemote && world.rand.nextInt(25) == 1) {
            if (this == OreSpawnMain.MyCherryLeaves) {
                this.dropBlockAsItem(world, x, y, z, new ItemStack(OreSpawnMain.MyCherry));
            }

            if (this == OreSpawnMain.MyPeachLeaves) {
                this.dropBlockAsItem(world, x, y, z, new ItemStack(OreSpawnMain.MyPeach));
            }
        }
    }

    public int quantityDropped(Random rand) {
        if (this == OreSpawnMain.MyCherryLeaves) {
            return rand.nextInt(4);
        } else {
            return this == OreSpawnMain.MyPeachLeaves ? rand.nextInt(1) : 0;
        }
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        int n = 2;
        int totaldist = 0;
        if (!world.isRemote && world.checkChunksExist(x - n, y - n, z - n, x + n, y + n, z + n)) {
            for (int i = -n; i <= n; i++) {
                for (int j = -n; j <= 0; j++) {
                    for (int k = -n; k <= n; k++) {
                        totaldist = Math.abs(i) + Math.abs(j) + Math.abs(k);
                        if (totaldist <= 3) {
                            Block bid = world.getBlock(x + i, y + j, z + k);
                            if (bid != null && bid.canSustainLeaves(world, x + i, y + j, z + k)) {
                                long t = world.getWorldTime();
                                t %= 24000L;
                                if (this == OreSpawnMain.MyScaryLeaves && t < 12000L) {
                                    OreSpawnMain.setBlockFast(world, x, y, z, OreSpawnMain.MyAppleLeaves, 0, 3);
                                }

                                bid = world.getBlock(x, y - 1, z);
                                if (bid == Blocks.air && world.rand.nextInt(20) == 3) {
                                    this.dropBlockAsItemWithChance(world, x, y - 1, z, 0, 0.0F, 0);
                                }

                                return;
                            }
                        }
                    }
                }
            }

            this.removeLeaves(world, x, y, z);
        }
    }

    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {}

    private void removeLeaves(World world, int x, int y, int z) {
        this.dropBlockAsItem(world, x, y, z, 0, 0);
        world.setBlock(x, y, z, Blocks.air, 0, 2);
    }

    public boolean isOpaqueCube() {
        return OreSpawnMain.FastGraphicsLeaves != 0;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
        Block i1 = blockAccess.getBlock(x, y, z);
        return OreSpawnMain.FastGraphicsLeaves == 0 || i1 != this;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
        this.generic_solid = iconRegister.registerIcon("OreSpawn:generic_solid");
        this.scary_solid = iconRegister.registerIcon("OreSpawn:scary_solid");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (OreSpawnMain.FastGraphicsLeaves != 0) {
            if (this == OreSpawnMain.MyCherryLeaves) {
                return this.generic_solid;
            } else {
                return this == OreSpawnMain.MyPeachLeaves ? this.generic_solid : this.scary_solid;
            }
        } else {
            return this.blockIcon;
        }
    }

    public String[] func_150125_e() {
        return null;
    }
}
