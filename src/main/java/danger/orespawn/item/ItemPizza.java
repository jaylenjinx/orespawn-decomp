package danger.orespawn.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPizza extends Item {

    private Block spawnID;

    public ItemPizza(int n, Block block) {
        this.spawnID = block;
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        Block i1 = world.getBlock(x, y, z);
        if (i1 == Blocks.snow && (world.getBlockMetadata(x, y, z) & 7) < 1) {
            side = 1;
        } else if (i1 != Blocks.vine && i1 != Blocks.tallgrass && i1 != Blocks.deadbush) {
            if (side == 0) {
                y--;
            }

            if (side == 1) {
                y++;
            }

            if (side == 2) {
                z--;
            }

            if (side == 3) {
                z++;
            }

            if (side == 4) {
                x--;
            }

            if (side == 5) {
                x++;
            }
        }

        if (!player.canPlayerEdit(x, y, z, side, stack)) {
            return false;
        } else if (stack.stackSize == 0) {
            return false;
        } else {
            if (world.canPlaceEntityOnSide(this.spawnID, x, y, z, false, side, (Entity) null, stack)) {
                int j1 = this.spawnID.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, 0);
                if (world.setBlock(x, y, z, this.spawnID, j1, 3)) {
                    if (world.getBlock(x, y, z) == this.spawnID) {
                        this.spawnID.onBlockPlacedBy(world, x, y, z, player, stack);
                        this.spawnID.onPostBlockPlaced(world, x, y, z, j1);
                    }

                    world.playSoundEffect(
                        x + 0.5F,
                        y + 0.5F,
                        z + 0.5F,
                        this.spawnID.stepSound.getStepResourcePath(),
                        (this.spawnID.stepSound.getVolume() + 1.0F) / 2.0F,
                        this.spawnID.stepSound.getPitch() * 0.8F);
                    stack.stackSize--;
                }
            }

            return true;
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
