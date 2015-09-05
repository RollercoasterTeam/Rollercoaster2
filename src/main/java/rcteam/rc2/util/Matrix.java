package rcteam.rc2.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import org.lwjgl.util.vector.Matrix2f;
import org.lwjgl.util.vector.Vector2f;

import java.util.List;

public class Matrix {
	private int columns = 2;
	private int rows = 2;
	public int[][] matrix;

	public Matrix() {
		this(2);
	}

	public Matrix(int size) {
		this(size, size);
	}

	public Matrix(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		this.matrix = new int[this.rows][this.columns];
		setIdentity();
	}

	public static Matrix copy(Matrix matrix) {
		Matrix ret = new Matrix(matrix.rows, matrix.columns);
		ret.matrix = matrix.matrix;
		return ret;
	}

	public static Matrix getMatrixFromVectors(List<Vector2f> vectors) {
		Matrix matrix = new Matrix(2, vectors.size());
		for (int v = 0; v < vectors.size(); v++) {
			matrix.matrix[0][v] = (int) vectors.get(v).getX();
			matrix.matrix[1][v] = (int) vectors.get(v).getY();
		}
		return matrix;
	}

	public Matrix setIdentity() {
		return setIdentity(this);
	}

	public static Matrix setIdentity(Matrix matrix) {
		for (int r = 0; r < matrix.matrix.length; r++) {
			for (int c = 0; c < matrix.matrix[r].length; c++) {
				matrix.matrix[r][c] = r == c ? 1 : 0;
			}
		}
		return matrix;
	}

	public void setSize(int size) {
		if (size >= 2) {
			this.rows = size;
			this.columns = size;
			this.matrix = new int[this.rows][this.columns];
		}
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getRows() {
		return this.rows;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getColumns() {
		return this.columns;
	}

	public void setZero() {
		for (int r = 0; r < this.rows; r++) {
			for (int c = 0; c < this.columns; c++) {
				this.matrix[r][c] = 0;
			}
		}
	}

	public static Matrix rotate(Matrix start, int times, boolean counterClockwise, int minCol, int minRow) {
		Matrix matrix = Matrix.copy(start);
		Matrix rotation = new Matrix(2, 1);
//		rotation.setZero();
		if (matrix.rows > 2) return matrix;
		switch (times) {
			default:
			case 0: return matrix;
			case 1:
				rotation.matrix[0][0] = 0;
				rotation.matrix[1][0] = counterClockwise ? 1 : -1;
//				rotation.matrix[0][0] = counterClockwise ? -1 : 1;
//				rotation.matrix[1][0] = counterClockwise ? 1 : -1;
				break;
			case 2:
				rotation.matrix[0][0] = -1;
//				rotation.matrix[0][0] = -1;
//				rotation.matrix[1][0] = -1;
				break;
			case 3:
				rotation.matrix[0][0] = 0;
				rotation.matrix[1][0] = counterClockwise ? -1 : 1;
//				rotation.matrix[0][0] = counterClockwise ? 1 : -1;
//				rotation.matrix[1][0] = counterClockwise ? -1 : 1;
				break;
		}
//		int minCol = 0;
//		int minRow = 0;
//		for (int r = 0; r < start.rows; r++) {
			for (int c = 0; c < start.columns; c++) {
//				matrix.matrix[r][c] = (rotation.matrix[r][0] * start.matrix[0][c]) + (rotation.matrix[r][1] * start.matrix[1][c]);
				int column = (rotation.matrix[0][0] * start.matrix[0][c]) - (rotation.matrix[1][0] * start.matrix[1][c]);
				int row = (rotation.matrix[1][0] * start.matrix[0][c]) + (rotation.matrix[0][0] * start.matrix[1][c]);
				minCol = Math.min(minCol, column);
				minRow = Math.min(minRow, row);
				matrix.matrix[0][c] = column;
				matrix.matrix[1][c] = row;
			}
//		}
		for (int c = 0; c < matrix.columns; c++) {
			matrix.matrix[0][c] += Math.abs(minCol);
			matrix.matrix[1][c] += Math.abs(minRow);
		}
		return matrix;
	}
}
