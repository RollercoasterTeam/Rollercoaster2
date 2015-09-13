package rcteam.rc2.block;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import rcteam.rc2.RC2;
import rcteam.rc2.block.te.TileEntitySupport;
import rcteam.rc2.rollercoaster.SupportUtils;
import rcteam.rc2.util.OBJModel;

import javax.vecmath.Vector3f;
import java.util.List;

public class BlockSupport extends Block {
	public ExtendedBlockState state = new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[] {OBJModel.OBJProperty.instance});
	private SupportUtils.SupportInfo info;

	public BlockSupport(SupportUtils.SupportInfo info) {
		super(info.type.material);
		this.info = info;
		if (RC2.isRunningInDev) setCreativeTab(RC2.tab);
		if (!RC2.isRunningInDev) setBlockUnbreakable();
		setUnlocalizedName("support_" + info.type.unlocalizedName);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return true;
	}

	@Override
	public boolean isVisuallyOpaque() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {
		Vector3f min = new Vector3f();
		Vector3f max = new Vector3f(1.0f, 1.0f, 1.0f);
		if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntitySupport) {
			TileEntitySupport support = (TileEntitySupport) world.getTileEntity(pos);
			List<SupportUtils.SupportSlot> visibleSlots = Lists.newArrayList();
			for (String s : support.info.type.getValidSupportSlotNames()) {
				if (support.info.isSlotVisible(SupportUtils.getSlotFromName(s)) && !visibleSlots.contains(SupportUtils.getSlotFromName(s))) visibleSlots.add(SupportUtils.getSlotFromName(s));
			}
			boolean fullHeight = false;
			for (SupportUtils.SupportSlot slot : visibleSlots) {
				if (support.info.getVisibility(slot) || support.info.getTopVisibility(slot)) {
					fullHeight = true;
					break;
				}
			}
			if (!visibleSlots.isEmpty()) {
				min.y = 0.0f;
				max.y = fullHeight ? visibleSlots.get(0).height : visibleSlots.get(0).heightBase;
				if (visibleSlots.size() == 1) {
					min.x = visibleSlots.get(0).centerXZ.x - (visibleSlots.get(0).widthXZ / 2.0f);
					max.x = visibleSlots.get(0).centerXZ.x + (visibleSlots.get(0).widthXZ / 2.0f);
					min.z = visibleSlots.get(0).centerXZ.y - (visibleSlots.get(0).widthXZ / 2.0f);
					max.z = visibleSlots.get(0).centerXZ.y + (visibleSlots.get(0).widthXZ / 2.0f);
				}
			}
			this.setBlockBounds(min.x, min.y, min.z, max.x, max.y, max.z);
		}
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntitySupport tileEntitySupport = (TileEntitySupport) world.getTileEntity(pos);
		List<String> visibleGroups = Lists.newArrayList();
		if (tileEntitySupport == null) visibleGroups.add(OBJModel.Group.ALL);
		else {
			visibleGroups.clear();
			visibleGroups.addAll(tileEntitySupport.info.getVisibleSlotNames());
			visibleGroups.addAll(tileEntitySupport.info.getVisibleSlotBaseNames());
			visibleGroups.addAll(tileEntitySupport.info.getVisibleSlotTopNames());
		}
		OBJModel.OBJState retState = new OBJModel.OBJState(visibleGroups, true);
		if (state instanceof IExtendedBlockState) {
			retState = ((IExtendedBlockState) state).getValue(OBJModel.OBJProperty.instance);
			RC2.logger.info(retState.getGroupNamesFromMap().size());
		}
		return ((IExtendedBlockState) this.state.getBaseState()).withProperty(OBJModel.OBJProperty.instance, retState);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntitySupport(this.info);
	}
}
