package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PoisonSword extends ItemSword {

    private int weaponDamage;
    private final ToolMaterial toolMaterial;

    public PoisonSword(int n, ToolMaterial enumToolMaterial) {
        super(enumToolMaterial);
        this.toolMaterial = enumToolMaterial;
        this.weaponDamage = 15;
        this.maxStackSize = 1;
        this.setMaxDamage(1300);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        stack.addEnchantment(Enchantment.sharpness, 1);
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantment.sharpness, 1);
        }
    }

    public String getMaterialName() {
        return "Emerald";
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        int n = 5;
        if (target != null) {
            n = 10 + target.worldObj.rand.nextInt(10);
            target.addPotionEffect(new PotionEffect(Potion.poison.id, n * 20, 0));
            n = 10 + target.worldObj.rand.nextInt(10);
            target.addPotionEffect(new PotionEffect(Potion.wither.id, n * 20, 0));
            n = 10 + target.worldObj.rand.nextInt(10);
            target.addPotionEffect(new PotionEffect(Potion.weakness.id, n * 20, 0));
        }

        stack.damageItem(1, attacker);
        return true;
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
