package danger.orespawn.entity;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.MyEntityAIDance;
import danger.orespawn.entity.ai.MyEntityAIFollowOwner;
import danger.orespawn.entity.ai.MyEntityAIJealousy;
import danger.orespawn.entity.ai.MyEntityAINearestAttackableTarget;
import danger.orespawn.entity.ai.MyEntityAIWander;
import danger.orespawn.entity.ai.MyValentineTarget;
import danger.orespawn.entity.projectile.Shoes;
import danger.orespawn.entity.projectile.UltimateArrow;
import danger.orespawn.item.ItemOreSpawnArmor;

public class Girlfriend extends EntityTameable implements IRangedAttackMob {

    public int which_girl = 0;
    public int which_wet_girl = 0;
    public int wet_count = 0;
    private int auto_heal = 200;
    private int force_sync = 50;
    private int fight_sound_ticker = 0;
    private int taunt_sound_ticker = 0;
    private int had_target = 0;
    private int voice = 0;
    private int is_princess = 0;
    public MyEntityAIDance Dance = null;
    private float moveSpeed = 0.3F;
    private int voice_enable = 1;
    public int passenger = 0;
    public int feelingBetter = 0;
    private static final ResourceLocation DryTexture0 = new ResourceLocation("orespawn", "girlfriend0.png");
    private static final ResourceLocation DryTexture1 = new ResourceLocation("orespawn", "girlfriend1.png");
    private static final ResourceLocation DryTexture2 = new ResourceLocation("orespawn", "girlfriend2.png");
    private static final ResourceLocation DryTexture3 = new ResourceLocation("orespawn", "girlfriend3.png");
    private static final ResourceLocation DryTexture4 = new ResourceLocation("orespawn", "girlfriend4.png");
    private static final ResourceLocation DryTexture5 = new ResourceLocation("orespawn", "girlfriend5.png");
    private static final ResourceLocation DryTexture6 = new ResourceLocation("orespawn", "girlfriend6.png");
    private static final ResourceLocation DryTexture7 = new ResourceLocation("orespawn", "girlfriend7.png");
    private static final ResourceLocation DryTexture8 = new ResourceLocation("orespawn", "girlfriend8.png");
    private static final ResourceLocation DryTexture9 = new ResourceLocation("orespawn", "girlfriend9.png");
    private static final ResourceLocation DryTexture10 = new ResourceLocation("orespawn", "girlfriend10.png");
    private static final ResourceLocation DryTexture11 = new ResourceLocation("orespawn", "girlfriend11.png");
    private static final ResourceLocation DryTexture12 = new ResourceLocation("orespawn", "girlfriend12.png");
    private static final ResourceLocation DryTexture13 = new ResourceLocation("orespawn", "girlfriend13.png");
    private static final ResourceLocation DryTexture14 = new ResourceLocation("orespawn", "girlfriend14.png");
    private static final ResourceLocation DryTexture15 = new ResourceLocation("orespawn", "girlfriend15.png");
    private static final ResourceLocation DryTexture16 = new ResourceLocation("orespawn", "girlfriend16.png");
    private static final ResourceLocation DryTexture17 = new ResourceLocation("orespawn", "girlfriend17.png");
    private static final ResourceLocation DryTexture18 = new ResourceLocation("orespawn", "girlfriend18.png");
    private static final ResourceLocation DryTexture19 = new ResourceLocation("orespawn", "girlfriend19.png");
    private static final ResourceLocation DryTexture20 = new ResourceLocation("orespawn", "girlfriend20.png");
    private static final ResourceLocation DryTexture21 = new ResourceLocation("orespawn", "girlfriend21.png");
    private static final ResourceLocation DryTexture22 = new ResourceLocation("orespawn", "girlfriend22.png");
    private static final ResourceLocation DryTexture23 = new ResourceLocation("orespawn", "girlfriend23.png");
    private static final ResourceLocation DryTexture24 = new ResourceLocation("orespawn", "girlfriend24.png");
    private static final ResourceLocation DryTexture25 = new ResourceLocation("orespawn", "girlfriend25.png");
    private static final ResourceLocation DryTexture26 = new ResourceLocation("orespawn", "girlfriend26.png");
    private static final ResourceLocation DryTexture27 = new ResourceLocation("orespawn", "girlfriend27.png");
    private static final ResourceLocation DryTexture28 = new ResourceLocation("orespawn", "girlfriend28.png");
    private static final ResourceLocation DryTexture29 = new ResourceLocation("orespawn", "girlfriend29.png");
    private static final ResourceLocation DryTexture30 = new ResourceLocation("orespawn", "girlfriend30.png");
    private static final ResourceLocation DryTexture31 = new ResourceLocation("orespawn", "girlfriend31.png");
    private static final ResourceLocation DryTexture32 = new ResourceLocation("orespawn", "girlfriend32.png");
    private static final ResourceLocation DryTexture33 = new ResourceLocation("orespawn", "girlfriend33.png");
    private static final ResourceLocation DryTexture34 = new ResourceLocation("orespawn", "girlfriend34.png");
    private static final ResourceLocation DryTexture35 = new ResourceLocation("orespawn", "girlfriend35.png");
    private static final ResourceLocation DryTexture36 = new ResourceLocation("orespawn", "girlfriend36.png");
    private static final ResourceLocation DryTexture37 = new ResourceLocation("orespawn", "girlfriend37.png");
    private static final ResourceLocation DryTexture38 = new ResourceLocation("orespawn", "girlfriend38.png");
    private static final ResourceLocation DryTexture39 = new ResourceLocation("orespawn", "girlfriend39.png");
    private static final ResourceLocation DryTexture40 = new ResourceLocation("orespawn", "girlfriend40.png");
    private static final ResourceLocation ValentineTexture = new ResourceLocation("orespawn", "girlfriendv.png");
    private static final ResourceLocation WetTexture0 = new ResourceLocation("orespawn", "bikini0.png");
    private static final ResourceLocation WetTexture1 = new ResourceLocation("orespawn", "bikini1.png");
    private static final ResourceLocation WetTexture2 = new ResourceLocation("orespawn", "bikini2.png");
    private static final ResourceLocation WetTexture3 = new ResourceLocation("orespawn", "bikini3.png");
    private static final ResourceLocation WetTexture4 = new ResourceLocation("orespawn", "bikini4.png");
    private static final ResourceLocation WetTexture5 = new ResourceLocation("orespawn", "bikini5.png");
    private static final ResourceLocation WetTexture6 = new ResourceLocation("orespawn", "bikini6.png");
    private static final ResourceLocation WetTexture7 = new ResourceLocation("orespawn", "bikini7.png");
    private static final ResourceLocation WetTexture8 = new ResourceLocation("orespawn", "bikini8.png");
    private static final ResourceLocation WetTexture9 = new ResourceLocation("orespawn", "bikini9.png");
    private static final ResourceLocation WetTexture10 = new ResourceLocation("orespawn", "bikini10.png");
    private static final ResourceLocation WetTexture11 = new ResourceLocation("orespawn", "bikini11.png");
    private static final ResourceLocation WetTexture12 = new ResourceLocation("orespawn", "bikini12.png");
    private static final ResourceLocation WetTexture13 = new ResourceLocation("orespawn", "bikini13.png");
    private static final ResourceLocation WetTexture14 = new ResourceLocation("orespawn", "bikini14.png");
    private static final ResourceLocation WetTexture15 = new ResourceLocation("orespawn", "bikini15.png");
    private static final ResourceLocation WetTexture16 = new ResourceLocation("orespawn", "bikini16.png");
    private static final ResourceLocation WetTexture17 = new ResourceLocation("orespawn", "bikini17.png");
    private static final ResourceLocation PrincessTexture1 = new ResourceLocation("orespawn", "FrogPrincess.png");
    private static final ResourceLocation PrincessTexture2 = new ResourceLocation("orespawn", "FrogPrincess2.png");

