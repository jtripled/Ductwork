package com.jtripled.ductwork.block;

import com.jtripled.ductwork.container.ContainerItemDuct;
import com.jtripled.ductwork.gui.GUIItemDuct;
import com.jtripled.ductwork.tile.TileItemDuct;
import com.jtripled.voxen.block.BlockDuct;
import com.jtripled.voxen.block.IBlockFaceable;
import com.jtripled.voxen.gui.GUIHolder;
import com.jtripled.voxen.util.Tab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import static net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

/**
 *
 * @author jtripled
 */
public class BlockItemDuct extends BlockDuct implements IBlockFaceable.All, GUIHolder
{
    public BlockItemDuct()
    {
        super("item_duct", Material.IRON);
        this.setTab(Tab.REDSTONE);
        this.setItem();
        this.setTileClass(TileItemDuct.class);
        this.setFullCube(false);
        this.setOpaque(false);
        this.setRenderLayer(BlockRenderLayer.CUTOUT_MIPPED);
        this.setTopSolid(true);
        this.setRenderSides(true);
    }

    @Override
    public boolean canConnect(BlockPos pos, IBlockState state, IBlockAccess world, BlockPos otherPos, IBlockState otherState, EnumFacing face)
    {
        TileEntity tile = world.getTileEntity(otherPos);
        return tile != null && face != state.getValue(FACING)
                && tile.hasCapability(ITEM_HANDLER_CAPABILITY, face.getOpposite());
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
