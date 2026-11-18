package danger.orespawn.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class ItemExperienceTreeSeed extends Item {

    public ItemExperienceTreeSeed(int i) {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            Block bid = world.getBlock(x, y, z);
            if (bid != Blocks.grass && bid != Blocks.dirt && bid != Blocks.farmland) {
                return false;
            }

            world.setBlock(x, y + 1, z, OreSpawnMain.MyExperiencePlant, 0, 2);
        } else {
            for (int j1 = 0; j1 < 10; j1++) {
                world.spawnParticle(
                    "happyVillager",
                    x + world.rand.nextFloat(),
                    y + 1.0 + world.rand.nextFloat(),
                    z + world.rand.nextFloat(),
                    0.0,
                    0.0,
                    0.0);
            }
        }

        if (!player.capabilities.isCreativeMode) {
            stack.stackSize--;
        }

        return true;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
