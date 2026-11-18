package danger.orespawn.dispenser;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import danger.orespawn.item.ItemSpawnEgg;

public final class DispenserBehaviorOreSpawnEgg extends BehaviorDefaultDispenseItem {

    public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack) {
        EnumFacing enumfacing = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
        double d0 = par1IBlockSource.getX() + enumfacing.getFrontOffsetX() * 2.0;
        double d1 = par1IBlockSource.getYInt() + 0.2F;
        double d2 = par1IBlockSource.getZ() + enumfacing.getFrontOffsetZ() * 2.0;
        Item it = par2ItemStack.getItem();
        if (it instanceof ItemSpawnEgg) {
            ItemSpawnEgg ise = (ItemSpawnEgg) it;
            Entity entity = ItemSpawnEgg
                .spawn_something(ise.my_id, par1IBlockSource.getWorld(), (int) d0, (int) d1, (int) d2);
            if (entity instanceof EntityLivingBase && par2ItemStack.hasDisplayName()) {
                ((EntityLiving) entity).setCustomNameTag(par2ItemStack.getDisplayName());
            }
        }

        par2ItemStack.splitStack(1);
        return par2ItemStack;
    }
}
