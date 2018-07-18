package com.jtripled.ductwork.block;

import com.jtripled.ductwork.container.ContainerFilterHopper;
import com.jtripled.ductwork.gui.GUIFilterHopper;
import com.jtripled.ductwork.tile.TileFilterHopper;
import com.jtripled.voxen.block.BlockBase;
import com.jtripled.voxen.block.IBlockEnableable;
import com.jtripled.voxen.block.IBlockFaceable;
import com.jtripled.voxen.block.IBlockStorage;
import com.jtripled.voxen.gui.GUIHolder;
import com.jtripled.voxen.util.Tab;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 *
 * @author jtripled
 */
public class BlockFilterHopper extends BlockBase implements IBlockFaceable.NoUp, IBlockEnableable, IBlockStorage, GUIHolder
{
    public BlockFilterHopper()
    {
        super("filter_hopper", Material.IRON);
        this.setTab(Tab.REDSTONE);
        this.setItem();
        this.setTileClass(TileFilterHopper.class);
        this.setFullCube(false);
        this.setOpaque(false);
        this.setRenderLayer(BlockRenderLayer.CUTOUT_MIPPED);
        this.setTopSolid(true);
        this.setRenderSides(true);
    }
    
    @Override
    public ContainerFilterHopper getServerGUI(EntityPlayer player, World world, BlockPos pos)
    {
        return new ContainerFilterHopper((TileFilterHopper) world.getTileEntity(pos), player.inventory);
    }
    
    @Override
    public GUIFilterHopper getClientGUI(EntityPlayer player, World world, BlockPos pos)
    {
        return new GUIFilterHopper(getServerGUI(player, world, pos));
    }
}
