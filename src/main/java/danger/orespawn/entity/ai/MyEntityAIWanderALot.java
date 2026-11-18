package danger.orespawn.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.Vec3;

public class MyEntityAIWanderALot extends EntityAIBase {

    private EntityCreature entity;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private double speed;
    private int xzRange = 10;
    private int busy = 0;

    public MyEntityAIWanderALot(EntityCreature creature, int xzRange2, double speed2) {
        this.entity = creature;
        this.xzRange = xzRange2;
        this.speed = speed2;
        this.setMutexBits(1);
    }

    public void setBusy(int i) {
        this.busy = i;
    }

    public boolean shouldExecute() {
        if (this.busy != 0) {
            return false;
        } else if (this.entity.worldObj.rand.nextInt(30) != 0) {
            return false;
        } else if (this.entity instanceof EntityTameable && ((EntityTameable) this.entity).isSitting()) {
            return false;
        } else {
            Vec3 randomTarget = RandomPositionGenerator.findRandomTarget(this.entity, this.xzRange, 7);
            if (randomTarget == null) {
                return false;
            } else {
                this.xPosition = randomTarget.xCoord;
                this.yPosition = randomTarget.yCoord;
                this.zPosition = randomTarget.zCoord;
                return true;
            }
        }
    }

    public boolean continueExecuting() {
        return !this.entity.getNavigator()
            .noPath();
    }

    public void startExecuting() {
        this.entity.getNavigator()
            .tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }
}
