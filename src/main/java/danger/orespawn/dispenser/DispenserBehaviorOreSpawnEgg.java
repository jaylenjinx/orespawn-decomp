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

    public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
        EnumFacing enumfacing = BlockDispenser.func_149937_b(source.getBlockMetadata());
        double d0 = source.getX() + enumfacing.getFrontOffsetX() * 2.0;
        double d1 = source.getYInt() + 0.2F;
        double d2 = source.getZ() + enumfacing.getFrontOffsetZ() * 2.0;
        Item it = stack.getItem();
        if (it instanceof ItemSpawnEgg) {
            ItemSpawnEgg ise = (ItemSpawnEgg) it;
            Entity entity = ItemSpawnEgg.spawn_something(ise.my_id, source.getWorld(), (int) d0, (int) d1, (int) d2);
            if (entity instanceof EntityLivingBase && stack.hasDisplayName()) {
                ((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());
            }
        }

        stack.splitStack(1);
        return stack;
    }
}
