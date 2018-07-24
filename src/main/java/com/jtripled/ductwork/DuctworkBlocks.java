package com.jtripled.ductwork;

import com.jtripled.ductwork.block.BlockFilterHopper;
import com.jtripled.ductwork.block.BlockItemDuct;
import com.jtripled.ductwork.tile.TileFilterHopper;
import com.jtripled.ductwork.tile.TileItemDuct;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
        Ductwork.getProxy().registerBlock(event, FILTER_HOPPER, TileFilterHopper.class, BlockFilterHopper.ENABLED);
        Ductwork.getProxy().registerBlock(event, ITEM_DUCT, TileItemDuct.class);
    }
}
