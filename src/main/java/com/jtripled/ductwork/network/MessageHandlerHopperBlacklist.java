package com.jtripled.ductwork.network;

import com.jtripled.ductwork.tile.TileGratedHopper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 *
 * @author jtripled
 */
public class MessageHandlerHopperBlacklist implements IMessageHandler<MessageHopperBlacklist, IMessage>
{
    @Override
    public IMessage onMessage(MessageHopperBlacklist message, MessageContext context)
    {
        EntityPlayerMP player = context.getServerHandler().player;
        BlockPos location = message.getLocation();
        boolean blacklist = message.getBlacklist();
        TileEntity tile = player.world.getTileEntity(location);
        
        if (tile instanceof TileGratedHopper)
            ((TileGratedHopper) tile).setBlacklist(blacklist);
        
        return null;
    }
}