    public Girlfriend(World world) {
        super(world);
        this.which_girl = this.rand.nextInt(41);
        this.which_wet_girl = this.rand.nextInt(18);
        this.voice = this.rand.nextInt(10);
        this.setSize(0.5F, 1.6F);
        if (OreSpawnMain.valentines_day != 0) {
            this.setSize(2.5F, 8.0F);
        }

        this.isImmuneToFire = true;
        this.fireResistance = 100;
        this.getNavigator()
            .setAvoidsWater(false);
        this.setSitting(false);
        this.tasks.addTask(1, new MyEntityAIFollowOwner(this, 1.4F, 12.0F, 1.5F));
        this.tasks.addTask(2, new EntityAITempt(this, 1.25, Item.getItemFromBlock(Blocks.red_flower), false));
        this.Dance = new MyEntityAIDance(this);
        this.tasks.addTask(3, this.Dance);
        this.tasks.addTask(4, new EntityAIArrowAttack(this, 1.25, 20, 10.0F));
        this.tasks.addTask(5, new EntityAISwimming(this));
        this.tasks.addTask(6, new EntityAIPanic(this, 1.5));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new MyEntityAIWander(this, 0.75F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.tasks.addTask(10, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(11, new EntityAIMoveIndoors(this));
        this.targetTasks.addTask(1, new MyValentineTarget(this, EntityPlayer.class, 16.0F, 0, true, true));
        this.targetTasks.addTask(2, new MyValentineTarget(this, Boyfriend.class, 16.0F, 0, true, true));
        if (OreSpawnMain.PlayNicely == 0) {
            this.targetTasks.addTask(
                2,
                new MyEntityAINearestAttackableTarget(
                    this,
                    EntityCreeper.class,
                    20.0F,
                    0,
                    true,
                    true,
                    IMob.mobSelector));
        }

        if (OreSpawnMain.PlayNicely == 0) {
            this.targetTasks.addTask(
                3,
                new MyEntityAINearestAttackableTarget(
                    this,
                    EntityLiving.class,
                    15.0F,
                    0,
                    true,
                    true,
                    IMob.mobSelector));
        }

        if (OreSpawnMain.PlayNicely == 0) {
            this.targetTasks.addTask(4, new MyEntityAIJealousy(this, Girlfriend.class, 6.0F, 5, true));
        }

        if (OreSpawnMain.PlayNicely == 0) {
            this.targetTasks.addTask(5, new MyEntityAIJealousy(this, Girlfriend.class, 3.0F, 15, true));
        }

        this.experienceValue = 0;
    }

    protected void entityInit() {
        super.entityInit();
        this.which_girl = this.rand.nextInt(41);
        this.dataWatcher.addObject(20, this.which_girl);
        this.wet_count = 0;
        this.which_wet_girl = this.rand.nextInt(18);
        this.dataWatcher.addObject(22, this.which_wet_girl);
        this.voice = this.rand.nextInt(10);
        this.dataWatcher.addObject(21, this.voice);
        this.dataWatcher.addObject(23, this.voice_enable);
        this.dataWatcher.addObject(24, this.is_princess);
        this.dataWatcher.addObject(25, this.feelingBetter);
        this.auto_heal = 200;
        this.force_sync = 50;
        this.fight_sound_ticker = 0;
        this.taunt_sound_ticker = 0;
        this.had_target = 0;
        this.setSitting(false);
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
            .setBaseValue(8.0);
    }

    public int getTotalArmorValue() {
        int i = 0;

        for (ItemStack itemstack : this.getLastActiveItems()) {
            if (itemstack != null && itemstack.getItem() instanceof ItemArmor) {
                int l = ((ItemArmor) itemstack.getItem()).damageReduceAmount;
                i += l;
            }
        }

        if (i < 8) {
            i = 8;
        }

        if (i > 23) {
            i = 23;
        }

        return i;
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
        this.passenger = 0;
        if (this.isTamed() && !this.isSitting()) {
            Entity e = this.getOwner();
            if (e != null && e instanceof EntityPlayer) {
                EntityPlayer p = (EntityPlayer) e;
                Entity r = e.ridingEntity;
                if (r != null && r instanceof Elevator) {
                    float f = -0.45F;
                    this.setPosition(
                        r.posX - f * Math.sin(Math.toRadians(r.rotationYaw)),
                        r.posY,
                        r.posZ + f * Math.cos(Math.toRadians(r.rotationYaw)));
                    this.rotationYaw = r.rotationYaw;
                    this.rotationPitch = r.rotationPitch;
                    this.limbSwing = this.limbSwingAmount = 0.0F;
                    this.fallDistance = 0.0F;
                    this.passenger = 1;
                }
            }
        }
    }

    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger("GirlType", this.getTameSkin());
        tag.setInteger("WetGirlType", this.getWetTameSkin());
        tag.setInteger("GirlVoice", this.dataWatcher.getWatchableObjectInt(21));
        tag.setInteger("GirlVoiceEnable", this.dataWatcher.getWatchableObjectInt(23));
        tag.setInteger("IsPrincess", this.dataWatcher.getWatchableObjectInt(24));
        tag.setInteger("feelingBetter", this.dataWatcher.getWatchableObjectInt(25));
    }

    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        this.which_girl = tag.getInteger("GirlType");
        this.setTameSkin(this.which_girl);
        this.which_wet_girl = tag.getInteger("WetGirlType");
        this.setWetTameSkin(this.which_wet_girl);
        this.voice = tag.getInteger("GirlVoice");
        this.dataWatcher.updateObject(21, this.voice);
        this.voice_enable = tag.getInteger("GirlVoiceEnable");
        this.dataWatcher.updateObject(23, this.voice_enable);
        this.is_princess = tag.getInteger("IsPrincess");
        this.dataWatcher.updateObject(24, this.is_princess);
        this.feelingBetter = tag.getInteger("feelingBetter");
        this.dataWatcher.updateObject(25, this.feelingBetter);
        if (OreSpawnMain.valentines_day != 0 && this.feelingBetter != 0) {
            this.setSize(0.5F, 1.6F);
        }
    }

