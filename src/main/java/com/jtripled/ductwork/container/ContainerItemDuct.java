package com.jtripled.ductwork.container;

import com.jtripled.ductwork.tile.TileItemDuct;
import com.jtripled.voxen.container.ContainerTile;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 *
 * @author jtripled
 */
public final class ContainerItemDuct extends ContainerTile<TileItemDuct>
{
    public ContainerItemDuct(TileItemDuct tile, InventoryPlayer playerInventory)
    {
        super(1, playerInventory, tile);
        IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, tile.getFacing());
        addSlotToContainer(new SlotItemHandler(inventory, 0, 80, 18) {
            @Override
            public void onSlotChanged() {
                tile.markDirty();
            }
        });
    }
}
