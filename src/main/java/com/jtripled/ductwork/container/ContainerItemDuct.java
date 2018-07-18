package com.jtripled.ductwork.container;

import com.jtripled.ductwork.tile.TileItemDuct;
import com.jtripled.voxen.container.ContainerTile;
import com.jtripled.voxen.container.ContainerTileItemSlot;
import net.minecraft.entity.player.InventoryPlayer;

/**
 *
 * @author jtripled
 */
public final class ContainerItemDuct extends ContainerTile<TileItemDuct>
{
    public ContainerItemDuct(TileItemDuct tile, InventoryPlayer playerInventory)
    {
        super(1, playerInventory, tile);
        addSlotToContainer(new ContainerTileItemSlot<>(tile, tile.getInventory(), 0, 80, 18));
    }
}
