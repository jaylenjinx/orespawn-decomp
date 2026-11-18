package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class ExperienceCatcher extends Item {

    public ExperienceCatcher(int i) {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World world, int x, int y, int z,
        int par7, float par8, float par9, float par10) {
        par2EntityPlayer.swingItem();
        System.out.printf("x, y,z, 7,8,9,10 == %d, %d, %d - %d, %f, %f, %f\n", x, y, z, par7, par8, par9, par10);
        if (!par2EntityPlayer.worldObj.isRemote) {
            AxisAlignedBB bb = AxisAlignedBB
                .getBoundingBox(x - 0.5 + par8, y, z - 0.5 + par10, x + 0.5 + par8, y + 2.0, z + 0.5 + par10);

            for (Entity var3x : world.getEntitiesWithinAABB(EntityXPOrb.class, bb)) {
                if (var3x instanceof EntityXPOrb) {
                    EntityXPOrb ex = (EntityXPOrb) var3x;
                    if (ex.getXpValue() >= 3 && world.rand.nextInt(5) != 1) {
                        var3x.setDead();
                        EntityItem var4x = null;
                        ItemStack is = new ItemStack(Items.experience_bottle, 1, 0);
                        var4x = new EntityItem(par2EntityPlayer.worldObj, par8 + x, y + 1.0, par10 + z, is);
                        if (var4x != null) {
                            par2EntityPlayer.worldObj.spawnEntityInWorld(var4x);
                        }

                        is = new ItemStack(Items.string, 1, 0);
                        var4x = new EntityItem(par2EntityPlayer.worldObj, par8 + x, y + 1.0, par10 + z, is);
                        if (var4x != null) {
                            par2EntityPlayer.worldObj.spawnEntityInWorld(var4x);
                        }

                        is = new ItemStack(Items.stick, 1, 0);
                        var4x = new EntityItem(par2EntityPlayer.worldObj, par8 + x, y + 1.0, par10 + z, is);
                        if (var4x != null) {
                            par2EntityPlayer.worldObj.spawnEntityInWorld(var4x);
                        }

                        if (!par2EntityPlayer.capabilities.isCreativeMode) {
                            par1ItemStack.stackSize--;
                        }

                        return true;
                    }
                }
            }

            EntityItem var4x = null;
            ItemStack isx = new ItemStack(OreSpawnMain.MyExperienceCatcher, 1, 0);
            var4x = new EntityItem(par2EntityPlayer.worldObj, par8 + x, y + 1.0, par10 + z, isx);
            if (var4x != null) {
                par2EntityPlayer.worldObj.spawnEntityInWorld(var4x);
            }

            par1ItemStack.stackSize--;
        }

        return true;
    }

    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        par3EntityPlayer.swingItem();
        return par1ItemStack;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
