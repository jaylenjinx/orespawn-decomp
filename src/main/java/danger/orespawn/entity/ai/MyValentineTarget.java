package danger.orespawn.entity.ai;

import java.util.Collections;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.Girlfriend;

public class MyValentineTarget extends MyEntityAITarget {

    EntityLivingBase targetEntity;
    EntityLivingBase Me;
    Class targetClass;
    int targetChance;
    private final IEntitySelector targetEntitySelector;
    private MyValentineTargetSorter theNearestAttackableTargetSorter;

    public MyValentineTarget(EntityLiving living, Class clazz, float f, int n, boolean flag) {
        this(living, clazz, f, n, flag, false);
        this.Me = living;
    }

    public MyValentineTarget(EntityLiving living, Class clazz, float f, int n, boolean flag, boolean flag2) {
        this(living, clazz, f, n, flag, flag2, (IEntitySelector) null);
        this.Me = living;
    }

    public MyValentineTarget(EntityLiving living, Class targetClass2, float targetDistance, int targetChance2,
        boolean flag, boolean flag2, IEntitySelector iEntitySelector) {
        super(living, targetDistance, flag, flag2);
        this.targetClass = targetClass2;
        this.targetDistance = targetDistance;
        this.targetChance = targetChance2;
        this.theNearestAttackableTargetSorter = new MyValentineTargetSorter(this, living);
        this.targetEntitySelector = iEntitySelector;
        this.setMutexBits(1);
        this.Me = living;
    }

    public boolean shouldExecute() {
        if (OreSpawnMain.valentines_day == 0) {
            return false;
        } else {
            if (this.Me != null && this.Me instanceof Girlfriend) {
                Girlfriend gf = (Girlfriend) this.Me;
                if (gf.feelingBetter != 0) {
                    return false;
                }
            }

            if (this.targetChance > 0 && this.taskOwner.getRNG()
                .nextInt(100) > this.targetChance) {
                return false;
            } else {
                List list = this.taskOwner.worldObj.selectEntitiesWithinAABB(
                    this.targetClass,
                    this.taskOwner.boundingBox.expand(this.targetDistance, 4.0, this.targetDistance),
                    this.targetEntitySelector);
                Collections.sort(list, this.theNearestAttackableTargetSorter);

                for (Entity entity : (List<Entity>) list) {
                    if (entity instanceof EntityLivingBase) {
                        EntityLivingBase targetEntity2 = (EntityLivingBase) entity;
                        if (this.isSuitableTarget(targetEntity2, false)) {
                            this.targetEntity = targetEntity2;
                            return true;
                        }
                    }
                }

                this.targetEntity = null;
                return false;
            }
        }
    }

    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }
}
