package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.entity.Elevator;

public class ItemElevator extends Item {

    public ItemElevator(int par1) {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabTransport);
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4,
        int par5, int par6, int par7, float par8, float par9, float par10) {
        if (par3World.isRemote) {
            return true;
        } else {
            Elevator elevator = (Elevator) EntityList.createEntityByName("Hoverboard", par3World);
            elevator
                .setLocationAndAngles(par4 + 0.5F, par5 + 1.2F, par6 + 0.5F, par3World.rand.nextFloat() * 360.0F, 0.0F);
            par3World.spawnEntityInWorld(elevator);
            if (!par2EntityPlayer.capabilities.isCreativeMode) {
                par1ItemStack.stackSize--;
            }

            return true;
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
