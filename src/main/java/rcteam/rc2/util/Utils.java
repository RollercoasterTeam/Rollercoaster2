package rcteam.rc2.util;

import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.TRSRTransformation;

import javax.vecmath.Matrix4f;

public class Utils {
	//getFacingFromEntity taken from BlockPistonBase
	public static EnumFacing getFacingFromEntity(World worldIn, BlockPos clickedBlock, EntityLivingBase entityIn) {
		if (MathHelper.abs((float) entityIn.posX - (float) clickedBlock.getX()) < 2.0F && MathHelper.abs((float)entityIn.posZ - (float)clickedBlock.getZ()) < 2.0F) {
			double d0 = entityIn.posY + (double)entityIn.getEyeHeight();

			if (d0 - (double)clickedBlock.getY() > 2.0D) {
				return EnumFacing.UP;
			}

			if ((double)clickedBlock.getY() - d0 > 0.0D) {
				return EnumFacing.DOWN;
			}
		}

		return entityIn.getHorizontalFacing().getOpposite();
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
}
