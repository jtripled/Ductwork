package com.jtripled.ductwork.proxy;

import com.jtripled.ductwork.container.ContainerFilterHopper;
import com.jtripled.ductwork.container.ContainerItemDuct;
import com.jtripled.ductwork.gui.GUIFilterHopper;
import com.jtripled.ductwork.gui.GUIItemDuct;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 *
 * @author jtripled
 */
public class ProxyClient extends Proxy
{
    @Override
    public void onPreInit(FMLPreInitializationEvent event)
    {
        super.onPreInit(event);
    }
    
    @Override
    public void onInit(FMLInitializationEvent event)
    {
        super.onInit(event);
        //NETWORK.registerMessage(MessageHandlerHopperBlacklist.class, MessageHopperBlacklist.class, 0, Side.SERVER);
        //NetworkRegistry.INSTANCE.registerGuiHandler(this, this);
    }
    
    @Override
    public void onPostInit(FMLPostInitializationEvent event)
    {
        super.onPostInit(event);
    }
    
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

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case 0:
                return new GUIFilterHopper((ContainerFilterHopper) getServerGuiElement(ID, player, world, x, y, z));
            case 1:
                return new GUIItemDuct((ContainerItemDuct) getServerGuiElement(ID, player, world, x, y, z));
        }
        return null;
    }
}