    protected void updateAITick() {
        super.updateAITick();
        ItemStack stack = this.getCurrentEquippedItem();
        Entity victim = this.getAttackTarget();
        if (OreSpawnMain.PlayNicely != 0) {
            victim = null;
        }

        if (this.worldObj.rand.nextInt(100) == 1) {
            this.setRevengeTarget(null);
        }

        if (this.worldObj.rand.nextInt(200) == 1) {
            this.setAttackTarget(null);
        }

        if (stack != null && !this.isSitting()) {
            if (victim != null) {
                if (victim instanceof EntityLivingBase && this.getHeldItem() != null) {
                    if (this.getDistanceToEntity(victim) < 4.0F
                        || stack.getItem() == OreSpawnMain.MyBertha && this.getDistanceToEntity(victim) < 10.0F) {
                        this.attackTime--;
                        if (this.attackTime <= 0) {
                            this.attackTime = 25;
                            this.swingItem();
                            this.attackTargetEntityWithCurrentItem(victim);
                            this.fight_sound_ticker--;
                            if (this.fight_sound_ticker <= 0) {
                                if (!this.worldObj.isRemote && this.voice_enable != 0) {
                                    this.worldObj
                                        .playSoundAtEntity(this, "orespawn:o_fight", 0.5F, this.getSoundPitch());
                                }

                                this.fight_sound_ticker = 3;
                            }

                            this.had_target = 1;
                        }
                    } else
                        if (this.getDistanceToEntity(victim) < 7.0F && stack.getItem() != OreSpawnMain.MyUltimateBow) {
                            this.taunt_sound_ticker--;
                            if (this.taunt_sound_ticker <= 0) {
                                if (!this.worldObj.isRemote && this.voice_enable != 0) {
                                    this.worldObj
                                        .playSoundAtEntity(this, "orespawn:o_taunt", 0.5F, this.getSoundPitch());
                                }

                                this.taunt_sound_ticker = 300;
                            }

                            this.getNavigator()
                                .tryMoveToEntityLiving(victim, 1.25);
                        }
                }
            } else {
                this.fight_sound_ticker = 0;
                this.attackTime = 0;
                if (this.had_target != 0) {
                    this.had_target = 0;
                    if (!this.worldObj.isRemote && this.voice_enable != 0) {
                        this.worldObj.playSoundAtEntity(this, "orespawn:o_woohoo", 0.4F, this.getSoundPitch());
                    }
                }
            }
        }
    }

    public void setPrincess(int n) {
        this.is_princess = n;
    }

