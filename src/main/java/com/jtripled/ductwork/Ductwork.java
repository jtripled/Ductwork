package com.jtripled.ductwork;

import com.jtripled.voxen.mod.ModBase;
import com.jtripled.voxen.mod.Registry;
import com.jtripled.voxen.network.Network;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 *
 * @author jtripled
 */
@Mod(modid = Ductwork.ID, name = Ductwork.NAME, version = Ductwork.VERSION)
@Mod.EventBusSubscriber
public class Ductwork extends ModBase
{
    public static final String ID = "ductwork";
    public static final String NAME = "Ductwork";
    public static final String VERSION = "1.0";
    
    @Mod.Instance(Ductwork.ID)
    public static Ductwork INSTANCE;
    
    public static Registry REGISTRY;
    
    public static final Network NETWORK = new Network(ID);

    @Override
    public String getID()
    {
        return ID;
    }

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public String getVersion()
    {
        return VERSION;
    }
    
    @Override
    public Registry getRegistry()
    {
        return REGISTRY;
    }
    
    @Override
    public Network getNetwork()
    {
        return NETWORK;
    }
    
    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event)
    {
        REGISTRY = new DuctworkRegistry();
        preInit(event);
    }
    
    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event)
    {
        init(event);
    }
    
    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event)
    {
        postInit(event);
    }
}
