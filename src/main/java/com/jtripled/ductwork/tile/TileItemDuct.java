package com.jtripled.ductwork.tile;

import com.jtripled.ductwork.block.BlockItemDuct;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
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
public class TileItemDuct extends TileEntity implements ITickable
{
    private final ItemStackHandler inventory;
    private EnumFacing previous;
    private int transferCooldown;
    
    public TileItemDuct()
    {
        this.previous = EnumFacing.EAST;
        this.inventory = new ItemStackHandler(1);
        this.transferCooldown = -1;
    }
    
    public EnumFacing getFacing()
    {
        return this.world.getBlockState(this.getPos()).getValue(BlockItemDuct.FACING);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
                && (facing == null || facing == this.getFacing());
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
                && (facing == null || facing == this.getFacing())
                ? (T)inventory : null;
    }
    
    public IItemHandler getInventory()
    {
        return inventory;
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("cooldown", transferCooldown);
        compound.setInteger("previous", previous.getIndex());
        compound.setTag("inventory", inventory.serializeNBT());
        return super.writeToNBT(compound);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        transferCooldown = compound.getInteger("cooldown");
        previous = EnumFacing.getFront(compound.getInteger("previous"));
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
        if (this.getWorld() != null && !this.getWorld().isRemote)
        {
            transferCooldown -= 1;
            if (transferCooldown <= 0)
            {
                transferCooldown = 0;
                boolean flag = false;
                if (!inventory.getStackInSlot(0).isEmpty())
                    flag = transferOut();
                if (flag)
                {
                    transferCooldown = 8;
                    this.markDirty();
                }
            }
        }
    }
    
    public boolean transferOut()
    {
        EnumFacing next = getNextFacing(previous, world.getBlockState(pos).getActualState(world, pos));
        if (next == null)
            return false;
        TileEntity testTile = world.getTileEntity(pos.offset(next));
        if (testTile != null && testTile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, next.getOpposite()))
        {
            previous = next;
            IItemHandler nextInventory = testTile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, next.getOpposite());
            ItemStack outStack = inventory.getStackInSlot(0);
            if (!outStack.isEmpty())
            {
                ItemStack inStack;
                for (int i = 0; i < nextInventory.getSlots(); i++)
                {
                    inStack = nextInventory.getStackInSlot(i);
                    if (inStack.isEmpty() || (inStack.getCount() < inStack.getMaxStackSize()
                            && outStack.getItem() == inStack.getItem()))
                    {
                        if (nextInventory.insertItem(i, inventory.extractItem(0, 1, true), true) == ItemStack.EMPTY)
                        {
                            nextInventory.insertItem(i, inventory.extractItem(0, 1, false), false);
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        return false;
    }
    
    private static EnumFacing getNextFacing(EnumFacing previous, IBlockState state)
    {
        EnumFacing[] next;
        switch (previous)
        {
            case DOWN: next = new EnumFacing[] { EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST, EnumFacing.DOWN }; break;
            case UP: next = new EnumFacing[] { EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST, EnumFacing.DOWN, EnumFacing.UP }; break;
            case NORTH: next = new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST, EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH }; break;
            case SOUTH: next = new EnumFacing[] { EnumFacing.WEST, EnumFacing.EAST, EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH }; break;
            case WEST: next = new EnumFacing[] { EnumFacing.EAST, EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST }; break;
            default: next = EnumFacing.values(); break;
        }
        for (EnumFacing face : next)
        {
            if (face != state.getValue(BlockItemDuct.FACING))
            {
                switch (face)
                {
                    case DOWN: if (state.getValue(BlockItemDuct.DOWN)) return face; break;
                    case UP: if (state.getValue(BlockItemDuct.UP)) return face; break;
                    case NORTH: if (state.getValue(BlockItemDuct.NORTH)) return face; break;
                    case SOUTH: if (state.getValue(BlockItemDuct.SOUTH)) return face; break;
                    case WEST: if (state.getValue(BlockItemDuct.WEST)) return face; break;
                    default: if (state.getValue(BlockItemDuct.EAST)) return EnumFacing.EAST; break;
                }
            }
        }
        return null;
    }
}