package com.jtripled.ductwork.gui;

import com.jtripled.ductwork.Ductwork;
import com.jtripled.ductwork.container.ContainerItemDuct;
import com.jtripled.voxen.gui.GUIContainerTile;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author jtripled
 */
public class GUIItemDuct extends GUIContainerTile<ContainerItemDuct>
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(Ductwork.ID, "textures/gui/item_duct.png");

    public GUIItemDuct(ContainerItemDuct container)
    {
        super(container);
        this.ySize = 132;
    }

    @Override
    public ResourceLocation getTexture()
    {
        return TEXTURE;
    }
}
