package danger.orespawn;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemZooKeeper extends Item {

    public ItemZooKeeper(int i) {
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setMaxDamage(1);
    }

    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        for (int var3x = 0; var3x < 8; var3x++) {
            float f1 = player.worldObj.rand.nextFloat() * 3.0F - player.worldObj.rand.nextFloat() * 3.0F;
            float f2 = 0.25F + player.worldObj.rand.nextFloat() * 2.0F;
            float f3 = player.worldObj.rand.nextFloat() * 3.0F - player.worldObj.rand.nextFloat() * 3.0F;
            player.worldObj.spawnParticle(
                "smoke",
                (float) entity.posX + f1,
                (float) entity.posY + f2,
                (float) entity.posZ + f3,
                0.0,
                0.0,
                0.0);
            f1 = player.worldObj.rand.nextFloat() * 3.0F - player.worldObj.rand.nextFloat() * 3.0F;
            f2 = 0.25F + player.worldObj.rand.nextFloat() * 2.0F;
            f3 = player.worldObj.rand.nextFloat() * 3.0F - player.worldObj.rand.nextFloat() * 3.0F;
            player.worldObj.spawnParticle(
                "explode",
                (float) entity.posX + f1,
                (float) entity.posY + f2,
                (float) entity.posZ + f3,
                0.0,
                0.0,
                0.0);
            f1 = player.worldObj.rand.nextFloat() * 3.0F - player.worldObj.rand.nextFloat() * 3.0F;
            f2 = 0.25F + player.worldObj.rand.nextFloat() * 2.0F;
            f3 = player.worldObj.rand.nextFloat() * 3.0F - player.worldObj.rand.nextFloat() * 3.0F;
            player.worldObj.spawnParticle(
                "reddust",
                (float) entity.posX + f1,
                (float) entity.posY + f2,
                (float) entity.posZ + f3,
                0.0,
                0.0,
                0.0);
        }

        player.worldObj.playSoundAtEntity(player, "random.explode", 0.5F, 1.5F);
        if (entity != null && entity instanceof EntityLiving) {
            EntityLiving e = (EntityLiving) entity;
            e.func_110163_bv();
            stack.damageItem(2, player);
            if (stack.stackSize <= 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
            }

            return true;
        } else {
            return false;
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
