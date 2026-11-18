package danger.orespawn.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.Vec3;

public class MyEntityAIWander extends EntityAIBase {

    private EntityCreature entity;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private float speed;

    public MyEntityAIWander(EntityCreature creature, float speed2) {
        this.entity = creature;
        this.speed = speed2;
        this.setMutexBits(1);
    }

    public boolean shouldExecute() {
        if (this.entity.getRNG()
            .nextInt(90) != 0) {
            return false;
        } else if (this.entity instanceof EntityTameable && ((EntityTameable) this.entity).isSitting()) {
            return false;
        } else {
            Vec3 randomTarget = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);
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
        if (this.entity != null && this.entity instanceof EntityTameable) {
            EntityTameable gf = (EntityTameable) this.entity;
            EntityLivingBase owner = gf.getOwner();
            if (owner != null && (int) gf.posZ == (int) owner.posZ
                && (int) gf.posX == (int) owner.posX
                && (int) gf.posY < (int) owner.posY + 2
                && (int) gf.posY > (int) owner.posY - 2) {
                return false;
            }
        }

        return !this.entity.getNavigator()
            .noPath();
    }

    public void startExecuting() {
        this.entity.getNavigator()
            .tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }
}
