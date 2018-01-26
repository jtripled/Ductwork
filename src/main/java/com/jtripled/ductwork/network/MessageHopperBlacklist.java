package com.jtripled.ductwork.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 *
 * @author jtripled
 */
public class MessageHopperBlacklist implements IMessage
{
    private BlockPos location;
    private boolean blacklist;

    public MessageHopperBlacklist()
    {

    }

    public MessageHopperBlacklist(BlockPos location, boolean blacklist)
    {
        this.location = location;
        this.blacklist = blacklist;
    }

    public BlockPos getLocation()
    {
        return location;
    }

    public boolean getBlacklist()
    {
        return blacklist;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        location = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        blacklist = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(location.getX());
        buf.writeInt(location.getY());
        buf.writeInt(location.getZ());
        buf.writeBoolean(blacklist);
    }
}
