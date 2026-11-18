package danger.orespawn.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityLavaLovingItem extends EntityItem {

    public EntityLavaLovingItem(World world, double x, double y, double z, ItemStack stack) {
        super(world, x, y, z, stack);
        this.fireResistance = 300;
        this.isImmuneToFire = true;
        this.hurtResistantTime = 300;
    }

    public void noFire() {
        this.fireResistance = 300;
        this.isImmuneToFire = true;
        this.hurtResistantTime = 300;
    }

    public void yesFire() {
        this.fireResistance = 0;
        this.isImmuneToFire = false;
        this.hurtResistantTime = 0;
    }

    protected void dealFireDamage(float amount) {
        if (!this.isImmuneToFire) {
            this.attackEntityFrom(DamageSource.inFire, amount);
        }
    }
}
