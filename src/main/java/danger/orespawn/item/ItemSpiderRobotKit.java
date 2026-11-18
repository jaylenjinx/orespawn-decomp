package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.AntRobot;

public class ItemSpiderRobotKit extends Item {

    public ItemSpiderRobotKit(int i) {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabTools);
        if (i == OreSpawnMain.BaseItemID + 471) {
            this.setMaxDamage(OreSpawnMain.SpiderRobot_stats.health);
        } else {
            this.setMaxDamage(OreSpawnMain.AntRobot_stats.health);
        }
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        } else {
            String name = null;
            name = "Robot Spider";
            if (stack.getItem() == OreSpawnMain.AntRobotKit) {
                name = "Robot Red Ant";
            }

            Entity ent = spawnCreature(world, 0, name, x + 0.5, y + 1.01, z + 0.5);
            if (ent != null) {
                EntityLiving e = (EntityLiving) ent;
                e.setHealth(this.getMaxDamage() - this.getDamage(stack));
                if (ent instanceof EntityLiving && stack.hasDisplayName()) {
                    ((EntityLiving) ent).setCustomNameTag(stack.getDisplayName());
                }

                world.playSoundAtEntity(player, "random.explode", 1.0F, world.rand.nextFloat() * 0.2F + 0.9F);
                if (ent instanceof AntRobot) {
                    AntRobot a = (AntRobot) ent;
                    a.setOwned();
                }
            }

            if (!player.capabilities.isCreativeMode) {
                stack.stackSize--;
            }

            return true;
        }
    }

    public static Entity spawnCreature(World world, int n, String name, double x, double y, double z) {
        Entity entity = null;
        if (name == null) {
            entity = EntityList.createEntityByID(n, world);
        } else {
            entity = EntityList.createEntityByName(name, world);
        }

        if (entity != null) {
            entity.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0F, 0.0F);
            world.spawnEntityInWorld(entity);
            ((EntityLiving) entity).playLivingSound();
        }

        return entity;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
