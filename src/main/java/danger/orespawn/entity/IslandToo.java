package danger.orespawn.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;

public class IslandToo extends EntityAnimal {

    private int dir = 0;
    private float speed = 0.1F;
    private int width = 5;
    private int depth = 3;
    private int length = 10;
    private int timer = 42;
    private int just_spawned = 1;
    private int ticker = 0;
    private int once = 1;
    private double myX;
    private double myY;
    private double myZ;
    private int dirchange = 0;
    private int blocktype = 0;

    public IslandToo(World par1World) {
        super(par1World);
        this.setSize(0.5F, 0.5F);
        this.ticker = par1World.rand.nextInt(50);
        this.dirchange = this.worldObj.rand.nextInt(5000);
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
                this.dir = this.worldObj.rand.nextInt(4);
                if (this.worldObj.rand.nextInt(40) != 1) {
                    this.width = 1 + this.worldObj.rand.nextInt(5 * OreSpawnMain.IslandSizeFactor);
                    this.length = this.width;
                    this.depth = 1 + this.worldObj.rand.nextInt(4);
                    this.speed = this.worldObj.rand.nextFloat() / 40.0F * OreSpawnMain.IslandSpeedFactor;
                    if (this.length * this.width * this.depth <= 64) {
                        this.speed *= 2.0F;
                    }

                    if (this.length * this.width * this.depth <= 32) {
                        this.speed *= 2.0F;
                    }
                } else {
                    this.width = 5 + this.worldObj.rand.nextInt(8 * OreSpawnMain.IslandSizeFactor);
                    this.length = this.width;
                    this.depth = 3 + this.worldObj.rand.nextInt(6);
                    this.speed = this.worldObj.rand.nextFloat() / 150.0F * OreSpawnMain.IslandSpeedFactor;
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
                this.dir = this.worldObj.rand.nextInt(4);
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

    protected void fall(float par1) {}

    protected boolean canDespawn() {
        return false;
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.just_spawned = par1NBTTagCompound.getInteger("JustSpawned");
        this.width = par1NBTTagCompound.getInteger("Iwidth");
        this.depth = par1NBTTagCompound.getInteger("Idepth");
        this.length = par1NBTTagCompound.getInteger("Ilength");
        this.speed = par1NBTTagCompound.getFloat("Ispeed");
        this.dir = par1NBTTagCompound.getInteger("Idir");
        this.blocktype = par1NBTTagCompound.getInteger("Iblocktype");
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("JustSpawned", this.just_spawned);
        par1NBTTagCompound.setInteger("Iwidth", this.width);
        par1NBTTagCompound.setInteger("Idepth", this.depth);
        par1NBTTagCompound.setInteger("Ilength", this.length);
        par1NBTTagCompound.setFloat("Ispeed", this.speed);
        par1NBTTagCompound.setInteger("Idir", this.dir);
        par1NBTTagCompound.setInteger("Iblocktype", this.blocktype);
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return null;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        int xoff = 0;
        int zoff = 0;
        int ix = (int) this.posX;
        int iz = (int) this.posZ;
        if (ix < 0) {
            boolean var8 = true;
            this.posX = ix;
            this.posX -= 0.5;
        } else {
            this.posX = ix;
            this.posX += 0.5;
        }

        if (iz < 0) {
            boolean var9 = true;
            this.posZ = iz;
            this.posZ -= 0.5;
        } else {
            this.posZ = iz;
            this.posZ += 0.5;
        }

        super.attackEntityFrom(par1DamageSource, par2);
        return ret;
    }

    private void create_island() {
        int xoff = 0;
        int zoff = 0;
        if (this.posX < 0.0) {
            xoff = 1;
        }

        if (this.posZ < 0.0) {
            zoff = 1;
        }

        for (int k = 0; k <= this.depth; k++) {
            int il = this.length / (this.depth - k + 1);
            if (il < 1) {
                il = 1;
            }

            for (int i = -il; i <= il; i++) {
                for (int j = -il; j <= il; j++) {
                    int ix = (int) this.posX + j - xoff;
                    int iz = (int) this.posZ + i - zoff;
                    if (k == this.depth) {
                        Block bid;
                        if ((bid = this.worldObj.getBlock(ix, (int) this.posY + k, iz)) == Blocks.air) {
                            if (this.worldObj.rand.nextInt(5000) == 1) {
                                this.worldObj.setBlock(ix, (int) this.posY + k, iz, Blocks.water);
                            } else {
                                this.FastSetBlock(ix, (int) this.posY + k, iz, Blocks.grass);
                                if (this.worldObj.rand.nextInt(30) == 1) {
                                    if (this.worldObj.getBlock(ix, (int) this.posY + k + 1, iz) == Blocks.air) {
                                        if (this.worldObj.rand.nextInt(2) == 1) {
                                            this.worldObj.setBlock(
                                                ix,
                                                (int) this.posY + k + 1,
                                                iz,
                                                OreSpawnMain.MyFlowerPinkBlock);
                                        } else {
                                            this.worldObj.setBlock(
                                                ix,
                                                (int) this.posY + k + 1,
                                                iz,
                                                OreSpawnMain.MyFlowerBlueBlock);
                                        }
                                    }
                                } else if (this.worldObj.rand.nextInt(100) == 1
                                    && this.worldObj.getBlock(ix, (int) this.posY + k + 1, iz) == Blocks.air) {
                                        OreSpawnMain.OreSpawnTrees
                                            .SmallTree(this.worldObj, ix, (int) this.posY + k + 1, iz);
                                    }
                            }
                        } else if (bid == Blocks.bedrock) {
                            this.setDead();
                            return;
                        }
                    } else {
                        this.mySetBlock(ix, (int) this.posY + k, iz);
                    }
                }
            }
        }

        this.worldObj.setBlock((int) this.posX - xoff, (int) this.posY, (int) this.posZ - zoff, Blocks.air);
    }

    private void mySetBlock(int ix, int iy, int iz) {
        Block bid = Blocks.stone;
        if (this.blocktype == 0) {
            this.blocktype = 1 + this.worldObj.rand.nextInt(8);
        }

        if (this.blocktype == 1 && this.worldObj.rand.nextInt(5) == 1) {
            bid = Blocks.coal_ore;
        }

        if (this.blocktype == 2 && this.worldObj.rand.nextInt(10) == 1) {
            bid = Blocks.iron_ore;
        }

        if (this.blocktype == 3 && this.worldObj.rand.nextInt(20) == 1) {
            bid = Blocks.emerald_ore;
        }

        if (this.blocktype == 4 && this.worldObj.rand.nextInt(30) == 1) {
            bid = OreSpawnMain.MyOreTitaniumBlock;
        }

        if (this.blocktype == 5 && this.worldObj.rand.nextInt(30) == 1) {
            bid = OreSpawnMain.MyOreUraniumBlock;
        }

        if (this.blocktype == 6 && this.worldObj.rand.nextInt(30) == 1) {
            bid = OreSpawnMain.MyOreRubyBlock;
        }

        if (this.blocktype == 7 && this.worldObj.rand.nextInt(30) == 1) {
            bid = OreSpawnMain.MyOreAmethystBlock;
        }

        if (this.blocktype == 8 && this.worldObj.rand.nextInt(20) == 1) {
            bid = Blocks.gold_ore;
        }

        if (bid == Blocks.stone) {
            if (this.worldObj.rand.nextInt(3000) == 1) {
                bid = OreSpawnMain.MyEnderPearlBlock;
            }

            if (this.worldObj.rand.nextInt(3000) == 2) {
                bid = OreSpawnMain.MyEyeOfEnderBlock;
            }

            if (this.worldObj.rand.nextInt(3000) == 3) {
                bid = OreSpawnMain.MyBlockAmethystBlock;
            }

            if (this.worldObj.rand.nextInt(3000) == 4) {
                bid = OreSpawnMain.MyBlockRubyBlock;
            }

            if (this.worldObj.rand.nextInt(3000) == 5) {
                bid = OreSpawnMain.MyBlockUraniumBlock;
            }

            if (this.worldObj.rand.nextInt(3000) == 6) {
                bid = OreSpawnMain.MyBlockTitaniumBlock;
            }

            if (this.worldObj.rand.nextInt(3000) == 7) {
                bid = Blocks.gold_block;
            }

            if (this.worldObj.rand.nextInt(3000) == 8) {
                bid = Blocks.diamond_block;
            }
        }

        this.FastSetBlock(ix, iy, iz, bid);
    }

    private void update_island() {
        int xoff = 0;
        int zoff = 0;
        if (this.dir == 0) {
            this.myZ = this.myZ - this.speed;
        } else if (this.dir == 1) {
            this.myZ = this.myZ + this.speed;
        } else if (this.dir == 2) {
            this.myX = this.myX + this.speed;
        } else {
            this.myX = this.myX - this.speed;
        }

        int ke = 0;
        int ks = 0;
        int je = 0;
        int js = 0;
        int mx = (int) this.myX;
        int mz = (int) this.myZ;
        int px = (int) this.posX;
        int pz = (int) this.posZ;
        if (mx != px || mz != pz) {
            byte var26;
            byte var28;
            byte var30;
            byte var32;
            if (this.dir == 0) {
                var30 = 1;
                var32 = 1;
                var26 = -1;
                var28 = 1;
            } else if (this.dir == 1) {
                var30 = -1;
                var32 = -1;
                var26 = -1;
                var28 = 1;
            } else if (this.dir == 2) {
                var30 = -1;
                var32 = 1;
                var26 = -1;
                var28 = -1;
            } else {
                var30 = -1;
                var32 = 1;
                var26 = 1;
                var28 = 1;
            }

            if (this.posX < 0.0) {
                xoff = 1;
            }

            if (this.posZ < 0.0) {
                zoff = 1;
            }

            for (int i = 0; i <= this.depth; i++) {
                int il = this.length / (this.depth - i + 1);
                if (il < 1) {
                    il = 1;
                }

                for (int j = var30 * il; j <= var32 * il; j++) {
                    for (int k = var26 * il; k <= var28 * il; k++) {
                        int ix = (int) this.posX + k - xoff;
                        int iz = (int) this.posZ + j - zoff;
                        if (i == this.depth) {
                            Block bid = this.worldObj.getBlock(ix, (int) this.posY + i + 1, iz);
                            if (bid == OreSpawnMain.MyFlowerPinkBlock || bid == OreSpawnMain.MyFlowerBlueBlock
                                || bid == OreSpawnMain.MyFlowerBlackBlock
                                || bid == OreSpawnMain.MyFlowerScaryBlock) {
                                this.FastSetBlock(ix, (int) this.posY + i + 1, iz, Blocks.air);
                            }

                            if (bid == Blocks.water || bid == Blocks.flowing_water) {
                                this.worldObj.setBlock(ix, (int) this.posY + i, iz, Blocks.air);
                            }

                            if (bid == OreSpawnMain.MySkyTreeLog) {
                                this.worldObj.setBlock(ix, (int) this.posY + i + 1, iz, Blocks.air);
                                bid = this.worldObj.getBlock(ix, (int) this.posY + i + 2, iz);
                                if (bid == OreSpawnMain.MySkyTreeLog) {
                                    this.worldObj.setBlock(ix, (int) this.posY + i + 2, iz, Blocks.air);
                                    bid = this.worldObj.getBlock(ix, (int) this.posY + i + 3, iz);
                                    if (bid == OreSpawnMain.MySkyTreeLog) {
                                        this.worldObj.setBlock(ix, (int) this.posY + i + 3, iz, Blocks.air);
                                    }
                                }
                            }

                            bid = this.worldObj.getBlock(ix, (int) this.posY + i, iz);
                            if (bid == Blocks.water || bid == Blocks.flowing_water) {
                                this.worldObj.setBlock(ix, (int) this.posY + i, iz, Blocks.air);
                            }
                        }

                        this.FastSetBlock(ix, (int) this.posY + i, iz, Blocks.air);
                    }
                }
            }

            this.mySetBlock((int) this.posX - xoff, (int) this.posY, (int) this.posZ - zoff);
            this.posX = mx;
            if (this.myX < 0.0) {
                this.posX -= 0.5;
            } else {
                this.posX += 0.5;
            }

            this.posZ = mz;
            if (this.myZ < 0.0) {
                this.posZ -= 0.5;
            } else {
                this.posZ += 0.5;
            }

            if (this.dir == 0) {
                var30 = -1;
                var32 = -1;
                var26 = -1;
                var28 = 1;
            } else if (this.dir == 1) {
                var30 = 1;
                var32 = 1;
                var26 = -1;
                var28 = 1;
            } else if (this.dir == 2) {
                var30 = -1;
                var32 = 1;
                var26 = 1;
                var28 = 1;
            } else {
                var30 = -1;
                var32 = 1;
                var26 = -1;
                var28 = -1;
            }

            int var38 = 0;
            int var37 = 0;
            if (this.posX < 0.0) {
                var37 = 1;
            }

            if (this.posZ < 0.0) {
                var38 = 1;
            }

            this.worldObj.setBlock((int) this.posX - var37, (int) this.posY, (int) this.posZ - var38, Blocks.air);

            for (int var23 = 0; var23 <= this.depth; var23++) {
                int il = this.length / (this.depth - var23 + 1);
                if (il < 1) {
                    il = 1;
                }

                for (int j = var30 * il; j <= var32 * il; j++) {
                    for (int k = var26 * il; k <= var28 * il; k++) {
                        int ix = (int) this.posX + k - var37;
                        int iz = (int) this.posZ + j - var38;
                        if (var23 == this.depth) {
                            Block bidx;
                            if ((bidx = this.worldObj.getBlock(ix, (int) this.posY + var23, iz)) == Blocks.air) {
                                if (this.worldObj.rand.nextInt(5000) == 1) {
                                    this.worldObj.setBlock(ix, (int) this.posY + var23, iz, Blocks.water);
                                } else {
                                    this.FastSetBlock(ix, (int) this.posY + var23, iz, Blocks.grass);
                                    if (this.worldObj.rand.nextInt(30) == 1) {
                                        if (this.worldObj.getBlock(ix, (int) this.posY + var23 + 1, iz) == Blocks.air) {
                                            if (this.worldObj.rand.nextInt(2) == 1) {
                                                this.worldObj.setBlock(
                                                    ix,
                                                    (int) this.posY + var23 + 1,
                                                    iz,
                                                    OreSpawnMain.MyFlowerPinkBlock);
                                            } else {
                                                this.worldObj.setBlock(
                                                    ix,
                                                    (int) this.posY + var23 + 1,
                                                    iz,
                                                    OreSpawnMain.MyFlowerBlueBlock);
                                            }
                                        }
                                    } else if (this.worldObj.rand.nextInt(100) == 1
                                        && this.worldObj.getBlock(ix, (int) this.posY + var23 + 1, iz) == Blocks.air) {
                                            OreSpawnMain.OreSpawnTrees
                                                .SmallTree(this.worldObj, ix, (int) this.posY + var23 + 1, iz);
                                        }
                                }
                            } else if (bidx == Blocks.bedrock) {
                                this.setDead();
                                return;
                            }
                        } else {
                            Block bidx = this.worldObj.getBlock(ix, (int) this.posY + var23, iz);
                            if (bidx == Blocks.end_stone) {
                                if (!this.worldObj.isRemote) {
                                    this.worldObj.createExplosion(this, ix, this.posY + var23, iz, 5.0F, true);
                                }
                            } else {
                                this.mySetBlock(ix, (int) this.posY + var23, iz);
                            }
                        }
                    }
                }
            }

            this.worldObj.setBlock((int) this.posX - var37, (int) this.posY, (int) this.posZ - var38, Blocks.air);
        }
    }

    protected Item getDropItem() {
        return Item.getItemFromBlock(OreSpawnMain.MyIslandBlock);
    }

    public void FastSetBlock(int ix, int iy, int iz, Block id) {
        OreSpawnMain.setBlockFast(this.worldObj, ix, iy, iz, id, 0, 3);
    }
}
