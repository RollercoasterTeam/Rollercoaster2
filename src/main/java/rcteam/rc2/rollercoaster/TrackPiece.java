package rcteam.rc2.rollercoaster;

import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.StringUtils;

import javax.vecmath.Vector3f;

public class TrackPiece {
	private String name = "Default Name";
	private String displayName = null;
	private Vector3f size = new Vector3f(1, 1, 1);

	public TrackPiece(String name, Vector3f size) {
		this.name = name;
		this.size = size;
	}

	public String getName() {
		return this.name;
	}

	public String getDisplayName() {
		if (this.displayName != null) return this.displayName;
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
}
