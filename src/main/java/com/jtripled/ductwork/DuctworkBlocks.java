package com.jtripled.ductwork;

import com.jtripled.ductwork.block.BlockFilterHopper;
import com.jtripled.ductwork.block.BlockItemDuct;
import com.jtripled.ductwork.tile.TileFilterHopper;
import com.jtripled.ductwork.tile.TileItemDuct;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 *
 * @author jtripled
 */
@Mod.EventBusSubscriber
public class DuctworkBlocks
{
    public static final Block FILTER_HOPPER = new BlockFilterHopper();
    public static final Block ITEM_DUCT = new BlockItemDuct();
    
    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().register(FILTER_HOPPER);
        event.getRegistry().register(ITEM_DUCT);
        
        GameRegistry.registerTileEntity(TileFilterHopper.class, FILTER_HOPPER.getRegistryName().toString());
        GameRegistry.registerTileEntity(TileItemDuct.class, ITEM_DUCT.getRegistryName().toString());
        
        Ductwork.getProxy().registerIgnoredProperties(FILTER_HOPPER, BlockFilterHopper.ENABLED);
    }
}
