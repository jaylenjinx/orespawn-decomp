package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.entity.projectile.UltimateArrow;

public class UltimateBow extends Item {

    public UltimateBow(int n) {
        this.maxStackSize = 1;
        this.setMaxDamage(1000);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        stack.addEnchantment(Enchantment.power, 5);
        stack.addEnchantment(Enchantment.flame, 3);
        stack.addEnchantment(Enchantment.punch, 2);
        stack.addEnchantment(Enchantment.infinity, 1);
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantment.power, 5);
            stack.addEnchantment(Enchantment.flame, 3);
            stack.addEnchantment(Enchantment.punch, 2);
            stack.addEnchantment(Enchantment.infinity, 1);
        }
    }

    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int timeLeft) {
        EntityArrow ultimateArrow = new UltimateArrow(world, player, 3.0F);
        if (world.rand.nextInt(4) == 1) {
            ultimateArrow.setIsCritical(true);
        }

        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
        if (enchantmentLevel > 0) {
            ultimateArrow.setKnockbackStrength(enchantmentLevel);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0) {
            ultimateArrow.setFire(100);
        }

        stack.damageItem(1, player);
        world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
        ultimateArrow.canBePickedUp = 2;
        if (!world.isRemote) {
            world.spawnEntityInWorld(ultimateArrow);
        }
    }

    public ItemStack onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        return stack;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 9000;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.bow;
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }

    public int getItemEnchantability() {
        return 50;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
