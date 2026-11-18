package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemPickaxe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AmethystPickaxe extends ItemPickaxe {

    private int weaponDamage = 12;

    public AmethystPickaxe(int n, ToolMaterial toolMaterial) {
        super(toolMaterial);
        this.maxStackSize = 1;
        this.setMaxDamage(2000);
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    public int getDamageVsEntity(Entity entity) {
        return this.weaponDamage;
    }

    public int getDamageVsEntity() {
        return this.weaponDamage;
    }

    public String getMaterialName() {
        return "Amethyst";
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
