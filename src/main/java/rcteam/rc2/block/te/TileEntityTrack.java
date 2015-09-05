package rcteam.rc2.block.te;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import rcteam.rc2.rollercoaster.*;

import java.util.LinkedHashSet;
import java.util.List;

public class TileEntityTrack extends TileEntity {
	public TrackPieceInfo info;
	private List<BlockPos> dummies = Lists.newArrayList();
	private LinkedHashSet<BlockPos> parentPositions = Sets.newLinkedHashSet();
	private boolean isDummy = false;
	//TODO: make dummies have multiple parents if they are "placed" by multiple tracks

	public TileEntityTrack() {}

	public TileEntityTrack(TrackPieceInfo info) {
		this.info = info;
	}

	public TrackPieceInfo getInfo() {
		return this.info;
	}

	public TileEntityTrack setDummy(boolean isDummy) {
		this.isDummy = isDummy;
		return this;
	}

	public boolean isDummy() {
		return this.isDummy;
	}

	public boolean addDummyPos(BlockPos pos) {
		return !this.isDummy && this.dummies.add(pos);
	}

	public boolean addDummyPositions(List<BlockPos> list) {
		return !this.isDummy && this.dummies.addAll(list);
	}

	public List<BlockPos> getDummyPositions() {
		return this.dummies;
	}

	public int getNumberOfParents() {
		return this.parentPositions.size();
	}

	public boolean addParentPos(BlockPos pos) {
		if (!this.isDummy) return false;
		return this.parentPositions.add(pos);
	}

	public boolean removeParentPos(BlockPos pos) {
		return this.parentPositions.remove(pos);
	}

	public LinkedHashSet<BlockPos> getParentPositions() {
		return this.parentPositions;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("info")) this.info = TrackPieceInfo.readFromNBT(compound.getCompoundTag("info"));
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
}
