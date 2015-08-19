package rcteam.rc2.rollercoaster;

import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.StringUtils;

import javax.sound.midi.Track;
import javax.vecmath.Vector3f;

public class TrackPiece implements Comparable {
	private String name = "default_name";
	private String displayName = "Default Name";
	private Vector3f size = new Vector3f(1, 1, 1);
	private CategoryEnum categoryEnum;

	public TrackPiece(String name, Vector3f size, CategoryEnum categoryEnum) {
		this.name = name;
		this.displayName = this.getDisplayName();
		this.size = size;
		this.categoryEnum = categoryEnum;
	}

	public String getName() {
		return this.name;
	}

	public String getDisplayName() {
//		if (this.displayName != null) return this.displayName;
		String[] split = this.name.split("_");
//		String ret = this.name.replaceAll("_", " ");
		String ret = "";
		for (String s : split) {
			ret += StringUtils.capitalize(s) + " ";
		}
		ret = ret.substring(0, ret.length() - 1);
//		StringUtils.capitalize(ret);
		this.displayName = ret;
		return ret;
	}

	public Vector3f getSize() {
		return this.size;
	}

	public CategoryEnum getCategory() {
		return this.categoryEnum;
	}

	public NBTTagCompound writeToNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setString("name", this.name);
		compound.setFloat("size_x", this.size.x);
		compound.setFloat("size_y", this.size.y);
		compound.setFloat("size_z", this.size.z);
		compound.setInteger("category", this.categoryEnum.ordinal());
		return compound;
	}

	public static TrackPiece readFromNBT(NBTTagCompound compound) {
		String name = compound.getString("name");
		Vector3f size = new Vector3f(compound.getFloat("size_x"), compound.getFloat("size_y"), compound.getFloat("size_z"));
		CategoryEnum categoryEnum = CategoryEnum.values()[compound.getInteger("category")];
		return new TrackPiece(name, size, categoryEnum);
	}

	@Override
	public int compareTo(Object o) {
		if (!(o instanceof TrackPiece)) return 1;
		TrackPiece input = (TrackPiece) o;
		int catComp = input.getCategory().compareTo(this.categoryEnum);
		if (catComp == 0) {
			Vector3f inputVec = input.getSize();
			float inVolume = inputVec.x * inputVec.y * inputVec.z;
			float myVolume = this.size.x * this.size.y * this.size.z;
			if (inVolume == myVolume) {
				int nameComp = input.getName().compareTo(this.name);
				if (nameComp == 0) {
					int displayNameComp = input.getDisplayName().compareTo(this.getDisplayName());
					return displayNameComp;
				}
				return nameComp;
			} else if (inVolume > myVolume) return -1;
			else return 1;
		} else return catComp;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TrackPiece piece = (TrackPiece) o;

		if (name != null ? !name.equals(piece.name) : piece.name != null) return false;
		if (displayName != null ? !displayName.equals(piece.displayName) : piece.displayName != null) return false;
		if (size != null ? !size.equals(piece.size) : piece.size != null) return false;
		return categoryEnum == piece.categoryEnum;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
		result = 31 * result + (size != null ? size.hashCode() : 0);
		result = 31 * result + (categoryEnum != null ? categoryEnum.hashCode() : 0);
		return result;
	}
}
