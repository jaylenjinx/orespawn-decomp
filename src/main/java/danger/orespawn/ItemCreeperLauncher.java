package danger.orespawn;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCreeperLauncher extends Item {

    public ItemCreeperLauncher(int i) {
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setMaxDamage(1);
    }

    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (entity != null && entity instanceof EntityCreeper) {
            for (int var3x = 0; var3x < 6; var3x++) {
                float f1 = player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat();
                float f2 = 0.25F + player.worldObj.rand.nextFloat() * 6.0F;
                float f3 = player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat();
                player.worldObj.spawnParticle(
                    "smoke",
                    (float) entity.posX + f1,
                    (float) entity.posY + f2,
                    (float) entity.posZ + f3,
                    0.0,
                    f2 / 4.0F,
                    0.0);
                f1 = player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat();
                f2 = 0.25F + player.worldObj.rand.nextFloat() * 6.0F;
                f3 = player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat();
                player.worldObj.spawnParticle(
                    "explode",
                    (float) entity.posX + f1,
                    (float) entity.posY + f2,
                    (float) entity.posZ + f3,
                    0.0,
                    f2 / 4.0F,
                    0.0);
                f1 = player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat();
                f2 = 0.25F + player.worldObj.rand.nextFloat() * 6.0F;
                f3 = player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat();
                player.worldObj.spawnParticle(
                    "reddust",
                    (float) entity.posX + f1,
                    (float) entity.posY + f2,
                    (float) entity.posZ + f3,
                    0.0,
                    f2 / 4.0F,
                    0.0);
            }

            player.worldObj.playSoundAtEntity(player, "fireworks.launch", 2.0F, 1.2F);
            EntityLiving e = (EntityLiving) entity;
            e.addVelocity(0.0, 4.5, 0.0);
            if (!player.capabilities.isCreativeMode) {
                stack.stackSize--;
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
