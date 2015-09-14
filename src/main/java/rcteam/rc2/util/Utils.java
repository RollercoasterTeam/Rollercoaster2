package rcteam.rc2.util;

import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.TRSRTransformation;

import javax.vecmath.Matrix4f;

public class Utils {
	//getFacingFromEntity taken from BlockPistonBase
	public static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entityIn, boolean opposite, boolean canReturnYAxis) {
		if (MathHelper.abs((float) entityIn.posX - (float) clickedBlock.getX()) < 2.0F && MathHelper.abs((float)entityIn.posZ - (float)clickedBlock.getZ()) < 2.0F) {
			double d0 = entityIn.posY + (double)entityIn.getEyeHeight();
			if (canReturnYAxis) {
				if (d0 - (double) clickedBlock.getY() > 2.0D) return EnumFacing.UP;
				if ((double) clickedBlock.getY() - d0 > 0.0D) return EnumFacing.DOWN;
			}
		}
		return opposite ? entityIn.getHorizontalFacing().getOpposite() : entityIn.getHorizontalFacing();
	}

	public static TRSRTransformation getTRSRFromFacing(EnumFacing facing) {
		return new TRSRTransformation(getMatrix(facing));
	}

	public static Matrix4f getMatrix(EnumFacing facing) {
		Matrix4f ret = new Matrix4f();
		switch (facing) {
			case DOWN: ret = ModelRotation.X90_Y0.getMatrix(); break;
			case UP: ret = ModelRotation.X270_Y0.getMatrix(); break;
			case NORTH: ret = ModelRotation.X0_Y0.getMatrix(); break;
			case SOUTH: ret = ModelRotation.X0_Y180.getMatrix(); break;
			case WEST: ret = ModelRotation.X0_Y270.getMatrix(); break;
			case EAST: ret = ModelRotation.X0_Y90.getMatrix(); break;
			default: ret.setIdentity();
		}
		return ret;
	}

	public static <V extends Vec3i> Vec3i negate(V from) {
		return new Vec3i(-from.getX(), -from.getY(), -from.getZ());
	}

	public static <V extends Vec3i> Vec3i add(V a, V b) {
		return new Vec3i(a.getX() + b.getX(), a.getY() + b.getY(), a.getZ() + b.getZ());
	}

	public static <V extends Vec3i> Vec3i sub(V a, V b) {
		return new Vec3i(a.getX() - b.getX(), a.getY() - b.getY(), a.getZ() - b.getZ());
	}

	public static <V extends Vec3i> Vec3i mul(V a, V b) {
		return new Vec3i(a.getX() * b.getX(), a.getY() * b.getY(), a.getZ() * b.getZ());
	}

	public static <V extends Vec3i> Vec3i div(V a, V b) {
		return new Vec3i(a.getX() / b.getX(), a.getY() / b.getY(), a.getZ() / b.getZ());
	}
}
