package danger.orespawn.block;

import java.util.Random;

import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class BlockStrawberry extends BlockCrops {

    private IIcon[] iconArray;

    public BlockStrawberry(int n) {}

    public IIcon getIcon(int side, int meta) {
        if (meta < 7) {
            if (meta >= 6) {
                meta = 4;
            }

            return this.iconArray[meta >> 1];
        } else {
            return this.iconArray[3];
        }
    }

    public int quantityDropped(Random rand) {
        return 1 + rand.nextInt(5);
    }

    protected Item func_149866_i() {
        return OreSpawnMain.MyStrawberrySeed;
    }

    protected Item func_149865_P() {
        return OreSpawnMain.MyStrawberry;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.iconArray = new IIcon[4];

        for (int i = 0; i < this.iconArray.length; i++) {
            this.iconArray[i] = iconRegister.registerIcon("OreSpawn:strawberry_" + i);
        }
    }
}
