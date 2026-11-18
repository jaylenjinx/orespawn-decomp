package danger.orespawn.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class BlockDuplicatorLog extends Block {

    public BlockDuplicatorLog(int n) {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setTickRandomly(true);
    }

    public int tickRate() {
        return 1;
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (!world.isRemote) {
            if (OreSpawnMain.enableduplicatortree != 0) {
                OreSpawnMain.OreSpawnTrees.DuplicatorTree(world, x, y, z);
            }
        }
    }

    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(Item.getItemFromBlock(this), 1, 0));
    }

    protected ItemStack createStackedBlock(int n) {
        return new ItemStack(this, 1, 0);
    }

    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public boolean isWood(IBlockAccess world, int x, int y, int z) {
        return true;
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
