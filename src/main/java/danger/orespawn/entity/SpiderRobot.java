package danger.orespawn.entity;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C0CPacketInput;
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

public class SpiderRobot extends EntityLiving {

    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private int playing = 0;
    private GenericTargetSorter TargetSorter = null;
    private float moveSpeed = 0.35F;
    private RenderSpiderRobotInfo renderdata = new RenderSpiderRobotInfo();
    private int didonce = 0;
    private int rideTicker = 0;

    public SpiderRobot(World par1World) {
        super(par1World);
        this.setSize(3.25F, 2.25F);
        this.riddenByEntity = null;
        this.TargetSorter = new GenericTargetSorter(this);
        this.tasks.addTask(0, new EntityAIWatchClosest(this, EntityPlayer.class, 12.0F));
        this.tasks.addTask(1, new EntityAILookIdle(this));
        this.isImmuneToFire = true;
        this.experienceValue = OreSpawnMain.SpiderRobot_stats.health / 2;
    }

    public SpiderRobot(World par1World, double par2, double par4, double par6) {
        this(par1World);
        this.setPosition(par2, par4 + this.yOffset, par6);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(OreSpawnMain.SpiderRobot_stats.health);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        this.getAttributeMap()
            .registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(OreSpawnMain.SpiderRobot_stats.attack);
    }

    protected boolean canDespawn() {
        return false;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.SpiderRobot_stats.defense;
    }

