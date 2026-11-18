package danger.orespawn.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class BlockMosquitoPlant extends BlockCrops {

    @SideOnly(Side.CLIENT)
    private IIcon[] field_94364_a;

    public BlockMosquitoPlant(int n) {
        this.setTickRandomly(true);
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        super.updateTick(world, x, y, z, rand);
        if (!world.isRemote) {
            if (OreSpawnMain.MosquitoEnable != 0) {
                int rate = world.getBlockMetadata(x, y, z);
                rate &= 7;
                rate = 7 - rate;
                if (rate <= 1 || OreSpawnMain.OreSpawnRand.nextInt(rate) == 0) {
                    Block bid = world.getBlock(x, y + 1, z);
                    if (bid == Blocks.air) {
                        int howmany = 2 + OreSpawnMain.OreSpawnRand.nextInt(5);

                        for (int i = 0; i < howmany; i++) {
                            spawnCreature(world, "Mosquito", x + 0.5, y + 1.01, z + 0.5);
                        }
                    }
                }
            }
        }
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

    public IIcon getIcon(int side, int meta) {
        if (meta < 7) {
            if (meta >= 6) {
                meta = 4;
            }

            return this.field_94364_a[meta >> 1];
        } else {
            return this.field_94364_a[3];
        }
    }

    public int quantityDropped(Random rand) {
        return 1 + rand.nextInt(5);
    }

    protected Item func_149866_i() {
        return OreSpawnMain.MyMosquitoSeed;
    }

    protected Item func_149865_P() {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.field_94364_a = new IIcon[4];

        for (int i = 0; i < this.field_94364_a.length; i++) {
            this.field_94364_a[i] = iconRegister.registerIcon("OreSpawn:mosquito_" + i);
        }
    }
}
