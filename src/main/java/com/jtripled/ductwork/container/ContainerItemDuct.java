package com.jtripled.ductwork.container;

import com.jtripled.ductwork.tile.TileItemDuct;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

/**
 *
 * @author jtripled
 */
public final class ContainerItemDuct extends Container
{
    private final TileItemDuct tile;
    
    public ContainerItemDuct(TileItemDuct tile, InventoryPlayer inventory)
    {
        this.tile = tile;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 32 + (int) (1 * 18) + i * 18));
        for (int k = 0; k < 9; k++)
            addSlotToContainer(new Slot(inventory, k, 8 + k * 18, 90 + (int) (1 * 18)));
        addSlotToContainer(new SlotItemHandler(tile.getInventory(), 0, 80, 18) {
            @Override
            public void onSlotChanged() {
                tile.markDirty();
            }
        });
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }
    
    public TileItemDuct getTile()
    {
        return tile;
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index >= 36)
            {
                if (!this.mergeItemStack(itemstack1, 0, 36, true))
                    return ItemStack.EMPTY;
            }
            else if (!this.mergeItemStack(itemstack1, 36, 37, false))
                return ItemStack.EMPTY;
            
            if (itemstack1.isEmpty())
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();
        }
        return itemstack;
    }
}