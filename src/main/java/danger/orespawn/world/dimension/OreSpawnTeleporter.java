package danger.orespawn.world.dimension;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import danger.orespawn.OreSpawnMain;

public class OreSpawnTeleporter extends Teleporter {

    private WorldServer world;
    private World oldWorld;
    private Random random;
    private int newdim;

    public OreSpawnTeleporter(WorldServer world2, int dim, World world3) {
        super(world2);
        this.world = world2;
        this.oldWorld = world3;
        this.random = new Random(world2.getSeed());
        this.newdim = dim;
    }

    public void placeInPortal(Entity entity, double x, double y, double z, float yaw) {
        this.justPutMe(entity);
    }

    public boolean placeInExistingPortal(Entity entity, double x, double y, double z, float yaw) {
        this.justPutMe(entity);
        return true;
    }

    public boolean makePortal(Entity entity) {
        return true;
    }

    private boolean isGroundBlock(Block bid) {
        if (bid == Blocks.air) {
            return false;
        } else if (bid == Blocks.dirt) {
            return true;
        } else if (bid == Blocks.grass) {
            return true;
        } else if (bid == Blocks.stone) {
            return true;
        } else if (bid == Blocks.end_stone) {
            return true;
        } else if (bid == Blocks.netherrack) {
            return true;
        } else if (bid == Blocks.cobblestone) {
            return true;
        } else if (bid == Blocks.sand) {
            return true;
        } else {
            return bid == Blocks.sandstone ? true : bid == Blocks.farmland;
        }
    }

    public boolean justPutMe(Entity entity) {
        int posX = (int) entity.posX;
        int posZ = (int) entity.posZ;
        int posY = 120;
        int found = 0;
        int inarow = 0;
        int airfound = 0;

        label132: for (int i = 0; i < 1000 && found == 0; i++) {
            posY = 180;

            while (posY > 1) {
                label142: {
                    Block bid = this.world.getBlock(posX, posY + 1, posZ);
                    if (bid != Blocks.air && bid != null) {
                        if (this.isGroundBlock(bid)) {
                            inarow++;
                        }

                        if (airfound != 0 && inarow >= 3) {
                            break label142;
                        }
                    } else {
                        inarow = 0;
                        bid = this.world.getBlock(posX, posY, posZ);
                        if (bid == Blocks.air || bid == null) {
                            airfound = 1;
                            bid = this.world.getBlock(posX, posY - 1, posZ);
                            if (bid != Blocks.air && bid != null) {
                                if (this.world.getBlock(posX, posY - 1, posZ)
                                    .getMaterial()
                                    .isSolid()) {
                                    found = 1;
                                } else if (bid == Blocks.tallgrass && this.world.getBlock(posX, posY - 2, posZ)
                                    .getMaterial()
                                    .isSolid()) {
                                        found = 1;
                                        posY--;
                                    }
                                break label142;
                            }
                        }
                    }

                    posY--;
                    continue;
                }

                if (found == 0) {
                    posX = (int) entity.posX + this.world.rand.nextInt(3 + i / 5) - this.world.rand.nextInt(3 + i / 5);
                    if (i > 100) {
                        posX = posX + OreSpawnMain.OreSpawnRand.nextInt(2 + i / 5)
                            - OreSpawnMain.OreSpawnRand.nextInt(2 + i / 5);
                    }

                    if (i > 500) {
                        posX = posX + this.random.nextInt(2 + i / 5) - this.random.nextInt(2 + i / 5);
                    }

                    posZ = (int) entity.posZ + this.world.rand.nextInt(3 + i / 5) - this.world.rand.nextInt(3 + i / 5);
                    if (i > 100) {
                        posZ = posZ + OreSpawnMain.OreSpawnRand.nextInt(2 + i / 5)
                            - OreSpawnMain.OreSpawnRand.nextInt(2 + i / 5);
                    }

                    if (i > 500) {
                        posZ = posZ + this.random.nextInt(2 + i / 5) - this.random.nextInt(2 + i / 5);
                    }

                    airfound = 0;
                    inarow = 0;
                }
                continue label132;
            }
            break;
        }

        if (found == 0) {
            posX = (int) entity.posX;
            posZ = (int) entity.posZ;
            posY = 180;

            while (posY > 1 && (Blocks.air != this.world.getBlock(posX, posY + 1, posZ)
                || Blocks.air != this.world.getBlock(posX, posY, posZ)
                || Blocks.air == this.world.getBlock(posX, posY - 1, posZ))) {
                posY--;
            }
        }

        double oldX = entity.posX;
        double oldY = entity.posY;
        double oldZ = entity.posZ;
        double newX = posX;
        double newZ = posZ;
        double newY = posY;
        if (newX < 0.0) {
            newX -= 0.5;
        } else {
            newX += 0.5;
        }

        if (newZ < 0.0) {
            newZ -= 0.5;
        } else {
            newZ += 0.5;
        }

        entity.setLocationAndAngles(newX, newY, newZ, entity.rotationYaw, 0.0F);
        entity.motionX = entity.motionY = entity.motionZ = 0.0;
        MinecraftServer minecraftserver = MinecraftServer.getServer();
        WorldServer worldserver = minecraftserver.worldServerForDimension(this.oldWorld.provider.dimensionId);
        WorldServer worldserver1 = minecraftserver.worldServerForDimension(this.newdim);
        if (entity instanceof EntityPlayer) {
            EntityPlayer ep = (EntityPlayer) entity;
            AxisAlignedBB bb = AxisAlignedBB
                .getBoundingBox(oldX - 24.0, oldY - 12.0, oldZ - 24.0, oldX + 24.0, oldY + 12.0, oldZ + 24.0);

            for (Entity entity2 : this.oldWorld.getEntitiesWithinAABB(EntityTameable.class, bb)) {
                EntityTameable et = (EntityTameable) entity2;
                if (!et.isSitting()) {
                    String p1 = ep.getUniqueID()
                        .toString();
                    String p2 = et.func_152113_b();
                    if (p1 != null && p2 != null && p1.equals(p2) || et.func_152114_e(ep)) {
                        this.sendToThisDimension(entity2, newX, newY, newZ, (int) ep.rotationYaw);
                    }
                }
            }
        }

        worldserver.resetUpdateEntityTick();
        worldserver1.resetUpdateEntityTick();
        return true;
    }

    public void sendToThisDimension(Entity e, double newX, double newY, double newZ, int ro) {
        if (!this.oldWorld.isRemote) {
            e.worldObj.removeEntity(e);
            e.isDead = false;
            e.setLocationAndAngles(newX, newY, newZ, ro, 0.0F);
            e.motionX = e.motionY = e.motionZ = 0.0;
            e.setWorld(this.world);
            Entity entity = EntityList.createEntityByName(EntityList.getEntityString(e), this.world);
            if (entity != null) {
                entity.copyDataFrom(e, true);
                entity.setLocationAndAngles(newX, newY, newZ, ro, 0.0F);
                entity.motionX = entity.motionY = entity.motionZ = 0.0;
                entity.setWorld(this.world);
                this.world.spawnEntityInWorld(entity);
            }

            e.isDead = true;
        }
    }

    public void removeStalePortalLocations(long worldTime) {}
}
