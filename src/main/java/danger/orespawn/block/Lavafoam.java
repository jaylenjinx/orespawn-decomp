package danger.orespawn.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Lavafoam extends Block {

    public Lavafoam(int n) {
        super(Material.rock);
        this.setHardness(5.0F);
        this.setResistance(5.0F);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setTickRandomly(true);
        this.slipperiness = 1.1F;
    }

    public int tickRate() {
        return 10;
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
                int which = world.rand.nextInt(10);
                if (which == 1) {
                    world.spawnParticle("smoke", x2, y2, z2, 0.0, 0.0, 0.0);
                }

                if (which == 2) {
                    world.spawnParticle("reddust", x2, y2, z2, 0.0, 0.0, 0.0);
                }
            }
        }
    }

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        double pi = 3.14159;
        double pi2 = pi / 2.0;
        double pi4 = pi / 4.0;
        super.onEntityCollidedWithBlock(world, x, y, z, entity);
        if (entity != null) {
            if (entity instanceof EntityLivingBase) {
                double d = Math.atan2(entity.posX - (x + 0.5F), entity.posZ - (z + 0.5F));
                if (d < 0.0) {
                    d += pi * 2.0;
                }

                if (d > pi2 - pi4 && d < pi2 + pi4) {
                    entity.motionX = 0.45F;
                    entity.motionZ *= 1.35F;
                } else if (d > pi - pi4 && d < pi + pi4) {
                    entity.motionZ = -0.45F;
                    entity.motionX *= 1.35F;
                } else if (d > pi + pi2 - pi4 && d < pi + pi2 + pi4) {
                    entity.motionX = -0.45F;
                    entity.motionZ *= 1.35F;
                } else {
                    entity.motionZ = 0.45F;
                    entity.motionX *= 1.35F;
                }

                d = Math.sqrt(entity.motionZ * entity.motionZ + entity.motionX * entity.motionX);
                if (d > 1.0) {
                    entity.attackEntityFrom(DamageSource.fall, (float) d);
                }
            }
        }
    }

    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance, int fortune) {
        super.dropBlockAsItemWithChance(world, x, y, z, meta, chance, fortune);
        int j1 = 5 + world.rand.nextInt(5) + world.rand.nextInt(5);
        if (world.provider.dimensionId == -1) {
            this.dropXpOnBlockBreak(world, x, y, z, j1);
        }
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        float f = 0.0125F;
        return AxisAlignedBB.getBoundingBox(x + f, y, z + f, x + 1 - f, y + 1, z + 1 - f);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
