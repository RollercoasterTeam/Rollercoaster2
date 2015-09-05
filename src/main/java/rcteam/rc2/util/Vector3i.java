package rcteam.rc2.util;

import com.google.common.base.Objects;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;

public class Vector3i implements Comparable {
	public static final Vector3i NULL_VECTOR = new Vector3i(0, 0, 0);
	public int x, y, z;

	public Vector3i(Vec3i from) {
		this(from.getX(), from.getY(), from.getZ());
	}

	public Vector3i(BlockPos pos) {
		this(pos.getX(), pos.getY(), pos.getZ());
	}

	public Vector3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3i() {
		this(0, 0, 0);
	}

	public Vec3i toVec3i() {
		return new Vec3i(this.x, this.y, this.z);
	}

	public int getSize() {
		return this.x * this.y * this.z;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("x", this.x).add("y", this.y).add("z", this.z).toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Vector3i vector3i = (Vector3i) o;

		if (x != vector3i.x) return false;
		if (y != vector3i.y) return false;
		return z == vector3i.z;
	}

	@Override
	public int hashCode() {
		int result = x;
		result = 31 * result + y;
		result = 31 * result + z;
		return result;
	}

	public int compareTo(Vector3i vector3i) {
		return this.y == vector3i.y ? (this.z == vector3i.z ? this.x - vector3i.x : this.z - vector3i.z) : this.y - vector3i.y;
	}

	@Override
	public int compareTo(Object o) {
		return this.compareTo((Vector3i) o);
	}
}
