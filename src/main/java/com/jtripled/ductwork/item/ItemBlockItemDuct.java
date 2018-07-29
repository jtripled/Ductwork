package com.jtripled.ductwork.item;

import com.jtripled.ductwork.block.BlockItemDuct;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

/**
 *
 * @author jtripled
 */
public class ItemBlockItemDuct extends ItemBlock
{
    public ItemBlockItemDuct(BlockItemDuct block)
    {
        super(block);
        this.setUnlocalizedName(block.getUnlocalizedName());
        this.setRegistryName(block.getRegistryName());
        this.setCreativeTab(CreativeTabs.REDSTONE);
    }
}
