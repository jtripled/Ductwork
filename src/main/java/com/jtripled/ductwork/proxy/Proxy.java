package com.jtripled.ductwork.proxy;

import com.jtripled.ductwork.Ductwork;
import com.jtripled.ductwork.container.ContainerFilterHopper;
import com.jtripled.ductwork.container.ContainerItemDuct;
import com.jtripled.ductwork.network.MessageHandlerHopperBlacklist;
import com.jtripled.ductwork.network.MessageHopperBlacklist;
import com.jtripled.ductwork.tile.TileFilterHopper;
import com.jtripled.ductwork.tile.TileItemDuct;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

/**
 *
 * @author jtripled
 */
public class Proxy implements IGuiHandler
{
    public void onPreInit(FMLPreInitializationEvent event)
    {
        
    }
    
    public void onInit(FMLInitializationEvent event)
    {
        Ductwork.getNetwork().registerMessage(MessageHandlerHopperBlacklist.class, MessageHopperBlacklist.class, 0, Side.SERVER);
        NetworkRegistry.INSTANCE.registerGuiHandler(Ductwork.getInstance(), this);
    }
    
    public void onPostInit(FMLPostInitializationEvent event)
    {
        
    }
    
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

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case 0:
                return new ContainerFilterHopper((TileFilterHopper) world.getTileEntity(new BlockPos(x, y, z)), player.inventory);
            case 1:
                return new ContainerItemDuct((TileItemDuct) world.getTileEntity(new BlockPos(x, y, z)), player.inventory);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }
}
