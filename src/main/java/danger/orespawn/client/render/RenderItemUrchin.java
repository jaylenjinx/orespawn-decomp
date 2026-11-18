package danger.orespawn.client.render;

import net.minecraft.entity.Entity;

import danger.orespawn.entity.projectile.Acid;
import danger.orespawn.entity.projectile.BerthaHit;
import danger.orespawn.entity.projectile.DeadIrukandji;
import danger.orespawn.entity.projectile.IceBall;
import danger.orespawn.entity.projectile.InkSack;
import danger.orespawn.entity.projectile.LaserBall;
import danger.orespawn.entity.projectile.SunspotUrchin;
import danger.orespawn.entity.projectile.WaterBall;

public class RenderItemUrchin extends RenderSpinner {

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        if (!(entity instanceof BerthaHit)) {
            if (entity instanceof SunspotUrchin) {
                SunspotUrchin sunspotUrchin = (SunspotUrchin) entity;
                this.spinItemIconIndex = sunspotUrchin.getUrchinIndex();
            }

            if (entity instanceof WaterBall) {
                WaterBall waterBall = (WaterBall) entity;
                this.spinItemIconIndex = waterBall.getWaterBallIndex();
            }

            if (entity instanceof InkSack) {
                InkSack inkSack = (InkSack) entity;
                this.spinItemIconIndex = inkSack.getInkSackIndex();
            }

            if (entity instanceof LaserBall) {
                LaserBall laserBall = (LaserBall) entity;
                this.spinItemIconIndex = laserBall.getLaserBallIndex();
            }

            if (entity instanceof IceBall) {
                IceBall iceBall = (IceBall) entity;
                this.spinItemIconIndex = iceBall.getIceBallIndex();
            }

            if (entity instanceof Acid) {
                Acid acid = (Acid) entity;
                this.spinItemIconIndex = acid.getAcidIndex();
            }

            if (entity instanceof DeadIrukandji) {
                DeadIrukandji deadIrukandji = (DeadIrukandji) entity;
                this.spinItemIconIndex = deadIrukandji.getIrukandjiIndex();
            }

            super.doRender(entity, x, y, z, yaw, partialTicks);
        }
    }
}
