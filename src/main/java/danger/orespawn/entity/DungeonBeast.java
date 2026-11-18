package danger.orespawn.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.client.render.RenderInfo;
import danger.orespawn.entity.ai.GenericTargetSorter;
import danger.orespawn.entity.ai.MyEntityAIWanderALot;

public class DungeonBeast extends EntityMob {

    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private float moveSpeed = 0.29F;

    public DungeonBeast(World world) {
        super(world);
        this.setSize(1.15F, 1.1F);
        this.getNavigator()
            .setAvoidsWater(true);
        this.experienceValue = 60;
        this.fireResistance = 10;
        this.isImmuneToFire = false;
        this.TargetSorter = new GenericTargetSorter(this);
        this.renderdata = new RenderInfo();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new MyEntityAIWanderALot(this, 14, 1.0));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(OreSpawnMain.DungeonBeast_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
        if (this.renderdata == null) {
            this.renderdata = new RenderInfo();
        }

        this.renderdata.rf1 = 0.0F;
        this.renderdata.rf2 = 0.0F;
        this.renderdata.rf3 = 0.0F;
        this.renderdata.rf4 = 0.0F;
        this.renderdata.ri1 = 0;
        this.renderdata.ri2 = 0;
        this.renderdata.ri3 = 0;
        this.renderdata.ri4 = 0;
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.DungeonBeast_stats.health;
    }

    public RenderInfo getRenderInfo() {
        return this.renderdata;
    }

    public void setRenderInfo(RenderInfo r) {
        this.renderdata.rf1 = r.rf1;
        this.renderdata.rf2 = r.rf2;
        this.renderdata.rf3 = r.rf3;
        this.renderdata.rf4 = r.rf4;
        this.renderdata.ri1 = r.ri1;
        this.renderdata.ri2 = r.ri2;
        this.renderdata.ri3 = r.ri3;
        this.renderdata.ri4 = r.ri4;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.DungeonBeast_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return "orespawn:dbhit";
    }

    protected String getDeathSound() {
        return "orespawn:dbdead";
    }

    protected float getSoundVolume() {
        return 0.8F;
    }

    protected float getSoundPitch() {
        return 1.0F;
    }

    protected Item getDropItem() {
        int i = this.worldObj.rand.nextInt(4);
        if (i == 1) {
            return OreSpawnMain.MyCrystalPinkIngot;
        } else if (i == 2) {
            return OreSpawnMain.MyCrystalApple;
        } else {
            return i == 3 ? Item.getItemFromBlock(Blocks.log) : null;
        }
    }

    public boolean interact(EntityPlayer player) {
        return false;
    }

    public boolean attackEntityAsMob(Entity target) {
        return super.attackEntityAsMob(target);
    }

    protected void updateAITasks() {
        if (!this.isDead) {
            super.updateAITasks();
            if (this.worldObj.rand.nextInt(8) == 0) {
                EntityLivingBase e = this.findSomethingToAttack();
                if (e != null) {
                    if (this.getDistanceSqToEntity(e) < 8.0) {
                        this.setAttacking(1);
                        if (this.worldObj.rand.nextInt(7) == 0 || this.worldObj.rand.nextInt(8) == 1) {
                            this.attackEntityAsMob(e);
                        }
                    } else {
                        this.getNavigator()
                            .tryMoveToEntityLiving(e, 1.2);
                    }
                } else {
                    this.setAttacking(0);
                }
            }
        }
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = false;
        if (source.getDamageType()
            .equals("inWall")) {
            return ret;
        } else {
            if (!source.getDamageType()
                .equals("cactus")) {
                ret = super.attackEntityFrom(source, amount);
            }

            return ret;
        }
    }

    private boolean isSuitableTarget(EntityLivingBase target, boolean checkSight) {
        if (target == null) {
            return false;
        } else if (target == this) {
            return false;
        } else if (!target.isEntityAlive()) {
            return false;
        } else if (MyUtils.isIgnoreable(target)) {
            return false;
        } else if (!this.getEntitySenses()
            .canSee(target)) {
                return false;
            } else if (target instanceof Rat) {
                return false;
            } else if (target instanceof DungeonBeast) {
                return false;
            } else if (target instanceof Rotator) {
                return false;
            } else if (target instanceof Peacock) {
                return false;
            } else if (target instanceof Irukandji) {
                return false;
            } else if (target instanceof Skate) {
                return false;
            } else if (target instanceof Whale) {
                return false;
            } else if (target instanceof Flounder) {
                return false;
            } else {
                if (target instanceof EntityPlayer) {
                    EntityPlayer p = (EntityPlayer) target;
                    if (p.capabilities.isCreativeMode) {
                        return false;
                    }
                }

                return true;
            }
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            List entities = this.worldObj
                .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(16.0, 3.0, 16.0));
            Collections.sort(entities, this.TargetSorter);
            Iterator iterator = entities.iterator();
            Entity entity = null;
            EntityLivingBase target = null;

            while (iterator.hasNext()) {
                entity = (Entity) iterator.next();
                target = (EntityLivingBase) entity;
                if (this.isSuitableTarget(target, false)) {
                    return target;
                }
            }

            return null;
        }
    }

    public final int getAttacking() {
        return this.dataWatcher.getWatchableObjectByte(20);
    }

    public final void setAttacking(int attacking) {
        this.dataWatcher.updateObject(20, (byte) attacking);
    }

    public boolean getCanSpawnHere() {
        int sc = 0;

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
                        if (s != null && s.equals("Dungeon Beast")) {
                            return true;
                        }
                    }
                }
            }
        }

        if (!this.isValidLightLevel()) {
            return false;
        } else {
            if (this.worldObj.provider.dimensionId == OreSpawnMain.DimensionID5) {
                if (this.posY > 28.0 || this.posY < 25.0) {
                    return false;
                }

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        Block bid = this.worldObj
                            .getBlock((int) this.posX + j, (int) this.posY + 1, (int) this.posZ + i);
                        if (bid == Blocks.air) {
                            sc++;
                        }
                    }
                }

                if (sc < 6) {
                    return false;
                }
            }

            return true;
        }
    }
}
