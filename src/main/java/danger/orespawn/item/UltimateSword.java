package danger.orespawn.item;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.Boyfriend;
import danger.orespawn.entity.Girlfriend;

public class UltimateSword extends ItemSword {

    private int swingtimer = 0;
    private boolean leaf = false;

    public UltimateSword(int n, ToolMaterial enumToolMaterial) {
        super(enumToolMaterial);
        this.maxStackSize = 1;
        this.setMaxDamage(3000);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        if (this != OreSpawnMain.MyChainsaw) {
            if (this != OreSpawnMain.MyBattleAxe) {
                stack.addEnchantment(Enchantment.sharpness, OreSpawnMain.UltimateSwordMagic);
                stack.addEnchantment(Enchantment.smite, OreSpawnMain.UltimateSwordMagic);
                stack.addEnchantment(Enchantment.baneOfArthropods, OreSpawnMain.UltimateSwordMagic);
                stack.addEnchantment(Enchantment.knockback, 1 + OreSpawnMain.UltimateSwordMagic / 2);
                stack.addEnchantment(Enchantment.looting, 1 + OreSpawnMain.UltimateSwordMagic / 2);
                stack.addEnchantment(Enchantment.unbreaking, 1 + OreSpawnMain.UltimateSwordMagic / 2);
                stack.addEnchantment(Enchantment.fireAspect, 1 + OreSpawnMain.UltimateSwordMagic / 3);
            } else {
                stack.addEnchantment(Enchantment.looting, 1 + OreSpawnMain.UltimateSwordMagic / 2);
                stack.addEnchantment(Enchantment.unbreaking, 1 + OreSpawnMain.UltimateSwordMagic / 2);
            }
        }
    }

    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
        if (this == OreSpawnMain.MyChainsaw && entityLiving != null && this.swingtimer == 0) {
            entityLiving
                .playSound("orespawn:chainsawshort", 1.0F, entityLiving.worldObj.rand.nextFloat() * 0.2F + 0.9F);
            this.swingtimer = 50;
        }

