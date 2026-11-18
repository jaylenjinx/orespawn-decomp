package danger.orespawn.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.entity.EntityAnt;

public class OreSalt extends Block {

    public OreSalt(int n) {
        super(Material.rock);
        this.setHardness(5.0F);
        this.setResistance(2.0F);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof EntityAnt) {
            entity.attackEntityFrom(DamageSource.cactus, 5.0F);
        }
    }

    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof EntityAnt) {
            entity.attackEntityFrom(DamageSource.cactus, 5.0F);
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
