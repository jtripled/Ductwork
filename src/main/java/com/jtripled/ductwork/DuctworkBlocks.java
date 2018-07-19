package com.jtripled.ductwork;

import com.jtripled.ductwork.block.BlockFilterHopper;
import com.jtripled.ductwork.block.BlockItemDuct;
import com.jtripled.ductwork.tile.TileFilterHopper;
import com.jtripled.ductwork.tile.TileItemDuct;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
        event.getRegistry().register(FILTER_HOPPER);
        event.getRegistry().register(ITEM_DUCT);
        
        GameRegistry.registerTileEntity(TileFilterHopper.class, FILTER_HOPPER.getRegistryName().toString());
        GameRegistry.registerTileEntity(TileItemDuct.class, ITEM_DUCT.getRegistryName().toString());
        
        Ductwork.getProxy().registerIgnoredProperties(FILTER_HOPPER, BlockFilterHopper.ENABLED);
    }
    
    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event)
    {
        Item FILTER_HOPPER_ITEM = (new ItemBlock(FILTER_HOPPER)).setRegistryName(FILTER_HOPPER.getRegistryName()).setUnlocalizedName(FILTER_HOPPER.getUnlocalizedName());
        Item ITEM_DUCT_ITEM = (new ItemBlock(ITEM_DUCT)).setRegistryName(ITEM_DUCT.getRegistryName()).setUnlocalizedName(ITEM_DUCT.getUnlocalizedName());
        
        event.getRegistry().register(FILTER_HOPPER_ITEM);
        event.getRegistry().register(ITEM_DUCT_ITEM);
        
        Ductwork.getProxy().registerItemRenderer(FILTER_HOPPER_ITEM, "normal");
        Ductwork.getProxy().registerItemRenderer(ITEM_DUCT_ITEM, "normal");
    }
}
