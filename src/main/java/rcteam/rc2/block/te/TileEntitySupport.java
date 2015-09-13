package rcteam.rc2.block.te;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;
import rcteam.rc2.block.BlockSupport;
import rcteam.rc2.network.NetworkHandler;
import rcteam.rc2.rollercoaster.SupportUtils.*;

import java.util.Iterator;

public class TileEntitySupport extends TileEntity {
	public SupportInfo info;

	public TileEntitySupport() {}

	public TileEntitySupport(SupportInfo info) {
		this.info = info;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("info")) this.info = SupportInfo.readFromNBT(compound.getCompoundTag("info"));
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if (this.info != null) compound.setTag("info", this.info.writeToNBT());
	}

	@Override
	public void onChunkUnload() {
		this.writeToNBT(this.getTileData());
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		return new S35PacketUpdateTileEntity(this.getPos(), this.getBlockMetadata(), compound);
	}

	@Override
	public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
		this.readFromNBT(packet.getNbtCompound());
	}

	@SuppressWarnings("unchecked")
	public static void updateSupportColumn(World world, BlockPos input, SupportSlot slot, String operation, boolean focusOnly) {
		boolean toggle = operation.equalsIgnoreCase("toggle");
		if (focusOnly) {
			if (world.getTileEntity(input) != null && world.getTileEntity(input) instanceof TileEntitySupport) {
				TileEntitySupport support = (TileEntitySupport) world.getTileEntity(input);
				if (!(world.getBlockState(input.down()).getBlock() instanceof BlockSupport)) {
					support.info.setBasePlateVisibility(slot, toggle ? support.info.getBasePlateVisibility(slot) : Boolean.valueOf(operation));
				}
				if (world.getBlockState(input.up()).getBlock() instanceof BlockSupport) {
					support.info.setVisibility(slot, toggle ? support.info.getVisibility(slot) : Boolean.valueOf(operation));
				} else support.info.setTopVisibility(slot, toggle ? support.info.getTopVisibility(slot) : Boolean.valueOf(operation));
				world.markBlockRangeForRenderUpdate(input, input);
				NetworkHandler.updateSupportInfo(support.info.writeToNBT(), input);
			}
		} else {
			BlockPos start = input;
			BlockPos end = input;
			if (world.getBlockState(input).getBlock() instanceof BlockSupport) {
				while (world.getBlockState(start.down()).getBlock() instanceof BlockSupport) start = start.down();
				while (world.getBlockState(end.up()).getBlock() instanceof BlockSupport) end = end.up();
			}
			Iterator<BlockPos> iterator = BlockPos.getAllInBox(start, end).iterator();
			while (iterator.hasNext()) {
				BlockPos pos = iterator.next();
				TileEntitySupport support = (TileEntitySupport) world.getTileEntity(pos);
				if (!(world.getBlockState(pos.down()).getBlock() instanceof BlockSupport)) {
					support.info.setBasePlateVisibility(slot, toggle ? !support.info.getBasePlateVisibility(slot) : Boolean.valueOf(operation));
					support.info.setVisibility(slot, toggle ? !support.info.getVisibility(slot) : Boolean.valueOf(operation));
				} else if (!(world.getBlockState(pos.up()).getBlock() instanceof BlockSupport)) {
					support.info.setTopVisibility(slot, toggle ? !support.info.getTopVisibility(slot) : Boolean.valueOf(operation));
				} else {
					support.info.setVisibility(slot, toggle ? !support.info.getVisibility(slot) : Boolean.valueOf(operation));
				}
				NetworkHandler.updateSupportInfo(support.info.writeToNBT(), pos);
			}
			world.markBlockRangeForRenderUpdate(start, end);
		}
	}

	@Mod.EventHandler
	public void saveSupportData(WorldEvent.Save event) {
		this.writeToNBT(this.getTileData());
	}
}
