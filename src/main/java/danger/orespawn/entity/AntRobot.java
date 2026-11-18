package danger.orespawn.entity;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.client.render.RenderSpiderRobotInfo;
import danger.orespawn.entity.ai.GenericTargetSorter;

public class AntRobot extends EntityLiving {

    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private int playing = 0;
    private GenericTargetSorter TargetSorter = null;
    private float moveSpeed = 0.3F;
    private RenderSpiderRobotInfo renderdata = new RenderSpiderRobotInfo();
    private int didonce = 0;
    private int rideTicker = 0;
    private int owned = 0;

    public AntRobot(World world) {
        super(world);
        this.setSize(2.75F, 1.25F);
        this.riddenByEntity = null;
        this.TargetSorter = new GenericTargetSorter(this);
        this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 12.0F));
        this.tasks.addTask(2, new EntityAILookIdle(this));
        this.isImmuneToFire = true;
        this.experienceValue = OreSpawnMain.AntRobot_stats.health / 2;
    }

    public AntRobot(World world, double x, double y, double z) {
        this(world);
        this.setPosition(x, y + this.yOffset, z);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(OreSpawnMain.AntRobot_stats.health);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        this.getAttributeMap()
            .registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(OreSpawnMain.AntRobot_stats.attack);
    }

    protected boolean canDespawn() {
        return false;
    }

    public void setOwned() {
        this.owned = 1;
    }

    public int getOwned() {
        return this.owned;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.AntRobot_stats.defense;
    }

    protected void updateAITasks() {
        EntityLivingBase e = null;
        if (!this.isDead) {
            if (this.riddenByEntity == null) {
                super.updateAITasks();
                if (this.owned == 0 && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) {
                    if (this.worldObj.rand.nextInt(20) == 0) {
                        this.feetFindSomethingToHit();
                    }

                    if (this.worldObj.rand.nextInt(150) == 0) {
                        this.setAttackTarget(null);
                    }

                    e = this.getAttackTarget();
                    if (e != null && !e.isEntityAlive()) {
                        this.setAttackTarget(null);
                        e = null;
                    }

                    if (e == null) {
                        e = this.findSomethingToAttack(2.0F, false);
                    }

                    if (e != null) {
                        this.faceEntity(e, 10.0F, 10.0F);
                        if (this.getDistanceSqToEntity(e) > 16.0) {
                            double d1 = e.posZ - this.posZ;
                            double d2 = e.posX - this.posX;
                            double dd = Math.atan2(d1, d2);
                            this.goThisWay(0.2 * Math.cos(dd), 0.2 * Math.sin(dd));
                        }
                    } else {
                        this.setAttacking(0);
                    }

                    if (e != null && this.worldObj.rand.nextInt(15) == 0) {
                        e = this.getAttackTarget();
                        if (e == null) {
                            e = this.findSomethingToAttack(2.0F, true);
                        }

                        if (e != null) {
                            if (this.getDistanceSqToEntity(e) < (6.0F + e.width / 2.0F) * (6.0F + e.width / 2.0F)) {
                                this.setAttacking(1);
                                this.attackEntityAsMob(e);
                            } else {
                                this.setAttacking(0);
                            }
                        } else {
                            this.setAttacking(0);
                        }
                    }
                }
            }
        }
    }

    protected void updateAITick() {
        if (this.riddenByEntity == null) {
            super.updateAITick();
        }
    }

    private void initLegData() {
        if (this.renderdata == null) {
            this.renderdata = new RenderSpiderRobotInfo();
        }

        for (int i = 0; i < 6; i++) {
            this.renderdata.ycurrentangle[i] = 0.0F;
            this.renderdata.ywantedangle[i] = 0.0F;
            this.renderdata.ydisplayangle[i] = 0.0F;
            this.renderdata.yvelocity[i] = 0.0F;
            this.renderdata.ymid[i] = 0.0F;
            this.renderdata.yoff[i] = 0.0F;
            this.renderdata.yrange[i] = 0.0F;
            this.renderdata.udcurrentangle[i] = 0.0F;
            this.renderdata.udwantedangle[i] = 0.0F;
            this.renderdata.uddisplayangle[i] = 0.0F;
            this.renderdata.udvelocity[i] = 0.0F;
            this.renderdata.p1xangle[i] = Math.PI / 4;
            this.renderdata.p2xangle[i] = 0.0;
            this.renderdata.p3xangle[i] = -Math.PI / 4;
            this.renderdata.pxvelocity[i] = 0.0F;
            this.renderdata.foot_xpos[i] = (float) this.posX;
            this.renderdata.foot_ypos[i] = (float) this.posY;
            this.renderdata.foot_zpos[i] = (float) this.posZ;
            this.renderdata.realposx[i] = 0.0F;
            this.renderdata.realposy[i] = 0.0F;
            this.renderdata.realposz[i] = 0.0F;
            this.renderdata.legoff[i] = 0.0F;
            this.renderdata.footup[i] = 1;
            this.renderdata.uppoint[i] = 0.0F;
            this.renderdata.footingticker[i] = 0;
            this.renderdata.gpcounter = 0;
            if (i == 0) {
                this.renderdata.legoff[i] = 0.75F;
                this.renderdata.ymid[i] = 0.0F;
                this.renderdata.yrange[i] = (float) (Math.PI / 12);
                this.renderdata.pairedwith[i] = 1;
                this.renderdata.yoff[i] = -0.75F;
            }

            if (i == 1) {
                this.renderdata.legoff[i] = 0.75F;
                this.renderdata.ymid[i] = (float) Math.PI;
                this.renderdata.yrange[i] = (float) (-Math.PI / 12);
                this.renderdata.pairedwith[i] = 0;
                this.renderdata.yoff[i] = -0.75F;
            }

            if (i == 2) {
                this.renderdata.legoff[i] = 1.0F;
                this.renderdata.ymid[i] = (float) (-Math.PI / 4);
                this.renderdata.yrange[i] = (float) (Math.PI / 12);
                this.renderdata.pairedwith[i] = 3;
                this.renderdata.yoff[i] = -0.75F;
            }

            if (i == 3) {
                this.renderdata.legoff[i] = 1.0F;
                this.renderdata.ymid[i] = (float) (Math.PI * 5.0 / 4.0);
                this.renderdata.yrange[i] = (float) (-Math.PI / 12);
                this.renderdata.pairedwith[i] = 2;
                this.renderdata.yoff[i] = -0.75F;
            }

            if (i == 4) {
                this.renderdata.legoff[i] = 1.15F;
                this.renderdata.ymid[i] = (float) (Math.PI / 4);
                this.renderdata.yrange[i] = (float) (Math.PI / 12);
                this.renderdata.pairedwith[i] = 5;
                this.renderdata.yoff[i] = -0.75F;
            }

            if (i == 5) {
                this.renderdata.legoff[i] = 1.15F;
                this.renderdata.ymid[i] = (float) (Math.PI * 3.0 / 4.0);
                this.renderdata.yrange[i] = (float) (-Math.PI / 12);
                this.renderdata.pairedwith[i] = 4;
                this.renderdata.yoff[i] = -0.75F;
            }
        }
    }

    private float getNewVelocity(float v, float diff, float curval) {
        float tv = v * 18.0F;
        if (tv < 2.0F) {
            tv = 2.0F;
        }

        if (tv > 8.0F) {
            tv = 8.0F;
        }

        if (diff > 0.0F) {
            if (diff < 0.008726646259971648 * tv) {
                curval = 0.0F;
            } else {
                curval = (float) (curval + 0.004363323129985824 * tv);
                if (diff < 0.06981317007977318 * tv) {
                    curval = (float) ((Math.PI / 180.0) * tv);
                }

                if (diff < 0.03490658503988659 * tv) {
                    curval = (float) (0.008726646259971648 * tv);
                }

                if (curval > 0.06981317007977318 * tv) {
                    curval = (float) (0.06981317007977318 * tv);
                }
            }
        } else if (diff > -0.008726646259971648 * tv) {
            curval = 0.0F;
        } else {
            curval = (float) (curval - 0.004363323129985824 * tv);
            if (diff > -0.06981317007977318 * tv) {
                curval = -((float) ((Math.PI / 180.0) * tv));
            }

            if (diff > -0.03490658503988659 * tv) {
                curval = -((float) (0.008726646259971648 * tv));
            }

            if (curval < -0.06981317007977318 * tv) {
                curval = -((float) (0.06981317007977318 * tv));
            }
        }

        return curval;
    }

    public void updateLegs() {
        if (this.worldObj.isRemote) {
            this.rotationYaw %= 360.0F;

            while (this.rotationYaw < 0.0F) {
                this.rotationYaw += 360.0F;
            }

            this.renderdata.gpcounter++;
            if (this.didonce == 0) {
                this.didonce = 1;
                this.initLegData();
            }

            float d1 = (float) (this.prevPosX - this.posX);
            float d2 = (float) (this.prevPosY - this.posY);
            float d3 = (float) (this.prevPosZ - this.posZ);
            float realv = (float) Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
            int i = 0;

            for (int i2 = 0; i2 < 6; i2++) {
                int fcount = 0;
                this.renderdata.footingticker[i2]++;
                this.renderdata.realposx[i2] = (float) (this.posX - this.renderdata.legoff[i2] * Math.sin(
                    Math.toRadians(MathHelper.wrapAngleTo180_double(this.rotationYaw + 90.0F))
                        + this.renderdata.ymid[i2]));
                this.renderdata.realposz[i2] = (float) (this.posZ + this.renderdata.legoff[i2] * Math.cos(
                    Math.toRadians(MathHelper.wrapAngleTo180_double(this.rotationYaw + 90.0F))
                        + this.renderdata.ymid[i2]));
                this.renderdata.realposy[i2] = (float) this.posY + this.renderdata.yoff[i2];
                int it = this.renderdata.footingticker[i2]
                    + this.renderdata.footingticker[this.renderdata.pairedwith[i2]];
                if (it > 50 && this.renderdata.footingticker[i2]
                    > this.renderdata.footingticker[this.renderdata.pairedwith[i2]]) {
                    this.renderdata.footingticker[i2] = 0;
                }

                d1 = this.renderdata.realposx[i2] - this.renderdata.foot_xpos[i2];
                d2 = this.renderdata.realposy[i2] - this.renderdata.foot_ypos[i2];
                d3 = this.renderdata.realposz[i2] - this.renderdata.foot_zpos[i2];
                float dd = (float) Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                dd *= 16.0F;
                float da = (float) (Math.abs(
                    this.renderdata.ycurrentangle[i2]
                        - (Math.toRadians(MathHelper.wrapAngleTo180_double(this.rotationYaw))
                            + this.renderdata.ymid[i2]))
                    % (Math.PI * 2));
                if (da > Math.PI) {
                    da = (float) (da - (Math.PI * 2));
                }

                if (da < -Math.PI) {
                    da = (float) (da + (Math.PI * 2));
                }

                da = Math.abs(da);
                if (dd > 144.0F || dd < 22.0F
                    || da > Math.abs(this.renderdata.yrange[i2]) * 8.0F / 6.0F
                    || Math.abs(this.renderdata.udcurrentangle[i2]) > 1.25
                    || this.renderdata.footingticker[i2] == 0) {
                    this.findNewFooting(i2);
                    d1 = this.renderdata.realposx[i2] - this.renderdata.foot_xpos[i2];
                    d2 = this.renderdata.realposy[i2] - this.renderdata.foot_ypos[i2];
                    d3 = this.renderdata.realposz[i2] - this.renderdata.foot_zpos[i2];
                    dd = (float) Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                    dd *= 16.0F;
                }

                float c1 = (float) (49.0 * Math.cos(this.renderdata.p2xangle[i2] - this.renderdata.p1xangle[i2]));
                float c2 = 49.0F;
                float c3 = (float) (49.0 * Math.cos(this.renderdata.p2xangle[i2] - this.renderdata.p3xangle[i2]));
                float cc = c1 + c2 + c3;
                float diff = cc - dd;
                this.renderdata.pxvelocity[i2] = this
                    .getNewVelocity(realv, (float) (diff * Math.PI / 360.0), this.renderdata.pxvelocity[i2]);
                if (this.renderdata.pxvelocity[i2] == 0.0F || Math.abs(diff) < 8.0F) {
                    fcount++;
                }

                this.renderdata.p1xangle[i2] = this.renderdata.p1xangle[i2] + this.renderdata.pxvelocity[i2];
                this.renderdata.p2xangle[i2] = 0.0;
                this.renderdata.p3xangle[i2] = -this.renderdata.p1xangle[i2];
                if (this.renderdata.uppoint[i2] != 0.0F) {
                    dd = (float) Math.atan2(dd, (this.renderdata.realposy[i2] - this.renderdata.uppoint[i2]) * 16.0);
                } else {
                    dd = (float) Math.atan2(dd, (this.renderdata.realposy[i2] - this.renderdata.foot_ypos[i2]) * 16.0);
                }

                this.renderdata.udwantedangle[i2] = (float) (dd - (Math.PI / 2));

                while (this.renderdata.udwantedangle[i2] > Math.PI) {
                    this.renderdata.udwantedangle[i2] = (float) (this.renderdata.udwantedangle[i2] - (Math.PI * 2));
                }

                while (this.renderdata.udwantedangle[i2] < -Math.PI) {
                    this.renderdata.udwantedangle[i2] = (float) (this.renderdata.udwantedangle[i2] + (Math.PI * 2));
                }

                double rhm = this.renderdata.udwantedangle[i2];
                double rhdir = this.renderdata.udcurrentangle[i2];
                double rdv = (rhm - rhdir) % (Math.PI * 2);

                while (rdv > Math.PI) {
                    rdv -= Math.PI * 2;
                }

                while (rdv < -Math.PI) {
                    rdv += Math.PI * 2;
                }

                diff = (float) rdv;
                this.renderdata.udvelocity[i2] = this
                    .getNewVelocity(realv * 2.0F, diff, this.renderdata.udvelocity[i2]);
                if (this.renderdata.udvelocity[i2] == 0.0F || Math.abs(diff) < 0.03490658503988659) {
                    this.renderdata.uppoint[i2] = 0.0F;
                    fcount++;
                }

                rhdir += this.renderdata.udvelocity[i2];

                while (rhdir > Math.PI) {
                    rhdir -= Math.PI * 2;
                }

                while (rhdir < -Math.PI) {
                    rhdir += Math.PI * 2;
                }

                dd = this.renderdata.udcurrentangle[i2] = (float) rhdir;
                this.renderdata.uddisplayangle[i2] = dd;
                d1 = this.renderdata.realposx[i2] - this.renderdata.foot_xpos[i2];
                d3 = this.renderdata.realposz[i2] - this.renderdata.foot_zpos[i2];
                dd = (float) Math.atan2(d3, d1);
                rhm = this.renderdata.ywantedangle[i2] = dd;
                rhdir = this.renderdata.ycurrentangle[i2];
                rdv = (rhm - rhdir) % (Math.PI * 2);
                if (rdv > Math.PI) {
                    rdv -= Math.PI * 2;
                }

                if (rdv < -Math.PI) {
                    rdv += Math.PI * 2;
                }

                diff = (float) rdv;
                this.renderdata.yvelocity[i2] = this.getNewVelocity(realv, diff, this.renderdata.yvelocity[i2]);
                if (this.renderdata.yvelocity[i2] == 0.0F || Math.abs(diff) < 0.03490658503988659) {
                    fcount++;
                }

                this.renderdata.ycurrentangle[i2] = this.renderdata.ycurrentangle[i2] + this.renderdata.yvelocity[i2];

                while (this.renderdata.ycurrentangle[i2] > Math.PI) {
                    this.renderdata.ycurrentangle[i2] = (float) (this.renderdata.ycurrentangle[i2] - (Math.PI * 2));
                }

                while (this.renderdata.ycurrentangle[i2] < -Math.PI) {
                    this.renderdata.ycurrentangle[i2] = (float) (this.renderdata.ycurrentangle[i2] + (Math.PI * 2));
                }

                dd = (float) (this.renderdata.ycurrentangle[i2]
                    - Math.toRadians(MathHelper.wrapAngleTo180_double(this.rotationYaw))
                    - (Math.PI / 2));

                while (dd > Math.PI) {
                    dd = (float) (dd - (Math.PI * 2));
                }

                while (dd < -Math.PI) {
                    dd = (float) (dd + (Math.PI * 2));
                }

                this.renderdata.ydisplayangle[i2] = dd;
                if (fcount == 3) {
                    this.renderdata.footup[i2] = 0;
                }
            }
        }
    }

    private void findNewFooting(int i) {
        float f = 9.0F;
        int found = 0;
        float range = 0.0F;
        double rhdir = Math.toRadians((this.rotationYaw + 90.0F) % 360.0F);
        double pi = 3.1415926545;
        this.renderdata.footingticker[i] = 0;
        float d1 = (float) (this.posX - this.prevPosX);
        float d3 = (float) (this.posZ - this.prevPosZ);
        double rhm = Math.atan2(d3, d1);
        double velocity = Math.sqrt(d1 * d1 + d3 * d3);
        double rdv = Math.abs(rhm - rhdir) % (pi * 2.0);
        if (rdv > pi) {
            rdv -= pi * 2.0;
        }

        rdv = Math.abs(rdv);
        if (Math.abs(velocity) < 0.01) {
            rdv = 0.0;
        }

        range = this.renderdata.yrange[i];
        range *= 0.8F;
        if (Math.abs((this.prevRotationYaw - this.rotationYaw) % 360.0F) > 0.75F) {
            range = 0.0F;
        }

        if (i >= 4) {
            f = 4.0F;
        }

        if (rdv > 1.5) {
            range = -range;
            f = 4.0F;
            if (i >= 4) {
                f = 9.0F;
            }
        }

        if (i == 0 || i == 1) {
            f = 6.0F;
        }

        float fx;
        float deffx = fx = (float) (this.renderdata.realposx[i] - f / 2.0F
            * Math.sin(
                Math.toRadians(MathHelper.wrapAngleTo180_double(this.rotationYaw + 90.0F)) + this.renderdata.ymid[i]));
        float fz;
        float deffz = fz = (float) (this.renderdata.realposz[i] + f / 2.0F
            * Math.cos(
                Math.toRadians(MathHelper.wrapAngleTo180_double(this.rotationYaw + 90.0F)) + this.renderdata.ymid[i]));
        float fy;
        float deffy = fy = this.renderdata.realposy[i] - 1.0F;
        float oldf = f;
        int span = 1;

        while (found == 0 && f > 2.5F) {
            fx = (float) (this.renderdata.realposx[i] - f * Math.sin(
                Math.toRadians(MathHelper.wrapAngleTo180_double(this.rotationYaw + 90.0F)) + this.renderdata.ymid[i]
                    - range));
            fz = (float) (this.renderdata.realposz[i] + f * Math.cos(
                Math.toRadians(MathHelper.wrapAngleTo180_double(this.rotationYaw + 90.0F)) + this.renderdata.ymid[i]
                    - range));
            fy = this.renderdata.realposy[i];
            int j = 8;

            label95: while (true) {
                if (found == 0 && j > -9) {
                    int m = -span;

                    label90: while (true) {
                        if (found == 0 && m <= span) {
                            for (int n = -span; found == 0 && n <= span; n++) {
                                Block blk = this.worldObj.getBlock((int) fx + m, (int) fy + j, (int) fz + n);
                                if (blk != Blocks.air
                                    && this.worldObj.getBlock((int) fx + m, (int) fy + j, (int) fz + n)
                                        .getMaterial()
                                        .isSolid()) {
                                    d1 = this.renderdata.realposx[i] - (fx + m);
                                    float d2 = this.renderdata.realposy[i] - (fy + j + 1.0F);
                                    d3 = this.renderdata.realposz[i] - (fz + n);
                                    float dd = (float) Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                                    dd *= 16.0F;
                                    if (!(dd > 144.0F)) {
                                        fy += j + 1;
                                        fx += m;
                                        fz += n;
                                        found = 1;
                                        m++;
                                        continue label90;
                                    }
                                }
                            }
                        }

                        j--;
                        continue label95;
                    }
                }

                if (--f < 2.5F && range != 0.0F) {
                    range = 0.0F;
                    span = 3;
                    f = oldf;
                }
                break;
            }
        }

        if (found == 0) {
            fx = deffx;
            fy = deffy;
            fz = deffz;
        }

        float sfx = this.renderdata.foot_xpos[i];
        float sfy = this.renderdata.foot_ypos[i];
        float sfz = this.renderdata.foot_zpos[i];
        this.renderdata.foot_xpos[i] = fx;
        this.renderdata.foot_ypos[i] = fy;
        this.renderdata.foot_zpos[i] = fz;
        if (this.renderdata.footup[i] == 0) {
            this.renderdata.footup[i] = 1;
            d1 = sfx - fx;
            float d2 = sfy - fy;
            d3 = sfz - fz;
            float dd = (float) Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
            dd *= 16.0F;
            d1 = (sfy + fy) / 2.0F;
            if (dd > 3.0F) {
                d1 += 0.3F;
            }

            if (dd > 24.0F) {
                d1 += 0.6F;
            }

            if (dd > 50.0F) {
                d1 += 0.6F;
            }

            this.renderdata.uppoint[i] = d1;
        }
    }

    public boolean shouldRiderSit() {
        return true;
    }

    public int getTrackingRange() {
        return 128;
    }

    public int getUpdateFrequency() {
        return 10;
    }

    public boolean sendsVelocityUpdates() {
        return true;
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    protected void entityInit() {
        super.entityInit();
        this.func_110163_bv();
        this.initLegData();
        int i = 0;
        this.dataWatcher.addObject(20, i);
    }

    public RenderSpiderRobotInfo getRenderSpiderRobotInfo() {
        return this.renderdata;
    }

    public boolean canBePushed() {
        return false;
    }

    public double getMountedYOffset() {
        return 0.55 + Math.cos(this.rideTicker * 0.19F) * 0.02;
    }

    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            float f = -1.25F;
            f = (float) (f + Math.cos(this.rideTicker * 0.33F) * 0.05);
            this.riddenByEntity.setPosition(
                this.posX - f * Math.sin(Math.toRadians(this.rotationYaw)),
                this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(),
                this.posZ + f * Math.cos(Math.toRadians(this.rotationYaw)));
        }
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.getDamageType()
            .equals("inWall")) {
            return false;
        } else if (source.getDamageType()
            .equals("cactus")) {
                return false;
            } else if (source.getDamageType()
                .equals("inFire")) {
                    return false;
                } else if (source.getDamageType()
                    .equals("onFire")) {
                        return false;
                    } else if (source.getDamageType()
                        .equals("magic")) {
                            return false;
                        } else if (source.getDamageType()
                            .equals("starve")) {
                                return false;
                            } else {
                                Entity e = source.getEntity();
                                if (e != null && e instanceof EntityLiving) {
                                    this.setAttackTarget((EntityLiving) e);
                                    this.faceEntity(e, 20.0F, 20.0F);
                                }

                                return super.attackEntityFrom(source, amount);
                            }
    }

    protected void fall(float distance) {}

    protected void updateFallState(double distanceFallen, boolean onGround) {}

    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch,
        int posRotationIncrements) {
        if (this.riddenByEntity != null) {
            this.boatPosRotationIncrements = posRotationIncrements + 8;
        } else {
            this.boatPosRotationIncrements = posRotationIncrements + 6;
        }

        this.boatX = x;
        this.boatY = y;
        this.boatZ = z;
        this.boatYaw = yaw;
        this.boatPitch = pitch;
    }

    @SideOnly(Side.CLIENT)
    public void setVelocity(double vx, double vy, double vz) {
        if (this.riddenByEntity == null) {
            super.setVelocity(vx, vy, vz);
        }
    }

    public void onUpdate() {
        super.onUpdate();
        this.setFire(0);
        if (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && !this.worldObj.isRemote
            && this.riddenByEntity != null
            && this.worldObj.rand.nextInt(50) == 0) {
            this.feetFindSomethingToHit();
        }

        if (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && !this.worldObj.isRemote
            && this.riddenByEntity != null
            && this.worldObj.rand.nextInt(9) == 0) {
            EntityLivingBase e = null;
            e = this.findSomethingToAttack(1.0F, true);
            if (e != null) {
                if (this.getDistanceSqToEntity(e) < (6.0F + e.width / 2.0F) * (6.0F + e.width / 2.0F)) {
                    this.setAttacking(1);
                    this.attackEntityAsMob(e);
                }
            } else {
                this.setAttacking(0);
            }
        }

        float f = 4.0F;
        float dx = (float) (f * Math.cos(Math.toRadians(this.rotationYaw - 80.0F)));
        float dz = (float) (f * Math.sin(Math.toRadians(this.rotationYaw - 80.0F)));
        float dx2 = (float) (f * Math.cos(Math.toRadians(this.rotationYaw - 90.0F)));
        float dz2 = (float) (f * Math.sin(Math.toRadians(this.rotationYaw - 90.0F)));
        if (this.worldObj.rand.nextInt(18) == 0) {
            this.worldObj.spawnParticle(
                "flame",
                this.posX + dx,
                this.posY + 0.5,
                this.posZ + dz,
                dx2 / f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 20.0F,
                (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 10.0F,
                dz2 / f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 20.0F);
        }

        if (this.worldObj.rand.nextInt(7) == 0) {
            this.worldObj.spawnParticle(
                "smoke",
                this.posX + dx,
                this.posY + 0.5,
                this.posZ + dz,
                dx2 / f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 20.0F,
                (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 10.0F,
                dz2 / f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 20.0F);
        }

        if (this.worldObj.rand.nextInt(16) == 0) {
            this.worldObj.spawnParticle(
                "fireworksSpark",
                this.posX + dx,
                this.posY + 0.5,
                this.posZ + dz,
                dx2 / f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 20.0F,
                (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 5.0F,
                dz2 / f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 20.0F);
        }

        dx = (float) (f * Math.cos(Math.toRadians(this.rotationYaw - 100.0F)));
        dz = (float) (f * Math.sin(Math.toRadians(this.rotationYaw - 100.0F)));
        if (this.worldObj.rand.nextInt(18) == 0) {
            this.worldObj.spawnParticle(
                "flame",
                this.posX + dx,
                this.posY + 0.5,
                this.posZ + dz,
                dx2 / f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 20.0F,
                (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 10.0F,
                dz2 / f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 20.0F);
        }

        if (this.worldObj.rand.nextInt(7) == 0) {
            this.worldObj.spawnParticle(
                "smoke",
                this.posX + dx,
                this.posY + 0.5,
                this.posZ + dz,
                dx2 / f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 20.0F,
                (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 10.0F,
                dz2 / f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 20.0F);
        }

        if (this.worldObj.rand.nextInt(16) == 0) {
            this.worldObj.spawnParticle(
                "fireworksSpark",
                this.posX + dx,
                this.posY + 0.5,
                this.posZ + dz,
                dx2 / f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 20.0F,
                (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 5.0F,
                dz2 / f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 20.0F);
        }
    }

    public void onLivingUpdate() {
        List list = null;
        double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        double d6 = this.rand.nextFloat() * 2.0F - 1.0F;
        double d7 = (this.rand.nextInt(2) * 2 - 1) * 0.7;
        double obstruction_factor = 0.0;
        double relative_g = 0.0;
        double max_speed = 0.3;
        double gh = 1.75;
        int dist = 2;
        if (!this.isDead) {
            if (this.riddenByEntity == null) {
                super.onLivingUpdate();
            }

            if (this.motionY > 0.85F) {
                this.motionY = 0.85F;
            }

            if (this.motionY < -0.85F) {
                this.motionY = -0.85F;
            }

            if (this.motionX < -1.25) {
                this.motionX = -1.25;
            }

            if (this.motionX > 1.25) {
                this.motionX = 1.25;
            }

            if (this.motionZ < -1.25) {
                this.motionZ = -1.25;
            }

            if (this.motionZ > 1.25) {
                this.motionZ = 1.25;
            }

            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.rideTicker = this.rideTicker + this.worldObj.rand.nextInt(3);
            if (this.playing > 0) {
                this.playing--;
            }

            if (this.riddenByEntity != null && this.playing == 0 && this.worldObj.rand.nextInt(80) == 1) {
                this.worldObj.playSoundAtEntity(this, "orespawn:robotspider", 0.35F, 1.0F);
                this.playing = 125;
            }

            if (this.worldObj.isRemote) {
                if (this.riddenByEntity == null) {
                    Block bid = this.worldObj
                        .getBlock((int) this.posX, (int) ((float) this.posY - (float) gh + 1.0F), (int) this.posZ);
                    if (bid == Blocks.air) {
                        bid = this.worldObj
                            .getBlock((int) this.posX, (int) ((float) this.posY - (float) gh), (int) this.posZ);
                    }

                    if (bid != Blocks.air && bid != Blocks.water
                        && bid != Blocks.flowing_water
                        && bid != Blocks.lava
                        && bid != Blocks.flowing_lava) {
                        this.motionY += 0.12;
                        this.posY += 0.12;
                        this.boatY += 0.12;
                    } else {
                        this.motionY -= 0.002;
                    }
                }

                if (this.boatPosRotationIncrements > 0) {
                    double d4 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
                    double d5 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
                    double d11 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
                    this.setPosition(d4, d5, d11);
                    this.rotationPitch = (float) (this.rotationPitch
                        + (this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
                    double d10 = MathHelper.wrapAngleTo180_double(this.boatYaw - this.rotationYaw);
                    if (this.riddenByEntity != null) {
                        d10 = MathHelper
                            .wrapAngleTo180_double((double) this.riddenByEntity.rotationYaw - this.rotationYaw);
                    }

                    this.rotationYaw = (float) (this.rotationYaw + d10 / this.boatPosRotationIncrements);
                    this.setRotation(this.rotationYaw, this.rotationPitch);
                    this.boatPosRotationIncrements--;
                } else {
                    double d4 = this.posX + this.motionX;
                    double d5 = this.posY + this.motionY;
                    double d11 = this.posZ + this.motionZ;
                    this.setPosition(d4, d5, d11);
                    this.motionX *= 0.99;
                    this.motionY *= 0.95;
                    this.motionZ *= 0.99;
                }

                this.updateLegs();
            } else {
                if (this.riddenByEntity != null) {
                    gh = 2.25;
                    Block bidx = this.worldObj
                        .getBlock((int) this.posX, (int) ((float) this.posY - (float) gh), (int) this.posZ);
                    if (bidx != Blocks.air && bidx != Blocks.water
                        && bidx != Blocks.flowing_water
                        && bidx != Blocks.lava
                        && bidx != Blocks.flowing_lava) {
                        this.motionY += 0.06;
                        this.posY += 0.03;
                    } else {
                        this.motionY -= 0.02;
                    }
                } else {
                    Block bidx = this.worldObj
                        .getBlock((int) this.posX, (int) ((float) this.posY - (float) gh + 1.0F), (int) this.posZ);
                    if (bidx == Blocks.air) {
                        bidx = this.worldObj
                            .getBlock((int) this.posX, (int) ((float) this.posY - (float) gh), (int) this.posZ);
                    }

                    if (bidx != Blocks.air && bidx != Blocks.water
                        && bidx != Blocks.flowing_water
                        && bidx != Blocks.lava
                        && bidx != Blocks.flowing_lava) {
                        this.motionY += 0.15;
                        this.posY += 0.15;
                        this.boatY += 0.15;
                    } else {
                        this.motionY -= 0.002;
                    }
                }

                if (this.riddenByEntity != null) {
                    EntityPlayer pp = (EntityPlayer) this.riddenByEntity;
                    obstruction_factor = 0.0;
                    int n = 3;
                    n += (int) (velocity * 6.0);

                    for (int k = 1; k < n; k++) {
                        for (int i = 1; i < n * 2; i++) {
                            for (int j = -90; j <= 90; j += 30) {
                                double dx = i * Math.cos(Math.toRadians(this.rotationYaw + 90.0F + j));
                                double dz = i * Math.sin(Math.toRadians(this.rotationYaw + 90.0F + j));
                                Block block = this.worldObj
                                    .getBlock((int) (this.posX + dx), (int) this.posY - k, (int) (this.posZ + dz));
                                if (block != Blocks.air && block != Blocks.water
                                    && block != Blocks.flowing_water
                                    && block != Blocks.lava
                                    && block != Blocks.flowing_lava) {
                                    obstruction_factor += 0.02;
                                }
                            }
                        }
                    }

                    this.motionY += obstruction_factor * 0.05;
                    this.posY += obstruction_factor * 0.05;
                    double d4 = this.riddenByEntity.rotationYaw;
                    d4 %= 360.0;

                    while (d4 < 0.0) {
                        d4 += 360.0;
                    }

                    double d5 = this.rotationYaw;
                    d5 %= 360.0;

                    while (d5 < 0.0) {
                        d5 += 360.0;
                    }

                    relative_g = (d4 - d5) % 180.0;

                    while (relative_g < 0.0) {
                        relative_g += 180.0;
                    }

                    if (relative_g > 90.0) {
                        relative_g -= 180.0;
                    }

                    if (velocity > 0.01) {
                        d4 = 1.85 - velocity;
                        d4 = Math.abs(d4);
                        if (d4 < 0.01) {
                            d4 = 0.01;
                        }

                        if (d4 > 0.9) {
                            d4 = 0.9;
                        }

                        this.rotationYaw = this.riddenByEntity.rotationYaw + (float) (relative_g * d4);
                    } else {
                        this.rotationYaw = this.riddenByEntity.rotationYaw;
                    }

                    relative_g = Math.abs(relative_g) * velocity;
                    if (relative_g > 50.0) {
                        relative_g = 0.0;
                    }

                    this.rotationPitch = 0.0F;
                    this.setRotation(this.rotationYaw, this.rotationPitch);
                    double newvelocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                    double rr = Math.atan2(this.riddenByEntity.motionZ, this.riddenByEntity.motionX);
                    double rhm = Math.atan2(this.motionZ, this.motionX);
                    double rhdir = Math.toRadians((this.riddenByEntity.rotationYaw + 90.0F) % 360.0F);
                    double rt = 0.0;
                    double pi = 3.1415926545;
                    double deltav = 0.0;
                    float im = pp.moveForward;
                    double rdv = Math.abs(rhm - rhdir) % (pi * 2.0);
                    if (rdv > pi) {
                        rdv -= pi * 2.0;
                    }

                    rdv = Math.abs(rdv);
                    if (Math.abs(newvelocity) < 0.01) {
                        rdv = 0.0;
                    }

                    if (rdv > 1.5) {
                        newvelocity = -newvelocity;
                    }

                    if (Math.abs(im) > 0.001F) {
                        if (im > 0.0F) {
                            deltav = 0.05;
                        } else {
                            max_speed = 0.25;
                            deltav = -0.05;
                        }

                        newvelocity += deltav;
                        if (newvelocity >= 0.0) {
                            if (newvelocity > max_speed) {
                                newvelocity = max_speed;
                            }

                            this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
                            this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
                        } else {
                            if (newvelocity < -max_speed) {
                                newvelocity = -max_speed;
                            }

                            newvelocity = -newvelocity;
                            this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0F)) * newvelocity;
                            this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0F)) * newvelocity;
                        }
                    } else if (newvelocity >= 0.0) {
                        this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
                        this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
                    } else {
                        this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0F)) * (newvelocity * -1.0);
                        this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0F)) * (newvelocity * -1.0);
                    }

                    this.moveEntity(this.motionX, this.motionY, this.motionZ);
                    this.motionX *= 0.98;
                    this.motionY *= 0.98;
                    this.motionZ *= 0.98;
                }

                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.8;
                this.motionY *= 0.98;
                this.motionZ *= 0.8;
                if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                    this.riddenByEntity = null;
                }
            }
        }
    }

    public void goThisWay(double mx, double mz) {
        this.motionX = mx;
        this.motionZ = mz;
    }

    public boolean isAIEnabled() {
        return this.riddenByEntity == null;
    }

    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger("AntRobotOwned", this.owned);
    }

    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        this.owned = tag.getInteger("AntRobotOwned");
    }

    public float getShadowSize() {
        return 0.95F;
    }

    public boolean interact(EntityPlayer player) {
        ItemStack currentItem = player.inventory.getCurrentItem();
        if (currentItem != null && currentItem.stackSize <= 0) {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
            currentItem = null;
        }

        if (this.owned == 0) {
            return true;
        } else if (currentItem != null && currentItem.getItem() == Items.iron_ingot
            && player.getDistanceSqToEntity(this) < 25.0) {
                if (!this.worldObj.isRemote) {
                    float f = this.getMaxHealth() - this.getHealth();
                    if (f > 100.0F) {
                        f = 100.0F;
                    }

                    if (f > 0.0F) {
                        this.heal(f);
                    }
                }

                if (!player.capabilities.isCreativeMode) {
                    currentItem.stackSize--;
                    if (currentItem.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                    }
                }

                return true;
            } else if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer
                && this.riddenByEntity != player) {
                    return true;
                } else {
                    if (!this.worldObj.isRemote && this.riddenByEntity == null
                        && player.getDistanceSqToEntity(this) < 16.0) {
                        player.mountEntity(this);
                        this.worldObj.playSoundAtEntity(this, "orespawn:robotspidermount", 0.45F, 1.0F);
                    }

                    return true;
                }
    }

    private void feetFindSomethingToHit() {
        if (OreSpawnMain.PlayNicely == 0) {
            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(10.0, 8.0, 10.0));
            Iterator iterator = entities.iterator();
            Entity entity = null;
            EntityLivingBase living = null;

            while (iterator.hasNext()) {
                entity = (Entity) iterator.next();
                living = (EntityLivingBase) entity;
                if (this.feetisSuitableTarget(living, false)) {
                    this.feetattackEntityAsMob(living);
                }
            }
        }
    }

    private boolean feetisSuitableTarget(EntityLivingBase living, boolean flag) {
        if (living == null) {
            return false;
        } else if (living == this) {
            return false;
        } else if (!living.isEntityAlive()) {
            return false;
        } else if (living instanceof AntRobot) {
            return false;
        } else if (living == this.riddenByEntity) {
            return false;
        } else if (MyUtils.isIgnoreable(living)) {
            return false;
        } else if (!this.getEntitySenses()
            .canSee(living)) {
                return false;
            } else {
                float d1 = (float) (living.posX - this.posX);
                float d2 = (float) (living.posY - this.posY);
                float d3 = (float) (living.posZ - this.posZ);
                float dd = (float) Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                if (dd > 9.0F) {
                    return false;
                } else if (dd < 6.0F) {
                    return false;
                } else if (living instanceof EntityPlayer) {
                    EntityPlayer p = (EntityPlayer) living;
                    return !p.capabilities.isCreativeMode;
                } else {
                    return true;
                }
            }
    }

    public boolean feetattackEntityAsMob(Entity entity) {
        boolean ret = false;
        if (entity != null && entity instanceof EntityLivingBase) {
            double ks = 0.6;
            double inair = 0.1;
            float f3 = (float) Math.atan2(entity.posZ - this.posZ, entity.posX - this.posX);
            ret = entity
                .attackEntityFrom(DamageSource.causeMobDamage(this), OreSpawnMain.AntRobot_stats.attack / 10.0F);
            if (entity.isDead || entity instanceof EntityPlayer) {
                inair *= 2.0;
            }

            if (ret) {
                entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
        }

        return ret;
    }

    private EntityLivingBase findSomethingToAttack(float distmul, boolean dircheck) {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            List entities = this.worldObj.getEntitiesWithinAABB(
                EntityLivingBase.class,
                this.boundingBox.expand(12.0 * distmul, 12.0, 12.0 * distmul));
            Iterator iterator = entities.iterator();
            Entity entity = null;
            EntityLivingBase target = null;

            while (iterator.hasNext()) {
                entity = (Entity) iterator.next();
                target = (EntityLivingBase) entity;
                if (this.isSuitableTarget(target, dircheck)) {
                    return target;
                }
            }

            return null;
        }
    }

    private boolean isSuitableTarget(EntityLivingBase target, boolean checkSight) {
        if (target == null) {
            return false;
        } else if (target == this) {
            return false;
        } else if (!target.isEntityAlive()) {
            return false;
        } else if (target instanceof AntRobot) {
            return false;
        } else if (target == this.riddenByEntity) {
            return false;
        } else if (MyUtils.isIgnoreable(target)) {
            return false;
        } else if (!this.getEntitySenses()
            .canSee(target)) {
                return false;
            } else {
                if (checkSight) {
                    double rr = Math.atan2(target.posZ - this.posZ, target.posX - this.posX);
                    double rhdir = Math.toRadians((this.rotationYaw + 90.0F) % 360.0F);
                    double pi = 3.1415926545;
                    double rdd = Math.abs(rr - rhdir) % (pi * 2.0);
                    if (rdd > pi) {
                        rdd -= pi * 2.0;
                    }

                    rdd = Math.abs(rdd);
                    if (this.getDistanceSqToEntity(target) < 36.0) {
                        return true;
                    }

                    if (rdd > 0.75) {
                        return false;
                    }
                }

                if (target instanceof EntityPlayer) {
                    EntityPlayer p = (EntityPlayer) target;
                    return !p.capabilities.isCreativeMode;
                } else {
                    return true;
                }
            }
    }

    public boolean attackEntityAsMob(Entity target) {
        boolean ret = false;
        if (target != null && target instanceof EntityLivingBase) {
            double ks = 0.7;
            double inair = 0.1;
            float f3 = (float) Math.atan2(target.posZ - this.posZ, target.posX - this.posX);
            ret = target.attackEntityFrom(DamageSource.causeMobDamage(this), OreSpawnMain.AntRobot_stats.attack);
            if (target.isDead || target instanceof EntityPlayer) {
                inair *= 2.0;
            }

            if (ret) {
                target.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
        }

        return ret;
    }

    public int getAttacking() {
        return this.dataWatcher.getWatchableObjectInt(20);
    }

    public void setAttacking(int attacking) {
        this.dataWatcher.updateObject(20, attacking);
    }

    protected Item getDropItem() {
        return null;
    }

    private ItemStack dropItemRand(Item index, int n) {
        EntityItem entityItem = null;
        ItemStack is = new ItemStack(index, n, 0);
        entityItem = new EntityItem(
            this.worldObj,
            this.posX + OreSpawnMain.OreSpawnRand.nextInt(2) - OreSpawnMain.OreSpawnRand.nextInt(2),
            this.posY + 1.0,
            this.posZ + OreSpawnMain.OreSpawnRand.nextInt(2) - OreSpawnMain.OreSpawnRand.nextInt(2),
            is);
        if (entityItem != null) {
            this.worldObj.spawnEntityInWorld(entityItem);
        }

        return is;
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        ItemStack is = null;
        int i = 7 + this.worldObj.rand.nextInt(7);

        for (int i2 = 0; i2 < i; i2++) {
            int roll = this.worldObj.rand.nextInt(12);
            switch (roll) {
                case 0:
                    is = this.dropItemRand(Items.redstone, 1);
                    break;
                case 1:
                    is = this.dropItemRand(Items.repeater, 1);
                    break;
                case 2:
                    is = this.dropItemRand(Items.comparator, 1);
                    break;
                case 3:
                    is = this.dropItemRand(Item.getItemFromBlock(Blocks.redstone_block), 1);
                    break;
                case 4:
                    is = this.dropItemRand(Item.getItemFromBlock(Blocks.dispenser), 1);
                    break;
                case 5:
                    is = this.dropItemRand(Item.getItemFromBlock(Blocks.sticky_piston), 1);
                    break;
                case 6:
                    is = this.dropItemRand(Item.getItemFromBlock(Blocks.piston), 1);
                    break;
                case 7:
                    is = this.dropItemRand(Item.getItemFromBlock(Blocks.lever), 1);
                    break;
                case 8:
                    is = this.dropItemRand(Item.getItemFromBlock(Blocks.redstone_block), 1);
                    break;
                case 9:
                    is = this.dropItemRand(Item.getItemFromBlock(Blocks.light_weighted_pressure_plate), 1);
                    break;
                case 10:
                    is = this.dropItemRand(Items.iron_ingot, 1);
            }
        }
    }
}
