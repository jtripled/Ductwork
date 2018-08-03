package com.jtripled.ductwork;

import com.jtripled.ductwork.block.BlockFilterHopper;
import com.jtripled.ductwork.block.BlockItemDuct;
import com.jtripled.ductwork.tile.TileFilterHopper;
import com.jtripled.ductwork.tile.TileItemDuct;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

/**
 *
 * @author jtripled
 */
@Mod.EventBusSubscriber
@ObjectHolder(Ductwork.ID)
public class DuctworkBlocks
{
    public static final BlockFilterHopper FILTER_HOPPER = null;
    public static final BlockItemDuct ITEM_DUCT = null;
    
    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event)
    {
        register(event, new BlockFilterHopper(), TileFilterHopper.class, BlockFilterHopper.ENABLED);
        register(event, new BlockItemDuct(), TileItemDuct.class);
    }
    
    private static void register(RegistryEvent.Register<Block> event, Block block, Class<? extends TileEntity> tileClass, IProperty... ignoredProperties)
    {
        Ductwork.getProxy().registerBlock(event, block, tileClass, ignoredProperties);
    }
}
