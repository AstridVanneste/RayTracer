package RayTracer;

import Math.*;
import javafx.util.Pair;

import java.security.InvalidParameterException;

public class Transformation
{
	private Matrix forward;
	private Matrix inverse;

	public Transformation(Matrix forward, Matrix inverse) throws InvalidParameterException
	{
		if(forward.height() != 4 || forward.width() != 4)
		{
			throw new InvalidParameterException("Forward transformation matrix dimensions should be 4x4 but are " + forward.height() + "x" + forward.width());
		}

		if(inverse.height() != 4 || inverse.width() != 4)
		{
			throw new InvalidParameterException("Forward transformation matrix dimensions should be 4x4 but are " + inverse.height() + "x" + inverse.width());
		}

		this.forward = forward;
		this.inverse = inverse;
	}

	public Transformation add(Matrix forward, Matrix inverse) throws InvalidParameterException
	{
		if(forward.height() != 4 || forward.width() != 4)
		{
			throw new InvalidParameterException("Forward transformation matrix dimensions should be 4x4 but are " + forward.height() + "x" + forward.width());
		}

		if(inverse.height() != 4 || inverse.width() != 4)
		{
			throw new InvalidParameterException("Forward transformation matrix dimensions should be 4x4 but are " + inverse.height() + "x" + inverse.width());
		}

		this.forward = Matrix.multiply(this.forward, forward);
		this.inverse = Matrix.multiply(this.inverse, inverse);

		return this;
	}

	public Transformation add(Pair<Matrix, Matrix> transformation)
	{
		return this.add(transformation.getKey(), transformation.getValue());
	}

	public Vector transform(Vector vector)
	{
		Matrix matrix = new Matrix(vector);

		matrix = Matrix.multiply(matrix, this.forward);

		return matrix.toVector(0);
	}

	public Vector inverse(Vector vector)
	{
		Matrix matrix = new Matrix(vector);

		matrix = Matrix.multiply(matrix, this.inverse);

		return matrix.toVector(0);
	}
}
