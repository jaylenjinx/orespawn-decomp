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

public class BlockExperiencePlant extends BlockReed {

    public BlockExperiencePlant(int n) {
        float f = 0.375F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
        this.setTickRandomly(true);
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        Block bid = world.getBlock(x, y - 1, z);
        return bid == Blocks.air ? false : bid == Blocks.grass || bid == Blocks.dirt || bid == Blocks.farmland;
    }

    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (world.rand.nextInt(20) == 1) {
            for (int j1 = 0; j1 < 20; j1++) {
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
            if (world.rand.nextInt(10) == 1) {
                world.setBlock(x, y, z, Blocks.air, 0, 2);
                OreSpawnMain.OreSpawnTrees.ExperienceTree(world, x, y - 1, z);
            }
        }
    }

    public Item getItemDropped(int meta, Random rand, int fortune) {
        return Item.getItemFromBlock(OreSpawnMain.MyExperiencePlant);
    }

    public int quantityDropped(Random rand) {
        return 1;
    }

    public int idPicked(World world, int x, int y, int z) {
        return 0;
    }

    protected Item getSeedItem() {
        return OreSpawnMain.MyExperienceTreeSeed;
    }

    protected int getCropItem() {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
