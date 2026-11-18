package danger.orespawn.block;

import net.minecraft.block.BlockWorkbench;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class CrystalWorkbench extends BlockWorkbench {

    @SideOnly(Side.CLIENT)
    private IIcon workbenchIconTop;
    @SideOnly(Side.CLIENT)
    private IIcon workbenchIconFront;

    public CrystalWorkbench(int n, float f1, float f2) {
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHardness(f1);
        this.setResistance(f2);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
        float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        } else {
            player.openGui(OreSpawnMain.instance, 1, world, x, y, z);
            return true;
        }
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return side == 1 ? this.workbenchIconTop
            : (side == 0 ? this.blockIcon : (side != 2 && side != 4 ? this.blockIcon : this.workbenchIconFront));
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5) + "_side");
        this.workbenchIconTop = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5) + "_top");
        this.workbenchIconFront = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5) + "_bottom");
    }
}
