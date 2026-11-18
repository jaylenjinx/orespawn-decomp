package danger.orespawn.block;

import java.util.Random;

import net.minecraft.block.BlockTorch;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class BlockExtremeTorch extends BlockTorch {

    public BlockExtremeTorch(int n) {
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        int meta = world.getBlockMetadata(x, y, z);
        double x2 = x + 0.5F;
        double y2 = y + 0.7F;
        double z2 = z + 0.5F;
        double d = 0.213;
        double d2 = 0.271;
        if (meta == 1) {
            world.spawnParticle("smoke", x2 - d2, y2 + d, z2, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", x2 - d2, y2 + d, z2, 0.0, 0.0, 0.0);
            world.spawnParticle("reddust", x2 - d2, y2 + d, z2, 0.0, 0.0, 0.0);
        } else if (meta == 2) {
            world.spawnParticle("smoke", x2 + d2, y2 + d, z2, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", x2 + d2, y2 + d, z2, 0.0, 0.0, 0.0);
            world.spawnParticle("reddust", x2 + d2, y2 + d, z2, 0.0, 0.0, 0.0);
        } else if (meta == 3) {
            world.spawnParticle("smoke", x2, y2 + d, z2 - d2, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", x2, y2 + d, z2 - d2, 0.0, 0.0, 0.0);
            world.spawnParticle("reddust", x2, y2 + d, z2 - d2, 0.0, 0.0, 0.0);
        } else if (meta == 4) {
            world.spawnParticle("smoke", x2, y2 + d, z2 + d2, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", x2, y2 + d, z2 + d2, 0.0, 0.0, 0.0);
            world.spawnParticle("reddust", x2, y2 + d, z2 + d2, 0.0, 0.0, 0.0);
        } else {
            world.spawnParticle("smoke", x2, y2, z2, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", x2, y2, z2, 0.0, 0.0, 0.0);
            world.spawnParticle("reddust", x2, y2, z2, 0.0, 0.0, 0.0);
        }

        this.onBlockPlacedBy(world, x, y, z, null, null);
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return super.canPlaceBlockAt(world, x, y, z);
    }

    public void onBlockPlacedBy(World world, int x2, int y2, int z2, EntityLivingBase placer, ItemStack stack) {
        int x = x2;
        int y = y2;
        int z = z2;
        int found = 0;
        if (world.getBlock(x2, y2 - 1, z2) == OreSpawnMain.MyEyeOfEnderBlock) {
            for (int tries = 0; tries < 100 && found == 0; tries++) {
                if (world.rand.nextInt(2) == 0) {
                    x = x2 + 4 + world.rand.nextInt(3) - world.rand.nextInt(3);
                } else {
                    x = x2 - 4 + world.rand.nextInt(3) - world.rand.nextInt(3);
                }

                if (world.rand.nextInt(2) == 0) {
                    z = z2 + 4 + world.rand.nextInt(3) - world.rand.nextInt(3);
                } else {
                    z = z2 - 4 + world.rand.nextInt(3) - world.rand.nextInt(3);
                }

                for (y = y2 - 2; y <= y2 + 2; y++) {
                    if (world.getBlock(x, y - 1, z)
                        .getMaterial()
                        .isSolid() && world.getBlock(x, y, z) == Blocks.air
                        && world.getBlock(x, y + 1, z) == Blocks.air) {
                        found = 1;
                        break;
                    }
                }
            }

            if (found != 0) {
                if (!world.isRemote) {
                    Entity ent = null;
                    ent = spawnCreature(world, "Cephadrome", x + 0.5, y + 0.01, z + 0.5);
                } else {
                    for (int i = 0; i < 16; i++) {
                        world.spawnParticle(
                            "smoke",
                            x2 + world.rand.nextFloat() - world.rand.nextFloat(),
                            y2 + world.rand.nextFloat(),
                            z2 + world.rand.nextFloat() - world.rand.nextFloat(),
                            0.0,
                            0.0,
                            0.0);
                        world.spawnParticle(
                            "explode",
                            x2 + world.rand.nextFloat() - world.rand.nextFloat(),
                            y2 + world.rand.nextFloat(),
                            z2 + world.rand.nextFloat() - world.rand.nextFloat(),
                            0.0,
                            0.0,
                            0.0);
                        world.spawnParticle(
                            "reddust",
                            x2 + world.rand.nextFloat() - world.rand.nextFloat(),
                            y2 + world.rand.nextFloat(),
                            z2 + world.rand.nextFloat() - world.rand.nextFloat(),
                            0.0,
                            0.0,
                            0.0);
                    }
                }

                if (placer != null) {
                    placer.worldObj
                        .playSoundAtEntity(placer, "random.explode", 1.0F, world.rand.nextFloat() * 0.2F + 0.9F);
                } else {
                    world.playSound(x2, y2, z2, "random.explode", 1.0F, world.rand.nextFloat() * 0.2F + 0.9F, false);
                }

                world.setBlock(x2, y2, z2, Blocks.air);
            }
        }

        super.onBlockPlacedBy(world, x2, y2, z2, placer, stack);
    }

    public static Entity spawnCreature(World world, String str, double x, double y, double z) {
        Entity entity = null;
        entity = EntityList.createEntityByName(str, world);
        if (entity != null) {
            entity.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0F, 0.0F);
            world.spawnEntityInWorld(entity);
            ((EntityLiving) entity).playLivingSound();
        }

        return entity;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
