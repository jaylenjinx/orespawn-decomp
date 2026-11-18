package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EmeraldSword extends ItemSword {

    private int weaponDamage;
    private final ToolMaterial toolMaterial;

    public EmeraldSword(int n, ToolMaterial enumToolMaterial) {
        super(enumToolMaterial);
        this.toolMaterial = enumToolMaterial;
        this.weaponDamage = 15;
        this.maxStackSize = 1;
        this.setMaxDamage(1300);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public void onCreated(ItemStack stack, World world, EntityPlayer player) {}

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {}

    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {}

    public int getDamageVsEntity(Entity entity) {
        return this.weaponDamage;
    }

    public String getMaterialName() {
        return "Emerald";
    }

    public boolean hitEntity(ItemStack stack, EntityLiving target, EntityLiving attacker) {
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
