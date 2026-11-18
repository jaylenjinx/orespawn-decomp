package danger.orespawn.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.Girlfriend;

public class MyEntityAIFollowOwner extends EntityAIBase {

    private EntityTameable thePet;
    private EntityLivingBase theOwner;
    World theWorld;
    private float field_75336_f;
    private PathNavigate petPathfinder;
    private int field_75343_h;
    float maxDist;
    float minDist;
    private boolean field_75344_i;

    public MyEntityAIFollowOwner(EntityTameable pet, float f, float maxDist2, float minDist2) {
        this.thePet = pet;
        this.theWorld = pet.worldObj;
        this.field_75336_f = f;
        this.petPathfinder = pet.getNavigator();
        this.minDist = minDist2;
        this.maxDist = maxDist2;
        this.setMutexBits(3);
    }

    public boolean shouldExecute() {
        EntityLivingBase owner = this.thePet.getOwner();
        if (owner == null) {
            return false;
        } else {
            this.theOwner = owner;
            if (this.thePet.isSitting()) {
                return false;
            } else if (this.thePet instanceof Girlfriend && OreSpawnMain.valentines_day != 0) {
                return false;
            } else {
                return this.thePet != null && (this.thePet.posY < 60.0 || !this.thePet.worldObj.isDaytime())
                    && this.thePet.getDistanceSqToEntity(owner) > this.maxDist / 2.0F * (this.maxDist / 2.0F) ? true
                        : !(this.thePet.getDistanceSqToEntity(owner) < this.maxDist * this.maxDist);
            }
        }
    }

    public boolean continueExecuting() {
        if (this.thePet.isSitting()) {
            return false;
        } else if (this.petPathfinder.noPath()) {
            return false;
        } else {
            if (this.thePet != null && this.thePet instanceof EntityTameable) {
                EntityTameable gf = this.thePet;
                EntityLivingBase owner = gf.getOwner();
                if (owner != null && (int) gf.posZ == (int) owner.posZ
                    && (int) gf.posX == (int) owner.posX
                    && (int) gf.posY < (int) owner.posY + 2
                    && (int) gf.posY > (int) owner.posY - 2) {
                    return false;
                }
            }

            return this.thePet.getDistanceSqToEntity(this.theOwner) > this.minDist * this.minDist;
        }
    }

    public void startExecuting() {
        this.field_75343_h = 0;
        this.field_75344_i = this.thePet.getNavigator()
            .getAvoidsWater();
        this.thePet.getNavigator()
            .setAvoidsWater(false);
    }

    public void resetTask() {
        this.theOwner = null;
        this.petPathfinder.clearPathEntity();
        this.thePet.getNavigator()
            .setAvoidsWater(this.field_75344_i);
    }

    public void updateTask() {
        this.thePet.getLookHelper()
            .setLookPositionWithEntity(this.theOwner, 10.0F, this.thePet.getVerticalFaceSpeed());
        if (!this.thePet.isSitting() && --this.field_75343_h <= 0) {
            this.field_75343_h = 10;
            if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.field_75336_f)
                && this.thePet.getDistanceSqToEntity(this.theOwner) >= 144.0) {
                int x = MathHelper.floor_double(this.theOwner.posX) - 2;
                int z = MathHelper.floor_double(this.theOwner.posZ) - 2;
                int minY = MathHelper.floor_double(this.theOwner.boundingBox.minY);

                for (int i = 0; i <= 4; i++) {
                    for (int j = 0; j <= 4; j++) {
                        if ((i < 1 || j < 1 || i > 3 || j > 3)
                            && World.doesBlockHaveSolidTopSurface(this.theWorld, x + i, minY - 1, z + j)
                            && !this.theWorld.getBlock(x + i, minY, z + j)
                                .isNormalCube()
                            && !this.theWorld.getBlock(x + i, minY + 1, z + j)
                                .isNormalCube()) {
                            this.thePet.setLocationAndAngles(
                                x + i + 0.5F,
                                minY,
                                z + j + 0.5F,
                                this.thePet.rotationYaw,
                                this.thePet.rotationPitch);
                            this.petPathfinder.clearPathEntity();
                            return;
                        }
                    }
                }
            }
        }
    }
}
