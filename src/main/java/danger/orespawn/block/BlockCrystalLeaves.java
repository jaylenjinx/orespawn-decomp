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

public class BlockCrystalLeaves extends BlockLeaves {

    public BlockCrystalLeaves(int n) {
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(Item.getItemFromBlock(this), 1, 0));
    }

    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance, int fortune) {
        if (!world.isRemote) {
            if (world.rand.nextInt(100) == 1) {
                this.dropBlockAsItem(world, x, y, z, new ItemStack(OreSpawnMain.MyCrystalApple));
            }

            if (world.rand.nextInt(50) == 1) {
                if (this == OreSpawnMain.MyCrystalLeaves) {
                    this.dropBlockAsItem(world, x, y, z, new ItemStack(OreSpawnMain.MyCrystalPlant));
                }

                if (this == OreSpawnMain.MyCrystalLeaves2) {
                    this.dropBlockAsItem(world, x, y, z, new ItemStack(OreSpawnMain.MyCrystalPlant2));
                }

                if (this == OreSpawnMain.MyCrystalLeaves3) {
                    this.dropBlockAsItem(world, x, y, z, new ItemStack(OreSpawnMain.MyCrystalPlant3));
                }
            }
        }
    }

    public int quantityDropped(Random rand) {
        return 1;
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        int n = 2;
        int totaldist = 0;
        int chance = 20;
        if (world.provider.dimensionId == OreSpawnMain.DimensionID4) {
            chance = 100;
            n = 1;
        }

        if (!world.isRemote && world.checkChunksExist(x - n, y - n, z - n, x + n, y + n, z + n)) {
            for (int i = -n; i <= n; i++) {
                for (int j = -n; j <= 0; j++) {
                    for (int k = -n; k <= n; k++) {
                        totaldist = Math.abs(i) + Math.abs(j) + Math.abs(k);
                        if (totaldist <= 3) {
                            Block bid = world.getBlock(x + i, y + j, z + k);
                            if (bid != null && bid.canSustainLeaves(world, x + i, y + j, z + k)) {
                                bid = world.getBlock(x, y - 1, z);
                                if (bid == Blocks.air && world.rand.nextInt(chance) == 3) {
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

    private void removeLeaves(World world, int x, int y, int z) {
        this.dropBlockAsItem(world, x, y, z, 0, 0);
        world.setBlock(x, y, z, Blocks.air, 0, 2);
    }

    public boolean isOpaqueCube() {
        return OreSpawnMain.FastGraphicsLeaves != 0;
    }

    public boolean renderAsNormalBlock() {
        return OreSpawnMain.FastGraphicsLeaves != 0;
    }

    public int getRenderType() {
        return OreSpawnMain.FastGraphicsLeaves != 0 ? super.getRenderType() : 1;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
        Block i1 = blockAccess.getBlock(x, y, z);
        return OreSpawnMain.FastGraphicsLeaves == 0 || i1 != this;
    }

    @SideOnly(Side.CLIENT)
    public int getBlockColor() {
        return 14540253;
    }

    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        return 14540253;
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
        return 14540253;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.blockIcon;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }

    public String[] func_150125_e() {
        return null;
    }
}