    public ResourceLocation getTexture() {
        if (OreSpawnMain.valentines_day != 0 && this.feelingBetter == 0) {
            return ValentineTexture;
        } else {
            if (this.wet_count <= 0) {
                int txture = this.getTameSkin();
                if (this.is_princess == 1) {
                    return PrincessTexture1;
                }

                if (this.is_princess == 2) {
                    return PrincessTexture2;
                }

                if (txture == 0) {
                    return DryTexture0;
                }

                if (txture == 1) {
                    return DryTexture1;
                }

                if (txture == 2) {
                    return DryTexture2;
                }

                if (txture == 3) {
                    return DryTexture3;
                }

                if (txture == 4) {
                    return DryTexture4;
                }

                if (txture == 5) {
                    return DryTexture5;
                }

                if (txture == 6) {
                    return DryTexture6;
                }

                if (txture == 7) {
                    return DryTexture7;
                }

                if (txture == 8) {
                    return DryTexture8;
                }

                if (txture == 9) {
                    return DryTexture9;
                }

                if (txture == 10) {
                    return DryTexture10;
                }

                if (txture == 11) {
                    return DryTexture11;
                }

                if (txture == 12) {
                    return DryTexture12;
                }

                if (txture == 13) {
                    return DryTexture13;
                }

                if (txture == 14) {
                    return DryTexture14;
                }

                if (txture == 15) {
                    return DryTexture15;
                }

                if (txture == 16) {
                    return DryTexture16;
                }

                if (txture == 17) {
                    return DryTexture17;
                }

                if (txture == 18) {
                    return DryTexture18;
                }

                if (txture == 19) {
                    return DryTexture19;
                }

                if (txture == 20) {
                    return DryTexture20;
                }

                if (txture == 21) {
                    return DryTexture21;
                }

                if (txture == 22) {
                    return DryTexture22;
                }

                if (txture == 23) {
                    return DryTexture23;
                }

                if (txture == 24) {
                    return DryTexture24;
                }

                if (txture == 25) {
                    return DryTexture25;
                }

                if (txture == 26) {
                    return DryTexture26;
                }

                if (txture == 27) {
                    return DryTexture27;
                }

                if (txture == 28) {
                    return DryTexture28;
                }

                if (txture == 29) {
                    return DryTexture29;
                }

                if (txture == 30) {
                    return DryTexture30;
                }

                if (txture == 31) {
                    return DryTexture31;
                }

                if (txture == 32) {
                    return DryTexture32;
                }

                if (txture == 33) {
                    return DryTexture33;
                }

                if (txture == 34) {
                    return DryTexture34;
                }

                if (txture == 35) {
                    return DryTexture35;
                }

                if (txture == 36) {
                    return DryTexture36;
                }

                if (txture == 37) {
                    return DryTexture37;
                }

                if (txture == 38) {
                    return DryTexture38;
                }

                if (txture == 39) {
                    return DryTexture39;
                }

                if (txture == 40) {
                    return DryTexture40;
                }
            } else {
                int temp = this.getWetTameSkin();
                if (temp == 0) {
                    return WetTexture0;
                }

                if (temp == 1) {
                    return WetTexture1;
                }

                if (temp == 2) {
                    return WetTexture2;
                }

                if (temp == 3) {
                    return WetTexture3;
                }

                if (temp == 4) {
                    return WetTexture4;
                }

                if (temp == 5) {
                    return WetTexture5;
                }

                if (temp == 6) {
                    return WetTexture6;
                }

                if (temp == 7) {
                    return WetTexture7;
                }

                if (temp == 8) {
                    return WetTexture8;
                }

                if (temp == 9) {
                    return WetTexture9;
                }

                if (temp == 10) {
                    return WetTexture10;
                }

                if (temp == 11) {
                    return WetTexture11;
                }

                if (temp == 12) {
                    return WetTexture12;
                }

                if (temp == 13) {
                    return WetTexture13;
                }

                if (temp == 14) {
                    return WetTexture14;
                }

                if (temp == 15) {
                    return WetTexture15;
                }

                if (temp == 16) {
                    return WetTexture16;
                }

                if (temp == 17) {
                    return WetTexture17;
                }
            }

            return null;
        }
    }

    public int getTameSkin() {
        return this.dataWatcher.getWatchableObjectInt(20);
    }

    public int getVoice() {
        return this.dataWatcher.getWatchableObjectInt(21);
    }

    public void setTameSkin(int n) {
        this.dataWatcher.updateObject(20, n);
        this.which_girl = n;
    }

    public int getWetTameSkin() {
        return this.dataWatcher.getWatchableObjectInt(22);
    }

    public void setWetTameSkin(int n) {
        this.dataWatcher.updateObject(22, n);
        this.which_wet_girl = n;
    }

