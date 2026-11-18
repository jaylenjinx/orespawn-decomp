package danger.orespawn.client.render;

import net.minecraft.entity.Entity;

import danger.orespawn.entity.projectile.Shoes;

public class RenderShoe extends RenderSpinner {

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        if (entity instanceof Shoes) {
            Shoes shoes = (Shoes) entity;
            this.spinItemIconIndex = shoes.getShoeId();
        }

        super.doRender(entity, x, y, z, yaw, partialTicks);
    }
}
