package danger.orespawn.entity;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;

public class Island extends EntityAnimal {

    private float dir = 0.0F;
    private float speed = 0.1F;
    private int radius = 5;
    private int depth = 3;
    private int timer = 73;
    private int just_spawned = 1;
    private int ticker = 0;
    private int once = 1;
    private double myX;
    private double myY;
    private double myZ;
    private int dirchange;

    public Island(World world) {
        super(world);
        this.setSize(0.5F, 0.5F);
        this.ticker = world.rand.nextInt(50);
        this.dirchange = this.worldObj.rand.nextInt(2500);
    }

    public void onUpdate() {
        super.onUpdate();
        this.motionX = this.motionY = this.motionZ = 0.0;
        if (!this.worldObj.isRemote) {
            if (this.once != 0) {
                this.myX = this.posX;
                this.myY = this.posY;
                this.myZ = this.posZ;
                this.once = 0;
            }

            if (this.just_spawned != 0) {
                this.dir = this.worldObj.rand.nextFloat() * (float) Math.PI;
                if (this.worldObj.rand.nextInt(2) == 1) {
                    this.dir *= -1.0F;
                }

                if (this.worldObj.rand.nextInt(40) != 1) {
                    this.radius = 3 + this.worldObj.rand.nextInt(4);
                    this.depth = 2 + this.worldObj.rand.nextInt(3);
                    this.speed = this.worldObj.rand.nextFloat() / 50.0F * OreSpawnMain.IslandSpeedFactor;
                } else {
                    this.radius = 6 + this.worldObj.rand.nextInt(5);
                    this.depth = 3 + this.worldObj.rand.nextInt(4);
                    this.speed = this.worldObj.rand.nextFloat() / 200.0F * OreSpawnMain.IslandSpeedFactor;
                }

                this.create_island();
                this.ticker = this.worldObj.rand.nextInt(50);
                this.dirchange = this.worldObj.rand.nextInt(10000);
            }

            this.ticker++;
            if (this.ticker >= this.timer) {
                this.update_island();
                this.ticker = 0;
            }

            this.dirchange--;
            if (this.dirchange <= 0) {
                this.dirchange = this.worldObj.rand.nextInt(5000);
                this.dir = this.worldObj.rand.nextFloat() * (float) Math.PI;
                if (this.worldObj.rand.nextInt(2) == 1) {
                    this.dir *= -1.0F;
                }
            }

            this.just_spawned = 0;
        }
    }

    public void onLivingUpdate() {
        if (this.worldObj.isRemote) {
            super.onLivingUpdate();
        }
    }

    protected void updateAITick() {}

    protected void updateAITasks() {}

    protected void fall(float distance) {}

