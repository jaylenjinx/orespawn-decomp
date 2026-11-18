package danger.orespawn.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;

public class TileEntityCrystalFurnace extends TileEntity implements ISidedInventory {

    private static final int[] slots_top = new int[] { 0 };
    private static final int[] slots_bottom = new int[] { 2, 1 };
    private static final int[] slots_sides = new int[] { 1 };
    private ItemStack[] furnaceItemStacks = new ItemStack[3];
    public int furnaceBurnTime;
    public int currentItemBurnTime;
    public int furnaceCookTime;
    private String field_94130_e;

    public int getSizeInventory() {
        return this.furnaceItemStacks.length;
    }

    public ItemStack getStackInSlot(int slot) {
        return this.furnaceItemStacks[slot];
    }

    public ItemStack decrStackSize(int slot, int count) {
        if (this.furnaceItemStacks[slot] != null) {
            if (this.furnaceItemStacks[slot].stackSize <= count) {
                ItemStack itemstack = this.furnaceItemStacks[slot];
                this.furnaceItemStacks[slot] = null;
                return itemstack;
            } else {
                ItemStack itemstack = this.furnaceItemStacks[slot].splitStack(count);
                if (this.furnaceItemStacks[slot].stackSize == 0) {
                    this.furnaceItemStacks[slot] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    public ItemStack getStackInSlotOnClosing(int slot) {
        if (this.furnaceItemStacks[slot] != null) {
            ItemStack itemstack = this.furnaceItemStacks[slot];
            this.furnaceItemStacks[slot] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    public void setInventorySlotContents(int slot, ItemStack stack) {
        this.furnaceItemStacks[slot] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.field_94130_e : "container.furnace";
    }

    public boolean hasCustomInventoryName() {
        return this.field_94130_e != null && this.field_94130_e.length() > 0;
    }

    public void func_145951_a(String str) {
        this.field_94130_e = str;
    }

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        NBTTagList nbttaglist = tag.getTagList("Items", 10);
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); i++) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");
            if (b0 >= 0 && b0 < this.furnaceItemStacks.length) {
                this.furnaceItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.furnaceBurnTime = tag.getShort("BurnTime");
        this.furnaceCookTime = tag.getShort("CookTime");
        this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
        if (tag.hasKey("CustomName")) {
            this.field_94130_e = tag.getString("CustomName");
        }
    }

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setShort("BurnTime", (short) this.furnaceBurnTime);
        tag.setShort("CookTime", (short) this.furnaceCookTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.furnaceItemStacks.length; i++) {
            if (this.furnaceItemStacks[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.furnaceItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        tag.setTag("Items", nbttaglist);
        if (this.hasCustomInventoryName()) {
            tag.setString("CustomName", this.field_94130_e);
        }
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int scale) {
        return this.furnaceCookTime * scale / 150;
    }

    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int scale) {
        if (this.currentItemBurnTime == 0) {
            this.currentItemBurnTime = 150;
        }

        return this.furnaceBurnTime * scale / this.currentItemBurnTime;
    }

    public boolean isBurning() {
        return this.furnaceBurnTime > 0;
    }

    public void updateEntity() {
        boolean flag = this.furnaceBurnTime > 0;
        boolean flag1 = false;
        if (this.furnaceBurnTime > 0) {
            this.furnaceBurnTime--;
        }

        if (!this.worldObj.isRemote) {
            if (this.furnaceBurnTime == 0 && this.canSmelt()) {
                this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
                if (this.furnaceBurnTime > 0) {
                    flag1 = true;
                    if (this.furnaceItemStacks[1] != null) {
                        this.furnaceItemStacks[1].stackSize--;
                        if (this.furnaceItemStacks[1].stackSize == 0) {
                            this.furnaceItemStacks[1] = this.furnaceItemStacks[1].getItem()
                                .getContainerItem(this.furnaceItemStacks[1]);
                        }
                    }
                }
            }

            if (this.isBurning() && this.canSmelt()) {
                this.furnaceCookTime++;
                if (this.furnaceCookTime == 150) {
                    this.furnaceCookTime = 0;
                    this.smeltItem();
                    flag1 = true;
                }
            } else {
                this.furnaceCookTime = 0;
            }

            if (flag != this.furnaceBurnTime > 0) {
                flag1 = true;
                CrystalFurnace.updateFurnaceBlockState(
                    this.furnaceBurnTime > 0,
                    this.worldObj,
                    this.xCoord,
                    this.yCoord,
                    this.zCoord);
            }
        }

        if (flag1) {
            this.markDirty();
        }
    }

    private boolean canSmelt() {
        if (this.furnaceItemStacks[0] == null) {
            return false;
        } else {
            ItemStack itemstack = FurnaceRecipes.smelting()
                .getSmeltingResult(this.furnaceItemStacks[0]);
            if (itemstack == null) {
                return false;
            } else if (this.furnaceItemStacks[2] == null) {
                return true;
            } else if (!this.furnaceItemStacks[2].isItemEqual(itemstack)) {
                return false;
            } else {
                int result = this.furnaceItemStacks[2].stackSize + itemstack.stackSize;
                return result <= this.getInventoryStackLimit() && result <= itemstack.getMaxStackSize();
            }
        }
    }

    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack itemstack = FurnaceRecipes.smelting()
                .getSmeltingResult(this.furnaceItemStacks[0]);
            if (this.furnaceItemStacks[2] == null) {
                this.furnaceItemStacks[2] = itemstack.copy();
            } else if (this.furnaceItemStacks[2].isItemEqual(itemstack)) {
                this.furnaceItemStacks[2].stackSize = this.furnaceItemStacks[2].stackSize + itemstack.stackSize;
            }

            this.furnaceItemStacks[0].stackSize--;
            if (this.furnaceItemStacks[0].stackSize <= 0) {
                this.furnaceItemStacks[0] = null;
            }
        }
    }

    public static int getItemBurnTime(ItemStack stack) {
        if (stack == null) {
            return 0;
        } else {
            Item item = stack.getItem();
            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air) {
                Block block = Block.getBlockFromItem(item);
                if (block == Blocks.wooden_slab) {
                    return 150;
                }

                if (block.getMaterial() == Material.wood) {
                    return 300;
                }

                if (block == Blocks.coal_block) {
                    return 16000;
                }
            }

            if (item instanceof ItemTool && ((ItemTool) item).getToolMaterialName()
                .equals("WOOD")) {
                return 200;
            } else if (item instanceof ItemSword && ((ItemSword) item).getToolMaterialName()
                .equals("WOOD")) {
                    return 200;
                } else if (item instanceof ItemHoe && ((ItemHoe) item).getToolMaterialName()
                    .equals("WOOD")) {
                        return 200;
                    } else if (item == Items.stick) {
                        return 100;
                    } else if (item == Items.coal) {
                        return 1600;
                    } else if (item == Items.lava_bucket) {
                        return 20000;
                    } else if (item == Item.getItemFromBlock(Blocks.sapling)) {
                        return 100;
                    } else if (item == Items.blaze_rod) {
                        return 2400;
                    } else if (item == Item.getItemFromBlock(OreSpawnMain.CrystalCoal)) {
                        return 20000;
                    } else if (item == Item.getItemFromBlock(OreSpawnMain.MyCrystalTreeLog)) {
                        return 800;
                    } else {
                        return item == Item.getItemFromBlock(OreSpawnMain.CrystalPlanksBlock) ? 400
                            : GameRegistry.getFuelValue(stack);
                    }
        }
    }

    public static boolean isItemFuel(ItemStack stack) {
        return getItemBurnTime(stack) > 0;
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false
            : player.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) <= 64.0;
    }

    public void openChest() {}

    public void closeChest() {}

    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return slot == 2 ? false : (slot == 1 ? isItemFuel(stack) : true);
    }

    public int[] getAccessibleSlotsFromSide(int side) {
        return side == 0 ? slots_bottom : (side == 1 ? slots_top : slots_sides);
    }

    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return this.isItemValidForSlot(slot, stack);
    }

    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return side != 0 || slot != 1 || stack.getItem() == Items.bucket;
    }

    public void openInventory() {}

    public void closeInventory() {}
}
