package danger.orespawn.client.render;

import net.minecraft.entity.Entity;

import danger.orespawn.entity.projectile.EntityCage;

public class RenderCage extends RenderSpinner {

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.spinItemIconIndex = 160;
        if (entity instanceof EntityCage) {
            EntityCage entityCage = (EntityCage) entity;
            this.spinItemIconIndex = entityCage.getCageIndex();
        }

        super.doRender(entity, x, y, z, yaw, partialTicks);
    }
}
