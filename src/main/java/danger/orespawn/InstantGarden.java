package danger.orespawn;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class InstantGarden extends Item {

    public InstantGarden(int i) {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer Player, World world, int cposx, int cposy, int cposz,
        int par7, float par8, float par9, float par10) {
        int deltax = 0;
        int deltaz = 0;
        int bid = 0;
        int dirx = 0;
        int dirz = 0;
        int height = 10;
        int width = 7;
        int length = 18;
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
                            for (int j = -width; j <= width; j++) {
                                world.setBlock(
                                    x + k * deltax + j * deltaz,
                                    y + i,
                                    z + k * deltaz + j * deltax,
                                    Blocks.air,
                                    0,
                                    2);
                                if (i == 0) {
                                    world.setBlock(
                                        x + k * deltax + j * deltaz,
                                        y + i - 1,
                                        z + k * deltaz + j * deltax,
                                        Blocks.grass,
                                        0,
                                        2);
                                }
                            }
                        }
                    }

                    for (int k = 1; k < length - 1; k++) {
                        int var28 = 0;

                        for (int jx = -width; jx <= width; jx++) {
                            if (var28 == 1) {
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y - 1,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.farmland,
                                    0,
                                    2);
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y,
                                    z + k * deltaz + jx * deltax,
                                    OreSpawnMain.MyRadishPlant,
                                    0,
                                    2);
                            }

                            if (var28 == 2) {
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y - 1,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.farmland,
                                    0,
                                    2);
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y,
                                    z + k * deltaz + jx * deltax,
                                    OreSpawnMain.MyLettucePlant1,
                                    0,
                                    2);
                            }

                            if (var28 == 3) {
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y - 1,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.farmland,
                                    0,
                                    2);
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.carrots,
                                    0,
                                    2);
                            }

                            if (var28 == 4) {
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y - 1,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.water,
                                    0,
                                    2);
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y - 2,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.cobblestone,
                                    0,
                                    2);
                            }

                            if (var28 == 5) {
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y - 1,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.farmland,
                                    0,
                                    2);
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.potatoes,
                                    0,
                                    2);
                            }

                            if (var28 == 6) {
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y - 1,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.farmland,
                                    0,
                                    2);
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.wheat,
                                    0,
                                    2);
                            }

                            if (var28 == 7) {
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y - 1,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.farmland,
                                    0,
                                    2);
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y,
                                    z + k * deltaz + jx * deltax,
                                    OreSpawnMain.MyTomatoPlant1,
                                    0,
                                    2);
                            }

                            if (var28 == 8) {
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y - 1,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.water,
                                    0,
                                    2);
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y - 2,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.cobblestone,
                                    0,
                                    2);
                            }

                            if (var28 == 9) {
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y - 1,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.farmland,
                                    0,
                                    2);
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y,
                                    z + k * deltaz + jx * deltax,
                                    OreSpawnMain.MyCornPlant1,
                                    0,
                                    2);
                            }

                            if (var28 == 10) {
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y - 1,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.farmland,
                                    0,
                                    2);
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y,
                                    z + k * deltaz + jx * deltax,
                                    OreSpawnMain.MyStrawberryPlant,
                                    0,
                                    2);
                            }

                            if (var28 == 11) {
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y - 2,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.cobblestone,
                                    0,
                                    2);
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y - 1,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.sand,
                                    0,
                                    2);
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.reeds,
                                    0,
                                    2);
                            }

                            if (var28 == 12) {
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y - 1,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.water,
                                    0,
                                    2);
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y - 2,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.cobblestone,
                                    0,
                                    2);
                            }

                            if (var28 == 13) {
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y - 1,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.farmland,
                                    0,
                                    2);
                                world.setBlock(
                                    x + k * deltax + jx * deltaz,
                                    y,
                                    z + k * deltaz + jx * deltax,
                                    Blocks.melon_stem,
                                    0,
                                    2);
                            }

                            var28++;
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
