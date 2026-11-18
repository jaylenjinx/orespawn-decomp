package danger.orespawn.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class OreCrystalCrystal extends Block {

    public OreCrystalCrystal(int n, float lv, float f1, float f2) {
        super(Material.rock);
        this.setHardness(f1);
        this.setResistance(f2);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setLightLevel(lv);
        this.setTickRandomly(true);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (world.rand.nextInt(20) == 0) {
            this.sparkle(world, x, y, z);
        }
    }

    private void sparkle(World world, int x, int y, int z) {
        int which = 0;
        float dx = 0.5F;
        float dz = 0.5F;
        float dy = 0.5F;
        if (this == OreSpawnMain.TigersEye) {
            world.spawnParticle(
                "flame",
                x + dx,
                (double) y + dy,
                z + dz,
                (world.rand.nextFloat() - world.rand.nextFloat()) / 4.0F,
                (world.rand.nextFloat() - world.rand.nextFloat()) / 4.0F,
                (world.rand.nextFloat() - world.rand.nextFloat()) / 4.0F);
        } else {
            world.spawnParticle(
                "fireworksSpark",
                x + dx,
                (double) y + dy,
                z + dz,
                (world.rand.nextFloat() - world.rand.nextFloat()) / 4.0F,
                (world.rand.nextFloat() - world.rand.nextFloat()) / 4.0F,
                (world.rand.nextFloat() - world.rand.nextFloat()) / 4.0F);
        }
    }

    public int getRenderType() {
        return 1;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
        if (this == OreSpawnMain.CrystalCrystal && !world.isRemote && world.rand.nextInt(10) == 1) {
            world.newExplosion(
                (Entity) null,
                x + 0.5F,
                y + 0.5F,
                z + 0.5F,
                1.0F,
                true,
                world.getGameRules()
                    .getGameRuleBooleanValue("mobGriefing"));
        }

        super.onBlockDestroyedByPlayer(world, x, y, z, meta);
    }

    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance, int fortune) {
        super.dropBlockAsItemWithChance(world, x, y, z, meta, chance, fortune);
        int j1 = 5 + world.rand.nextInt(5) + world.rand.nextInt(10);
        if (y < 40) {
            this.dropXpOnBlockBreak(world, x, y, z, j1);
        }
    }

    public int quantityDropped(Random rand) {
        return this != OreSpawnMain.TigersEye ? 1 : rand.nextInt(2);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
