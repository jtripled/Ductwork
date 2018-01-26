package com.jtripled.ductwork.block;

import com.jtripled.ductwork.Ductwork;
import com.jtripled.ductwork.container.ContainerItemDuct;
import com.jtripled.ductwork.gui.GUIItemDuct;
import com.jtripled.ductwork.tile.TileItemDuct;
import com.jtripled.voxen.block.BlockDuct;
import com.jtripled.voxen.block.IBlockStorage;
import com.jtripled.voxen.gui.GUIHolder;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

/**
 *
 * @author jtripled
 */
public class BlockItemDuct extends BlockDuct implements IBlockStorage, GUIHolder
{
    public BlockItemDuct()
    {
        super(Ductwork.INSTANCE, "item_duct", Material.IRON);
        this.setTab(CreativeTabs.REDSTONE);
        this.setItem();
        this.setTileClass(TileItemDuct.class);
    }

    @Override
    public boolean canConnect(IBlockState state, IBlockAccess world, BlockPos otherPos, EnumFacing otherFacing)
    {
        TileEntity tile = world.getTileEntity(otherPos);
        return tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, otherFacing);
    }

    @Override
    public ContainerItemDuct getServerGUI(EntityPlayer player, World world, BlockPos pos)
    {
        return new ContainerItemDuct((TileItemDuct) world.getTileEntity(pos), player.inventory);
    }

    @Override
    public GUIItemDuct getClientGUI(EntityPlayer player, World world, BlockPos pos)
    {
        return new GUIItemDuct(getServerGUI(player, world, pos));
    }
}
