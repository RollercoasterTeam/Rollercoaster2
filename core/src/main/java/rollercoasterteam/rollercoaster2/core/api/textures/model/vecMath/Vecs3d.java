package rollercoasterteam.rollercoaster2.core.api.textures.model.vecMath;

import rollercoasterteam.rollercoaster2.core.BlockPosition;
import rollercoasterteam.rollercoaster2.core.api.world.RCWorld;

public class Vecs3d {
	protected double x, y, z;
	protected RCWorld w = null;

	public Vecs3d(double x, double y, double z)
	{

		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vecs3d(double x, double y, double z, RCWorld w)
	{

		this(x, y, z);
		this.w = w;
	}


	public Vecs3d(BlockPosition vec, RCWorld w)
	{

		this(vec.getX(), vec.getY(), vec.getZ());
		this.w = w;
	}

	public boolean hasWorld()
	{

		return w != null;
	}

	public Vecs3d add(double x, double y, double z)
	{

		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}


	public Vecs3d add(Vecs3d vec)
	{

		return add(vec.x, vec.y, vec.z);
	}

	public Vecs3d sub(double x, double y, double z)
	{

		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}


	public Vecs3d sub(Vecs3d vec)
	{

		return sub(vec.x, vec.y, vec.z);
	}

	public Vecs3d mul(double x, double y, double z)
	{

		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}

	public Vecs3d mul(double multiplier)
	{

		return mul(multiplier, multiplier, multiplier);
	}

	public Vecs3d multiply(Vecs3d v)
	{

		return mul(v.getX(), v.getY(), v.getZ());
	}

	public Vecs3d div(double x, double y, double z)
	{

		this.x /= x;
		this.y /= y;
		this.z /= z;
		return this;
	}

	public Vecs3d div(double multiplier)
	{

		return div(multiplier, multiplier, multiplier);
	}

	public double length()
	{

		return Math.sqrt(x * x + y * y + z * z);
	}

	public Vecs3d normalize()
	{

		Vecs3d v = clone();

		double len = length();

		if (len == 0)
			return v;

		v.x /= len;
		v.y /= len;
		v.z /= len;

		return v;
	}

	public Vecs3d abs()
	{

		return new Vecs3d(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	public double dot(Vecs3d v)
	{

		return x * v.getX() + y * v.getY() + z * v.getZ();
	}

	public Vecs3d cross(Vecs3d v)
	{

		return new Vecs3d(y * v.getZ() - z * v.getY(), x * v.getZ() - z
				* v.getX(), x * v.getY() - y * v.getX());
	}

	public Vecs3d getRelative(double x, double y, double z)
	{

		return clone().add(x, y, z);
	}


	public boolean isZero()
	{

		return x == 0 && y == 0 && z == 0;
	}

	@Override
	public Vecs3d clone()
	{

		return new Vecs3d(x, y, z, w);
	}

	public RCWorld getWorld()
	{

		return w;
	}

	public Vecs3d setWorld(RCWorld world)
	{

		w = world;

		return this;
	}

	public double getX()
	{

		return x;
	}

	public double getY()
	{

		return y;
	}

	public double getZ()
	{

		return z;
	}

	public int getBlockX()
	{

		return (int) Math.floor(x);
	}

	public int getBlockY()
	{

		return (int) Math.floor(y);
	}

	public int getBlockZ()
	{

		return (int) Math.floor(z);
	}

	public double distanceTo(Vecs3d vec)
	{

		return distanceTo(vec.x, vec.y, vec.z);
	}

	public double distanceTo(double x, double y, double z)
	{

		double dx = x - this.x;
		double dy = y - this.y;
		double dz = z - this.z;
		return dx * dx + dy * dy + dz * dz;
	}

	public void setX(double x)
	{

		this.x = x;
	}

	public void setY(double y)
	{

		this.y = y;
	}

	public void setZ(double z)
	{

		this.z = z;
	}

	@Override
	public boolean equals(Object obj)
	{

		if (obj instanceof Vecs3d)
		{
			Vecs3d vec = (Vecs3d) obj;
			return vec.w == w && vec.x == x && vec.y == y && vec.z == z;
		}
		return false;
	}

	@Override
	public int hashCode()
	{

		return new Double(x).hashCode() + new Double(y).hashCode() << 8 + new Double(
				z).hashCode() << 16;
	}
}