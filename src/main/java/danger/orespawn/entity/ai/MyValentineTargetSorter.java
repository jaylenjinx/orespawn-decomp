package danger.orespawn.entity.ai;

import java.util.Comparator;

import net.minecraft.entity.Entity;

public class MyValentineTargetSorter implements Comparator {

    private Entity theEntity;
    final MyValentineTarget parent;

    public MyValentineTargetSorter(MyValentineTarget entityAINearestAttackableTarget, Entity entity) {
        this.parent = entityAINearestAttackableTarget;
        this.theEntity = entity;
    }

    public int compareDistanceSq(Entity entity2, Entity entity) {
        double distanceSq = this.theEntity.getDistanceSqToEntity(entity2);
        double distanceSq2 = this.theEntity.getDistanceSqToEntity(entity);
        return distanceSq < distanceSq2 ? -1 : (distanceSq > distanceSq2 ? 1 : 0);
    }

    @Override
    public int compare(Object a, Object b) {
        return this.compareDistanceSq((Entity) a, (Entity) b);
    }
}