    public boolean isAIEnabled() {
        return true;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    protected void fall(float distance) {
        float i = MathHelper.ceiling_float_int(distance - 3.0F);
        if (i > 0.0F) {
            if (i > 3.0F) {
                this.playSound("damage.fallbig", 1.0F, 1.0F);
                i = 3.0F;
            } else {
                this.playSound("damage.fallsmall", 1.0F, 1.0F);
            }

            this.attackEntityFrom(DamageSource.fall, i);
        }
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.valentines_day != 0 && this.feelingBetter == 0 ? 800 : 80;
    }

    public void onLivingUpdate() {
        this.updateArmSwingProgress();
        super.onLivingUpdate();
        if (this.isInWater() || this.handleLavaMovement()) {
            this.wet_count = 500;
        } else if (this.wet_count > 0) {
            this.wet_count--;
        }

        this.auto_heal--;
        if (this.auto_heal <= 0) {
            if (this.mygetMaxHealth() > this.getGirlfriendHealth()) {
                this.heal(1.0F);
            }

            this.auto_heal = 100;
        }

        this.force_sync--;
        if (this.force_sync <= 0) {
            this.force_sync = 20;
            if (!this.worldObj.isRemote) {
                this.dataWatcher.updateObject(21, this.voice);
                this.dataWatcher.updateObject(23, this.voice_enable);
                this.dataWatcher.updateObject(24, this.is_princess);
                this.dataWatcher.updateObject(25, this.feelingBetter);
                this.setSitting(this.isSitting());
            } else {
                this.voice = this.getVoice();
                this.voice_enable = this.dataWatcher.getWatchableObjectInt(23);
                int nowfeeling = this.dataWatcher.getWatchableObjectInt(25);
                if (nowfeeling != this.feelingBetter && nowfeeling != 0) {
                    this.feelingBetter = nowfeeling;
                    this.setSize(0.5F, 1.6F);
                }
            }
        }
    }

    public int getGirlfriendHealth() {
        return (int) this.getHealth();
    }

    public boolean interact(EntityPlayer player) {
        ItemStack currentItem = player.inventory.getCurrentItem();
        if (currentItem != null && currentItem.stackSize <= 0) {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
            currentItem = null;
        }

        if (currentItem != null
            && (currentItem.getItem() == Item.getItemFromBlock(Blocks.red_flower)
                || currentItem.getItem() == Item.getItemFromBlock(OreSpawnMain.CrystalFlowerRedBlock))
            && player.getDistanceSqToEntity(this) < 16.0) {
            if (!this.isTamed()) {
                if (!this.worldObj.isRemote) {
                    if (this.rand.nextInt(3) == 0) {
                        this.setTamed(true);
                        this.func_152115_b(
                            player.getUniqueID()
                                .toString());
                        this.playTameEffect(true);
                        this.worldObj.setEntityState(this, (byte) 7);
                        this.heal(this.mygetMaxHealth() - this.getHealth());
                    } else {
                        this.playTameEffect(false);
                        this.worldObj.setEntityState(this, (byte) 6);
                    }
                }
            } else if (this.func_152114_e(player)) {
                if (this.worldObj.isRemote) {
                    this.playTameEffect(true);
                    this.worldObj.setEntityState(this, (byte) 7);
                }

                if (this.mygetMaxHealth() > this.getHealth()) {
                    this.heal(this.mygetMaxHealth() - this.getHealth());
                }
            }

            if (!player.capabilities.isCreativeMode) {
                currentItem.stackSize--;
                if (currentItem.stackSize <= 0) {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                }
            }

            return true;
        } else if (this.isTamed() && currentItem != null
            && currentItem.getItem() == Item.getItemFromBlock(Blocks.deadbush)
            && player.getDistanceSqToEntity(this) < 16.0
            && this.func_152114_e(player)) {
                if (!this.worldObj.isRemote) {
                    this.setTamed(false);
                    this.func_152115_b("");
                    this.playTameEffect(false);
                    this.worldObj.setEntityState(this, (byte) 6);
                }

                if (!player.capabilities.isCreativeMode) {
                    currentItem.stackSize--;
                    if (currentItem.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                    }
                }

                return true;
            } else if (this.isTamed() && currentItem != null
                && currentItem.getItem() == OreSpawnMain.MyRuby
                && player.getDistanceSqToEntity(this) < 16.0
                && this.func_152114_e(player)) {
                    if (!this.worldObj.isRemote) {
                        this.voice_enable = 0;
                        this.dataWatcher.updateObject(23, this.voice_enable);
                        this.playTameEffect(true);
                        this.worldObj.setEntityState(this, (byte) 7);
                    }

                    if (!player.capabilities.isCreativeMode) {
                        currentItem.stackSize--;
                        if (currentItem.stackSize <= 0) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                        }
                    }

                    return true;
                } else if (this.isTamed() && currentItem != null
                    && currentItem.getItem() == OreSpawnMain.MyAmethyst
                    && player.getDistanceSqToEntity(this) < 16.0
                    && this.func_152114_e(player)) {
                        if (!this.worldObj.isRemote) {
                            this.voice_enable = 1;
                            this.dataWatcher.updateObject(23, this.voice_enable);
                            this.playTameEffect(true);
                            this.worldObj.setEntityState(this, (byte) 7);
                        }

                        if (!player.capabilities.isCreativeMode) {
                            currentItem.stackSize--;
                            if (currentItem.stackSize <= 0) {
                                player.inventory
                                    .setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                            }
                        }

                        return true;
                    } else if (this.isTamed() && currentItem != null
                        && (currentItem.getItem() == Item.getItemFromBlock(Blocks.yellow_flower)
                            || currentItem.getItem() == Item.getItemFromBlock(OreSpawnMain.CrystalFlowerYellowBlock))
                        && player.getDistanceSqToEntity(this) < 16.0
                        && this.func_152114_e(player)) {
                            if (!this.worldObj.isRemote) {
                                if (this.wet_count <= 0 && !this.isInWater() && !this.handleLavaMovement()) {
                                    this.which_girl++;
                                    if (this.which_girl > 40) {
                                        this.which_girl = 0;
                                    }

                                    this.setTameSkin(this.which_girl);
                                    this.worldObj.setEntityState(this, (byte) 7);
                                } else {
                                    this.which_wet_girl++;
                                    if (this.which_wet_girl > 17) {
                                        this.which_wet_girl = 0;
                                    }

                                    this.setWetTameSkin(this.which_wet_girl);
                                    this.worldObj.setEntityState(this, (byte) 7);
                                    if (this.isInWater() || this.handleLavaMovement()) {
                                        this.wet_count = 500;
                                    }
                                }
                            }

                            if (!player.capabilities.isCreativeMode) {
                                currentItem.stackSize--;
                                if (currentItem.stackSize <= 0) {
                                    player.inventory
                                        .setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                                }
                            }

                            return true;
                        } else if (this.isTamed() && currentItem != null
                            && this.func_152114_e(player)
                            && player.getDistanceSqToEntity(this) < 16.0) {
                                if (currentItem.getItem() instanceof ItemFood) {
                                    if (!this.worldObj.isRemote) {
                                        ItemFood item = (ItemFood) currentItem.getItem();
                                        if (this.mygetMaxHealth() > this.getHealth()) {
                                            this.heal(item.func_150905_g(currentItem) * 5);
                                        }

                                        this.playTameEffect(true);
                                        this.worldObj.setEntityState(this, (byte) 7);
                                    }

                                    if (!player.capabilities.isCreativeMode) {
                                        currentItem.stackSize--;
                                        if (currentItem.stackSize <= 0) {
                                            player.inventory.setInventorySlotContents(
                                                player.inventory.currentItem,
                                                (ItemStack) null);
                                        }
                                    }
                                } else {
                                    if (!this.worldObj.isRemote) {
                                        this.playTameEffect(true);
                                        this.worldObj.setEntityState(this, (byte) 7);
                                    }

                                    ItemStack heldItem = this.getCurrentEquippedItem();
                                    this.setCurrentItemOrArmor(0, currentItem);
                                    if (currentItem.getItem() == Items.diamond) {
                                        this.setSitting(true);
                                    } else {
                                        this.setSitting(false);
                                    }

                                    if (heldItem != null) {
                                        player.inventory
                                            .setInventorySlotContents(player.inventory.currentItem, heldItem);
                                    } else {
                                        player.inventory
                                            .setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                                        Item itm = currentItem.getItem();
                                        if (itm instanceof ItemOreSpawnArmor) {
                                            if (itm == OreSpawnMain.CrystalPinkHelmet
                                                || itm == OreSpawnMain.TigersEyeHelmet) {
                                                ItemStack v4 = this.getEquipmentInSlot(4);
                                                this.setCurrentItemOrArmor(4, currentItem);
                                                this.setCurrentItemOrArmor(0, v4);
                                            }

                                            if (itm == OreSpawnMain.CrystalPinkBody
                                                || itm == OreSpawnMain.TigersEyeBody) {
                                                ItemStack v4 = this.getEquipmentInSlot(3);
                                                this.setCurrentItemOrArmor(3, currentItem);
                                                this.setCurrentItemOrArmor(0, v4);
                                            }

                                            if (itm == OreSpawnMain.CrystalPinkLegs
                                                || itm == OreSpawnMain.TigersEyeLegs) {
                                                ItemStack v4 = this.getEquipmentInSlot(2);
                                                this.setCurrentItemOrArmor(2, currentItem);
                                                this.setCurrentItemOrArmor(0, v4);
                                            }

                                            if (itm == OreSpawnMain.CrystalPinkBoots
                                                || itm == OreSpawnMain.TigersEyeBoots) {
                                                ItemStack v4 = this.getEquipmentInSlot(1);
                                                this.setCurrentItemOrArmor(1, currentItem);
                                                this.setCurrentItemOrArmor(0, v4);
                                            }
                                        }
                                    }
                                }

                                return true;
                            } else if (this.isTamed() && currentItem != null
                                && currentItem.getItem() == Item.getItemFromBlock(Blocks.diamond_block)
                                && player.getDistanceSqToEntity(this) < 16.0) {
                                    this.setSitting(false);
                                    this.setTamed(true);
                                    this.func_152115_b(
                                        player.getUniqueID()
                                            .toString());
                                    this.playTameEffect(true);
                                    this.worldObj.setEntityState(this, (byte) 7);
                                    if (!player.capabilities.isCreativeMode) {
                                        currentItem.stackSize--;
                                        if (currentItem.stackSize <= 0) {
                                            player.inventory.setInventorySlotContents(
                                                player.inventory.currentItem,
                                                (ItemStack) null);
                                        }
                                    }

                                    return true;
                                } else if (this.isTamed() && currentItem != null
                                    && currentItem.getItem() == Items.name_tag
                                    && player.getDistanceSqToEntity(this) < 16.0
                                    && this.func_152114_e(player)) {
                                        this.setCustomNameTag(currentItem.getDisplayName());
                                        if (!player.capabilities.isCreativeMode) {
                                            currentItem.stackSize--;
                                            if (currentItem.stackSize <= 0) {
                                                player.inventory.setInventorySlotContents(
                                                    player.inventory.currentItem,
                                                    (ItemStack) null);
                                            }
                                        }

                                        return true;
                                    } else if (this.isTamed() && currentItem == null
                                        && player.getDistanceSqToEntity(this) < 16.0
                                        && this.func_152114_e(player)) {
                                            ItemStack equipmentInSlot = this.getEquipmentInSlot(0);
                                            int it = 0;
                                            if (equipmentInSlot == null) {
                                                equipmentInSlot = this.getEquipmentInSlot(++it);
                                            }

                                            if (equipmentInSlot == null) {
                                                equipmentInSlot = this.getEquipmentInSlot(++it);
                                            }

                                            if (equipmentInSlot == null) {
                                                equipmentInSlot = this.getEquipmentInSlot(++it);
                                            }

                                            if (equipmentInSlot == null) {
                                                equipmentInSlot = this.getEquipmentInSlot(++it);
                                            }

                                            if (equipmentInSlot != null) {
                                                player.inventory.setInventorySlotContents(
                                                    player.inventory.currentItem,
                                                    equipmentInSlot);
                                                this.setCurrentItemOrArmor(it, null);
                                                this.setSitting(false);
                                                if (!this.worldObj.isRemote) {
                                                    this.worldObj.setEntityState(this, (byte) 6);
                                                }
                                            } else if (!this.worldObj.isRemote) {
                                                this.setSitting(false);
                                                this.playTameEffect(true);
                                                this.worldObj.setEntityState(this, (byte) 7);
                                                String healthMessage = new String();
                                                healthMessage = String.format(
                                                    "I have %d health. Thank you for asking! xoxo",
                                                    this.getGirlfriendHealth());
                                                player.addChatComponentMessage(new ChatComponentText(healthMessage));
                                            }

                                            return true;
                                        } else {
                                            return super.interact(player);
                                        }
    }

