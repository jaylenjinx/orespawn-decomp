package danger.orespawn.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class BlockExperienceLeaves extends BlockLeaves {

    private IIcon generic_solid = null;

    public BlockExperienceLeaves(int n) {}

    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(Item.getItemFromBlock(this), 1, 0));
    }

    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance, int fortune) {
        if (!world.isRemote) {}
    }

    public int quantityDropped(Random rand) {
        return 0;
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
                                if (t >= 14000L && t <= 22000L) {
                                    if (world.rand.nextInt(65) == 1) {
                                        bid = world.getBlock(x, y + 1, z);
                                        if (bid == Blocks.air) {
                                            this.dropBlockAsItem(
                                                world,
                                                x,
                                                y + 2,
                                                z,
                                                new ItemStack(Items.experience_bottle));
                                        }
                                    }

                                    if (world.rand.nextInt(75) == 1) {
                                        bid = world.getBlock(x, y - 1, z);
                                        if (bid == Blocks.air) {
                                            EntityExpBottle entityExpBottle = new EntityExpBottle(world, x, y - 1, z);
                                            entityExpBottle.setLocationAndAngles(x, y - 1, z, 0.0F, 0.0F);
                                            entityExpBottle.setThrowableHeading(
                                                (world.rand.nextFloat() - world.rand.nextFloat()) / 2.0F,
                                                -0.1F,
                                                (world.rand.nextFloat() - world.rand.nextFloat()) / 2.0F,
                                                0.4F,
                                                5.0F);
                                            world.spawnEntityInWorld(entityExpBottle);
                                        }
                                    }

                                    return;
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

    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        long t = world.getWorldTime();
        t %= 24000L;
        if (t >= 13000L && t <= 23000L) {
            int rate = 0;
            if (t < 14000L) {
                rate = (14000 - (int) t) / 2;
            }

            if (t > 22000L) {
                rate = (int) (t - 22000L) / 2;
            }

            if (world.rand.nextInt(200 + rate) == 1) {
                Block bid = world.getBlock(x, y + 1, z);
                if (bid == Blocks.air) {
                    for (int i = 0; i < 10; i++) {
                        world.spawnParticle(
                            "fireworksSpark",
                            x,
                            y + 1.25,
                            z,
                            world.rand.nextGaussian(),
                            Math.abs(world.rand.nextGaussian()),
                            world.rand.nextGaussian());
                    }
                }
            }

            if (world.rand.nextInt(40 + rate) == 1) {
                Block bid = world.getBlock(x, y - 1, z);
                if (bid == Blocks.air) {
                    for (int i = 0; i < 4; i++) {
                        world.spawnParticle(
                            "fireworksSpark",
                            x,
                            y - 1.25,
                            z,
                            world.rand.nextFloat() - world.rand.nextFloat(),
                            -Math.abs(world.rand.nextFloat()),
                            world.rand.nextFloat() - world.rand.nextFloat());
                    }
                }
            }
        }
    }

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
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return OreSpawnMain.FastGraphicsLeaves != 0 ? this.generic_solid : this.blockIcon;
    }

    public String[] func_150125_e() {
        return null;
    }
}
