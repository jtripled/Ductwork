package com.jtripled.ductwork;

import com.jtripled.ductwork.item.ItemBlockFilterHopper;
import com.jtripled.ductwork.item.ItemBlockItemDuct;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 *
 * @author jtripled
 */
@Mod.EventBusSubscriber
public class DuctworkItems
{
    public static final ItemBlockFilterHopper FILTER_HOPPER_ITEM = new ItemBlockFilterHopper(DuctworkBlocks.FILTER_HOPPER);
    public static final ItemBlockItemDuct ITEM_DUCT_ITEM = new ItemBlockItemDuct(DuctworkBlocks.ITEM_DUCT);
    
    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event)
    {
        register(event, FILTER_HOPPER_ITEM);
        register(event, ITEM_DUCT_ITEM);
    }
    
    private static void register(RegistryEvent.Register<Item> event, Item item)
    {
        Ductwork.getProxy().registerItem(event, item);
    }
}
