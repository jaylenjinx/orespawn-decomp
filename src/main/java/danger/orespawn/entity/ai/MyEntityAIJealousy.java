package danger.orespawn.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;

import danger.orespawn.entity.Boyfriend;
import danger.orespawn.entity.Girlfriend;

public class MyEntityAIJealousy extends MyEntityAINearestAttackableTarget {

    private EntityTameable theTameable;

    public MyEntityAIJealousy(EntityTameable pet, Class clazz, float f, int n, boolean flag) {
        super(pet, clazz, f, n, flag);
        this.theTameable = pet;
    }

    @Override
    public boolean shouldExecute() {
        EntityTameable te = (EntityTameable) this.taskOwner;
        Girlfriend gf = null;
        Boyfriend bf = null;
        EntityLivingBase ep = null;
        if (te == null) {
            return false;
        } else if (!te.isTamed()) {
            return false;
        } else if (te.isSitting()) {
            return false;
        } else if (!super.shouldExecute()) {
            return false;
        } else {
            Entity victim = this.targetEntity;
            if (victim == null) {
                return false;
            } else {
                if (te instanceof Girlfriend) {
                    if (victim instanceof Girlfriend) {
                        gf = (Girlfriend) victim;
                        if (gf.isTamed()) {
                            return false;
                        }
                    }
                } else if (victim instanceof Boyfriend) {
                    bf = (Boyfriend) victim;
                    if (bf.isTamed()) {
                        return false;
                    }
                }

                ep = te.getOwner();
                return ep != null;
            }
        }
    }
}
