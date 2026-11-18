package danger.orespawn.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class OreUranium extends Block {

    private boolean glowing = false;
    private int glowcount = 0;

    public OreUranium(int n) {
        super(Material.rock);
        this.setHardness(10.0F);
        this.setResistance(1.0F);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setTickRandomly(true);
        this.glowing = false;
    }

    public int tickRate() {
        return 30;
    }

    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
        this.glow(world, x, y, z);
        super.onBlockClicked(world, x, y, z, player);
    }

    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        this.glow(world, x, y, z);
        super.onEntityWalking(world, x, y, z, entity);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
        float hitY, float hitZ) {
        this.glow(world, x, y, z);
        return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
    }

    private void glow(World world, int x, int y, int z) {
        this.glowing = true;
        this.glowcount = 10;
        this.sparkle(world, x, y, z);
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {}

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (this.glowing) {
            this.sparkle(world, x, y, z);
            if (this.glowcount > 0) {
                this.glowcount--;
            } else {
                this.glowing = false;
            }
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
                world.spawnParticle("reddust", x2, y2, z2, 0.0, 0.0, 0.0);
            }
        }
    }

    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance, int fortune) {
        super.dropBlockAsItemWithChance(world, x, y, z, meta, chance, fortune);
        int j1 = 5 + world.rand.nextInt(5) + world.rand.nextInt(10);
        if (y < 40) {
            this.dropXpOnBlockBreak(world, x, y, z, j1);
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
