package com.jtripled.ductwork.proxy;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;

/**
 *
 * @author jtripled
 */
public class ProxyClient extends Proxy
{
    @Override
    public String localize(String unlocalized, Object... args)
    {
        return net.minecraft.client.resources.I18n.format(unlocalized, args);
    }
    
    @Override
    public void registerItem(RegistryEvent.Register<Item> event, Item item)
    {
        super.registerItem(event, item);
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "normal"));
    }
    
    @Override
    public void registerBlock(RegistryEvent.Register<Block> event, Block block, Class<? extends TileEntity> tileClass, IProperty... ignoredProperties)
    {
        super.registerBlock(event, block, tileClass, ignoredProperties);
        if (ignoredProperties.length > 0)
            ModelLoader.setCustomStateMapper((Block) block, (new StateMap.Builder()).ignore(ignoredProperties).build());
    }
}
