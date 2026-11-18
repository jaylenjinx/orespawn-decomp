package danger.orespawn.block;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.entity.EntityAnt;
import danger.orespawn.entity.PurplePower;

public class CreeperRepellent extends BlockTorch {

    public CreeperRepellent(int n) {
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        int meta = world.getBlockMetadata(x, y, z);
        double x2 = x + 0.5F;
        double d = y + 0.7F;
        double z2 = z + 0.5F;
        double d2 = 0.413;
        double d3 = 0.271;
        if (meta == 1) {
            world.spawnParticle("smoke", x2 - d3, d + d2, z2, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", x2 - d3, d + d2, z2, 0.0, 0.0, 0.0);
            world.spawnParticle("reddust", x2 - d3, d + d2, z2, 0.0, 0.0, 0.0);
        } else if (meta == 2) {
            world.spawnParticle("smoke", x2 + d3, d + d2, z2, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", x2 + d3, d + d2, z2, 0.0, 0.0, 0.0);
            world.spawnParticle("reddust", x2 + d3, d + d2, z2, 0.0, 0.0, 0.0);
        } else if (meta == 3) {
            world.spawnParticle("smoke", x2, d + d2, z2 - d3, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", x2, d + d2, z2 - d3, 0.0, 0.0, 0.0);
            world.spawnParticle("reddust", x2, d + d2, z2 - d3, 0.0, 0.0, 0.0);
        } else if (meta == 4) {
            world.spawnParticle("smoke", x2, d + d2, z2 + d3, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", x2, d + d2, z2 + d3, 0.0, 0.0, 0.0);
            world.spawnParticle("reddust", x2, d + d2, z2 + d3, 0.0, 0.0, 0.0);
        } else {
            world.spawnParticle("smoke", x2, d + 0.21, z2, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", x2, d + 0.21, z2, 0.0, 0.0, 0.0);
            world.spawnParticle("reddust", x2, d + 0.21, z2, 0.0, 0.0, 0.0);
        }
    }

    public int tickRate(World world) {
        return 10;
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (!world.isRemote) {
            this.findSomethingToRepell(world, x, y, z);
            world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
        }
    }

    public void onBlockAdded(World world, int x, int y, int z) {
        world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block blockId) {
        world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return super.canPlaceBlockAt(world, x, y, z);
    }

    private void findSomethingToRepell(World world, int x, int y, int z) {
        AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(x - 20.0, y - 10.0, z - 20.0, x + 20.0, y + 10.0, z + 20.0);
        List entities = world.getEntitiesWithinAABB(EntityLivingBase.class, bb);
        Iterator iterator = entities.iterator();
        EntityLivingBase living = null;

        while (iterator.hasNext()) {
            living = (EntityLivingBase) iterator.next();
            if (living != null && living instanceof EntityCreeper) {
                double d1 = living.posX - x;
                double d2 = living.posY - y;
                double d3 = living.posZ - z;
                double f = d1 * d1 + d2 * d2 + d3 * d3;
                f = Math.sqrt(f);
                f = 20.0 - f;
                if (f > 20.0) {
                    f = 20.0;
                }

                if (f < 0.0) {
                    f = 0.0;
                }

                f *= 0.4;
                double d = (float) Math.atan2(living.posX - x, living.posZ - z);
                living.motionX = living.motionX + f * Math.sin(d);
                living.motionZ = living.motionZ + f * Math.cos(d);
            }

            if (living != null && living instanceof EntityAnt) {
                double d1x = living.posX - x;
                double d2x = living.posY - y;
                double d3x = living.posZ - z;
                double fx = d1x * d1x + d2x * d2x + d3x * d3x;
                fx = Math.sqrt(fx);
                fx = 20.0 - fx;
                if (fx > 20.0) {
                    fx = 20.0;
                }

                if (fx < 0.0) {
                    fx = 0.0;
                }

                fx *= 0.4;
                double d = (float) Math.atan2(living.posX - x, living.posZ - z);
                living.motionX = living.motionX + fx * Math.sin(d);
                living.motionZ = living.motionZ + fx * Math.cos(d);
            }

            if (living != null && living instanceof PurplePower) {
                PurplePower p = (PurplePower) living;
                if (p.getPurpleType() == 10) {
                    return;
                }

                double d1xx = living.posX - x;
                double d2xx = living.posY - y;
                double d3xx = living.posZ - z;
                double fxx = d1xx * d1xx + d2xx * d2xx + d3xx * d3xx;
                fxx = Math.sqrt(fxx);
                fxx = 20.0 - fxx;
                if (fxx > 20.0) {
                    fxx = 20.0;
                }

                if (fxx < 0.0) {
                    fxx = 0.0;
                }

                fxx *= 0.4;
                double d = (float) Math.atan2(living.posX - x, living.posZ - z);
                living.motionX = living.motionX + fxx * Math.sin(d);
                living.motionZ = living.motionZ + fxx * Math.cos(d);
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
