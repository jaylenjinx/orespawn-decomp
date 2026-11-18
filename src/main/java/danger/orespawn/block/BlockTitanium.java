package danger.orespawn.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTitanium extends Block {

    public BlockTitanium(int n) {
        super(Material.rock);
        this.setHardness(5.0F);
        this.setResistance(5.0F);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setLightLevel(0.5F);
    }

    public int tickRate() {
        return 100;
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (world.rand.nextInt(20) == 0) {
            this.sparkle(world, x, y, z);
        }
    }

    private void sparkle(World world, int x, int y, int z) {
        Random rand = world.rand;
        double d = 0.0625;

        for (int i = 0; i < 6; i++) {
            double x2 = x + rand.nextFloat();
            double y2 = y + rand.nextFloat();
            double z2 = z + rand.nextFloat();
            if (i == 0 && !world.getBlock(x, y + 1, z)
                .isOpaqueCube()) {
                y2 = y + 1 + d;
            }

            if (i == 1 && !world.getBlock(x, y - 1, z)
                .isOpaqueCube()) {
                y2 = y + 0 - d;
            }

            if (i == 2 && !world.getBlock(x, y, z + 1)
                .isOpaqueCube()) {
                z2 = z + 1 + d;
            }

            if (i == 3 && !world.getBlock(x, y, z - 1)
                .isOpaqueCube()) {
                z2 = z + 0 - d;
            }

            if (i == 4 && !world.getBlock(x + 1, y, z)
                .isOpaqueCube()) {
                x2 = x + 1 + d;
            }

            if (i == 5 && !world.getBlock(x - 1, y, z)
                .isOpaqueCube()) {
                x2 = x + 0 - d;
            }

            if (x2 < x || x2 > x + 1 || y2 < 0.0 || y2 > y + 1 || z2 < z || z2 > z + 1) {
                int which = world.rand.nextInt(3);
                if (which == 0) {
                    world.spawnParticle("flame", x2, y2, z2, 0.0, 0.0, 0.0);
                }

                if (which == 1) {
                    world.spawnParticle("smoke", x2, y2, z2, 0.0, 0.0, 0.0);
                }

                if (which == 2) {
                    world.spawnParticle("reddust", x2, y2, z2, 0.0, 0.0, 0.0);
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