    protected void updateAITasks() {
        if (!this.isDead) {
            if (this.riddenByEntity == null) {
                super.updateAITasks();
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

        for (int i = 0; i < 8; i++) {
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
                this.renderdata.legoff[i] = 1.25F;
                this.renderdata.ymid[i] = -0.32F;
                this.renderdata.yrange[i] = (float) (Math.PI / 12);
                this.renderdata.pairedwith[i] = 1;
                this.renderdata.yoff[i] = -0.3F;
            }

            if (i == 1) {
                this.renderdata.legoff[i] = 1.25F;
                this.renderdata.ymid[i] = 3.4615927F;
                this.renderdata.yrange[i] = (float) (-Math.PI / 12);
                this.renderdata.pairedwith[i] = 0;
                this.renderdata.yoff[i] = -0.3F;
            }

            if (i == 2) {
                this.renderdata.legoff[i] = 2.0F;
                this.renderdata.ymid[i] = -1.0F;
                this.renderdata.yrange[i] = (float) (Math.PI / 12);
                this.renderdata.pairedwith[i] = 3;
                this.renderdata.yoff[i] = -0.1F;
            }

            if (i == 3) {
                this.renderdata.legoff[i] = 2.0F;
                this.renderdata.ymid[i] = 4.1415925F;
                this.renderdata.yrange[i] = (float) (-Math.PI / 12);
                this.renderdata.pairedwith[i] = 2;
                this.renderdata.yoff[i] = -0.1F;
            }

            if (i == 4) {
                this.renderdata.legoff[i] = 1.75F;
                this.renderdata.ymid[i] = (float) (Math.PI / 5);
                this.renderdata.yrange[i] = (float) (Math.PI / 12);
                this.renderdata.pairedwith[i] = 5;
                this.renderdata.yoff[i] = -0.3F;
            }

            if (i == 5) {
                this.renderdata.legoff[i] = 1.75F;
                this.renderdata.ymid[i] = (float) (Math.PI * 4.0 / 5.0);
                this.renderdata.yrange[i] = (float) (-Math.PI / 12);
                this.renderdata.pairedwith[i] = 4;
                this.renderdata.yoff[i] = -0.3F;
            }

            if (i == 6) {
                this.renderdata.legoff[i] = 3.4F;
                this.renderdata.ymid[i] = 1.05F;
                this.renderdata.yrange[i] = (float) (Math.PI / 12);
                this.renderdata.pairedwith[i] = 7;
                this.renderdata.yoff[i] = -0.1F;
            }

            if (i == 7) {
                this.renderdata.legoff[i] = 3.4F;
                this.renderdata.ymid[i] = 2.0915928F;
                this.renderdata.yrange[i] = (float) (-Math.PI / 12);
                this.renderdata.pairedwith[i] = 6;
                this.renderdata.yoff[i] = -0.1F;
            }
        }
    }

    private float getNewVelocity(float v, float diff, float curval) {
        float tv = v * 8.0F;
        if (tv < 1.0F) {
            tv = 1.0F;
        }

        if (tv > 4.0F) {
            tv = 4.0F;
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

            for (int var22 = 0; var22 < 8; var22++) {
                int fcount = 0;
                this.renderdata.footingticker[var22]++;
                this.renderdata.realposx[var22] = (float) (this.posX - this.renderdata.legoff[var22] * Math.sin(
                    Math.toRadians(MathHelper.wrapAngleTo180_double(this.rotationYaw + 90.0F))
                        + this.renderdata.ymid[var22]));
                this.renderdata.realposz[var22] = (float) (this.posZ + this.renderdata.legoff[var22] * Math.cos(
                    Math.toRadians(MathHelper.wrapAngleTo180_double(this.rotationYaw + 90.0F))
                        + this.renderdata.ymid[var22]));
                this.renderdata.realposy[var22] = (float) this.posY + this.renderdata.yoff[var22];
                int it = this.renderdata.footingticker[var22]
                    + this.renderdata.footingticker[this.renderdata.pairedwith[var22]];
                if (it > 50 && this.renderdata.footingticker[var22]
                    > this.renderdata.footingticker[this.renderdata.pairedwith[var22]]) {
                    this.renderdata.footingticker[var22] = 0;
                }

                d1 = this.renderdata.realposx[var22] - this.renderdata.foot_xpos[var22];
                d2 = this.renderdata.realposy[var22] - this.renderdata.foot_ypos[var22];
                d3 = this.renderdata.realposz[var22] - this.renderdata.foot_zpos[var22];
                float dd = (float) Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                dd *= 16.0F;
                float da = (float) (Math.abs(
                    this.renderdata.ycurrentangle[var22]
                        - (Math.toRadians(MathHelper.wrapAngleTo180_double(this.rotationYaw))
                            + this.renderdata.ymid[var22]))
                    % (Math.PI * 2));
                if (da > Math.PI) {
                    da = (float) (da - (Math.PI * 2));
                }

                if (da < -Math.PI) {
                    da = (float) (da + (Math.PI * 2));
                }

                da = Math.abs(da);
                if (dd > 294.0F || dd < 32.0F
                    || da > Math.abs(this.renderdata.yrange[var22]) * 8.0F / 7.0F
                    || Math.abs(this.renderdata.udcurrentangle[var22]) > 1.25
                    || this.renderdata.footingticker[var22] == 0) {
                    this.findNewFooting(var22);
                    d1 = this.renderdata.realposx[var22] - this.renderdata.foot_xpos[var22];
                    d2 = this.renderdata.realposy[var22] - this.renderdata.foot_ypos[var22];
                    d3 = this.renderdata.realposz[var22] - this.renderdata.foot_zpos[var22];
                    dd = (float) Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                    dd *= 16.0F;
                }

                float c1 = (float) (99.0 * Math.cos(this.renderdata.p2xangle[var22] - this.renderdata.p1xangle[var22]));
                float c2 = 99.0F;
                float c3 = (float) (99.0 * Math.cos(this.renderdata.p2xangle[var22] - this.renderdata.p3xangle[var22]));
                float cc = c1 + c2 + c3;
                float diff = cc - dd;
                this.renderdata.pxvelocity[var22] = this
                    .getNewVelocity(realv, (float) (diff * Math.PI / 360.0), this.renderdata.pxvelocity[var22]);
                if (this.renderdata.pxvelocity[var22] == 0.0F || Math.abs(diff) < 8.0F) {
                    fcount++;
                }

                this.renderdata.p1xangle[var22] = this.renderdata.p1xangle[var22] + this.renderdata.pxvelocity[var22];
                this.renderdata.p2xangle[var22] = 0.0;
                this.renderdata.p3xangle[var22] = -this.renderdata.p1xangle[var22];
                if (this.renderdata.uppoint[var22] != 0.0F) {
                    dd = (float) Math
                        .atan2(dd, (this.renderdata.realposy[var22] - this.renderdata.uppoint[var22]) * 16.0);
                } else {
                    dd = (float) Math
                        .atan2(dd, (this.renderdata.realposy[var22] - this.renderdata.foot_ypos[var22]) * 16.0);
                }

                this.renderdata.udwantedangle[var22] = (float) (dd - (Math.PI / 2));

                while (this.renderdata.udwantedangle[var22] > Math.PI) {
                    this.renderdata.udwantedangle[var22] = (float) (this.renderdata.udwantedangle[var22]
                        - (Math.PI * 2));
                }

                while (this.renderdata.udwantedangle[var22] < -Math.PI) {
                    this.renderdata.udwantedangle[var22] = (float) (this.renderdata.udwantedangle[var22]
                        + (Math.PI * 2));
                }

                double rhm = this.renderdata.udwantedangle[var22];
                double rhdir = this.renderdata.udcurrentangle[var22];
                double rdv = (rhm - rhdir) % (Math.PI * 2);

                while (rdv > Math.PI) {
                    rdv -= Math.PI * 2;
                }

                while (rdv < -Math.PI) {
                    rdv += Math.PI * 2;
                }

                diff = (float) rdv;
                this.renderdata.udvelocity[var22] = this
                    .getNewVelocity(realv * 2.0F, diff, this.renderdata.udvelocity[var22]);
                if (this.renderdata.udvelocity[var22] == 0.0F || Math.abs(diff) < 0.03490658503988659) {
                    this.renderdata.uppoint[var22] = 0.0F;
                    fcount++;
                }

                rhdir += this.renderdata.udvelocity[var22];

                while (rhdir > Math.PI) {
                    rhdir -= Math.PI * 2;
                }

                while (rhdir < -Math.PI) {
                    rhdir += Math.PI * 2;
                }

                dd = this.renderdata.udcurrentangle[var22] = (float) rhdir;
                this.renderdata.uddisplayangle[var22] = dd;
                d1 = this.renderdata.realposx[var22] - this.renderdata.foot_xpos[var22];
                d3 = this.renderdata.realposz[var22] - this.renderdata.foot_zpos[var22];
                dd = (float) Math.atan2(d3, d1);
                rhm = this.renderdata.ywantedangle[var22] = dd;
                rhdir = this.renderdata.ycurrentangle[var22];
                rdv = (rhm - rhdir) % (Math.PI * 2);
                if (rdv > Math.PI) {
                    rdv -= Math.PI * 2;
                }

                if (rdv < -Math.PI) {
                    rdv += Math.PI * 2;
                }

                diff = (float) rdv;
                this.renderdata.yvelocity[var22] = this.getNewVelocity(realv, diff, this.renderdata.yvelocity[var22]);
                if (this.renderdata.yvelocity[var22] == 0.0F || Math.abs(diff) < 0.03490658503988659) {
                    fcount++;
                }

                this.renderdata.ycurrentangle[var22] = this.renderdata.ycurrentangle[var22]
                    + this.renderdata.yvelocity[var22];

                while (this.renderdata.ycurrentangle[var22] > Math.PI) {
                    this.renderdata.ycurrentangle[var22] = (float) (this.renderdata.ycurrentangle[var22]
                        - (Math.PI * 2));
                }

                while (this.renderdata.ycurrentangle[var22] < -Math.PI) {
                    this.renderdata.ycurrentangle[var22] = (float) (this.renderdata.ycurrentangle[var22]
                        + (Math.PI * 2));
                }

                dd = (float) (this.renderdata.ycurrentangle[var22]
                    - Math.toRadians(MathHelper.wrapAngleTo180_double(this.rotationYaw))
                    - (Math.PI / 2));

                while (dd > Math.PI) {
                    dd = (float) (dd - (Math.PI * 2));
                }

                while (dd < -Math.PI) {
                    dd = (float) (dd + (Math.PI * 2));
                }

                this.renderdata.ydisplayangle[var22] = dd;
                if (fcount == 3) {
                    this.renderdata.footup[var22] = 0;
                    Block bid = this.worldObj.getBlock(
                        (int) this.renderdata.foot_xpos[var22],
                        (int) this.renderdata.foot_ypos[var22],
                        (int) this.renderdata.foot_zpos[var22]);
                    if (bid == Blocks.tallgrass && this.riddenByEntity != null
                        && this.worldObj.getGameRules()
                            .getGameRuleBooleanValue("mobGriefing")) {
                        this.worldObj.setBlock(
                            (int) this.renderdata.foot_xpos[var22],
                            (int) this.renderdata.foot_ypos[var22],
                            (int) this.renderdata.foot_zpos[var22],
                            Blocks.air);
                    }

                    bid = this.worldObj.getBlock(
                        (int) this.renderdata.foot_xpos[var22],
                        (int) this.renderdata.foot_ypos[var22] - 1,
                        (int) this.renderdata.foot_zpos[var22]);
                    if (bid == Blocks.grass && this.riddenByEntity != null
                        && this.worldObj.getGameRules()
                            .getGameRuleBooleanValue("mobGriefing")) {
                        this.worldObj.setBlock(
                            (int) this.renderdata.foot_xpos[var22],
                            (int) this.renderdata.foot_ypos[var22] - 1,
                            (int) this.renderdata.foot_zpos[var22],
                            Blocks.dirt);
                    }
                }
            }
        }
    }

    private void findNewFooting(int i) {
        float f = 16.0F;
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
        range *= 0.875F;
        if (Math.abs((this.prevRotationYaw - this.rotationYaw) % 360.0F) > 0.75F) {
            range = 0.0F;
        }

        if (i >= 4) {
            f = 10.0F;
        }

        if (rdv > 1.5) {
            range = -range;
            f = 10.0F;
            if (i >= 4) {
                f = 16.0F;
            }
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

        while (found == 0 && f > 3.5F) {
            fx = (float) (this.renderdata.realposx[i] - f * Math.sin(
                Math.toRadians(MathHelper.wrapAngleTo180_double(this.rotationYaw + 90.0F)) + this.renderdata.ymid[i]
                    - range));
            fz = (float) (this.renderdata.realposz[i] + f * Math.cos(
                Math.toRadians(MathHelper.wrapAngleTo180_double(this.rotationYaw + 90.0F)) + this.renderdata.ymid[i]
                    - range));
            fy = this.renderdata.realposy[i];
            int j = 11;

            label102: while (true) {
                if (found == 0 && j > -14) {
                    int m = -span;

                    label97: while (true) {
                        if (found == 0 && m <= span) {
                            int n = -span;

                            while (true) {
                                if (found == 0 && n <= span) {
                                    Block blk = this.worldObj.getBlock((int) fx + m, (int) fy + j, (int) fz + n);
                                    if (blk == Blocks.air
                                        || !this.worldObj.getBlock((int) fx + m, (int) fy + j, (int) fz + n)
                                            .getMaterial()
                                            .isSolid()) {
                                        n++;
                                        continue;
                                    }

                                    fy += j + 1;
                                    fx += m;
                                    fz += n;
                                    found = 1;
                                }

                                m++;
                                continue label97;
                            }
                        }

                        j--;
                        continue label102;
                    }
                }

                if (found == 1) {
                    d1 = this.renderdata.realposx[i] - fx;
                    float d2 = this.renderdata.realposy[i] - fy;
                    d3 = this.renderdata.realposz[i] - fz;
                    float dd = (float) Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                    dd *= 16.0F;
                    if (dd > 294.0F) {
                        found = 0;
                    }
                }

                if (--f < 3.5F && range != 0.0F) {
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
                d1++;
            }

            if (dd > 48.0F) {
                d1++;
            }

            if (dd > 100.0F) {
                d1++;
            }

            this.renderdata.uppoint[i] = d1;
        }
    }

    public boolean shouldRiderSit() {
        return false;
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
        this.dataWatcher.addObject(20, (byte) 0);
    }

    public RenderSpiderRobotInfo getRenderSpiderRobotInfo() {
        return this.renderdata;
    }

    public boolean canBePushed() {
        return false;
    }

    public double getMountedYOffset() {
        return this.riddenByEntity != null && this.riddenByEntity instanceof SpiderDriver ? 2.0
            : 2.625 + Math.cos(this.rideTicker * 0.19F) * 0.02;
    }

    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            float f = -3.0F;
            f = (float) (f + Math.cos(this.rideTicker * 0.33F) * 0.05);
            this.riddenByEntity.setPosition(
                this.posX - f * Math.sin(Math.toRadians(this.rotationYaw)),
                this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(),
                this.posZ + f * Math.cos(Math.toRadians(this.rotationYaw)));
        }
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (par1DamageSource.getDamageType()
            .equals("inWall")) {
            return false;
        } else if (par1DamageSource.getDamageType()
            .equals("cactus")) {
                return false;
            } else if (par1DamageSource.getDamageType()
                .equals("inFire")) {
                    return false;
                } else if (par1DamageSource.getDamageType()
                    .equals("onFire")) {
                        return false;
                    } else if (par1DamageSource.getDamageType()
                        .equals("magic")) {
                            return false;
                        } else {
                            return par1DamageSource.getDamageType()
                                .equals("starve") ? false : super.attackEntityFrom(par1DamageSource, par2);
                        }
    }

    protected void fall(float par1) {}

    protected void updateFallState(double par1, boolean par3) {}

    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
        if (this.riddenByEntity != null) {
            this.boatPosRotationIncrements = par9 + 8;
        } else {
            this.boatPosRotationIncrements = par9 + 6;
        }

        this.boatX = par1;
        this.boatY = par3;
        this.boatZ = par5;
        this.boatYaw = par7;
        this.boatPitch = par8;
    }

