package com.jtripled.ductwork.container;

import com.jtripled.ductwork.tile.TileGratedHopper;
import com.jtripled.voxen.container.ContainerTile;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.SlotItemHandler;

/**
 *
 * @author jtripled
 */
public final class ContainerGratedHopper extends ContainerTile<TileGratedHopper>
{
    public ContainerGratedHopper(TileGratedHopper tile, InventoryPlayer playerInventory)
    {
        super(2.5f, playerInventory, tile);
        for (int i = 0; i < 5; i++)
        {
            addSlotToContainer(new SlotItemHandler(tile.getInventory(), i, 44 + i * 18, 18) {
                @Override
                public void onSlotChanged() {
                    tile.markDirty();
                }
            });
        }
        for (int i = 0; i < 5; i++)
        {
            addSlotToContainer(new SlotItemHandler(tile.getFilter(), i, 44 + i * 18, 45) {
                @Override
                public void onSlotChanged() {
                    tile.markDirty();
                }
            });
        }
    }
}
