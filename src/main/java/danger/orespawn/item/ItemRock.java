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
import danger.orespawn.entity.RockBase;
import danger.orespawn.entity.projectile.EntityThrownRock;

public class ItemRock extends Item {

    public ItemRock(int i) {
        this.maxStackSize = 64;
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!player.capabilities.isCreativeMode) {
            stack.stackSize--;
        }

        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) {
            if (this == OreSpawnMain.MySmallRock) {
                world.spawnEntityInWorld(new EntityThrownRock(world, player, 1));
            }

            if (this == OreSpawnMain.MyRock) {
                world.spawnEntityInWorld(new EntityThrownRock(world, player, 2));
            }

            if (this == OreSpawnMain.MyRedRock) {
                world.spawnEntityInWorld(new EntityThrownRock(world, player, 3));
            }

            if (this == OreSpawnMain.MyGreenRock) {
                world.spawnEntityInWorld(new EntityThrownRock(world, player, 4));
            }

            if (this == OreSpawnMain.MyBlueRock) {
                world.spawnEntityInWorld(new EntityThrownRock(world, player, 5));
            }

            if (this == OreSpawnMain.MyPurpleRock) {
                world.spawnEntityInWorld(new EntityThrownRock(world, player, 6));
            }

            if (this == OreSpawnMain.MySpikeyRock) {
                world.spawnEntityInWorld(new EntityThrownRock(world, player, 7));
            }

            if (this == OreSpawnMain.MyTNTRock) {
                world.spawnEntityInWorld(new EntityThrownRock(world, player, 8));
            }

            if (this == OreSpawnMain.MyCrystalRedRock) {
                world.spawnEntityInWorld(new EntityThrownRock(world, player, 9));
            }

            if (this == OreSpawnMain.MyCrystalGreenRock) {
                world.spawnEntityInWorld(new EntityThrownRock(world, player, 10));
            }

            if (this == OreSpawnMain.MyCrystalBlueRock) {
                world.spawnEntityInWorld(new EntityThrownRock(world, player, 11));
            }

            if (this == OreSpawnMain.MyCrystalTNTRock) {
                world.spawnEntityInWorld(new EntityThrownRock(world, player, 12));
            }
        }

        return stack;
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (x < 0) {
                x++;
            }

            if (z < 0) {
                z++;
            }

            Entity e = this.spawnCreature(world, "Rock", x, y + 1.01, z);
            if (e != null) {
                RockBase r = (RockBase) e;
                if (this == OreSpawnMain.MySmallRock) {
                    r.placeRock(1);
                }

                if (this == OreSpawnMain.MyRock) {
                    r.placeRock(2);
                }

                if (this == OreSpawnMain.MyRedRock) {
                    r.placeRock(3);
                }

                if (this == OreSpawnMain.MyGreenRock) {
                    r.placeRock(4);
                }

                if (this == OreSpawnMain.MyBlueRock) {
                    r.placeRock(5);
                }

                if (this == OreSpawnMain.MyPurpleRock) {
                    r.placeRock(6);
                }

                if (this == OreSpawnMain.MySpikeyRock) {
                    r.placeRock(7);
                }

                if (this == OreSpawnMain.MyTNTRock) {
                    r.placeRock(8);
                }

                if (this == OreSpawnMain.MyCrystalRedRock) {
                    r.placeRock(9);
                }

                if (this == OreSpawnMain.MyCrystalGreenRock) {
                    r.placeRock(10);
                }

                if (this == OreSpawnMain.MyCrystalBlueRock) {
                    r.placeRock(11);
                }

                if (this == OreSpawnMain.MyCrystalTNTRock) {
                    r.placeRock(12);
                }
            }
        }

        if (!player.capabilities.isCreativeMode) {
            stack.stackSize--;
        }

        return true;
    }

    private Entity spawnCreature(World world, String str, double x, double y, double z) {
        Entity entity = null;
        entity = EntityList.createEntityByName(str, world);
        if (entity != null) {
            if (x > 0.0) {
                x += 0.5;
            }

            if (x < 0.0) {
                x -= 0.5;
            }

            if (z > 0.0) {
                z += 0.5;
            }

            if (z < 0.0) {
                z -= 0.5;
            }

            entity.setLocationAndAngles(x, y + 0.01, z, world.rand.nextFloat() * 360.0F, 0.0F);
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
