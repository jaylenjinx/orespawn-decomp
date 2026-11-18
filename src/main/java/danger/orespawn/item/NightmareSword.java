package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NightmareSword extends ItemSword {

    public NightmareSword(int n, ToolMaterial enumToolMaterial) {
        super(enumToolMaterial);
        this.maxStackSize = 1;
        this.setMaxDamage(1200);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        stack.addEnchantment(Enchantment.sharpness, 1);
        stack.addEnchantment(Enchantment.knockback, 3);
        stack.addEnchantment(Enchantment.fireAspect, 1);
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantment.sharpness, 1);
            stack.addEnchantment(Enchantment.knockback, 3);
            stack.addEnchantment(Enchantment.fireAspect, 1);
        }
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        this.onUsingTick(stack, (EntityPlayer) null, 0);
    }

    public String getMaterialName() {
        return "Uranium/Titanium";
    }

    public boolean hitEntity(ItemStack stack, EntityLiving target, EntityLiving attacker) {
        stack.damageItem(1, attacker);
        return true;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 5000;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
