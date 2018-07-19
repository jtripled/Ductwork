package com.jtripled.ductwork.gui;

import com.jtripled.ductwork.Ductwork;
import com.jtripled.ductwork.container.ContainerFilterHopper;
import com.jtripled.ductwork.network.MessageHopperBlacklist;
import com.jtripled.ductwork.tile.TileFilterHopper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

/**
 *
 * @author jtripled
 */
public class GUIFilterHopper extends GuiContainer
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(Ductwork.ID, "textures/gui/filter_hopper.png");
    
    private final ContainerFilterHopper container;
    private final TileFilterHopper tile;
    
    public GUIFilterHopper(ContainerFilterHopper container)
    {
        super(container);
        this.ySize = 159;
        this.container = container;
        this.tile = container.getTile();
    }
    
    @Override
    public void initGui()
    {
        super.initGui();
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.buttonList.add(new GuiButton(1, x + 138, y + 43, 20, 20, ""));
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
        if (tile.isBlacklist())
            drawTexturedModalRect(x + 44, y + 45, 0, ySize, 88, 16);
        
        //blacklistButton.x = x + 138;
        //blacklistButton.y = y + 43;
        //blacklistButton.drawButton(mc, mouseX, mouseY, ticks);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        
        fontRenderer.drawString("Inventory", 8, ySize - 93, 0x404040);
        fontRenderer.drawString(Ductwork.getProxy().localize("tile.filter_hopper.name"), 8, 6, 0x404040);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(TEXTURE);
        
        if (tile.isBlacklist())
            drawTexturedModalRect(143, 48, 18, ySize + 16, 16, 16);
        else
            drawTexturedModalRect(143, 48, 0, ySize + 16, 16, 16);
        
        if (mouseX >= x + 138 && mouseY >= y + 43 && mouseX < x + 158 && mouseY < y + 63)
        {
            TextComponentString message = new TextComponentString(tile.isBlacklist() ? Ductwork.getProxy().localize("ductwork.blacklist") : Ductwork.getProxy().localize("ductwork.whitelist"));
            message.getStyle().setColor(tile.isBlacklist() ? TextFormatting.RED : TextFormatting.GRAY);
            drawHoveringText(message.getFormattedText(), mouseX - (width - xSize) / 2, mouseY - (height - ySize) / 2);
        }
    }
    
    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 1)
        {
            boolean blacklist = !tile.isBlacklist();
            tile.setBlacklist(blacklist);
            Ductwork.getNetwork().sendToServer(new MessageHopperBlacklist(tile.getPos(), blacklist));
        }
    }
}
