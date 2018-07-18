package com.jtripled.ductwork;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

/**
 *
 * @author jtripled
 */
@ObjectHolder(Ductwork.ID)
public class DuctworkBlocks
{
    public static final Block FILTER_HOPPER = null;
    public static final Block ITEM_DUCT = null;
    
    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().register(FILTER_HOPPER);
        event.getRegistry().register(ITEM_DUCT);
    }
}
