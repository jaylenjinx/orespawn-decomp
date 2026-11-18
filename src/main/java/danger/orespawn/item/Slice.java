package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.entity.Boyfriend;
import danger.orespawn.entity.Girlfriend;
import danger.orespawn.entity.projectile.BerthaHit;

public class Slice extends ItemSword {

    public Slice(int n, ToolMaterial enumToolMaterial) {
        super(enumToolMaterial);
        this.maxStackSize = 1;
        this.setMaxDamage(2600);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        stack.addEnchantment(Enchantment.sharpness, 5);
        stack.addEnchantment(Enchantment.baneOfArthropods, 1);
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantment.sharpness, 5);
            stack.addEnchantment(Enchantment.baneOfArthropods, 1);
        }
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        this.onUsingTick(stack, (EntityPlayer) null, 0);
    }

    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        return entity != null
            && (entity instanceof EntityPlayer || entity instanceof Girlfriend || entity instanceof Boyfriend);
    }

    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
        if (entityLiving != null && entityLiving instanceof EntityPlayer) {
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
