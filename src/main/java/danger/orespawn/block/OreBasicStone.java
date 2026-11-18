package danger.orespawn.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class OreBasicStone extends Block {

    public OreBasicStone(int n, float f1, float f2) {
        super(Material.rock);
        this.setHardness(f1);
        this.setResistance(f2);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setTickRandomly(false);
    }

    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
        if (!world.isRemote && this == OreSpawnMain.CrystalRat) {
            int num = 1 + world.rand.nextInt(10);

            for (int i = 0; i < num; i++) {
                spawnCreature(
                    world,
                    0,
                    "Rat",
                    x + 0.5 + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2,
                    y + 0.01,
                    z + 0.5 + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2);
            }
        }

        if (!world.isRemote && this == OreSpawnMain.CrystalFairy) {
            int num = 1 + world.rand.nextInt(6);

            for (int i = 0; i < num; i++) {
                spawnCreature(
                    world,
                    0,
                    "Fairy",
                    x + 0.5 + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2,
                    y + 0.01,
                    z + 0.5 + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2);
            }
        }

        if (!world.isRemote && this == OreSpawnMain.RedAntTroll) {
            int num = 15 + world.rand.nextInt(6);

            for (int i = 0; i < num; i++) {
                spawnCreature(
                    world,
                    0,
                    "Red Ant",
                    x + 0.5 + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2,
                    y + 0.01,
                    z + 0.5 + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2);
            }
        }

        if (!world.isRemote && this == OreSpawnMain.TermiteTroll) {
            int num = 15 + world.rand.nextInt(6);

            for (int i = 0; i < num; i++) {
                spawnCreature(
                    world,
                    0,
                    "Termite",
                    x + 0.5 + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2,
                    y + 0.01,
                    z + 0.5 + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2);
            }
        }

        super.onBlockDestroyedByPlayer(world, x, y, z, meta);
    }

    public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
        return true;
    }

    public boolean isOpaqueCube() {
        return OreSpawnMain.current_dimension == OreSpawnMain.DimensionID5;
    }

    public boolean renderAsNormalBlock() {
        return OreSpawnMain.current_dimension == OreSpawnMain.DimensionID5;
    }

    public static Entity spawnCreature(World world, int n, String name, double x, double y, double z) {
        Entity entity = null;
        if (name == null) {
            entity = EntityList.createEntityByID(n, world);
        } else {
            entity = EntityList.createEntityByName(name, world);
        }

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
