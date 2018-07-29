package com.jtripled.ductwork.item;

import com.jtripled.ductwork.block.BlockFilterHopper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

/**
 *
 * @author jtripled
 */
public class ItemBlockFilterHopper extends ItemBlock
{
    public ItemBlockFilterHopper(BlockFilterHopper block)
    {
        super(block);
        this.setUnlocalizedName(block.getUnlocalizedName());
        this.setRegistryName(block.getRegistryName());
        this.setCreativeTab(CreativeTabs.REDSTONE);
    }
}
