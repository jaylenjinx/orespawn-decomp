package danger.orespawn.entity;

import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;

public class EnderReaper extends EntityMob {

    private static final UUID attackingSpeedBoostModifierUUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final AttributeModifier attackingSpeedBoostModifier = new AttributeModifier(
        attackingSpeedBoostModifierUUID,
        "Attacking speed boost",
        6.2F,
        0).setSaved(false);
    private int teleportDelay;
    private int stareTimer;
    private Entity lastEntityToAttack;

    public EnderReaper(World world) {
        super(world);
        this.setSize(0.7F, 2.9F);
        this.stepHeight = 1.0F;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(OreSpawnMain.EnderReaper_stats.health);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(0.37);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(OreSpawnMain.EnderReaper_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(18, new Byte((byte) 0));
    }

    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
    }

    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
    }

    protected Entity findPlayerToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        } else {
            EntityPlayer entityplayer = this.worldObj.getClosestVulnerablePlayerToEntity(this, 81.0);
            if (entityplayer != null) {
                if (this.shouldAttackPlayer(entityplayer)) {
                    if (this.stareTimer == 0) {
                        this.worldObj.playSoundAtEntity(entityplayer, "mob.endermen.stare", 1.0F, 1.0F);
                    }

                    if (this.stareTimer++ == 5) {
                        this.stareTimer = 0;
                    }

                    this.setScreaming(true);
                    return entityplayer;
                }

                this.stareTimer = 0;
                this.setScreaming(false);
            }

            return null;
        }
    }

    private boolean shouldAttackPlayer(EntityPlayer player) {
        ItemStack itemstack = player.inventory.armorInventory[3];
        if (itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
            return false;
        } else {
            Vec3 vec3 = player.getLook(1.0F)
                .normalize();
            Vec3 vec31 = Vec3.createVectorHelper(
                this.posX - player.posX,
                this.boundingBox.minY + this.height / 2.0F - (player.posY + player.getEyeHeight()),
                this.posZ - player.posZ);
            double d0 = vec31.lengthVector();
            vec31 = vec31.normalize();
            double d1 = vec3.dotProduct(vec31);
            return d1 > 1.0 - 0.025 / d0 ? player.canEntityBeSeen(this) : false;
        }
    }

    public void onLivingUpdate() {
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.drown, 1.0F);
        }

        if (this.lastEntityToAttack != this.entityToAttack) {
            IAttributeInstance attributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            attributeinstance.removeModifier(attackingSpeedBoostModifier);
            if (this.entityToAttack != null) {
                attributeinstance.applyModifier(attackingSpeedBoostModifier);
            }
        }

        this.lastEntityToAttack = this.entityToAttack;

        for (int i = 0; i < 2; i++) {
            this.worldObj.spawnParticle(
                "portal",
                this.posX + (this.rand.nextDouble() - 0.5) * this.width,
                this.posY + this.rand.nextDouble() * this.height - 0.25,
                this.posZ + (this.rand.nextDouble() - 0.5) * this.width,
                (this.rand.nextDouble() - 0.5) * 2.0,
                -this.rand.nextDouble(),
                (this.rand.nextDouble() - 0.5) * 2.0);
        }

        if (this.worldObj.isDaytime() && !this.worldObj.isRemote) {
            float f = this.getBrightness(1.0F);
            if (f > 0.5F && this.worldObj.canBlockSeeTheSky(
                MathHelper.floor_double(this.posX),
                MathHelper.floor_double(this.posY),
                MathHelper.floor_double(this.posZ)) && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
                this.entityToAttack = null;
                this.setScreaming(false);
                this.teleportRandomly();
            }
        }

        if (this.isWet() || this.isBurning()) {
            this.setScreaming(false);
            this.teleportRandomly();
        }

        this.isJumping = false;
        if (this.entityToAttack != null) {
            this.faceEntity(this.entityToAttack, 100.0F, 100.0F);
        }

        if (!this.worldObj.isRemote && this.isEntityAlive()) {
            if (this.entityToAttack != null) {
                if (this.entityToAttack instanceof EntityPlayer
                    && this.shouldAttackPlayer((EntityPlayer) this.entityToAttack)) {
                    if (this.entityToAttack.getDistanceSqToEntity(this) < 16.0) {
                        this.teleportRandomly();
                    }

                    this.teleportDelay = 0;
                } else if (this.entityToAttack.getDistanceSqToEntity(this) > 256.0 && this.teleportDelay++ >= 30
                    && this.teleportToEntity(this.entityToAttack)) {
                        this.teleportDelay = 0;
                    }
            } else {
                this.setScreaming(false);
                this.teleportDelay = 0;
            }
        }

        super.onLivingUpdate();
    }

    protected boolean teleportRandomly() {
        double d0 = this.posX + (this.rand.nextDouble() - 0.5) * 64.0;
        double d1 = this.posY + (this.rand.nextInt(64) - 32);
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5) * 64.0;
        return this.teleportTo(d0, d1, d2);
    }

    protected boolean teleportToEntity(Entity entity) {
        Vec3 vec3 = Vec3.createVectorHelper(
            this.posX - entity.posX,
            this.boundingBox.minY + this.height / 2.0F - entity.posY + entity.getEyeHeight(),
            this.posZ - entity.posZ);
        vec3 = vec3.normalize();
        double d0 = 16.0;
        double d1 = this.posX + (this.rand.nextDouble() - 0.5) * 8.0 - vec3.xCoord * d0;
        double d2 = this.posY + (this.rand.nextInt(16) - 8) - vec3.yCoord * d0;
        double d3 = this.posZ + (this.rand.nextDouble() - 0.5) * 8.0 - vec3.zCoord * d0;
        return this.teleportTo(d1, d2, d3);
    }

    protected boolean teleportTo(double x, double y, double z) {
        double d3 = this.posX;
        double d4 = this.posY;
        double d5 = this.posZ;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        boolean flag = false;
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.posY);
        int k = MathHelper.floor_double(this.posZ);
        if (this.worldObj.blockExists(i, j, k)) {
            boolean flag1 = false;

            while (!flag1 && j > 0) {
                Block l = this.worldObj.getBlock(i, j - 1, k);
                if (l != Blocks.air && l.getMaterial()
                    .blocksMovement()) {
                    flag1 = true;
                } else {
                    this.posY--;
                    j--;
                }
            }

            if (flag1) {
                this.setPosition(this.posX, this.posY, this.posZ);
                if (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox)
                    .isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox)) {
                    flag = true;
                }
            }
        }

        if (!flag) {
            this.setPosition(d3, d4, d5);
            return false;
        } else {
            short short1 = 128;

            for (int lx = 0; lx < short1; lx++) {
                double d6 = lx / (short1 - 1.0);
                float f = (this.rand.nextFloat() - 0.5F) * 0.2F;
                float f1 = (this.rand.nextFloat() - 0.5F) * 0.2F;
                float f2 = (this.rand.nextFloat() - 0.5F) * 0.2F;
                double d7 = d3 + (this.posX - d3) * d6 + (this.rand.nextDouble() - 0.5) * this.width * 2.0;
                double d8 = d4 + (this.posY - d4) * d6 + this.rand.nextDouble() * this.height;
                double d9 = d5 + (this.posZ - d5) * d6 + (this.rand.nextDouble() - 0.5) * this.width * 2.0;
                this.worldObj.spawnParticle("portal", d7, d8, d9, f, f1, f2);
            }

            this.worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
            this.playSound("mob.endermen.portal", 1.0F, 1.0F);
            return true;
        }
    }

    protected String getLivingSound() {
        return this.isScreaming() ? "mob.endermen.scream" : "mob.endermen.idle";
    }

    protected String getHurtSound() {
        return "mob.endermen.hit";
    }

    protected String getDeathSound() {
        return "mob.endermen.death";
    }

    protected Item getDropItem() {
        return Items.ender_eye;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.EnderReaper_stats.defense;
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {
        Item j = this.getDropItem();
        if (j != null) {
            int k = this.rand.nextInt(2 + lootingLevel);

            for (int l = 0; l < k; l++) {
                this.dropItem(j, 1);
            }
        }
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isEntityInvulnerable()) {
            return false;
        } else {
            this.setScreaming(true);
            if (source instanceof EntityDamageSourceIndirect) {
                for (int i = 0; i < 16; i++) {
                    if (this.teleportRandomly()) {
                        return true;
                    }
                }

                return super.attackEntityFrom(source, amount);
            } else {
                return super.attackEntityFrom(source, amount);
            }
        }
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
                        if (s != null && s.equals("Ender Reaper")) {
                            return true;
                        }
                    }
                }
            }
        }

        if (!this.isValidLightLevel()) {
            return false;
        } else if (this.worldObj.isDaytime()) {
            return false;
        } else if (this.posY < 30.0) {
            return false;
        } else {
            EnderReaper target = null;
            target = (EnderReaper) this.worldObj
                .findNearestEntityWithinAABB(EnderReaper.class, this.boundingBox.expand(16.0, 8.0, 16.0), this);
            return target == null;
        }
    }

    public boolean isScreaming() {
        return this.dataWatcher.getWatchableObjectByte(18) > 0;
    }

    public void setScreaming(boolean flag) {
        this.dataWatcher.updateObject(18, (byte) (flag ? 1 : 0));
    }
}
