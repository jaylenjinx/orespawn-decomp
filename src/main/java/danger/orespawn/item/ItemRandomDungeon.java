package danger.orespawn.item;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class ItemRandomDungeon extends Item {

    Random rand = OreSpawnMain.OreSpawnRand;

    public ItemRandomDungeon(int i) {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        stack.addEnchantment(Enchantment.fortune, 2);
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantment.fortune, 2);
        }
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        this.onUsingTick(stack, (EntityPlayer) null, 0);
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int clickedX, int clickedY,
        int clickedZ, int side, float hitX, float hitY, float hitZ) {
        Block block = world.getBlock(clickedX, clickedY, clickedZ);
        if (block != Blocks.stone && block != Blocks.cobblestone && block != Blocks.grass && block != Blocks.dirt) {
            return false;
        } else if (clickedY < 40) {
            return false;
        } else {
            if (!world.isRemote) {
                world.setBlock(clickedX, clickedY + 1, clickedZ, OreSpawnMain.MyDungeonSpawnerBlock, 0, 2);
            }

            if (!player.capabilities.isCreativeMode) {
                stack.stackSize--;
            }

            return true;
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
