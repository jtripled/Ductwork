package com.jtripled.ductwork.container;

import com.jtripled.ductwork.tile.TileFilterHopper;
import com.jtripled.voxen.container.ContainerTile;
import com.jtripled.voxen.container.ContainerTileItemSlot;
import net.minecraft.entity.player.InventoryPlayer;

/**
 *
 * @author jtripled
 */
public final class ContainerFilterHopper extends ContainerTile<TileFilterHopper>
{
    public ContainerFilterHopper(TileFilterHopper tile, InventoryPlayer playerInventory)
    {
        super(2.5f, playerInventory, tile);
        for (int i = 0; i < 5; i++)
        {
            addSlotToContainer(new ContainerTileItemSlot<>(tile, tile.getInventory(), i, 44 + i * 18, 18));
        }
        for (int i = 0; i < 5; i++)
        {
            addSlotToContainer(new ContainerTileItemSlot<>(tile, tile.getFilter(), i, 44 + i * 18, 45));
        }
    }
}
