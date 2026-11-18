package danger.orespawn;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMinersDream extends Item {

    public ItemMinersDream(int i) {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer Player, World world, int cposx, int cposy, int cposz,
        int par7, float par8, float par9, float par10) {
        int deltax = 0;
        int deltaz = 0;
        int dirx = 0;
        int dirz = 0;
        int height = 5;
        int width = 5;
        int length = 64;
        int torches = 5;
        int solid_count = 0;
        if (cposx < 0) {
            dirx = -1;
        }

        if (cposz < 0) {
            dirz = -1;
        }

        int pposx = (int) (Player.posX + 0.99 * dirx);
        int pposy = (int) Player.posY;
        int pposz = (int) (Player.posZ + 0.99 * dirz);
        if (cposx - pposx != 0 && cposz - pposz != 0) {
            return false;
        } else {
            int x = cposx;
            int y = pposy;
            int z = cposz;
            if (cposx - pposx < 0) {
                deltax = -1;
            }

            if (cposx - pposx > 0) {
                deltax = 1;
            }

            if (cposz - pposz < 0) {
                deltaz = -1;
            }

            if (cposz - pposz > 0) {
                deltaz = 1;
            }

            if (deltax == 0 && deltaz == 0) {
                return false;
            } else if (deltax != 0 && deltaz != 0) {
                return false;
            } else {
                Player.worldObj.playSoundAtEntity(Player, "random.explode", 1.0F, 1.5F);
                if (world.isRemote) {
                    return true;
                } else {
                    for (int i = 0; i < height; i++) {
                        for (int k = 0; k < length; k++) {
                            solid_count = 0;

                            for (int j = -width; j <= width; j++) {
                                Block bid = world
                                    .getBlock(x + k * deltax + j * deltaz, y + i, z + k * deltaz + j * deltax);
                                if (bid == Blocks.stone || bid == Blocks.dirt
                                    || bid == Blocks.gravel
                                    || bid == Blocks.flowing_water
                                    || bid == Blocks.water
                                    || bid == Blocks.flowing_lava
                                    || bid == Blocks.lava
                                    || bid == Blocks.netherrack
                                    || bid == Blocks.end_stone
                                    || bid == OreSpawnMain.CrystalStone) {
                                    world.setBlock(
                                        x + k * deltax + j * deltaz,
                                        y + i,
                                        z + k * deltaz + j * deltax,
                                        Blocks.air,
                                        0,
                                        2);
                                }

                                if (i == height - 1) {
                                    bid = world
                                        .getBlock(x + k * deltax + j * deltaz, y + i + 1, z + k * deltaz + j * deltax);
                                    if (bid != Blocks.air) {
                                        solid_count++;
                                    }

                                    if (bid == Blocks.air || bid == Blocks.gravel
                                        || bid == Blocks.sand
                                        || bid == Blocks.flowing_water
                                        || bid == Blocks.water
                                        || bid == Blocks.flowing_lava
                                        || bid == Blocks.lava) {
                                        if (world.provider.dimensionId == OreSpawnMain.DimensionID5) {
                                            world.setBlock(
                                                x + k * deltax + j * deltaz,
                                                y + i + 1,
                                                z + k * deltaz + j * deltax,
                                                OreSpawnMain.CrystalStone,
                                                0,
                                                2);
                                        } else {
                                            world.setBlock(
                                                x + k * deltax + j * deltaz,
                                                y + i + 1,
                                                z + k * deltaz + j * deltax,
                                                Blocks.cobblestone,
                                                0,
                                                2);
                                        }
                                    }
                                }
                            }

                            if (i == height - 1 && solid_count == 0) {
                                for (int var30 = -width; var30 <= width; var30++) {
                                    world.setBlock(
                                        x + k * deltax + var30 * deltaz,
                                        y + i + 1,
                                        z + k * deltaz + var30 * deltax,
                                        Blocks.air,
                                        0,
                                        2);
                                }
                            }
                        }
                    }

                    for (int k = 0; k < length; k += torches) {
                        Block bidx = world.getBlock(x + k * deltax, y - 1, z + k * deltaz);
                        if ((bidx == Blocks.stone || bidx == Blocks.dirt
                            || bidx == Blocks.gravel
                            || bidx == Blocks.netherrack
                            || bidx == Blocks.end_stone
                            || bidx == Blocks.bedrock) && world.isAirBlock(x + k * deltax, y, z + k * deltaz)) {
                            world.setBlock(x + k * deltax, y, z + k * deltaz, OreSpawnMain.ExtremeTorch, 0, 2);
                        }

                        if (bidx == OreSpawnMain.CrystalStone && world.isAirBlock(x + k * deltax, y, z + k * deltaz)) {
                            world.setBlock(x + k * deltax, y, z + k * deltaz, OreSpawnMain.CrystalTorch, 0, 2);
                        }
                    }

                    if (!Player.capabilities.isCreativeMode) {
                        par1ItemStack.stackSize--;
                    }

                    return true;
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