    public boolean isWheat(ItemStack stack) {
        return stack != null && stack.getItem() == Item.getItemFromBlock(Blocks.red_flower);
    }

    protected boolean canDespawn() {
        return false;
    }

    protected String getLivingSound() {
        if (this.isSitting() || this.voice_enable == 0) {
            return null;
        } else if (this.Dance.is_dancing != 0) {
            return null;
        } else if (this.rand.nextInt(11) == 1) {
            Entity victim = this.getAttackTarget();
            if (victim != null) {
                return null;
            } else if (!this.isInWater() && !this.handleLavaMovement()) {
                if (this.rand.nextInt(4) != 0) {
                    if (this.posY < 60.0) {
                        return null;
                    }

                    if (this.worldObj.isThundering()) {
                        return "orespawn:o_thunder";
                    }

                    if (this.worldObj.isRaining()) {
                        return "orespawn:o_rain";
                    }

                    if (!this.worldObj.isDaytime()
                        && this.worldObj.canBlockSeeTheSky((int) this.posX, (int) this.posY, (int) this.posZ)) {
                        if (this.worldObj.rand.nextInt(3) == 0) {
                            return "orespawn:o_dark";
                        }

                        return null;
                    }
                }

                if (!this.isTamed()) {
                    return null;
                } else {
                    return !(this.mygetMaxHealth() > this.getHealth())
                        && (OreSpawnMain.valentines_day == 0 || this.feelingBetter != 0) ? "orespawn:o_happy"
                            : "orespawn:o_hurt";
                }
            } else {
                return "orespawn:o_water";
            }
        } else {
            return null;
        }
    }

