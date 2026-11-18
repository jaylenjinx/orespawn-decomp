package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class ItemSunFish extends ItemFood {

    public ItemSunFish(int n, int n2, float f, boolean flag) {
        super(n2, f, flag);
        this.setAlwaysEdible();
    }

    public void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        super.onFoodEaten(stack, world, player);
        if (!world.isRemote && this == OreSpawnMain.MySunFish) {
            player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 0));
        }

        if (!world.isRemote && this == OreSpawnMain.MyButterCandy) {
            player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 2000, 0));
            player.addPotionEffect(new PotionEffect(Potion.jump.id, 2000, 0));
        }

        if (!world.isRemote && this == OreSpawnMain.MyBacon) {
            player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 2000, 0));
            player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 2000, 0));
        }

        if (!world.isRemote && this == OreSpawnMain.MyCrystalApple) {
            player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 3000, 0));
            player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 3000, 0));
        }

        if (!world.isRemote && this == OreSpawnMain.MyLove) {
            player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 6000, 3));
            player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 6000, 2));
            player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 2));
            player.addPotionEffect(new PotionEffect(Potion.resistance.id, 6000, 1));
            player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 5000, 0));
            player.addPotionEffect(new PotionEffect(Potion.jump.id, 5000, 0));
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
