package danger.orespawn.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.ai.MyEntityAIWanderALot;
import danger.orespawn.world.dimension.OreSpawnTeleporter;

public class EntityRainbowAnt extends EntityAnt {

    public EntityRainbowAnt(World world) {
        super(world);
        this.setSize(0.1F, 0.1F);
        this.experienceValue = 0;
        this.getNavigator()
            .setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAIPanic(this, 1.4F));
        this.tasks.addTask(1, new MyEntityAIWanderALot(this, 9, 1.0));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(0.0);
    }

    @Override
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
                if (player.dimension != OreSpawnMain.DimensionID3) {
                    MinecraftServer.getServer()
                        .getConfigurationManager()
                        .transferPlayerToDimension(
                            (EntityPlayerMP) player,
                            OreSpawnMain.DimensionID3,
                            new OreSpawnTeleporter(
                                MinecraftServer.getServer()
                                    .worldServerForDimension(OreSpawnMain.DimensionID3),
                                OreSpawnMain.DimensionID3,
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
}
