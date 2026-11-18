package danger.orespawn.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class BlockTomato extends BlockReed {

    private int myMaxHeight = 0;

    public BlockTomato(int n) {
        float f = 0.375F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
        this.setTickRandomly(true);
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        Block bid = world.getBlock(x, y - 1, z);
        return bid == Blocks.air ? false
            : bid == OreSpawnMain.MyTomatoPlant1 || bid == OreSpawnMain.MyTomatoPlant2
                || bid == OreSpawnMain.MyTomatoPlant3
                || bid == OreSpawnMain.MyTomatoPlant4
                || bid == Blocks.grass
                || bid == Blocks.dirt
                || bid == Blocks.farmland;
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        int Height = 1;
        int dontGrow = 0;
        if (!world.isRemote) {
            if (this == OreSpawnMain.MyTomatoPlant1 || this == OreSpawnMain.MyTomatoPlant2) {
                int meta = world.getBlockMetadata(x, y, z);
                this.myMaxHeight = meta >> 8;
                meta &= 255;
                if (this.myMaxHeight == 0) {
                    this.myMaxHeight = 3 + OreSpawnMain.OreSpawnRand.nextInt(3);
                }

                Block bid = world.getBlock(x, y + 1, z);
                if (bid == Blocks.air) {
                    for (int i = 1; i < 10; i++) {
                        bid = world.getBlock(x, y - i, z);
                        if (bid != OreSpawnMain.MyTomatoPlant1 && bid != OreSpawnMain.MyTomatoPlant2
                            && bid != OreSpawnMain.MyTomatoPlant3
                            && bid != OreSpawnMain.MyTomatoPlant4) {
                            break;
                        }

                        Height++;
                        if (bid == OreSpawnMain.MyTomatoPlant3 || bid == OreSpawnMain.MyTomatoPlant4) {
                            dontGrow = 1;
                        }
                    }

                    if (dontGrow != 0) {
                        this.myMaxHeight = Height;
                    }

                    if (meta >= 5 - this.myMaxHeight / 3) {
                        if (Height < this.myMaxHeight) {
                            world.setBlock(x, y + 1, z, OreSpawnMain.MyTomatoPlant1, this.myMaxHeight << 8, 2);
                            world.setBlock(x, y, z, OreSpawnMain.MyTomatoPlant2, this.myMaxHeight << 8, 2);
                        } else {
                            for (int i = 0; i < this.myMaxHeight; i++) {
                                bid = world.getBlock(x, y - i, z);
                                if (bid == OreSpawnMain.MyTomatoPlant2) {
                                    world.setBlock(x, y - i, z, OreSpawnMain.MyTomatoPlant3, this.myMaxHeight << 8, 2);
                                } else if (bid == OreSpawnMain.MyTomatoPlant3) {
                                    world.setBlock(x, y - i, z, OreSpawnMain.MyTomatoPlant4, this.myMaxHeight << 8, 2);
                                }
                            }

                            bid = world.getBlock(x, y, z);
                            world.setBlock(x, y, z, bid, this.myMaxHeight << 8, 2);
                        }
                    } else {
                        bid = world.getBlock(x, y, z);
                        world.setBlock(x, y, z, bid, this.myMaxHeight << 8 | meta + 1, 2);
                    }
                }
            }
        }
    }

    public Item getItemDropped(int meta, Random rand, int fortune) {
        return OreSpawnMain.MyTomato;
    }

    public int quantityDropped(Random rand) {
        return this == OreSpawnMain.MyTomatoPlant4 ? 2 + rand.nextInt(4) : 0;
    }

    public Item itemPicked(World world, int x, int y, int z) {
        return OreSpawnMain.MyTomato;
    }

    protected Item getSeedItem() {
        return OreSpawnMain.MyTomato;
    }

    protected Item getCropItem() {
        return OreSpawnMain.MyTomato;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
