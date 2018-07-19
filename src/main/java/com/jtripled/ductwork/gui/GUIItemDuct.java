package com.jtripled.ductwork.gui;

import com.jtripled.ductwork.Ductwork;
import com.jtripled.ductwork.container.ContainerItemDuct;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author jtripled
 */
public class GUIItemDuct extends GuiContainer
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(Ductwork.ID, "textures/gui/item_duct.png");
    
    public GUIItemDuct(ContainerItemDuct container)
    {
        super(container);
        this.ySize = 132;
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
        fontRenderer.drawString(Ductwork.getProxy().localize("tile.item_duct.name"), 8, 6, 0x404040);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(TEXTURE);
        
        
    }
}
