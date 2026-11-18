package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class ExperienceCatcher extends Item {

    public ExperienceCatcher(int i) {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        player.swingItem();
        System.out.printf("x, y,z, 7,8,9,10 == %d, %d, %d - %d, %f, %f, %f\n", x, y, z, side, hitX, hitY, hitZ);
        if (!player.worldObj.isRemote) {
            AxisAlignedBB bb = AxisAlignedBB
                .getBoundingBox(x - 0.5 + hitX, y, z - 0.5 + hitZ, x + 0.5 + hitX, y + 2.0, z + 0.5 + hitZ);

            for (Entity entity : world.getEntitiesWithinAABB(EntityXPOrb.class, bb)) {
                if (entity instanceof EntityXPOrb) {
                    EntityXPOrb ex = (EntityXPOrb) entity;
                    if (ex.getXpValue() >= 3 && world.rand.nextInt(5) != 1) {
                        entity.setDead();
                        EntityItem entityItem = null;
                        ItemStack is = new ItemStack(Items.experience_bottle, 1, 0);
                        entityItem = new EntityItem(player.worldObj, hitX + x, y + 1.0, hitZ + z, is);
                        if (entityItem != null) {
                            player.worldObj.spawnEntityInWorld(entityItem);
                        }

                        is = new ItemStack(Items.string, 1, 0);
                        entityItem = new EntityItem(player.worldObj, hitX + x, y + 1.0, hitZ + z, is);
                        if (entityItem != null) {
                            player.worldObj.spawnEntityInWorld(entityItem);
                        }

                        is = new ItemStack(Items.stick, 1, 0);
                        entityItem = new EntityItem(player.worldObj, hitX + x, y + 1.0, hitZ + z, is);
                        if (entityItem != null) {
                            player.worldObj.spawnEntityInWorld(entityItem);
                        }

                        if (!player.capabilities.isCreativeMode) {
                            stack.stackSize--;
                        }

                        return true;
                    }
                }
            }

            EntityItem entityItem = null;
            ItemStack isx = new ItemStack(OreSpawnMain.MyExperienceCatcher, 1, 0);
            entityItem = new EntityItem(player.worldObj, hitX + x, y + 1.0, hitZ + z, isx);
            if (entityItem != null) {
                player.worldObj.spawnEntityInWorld(entityItem);
            }

            stack.stackSize--;
        }

        return true;
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.swingItem();
        return stack;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
