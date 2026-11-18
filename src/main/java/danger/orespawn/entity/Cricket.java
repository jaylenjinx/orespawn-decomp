package danger.orespawn.entity;

import java.util.List;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

import danger.orespawn.entity.ai.MyEntityAIWanderALot;

public class Cricket extends EntityAnimal {

    public double moveSpeed = 0.15F;
    private int singing = 0;
    private int jumpcount = 0;

    public Cricket(World world) {
        super(world);
        this.setSize(0.1F, 0.1F);
        this.experienceValue = 1;
        this.getNavigator()
            .setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAIPanic(this, 1.4));
        this.tasks.addTask(1, new MyEntityAIWanderALot(this, 8, 1.0));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        this.getAttributeMap()
            .registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(0.0);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public int getSinging() {
        return this.dataWatcher.getWatchableObjectByte(20);
    }

    public void setSinging(int n) {
        this.dataWatcher.updateObject(20, (byte) n);
    }

    private void jumpAround() {
        this.motionY = this.motionY + (0.55F + Math.abs(this.worldObj.rand.nextFloat() * 0.35F));
        this.posY += 0.25;
        float f = 0.3F + Math.abs(this.worldObj.rand.nextFloat() * 0.25F);
        float d = (float) (this.worldObj.rand.nextFloat() * Math.PI * 2.0);
        this.motionX = this.motionX + f * Math.sin(d);
        this.motionZ = this.motionZ + f * Math.cos(d);
        this.isAirBorne = true;
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
        if (!this.worldObj.isRemote) {
            if (this.singing != 0) {
                this.singing--;
                if (this.singing <= 0) {
                    this.setSinging(0);
                }
            }

            if (this.jumpcount > 0) {
                this.jumpcount--;
            }

            if (this.jumpcount == 0 && this.worldObj.rand.nextInt(50) == 1) {
                this.jumpAround();
                this.jumpcount = 50;
            }
        }
    }

    public boolean isAIEnabled() {
        return true;
    }

    public int mygetMaxHealth() {
        return 3;
    }

    protected String getLivingSound() {
        if (!this.worldObj.isRemote) {
            if (this.worldObj.rand.nextInt(2) == 0) {
                return null;
            }

            this.singing = 40;
            this.setSinging(this.singing);
        }

        return "orespawn:cricket";
    }

    protected String getHurtSound() {
        return null;
    }

    protected String getDeathSound() {
        return null;
    }

    protected float getSoundVolume() {
        return 0.7F;
    }

    protected void playStepSound(int n, int n2, int n3, int n4) {}

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {}

    protected boolean canTriggerWalking() {
        return true;
    }

    protected void fall(float distance) {}

    protected void updateFallState(double distanceFallen, boolean onGround) {}

    public EntityAgeable createChild(EntityAgeable partner) {
        return null;
    }

    public boolean getCanSpawnHere() {
        return this.posY < 30.0 ? false : this.findBuddies() <= 5;
    }

    private int findBuddies() {
        List entities = this.worldObj.getEntitiesWithinAABB(Cricket.class, this.boundingBox.expand(20.0, 10.0, 20.0));
        return entities.size();
    }
}
