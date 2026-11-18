package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.entity.AttackSquid;

public class ItemSquidZooka extends Item {

    public ItemSquidZooka(int i) {
        this.maxStackSize = 1;
        this.setMaxDamage(100);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage() <= 1) {
            return par1ItemStack;
        } else {
            par2World.playSoundAtEntity(par3EntityPlayer, "random.explode", 0.5F, 0.5F);
            if (!par2World.isRemote) {
                double xzoff = 2.5;
                double yoff = 1.65;
                Entity e = spawnCreature(
                    par2World,
                    "Attack Squid",
                    par3EntityPlayer.posX - xzoff * Math.sin(Math.toRadians(par3EntityPlayer.rotationYawHead + 15.0F)),
                    par3EntityPlayer.posY + yoff,
                    par3EntityPlayer.posZ + xzoff * Math.cos(Math.toRadians(par3EntityPlayer.rotationYawHead + 15.0F)));
                if (e instanceof AttackSquid) {
                    AttackSquid a = (AttackSquid) e;
                    a.setWasShot();
                }

                float f = 3.6F;
                e.motionX = -MathHelper.sin(par3EntityPlayer.rotationYaw / 180.0F * (float) Math.PI)
                    * MathHelper.cos(par3EntityPlayer.rotationPitch / 180.0F * (float) Math.PI)
                    * f;
                e.motionZ = MathHelper.cos(par3EntityPlayer.rotationYaw / 180.0F * (float) Math.PI)
                    * MathHelper.cos(par3EntityPlayer.rotationPitch / 180.0F * (float) Math.PI)
                    * f;
                e.motionY = -MathHelper.sin(par3EntityPlayer.rotationPitch / 180.0F * (float) Math.PI) * f;
                e.motionX = e.motionX + (par2World.rand.nextFloat() - par2World.rand.nextFloat()) * 0.05;
                e.motionY = e.motionY + (par2World.rand.nextFloat() - par2World.rand.nextFloat()) * 0.05;
                e.motionZ = e.motionZ + (par2World.rand.nextFloat() - par2World.rand.nextFloat()) * 0.05;
            }

            par3EntityPlayer.swingItem();
            par3EntityPlayer.addVelocity(
                Math.cos(Math.toRadians(par3EntityPlayer.rotationYawHead - 90.0F)) * 0.45,
                0.1,
                Math.sin(Math.toRadians(par3EntityPlayer.rotationYawHead - 90.0F)) * 0.45);
            par1ItemStack.damageItem(1, par3EntityPlayer);
            return par1ItemStack;
        }
    }

    public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = EntityList.createEntityByName(par1, par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);
            par0World.spawnEntityInWorld(var8);
        }

        return var8;
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
