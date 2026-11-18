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

    public CreeperRepellent(int par1) {
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        int var6 = par1World.getBlockMetadata(par2, par3, par4);
        double var7 = par2 + 0.5F;
        double var9 = par3 + 0.7F;
        double var11 = par4 + 0.5F;
        double var13 = 0.413;
        double var15 = 0.271;
        if (var6 == 1) {
            par1World.spawnParticle("smoke", var7 - var15, var9 + var13, var11, 0.0, 0.0, 0.0);
            par1World.spawnParticle("flame", var7 - var15, var9 + var13, var11, 0.0, 0.0, 0.0);
            par1World.spawnParticle("reddust", var7 - var15, var9 + var13, var11, 0.0, 0.0, 0.0);
        } else if (var6 == 2) {
            par1World.spawnParticle("smoke", var7 + var15, var9 + var13, var11, 0.0, 0.0, 0.0);
            par1World.spawnParticle("flame", var7 + var15, var9 + var13, var11, 0.0, 0.0, 0.0);
            par1World.spawnParticle("reddust", var7 + var15, var9 + var13, var11, 0.0, 0.0, 0.0);
        } else if (var6 == 3) {
            par1World.spawnParticle("smoke", var7, var9 + var13, var11 - var15, 0.0, 0.0, 0.0);
            par1World.spawnParticle("flame", var7, var9 + var13, var11 - var15, 0.0, 0.0, 0.0);
            par1World.spawnParticle("reddust", var7, var9 + var13, var11 - var15, 0.0, 0.0, 0.0);
        } else if (var6 == 4) {
            par1World.spawnParticle("smoke", var7, var9 + var13, var11 + var15, 0.0, 0.0, 0.0);
            par1World.spawnParticle("flame", var7, var9 + var13, var11 + var15, 0.0, 0.0, 0.0);
            par1World.spawnParticle("reddust", var7, var9 + var13, var11 + var15, 0.0, 0.0, 0.0);
        } else {
            par1World.spawnParticle("smoke", var7, var9 + 0.21, var11, 0.0, 0.0, 0.0);
            par1World.spawnParticle("flame", var7, var9 + 0.21, var11, 0.0, 0.0, 0.0);
            par1World.spawnParticle("reddust", var7, var9 + 0.21, var11, 0.0, 0.0, 0.0);
        }
    }

    public int tickRate(World par1World) {
        return 10;
    }

    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        if (!par1World.isRemote) {
            this.findSomethingToRepell(par1World, par2, par3, par4);
            par1World.scheduleBlockUpdate(par2, par3, par4, this, this.tickRate(par1World));
        }
    }

    public void onBlockAdded(World world, int x, int y, int z) {
        world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block blockId) {
        world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
    }

    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        return super.canPlaceBlockAt(par1World, par2, par3, par4);
    }

    private void findSomethingToRepell(World par1World, int par2, int par3, int par4) {
        AxisAlignedBB bb = AxisAlignedBB
            .getBoundingBox(par2 - 20.0, par3 - 10.0, par4 - 20.0, par2 + 20.0, par3 + 10.0, par4 + 20.0);
        List var5 = par1World.getEntitiesWithinAABB(EntityLivingBase.class, bb);
        Iterator var2x = var5.iterator();
        EntityLivingBase var3x = null;

        while (var2x.hasNext()) {
            var3x = (EntityLivingBase) var2x.next();
            if (var3x != null && var3x instanceof EntityCreeper) {
                double d1 = var3x.posX - par2;
                double d2 = var3x.posY - par3;
                double d3 = var3x.posZ - par4;
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
                double d = (float) Math.atan2(var3x.posX - par2, var3x.posZ - par4);
                var3x.motionX = var3x.motionX + f * Math.sin(d);
                var3x.motionZ = var3x.motionZ + f * Math.cos(d);
            }

            if (var3x != null && var3x instanceof EntityAnt) {
                double d1x = var3x.posX - par2;
                double d2x = var3x.posY - par3;
                double d3x = var3x.posZ - par4;
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
                double d = (float) Math.atan2(var3x.posX - par2, var3x.posZ - par4);
                var3x.motionX = var3x.motionX + fx * Math.sin(d);
                var3x.motionZ = var3x.motionZ + fx * Math.cos(d);
            }

            if (var3x != null && var3x instanceof PurplePower) {
                PurplePower p = (PurplePower) var3x;
                if (p.getPurpleType() == 10) {
                    return;
                }

                double d1xx = var3x.posX - par2;
                double d2xx = var3x.posY - par3;
                double d3xx = var3x.posZ - par4;
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
                double d = (float) Math.atan2(var3x.posX - par2, var3x.posZ - par4);
                var3x.motionX = var3x.motionX + fxx * Math.sin(d);
                var3x.motionZ = var3x.motionZ + fxx * Math.cos(d);
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
