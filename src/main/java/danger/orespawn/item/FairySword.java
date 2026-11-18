package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.entity.Fairy;

public class FairySword extends ItemSword {

    private int weaponDamage;
    private final ToolMaterial toolMaterial;

    public FairySword(int n, ToolMaterial enumToolMaterial) {
        super(enumToolMaterial);
        this.toolMaterial = enumToolMaterial;
        this.weaponDamage = 15;
        this.maxStackSize = 1;
        this.setMaxDamage(1300);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public String getMaterialName() {
        return "Fairy";
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        int n = 5;
        if (target != null && !target.worldObj.isRemote) {
            int num = 1 + target.worldObj.rand.nextInt(3);

            for (int i = 0; i < num; i++) {
                Fairy r = null;
                r = (Fairy) spawnCreature(
                    target.worldObj,
                    0,
                    "Fairy",
                    target.posX + (target.worldObj.rand.nextFloat() - target.worldObj.rand.nextFloat()) * 0.5,
                    target.posY + target.worldObj.rand.nextFloat() + 0.01,
                    target.posZ + (target.worldObj.rand.nextFloat() - target.worldObj.rand.nextFloat()) * 0.5);
                if (r != null) {
                    r.setOwner(attacker);
                }
            }
        }

        stack.damageItem(1, attacker);
        return true;
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

    public int getMaxItemUseDuration(ItemStack stack) {
        return 3000;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
