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

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack.getMaxDamage() - stack.getItemDamage() <= 1) {
            return stack;
        } else {
            world.playSoundAtEntity(player, "random.explode", 0.5F, 0.5F);
            if (!world.isRemote) {
                double xzoff = 2.5;
                double yoff = 1.65;
                Entity e = spawnCreature(
                    world,
                    "Attack Squid",
                    player.posX - xzoff * Math.sin(Math.toRadians(player.rotationYawHead + 15.0F)),
                    player.posY + yoff,
                    player.posZ + xzoff * Math.cos(Math.toRadians(player.rotationYawHead + 15.0F)));
                if (e instanceof AttackSquid) {
                    AttackSquid a = (AttackSquid) e;
                    a.setWasShot();
                }

                float f = 3.6F;
                e.motionX = -MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI)
                    * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI)
                    * f;
                e.motionZ = MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI)
                    * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI)
                    * f;
                e.motionY = -MathHelper.sin(player.rotationPitch / 180.0F * (float) Math.PI) * f;
                e.motionX = e.motionX + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.05;
                e.motionY = e.motionY + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.05;
                e.motionZ = e.motionZ + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.05;
            }

            player.swingItem();
            player.addVelocity(
                Math.cos(Math.toRadians(player.rotationYawHead - 90.0F)) * 0.45,
                0.1,
                Math.sin(Math.toRadians(player.rotationYawHead - 90.0F)) * 0.45);
            stack.damageItem(1, player);
            return stack;
        }
    }

    public static Entity spawnCreature(World world, String str, double x, double y, double z) {
        Entity entity = null;
        entity = EntityList.createEntityByName(str, world);
        if (entity != null) {
            entity.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0F, 0.0F);
            world.spawnEntityInWorld(entity);
        }

        return entity;
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
