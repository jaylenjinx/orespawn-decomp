package danger.orespawn.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Items;
import net.minecraft.world.World;

public class RedCow extends EntityCow {

    public RedCow(World world) {
        super(world);
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        int n = this.rand.nextInt(3) + this.rand.nextInt(1 + lootingLevel);

        for (int i = 0; i < n; i++) {
            this.dropItem(Items.apple, 1);
        }

        super.dropFewItems(wasRecentlyHit, lootingLevel);
    }

    public EntityCow createChild(EntityAgeable entityageable) {
        return this.spawnBabyAnimal(entityageable);
    }

    public RedCow spawnBabyAnimal(EntityAgeable entityAgeable) {
        return new RedCow(this.worldObj);
    }

    protected void updateAITick() {
        if (this.worldObj.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
        }

        super.updateAITick();
    }

    protected boolean canDespawn() {
        return false;
    }
}
