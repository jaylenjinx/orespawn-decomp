package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.entity.projectile.LaserBall;

public class ItemRayGun extends Item {

    public ItemRayGun(int i) {
        this.maxStackSize = 1;
        this.setMaxDamage(50);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack.getMaxDamage() - stack.getItemDamage() <= 1) {
            return stack;
        } else {
            world.playSoundAtEntity(player, "fireworks.launch", 3.5F, 0.5F);
            if (!world.isRemote) {
                double xzoff = 1.0;
                double yoff = 1.55;
                LaserBall lb = new LaserBall(world, player);
                lb.setSpecial();
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
            }

            player.swingItem();
            player.addVelocity(
                Math.cos(Math.toRadians(player.rotationYawHead - 90.0F)) * 1.5,
                0.3,
                Math.sin(Math.toRadians(player.rotationYawHead - 90.0F)) * 1.5);
            stack.damageItem(1, player);
            return stack;
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
