package danger.orespawn.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class UltimateHoe extends ItemHoe {

    public UltimateHoe(int n, ToolMaterial toolMaterial) {
        super(toolMaterial);
        this.maxStackSize = 1;
        this.setMaxDamage(3000);
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        stack.addEnchantment(Enchantment.efficiency, 2);
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantment.efficiency, 2);
        }
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        this.onUsingTick(stack, (EntityPlayer) null, 0);
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        if (!player.canPlayerEdit(x, y, z, side, stack)) {
            return false;
        } else {
            Block i1 = world.getBlock(x, y, z);
            boolean air = world.isAirBlock(x, y + 1, z);
            if (side != 0 && air && (i1 == Blocks.grass || i1 == Blocks.dirt)) {
                Block block = Blocks.farmland;
                world.playSoundEffect(
                    x + 0.5F,
                    y + 0.5F,
                    z + 0.5F,
                    block.stepSound.getBreakSound(),
                    (block.stepSound.getVolume() + 1.0F) / 2.0F,
                    block.stepSound.getPitch() * 0.8F);
                if (world.isRemote) {
                    return true;
                } else {
                    for (int i = -1; i <= 1; i++) {
                        for (int k = -1; k <= 1; k++) {
                            for (int j = -1; j <= 1; j++) {
                                i1 = world.getBlock(x + i, y + j, z + k);
                                air = world.isAirBlock(x + i, y + j + 1, z + k);
                                if (air && (i1 == Blocks.grass || i1 == Blocks.dirt)) {
                                    world.setBlock(x + i, y + j, z + k, block, 7, 2);
                                }
                            }
                        }
                    }

                    stack.damageItem(1, player);
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    public String getMaterialName() {
        return "Uranium/Titanium";
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
