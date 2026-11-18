package danger.orespawn.entity.ai;

import java.util.Collections;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityTameable;

import danger.orespawn.entity.Girlfriend;

public class MyEntityAINearestAttackableTarget extends MyEntityAITarget {

    EntityLiving targetEntity;
    Class targetClass;
    int targetChance;
    private final IEntitySelector targetEntitySelector;
    private MyEntityAINearestAttackableTargetSorter theNearestAttackableTargetSorter;

    public MyEntityAINearestAttackableTarget(EntityLiving living, Class clazz, float f, int n, boolean flag) {
        this(living, clazz, f, n, flag, false);
    }

    public MyEntityAINearestAttackableTarget(EntityLiving living, Class clazz, float f, int n, boolean flag,
        boolean flag2) {
        this(living, clazz, f, n, flag, flag2, (IEntitySelector) null);
    }

    public MyEntityAINearestAttackableTarget(EntityLiving living, Class targetClass2, float targetDistance,
        int targetChance2, boolean flag, boolean flag2, IEntitySelector iEntitySelector) {
        super(living, targetDistance, flag, flag2);
        this.targetClass = targetClass2;
        this.targetDistance = targetDistance;
        this.targetChance = targetChance2;
        this.theNearestAttackableTargetSorter = new MyEntityAINearestAttackableTargetSorter(this, living);
        this.targetEntitySelector = iEntitySelector;
        this.setMutexBits(1);
    }

    public boolean shouldExecute() {
        if (this.taskOwner instanceof EntityTameable && !((EntityTameable) this.taskOwner).isTamed()) {
            return false;
        } else if (this.taskOwner instanceof Girlfriend && !((Girlfriend) this.taskOwner).isTamed()) {
            return false;
        } else if (this.taskOwner instanceof Girlfriend && ((Girlfriend) this.taskOwner).isSitting()) {
            return false;
        } else if (this.targetChance > 0 && this.taskOwner.getRNG()
            .nextInt(100) > this.targetChance) {
                return false;
            } else {
                List list = this.taskOwner.worldObj.selectEntitiesWithinAABB(
                    this.targetClass,
                    this.taskOwner.boundingBox.expand(this.targetDistance, 4.0, this.targetDistance),
                    this.targetEntitySelector);
                Collections.sort(list, this.theNearestAttackableTargetSorter);

                for (Entity entity : (List<Entity>) list) {
                    EntityLiving targetEntity2 = (EntityLiving) entity;
                    if (this.isSuitableTarget(targetEntity2, false)) {
                        this.targetEntity = targetEntity2;
                        return true;
                    }
                }

                this.targetEntity = null;
                return false;
            }
    }

    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }
}
