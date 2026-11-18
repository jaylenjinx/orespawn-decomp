package danger.orespawn.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class ItemSifter extends Item {

    public ItemSifter(int i) {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setMaxDamage(600);
    }

    private void dropItemRand(Item index, int n, World world, int x, int y, int z) {
        EntityItem entityItem = new EntityItem(
            world,
            x + OreSpawnMain.OreSpawnRand.nextInt(2) - OreSpawnMain.OreSpawnRand.nextInt(2) + 0.5,
            y + 1.1,
            z + OreSpawnMain.OreSpawnRand.nextInt(2) - OreSpawnMain.OreSpawnRand.nextInt(2) + 0.5,
            new ItemStack(index, n, 0));
        world.spawnEntityInWorld(entityItem);
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        } else {
            Block bid = world.getBlock(x, y, z);
            Block bid2 = world.getBlock(x, y + 1, z);
            if (bid2 == Blocks.flowing_water) {
                bid = Blocks.water;
            }

            if (bid2 == Blocks.water) {
                bid = Blocks.water;
            }

            if (bid == Blocks.water) {
                int i = world.rand.nextInt(160);
                switch (i) {
                    case 0:
                        this.dropItemRand(Items.fish, 1, world, x, y, z);
                        break;
                    case 1:
                        this.dropItemRand(OreSpawnMain.MyGreenFish, 1, world, x, y, z);
                        break;
                    case 2:
                        this.dropItemRand(OreSpawnMain.MyBlueFish, 1, world, x, y, z);
                        break;
                    case 3:
                        this.dropItemRand(OreSpawnMain.MyPinkFish, 1, world, x, y, z);
                        break;
                    case 4:
                        this.dropItemRand(OreSpawnMain.MyRockFish, 1, world, x, y, z);
                        break;
                    case 5:
                        this.dropItemRand(OreSpawnMain.MyWoodFish, 1, world, x, y, z);
                        break;
                    case 6:
                        this.dropItemRand(OreSpawnMain.MyGreyFish, 1, world, x, y, z);
                        break;
                    case 7:
                        this.dropItemRand(Items.glass_bottle, 1, world, x, y, z);
                        break;
                    case 8:
                        this.dropItemRand(Items.iron_ingot, 1, world, x, y, z);
                        break;
                    case 9:
                        this.dropItemRand(Items.gold_nugget, 1, world, x, y, z);
                        break;
                    case 10:
                        this.dropItemRand(OreSpawnMain.MyItemShoes, 1, world, x, y, z);
                        break;
                    case 11:
                        this.dropItemRand(OreSpawnMain.MyItemShoes_1, 1, world, x, y, z);
                        break;
                    case 12:
                        this.dropItemRand(OreSpawnMain.MyItemShoes_2, 1, world, x, y, z);
                        break;
                    case 13:
                        this.dropItemRand(OreSpawnMain.MyItemShoes_3, 1, world, x, y, z);
                        break;
                    case 14:
                        this.dropItemRand(Items.glass_bottle, 1, world, x, y, z);
                        break;
                    case 15:
                        this.dropItemRand(Items.bone, 1, world, x, y, z);
                        break;
                    case 16:
                        this.dropItemRand(Item.getItemFromBlock(Blocks.stone), 1, world, x, y, z);
                        break;
                    case 17:
                        this.dropItemRand(Items.bucket, 1, world, x, y, z);
                        break;
                    case 18:
                        this.dropItemRand(Items.water_bucket, 1, world, x, y, z);
                        break;
                    case 19:
                        if (world.rand.nextInt(3) == 1) {
                            this.dropItemRand(Items.emerald, 1, world, x, y, z);
                        } else {
                            this.dropItemRand(Item.getItemFromBlock(Blocks.gravel), 1, world, x, y, z);
                        }
                        break;
                    case 20:
                        if (world.rand.nextInt(3) == 1) {
                            this.dropItemRand(OreSpawnMain.MyRuby, 1, world, x, y, z);
                        } else {
                            this.dropItemRand(Item.getItemFromBlock(Blocks.gravel), 1, world, x, y, z);
                        }
                        break;
                    case 21:
                        if (world.rand.nextInt(3) == 1) {
                            this.dropItemRand(OreSpawnMain.MyAmethyst, 1, world, x, y, z);
                        } else {
                            this.dropItemRand(Item.getItemFromBlock(Blocks.gravel), 1, world, x, y, z);
                        }
                        break;
                    case 22:
                        this.dropItemRand(OreSpawnMain.MyMothScale, 1, world, x, y, z);
                        break;
                    case 23:
                        this.dropItemRand(OreSpawnMain.UraniumNugget, 1, world, x, y, z);
                        break;
                    case 24:
                        this.dropItemRand(OreSpawnMain.TitaniumNugget, 1, world, x, y, z);
                        break;
                    case 25:
                        if (world.rand.nextInt(2) == 1) {
                            this.dropItemRand(Items.diamond, 1, world, x, y, z);
                        } else {
                            this.dropItemRand(Item.getItemFromBlock(Blocks.gravel), 1, world, x, y, z);
                        }
                        break;
                    case 26:
                        this.dropItemRand(Items.iron_ingot, 1, world, x, y, z);
                        break;
                    case 27:
                        this.dropItemRand(Items.gold_nugget, 1, world, x, y, z);
                        break;
                    case 28:
                        this.dropItemRand(Items.redstone, 1, world, x, y, z);
                        break;
                    case 29:
                        this.dropItemRand(Items.coal, 1, world, x, y, z);
                        break;
                    case 30:
                        this.dropItemRand(OreSpawnMain.MyItemShoes, 1, world, x, y, z);
                        break;
                    case 31:
                        this.dropItemRand(OreSpawnMain.MyItemShoes_1, 1, world, x, y, z);
                        break;
                    case 32:
                        this.dropItemRand(OreSpawnMain.MyItemShoes_2, 1, world, x, y, z);
                        break;
                    case 33:
                        this.dropItemRand(OreSpawnMain.MyItemShoes_3, 1, world, x, y, z);
                        break;
                    case 34:
                        this.dropItemRand(Items.fish, 1, world, x, y, z);
                        break;
                    case 35:
                        this.dropItemRand(Items.glass_bottle, 1, world, x, y, z);
                        break;
                    case 36:
                        this.dropItemRand(Items.bone, 1, world, x, y, z);
                        break;
                    case 37:
                        this.dropItemRand(Item.getItemFromBlock(Blocks.stone), 1, world, x, y, z);
                        break;
                    case 38:
                        this.dropItemRand(Item.getItemFromBlock(Blocks.stone_button), 1, world, x, y, z);
                        break;
                    case 39:
                        this.dropItemRand(Items.bucket, 1, world, x, y, z);
                        break;
                    case 40:
                        this.dropItemRand(Items.water_bucket, 1, world, x, y, z);
                }
            }

            if (bid == Blocks.sand) {
                int i = world.rand.nextInt(60);
                switch (i) {
                    case 0:
                        this.dropItemRand(Items.iron_horse_armor, 1, world, x, y, z);
                        break;
                    case 1:
                        this.dropItemRand(Items.shears, 1, world, x, y, z);
                        break;
                    case 2:
                        this.dropItemRand(Items.carrot_on_a_stick, 1, world, x, y, z);
                        break;
                    case 3:
                        this.dropItemRand(Items.poisonous_potato, 1, world, x, y, z);
                        break;
                    case 4:
                        this.dropItemRand(Items.item_frame, 1, world, x, y, z);
                        break;
                    case 5:
                        this.dropItemRand(Items.bone, 1, world, x, y, z);
                        break;
                    case 6:
                        this.dropItemRand(Items.compass, 1, world, x, y, z);
                        break;
                    case 7:
                        this.dropItemRand(Items.glass_bottle, 1, world, x, y, z);
                        break;
                    case 8:
                        this.dropItemRand(Items.saddle, 1, world, x, y, z);
                        break;
                    case 9:
                        this.dropItemRand(Items.iron_helmet, 1, world, x, y, z);
                        break;
                    case 10:
                        this.dropItemRand(Items.iron_chestplate, 1, world, x, y, z);
                        break;
                    case 11:
                        this.dropItemRand(Items.iron_leggings, 1, world, x, y, z);
                        break;
                    case 12:
                        this.dropItemRand(Items.iron_boots, 1, world, x, y, z);
                        break;
                    case 13:
                        this.dropItemRand(Item.getItemFromBlock(Blocks.sand), 1, world, x, y, z);
                }
            }

            if (bid == Blocks.gravel) {
                int i = world.rand.nextInt(60);
                switch (i) {
                    case 0:
                        this.dropItemRand(Items.flint, 1, world, x, y, z);
                        break;
                    case 1:
                        this.dropItemRand(OreSpawnMain.MySalt, 1, world, x, y, z);
                        break;
                    case 2:
                        this.dropItemRand(Items.flint_and_steel, 1, world, x, y, z);
                        break;
                    case 3:
                        this.dropItemRand(Items.spider_eye, 1, world, x, y, z);
                        break;
                    case 4:
                        this.dropItemRand(Items.item_frame, 1, world, x, y, z);
                        break;
                    case 5:
                        this.dropItemRand(Items.feather, 1, world, x, y, z);
                        break;
                    case 6:
                        this.dropItemRand(Items.string, 1, world, x, y, z);
                        break;
                    case 7:
                        this.dropItemRand(Items.glass_bottle, 1, world, x, y, z);
                        break;
                    case 8:
                        this.dropItemRand(Items.lead, 1, world, x, y, z);
                        break;
                    case 9:
                        this.dropItemRand(Items.name_tag, 1, world, x, y, z);
                        break;
                    case 10:
                        this.dropItemRand(Item.getItemFromBlock(Blocks.sand), 1, world, x, y, z);
                        break;
                    case 11:
                        this.dropItemRand(Item.getItemFromBlock(Blocks.gravel), 1, world, x, y, z);
                }
            }

            if (bid == Blocks.dirt) {
                int i = world.rand.nextInt(60);
                switch (i) {
                    case 0:
                        this.dropItemRand(Items.string, 1, world, x, y, z);
                        break;
                    case 1:
                        this.dropItemRand(OreSpawnMain.MySalt, 1, world, x, y, z);
                        break;
                    case 2:
                        this.dropItemRand(Items.shears, 1, world, x, y, z);
                        break;
                    case 3:
                        this.dropItemRand(Items.stick, 1, world, x, y, z);
                        break;
                    case 4:
                        this.dropItemRand(Items.bowl, 1, world, x, y, z);
                        break;
                    case 5:
                        this.dropItemRand(Items.flower_pot, 1, world, x, y, z);
                        break;
                    case 6:
                        this.dropItemRand(Items.sign, 1, world, x, y, z);
                        break;
                    case 7:
                        this.dropItemRand(Items.brick, 1, world, x, y, z);
                        break;
                    case 8:
                        this.dropItemRand(Items.paper, 1, world, x, y, z);
                        break;
                    case 9:
                        this.dropItemRand(Items.bone, 1, world, x, y, z);
                        break;
                    case 10:
                        this.dropItemRand(Items.glass_bottle, 1, world, x, y, z);
                        break;
                    case 11:
                        this.dropItemRand(Item.getItemFromBlock(Blocks.sand), 1, world, x, y, z);
                        break;
                    case 12:
                        this.dropItemRand(Item.getItemFromBlock(Blocks.gravel), 1, world, x, y, z);
                        break;
                    case 13:
                        this.dropItemRand(Item.getItemFromBlock(Blocks.dirt), 1, world, x, y, z);
                }
            }

            if (bid == Blocks.grass) {
                int i = world.rand.nextInt(60);
                switch (i) {
                    case 0:
                        this.dropItemRand(Item.getItemFromBlock(Blocks.yellow_flower), 1, world, x, y, z);
                        break;
                    case 1:
                        this.dropItemRand(Item.getItemFromBlock(Blocks.red_flower), 1, world, x, y, z);
                        break;
                    case 2:
                        this.dropItemRand(Item.getItemFromBlock(OreSpawnMain.MyFlowerPinkBlock), 1, world, x, y, z);
                        break;
                    case 3:
                        this.dropItemRand(Item.getItemFromBlock(OreSpawnMain.MyFlowerBlueBlock), 1, world, x, y, z);
                        break;
                    case 4:
                        this.dropItemRand(Item.getItemFromBlock(OreSpawnMain.MyFlowerBlackBlock), 1, world, x, y, z);
                        break;
                    case 5:
                        this.dropItemRand(Item.getItemFromBlock(OreSpawnMain.MyFlowerScaryBlock), 1, world, x, y, z);
                    case 6:
                        this.dropItemRand(Items.wheat, 1, world, x, y, z);
                        break;
                    case 7:
                        this.dropItemRand(Items.pumpkin_seeds, 1, world, x, y, z);
                        break;
                    case 8:
                        this.dropItemRand(Items.melon_seeds, 1, world, x, y, z);
                        break;
                    case 9:
                        this.dropItemRand(Items.carrot, 1, world, x, y, z);
                        break;
                    case 10:
                        this.dropItemRand(Items.potato, 1, world, x, y, z);
                        break;
                    case 11:
                        this.dropItemRand(Item.getItemFromBlock(Blocks.deadbush), 1, world, x, y, z);
                        break;
                    case 12:
                        this.dropItemRand(Item.getItemFromBlock(Blocks.gravel), 1, world, x, y, z);
                        break;
                    case 13:
                        this.dropItemRand(Item.getItemFromBlock(Blocks.dirt), 1, world, x, y, z);
                        break;
                    case 14:
                        this.dropItemRand(Item.getItemFromBlock(Blocks.grass), 1, world, x, y, z);
                }
            }

            stack.damageItem(1, player);
            return true;
        }
    }

    public String getMaterialName() {
        return "Unknown";
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
