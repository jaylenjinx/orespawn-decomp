package danger.orespawn.entity;

import java.util.List;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.MyEntityAIWanderALot;
import danger.orespawn.world.dimension.OreSpawnTeleporter;

public class EntityAnt extends EntityAnimal {

    public double moveSpeed = 0.15F;
    private static final ResourceLocation texture1 = new ResourceLocation("orespawn", "ant.png");
    private static final ResourceLocation texture2 = new ResourceLocation("orespawn", "red_ant.png");
    private static final ResourceLocation texture3 = new ResourceLocation("orespawn", "rainbow_ant.png");
    private static final ResourceLocation texture4 = new ResourceLocation("orespawn", "unstableant.png");
    private static final ResourceLocation texture5 = new ResourceLocation("orespawn", "termite.png");

    public EntityAnt(World world) {
        super(world);
        this.setSize(0.1F, 0.1F);
        this.experienceValue = 0;
        this.getNavigator()
            .setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAIPanic(this, 1.4));
        this.tasks.addTask(1, new MyEntityAIWanderALot(this, 9, 1.0));
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

    public ResourceLocation getTexture(EntityAnt a) {
        if (a instanceof EntityRedAnt) {
            return texture2;
        } else if (a instanceof EntityRainbowAnt) {
            return texture3;
        } else if (a instanceof EntityUnstableAnt) {
            return texture4;
        } else {
            return a instanceof Termite ? texture5 : texture1;
        }
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        super.onUpdate();
    }

    public boolean interact(EntityPlayer player) {
        if (player == null) {
            return false;
        } else if (!(player instanceof EntityPlayerMP)) {
            return false;
        } else {
            ItemStack currentItem = player.inventory.getCurrentItem();
            if (currentItem != null && currentItem.stackSize <= 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                currentItem = null;
            }

            if (currentItem != null) {
                return false;
            } else {
                if (player.dimension != OreSpawnMain.DimensionID) {
                    MinecraftServer.getServer()
                        .getConfigurationManager()
                        .transferPlayerToDimension(
                            (EntityPlayerMP) player,
                            OreSpawnMain.DimensionID,
                            new OreSpawnTeleporter(
                                MinecraftServer.getServer()
                                    .worldServerForDimension(OreSpawnMain.DimensionID),
                                OreSpawnMain.DimensionID,
                                this.worldObj));
                } else {
                    MinecraftServer.getServer()
                        .getConfigurationManager()
                        .transferPlayerToDimension(
                            (EntityPlayerMP) player,
                            0,
                            new OreSpawnTeleporter(
                                MinecraftServer.getServer()
                                    .worldServerForDimension(0),
                                0,
                                this.worldObj));
                }

                return true;
            }
        }
    }

    public boolean isAIEnabled() {
        return true;
    }

    public int mygetMaxHealth() {
        return 1;
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return null;
    }

    protected String getDeathSound() {
        return null;
    }

    protected float getSoundVolume() {
        return 0.0F;
    }

    protected void playStepSound(int n, int n2, int n3, int n4) {}

    protected void dropFewItems(boolean wasRecentlyHit, int lootingLevel) {}

    protected boolean canTriggerWalking() {
        return true;
    }

    public EntityAgeable createChild(EntityAgeable partner) {
        return null;
    }

    public boolean getCanSpawnHere() {
        return this.posY < 50.0 ? false : this.findBuddies() <= 4;
    }

    private int findBuddies() {
        List entities = this.worldObj.getEntitiesWithinAABB(EntityAnt.class, this.boundingBox.expand(20.0, 10.0, 20.0));
        return entities.size();
    }

    public void updateAITick() {
        if (this.worldObj.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
        }

        super.updateAITick();
    }
}
