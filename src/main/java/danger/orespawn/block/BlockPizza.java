package danger.orespawn.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class BlockPizza extends Block {

    @SideOnly(Side.CLIENT)
    private IIcon pizzaTopIcon;
    @SideOnly(Side.CLIENT)
    private IIcon pizzaBottomIcon;
    @SideOnly(Side.CLIENT)
    private IIcon field_94382_c;

    public BlockPizza(int n) {
        super(Material.cake);
        this.setTickRandomly(true);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
        int l = blockAccess.getBlockMetadata(x, y, z);
        float f = 0.0625F;
        float f1 = (1 + l * 2) / 16.0F;
        float f2 = 0.25F;
        this.setBlockBounds(f1, 0.0F, f, 1.0F - f, f2, 1.0F - f);
    }

    public void setBlockBoundsForItemRender() {
        float f = 0.0625F;
        float f1 = 0.25F;
        this.setBlockBounds(f, 0.0F, f, 1.0F - f, f1, 1.0F - f);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        int l = world.getBlockMetadata(x, y, z);
        float f = 0.0625F;
        float f1 = (1 + l * 2) / 16.0F;
        float f2 = 0.25F;
        return AxisAlignedBB.getBoundingBox(x + f1, y, z + f, x + 1 - f, y + f2 - f, z + 1 - f);
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        int l = world.getBlockMetadata(x, y, z);
        float f = 0.0625F;
        float f1 = (1 + l * 2) / 16.0F;
        float f2 = 0.25F;
        return AxisAlignedBB.getBoundingBox(x + f1, y, z + f, x + 1 - f, y + f2, z + 1 - f);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return side == 1 ? this.pizzaTopIcon
            : (side == 0 ? this.pizzaBottomIcon : (meta > 0 && side == 4 ? this.field_94382_c : this.blockIcon));
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5) + "_side");
        this.field_94382_c = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5) + "_inner");
        this.pizzaTopIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5) + "_top");
        this.pizzaBottomIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5) + "_bottom");
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
        float hitY, float hitZ) {
        this.eatPizzaSlice(world, x, y, z, player);
        return true;
    }

    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
        this.eatPizzaSlice(world, x, y, z, player);
    }

    private void eatPizzaSlice(World world, int x, int y, int z, EntityPlayer player) {
        if (player.canEat(false)) {
            player.getFoodStats()
                .addStats(4, 0.2F);
            int l = world.getBlockMetadata(x, y, z) + 1;
            if (l >= 6) {
                world.setBlockToAir(x, y, z);
            } else {
                world.setBlockMetadataWithNotify(x, y, z, l, 2);
            }
        }
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return !super.canPlaceBlockAt(world, x, y, z) ? false : this.canBlockStay(world, x, y, z);
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, int neighbor) {
        if (!this.canBlockStay(world, x, y, z)) {
            world.setBlockToAir(x, y, z);
        }
    }

    public boolean canBlockStay(World world, int x, int y, int z) {
        return world.getBlock(x, y - 1, z)
            .isNormalCube();
    }

    public int quantityDropped(Random rand) {
        return 0;
    }

    public Item getItemDropped(int meta, Random rand, int fortune) {
        return OreSpawnMain.MyPizzaItem;
    }
}
