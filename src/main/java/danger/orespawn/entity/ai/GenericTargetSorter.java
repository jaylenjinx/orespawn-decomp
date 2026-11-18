package danger.orespawn.entity.ai;

import java.util.Comparator;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;

public class GenericTargetSorter implements Comparator {

    private Entity theEntity;

    public GenericTargetSorter(Entity entity) {
        this.theEntity = entity;
    }

    public int compareDistanceSq(Entity entity2, Entity entity) {
        double weight = 0.0;
        double distanceSq = this.theEntity.getDistanceSqToEntity(entity2);
        if (entity2 instanceof EntityCreeper) {
            distanceSq /= 2.0;
        }

        weight = entity2.height * entity2.width;
        if (weight > 1.0) {
            distanceSq /= weight;
        }

        double distanceSq2 = this.theEntity.getDistanceSqToEntity(entity);
        if (entity instanceof EntityCreeper) {
            distanceSq2 /= 2.0;
        }

        weight = entity.height * entity.width;
        if (weight > 1.0) {
            distanceSq2 /= weight;
        }

        return distanceSq < distanceSq2 ? -1 : (distanceSq > distanceSq2 ? 1 : 0);
    }

    @Override
    public int compare(Object a, Object b) {
        return this.compareDistanceSq((Entity) a, (Entity) b);
    }
}