    protected String getHurtSound() {
        return this.voice_enable == 0 ? null : "orespawn:o_ow";
    }

    protected String getDeathSound() {
        return this.isTamed() ? "orespawn:o_death_girlfriend" : "orespawn:o_death_single";
    }

    protected float getSoundVolume() {
        return 0.3F;
    }

    protected Item getDropItem() {
        return Item.getItemFromBlock(Blocks.red_flower);
    }

    private void dropItemRand(Item index, int n) {
        EntityItem entityItem = new EntityItem(
            this.worldObj,
            this.posX + OreSpawnMain.OreSpawnRand.nextInt(4) - OreSpawnMain.OreSpawnRand.nextInt(4),
            this.posY + 1.0,
            this.posZ + OreSpawnMain.OreSpawnRand.nextInt(4) - OreSpawnMain.OreSpawnRand.nextInt(4),
            new ItemStack(index, n, 0));
        this.worldObj.spawnEntityInWorld(entityItem);
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        int roll = 0;
        if (this.isTamed()) {
            roll = this.rand.nextInt(5);
            roll += 2;

            for (int i = 0; i < roll; i++) {
                this.dropItem(Item.getItemFromBlock(Blocks.red_flower), 1);
            }
        }

        Item v6 = OreSpawnMain.MyItemShoes;
        Item v7 = OreSpawnMain.MyItemShoes_1;
        Item v8 = OreSpawnMain.MyItemShoes_2;
        Item v9 = OreSpawnMain.MyItemShoes_3;
        roll = this.rand.nextInt(16);
        roll += 4;

        for (int i = 0; i < roll; i++) {
            this.dropItem(v6, 1);
        }

        roll = this.rand.nextInt(16);
        roll += 4;

        for (int i = 0; i < roll; i++) {
            this.dropItem(v7, 1);
        }

        roll = this.rand.nextInt(16);
        roll += 4;

        for (int i = 0; i < roll; i++) {
            this.dropItem(v8, 1);
        }

        roll = this.rand.nextInt(16);
        roll += 4;

        for (int i = 0; i < roll; i++) {
            this.dropItem(v9, 1);
        }

        if (this.isTamed()) {
            ItemStack heldItem = this.getCurrentEquippedItem();
            if (heldItem != null && heldItem.stackSize > 0) {
                this.dropItem(heldItem.getItem(), heldItem.stackSize);
            }

            heldItem = this.getEquipmentInSlot(1);
            if (heldItem != null && heldItem.stackSize > 0) {
                this.dropItem(heldItem.getItem(), heldItem.stackSize);
            }

            heldItem = this.getEquipmentInSlot(2);
            if (heldItem != null && heldItem.stackSize > 0) {
                this.dropItem(heldItem.getItem(), heldItem.stackSize);
            }

            heldItem = this.getEquipmentInSlot(3);
            if (heldItem != null && heldItem.stackSize > 0) {
                this.dropItem(heldItem.getItem(), heldItem.stackSize);
            }

            heldItem = this.getEquipmentInSlot(4);
            if (heldItem != null && heldItem.stackSize > 0) {
                this.dropItem(heldItem.getItem(), heldItem.stackSize);
            }
        }
    }

    public void attackEntityWithRangedAttack(EntityLivingBase living) {
        ItemStack it = null;
        if (!this.isSwingInProgress) {
            it = this.getCurrentEquippedItem();
            if (it != null && it.getItem() == OreSpawnMain.MyUltimateBow) {
                EntityArrow ultimateArrow = new UltimateArrow(this.worldObj, this, living, 2.0F, 10.0F);
                if (this.worldObj.rand.nextInt(4) == 1) {
                    ultimateArrow.setIsCritical(true);
                }

                int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, it);
                if (enchantmentLevel > 0) {
                    ultimateArrow.setKnockbackStrength(enchantmentLevel);
                }

                if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, it) > 0) {
                    ultimateArrow.setFire(100);
                }