    @SideOnly(Side.CLIENT)
    public void setVelocity(double par1, double par3, double par5) {
        if (this.riddenByEntity == null) {
            super.setVelocity(par1, par3, par5);
        }
    }

    public void onUpdate() {
        super.onUpdate();
        this.setFire(0);
        if (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && !this.worldObj.isRemote
            && this.riddenByEntity != null
            && this.worldObj.rand.nextInt(40) == 0) {
            this.feetFindSomethingToHit();
        }

        if (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && !this.worldObj.isRemote
            && this.riddenByEntity != null
            && this.worldObj.rand.nextInt(15) == 0) {
            EntityLivingBase e = null;
            e = this.findSomethingToAttack();
            if (e != null) {
                if (this.getDistanceSqToEntity(e) < (12.0F + e.width / 2.0F) * (12.0F + e.width / 2.0F)) {
                    this.setAttacking(1);
                    this.attackEntityAsMob(e);
                }
            } else {
                this.setAttacking(0);
            }
        }

        float f = 8.0F;
        float dx = (float) (f * Math.cos(Math.toRadians(this.rotationYaw - 90.0F)));
        float dz = (float) (f * Math.sin(Math.toRadians(this.rotationYaw - 90.0F)));
        if (this.worldObj.rand.nextInt(8) == 0) {
            this.worldObj.spawnParticle(
                "flame",
                this.posX + dx,
                this.posY + 2.0,
                this.posZ + dz,
                dx / f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 20.0F,
                (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 10.0F,
                dz / f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 20.0F);
        }

        if (this.worldObj.rand.nextInt(2) == 0) {
            this.worldObj.spawnParticle(
                "smoke",
                this.posX + dx,
                this.posY + 2.0,
                this.posZ + dz,
                dx / f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 20.0F,
                (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 10.0F,
                dz / f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 20.0F);
        }

        if (this.worldObj.rand.nextInt(10) == 0) {
            this.worldObj.spawnParticle(
                "fireworksSpark",
                this.posX + dx,
                this.posY + 2.0,
                this.posZ + dz,
                dx / f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 20.0F,
                (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 5.0F,
                dz / f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) / 20.0F);
        }
    }

    public void onLivingUpdate() {
        List list = null;
        double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        double d6 = this.rand.nextFloat() * 2.0F - 1.0F;
        double d7 = (this.rand.nextInt(2) * 2 - 1) * 0.7;
        double obstruction_factor = 0.0;
        double relative_g = 0.0;
        double max_speed = 0.45;
        double gh = 1.55;
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
                this.worldObj.playSoundAtEntity(this, "orespawn:robotspider", 0.45F, 1.0F);
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
                } else if (this.riddenByEntity instanceof EntityPlayer) {
                    EntityClientPlayerMP pp = (EntityClientPlayerMP) this.riddenByEntity;
                    pp.sendQueue.addToSendQueue(new C05PacketPlayerLook(pp.rotationYaw, pp.rotationPitch, pp.onGround));
                    pp.sendQueue.addToSendQueue(
                        new C0CPacketInput(
                            pp.moveStrafing,
                            pp.moveForward,
                            pp.movementInput.jump,
                            pp.movementInput.sneak));
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
                    gh = 4.25;
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

                if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer) {
                    EntityPlayer pp = (EntityPlayer) this.riddenByEntity;
                    obstruction_factor = 0.0;
                    int var75 = 3;
                    var75 += (int) (velocity * 6.0);

                    for (int k = 1; k < var75; k++) {
                        for (int i = 1; i < var75 * 3; i++) {
                            for (int j = -90; j <= 90; j += 30) {
                                double dx = i * Math.cos(Math.toRadians(this.rotationYaw + 90.0F + j));
                                double dz = i * Math.sin(Math.toRadians(this.rotationYaw + 90.0F + j));
                                Block var79 = this.worldObj
                                    .getBlock((int) (this.posX + dx), (int) this.posY - k, (int) (this.posZ + dz));
                                if (var79 != Blocks.air && var79 != Blocks.water
                                    && var79 != Blocks.flowing_water
                                    && var79 != Blocks.lava
                                    && var79 != Blocks.flowing_lava) {
                                    obstruction_factor += 0.03;
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

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}

    public float getShadowSize() {
        return 0.95F;
    }

    public boolean interact(EntityPlayer par1EntityPlayer) {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.stackSize <= 0) {
            par1EntityPlayer.inventory
                .setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack) null);
            var2 = null;
        }

        if (var2 != null && var2.getItem() == Items.iron_ingot && par1EntityPlayer.getDistanceSqToEntity(this) < 25.0) {
            if (!this.worldObj.isRemote) {
                float f = this.getMaxHealth() - this.getHealth();
                if (f > 100.0F) {
                    f = 100.0F;
                }

                if (f > 0.0F) {
                    this.heal(f);
                }
            }

            if (!par1EntityPlayer.capabilities.isCreativeMode) {
                var2.stackSize--;
                if (var2.stackSize <= 0) {
                    par1EntityPlayer.inventory
                        .setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack) null);
                }
            }

            return true;
        } else if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer
            && this.riddenByEntity != par1EntityPlayer) {
                return true;
            } else {
                if (!this.worldObj.isRemote && this.riddenByEntity == null
                    && par1EntityPlayer.getDistanceSqToEntity(this) < 16.0) {
                    par1EntityPlayer.mountEntity(this);
                    this.worldObj.playSoundAtEntity(this, "orespawn:robotspidermount", 0.65F, 1.0F);
                }

                return true;
            }
    }

