package danger.orespawn.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class CrystalFurnace extends BlockContainer {

    private final Random furnaceRand = new Random();
    private final boolean isActive;
    private static boolean keepFurnaceInventory;
    @SideOnly(Side.CLIENT)
    private IIcon furnaceIconTop;
    @SideOnly(Side.CLIENT)
    private IIcon furnaceIconFront;

    public CrystalFurnace(int n, boolean isActive2, float f1, float f2) {
        super(Material.rock);
        this.isActive = isActive2;
        if (!isActive2) {
            this.setCreativeTab(CreativeTabs.tabDecorations);
        } else {
            this.setLightLevel(0.6F);
        }

        this.setHardness(f1);
        this.setResistance(f2);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public Item getItemDropped(int meta, Random rand, int fortune) {
        return Item.getItemFromBlock(OreSpawnMain.CrystalFurnaceBlock);
    }

    @SideOnly(Side.CLIENT)
    public Item idPicked(World world, int x, int y, int z) {
        return Item.getItemFromBlock(OreSpawnMain.CrystalFurnaceBlock);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return side == 1 ? this.furnaceIconTop
            : (side == 0 ? this.furnaceIconTop : (side != meta ? this.blockIcon : this.furnaceIconFront));
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5) + "_side");
        this.furnaceIconFront = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5) + (this.isActive ? "_front_off" : "_front_off"));
        this.furnaceIconTop = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5) + "_top");
    }

    public static void updateFurnaceBlockState(boolean flag, World world, int x, int y, int z) {
        int l = world.getBlockMetadata(x, y, z);
        TileEntity tileentity = world.getTileEntity(x, y, z);
        keepFurnaceInventory = true;
        if (flag) {
            world.setBlock(x, y, z, OreSpawnMain.CrystalFurnaceOnBlock);
        } else {
            world.setBlock(x, y, z, OreSpawnMain.CrystalFurnaceBlock);
        }

        keepFurnaceInventory = false;
        world.setBlockMetadataWithNotify(x, y, z, l, 2);
        if (tileentity != null) {
            tileentity.validate();
            world.setTileEntity(x, y, z, tileentity);
        }
    }

    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        this.setDefaultDirection(world, x, y, z);
    }

    private void setDefaultDirection(World world, int x, int y, int z) {
        if (!world.isRemote) {
            Block l = world.getBlock(x, y, z - 1);
            Block i1 = world.getBlock(x, y, z + 1);
            Block j1 = world.getBlock(x - 1, y, z);
            Block k1 = world.getBlock(x + 1, y, z);
            byte b0 = 3;
            if (l.func_149730_j() && !i1.func_149730_j()) {
                b0 = 3;
            }

            if (i1.func_149730_j() && !l.func_149730_j()) {
                b0 = 2;
            }

            if (j1.func_149730_j() && !k1.func_149730_j()) {
                b0 = 5;
            }

            if (k1.func_149730_j() && !j1.func_149730_j()) {
                b0 = 4;
            }

            world.setBlockMetadataWithNotify(x, y, z, b0, 2);
        }
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
        float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        } else {
            TileEntityCrystalFurnace tileentitycrystalfurnace = (TileEntityCrystalFurnace) world.getTileEntity(x, y, z);
            if (tileentitycrystalfurnace != null) {
                player.openGui(OreSpawnMain.instance, 0, world, x, y, z);
            }

            return true;
        }
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (this.isActive) {
            int l = world.getBlockMetadata(x, y, z);
            float f = x + 0.5F;
            float f1 = y + 0.0F + rand.nextFloat() * 6.0F / 16.0F;
            float f2 = z + 0.5F;
            float f3 = rand.nextFloat() * 0.6F - 0.3F;
            float f4 = rand.nextFloat() * 0.6F - 0.3F;
            if (l == 4) {
                world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0, 0.0, 0.0);
                world.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0, 0.0, 0.0);
            } else if (l == 5) {
                world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0, 0.0, 0.0);
                world.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0, 0.0, 0.0);
            } else if (l == 2) {
                world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0, 0.0, 0.0);
                world.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0, 0.0, 0.0);
            } else if (l == 3) {
                world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0, 0.0, 0.0);
                world.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0, 0.0, 0.0);
            }
        }
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        int l = MathHelper.floor_double(placer.rotationYaw * 4.0F / 360.0F + 0.5) & 3;
        if (l == 0) {
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }

        if (l == 1) {
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }

        if (l == 2) {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }

        if (l == 3) {
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }

        if (stack.hasDisplayName()) {
            ((TileEntityCrystalFurnace) world.getTileEntity(x, y, z)).func_145951_a(stack.getDisplayName());
        }
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        if (!keepFurnaceInventory) {
            TileEntityCrystalFurnace tileentitycrystalfurnace = (TileEntityCrystalFurnace) world.getTileEntity(x, y, z);
            if (tileentitycrystalfurnace != null) {
                for (int j1 = 0; j1 < tileentitycrystalfurnace.getSizeInventory(); j1++) {
                    ItemStack itemstack = tileentitycrystalfurnace.getStackInSlot(j1);
                    if (itemstack != null) {
                        float f = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                        float f1 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                        float f2 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0) {
                            int k1 = this.furnaceRand.nextInt(21) + 10;
                            if (k1 > itemstack.stackSize) {
                                k1 = itemstack.stackSize;
                            }

                            itemstack.stackSize -= k1;
                            EntityItem entityitem = new EntityItem(
                                world,
                                x + f,
                                y + f1,
                                z + f2,
                                new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
                            if (itemstack.hasTagCompound()) {
                                entityitem.getEntityItem()
                                    .setTagCompound(
                                        (NBTTagCompound) itemstack.getTagCompound()
                                            .copy());
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (float) this.furnaceRand.nextGaussian() * f3;
                            entityitem.motionY = (float) this.furnaceRand.nextGaussian() * f3 + 0.2F;
                            entityitem.motionZ = (float) this.furnaceRand.nextGaussian() * f3;
                            world.spawnEntityInWorld(entityitem);
                        }
                    }
                }

                world.func_147453_f(x, y, z, block);
            }
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public int getComparatorInputOverride(World world, int x, int y, int z, int n) {
        return Container.calcRedstoneFromInventory((IInventory) world.getTileEntity(x, y, z));
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityCrystalFurnace();
    }
}
