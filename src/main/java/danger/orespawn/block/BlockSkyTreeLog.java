package danger.orespawn.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class BlockSkyTreeLog extends Block {

    public BlockSkyTreeLog(int n, int n2) {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(Item.getItemFromBlock(this), 1, 0));
    }

    protected ItemStack createStackedBlock(int n) {
        return new ItemStack(Item.getItemFromBlock(this), 1, 0);
    }

    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public boolean isWood(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public Item getItemDropped(int meta, Random rand, int fortune) {
        return Item.getItemFromBlock(OreSpawnMain.MySkyTreeLog);
    }

    public void breakRecursor(World world, int x, int y, int z, int xf, int yf, int zf, int recursion) {
        int n = 1;
        if (recursion <= 1000) {
            for (int i = -n; i <= n; i++) {
                for (int j = -n; j <= n; j++) {
                    for (int k = -n; k <= n; k++) {
                        if ((i != 0 || j != 0 || k != 0) && (x + i != xf || y + j != yf || z + k != zf)
                            && (recursion <= 0 || x + i < xf - n
                                || x + i > xf + n
                                || y + j < yf - n
                                || y + j > yf + n
                                || z + k < zf - n
                                || z + k > zf + n)) {
                            Block block = world.getBlock(x + i, y + j, z + k);
                            if (block == this) {
                                world.setBlock(x + i, y + j, z + k, Blocks.air, 0, 2);
                                this.dropBlockAsItem(world, x + i, y + j, z + k, 0, 0);
                                this.breakRecursor(world, x + i, y + j, z + k, x, y, z, recursion + 1);
                            }
                        }
                    }
                }
            }
        }
    }

    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
        world.setBlock(x, y, z, Blocks.air, 0, 2);
        this.breakRecursor(world, x, y, z, x, y, z, 0);
        this.dropBlockAsItem(world, x, y, z, 0, 0);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
