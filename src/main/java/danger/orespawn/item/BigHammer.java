package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BigHammer extends ItemSword {

    private int weaponDamage;
    private final ToolMaterial toolMaterial;

    public BigHammer(int par1, ToolMaterial par2EnumToolMaterial) {
        super(par2EnumToolMaterial);
        this.toolMaterial = par2EnumToolMaterial;
        this.weaponDamage = 15;
        this.maxStackSize = 1;
        this.setMaxDamage(9000);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public String getMaterialName() {
        return "AMETHYST";
    }

    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving,
        EntityLivingBase par3EntityLiving) {
        int var2x = 5;
        if (par2EntityLiving != null && !par2EntityLiving.worldObj.isRemote) {
            par2EntityLiving.addVelocity(0.0, Math.abs(par2EntityLiving.worldObj.rand.nextFloat() * 2.0F / 3.0F), 0.0);
        }

        par1ItemStack.damageItem(1, par3EntityLiving);
        return true;
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 3000;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
