package com.jtripled.ductwork.gui;

import com.jtripled.ductwork.Ductwork;
import com.jtripled.ductwork.container.ContainerGratedHopper;
import com.jtripled.ductwork.network.MessageHopperBlacklist;
import com.jtripled.ductwork.tile.TileGratedHopper;
import com.jtripled.voxen.gui.GUIButton;
import com.jtripled.voxen.gui.GUIButtonToggle;
import com.jtripled.voxen.gui.GUIContainerTile;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

/**
 *
 * @author jtripled
 */
public class GUIGratedHopper extends GUIContainerTile<ContainerGratedHopper>
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(Ductwork.ID, "textures/gui/grated_hopper.png");
    
    public GUIGratedHopper(ContainerGratedHopper container)
    {
        super(container);
        this.xSize = 176;
        this.ySize = 159;
    }
    
    public boolean getBlacklistState()
    {
        return container.getTile().isBlacklist();
    }
    
    @Override
    public void addButtons(int x, int y)
    {
        this.addButton(new GUIButtonToggle(x + 138, y + 43) {
                @Override
                public boolean getState() {
                    return getBlacklistState();
                }
                @Override
                public String getTooltip(boolean state) {
                    TextComponentString message = new TextComponentString(state ? Ductwork.INSTANCE.getProxy().localize("ductwork.blacklist") : Ductwork.INSTANCE.getProxy().localize("ductwork.whitelist"));
                    message.getStyle().setColor(state ? TextFormatting.RED : TextFormatting.GRAY);
                    return message.getFormattedText();
                }},
                (GUIButton button) -> {
                    TileGratedHopper tile = this.getContainer().getTile();
                    boolean blacklist = !tile.isBlacklist();
                    tile.setBlacklist(blacklist);
                    tile.markDirty();
                    Ductwork.INSTANCE.getNetwork().sendToServer(new MessageHopperBlacklist(tile.getPos(), blacklist));
                });
    }
    
    @Override
    public void drawBackground(float ticks, int mouseX, int mouseY, int x, int y)
    {
        if (getBlacklistState())
            drawTexturedModalRect(x + 44, y + 45, 0, 159, 88, 16);
    }

    @Override
    public ResourceLocation getTexture()
    {
        return TEXTURE;
    }
}
