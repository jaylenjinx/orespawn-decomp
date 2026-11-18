package danger.orespawn.entity.ai;

import java.util.Comparator;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;

public class MyEntityAINearestAttackableTargetSorter implements Comparator {

    private Entity theEntity;
    final MyEntityAINearestAttackableTarget parent;

    public MyEntityAINearestAttackableTargetSorter(MyEntityAINearestAttackableTarget entityAINearestAttackableTarget,
        Entity entity) {
        this.parent = entityAINearestAttackableTarget;
        this.theEntity = entity;
    }

    public int compareDistanceSq(Entity entity2, Entity entity) {
        double distanceSq = this.theEntity.getDistanceSqToEntity(entity2);
        if (entity2 instanceof EntityCreeper) {
            distanceSq /= 2.0;
        }

        double distanceSq2 = this.theEntity.getDistanceSqToEntity(entity);
        if (entity instanceof EntityCreeper) {
            distanceSq2 /= 2.0;
        }

        return distanceSq < distanceSq2 ? -1 : (distanceSq > distanceSq2 ? 1 : 0);
    }

    @Override
    public int compare(Object a, Object b) {
        return this.compareDistanceSq((Entity) a, (Entity) b);
    }
}
