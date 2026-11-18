package danger.orespawn.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Items;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;

public class CrystalCow extends RedCow {

    public CrystalCow(World world) {
        super(world);
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        int n = this.rand.nextInt(3) + this.rand.nextInt(1 + lootingLevel);

        for (int i = 0; i < n; i++) {
            this.dropItem(OreSpawnMain.MyCrystalApple, 1);
        }

        this.dropItem(Items.apple, 1);
        super.dropFewItems(wasRecentlyHit, lootingLevel);
    }

    @Override
    public EntityCow createChild(EntityAgeable entityageable) {
        return this.spawnBabyAnimal(entityageable);
    }

    public CrystalCow spawnBabyAnimal(EntityAgeable entityAgeable) {
        return new CrystalCow(this.worldObj);
    }
}
