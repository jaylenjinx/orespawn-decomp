package danger.orespawn.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class IslandBlock extends BlockReed {

    public IslandBlock(int n) {
        float f = 0.375F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return world.getBlock(x, y - 1, z)
            .getMaterial()
            .isSolid();
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
        int isok = 1;
        if (!world.isRemote) {
            int n = 1 + world.rand.nextInt(3);
            int m = 64;
            if (OreSpawnMain.IslandSizeFactor == 2) {
                m = 55;
            }

            if (OreSpawnMain.IslandSizeFactor == 1) {
                m = 45;
            }

            for (int i = 0; i < n; i++) {
                int height = 12 + world.rand.nextInt(m);
                boolean flag = true;

                for (int k = -10; k <= 10; k++) {
                    for (int j = -10; j <= 10; j++) {
                        Block bid = world.getBlock(x + j, y + height, z + k);
                        if (bid != Blocks.air) {
                            flag = false;
                            break;
                        }
                    }
                }

                if (flag) {
                    if (world.rand.nextInt(25) == 1) {
                        spawnCreature(world, "Island", x, y + height, z);
                    } else {
                        spawnCreature(world, "IslandToo", x, y + height, z);
                    }
                }
            }

            world.setBlock(x, y, z, Blocks.air, 0, 2);
            world.setBlock(x, y + 1, z, Blocks.air, 0, 2);
        }
    }

    public Item getItemDropped(int meta, Random rand, int fortune) {
        return Item.getItemFromBlock(OreSpawnMain.MyIslandBlock);
    }

    public int quantityDropped(Random rand) {
        return 1;
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
