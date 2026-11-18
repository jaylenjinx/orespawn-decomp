package danger.orespawn;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemPickaxe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CrystalPickaxe extends ItemPickaxe {

    public CrystalPickaxe(int par1, ToolMaterial par2) {
        super(par2);
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
