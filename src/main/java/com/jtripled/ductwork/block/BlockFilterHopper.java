package com.jtripled.ductwork.block;

import com.jtripled.ductwork.Ductwork;
import com.jtripled.ductwork.tile.TileFilterHopper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author jtripled
 */
public final class BlockFilterHopper extends Block
{
    public static final int GUI_ID = 0;
    
    public static final PropertyDirection FACING = PropertyDirection.create("facing", (EnumFacing face) -> { return face != EnumFacing.UP; });
    public static final PropertyBool ENABLED = PropertyBool.create("enabled");
    
    public BlockFilterHopper()
    {
        super(Material.IRON);
        this.setUnlocalizedName("filter_hopper");
        this.setRegistryName(Ductwork.getID(), "filter_hopper");
        this.setCreativeTab(CreativeTabs.REDSTONE);
        this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(ENABLED, true));
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
            player.openGui(Ductwork.getInstance(), GUI_ID, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
    
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileFilterHopper tile = (TileFilterHopper) world.getTileEntity(pos);
        
        for (int i = 0; i < tile.getInventory().getSlots(); ++i)
        {
            ItemStack stack = tile.getInventory().getStackInSlot(i);
            if (!stack.isEmpty())
            {
                double x = pos.getX();
                double y = pos.getY();
                double z = pos.getZ();
                
                float f = RANDOM.nextFloat() * 0.8F + 0.1F;
                float f1 = RANDOM.nextFloat() * 0.8F + 0.1F;
                float f2 = RANDOM.nextFloat() * 0.8F + 0.1F;

                while (!stack.isEmpty())
                {
                    EntityItem entityitem = new EntityItem(world, x + (double)f, y + (double)f1, z + (double)f2, stack.splitStack(RANDOM.nextInt(21) + 10));
                    float f3 = 0.05F;
                    entityitem.motionX = RANDOM.nextGaussian() * 0.05000000074505806D;
                    entityitem.motionY = RANDOM.nextGaussian() * 0.05000000074505806D + 0.20000000298023224D;
                    entityitem.motionZ = RANDOM.nextGaussian() * 0.05000000074505806D;
                    world.spawnEntity(entityitem);
                }
            }
        }

        super.breakBlock(world, pos, state);
    }
    
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        if (facing.getOpposite() == EnumFacing.UP)
            return this.getDefaultState().withProperty(FACING, EnumFacing.DOWN);
        return this.getDefaultState().withProperty(FACING, facing.getOpposite());
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING, ENABLED});
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7)).withProperty(ENABLED, (meta & 8) != 8);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return (state.getValue(FACING).getIndex() & 7) | (state.getValue(ENABLED) ? 8 : 0);
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }
    
    @Override
    public TileFilterHopper createTileEntity(World world, IBlockState state)
    {
        return new TileFilterHopper();
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
        return true;
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
}
