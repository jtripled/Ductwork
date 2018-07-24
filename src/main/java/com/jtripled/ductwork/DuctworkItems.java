package com.jtripled.ductwork;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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
    public static final Item FILTER_HOPPER_ITEM = new ItemBlock(DuctworkBlocks.FILTER_HOPPER).setRegistryName(DuctworkBlocks.FILTER_HOPPER.getRegistryName()).setUnlocalizedName(DuctworkBlocks.FILTER_HOPPER.getUnlocalizedName());
    public static final Item ITEM_DUCT_ITEM = new ItemBlock(DuctworkBlocks.ITEM_DUCT).setRegistryName(DuctworkBlocks.ITEM_DUCT.getRegistryName()).setUnlocalizedName(DuctworkBlocks.ITEM_DUCT.getUnlocalizedName());
    
    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event)
    {
        Ductwork.getProxy().registerItem(event, FILTER_HOPPER_ITEM);
        Ductwork.getProxy().registerItem(event, ITEM_DUCT_ITEM);
    }
}
