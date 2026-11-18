package danger.orespawn.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;

public class EntityMosquito extends EntityAmbientCreature {

    private ChunkCoordinates currentFlightTarget = null;

    public EntityMosquito(World world) {
        super(world);
        this.setSize(0.2F, 0.2F);
        this.getNavigator()
            .setAvoidsWater(true);
        this.experienceValue = 5;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(0.1F);
        this.getAttributeMap()
            .registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(0.0);
    }

    protected void entityInit() {
        super.entityInit();
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    protected float getSoundPitch() {
        return 1.5F;
    }

    protected String getLivingSound() {
        return "orespawn:mosquito";
    }

    protected String getHurtSound() {
        return null;
    }

    protected String getDeathSound() {
        return null;
    }

    public boolean canBePushed() {
        return false;
    }

    protected void collideWithEntity(Entity entity) {}

    protected void collideWithNearbyEntities() {}

    public int mygetMaxHealth() {
        return 2;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onUpdate() {
        super.onUpdate();
        this.motionY *= 0.6F;
    }

    protected void updateAITasks() {
        int keep_trying = 50;
        if (!this.isDead) {
            super.updateAITasks();
            if (this.currentFlightTarget == null) {
                this.currentFlightTarget = new ChunkCoordinates((int) this.posX, (int) this.posY, (int) this.posZ);
            }

            if (this.rand.nextInt(20) == 0
                || this.currentFlightTarget.getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ)
                    < 3.0F) {
                EntityPlayer target = null;
                if (OreSpawnMain.OreSpawnRand.nextInt(4) == 0) {
                    target = (EntityPlayer) this.worldObj.findNearestEntityWithinAABB(
                        EntityPlayer.class,
                        this.boundingBox.expand(10.0, 6.0, 10.0),
                        this);
                    if (target != null) {
                        this.currentFlightTarget.set((int) target.posX, (int) target.posY + 2, (int) target.posZ);
                    } else {
                        for (Block bid = Blocks.stone; bid != Blocks.air && keep_trying != 0; keep_trying--) {
                            this.currentFlightTarget.set(
                                (int) this.posX + this.rand.nextInt(6) - this.rand.nextInt(6),
                                (int) this.posY + this.rand.nextInt(6) - 2,
                                (int) this.posZ + this.rand.nextInt(6) - this.rand.nextInt(6));
                            bid = this.worldObj.getBlock(
                                this.currentFlightTarget.posX,
                                this.currentFlightTarget.posY,
                                this.currentFlightTarget.posZ);
                        }
                    }
                } else {
                    for (Block bid = Blocks.stone; bid != Blocks.air && keep_trying != 0; keep_trying--) {
                        this.currentFlightTarget.set(
                            (int) this.posX + this.rand.nextInt(6) - this.rand.nextInt(6),
                            (int) this.posY + this.rand.nextInt(6) - 2,
                            (int) this.posZ + this.rand.nextInt(6) - this.rand.nextInt(6));
                        bid = this.worldObj.getBlock(
                            this.currentFlightTarget.posX,
                            this.currentFlightTarget.posY,
                            this.currentFlightTarget.posZ);
                    }
                }
            }

            double dx = this.currentFlightTarget.posX + 0.5 - this.posX;
            double dy = this.currentFlightTarget.posY + 0.1 - this.posY;
            double dz = this.currentFlightTarget.posZ + 0.5 - this.posZ;
            this.motionX = this.motionX + (Math.signum(dx) * 0.5 - this.motionX) * 0.1F;
            this.motionY = this.motionY + (Math.signum(dy) * 0.7F - this.motionY) * 0.1F;
            this.motionZ = this.motionZ + (Math.signum(dz) * 0.5 - this.motionZ) * 0.1F;
            float f = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
            float rotationYaw = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
            this.moveForward = 0.3F;
            this.rotationYaw += rotationYaw;
        }
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void fall(float distance) {}

    protected void updateFallState(double distanceFallen, boolean onGround) {}

    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    public boolean getCanSpawnHere() {
        return true;
    }

    public void initCreature() {}
}
