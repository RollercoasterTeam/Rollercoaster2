package rcteam.rc2.rollercoaster;

import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.StringUtils;

import javax.vecmath.Vector3f;

public class TrackPiece implements Comparable {
	private String name = "default_name";
	private String displayName = "Default Name";
	private Vector3f size = new Vector3f(1, 1, 1);

	public TrackPiece(String name, Vector3f size) {
		this.name = name;
		this.displayName = this.getDisplayName();
		this.size = size;
	}

	public String getName() {
		return this.name;
	}

	public String getDisplayName() {
//		if (this.displayName != null) return this.displayName;
		String ret = this.name.replaceAll("_", " ");
		StringUtils.capitalize(ret);
		this.displayName = ret;
		return ret;
	}

	public Vector3f getSize() {
		return this.size;
	}

	public NBTTagCompound writeToNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setString("name", this.name);
		compound.setFloat("size_x", this.size.x);
		compound.setFloat("size_y", this.size.y);
		compound.setFloat("size_z", this.size.z);
		return compound;
	}

	public static TrackPiece readFromNBT(NBTTagCompound compound) {
		String name = compound.getString("name");
		Vector3f size = new Vector3f(compound.getFloat("size_x"), compound.getFloat("size_y"), compound.getFloat("size_z"));
		return new TrackPiece(name, size);
	}

	@Override
	public int compareTo(Object o) {
		if (!(o instanceof TrackPiece)) return -1;
		TrackPiece piece = (TrackPiece) o;
		if (piece == this) return 0;
		return piece.getName().compareTo(this.getName());
	}

	@Override
	public String toString() {
		return this.name;
	}
}
