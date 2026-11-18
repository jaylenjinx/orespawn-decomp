package danger.orespawn.block;

import java.util.Random;

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
import danger.orespawn.entity.TheKing;

public class KingSpawnerBlock extends BlockReed {

    public KingSpawnerBlock(int n) {
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
        if (!world.isRemote) {
            this.updateTick(world, x, y, z, rand);
        } else if (world.rand.nextInt(20) == 1) {
            for (int j1 = 0; j1 < 20; j1++) {
                world.spawnParticle(
                    "fireworksSpark",
                    x + world.rand.nextFloat(),
                    (double) y + world.rand.nextFloat(),
                    z + world.rand.nextFloat(),
                    0.0,
                    0.0,
                    0.0);
            }
        }
    }

    public void onBlockAdded(World world, int x, int y, int z) {
        if (!world.isRemote) {
            world.scheduleBlockUpdate(x, y, z, this, 100);
        }
    }

    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
        this.updateTick(world, x, y, z, null);
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (!world.isRemote) {
            if (OreSpawnMain.TheKingEnable != 0) {
                spawnTheKing(world, x, y + 8, z);
            }

            world.setBlock(x, y, z, Blocks.air, 0, 2);
            world.setBlock(x, y + 1, z, Blocks.air, 0, 2);
        }
    }

    public Item getItemDropped(int meta, Random rand, int fortune) {
        return Item.getItemFromBlock(OreSpawnMain.MyKingSpawnerBlock);
    }

    public int quantityDropped(Random rand) {
        return 1;
    }

    public static Entity spawnTheKing(World world, double x, double y, double z) {
        Entity entity = null;
        entity = EntityList.createEntityByName("The King", world);
        if (entity != null) {
            entity.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0F, 0.0F);
            world.spawnEntityInWorld(entity);
            ((EntityLiving) entity).playLivingSound();
            ((TheKing) entity).setGuardMode(1);
        }

        return entity;
    }

    public boolean canBlockStay(World world, int x, int y, int z) {
        this.updateTick(world, x, y, z, null);
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
