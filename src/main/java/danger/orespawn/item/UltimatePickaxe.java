package danger.orespawn.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.Boyfriend;
import danger.orespawn.entity.Girlfriend;

public class UltimatePickaxe extends ItemPickaxe {

    private int weaponDamage = 15;

    public UltimatePickaxe(int n, ToolMaterial toolMaterial) {
        super(toolMaterial);
        this.maxStackSize = 1;
        this.setMaxDamage(3000);
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        stack.addEnchantment(Enchantment.efficiency, 5);
        stack.addEnchantment(Enchantment.fortune, 5);
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantment.efficiency, 5);
            stack.addEnchantment(Enchantment.fortune, 5);
        }
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        this.onUsingTick(stack, (EntityPlayer) null, 0);
    }

    public boolean canHarvestBlock(Block block) {
        return true;
    }

    public int getDamageVsEntity(Entity entity) {
        if (entity instanceof Girlfriend) {
            return 1;
        } else if (entity instanceof Boyfriend) {
            return 1;
        } else {
            return entity instanceof EntityPlayer ? 1 : this.weaponDamage;
        }
    }

    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (entity != null && OreSpawnMain.ultimate_sword_pvp == 0) {
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

    private ItemStack dropItemAnItem(World world, int x, int y, int z, Item index, int n) {
        EntityItem entityItem = null;
        ItemStack is = new ItemStack(index, n, 0);
        entityItem = new EntityItem(world, x, y, z, is);
        if (entityItem != null) {
            world.spawnEntityInWorld(entityItem);
        }

        return is;
    }

    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z,
        EntityLivingBase entity) {
        if (block.getBlockHardness(world, x, y, z) != 0.0) {
            stack.damageItem(1, entity);
        }

        if (!world.isRemote) {
            if (block == Blocks.iron_ore && world.rand.nextInt(2) != 0) {
                this.dropItemAnItem(world, x, y, z, Items.iron_ingot, 1 + world.rand.nextInt(2));
            }

            if (block == Blocks.gold_ore && world.rand.nextInt(2) != 0) {
                this.dropItemAnItem(world, x, y, z, Items.gold_ingot, 1 + world.rand.nextInt(2));
            }

            if (block == Blocks.stone && world.rand.nextInt(100) == 2) {
                int i = world.rand.nextInt(10);
                if (i == 0) {
                    this.dropItemAnItem(world, x, y, z, Items.diamond, 1);
                }

                if (i == 1) {
                    this.dropItemAnItem(world, x, y, z, Items.emerald, 1);
                }

                if (i == 2) {
                    this.dropItemAnItem(world, x, y, z, OreSpawnMain.MyAmethyst, 1);
                }

                if (i == 3) {
                    this.dropItemAnItem(world, x, y, z, OreSpawnMain.MyRuby, 1);
                }

                if (i == 4) {
                    this.dropItemAnItem(world, x, y, z, OreSpawnMain.UraniumNugget, 1);
                }

                if (i == 5) {
                    this.dropItemAnItem(world, x, y, z, OreSpawnMain.TitaniumNugget, 1);
                }
            }
        }

        return true;
    }

    public String getMaterialName() {
        return "Uranium/Titanium";
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