        return false;
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        if (this != OreSpawnMain.MyChainsaw) {
            int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.looting.effectId, stack);
            if (lvl <= 0) {
                if (this != OreSpawnMain.MyBattleAxe) {
                    stack.addEnchantment(Enchantment.sharpness, OreSpawnMain.UltimateSwordMagic);
                    stack.addEnchantment(Enchantment.smite, OreSpawnMain.UltimateSwordMagic);
                    stack.addEnchantment(Enchantment.baneOfArthropods, OreSpawnMain.UltimateSwordMagic);
                    stack.addEnchantment(Enchantment.knockback, 1 + OreSpawnMain.UltimateSwordMagic / 2);
                    stack.addEnchantment(Enchantment.looting, 1 + OreSpawnMain.UltimateSwordMagic / 2);
                    stack.addEnchantment(Enchantment.unbreaking, 1 + OreSpawnMain.UltimateSwordMagic / 2);
                    stack.addEnchantment(Enchantment.fireAspect, 1 + OreSpawnMain.UltimateSwordMagic / 3);
                } else {
                    stack.addEnchantment(Enchantment.looting, 1 + OreSpawnMain.UltimateSwordMagic / 2);
                    stack.addEnchantment(Enchantment.unbreaking, 1 + OreSpawnMain.UltimateSwordMagic / 2);
                }
            }
        }
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        if (this == OreSpawnMain.MyChainsaw) {
            if (this.swingtimer > 0) {
                this.swingtimer--;
            }

            if (world.isRemote && this.swingtimer > 0) {
                float f = 1.0F;
                float dx = (float) (f * Math.cos(Math.toRadians(entity.rotationYaw + 90.0F + 45.0F)));
                float dz = (float) (f * Math.sin(Math.toRadians(entity.rotationYaw + 90.0F + 45.0F)));
                if (world.rand.nextInt(8) == 0) {
                    world.spawnParticle(
                        "flame",
                        entity.posX + dx,
                        entity.posY,
                        entity.posZ + dz,
                        (world.rand.nextFloat() - world.rand.nextFloat()) / 20.0F,
                        world.rand.nextFloat() / 10.0F,
                        (world.rand.nextFloat() - world.rand.nextFloat()) / 20.0F);
                }

                if (world.rand.nextInt(2) == 0) {
                    world.spawnParticle(
                        "smoke",
                        entity.posX + dx,
                        entity.posY,
                        entity.posZ + dz,
                        (world.rand.nextFloat() - world.rand.nextFloat()) / 20.0F,
                        world.rand.nextFloat() / 10.0F,
                        (world.rand.nextFloat() - world.rand.nextFloat()) / 20.0F);
                }

                if (world.rand.nextInt(10) == 0) {
                    world.spawnParticle(
                        "fireworksSpark",
                        entity.posX + dx,
                        entity.posY,
                        entity.posZ + dz,
                        (world.rand.nextFloat() - world.rand.nextFloat()) / 20.0F,
                        world.rand.nextFloat() / 5.0F,
                        (world.rand.nextFloat() - world.rand.nextFloat()) / 20.0F);
                }
            }
        } else {
            int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.looting.effectId, stack);
            if (lvl <= 0) {
                if (this != OreSpawnMain.MyBattleAxe) {
                    stack.addEnchantment(Enchantment.sharpness, OreSpawnMain.UltimateSwordMagic);
                    stack.addEnchantment(Enchantment.smite, OreSpawnMain.UltimateSwordMagic);
                    stack.addEnchantment(Enchantment.baneOfArthropods, OreSpawnMain.UltimateSwordMagic);
                    stack.addEnchantment(Enchantment.knockback, 1 + OreSpawnMain.UltimateSwordMagic / 2);
                    stack.addEnchantment(Enchantment.looting, 1 + OreSpawnMain.UltimateSwordMagic / 2);
                    stack.addEnchantment(Enchantment.unbreaking, 1 + OreSpawnMain.UltimateSwordMagic / 2);
                    stack.addEnchantment(Enchantment.fireAspect, 1 + OreSpawnMain.UltimateSwordMagic / 3);
                } else {
                    stack.addEnchantment(Enchantment.looting, 1 + OreSpawnMain.UltimateSwordMagic / 2);
                    stack.addEnchantment(Enchantment.unbreaking, 1 + OreSpawnMain.UltimateSwordMagic / 2);
                }
            }
        }
    }

    public String getMaterialName() {
        return "Uranium/Titanium";
    }

    public boolean hitEntity(ItemStack stack, EntityLiving target, EntityLiving attacker) {
        stack.damageItem(1, attacker);
        return true;
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

        if (this == OreSpawnMain.MyChainsaw && player != null) {
            this.findSomethingToHit(player);
        }

        return false;
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

    private void findSomethingToHit(EntityPlayer player) {
        List entities = player.worldObj
            .getEntitiesWithinAABB(EntityLivingBase.class, player.boundingBox.expand(5.0, 5.0, 5.0));
        Iterator iterator = entities.iterator();
        Entity entity = null;
        EntityLivingBase living = null;

        while (iterator.hasNext()) {
            entity = (Entity) iterator.next();
            living = (EntityLivingBase) entity;
            if (this.isSuitableTarget(living, false, player)) {
                living.attackEntityFrom(DamageSource.causePlayerDamage(player), OreSpawnMain.chainsaw_stats.damage);
            }
        }
    }

    private boolean isSuitableTarget(EntityLivingBase living, boolean flag, EntityPlayer player) {
        if (living == null) {
            return false;
        } else if (living == player) {
            return false;
        } else if (!living.isEntityAlive()) {
            return false;
        } else {
            if (OreSpawnMain.ultimate_sword_pvp == 0) {
                if (living instanceof EntityPlayer || living instanceof Girlfriend || living instanceof Boyfriend) {
                    return false;
                }

                if (living instanceof EntityTameable) {
                    EntityTameable t = (EntityTameable) living;
                    if (t.isTamed()) {
                        return false;
                    }
                }
            }

            return this.MyCanSee(living, player);
        }
    }

    public boolean MyCanSee(EntityLivingBase e, EntityPlayer player) {
        int nblks = 10;
        double cx = player.posX;
        double cz = player.posZ;
        float startx = (float) cx;
        float starty = (float) (player.posY + 1.4F);
        float startz = (float) cz;
        float dx = (float) ((e.posX - startx) / 10.0);
        float dy = (float) ((e.posY + e.height / 2.0F - starty) / 10.0);
        float dz = (float) ((e.posZ - startz) / 10.0);
        if (Math.abs(dx) > 1.0) {
            dy /= Math.abs(dx);
            dz /= Math.abs(dx);
            nblks = (int) (nblks * Math.abs(dx));
            if (dx > 1.0F) {
                dx = 1.0F;
            }

            if (dx < -1.0F) {
                dx = -1.0F;
            }
        }

        if (Math.abs(dy) > 1.0) {
            dx /= Math.abs(dy);
            dz /= Math.abs(dy);
            nblks = (int) (nblks * Math.abs(dy));
            if (dy > 1.0F) {
                dy = 1.0F;
            }

            if (dy < -1.0F) {
                dy = -1.0F;
            }
        }

        if (Math.abs(dz) > 1.0) {
            dy /= Math.abs(dz);
            dx /= Math.abs(dz);
            nblks = (int) (nblks * Math.abs(dz));
            if (dz > 1.0F) {
                dz = 1.0F;
            }

            if (dz < -1.0F) {
                dz = -1.0F;
            }
        }

        for (int i = 0; i < nblks; i++) {
            startx += dx;
            starty += dy;
            startz += dz;
            Block bid = player.worldObj.getBlock((int) startx, (int) starty, (int) startz);
            if (bid != Blocks.air) {
                return false;
            }
        }

        return true;
    }

    public boolean canHarvestBlock(Block block) {
        return this.canCrush(block);
    }

    private boolean canCrush(Block blockID) {
        if (this == OreSpawnMain.MyChainsaw) {
            if (blockID == Blocks.web) {
                return true;
            } else if (blockID == Blocks.log) {
                return true;
            } else if (blockID == Blocks.leaves) {
                return true;
            } else if (blockID == Blocks.planks) {
                return true;
            } else if (blockID == Blocks.sapling) {
                return true;
            } else if (blockID == Blocks.tallgrass) {
                return true;
            } else if (blockID == Blocks.cactus) {
                return true;
            } else if (blockID == OreSpawnMain.CrystalPlanksBlock) {
                return true;
            } else if (blockID == OreSpawnMain.MyAppleLeaves) {
                return true;
            } else if (blockID == OreSpawnMain.MySkyTreeLog) {
                return true;
            } else if (blockID == OreSpawnMain.MyDT) {
                return true;
            } else if (blockID == OreSpawnMain.MyExperienceLeaves) {
                return true;
            } else if (blockID == OreSpawnMain.MyScaryLeaves) {
                return true;
            } else if (blockID == OreSpawnMain.MyCherryLeaves) {
                return true;
            } else if (blockID == OreSpawnMain.MyPeachLeaves) {
                return true;
            } else if (blockID == OreSpawnMain.MyCrystalLeaves) {
                return true;
            } else if (blockID == OreSpawnMain.MyCrystalLeaves2) {
                return true;
            } else {
                return blockID == OreSpawnMain.MyCrystalLeaves3 ? true : blockID == OreSpawnMain.MyCrystalTreeLog;
            }
        } else {
            return blockID == Blocks.web;
        }
    }

    private boolean isLeaves(Block blockID) {
        if (blockID == Blocks.web) {
            return true;
        } else if (blockID == Blocks.leaves) {
            return true;
        } else if (blockID == Blocks.sapling) {
            return true;
        } else if (blockID == Blocks.tallgrass) {
            return true;
        } else if (blockID == OreSpawnMain.MyAppleLeaves) {
            return true;
        } else if (blockID == OreSpawnMain.MyExperienceLeaves) {
            return true;
        } else if (blockID == OreSpawnMain.MyScaryLeaves) {
            return true;
        } else if (blockID == OreSpawnMain.MyCherryLeaves) {
            return true;
        } else if (blockID == OreSpawnMain.MyPeachLeaves) {
            return true;
        } else if (blockID == OreSpawnMain.MyCrystalLeaves) {
            return true;
        } else {
            return blockID == OreSpawnMain.MyCrystalLeaves2 ? true : blockID == OreSpawnMain.MyCrystalLeaves3;
        }
    }

    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z,
        EntityLivingBase entity) {
        if (this == OreSpawnMain.MyChainsaw && !world.isRemote) {
            for (int i = -5; i <= 5; i++) {
                for (int j = -5; j <= 10; j++) {
                    for (int k = -5; k <= 5; k++) {
                        Block bid = world.getBlock(x + i, y + j, z + k);
                        if (this.leaf) {
                            if (this.isLeaves(bid)) {
                                this.dropItemRand(world, Item.getItemFromBlock(bid), 1, x + i, y + j, z + k);
                                world.setBlock(x + i, y + j, z + k, Blocks.air);
                            }
                        } else if (this.canCrush(bid)) {
                            this.dropItemRand(world, Item.getItemFromBlock(bid), 1, x + i, y + j, z + k);
                            world.setBlock(x + i, y + j, z + k, Blocks.air);
                        }
                    }
                }
            }
        }

        return super.onBlockDestroyed(stack, world, block, x, y, z, entity);
    }

    private ItemStack dropItemRand(World world, Item index, int n, int x, int y, int z) {
        EntityItem entityItem = null;
        ItemStack is = new ItemStack(index, n, 0);
        entityItem = new EntityItem(
            world,
            x + OreSpawnMain.OreSpawnRand.nextInt(5) - OreSpawnMain.OreSpawnRand.nextInt(5),
            y + 1.0 + world.rand.nextInt(5),
            z + OreSpawnMain.OreSpawnRand.nextInt(5) - OreSpawnMain.OreSpawnRand.nextInt(5),
            is);
        if (entityItem != null) {
            world.spawnEntityInWorld(entityItem);
        }

        return is;
    }

    public float getStrVsBlock(ItemStack stack, Block block) {
        if (block != null && this == OreSpawnMain.MyChainsaw) {
            this.leaf = this.isLeaves(block);
            if (block.getMaterial() == Material.wood || block.getMaterial() == Material.plants
                || block.getMaterial() == Material.vine) {
                return OreSpawnMain.chainsaw_stats.efficiency;
            }

            if (this.canCrush(block)) {
                return OreSpawnMain.chainsaw_stats.efficiency;
            }
        }

        return 2.0F;
    }
}
