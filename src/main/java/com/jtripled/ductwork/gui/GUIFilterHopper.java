package com.jtripled.ductwork.gui;

import com.jtripled.ductwork.Ductwork;
import com.jtripled.ductwork.container.ContainerFilterHopper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author jtripled
 */
public class GUIFilterHopper extends GuiContainer
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(Ductwork.ID, "textures/gui/filter_hopper.png");
    
    public GUIFilterHopper(ContainerFilterHopper container)
    {
        super(container);
        this.ySize = 159;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float ticks, int mouseX, int mouseY)
    {
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        
        drawDefaultBackground();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(TEXTURE);
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        
        fontRenderer.drawString("Inventory", 8, ySize - 93, 0x404040);
        fontRenderer.drawString("Item Duct", 8, 6, 0x404040);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(TEXTURE);
        
        
    }
    
    /*public void addElements(int x, int y)
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
    }*/
}
