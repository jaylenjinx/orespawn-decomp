package danger.orespawn.client.render;

import net.minecraft.entity.Entity;

import danger.orespawn.entity.projectile.EntityCage;

public class RenderCage extends RenderSpinner {

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.spinItemIconIndex = 160;
        if (par1Entity instanceof EntityCage) {
            EntityCage var2x = (EntityCage) par1Entity;
            this.spinItemIconIndex = var2x.getCageIndex();
        }

        super.doRender(par1Entity, par2, par4, par6, par8, par9);
    }
}
