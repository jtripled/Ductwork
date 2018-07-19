package com.jtripled.ductwork.block;

import com.jtripled.ductwork.Ductwork;
import com.jtripled.ductwork.tile.TileItemDuct;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import static net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

/**
 *
 * @author jtripled
 */
public final class BlockItemDuct extends Block
{
    public static final String NAME = "item_duct";
    public static final ResourceLocation RESOURCE = new ResourceLocation(Ductwork.ID, NAME);
    public static final AxisAlignedBB BOX = new AxisAlignedBB(0.3125, 0.3125, 0.3125, 0.6875, 0.6875, 0.6875);
    public static final int GUI_ID = 1;
    
    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    
    public BlockItemDuct()
    {
        super(Material.IRON);
        this.setUnlocalizedName(NAME);
        this.setRegistryName(RESOURCE);
        this.setCreativeTab(CreativeTabs.REDSTONE);
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            player.openGui(Ductwork.getInstance(), GUI_ID, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
    
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, facing.getOpposite());
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING, UP, DOWN, NORTH, EAST, SOUTH, WEST});
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }
    
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return state.withProperty(UP, canConnect(state, world, pos.up(), EnumFacing.UP))
                    .withProperty(DOWN, canConnect(state, world, pos.down(), EnumFacing.DOWN))
                    .withProperty(NORTH, canConnect(state, world, pos.north(), EnumFacing.NORTH))
                    .withProperty(EAST, canConnect(state, world, pos.east(), EnumFacing.EAST))
                    .withProperty(SOUTH, canConnect(state, world, pos.south(), EnumFacing.SOUTH))
                    .withProperty(WEST, canConnect(state, world, pos.west(), EnumFacing.WEST));
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }
    
    @Override
    public TileItemDuct createTileEntity(World world, IBlockState state)
    {
        return new TileItemDuct();
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        AxisAlignedBB bb = BOX;
        state = state.getActualState(world, pos);
        switch (getFacing(state))
        {
            case UP: state = state.withProperty(UP, true); break;
            case DOWN: state = state.withProperty(DOWN, true); break;
            case NORTH: state = state.withProperty(NORTH, true); break;
            case EAST: state = state.withProperty(EAST, true); break;
            case SOUTH: state = state.withProperty(SOUTH, true); break;
            case WEST: state = state.withProperty(WEST, true); break;
        }
        if (isConnectedUp(state))    bb = bb.expand( 0,       0.3125, 0);
        if (isConnectedDown(state))  bb = bb.expand( 0,      -0.3125, 0);
        if (isConnectedSouth(state)) bb = bb.expand( 0,       0,      0.3125);
        if (isConnectedNorth(state)) bb = bb.expand( 0,       0,     -0.3125);
        if (isConnectedEast(state))  bb = bb.expand( 0.3125,  0,      0);
        if (isConnectedWest(state))  bb = bb.expand(-0.3125,  0,      0);
        return bb;
    }
    
    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    
    @Override
    public boolean isTopSolid(IBlockState state)
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    
    public static boolean canConnect(IBlockState state, IBlockAccess world, BlockPos otherPos, EnumFacing face)
    {
        TileEntity tile = world.getTileEntity(otherPos);
        return tile != null && face != state.getValue(FACING)
                && tile.hasCapability(ITEM_HANDLER_CAPABILITY, face.getOpposite());
    }
    
    public static EnumFacing getFacing(IBlockState state)
    {
        return state.getValue(FACING);
    }
    
    public static boolean isFacingUp(IBlockState state)
    {
        return state.getValue(FACING) == EnumFacing.UP;
    }
    
    public static boolean isFacingDown(IBlockState state)
    {
        return state.getValue(FACING) == EnumFacing.DOWN;
    }
    
    public static boolean isFacingNorth(IBlockState state)
    {
        return state.getValue(FACING) == EnumFacing.NORTH;
    }
    
    public static boolean isFacingEast(IBlockState state)
    {
        return state.getValue(FACING) == EnumFacing.EAST;
    }
    
    public static boolean isFacingSouth(IBlockState state)
    {
        return state.getValue(FACING) == EnumFacing.SOUTH;
    }
    
    public static boolean isFacingWest(IBlockState state)
    {
        return state.getValue(FACING) == EnumFacing.WEST;
    }
    
    public static boolean isConnectedUp(IBlockState state)
    {
        return state.getValue(UP);
    }
    
    public static boolean isConnectedDown(IBlockState state)
    {
        return state.getValue(DOWN);
    }
    
    public static boolean isConnectedNorth(IBlockState state)
    {
        return state.getValue(NORTH);
    }
    
    public static boolean isConnectedEast(IBlockState state)
    {
        return state.getValue(EAST);
    }
    
    public static boolean isConnectedSouth(IBlockState state)
    {
        return state.getValue(SOUTH);
    }
    
    public static boolean isConnectedWest(IBlockState state)
    {
        return state.getValue(WEST);
    }
}
