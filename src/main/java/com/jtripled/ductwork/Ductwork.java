package com.jtripled.ductwork;

import com.jtripled.ductwork.container.ContainerFilterHopper;
import com.jtripled.ductwork.container.ContainerItemDuct;
import com.jtripled.ductwork.network.MessageHandlerHopperBlacklist;
import com.jtripled.ductwork.network.MessageHopperBlacklist;
import com.jtripled.ductwork.proxy.Proxy;
import com.jtripled.ductwork.tile.TileFilterHopper;
import com.jtripled.ductwork.tile.TileItemDuct;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 *
 * @author jtripled
 */
@Mod(modid = Ductwork.ID, name = Ductwork.NAME, version = Ductwork.VERSION, dependencies = Ductwork.DEPENDS)
@Mod.EventBusSubscriber
public class Ductwork implements IGuiHandler
{
    public static final String ID = "ductwork";
    public static final String NAME = "Ductwork";
    public static final String VERSION = "1.0";
    public static final String DEPENDS = "";
    
    @Mod.Instance(ID)
    public static Ductwork INSTANCE;
    
    @SidedProxy(serverSide = "com.jtripled." + ID + ".proxy.ProxyServer", clientSide = "com.jtripled." + ID + ".proxy.ProxyClient")
    public static final Proxy PROXY = null;
    
    public static final SimpleNetworkWrapper NETWORK = new SimpleNetworkWrapper(ID);
    
    public static Ductwork getInstance()
    {
        return INSTANCE;
    }

    public static String getID()
    {
        return ID;
    }

    public static String getName()
    {
        return NAME;
    }

    public static String getVersion()
    {
        return VERSION;
    }
    
    public static Proxy getProxy()
    {
        return PROXY;
    }
    
    public SimpleNetworkWrapper getNetwork()
    {
        return NETWORK;
    }
    
    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event)
    {
        
    }
    
    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event)
    {
        NETWORK.registerMessage(MessageHandlerHopperBlacklist.class, MessageHopperBlacklist.class, 0, Side.SERVER);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, this);
    }
    
    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event)
    {
        
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
