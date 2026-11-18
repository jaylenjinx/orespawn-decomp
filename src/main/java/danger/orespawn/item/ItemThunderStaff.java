package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.entity.projectile.ThunderBolt;

public class ItemThunderStaff extends Item {

    private int ticker = 50;

    public ItemThunderStaff(int i) {
        this.maxStackSize = 1;
        this.setMaxDamage(50);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack.getMaxDamage() - stack.getItemDamage() <= 1) {
            return stack;
        } else {
            double xzoff = 1.0;
            double yoff = 1.55;
            ThunderBolt lb = new ThunderBolt(world, player);
            lb.setLocationAndAngles(
                player.posX - xzoff * Math.sin(Math.toRadians(player.rotationYawHead + 45.0F)),
                player.posY + yoff,
                player.posZ + xzoff * Math.cos(Math.toRadians(player.rotationYawHead + 45.0F)),
                player.rotationYawHead,
                player.rotationPitch);
            lb.motionX *= 3.0;
            lb.motionY *= 3.0;
            lb.motionZ *= 3.0;
            world.spawnEntityInWorld(lb);
            player.swingItem();
            player.addVelocity(
                Math.cos(Math.toRadians(player.rotationYawHead - 90.0F)) * 0.5,
                0.15,
                Math.sin(Math.toRadians(player.rotationYawHead - 90.0F)) * 0.5);
            stack.damageItem(1, player);
            return stack;
        }
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        if (world.isRaining() && world.isThundering()) {
            if (this.ticker > 0) {
                this.ticker--;
            }

            if (this.ticker <= 0 && stack.getItemDamage() > 0) {
                stack.setItemDamage(stack.getItemDamage() - 1);
                this.ticker = 50;
            }
        }
    }

    public String getMaterialName() {
        return "Unknown";
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
