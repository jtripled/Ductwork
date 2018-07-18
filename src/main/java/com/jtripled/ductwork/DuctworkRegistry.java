package com.jtripled.ductwork;

import com.jtripled.ductwork.block.*;
import com.jtripled.ductwork.network.MessageHandlerHopperBlacklist;
import com.jtripled.ductwork.network.MessageHopperBlacklist;
import com.jtripled.voxen.block.IBlockBase;
import com.jtripled.voxen.mod.RegistrationHandler;
import com.jtripled.voxen.mod.Registry;
import net.minecraftforge.fml.relauncher.Side;

/**
 *
 * @author jtripled
 */
public class DuctworkRegistry implements Registry
{
    public static final IBlockBase FILTER_HOPPER = new BlockFilterHopper();
    public static final IBlockBase ITEM_DUCT = new BlockItemDuct();
    
    @Override
    public void onRegisterBlocks(RegistrationHandler handler)
    {
        handler.registerBlock(FILTER_HOPPER);
        handler.registerBlock(ITEM_DUCT);
    }
    
    @Override
    public void onRegisterMessages(RegistrationHandler handler)
    {
        handler.registerMessage(MessageHandlerHopperBlacklist.class, MessageHopperBlacklist.class, Side.SERVER);
    }
}
