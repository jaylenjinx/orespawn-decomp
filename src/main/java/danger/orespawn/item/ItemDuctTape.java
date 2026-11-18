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

public class ItemDuctTape extends Item {

    private Block field_150935_a;

    public ItemDuctTape(int n, Block block) {
        this.field_150935_a = block;
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        Block block = world.getBlock(x, y, z);
        if (block == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1) {
            side = 1;
        } else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush) {
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
            if (world.canPlaceEntityOnSide(this.field_150935_a, x, y, z, false, side, (Entity) null, stack)) {
                int i1 = this.field_150935_a.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, 0);
                if (world.setBlock(x, y, z, this.field_150935_a, i1, 3)) {
                    if (world.getBlock(x, y, z) == this.field_150935_a) {
                        this.field_150935_a.onBlockPlacedBy(world, x, y, z, player, stack);
                        this.field_150935_a.onPostBlockPlaced(world, x, y, z, i1);
                    }

                    world.playSoundEffect(
                        x + 0.5F,
                        y + 0.5F,
                        z + 0.5F,
                        this.field_150935_a.stepSound.func_150496_b(),
                        (this.field_150935_a.stepSound.getVolume() + 1.0F) / 2.0F,
                        this.field_150935_a.stepSound.getPitch() * 0.8F);
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
