package com.jtripled.ductwork.tile;

import com.jtripled.ductwork.block.BlockFilterHopper;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 *
 * @author jtripled
 */
public class TileFilterHopper extends TileEntity implements ITickable
{
    private final ItemStackHandler filter;
    private final ItemStackHandler inventory;
    private int transferCooldown;
    private boolean blacklist;
    
    public TileFilterHopper()
    {
        this.filter = new ItemStackHandler(5) {
            @Override
            public int getSlotLimit(int slot)
            {
                return 1;
            }
            @Override
            public void setStackInSlot(int slot, @Nonnull ItemStack stack)
            {
                validateSlotIndex(slot);
                if (!stack.isEmpty())
                    this.stacks.set(slot, stack);
                onContentsChanged(slot);
            }
            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
            {
                this.stacks.set(slot, new ItemStack(stack.getItem(), 1, stack.getMetadata()));
                onContentsChanged(slot);
                return stack;
            }
            @Nonnull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate)
            {
                this.stacks.set(slot, ItemStack.EMPTY);
                onContentsChanged(slot);
                return ItemStack.EMPTY;
            }
        };
        this.inventory = new ItemStackHandler(5) {
            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
            {
                ItemStack compare = new ItemStack(stack.getItem(), 1, stack.getMetadata());
                for (int i = 0; i < filter.getSlots(); i++)
                {
                    boolean equal = ItemStack.areItemStacksEqual(compare, filter.getStackInSlot(i));
                    if (equal)
                    {
                        return blacklist ? stack : super.insertItem(slot, stack, simulate);
                    }
                }
                return blacklist ? super.insertItem(slot, stack, simulate) : stack;
            }
        };
        this.transferCooldown = -1;
        this.blacklist = false;
    }
    
    public EnumFacing getFacing()
    {
        return this.world.getBlockState(this.getPos()).getValue(BlockFilterHopper.FACING);
    }
    
    public boolean isBlacklist()
    {
        return blacklist;
    }
    
    public void setBlacklist(boolean blacklist)
    {
        this.blacklist = blacklist;
        markDirty();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
                ? (T)inventory : null;
    }
    
    public IItemHandler getInventory()
    {
        return inventory;
    }
    
    public IItemHandler getFilter()
    {
        return filter;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("cooldown", transferCooldown);
        compound.setBoolean("blacklist", blacklist);
        compound.setTag("filter", filter.serializeNBT());
        compound.setTag("inventory", inventory.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        transferCooldown = compound.getInteger("cooldown");
        blacklist = compound.getBoolean("blacklist");
        filter.deserializeNBT(compound.getCompoundTag("filter"));
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        super.readFromNBT(compound);
    }
    
    @Override
    public void onDataPacket(NetworkManager network, SPacketUpdateTileEntity packet)
    {
        readFromNBT(packet.getNbtCompound());
    }
    
    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(getPos(), 1, getUpdateTag());
    }
    
    @Override
    public NBTTagCompound getUpdateTag()
    {
        return writeToNBT(super.getUpdateTag());
    }
    
    @Override
    public void handleUpdateTag(NBTTagCompound compound)
    {
        readFromNBT(compound);
    }

    @Override
    public void update()
    {
        if (getWorld() != null && !getWorld().isRemote)
        {
            transferCooldown -= 1;
            if (transferCooldown <= 0)
            {
                transferCooldown = 0;
                boolean flag = false;
                if (!isEmpty())
                    flag = transferOut();
                if (!isFull())
                    flag = transferIn() || flag;
                if (flag)
                {
                    transferCooldown = 8;
                    markDirty();
                }
            }
        }
    }

    public boolean isEmpty()
    {
        for (int i = 0; i < inventory.getSlots(); i++)
            if (!inventory.getStackInSlot(i).isEmpty())
                return false;
        return true;
    }

    public boolean isFull()
    {
        ItemStack stack;
        for (int i = 0; i < inventory.getSlots(); i++)
        {
            stack = inventory.getStackInSlot(i);
            if (stack.isEmpty() || stack.getCount() < stack.getMaxStackSize())
                return false;
        }
        return true;
    }
    
    public boolean transferOut()
    {
        EnumFacing face = getFacing();
        TileEntity testTile = world.getTileEntity(pos.offset(face));
        if (testTile != null && testTile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, face.getOpposite()))
        {
            IItemHandler nextInventory = testTile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, face.getOpposite());
            for (int i = 0; i < inventory.getSlots(); i++)
            {
                ItemStack outStack = inventory.getStackInSlot(i);
                if (!outStack.isEmpty())
                {
                    ItemStack inStack;
                    for (int j = 0; j < nextInventory.getSlots(); j++)
                    {
                        inStack = nextInventory.getStackInSlot(j);
                        if (inStack.isEmpty() || (inStack.getCount() < inStack.getMaxStackSize()
                                && outStack.getItem() == inStack.getItem()))
                        {
                            if (nextInventory.insertItem(j, inventory.extractItem(i, 1, true), true) == ItemStack.EMPTY)
                            {
                                nextInventory.insertItem(j, inventory.extractItem(i, 1, false), false);
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
        return false;
    }
    
    public boolean transferIn()
    {
        TileEntity testTile = world.getTileEntity(pos.up());
        if (testTile != null && testTile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN))
        {
            IItemHandler handler = testTile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
            ItemStack inStack;
            for (int i = 0; i < inventory.getSlots(); i++)
            {
                inStack = inventory.getStackInSlot(i);
                if (inStack.isEmpty() || inStack.getCount() < inStack.getMaxStackSize())
                {
                    ItemStack outStack;
                    for (int j = 0; j < handler.getSlots(); j++)
                    {
                        outStack = handler.getStackInSlot(j);
                        if (!outStack.isEmpty() && (inStack.isEmpty() || inStack.getItem() == outStack.getItem()))
                        {
                            ItemStack test = inventory.insertItem(i, handler.extractItem(j, 1, true), true);
                            if (test.isEmpty())
                            {
                                inventory.insertItem(i, handler.extractItem(j, 1, false), false);
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
        return false;
    }
}