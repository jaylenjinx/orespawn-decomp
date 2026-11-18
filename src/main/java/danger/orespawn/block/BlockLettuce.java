package danger.orespawn.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class BlockLettuce extends BlockReed {

    public BlockLettuce(int n) {
        float f = 0.375F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
        this.setTickRandomly(true);
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        Block bid = world.getBlock(x, y - 1, z);
        return bid == Blocks.air ? false
            : bid == OreSpawnMain.MyLettucePlant1 || bid == OreSpawnMain.MyLettucePlant2
                || bid == OreSpawnMain.MyLettucePlant3
                || bid == OreSpawnMain.MyLettucePlant4
                || bid == Blocks.grass
                || bid == Blocks.dirt
                || bid == Blocks.farmland;
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        int dontGrow = 0;
        if (!world.isRemote) {
            int meta = world.getBlockMetadata(x, y, z);
            meta &= 255;
            if (meta >= 4) {
                Block bid = world.getBlock(x, y, z);
                if (bid == OreSpawnMain.MyLettucePlant1) {
                    world.setBlock(x, y, z, OreSpawnMain.MyLettucePlant2, 0, 2);
                } else if (bid == OreSpawnMain.MyLettucePlant2) {
                    world.setBlock(x, y, z, OreSpawnMain.MyLettucePlant3, 0, 2);
                } else if (bid == OreSpawnMain.MyLettucePlant3) {
                    world.setBlock(x, y, z, OreSpawnMain.MyLettucePlant4, 0, 2);
                }
            } else {
                Block bid = world.getBlock(x, y, z);
                world.setBlock(x, y, z, bid, meta + 1, 2);
            }
        }
    }

    public Item getItemDropped(int meta, Random rand, int fortune) {
        return OreSpawnMain.MyLettuce;
    }

    public int quantityDropped(Random rand) {
        return this == OreSpawnMain.MyLettucePlant4 ? 2 + rand.nextInt(3) : 0;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
