package RayTracer.Factories;

import Math.Matrix;
import Math.Vector;

public class TransformationFactory
{
	public static Matrix translation(double x, double y, double z)
	{
		Matrix matrix = Matrix.identityMatrix(4);

		matrix.set(3, 0, x);
		matrix.set(3, 1, y);
		matrix.set(3, 2, z);

		return matrix;
	}

	public static Matrix scaling(double x, double y, double z)
	{
		Matrix matrix = new Matrix(4, 4);

		matrix.set(0, 0, x);
		matrix.set(1, 1, y);
		matrix.set(2, 2, z);

		return matrix;
	}

	public static Matrix rotation()
	{
		Matrix matrix = new Matrix(4, 4);

		return matrix;
	}
}
