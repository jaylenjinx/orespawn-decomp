package danger.orespawn.item;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemTool;

import com.google.common.collect.Sets;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class CrystalShovel extends ItemTool {

    public static final Set blocksEffectiveAgainst = Sets.newHashSet(
        new Block[] { Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow, Blocks.snow_layer,
            Blocks.clay, Blocks.farmland, Blocks.mycelium, OreSpawnMain.CrystalGrass });

    public CrystalShovel(int par1, ToolMaterial par2) {
        super(1.0F, par2, blocksEffectiveAgainst);
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    public boolean canHarvestBlock(Block par1Block) {
        return par1Block == Blocks.snow;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
