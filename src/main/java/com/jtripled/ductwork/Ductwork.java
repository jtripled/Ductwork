package com.jtripled.ductwork;

import com.jtripled.ductwork.network.MessageHandlerHopperBlacklist;
import com.jtripled.ductwork.network.MessageHopperBlacklist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 *
 * @author jtripled
 */
@Mod(modid = Ductwork.ID, name = Ductwork.NAME, version = Ductwork.VERSION, dependencies = Ductwork.DEPENDS)
@Mod.EventBusSubscriber
public class Ductwork
{
    public static final String ID = "ductwork";
    public static final String NAME = "Ductwork";
    public static final String VERSION = "1.0";
    public static final String DEPENDS = "";
    
    @Mod.Instance(Ductwork.ID)
    public static Ductwork INSTANCE;
    
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
    }
    
    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event)
    {
        
    }
}
