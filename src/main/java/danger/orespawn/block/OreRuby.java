package danger.orespawn.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class OreRuby extends Block {

    public OreRuby(int i) {
        super(Material.rock);
        this.setHardness(10.0F);
        this.setResistance(4.0F);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance, int fortune) {
        super.dropBlockAsItemWithChance(world, x, y, z, meta, chance, fortune);
        int j1 = 5 + world.rand.nextInt(5) + world.rand.nextInt(5);
        this.dropXpOnBlockBreak(world, x, y, z, j1);
    }

    public Item getItemDropped(int meta, Random rand, int fortune) {
        return OreSpawnMain.MyRuby;
    }

    public int quantityDroppedWithBonus(int fortune, Random rand) {
        return 1 + rand.nextInt(2);
    }

    public int quantityDropped(Random rand) {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