    protected boolean canDespawn() {
        return false;
    }

    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        this.just_spawned = tag.getInteger("JustSpawned");
        this.depth = tag.getInteger("Idepth");
        this.radius = tag.getInteger("Iradius");
        this.speed = tag.getFloat("Ispeed");
        this.dir = tag.getFloat("Idir");
    }

    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger("JustSpawned", this.just_spawned);
        tag.setInteger("Idepth", this.depth);
        tag.setInteger("Iradius", this.radius);
        tag.setFloat("Ispeed", this.speed);
        tag.setFloat("Idir", this.dir);
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return null;
    }

    private void create_island() {
        double deltadir = 0.10471975333333333;
        double deltamag = 0.35F;
        int ixlast = 0;
        int izlast = 0;
        int xoff = 0;
        int zoff = 0;

        for (int i = 0; i < this.depth; i++) {
            izlast = 0;
            ixlast = 0;

            for (double curdir = -3.1415926; curdir < 3.1415926; curdir += deltadir) {
                double tradius = this.radius;
                tradius /= i + 1;

                for (double h = 0.75; h < tradius; h += deltamag) {
                    int ix = (int) (this.posX + Math.cos(curdir + this.dir) * h);
                    int iz = (int) (this.posZ + Math.sin(curdir + this.dir) * h);
                    if (ix != ixlast || iz != izlast) {
                        ixlast = ix;
                        izlast = iz;
                        if (i == 0) {
                            Block bid;
                            if ((bid = this.worldObj.getBlock(ix, (int) this.posY - i + 1, iz)) == Blocks.air) {
                                if (this.worldObj.rand.nextInt(5000) == 1) {
                                    this.worldObj.setBlock(ix, (int) this.posY - i + 1, iz, Blocks.lava);
                                } else {
                                    this.FastSetBlock(ix, (int) this.posY - i + 1, iz, Blocks.mycelium);
                                    if (this.worldObj.rand.nextInt(20) == 1
                                        && this.worldObj.getBlock(ix, (int) this.posY - i + 2, iz) == Blocks.air) {
                                        if (this.worldObj.rand.nextInt(2) == 1) {
                                            this.worldObj
                                                .setBlock(ix, (int) this.posY - i + 2, iz, Blocks.brown_mushroom);
                                        } else {
                                            this.worldObj
                                                .setBlock(ix, (int) this.posY - i + 2, iz, Blocks.red_mushroom);
                                        }
                                    }
                                }
                            } else if (bid == Blocks.bedrock) {
                                this.setDead();
                                return;
                            }
                        } else if (this.worldObj.rand.nextInt(10) == 1) {
                            this.FastSetBlock(ix, (int) this.posY - i + 1, iz, Blocks.diamond_ore);
                        } else {
                            this.FastSetBlock(ix, (int) this.posY - i + 1, iz, Blocks.end_stone);
                        }
                    }
                }
            }
        }

        if (this.posX < 0.0) {
            xoff = -1;
        }

        if (this.posZ < 0.0) {
            zoff = -1;
        }

        this.worldObj.setBlock((int) this.posX + xoff, (int) this.posY, (int) this.posZ + zoff, Blocks.air);
        this.FastSetBlock((int) this.posX + xoff, (int) this.posY, (int) this.posZ + zoff, Blocks.air);
    }

    private void update_island() {
        double deltadir = 0.10471975333333333;
        double deltamag = 0.35F;
        double pi2 = 1.57079632675;
        int ixlast = 0;
        int izlast = 0;
        int xoff = 0;
        int zoff = 0;
        this.myX = this.myX + this.speed * Math.cos(this.dir);
        this.myZ = this.myZ + this.speed * Math.sin(this.dir);
        int mx = (int) this.myX;
        int mz = (int) this.myZ;
        int px = (int) this.posX;
        int pz = (int) this.posZ;
        if (mx != px || mz != pz) {
            for (int i = 0; i < this.depth; i++) {
                izlast = 0;
                ixlast = 0;

                for (double curdir = -3.3; curdir < 3.3; curdir += deltadir / 2.0) {
                    double tradius = this.radius;
                    tradius /= i + 1;
                    double h = 0.75;

                    while (h < tradius) {
                        h += deltamag;
                    }

                    h -= deltamag;
                    if (h < 0.75) {
                        h = 0.75;
                    }

                    for (; h < tradius + deltamag; h += deltamag / 2.0) {
                        int ix = (int) (this.posX + Math.cos(curdir + this.dir) * h);
                        int iz = (int) (this.posZ + Math.sin(curdir + this.dir) * h);
                        if (ix != ixlast || iz != izlast) {
                            ixlast = ix;
                            izlast = iz;
                            if (i == 0) {
                                Block bid = this.worldObj.getBlock(ix, (int) this.posY + 1 + 1, iz);
                                if (bid == Blocks.red_mushroom || bid == Blocks.brown_mushroom) {
                                    this.FastSetBlock(ix, (int) this.posY + 1 + 1, iz, Blocks.air);
                                }
                            }

                            this.FastSetBlock(ix, (int) this.posY - i + 1, iz, Blocks.air);
                        }
                    }
                }
            }

            if (this.posX < 0.0) {
                xoff = -1;
            }

            if (this.posZ < 0.0) {
                zoff = -1;
            }

            this.worldObj.setBlock((int) this.posX + xoff, (int) this.posY, (int) this.posZ + zoff, Blocks.end_stone);
            this.posX = (int) this.myX;
            if (this.myX < 0.0) {
                this.posX -= 0.5;
            } else {
                this.posX += 0.5;
            }

            this.posZ = (int) this.myZ;
            if (this.myZ < 0.0) {
                this.posZ -= 0.5;
            } else {
                this.posZ += 0.5;
            }

            for (int i = 0; i < this.depth; i++) {
                izlast = 0;
                ixlast = 0;

                for (double curdir = -3.1415926; curdir < 3.1415926; curdir += deltadir) {
                    double tradius = this.radius;
                    tradius /= i + 1;
                    double h = 0.75;

                    while (h < tradius) {
                        h += deltamag;
                    }

                    h -= deltamag * 3.0;
                    if (h < 0.75) {
                        h = 0.75;
                    }

                    for (; h < tradius; h += deltamag) {
                        int ix = (int) (this.posX + Math.cos(curdir + this.dir) * h);
                        int iz = (int) (this.posZ + Math.sin(curdir + this.dir) * h);
                        if (ix != ixlast || iz != izlast) {
                            ixlast = ix;
                            izlast = iz;
                            if (i == 0) {
                                Block bid;
                                if ((bid = this.worldObj.getBlock(ix, (int) this.posY - i + 1, iz)) == Blocks.air) {
                                    if (this.worldObj.rand.nextInt(5000) == 1) {
                                        this.worldObj.setBlock(ix, (int) this.posY - i + 1, iz, Blocks.lava);
                                    } else {
                                        this.FastSetBlock(ix, (int) this.posY - i + 1, iz, Blocks.mycelium);
                                        if (this.worldObj.rand.nextInt(20) == 1
                                            && this.worldObj.getBlock(ix, (int) this.posY - i + 2, iz) == Blocks.air) {
                                            if (this.worldObj.rand.nextInt(2) == 1) {
                                                this.worldObj
                                                    .setBlock(ix, (int) this.posY - i + 2, iz, Blocks.brown_mushroom);
                                            } else {
                                                this.worldObj
                                                    .setBlock(ix, (int) this.posY - i + 2, iz, Blocks.red_mushroom);
                                            }
                                        }
                                    }
                                } else if (bid == Blocks.bedrock) {
                                    this.setDead();
                                    return;
                                }
                            } else {
                                Block bid = this.worldObj.getBlock(ix, (int) this.posY - i + 1, iz);
                                if (bid == Blocks.stone) {
                                    if (!this.worldObj.isRemote) {
                                        this.worldObj.createExplosion(this, ix, this.posY - i + 1.0, iz, 5.0F, true);
                                    }
                                } else if (this.worldObj.rand.nextInt(10) == 1) {
                                    this.FastSetBlock(ix, (int) this.posY - i + 1, iz, Blocks.diamond_ore);
                                } else {
                                    this.FastSetBlock(ix, (int) this.posY - i + 1, iz, Blocks.end_stone);
                                }
                            }
                        }
                    }
                }
            }

            int n = 0;
            if (this.posX < 0.0) {
                n = -1;
            }

            int n2 = 0;
            if (this.posZ < 0.0) {
                n2 = -1;
            }

            this.worldObj.setBlock((int) this.posX + n, (int) this.posY, (int) this.posZ + n2, Blocks.air);
            this.FastSetBlock((int) this.posX + n, (int) this.posY, (int) this.posZ + n2, Blocks.air);
        }

        if (this.worldObj.rand.nextInt(2 + 2000 / this.timer) == 1) {
            AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(
                this.posX - 10.0,
                this.posY - 5.0,
                this.posZ - 10.0,
                this.posX + 10.0,
                this.posY + 5.0,
                this.posZ + 10.0);
            List entities = this.worldObj.getEntitiesWithinAABB(Triffid.class, bb);
            Iterator iterator = entities.iterator();
            if (!iterator.hasNext()) {
                EntityCreature newent = (EntityCreature) spawnCreature(
                    this.worldObj,
                    "Triffid",
                    this.posX,
                    this.posY + 2.01,
                    this.posZ);
            }
        }
    }

    public static Entity spawnCreature(World world, String str, double x, double y, double z) {
        Entity entity = null;
        entity = EntityList.createEntityByName(str, world);
        if (entity != null) {
            entity.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0F, 0.0F);
            world.spawnEntityInWorld(entity);
            ((EntityLiving) entity).playLivingSound();
        }

        return entity;
    }

    protected Item getDropItem() {
        return Item.getItemFromBlock(OreSpawnMain.MyIslandBlock);
    }

    public void FastSetBlock(int ix, int iy, int iz, Block id) {
        OreSpawnMain.setBlockFast(this.worldObj, ix, iy, iz, id, 0, 3);
    }
}
