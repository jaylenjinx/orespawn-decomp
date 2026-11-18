package danger.orespawn.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class ItemAppleSeed extends Item {

    public ItemAppleSeed(int i) {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World world, int x, int y, int z,
        int par7, float par8, float par9, float par10) {
        if (!world.isRemote) {
            Block bid = world.getBlock(x, y, z);
            if (bid != Blocks.grass && bid != Blocks.dirt && bid != Blocks.farmland) {
                return false;
            }

            if (this == OreSpawnMain.MyAppleSeed) {
                this.makeTree(world, x, y, z, OreSpawnMain.MyAppleLeaves, null);
            } else if (this == OreSpawnMain.MyCherrySeed) {
                this.makeTree(world, x, y, z, OreSpawnMain.MyCherryLeaves, null);
            } else {
                this.makeTree(world, x, y, z, OreSpawnMain.MyPeachLeaves, null);
            }
        }

        if (!par2EntityPlayer.capabilities.isCreativeMode) {
            par1ItemStack.stackSize--;
        }

        return true;
    }

    public void makeTree(World world, int x, int y, int z, Block blkid, Chunk chunk) {
        Block bid = world.getBlock(x, y, z);
        if (bid == Blocks.grass || bid == Blocks.dirt || bid == Blocks.farmland) {
            int h1 = 12;
            int h2 = 6;
            int h3 = 9;
            int h4 = 6;
            int h5 = 14;
            int w1 = 5;
            int w2 = 3;
            if (blkid == OreSpawnMain.MyPeachLeaves) {
                h1 = 10;
                h2 = 5;
                h3 = 7;
                h4 = 5;
                h5 = 12;
                w1 = 4;
                w2 = 2;
            }

            if (blkid == OreSpawnMain.MyCherryLeaves) {
                h1 = 8;
                h2 = 3;
                h3 = 5;
                h4 = 3;
                h5 = 10;
                w1 = 3;
                w2 = 1;
            }

            for (int j = 1; j < h1; j++) {
                world.setBlock(x, y + j, z, Blocks.log, 0, 2);
            }

            for (int var19 = 1; var19 < w1; var19++) {
                OreSpawnMain.setBlockSuperFast(world, x + var19, y + h2, z, Blocks.log, 0, 2, chunk);
            }

            for (int var20 = 1; var20 < w1; var20++) {
                OreSpawnMain.setBlockSuperFast(world, x - var20, y + h2, z, Blocks.log, 0, 2, chunk);
            }

            for (int var21 = 1; var21 < w1; var21++) {
                OreSpawnMain.setBlockSuperFast(world, x, y + h2, z + var21, Blocks.log, 0, 2, chunk);
            }

            for (int var22 = 1; var22 < w1; var22++) {
                OreSpawnMain.setBlockSuperFast(world, x, y + h2, z - var22, Blocks.log, 0, 2, chunk);
            }

            for (int var23 = 1; var23 < w2; var23++) {
                OreSpawnMain.setBlockSuperFast(world, x + var23, y + h3, z, Blocks.log, 0, 2, chunk);
            }

            for (int var24 = 1; var24 < w2; var24++) {
                OreSpawnMain.setBlockSuperFast(world, x - var24, y + h3, z, Blocks.log, 0, 2, chunk);
            }

            for (int var25 = 1; var25 < w2; var25++) {
                OreSpawnMain.setBlockSuperFast(world, x, y + h3, z + var25, Blocks.log, 0, 2, chunk);
            }

            for (int var26 = 1; var26 < w2; var26++) {
                OreSpawnMain.setBlockSuperFast(world, x, y + h3, z - var26, Blocks.log, 0, 2, chunk);
            }

            for (int i = h4; i < h5; i++) {
                int width = 6;
                if (i > 8) {
                    width = 5;
                }

                if (i > 10) {
                    width = 4;
                }

                if (blkid != OreSpawnMain.MyAppleLeaves) {
                    width--;
                }

                for (int var27 = -width; var27 <= width; var27++) {
                    for (int k = -width; k <= width; k++) {
                        bid = world.getBlock(x + k, y + i, z + var27);
                        if (bid == Blocks.air) {
                            OreSpawnMain.setBlockSuperFast(world, x + k, y + i, z + var27, blkid, 0, 2, chunk);
                        }
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
