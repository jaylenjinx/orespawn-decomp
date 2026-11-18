package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.projectile.IrukandjiArrow;

public class SkateBow extends Item {

    public SkateBow(int n) {
        this.maxStackSize = 1;
        this.setMaxDamage(300);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public void onCreated(ItemStack stack, World world, EntityPlayer player) {}

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {}

    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int timeLeft) {
        int n = this.getMaxItemUseDuration(stack) - timeLeft;
        boolean flag = player.capabilities.isCreativeMode
            || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
        if (flag || player.inventory.hasItem(OreSpawnMain.MyIrukandjiArrow)) {
            float f = n / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;
            if (f < 0.1) {
                return;
            }

            if (f > 1.75F) {
                f = 1.75F;
            }

            IrukandjiArrow irukandjiArrow = new IrukandjiArrow(world, player, f);
            if (world.rand.nextInt(20) == 1) {
                irukandjiArrow.setIsCritical(true);
            }

            int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
            if (enchantmentLevel > 0) {
                irukandjiArrow.setKnockbackStrength(enchantmentLevel);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0) {
                irukandjiArrow.setFire(100);
            }

            stack.damageItem(1, player);
            world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
            if (!flag) {
                player.inventory.consumeInventoryItem(OreSpawnMain.MyIrukandjiArrow);
            }

            if (!world.isRemote) {
                world.spawnEntityInWorld(irukandjiArrow);
            }
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
