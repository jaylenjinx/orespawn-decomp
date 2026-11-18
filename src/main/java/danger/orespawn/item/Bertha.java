package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.Boyfriend;
import danger.orespawn.entity.Girlfriend;
import danger.orespawn.entity.projectile.BerthaHit;

public class Bertha extends ItemSword {

    public Bertha(int n, ToolMaterial enumToolMaterial) {
        super(enumToolMaterial);
        this.maxStackSize = 1;
        this.setMaxDamage(9000);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        if (this == OreSpawnMain.MyRoyal) {
            stack.addEnchantment(Enchantment.unbreaking, 5);
        } else if (this != OreSpawnMain.MyHammy) {
            stack.addEnchantment(Enchantment.knockback, 5);
            stack.addEnchantment(Enchantment.baneOfArthropods, 1);
            stack.addEnchantment(Enchantment.fireAspect, 1);
        }
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack);
        if (lvl == 0) {
            lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
        }

        if (lvl <= 0) {
            if (this == OreSpawnMain.MyRoyal) {
                stack.addEnchantment(Enchantment.unbreaking, 5);
            } else if (this != OreSpawnMain.MyHammy) {
                stack.addEnchantment(Enchantment.knockback, 5);
                stack.addEnchantment(Enchantment.baneOfArthropods, 1);
                stack.addEnchantment(Enchantment.fireAspect, 1);
            }
        }
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        this.onUsingTick(stack, (EntityPlayer) null, 0);
    }

    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (entity != null && OreSpawnMain.big_bertha_pvp == 0) {
            if (entity instanceof EntityPlayer || entity instanceof Girlfriend || entity instanceof Boyfriend) {
                return true;
            }

            if (entity instanceof EntityTameable) {
                EntityTameable t = (EntityTameable) entity;
                if (t.isTamed()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
        if (entityLiving != null && entityLiving instanceof EntityPlayer && !entityLiving.worldObj.isRemote) {
            EntityPlayer p = (EntityPlayer) entityLiving;
            double xzoff = 2.0;
            double yoff = 1.55;
            BerthaHit lb = new BerthaHit(p.worldObj, p);
            lb.setLocationAndAngles(
                p.posX - xzoff * Math.sin(Math.toRadians(p.rotationYawHead)),
                p.posY + yoff,
                p.posZ + xzoff * Math.cos(Math.toRadians(p.rotationYawHead)),
                p.rotationYawHead,
                p.rotationPitch);
            lb.motionX *= 2.0;
            lb.motionY *= 2.0;
            lb.motionZ *= 2.0;
            if (this == OreSpawnMain.MyRoyal) {
                lb.setHitType(2);
            }

            if (this == OreSpawnMain.MyHammy) {
                lb.setHitType(3);
            }

            p.worldObj.spawnEntityInWorld(lb);
            stack.damageItem(1, p);
        }

        return false;
    }

    public String getMaterialName() {
        return "Uranium/Titanium";
    }

    public boolean hitEntity(ItemStack stack, EntityLiving target, EntityLiving attacker) {
        stack.damageItem(1, attacker);
        return true;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 9000;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
