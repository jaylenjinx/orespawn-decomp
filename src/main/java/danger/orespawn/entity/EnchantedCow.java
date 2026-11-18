package danger.orespawn.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EnchantedCow extends RedCow {

    public EnchantedCow(World world) {
        super(world);
    }

    private void dropEnchantedGoldenApple() {
        EntityItem entityItem = new EntityItem(
            this.worldObj,
            this.posX,
            this.posY + 1.0,
            this.posZ,
            new ItemStack(Items.golden_apple, 1, 1));
        this.worldObj.spawnEntityInWorld(entityItem);
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        int n = this.rand.nextInt(4) + this.rand.nextInt(1 + lootingLevel);

        for (int i = 0; i < n; i++) {
            this.dropItem(Items.apple, 1);
        }

        this.dropItem(Items.golden_apple, 2);
        this.dropEnchantedGoldenApple();
        super.dropFewItems(wasRecentlyHit, lootingLevel);
    }

    @Override
    public EntityCow createChild(EntityAgeable entityageable) {
        return this.spawnBabyAnimal(entityageable);
    }

    public EnchantedCow spawnBabyAnimal(EntityAgeable entityAgeable) {
        return new EnchantedCow(this.worldObj);
    }
}
