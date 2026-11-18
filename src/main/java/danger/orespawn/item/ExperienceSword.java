package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ExperienceSword extends ItemSword {

    private int weaponDamage;
    private final ToolMaterial toolMaterial;
    private World worldObj = null;
    private World worldObjr = null;

    public ExperienceSword(int n, ToolMaterial enumToolMaterial) {
        super(enumToolMaterial);
        this.toolMaterial = enumToolMaterial;
        this.weaponDamage = 15;
        this.maxStackSize = 1;
        this.setMaxDamage(1400);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        stack.addEnchantment(Enchantment.sharpness, 2);
        stack.addEnchantment(Enchantment.unbreaking, 3);
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantment.sharpness, 2);
            stack.addEnchantment(Enchantment.unbreaking, 3);
        }
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        EntityLivingBase e = null;
        ItemOreSpawnArmor ia = null;
        EntityPlayer p = null;
        this.onUsingTick(stack, (EntityPlayer) null, 0);
        if (this.worldObj == null && !world.isRemote) {
            this.worldObj = world;
        }

        if (this.worldObjr == null && world.isRemote) {
            this.worldObjr = world;
        }

        if (world.rand.nextInt(60) == 1 && entity != null && entity instanceof EntityLivingBase) {
            e = (EntityLivingBase) entity;
            if (e instanceof EntityPlayer) {
                p = (EntityPlayer) e;
            }

            for (int i = 1; i < 5; i++) {
                ItemStack is = p.getEquipmentInSlot(i);
                if (is != null) {
                    Item it = is.getItem();
                    if (it != null && it instanceof ItemOreSpawnArmor) {
                        ia = (ItemOreSpawnArmor) it;
                        if (ia.get_armor_material() == 4) {
                            switch (ia.get_armor_type()) {
                                case 0:
                                    if (!world.isRemote && p != null && world.rand.nextInt(10) == 1) {
                                        p.addExperience(1);
                                    }

                                    world.spawnParticle(
                                        "portal",
                                        e.posX,
                                        e.posY + 1.5,
                                        e.posZ,
                                        world.rand.nextGaussian(),
                                        world.rand.nextGaussian(),
                                        world.rand.nextGaussian());
                                    break;
                                case 1:
                                    if (!world.isRemote && p != null && world.rand.nextInt(20) == 1) {
                                        p.addExperience(1);
                                    }

                                    world.spawnParticle(
                                        "portal",
                                        e.posX,
                                        e.posY + 1.25,
                                        e.posZ,
                                        world.rand.nextGaussian(),
                                        world.rand.nextGaussian(),
                                        world.rand.nextGaussian());
                                    break;
                                case 2:
                                    if (!world.isRemote && p != null && world.rand.nextInt(30) == 1) {
                                        p.addExperience(1);
                                    }

                                    world.spawnParticle(
                                        "portal",
                                        e.posX,
                                        e.posY + 0.75,
                                        e.posZ,
                                        world.rand.nextGaussian(),
                                        world.rand.nextGaussian(),
                                        world.rand.nextGaussian());
                                    break;
                                case 3:
                                    if (!world.isRemote && p != null && world.rand.nextInt(40) == 1) {
                                        p.addExperience(1);
                                    }

                                    world.spawnParticle(
                                        "portal",
                                        e.posX,
                                        e.posY + 0.25,
                                        e.posZ,
                                        world.rand.nextGaussian(),
                                        world.rand.nextGaussian(),
                                        world.rand.nextGaussian());
                            }
                        }
                    }
                }
            }
        }
    }

    public int getDamageVsEntity(Entity entity) {
        return this.weaponDamage;
    }

    public String getMaterialName() {
        return "Emerald";
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        float i = 0.0F;
        EntityPlayer p = null;
        EntityLiving l = null;
        if (attacker instanceof EntityPlayer) {
            p = (EntityPlayer) attacker;
        }

        if (target != null && target instanceof EntityLiving) {
            i = 10.0F;
        }

        if (i > 0.0F && p != null) {
            p.addExperience((int) i);
        }

        if (p != null) {
            i = p.experienceLevel / 2;
            if (i > 0.0F && target != null) {
                target.attackEntityFrom(DamageSource.causePlayerDamage(p), i);
            }
        }

        if (this.worldObjr != null && target != null) {
            for (int j = 0; j <= i / 2.0F; j++) {
                this.worldObjr.spawnParticle(
                    "portal",
                    target.posX,
                    target.posY + 1.0,
                    target.posZ,
                    this.worldObjr.rand.nextGaussian(),
                    this.worldObjr.rand.nextGaussian(),
                    this.worldObjr.rand.nextGaussian());
            }
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
