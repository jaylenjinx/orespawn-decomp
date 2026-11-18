package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.AntRobot;
import danger.orespawn.entity.SpiderRobot;

public class ItemWrench extends Item {

    public ItemWrench(int i) {
        this.setCreativeTab(CreativeTabs.tabTools);
        this.setMaxDamage(100);
    }

    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (entity != null && entity instanceof SpiderRobot && entity.riddenByEntity == null) {
            EntityLiving e = (EntityLiving) entity;
            float h = e.getMaxHealth() - e.getHealth();
            e.setDead();
            this.dropItem(player.worldObj, e, OreSpawnMain.SpiderRobotKit, 1, (int) h);

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
        } else {
            if (entity == null || !(entity instanceof AntRobot) || entity.riddenByEntity != null) {
                return false;
            }

            AntRobot e = (AntRobot) entity;
            if (e.getOwned() == 0) {
                if (e.getHealth() / e.getMaxHealth() > 0.5F) {
                    return false;
                }

                e.setOwned();
            }

            float h = e.getMaxHealth() - e.getHealth();
            e.setDead();
            this.dropItem(player.worldObj, e, OreSpawnMain.AntRobotKit, 1, (int) h);

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
        }

        stack.damageItem(2, player);
        if (stack.stackSize <= 0) {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
        }

        return true;
    }

    private void dropItem(World world, EntityLiving e, Item index, int par1, int par2) {
        if (!world.isRemote) {
            ItemStack is = new ItemStack(index, par1, 0);
            is.setItemDamage(par2);
            EntityItem var3x = new EntityItem(world, e.posX, e.posY + 1.0, e.posZ, is);
            world.spawnEntityInWorld(var3x);
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
