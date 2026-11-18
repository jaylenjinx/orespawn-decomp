package danger.orespawn.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;

public class EntityLunaMoth extends EntityButterfly {

    private ChunkCoordinates currentFlightTarget = null;
    public int moth_type = 0;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public EntityLunaMoth(World world) {
        super(world);
        this.moth_type = OreSpawnMain.OreSpawnRand.nextInt(4);
        this.setSize(0.5F, 0.5F);
        this.getNavigator()
            .setAvoidsWater(true);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(0.1F);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(0.0);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected void collideWithEntity(Entity entity) {}

    @Override
    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.motionY *= 0.6;
    }

    private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {
        int found = 0;

        for (int i = -dy; i <= dy; i++) {
            for (int j = -dz; j <= dz; j++) {
                Block bid = this.worldObj.getBlock(x + dx, y + i, z + j);
                if (bid == Blocks.torch || bid == OreSpawnMain.ExtremeTorch) {
                    int d = dx * dx + j * j + i * i;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x + dx;
                        this.ty = y + i;
                        this.tz = z + j;
                        found++;
                    }
                }

                bid = this.worldObj.getBlock(x - dx, y + i, z + j);
                if (bid == Blocks.torch || bid == OreSpawnMain.ExtremeTorch) {
                    int d = dx * dx + j * j + i * i;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x - dx;
                        this.ty = y + i;
                        this.tz = z + j;
                        found++;
                    }
                }
            }
        }

        for (int i = -dx; i <= dx; i++) {
            for (int j = -dz; j <= dz; j++) {
                Block bidx = this.worldObj.getBlock(x + i, y + dy, z + j);
                if (bidx == Blocks.torch || bidx == OreSpawnMain.ExtremeTorch) {
                    int d = dy * dy + j * j + i * i;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x + i;
                        this.ty = y + dy;
                        this.tz = z + j;
                        found++;
                    }
                }

                bidx = this.worldObj.getBlock(x + i, y - dy, z + j);
                if (bidx == Blocks.torch || bidx == OreSpawnMain.ExtremeTorch) {
                    int d = dy * dy + j * j + i * i;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x + i;
                        this.ty = y - dy;
                        this.tz = z + j;
                        found++;
                    }
                }
            }
        }

        for (int i = -dx; i <= dx; i++) {
            for (int j = -dy; j <= dy; j++) {
                Block bidxx = this.worldObj.getBlock(x + i, y + j, z + dz);
                if (bidxx == Blocks.torch || bidxx == OreSpawnMain.ExtremeTorch) {
                    int d = dz * dz + j * j + i * i;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x + i;
                        this.ty = y + j;
                        this.tz = z + dz;
                        found++;
                    }
                }

                bidxx = this.worldObj.getBlock(x + i, y + j, z - dz);
                if (bidxx == Blocks.torch || bidxx == OreSpawnMain.ExtremeTorch) {
                    int d = dz * dz + j * j + i * i;
                    if (d < this.closest) {
                        this.closest = d;
                        this.tx = x + i;
                        this.ty = y + j;
                        this.tz = z - dz;
                        found++;
                    }
                }
            }
        }

        return found != 0;
    }

    @Override
    protected void updateAITasks() {
        int keep_trying = 25;
        if (!this.isDead) {
            super.updateAITasks();
            if (this.currentFlightTarget == null) {
                this.currentFlightTarget = new ChunkCoordinates((int) this.posX, (int) this.posY, (int) this.posZ);
            }

            if (this.rand.nextInt(100) != 0
                && !(this.currentFlightTarget.getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ)
                    < 4.0F)) {
                if (!this.worldObj.isDaytime() && this.rand.nextInt(10) == 0) {
                    this.closest = 99999;
                    this.tx = this.ty = this.tz = 0;

                    for (int i = 2; i < 15
                        && !this.scan_it((int) this.posX, (int) this.posY, (int) this.posZ, i, i, i); i++) {
                        if (i >= 6) {
                            i++;
                        }
                    }

                    if (this.closest < 99999) {
                        this.currentFlightTarget.set(this.tx, this.ty + 1, this.tz);
                    }
                }
            } else {
                for (Block bid = Blocks.stone; bid != Blocks.air && keep_trying != 0; keep_trying--) {
                    this.currentFlightTarget.set(
                        (int) this.posX + this.rand.nextInt(10) - this.rand.nextInt(10),
                        (int) this.posY + this.rand.nextInt(6) - 2,
                        (int) this.posZ + this.rand.nextInt(10) - this.rand.nextInt(10));
                    bid = this.worldObj.getBlock(
                        this.currentFlightTarget.posX,
                        this.currentFlightTarget.posY,
                        this.currentFlightTarget.posZ);
                }
            }

            double dx = this.currentFlightTarget.posX + 0.5 - this.posX;
            double dy = this.currentFlightTarget.posY + 0.1 - this.posY;
            double dz = this.currentFlightTarget.posZ + 0.5 - this.posZ;
            this.motionX = this.motionX + (Math.signum(dx) * 0.5 - this.motionX) * 0.1F;
            this.motionY = this.motionY + (Math.signum(dy) * 0.68 - this.motionY) * 0.1F;
            this.motionZ = this.motionZ + (Math.signum(dz) * 0.5 - this.motionZ) * 0.1F;
            float f = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
            float rotationYaw = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
            this.moveForward = 0.75F;
            this.rotationYaw += rotationYaw;
        }
    }

    @Override
    protected void fall(float distance) {}

    @Override
    protected void updateFallState(double distanceFallen, boolean onGround) {}

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    @Override
    public boolean getCanSpawnHere() {
        Block bid = this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ);
        if (bid != Blocks.air) {
            return false;
        } else if (this.worldObj.isDaytime()) {
            return false;
        } else {
            return this.worldObj.provider.dimensionId == OreSpawnMain.DimensionID4 ? true : !(this.posY < 50.0);
        }
    }

    @Override
    public void initCreature() {}
}