                it.damageItem(1, this);
                this.worldObj.playSoundAtEntity(
                    this,
                    "random.bow",
                    1.0F,
                    1.0F / (this.worldObj.rand.nextFloat() * 0.4F + 1.2F) + 0.5F);
                ultimateArrow.canBePickedUp = 2;
                this.worldObj.spawnEntityInWorld(ultimateArrow);
            } else {
                Shoes shoes = new Shoes(this.worldObj, this, 2 + this.rand.nextInt(4));
                double dx = living.posX - this.posX;
                double dy = living.posY + living.getEyeHeight() - 1.1 - shoes.posY;
                double dz = living.posZ - this.posZ;
                float f = MathHelper.sqrt_double(dx * dx + dz * dz) * 0.2F;
                shoes.setThrowableHeading(dx, dy + f, dz, 1.8F, 4.0F);
                this.worldObj.playSoundAtEntity(
                    this,
                    "random.bow",
                    0.75F,
                    1.0F / (this.getRNG()
                        .nextFloat() * 0.4F + 0.8F));
                this.worldObj.spawnEntityInWorld(shoes);
            }

            this.swingItem();
        }
    }

    public ItemStack getCurrentEquippedItem() {
        return this.getEquipmentInSlot(0);
    }

    public void attackTargetEntityWithCurrentItem(Entity entity) {
        ItemStack stack = this.getCurrentEquippedItem();
        if (stack != null) {
            float amount = 0.0F;
            if (this.isPotionActive(Potion.damageBoost)) {
                amount += 3 << this.getActivePotionEffect(Potion.damageBoost)
                    .getAmplifier();
            }

            if (this.isPotionActive(Potion.weakness)) {
                amount -= 2 << this.getActivePotionEffect(Potion.weakness)
                    .getAmplifier();
            }

            int n = 0;
            float attributeValue = (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
                .getAttributeValue();
            if (entity instanceof EntityLiving) {
                attributeValue += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase) entity);
                n += EnchantmentHelper.getKnockbackModifier(this, (EntityLiving) entity);
            }

            if (this.isSprinting()) {
                n++;
            }

            if (amount > 0.0F || attributeValue > 0.0F) {
                boolean flag = this.fallDistance > 0.0F && !this.onGround
                    && !this.isOnLadder()
                    && !this.isInWater()
                    && !this.handleLavaMovement()
                    && !this.isPotionActive(Potion.blindness)
                    && this.ridingEntity == null
                    && entity instanceof EntityLiving;
                if (flag) {
                    amount += this.rand.nextInt((int) amount / 2 + 2);
                }

                amount += attributeValue;
                boolean flag2 = entity.attackEntityFrom(DamageSource.causeMobDamage(this), amount);
                if (flag2 && n > 0) {
                    entity.addVelocity(
                        -MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F) * n * 0.5F,
                        0.1,
                        MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F) * n * 0.5F);
                    this.motionX *= 0.6;
                    this.motionZ *= 0.6;
                    this.setSprinting(false);
                }

                ItemStack heldItem = this.getCurrentEquippedItem();
                if (entity instanceof EntityLiving) {
                    int enchantmentLevel = EnchantmentHelper
                        .getEnchantmentLevel(Enchantment.fireAspect.effectId, heldItem);
                    if (enchantmentLevel > 0 && flag2) {
                        entity.setFire(enchantmentLevel * 4);
                    }
                }
            }
        }
    }

    protected float getSoundPitch() {
        return (this.voice - 5) * 0.02F + 1.0F;
    }

    public EntityAgeable createChild(EntityAgeable partner) {
        return null;
    }

    public void attackEntityWithRangedAttack(EntityLivingBase entityliving, float f) {
        this.attackEntityWithRangedAttack(entityliving);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = false;
        float p2 = amount;
        if (amount > 10.0F) {
            p2 = 10.0F;
        }

        if (!source.getDamageType()
            .equals("cactus")) {
            if (source.getDamageType()
                .equals("inWall") && OreSpawnMain.valentines_day != 0) {
                return ret;
            }

            if (OreSpawnMain.valentines_day != 0 && !this.worldObj.isRemote && this.feelingBetter == 0) {
                Entity e = source.getEntity();
                if (e != null && e instanceof EntityPlayer) {
                    EntityPlayer eb = (EntityPlayer) e;
                    ItemStack ist = eb.getCurrentEquippedItem();
                    if (ist != null) {
                        Item it = ist.getItem();
                        if (it == OreSpawnMain.MyRoseSword) {
                            if (this.worldObj.rand.nextInt(4) == 1) {
                                this.feelingBetter = 1;
                                this.setAttackTarget(null);
                                this.setSize(0.5F, 1.6F);
                                this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
                                    .setBaseValue(this.mygetMaxHealth());
                                int morelove = this.worldObj.rand.nextInt(10);

                                for (int i = 0; i < 10 + morelove; i++) {
                                    this.dropItemRand(OreSpawnMain.MyLove, 1);
                                }
                            } else {
                                this.dropItemRand(OreSpawnMain.MyLove, 1);
                            }
                        }
                    }
                }
            }

            ret = super.attackEntityFrom(source, p2);
        }

        return ret;
    }

    public boolean getCanSpawnHere() {
        for (int k = -3; k < 3; k++) {
            for (int j = -3; j < 3; j++) {
                for (int i = 0; i < 5; i++) {
                    Block bid = this.worldObj.getBlock((int) this.posX + j, (int) this.posY + i, (int) this.posZ + k);
                    if (bid == Blocks.mob_spawner) {
                        TileEntityMobSpawner tileentitymobspawner = null;
                        tileentitymobspawner = (TileEntityMobSpawner) this.worldObj
                            .getTileEntity((int) this.posX + j, (int) this.posY + i, (int) this.posZ + k);
                        String s = tileentitymobspawner.func_145881_a()
                            .getEntityNameToSpawn();
                        if (s != null && s.equals("Girlfriend")) {
                            return true;
                        }
                    }
                }
            }
        }

        return super.getCanSpawnHere();
    }
}
