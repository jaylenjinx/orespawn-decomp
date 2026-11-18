package danger.orespawn;

import net.minecraft.entity.Entity;

public class RenderItemUrchin extends RenderSpinner {

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        if (!(par1Entity instanceof BerthaHit)) {
            if (par1Entity instanceof SunspotUrchin) {
                SunspotUrchin var2x = (SunspotUrchin) par1Entity;
                this.spinItemIconIndex = var2x.getUrchinIndex();
            }

            if (par1Entity instanceof WaterBall) {
                WaterBall var2x = (WaterBall) par1Entity;
                this.spinItemIconIndex = var2x.getWaterBallIndex();
            }

            if (par1Entity instanceof InkSack) {
                InkSack var2x = (InkSack) par1Entity;
                this.spinItemIconIndex = var2x.getInkSackIndex();
            }

            if (par1Entity instanceof LaserBall) {
                LaserBall var2x = (LaserBall) par1Entity;
                this.spinItemIconIndex = var2x.getLaserBallIndex();
            }

            if (par1Entity instanceof IceBall) {
                IceBall var2x = (IceBall) par1Entity;
                this.spinItemIconIndex = var2x.getIceBallIndex();
            }

            if (par1Entity instanceof Acid) {
                Acid var2x = (Acid) par1Entity;
                this.spinItemIconIndex = var2x.getAcidIndex();
            }

            if (par1Entity instanceof DeadIrukandji) {
                DeadIrukandji var2x = (DeadIrukandji) par1Entity;
                this.spinItemIconIndex = var2x.getIrukandjiIndex();
            }

            super.doRender(par1Entity, par2, par4, par6, par8, par9);
        }
    }
}
