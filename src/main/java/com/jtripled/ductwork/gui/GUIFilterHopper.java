package com.jtripled.ductwork.gui;

import com.jtripled.ductwork.Ductwork;
import com.jtripled.ductwork.container.ContainerFilterHopper;
import com.jtripled.ductwork.network.MessageHopperBlacklist;
import com.jtripled.ductwork.tile.TileFilterHopper;
import com.jtripled.voxen.gui.GUIButtonToggle;
import com.jtripled.voxen.gui.GUIContainerTile;
import com.jtripled.voxen.gui.GUIInventorySlot;
import com.jtripled.voxen.gui.GUIInventorySlotBlacklist;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

/**
 *
 * @author jtripled
 */
public class GUIFilterHopper extends GUIContainerTile<ContainerFilterHopper>
{
    public GUIFilterHopper(ContainerFilterHopper container)
    {
        super(container);
        this.setType(Type.INVENTORY_2_5);
    }
    
    @Override
    public void addElements(int x, int y)
    {
        for (int i = 0; i < 5; i++)
        {
            this.addElement(new GUIInventorySlot(this, 43 + i * 18, 17));
        }
        for (int i = 0; i < 5; i++)
        {
            this.addElement(new GUIInventorySlotBlacklist(this, 43 + i * 18, 44) {
                @Override
                public boolean getBlacklistState() {
                    return container.getTile().isBlacklist();
                }
            });
        }
        this.addElement(new GUIButtonToggle(this, x + 138, y + 43) {
                @Override
                public boolean getState() {
                    return container.getTile().isBlacklist();
                }
                @Override
                public String[] getTooltip(boolean state) {
                    TextComponentString message = new TextComponentString(state ? Ductwork.INSTANCE.getProxy().localize("ductwork.blacklist") : Ductwork.INSTANCE.getProxy().localize("ductwork.whitelist"));
                    message.getStyle().setColor(state ? TextFormatting.RED : TextFormatting.GRAY);
                    return new String[] { message.getFormattedText() };
                }
                @Override
                public void onClick() {
                    TileFilterHopper tile = container.getTile();
                    boolean blacklist = !tile.isBlacklist();
                    tile.setBlacklist(blacklist);
                    tile.markDirty();
                    Ductwork.INSTANCE.getNetwork().sendToServer(new MessageHopperBlacklist(tile.getPos(), blacklist));
                }});
    }
}
