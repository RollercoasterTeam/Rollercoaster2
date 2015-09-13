package rcteam.rc2.block.te;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.common.util.Constants;
import rcteam.rc2.multiblock.MultiBlockManager;
import rcteam.rc2.multiblock.structures.MultiBlockTracks;
import rcteam.rc2.rollercoaster.*;

import java.util.LinkedHashSet;
import java.util.List;

public class TileEntityTrack extends TileEntity {
	public TrackPieceInfo info;
	private List<BlockPos> dummies = Lists.newArrayList();
	private LinkedHashSet<BlockPos> parentPositions = Sets.newLinkedHashSet();
	private boolean isDummy = false;

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
		if (compound.hasKey("dummy")) this.isDummy = compound.getBoolean("dummy");
		if (compound.hasKey("dummies")) {
			NBTTagList list = compound.getTagList("dummies", Constants.NBT.TAG_LONG);
			for (int i = 0; i < list.tagCount(); i++) {
				this.dummies.add(BlockPos.fromLong(((NBTTagLong) list.get(i)).getLong()));
			}
		}
		if (compound.hasKey("parents")) {
			NBTTagList list = new NBTTagList();
			for (int i = 0; i < list.tagCount(); i++) {
				this.parentPositions.add(BlockPos.fromLong(((NBTTagLong) list.get(i)).getLong()));
			}
		}
		if (compound.hasKey("multiblock")) {
			MultiBlockTracks multiBlockTracks = new MultiBlockTracks();
			multiBlockTracks.readFromNBT(compound.getCompoundTag("multiblock"));
			MultiBlockManager.structureMap.put(this.getPos(), multiBlockTracks);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if (this.info != null) compound.setTag("info", this.info.writeToNBT());
		compound.setBoolean("dummy", this.isDummy);
		NBTTagList dummyList = new NBTTagList();
		NBTTagList parentList = new NBTTagList();
		for (BlockPos dummyPos : this.dummies) {
			dummyList.appendTag(new NBTTagLong(dummyPos.toLong()));
		}
		for (BlockPos parentPos : this.parentPositions) {
			parentList.appendTag(new NBTTagLong(parentPos.toLong()));
		}
		compound.setTag("dummies", dummyList);
		compound.setTag("parents", parentList);
		if (!this.isDummy) {
			MultiBlockTracks multiBlockTracks = (MultiBlockTracks) MultiBlockManager.structureMap.get(this.getPos());
			if (multiBlockTracks != null) compound.setTag("multiblock", multiBlockTracks.writeToNBT());
		}
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
