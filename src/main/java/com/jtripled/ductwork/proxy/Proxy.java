package com.jtripled.ductwork.proxy;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 *
 * @author jtripled
 */
public class Proxy
{
    public String localize(String unlocalized, Object... args)
    {
        return null;
    }
    
    public void registerItem(RegistryEvent.Register<Item> event, Item item)
    {
        event.getRegistry().register(item);
    }
    
    public void registerBlock(RegistryEvent.Register<Block> event, Block block, Class<? extends TileEntity> tileClass, IProperty... ignoredProperties)
    {
        event.getRegistry().register(block);
        if (tileClass != null)
            GameRegistry.registerTileEntity(tileClass, block.getRegistryName());
    }
}
