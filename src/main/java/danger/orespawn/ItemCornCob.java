package danger.orespawn;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemSeedFood;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCornCob extends ItemSeedFood {

    public ItemCornCob(int par1, int par2, float par3, Block par4, Block par5) {
        super(par2, par3, par4, par5);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
