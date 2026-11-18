package danger.orespawn.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class CrystalGrass extends Block {

    @SideOnly(Side.CLIENT)
    private IIcon[] field_94364_a;

    public CrystalGrass(int n, float f1, float f2) {
        super(Material.grass);
        this.setHardness(f1);
        this.setResistance(f2);
        this.setTickRandomly(false);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
        return true;
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
    public IIcon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int n) {
        if (this.field_94364_a == null) {
            return null;
        } else if (n == 1) {
            return this.field_94364_a[0];
        } else {
            return n == 0 ? this.field_94364_a[1] : this.field_94364_a[2];
        }
    }

    public Item getItemDropped(int meta, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction,
        IPlantable plant) {
        return true;
    }

    public boolean isOpaqueCube() {
        return OreSpawnMain.current_dimension == OreSpawnMain.DimensionID5;
    }

    public boolean renderAsNormalBlock() {
        return OreSpawnMain.current_dimension == OreSpawnMain.DimensionID5;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.field_94364_a = new IIcon[3];
        this.field_94364_a[0] = iconRegister.registerIcon("OreSpawn:crystalgrass_top");
        this.field_94364_a[1] = iconRegister.registerIcon("OreSpawn:crystalgrass_bottom");
        this.field_94364_a[2] = iconRegister.registerIcon("OreSpawn:crystalgrass_side");
    }
}
