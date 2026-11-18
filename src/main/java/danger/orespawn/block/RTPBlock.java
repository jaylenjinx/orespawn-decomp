package danger.orespawn.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RTPBlock extends Block {

    public RTPBlock(int i) {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public void onEntityWalking(World world, int x2, int y2, int z2, Entity entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) entity;
            EntityPlayerMP mp = null;
            if (entity instanceof EntityPlayerMP) {
                mp = (EntityPlayerMP) entity;
            }

            int x = x2;
            int y = y2;
            int z = z2;
            int found = 0;

            for (int tries = 0; tries < 1000 && found == 0; tries++) {
                if (world.rand.nextInt(2) == 0) {
                    x = x2 + 16 + world.rand.nextInt(8) - world.rand.nextInt(8);
                } else {
                    x = x2 - 16 + world.rand.nextInt(8) - world.rand.nextInt(8);
                }

                if (world.rand.nextInt(2) == 0) {
                    z = z2 + 16 + world.rand.nextInt(8) - world.rand.nextInt(8);
                } else {
                    z = z2 - 16 + world.rand.nextInt(8) - world.rand.nextInt(8);
                }

                for (y = y2 - 4; y <= y2 + 4; y++) {
                    if (world.getBlock(x, y - 1, z)
                        .getMaterial()
                        .isSolid() && world.getBlock(x, y, z) == Blocks.air
                        && world.getBlock(x, y + 1, z) == Blocks.air) {
                        found = 1;
                        break;
                    }
                }
            }

            if (found != 0) {
                if (mp != null) {
                    mp.playerNetServerHandler.setPlayerLocation(x + 0.5F, y, z + 0.5F, p.rotationYaw, 0.0F);
                } else {
                    p.setLocationAndAngles(x + 0.5F, y, z + 0.5F, p.rotationYaw, 0.0F);
                }

                for (int i = 0; i < 6; i++) {
                    world.spawnParticle("smoke", x + 0.5F, y + 2.25F, z + 0.5F, 0.0, 0.0, 0.0);
                    world.spawnParticle("explode", x + 0.5F, y + 2.25F, z + 0.5F, 0.0, 0.0, 0.0);
                    world.spawnParticle("reddust", x + 0.5F, y + 2.25F, z + 0.5F, 0.0, 0.0, 0.0);
                }

                p.worldObj.playSoundAtEntity(p, "random.explode", 1.0F, 1.5F);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
