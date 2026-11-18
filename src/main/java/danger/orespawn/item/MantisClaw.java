package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MantisClaw extends ItemSword {

    private int weaponDamage;
    private final ToolMaterial toolMaterial;

    public MantisClaw(int n, ToolMaterial enumToolMaterial) {
        super(enumToolMaterial);
        this.toolMaterial = enumToolMaterial;
        this.weaponDamage = 10;
        this.maxStackSize = 1;
        this.setMaxDamage(1000);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public String getMaterialName() {
        return "AMETHYST";
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        int n = 5;
        if (target != null && attacker != null && !target.worldObj.isRemote) {
            target.heal(-1.0F);
            attacker.heal(1.0F);
        }

        stack.damageItem(1, attacker);
        return true;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 3000;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
