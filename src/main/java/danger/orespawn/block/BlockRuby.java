package danger.orespawn.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class BlockRuby extends Block {

    public BlockRuby(int n) {
        super(Material.rock);
        this.setHardness(4.0F);
        this.setResistance(4.0F);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setLightLevel(0.4F);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return true;
    }

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (this == OreSpawnMain.MyBlockMobzillaScaleBlock && entity instanceof EntityLivingBase) {
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.damageBoost.id, 200, 0));
        }
    }

    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        if (this == OreSpawnMain.MyBlockMobzillaScaleBlock && entity instanceof EntityLivingBase) {
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.damageBoost.id, 200, 0));
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
