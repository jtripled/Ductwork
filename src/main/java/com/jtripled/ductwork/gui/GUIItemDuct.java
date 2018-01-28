package com.jtripled.ductwork.gui;

import com.jtripled.ductwork.container.ContainerItemDuct;
import com.jtripled.voxen.gui.GUIContainerTile;
import com.jtripled.voxen.gui.GUIInventorySlot;

/**
 *
 * @author jtripled
 */
public class GUIItemDuct extends GUIContainerTile<ContainerItemDuct>
{
    public GUIItemDuct(ContainerItemDuct container)
    {
        super(container);
        this.setType(Type.INVENTORY_1);
    }
    
    @Override
    public void addElements(int x, int y)
    {
        this.addElement(new GUIInventorySlot(this, 79, 17));
    }
}
