package danger.orespawn.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemNetherLost extends Item {

    public ItemNetherLost(int n) {
        this.maxStackSize = 1;
        this.setMaxDamage(3000);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        stack.addEnchantment(Enchantment.sharpness, 2);
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantment.sharpness, 2);
        }
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        EntityLivingBase e = null;
        EntityPlayer p = null;
        this.onUsingTick(stack, (EntityPlayer) null, 0);
        if (world != null) {
            if (entity != null && entity instanceof EntityLivingBase) {
                e = (EntityLivingBase) entity;
                if (e instanceof EntityPlayer) {
                    p = (EntityPlayer) e;
                    ItemStack is = p.getCurrentEquippedItem();
                    if (is != null) {
                        Item it = is.getItem();
                        if (it != null && it instanceof ItemNetherLost && world.provider.dimensionId == -1) {
                            Block i = world.getBlock((int) p.posX, (int) p.posY - 1, (int) p.posZ);
                            if (i == Blocks.netherrack) {
                                world.setBlock((int) p.posX, (int) p.posY - 1, (int) p.posZ, Blocks.quartz_block);
                            }
                        }
                    }
                }
            }
        }
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
