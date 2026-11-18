package danger.orespawn.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class AntBlock extends BlockGrass {

    @SideOnly(Side.CLIENT)
    private IIcon[] field_94364_a;

    public AntBlock(int n) {
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (this.field_94364_a == null) {
            return null;
        } else {
            return side == 1 ? this.field_94364_a[0] : (side == 0 ? this.field_94364_a[1] : this.field_94364_a[2]);
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int n) {
        if (this.field_94364_a == null) {
            return null;
        } else if (n == 1) {
            return this.field_94364_a[0];
        } else {
            return n == 0 ? this.field_94364_a[1] : this.field_94364_a[2];
        }
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        int howmany = 0;
        if (!world.isRemote) {
            if (world.isRaining()) {
                return;
            }

            Block bid = world.getBlock(x, y + 1, z);
            if (bid == Blocks.air) {
                howmany = OreSpawnMain.OreSpawnRand.nextInt(6) + 2;

                for (int i = 0; i < howmany; i++) {
                    if (this == OreSpawnMain.MyAntBlock) {
                        if (OreSpawnMain.BlackAntEnable != 0) {
                            spawnCreature(world, "Ant", x + 0.5, y + 1.01, z + 0.5);
                        }
                    } else if (this == OreSpawnMain.MyRedAntBlock) {
                        if (OreSpawnMain.RedAntEnable != 0) {
                            spawnCreature(world, "Red Ant", x + 0.5, y + 1.01, z + 0.5);
                        }
                    } else if (this == OreSpawnMain.MyUnstableAntBlock) {
                        if (OreSpawnMain.UnstableAntEnable != 0) {
                            spawnCreature(world, "Unstable Ant", x + 0.5, y + 1.01, z + 0.5);
                        }
                    } else if (this == OreSpawnMain.TermiteBlock) {
                        if (OreSpawnMain.TermiteEnable != 0) {
                            spawnCreature(world, "Termite", x + 0.5, y + 1.01, z + 0.5);
                        }
                    } else if (OreSpawnMain.RainbowAntEnable != 0) {
                        spawnCreature(world, "Rainbow Ant", x + 0.5, y + 1.01, z + 0.5);
                    }
                }
            }
        }
    }

    public Item getItemDropped(int meta, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
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
    public int getBlockColor() {
        double d = 0.5;
        double d2 = 1.0;
        return ColorizerGrass.getGrassColor(d, d2);
    }

    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        return this.getBlockColor();
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
        int l = 0;
        int i1 = 0;
        int j1 = 0;

        for (int k1 = -1; k1 <= 1; k1++) {
            for (int l1 = -1; l1 <= 1; l1++) {
                int i2 = blockAccess.getBiomeGenForCoords(x + l1, z + k1)
                    .getBiomeGrassColor(x + l1, y, z + k1);
                l += (i2 & 0xFF0000) >> 16;
                i1 += (i2 & 0xFF00) >> 8;
                j1 += i2 & 0xFF;
            }
        }

        return (l / 9 & 0xFF) << 16 | (i1 / 9 & 0xFF) << 8 | j1 / 9 & 0xFF;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.field_94364_a = new IIcon[3];
        this.field_94364_a[0] = iconRegister.registerIcon("OreSpawn:antnest_top");
        this.field_94364_a[1] = iconRegister.registerIcon("OreSpawn:antnest_bottom");
        this.field_94364_a[2] = iconRegister.registerIcon("OreSpawn:antnest_side");
    }
}