    private void feetFindSomethingToHit() {
        if (OreSpawnMain.PlayNicely == 0) {
            List var5 = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(20.0, 8.0, 20.0));
            Iterator var2 = var5.iterator();
            Entity var3 = null;
            EntityLivingBase var4 = null;

            while (var2.hasNext()) {
                var3 = (Entity) var2.next();
                var4 = (EntityLivingBase) var3;
                if (this.feetisSuitableTarget(var4, false)) {
                    this.feetattackEntityAsMob(var4);
                }
            }
        }
    }

    private boolean feetisSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
        if (par1EntityLiving == null) {
            return false;
        } else if (par1EntityLiving == this) {
            return false;
        } else if (!par1EntityLiving.isEntityAlive()) {
            return false;
        } else if (par1EntityLiving instanceof SpiderRobot) {
            return false;
        } else if (par1EntityLiving instanceof EntitySpider) {
            return false;
        } else if (par1EntityLiving instanceof SpiderDriver) {
            return false;
        } else if (par1EntityLiving instanceof EntityCaveSpider) {
            return false;
        } else if (par1EntityLiving == this.riddenByEntity) {
            return false;
        } else {
            float d1 = (float) (par1EntityLiving.posX - this.posX);
            float d2 = (float) (par1EntityLiving.posY - this.posY);
            float d3 = (float) (par1EntityLiving.posZ - this.posZ);
            float dd = (float) Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
            if (dd > 18.0F) {
                return false;
            } else if (dd < 12.0F) {
                return false;
            } else if (par1EntityLiving instanceof EntityPlayer) {
                EntityPlayer p = (EntityPlayer) par1EntityLiving;
                return !p.capabilities.isCreativeMode;
            } else {
                return true;
            }
        }
    }

    public boolean feetattackEntityAsMob(Entity par1Entity) {
        boolean ret = false;
        if (par1Entity != null && par1Entity instanceof EntityLivingBase) {
            double ks = 0.6;
            double inair = 0.1;
            float f3 = (float) Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
            ret = par1Entity
                .attackEntityFrom(DamageSource.causeMobDamage(this), OreSpawnMain.SpiderRobot_stats.attack / 10.0F);
            if (par1Entity.isDead || par1Entity instanceof EntityPlayer) {
                inair *= 2.0;
            }

            if (ret) {
                par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
        }

        return ret;
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            List var5 = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(20.0, 12.0, 20.0));
            Iterator var2 = var5.iterator();
            Entity var3 = null;
            EntityLivingBase var4 = null;

            while (var2.hasNext()) {
                var3 = (Entity) var2.next();
                var4 = (EntityLivingBase) var3;
                if (this.isSuitableTarget(var4, false)) {
                    return var4;
                }
            }

            return null;
        }
    }

    private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
        if (par1EntityLiving == null) {
            return false;
        } else if (par1EntityLiving == this) {
            return false;
        } else if (!par1EntityLiving.isEntityAlive()) {
            return false;
        } else if (par1EntityLiving instanceof SpiderRobot) {
            return false;
        } else if (par1EntityLiving instanceof EntitySpider) {
            return false;
        } else if (par1EntityLiving instanceof SpiderDriver) {
            return false;
        } else if (par1EntityLiving instanceof EntityCaveSpider) {
            return false;
        } else if (par1EntityLiving == this.riddenByEntity) {
            return false;
        } else if (MyUtils.isIgnoreable(par1EntityLiving)) {
            return false;
        } else if (!this.getEntitySenses()
            .canSee(par1EntityLiving)) {
                return false;
            } else {
                double rr = Math.atan2(par1EntityLiving.posZ - this.posZ, par1EntityLiving.posX - this.posX);
                double rhdir = Math.toRadians((this.rotationYaw + 90.0F) % 360.0F);
                double pi = 3.1415926545;
                double rdd = Math.abs(rr - rhdir) % (pi * 2.0);
                if (rdd > pi) {
                    rdd -= pi * 2.0;
                }

                rdd = Math.abs(rdd);
                if (this.getDistanceSqToEntity(par1EntityLiving) < 36.0) {
                    return true;
                } else if (rdd > 0.75) {
                    return false;
                } else if (par1EntityLiving instanceof EntityPlayer) {
                    EntityPlayer p = (EntityPlayer) par1EntityLiving;
                    return !p.capabilities.isCreativeMode;
                } else {
                    return true;
                }
            }
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        boolean ret = false;
        if (par1Entity != null && par1Entity instanceof EntityLivingBase) {
            double ks = 1.2;
            double inair = 0.15;
            float f3 = (float) Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
            ret = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), OreSpawnMain.SpiderRobot_stats.attack);
            if (par1Entity.isDead || par1Entity instanceof EntityPlayer) {
                inair *= 2.0;
            }

            if (ret) {
                par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
        }

        return ret;
    }

    public int getAttacking() {
        return this.dataWatcher.getWatchableObjectByte(20);
    }

    public void setAttacking(int par1) {
        this.dataWatcher.updateObject(20, (byte) par1);
    }

    protected Item getDropItem() {
        return null;
    }

    private ItemStack dropItemRand(Item index, int par1) {
        EntityItem var3 = null;
        ItemStack is = new ItemStack(index, par1, 0);
        var3 = new EntityItem(
            this.worldObj,
            this.posX + OreSpawnMain.OreSpawnRand.nextInt(2) - OreSpawnMain.OreSpawnRand.nextInt(2),
            this.posY + 1.0,
            this.posZ + OreSpawnMain.OreSpawnRand.nextInt(2) - OreSpawnMain.OreSpawnRand.nextInt(2),
            is);
        if (var3 != null) {
            this.worldObj.spawnEntityInWorld(var3);
        }

        return is;
    }

    protected void dropFewItems(boolean par1, int par2) {
        ItemStack is = null;
        int i = 14 + this.worldObj.rand.nextInt(14);

        for (int var4 = 0; var4 < i; var4++) {
            int var3 = this.worldObj.rand.nextInt(15);
            switch (var3) {
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
            }
        }
    }
}
