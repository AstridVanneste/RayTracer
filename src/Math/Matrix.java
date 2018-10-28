package Math;

import java.security.InvalidParameterException;

public class Matrix
{
	private int m;
	private int n;
	private double[][] elements;

	public Matrix(int m, int n)
	{
		this.m = m;
		this.n = n;
		this.elements = new double[m][n];
	}

	public Matrix(double[][] elements)
	{
		this.elements = elements;
	}

	public Matrix(Vector vector)
	{
		this.m = 1;
		this.n = vector.size();

		this.elements = new double[this.m][this.n];
		this.elements[0] = vector.toArray();
	}

	public int height()
	{
		return this.m;
	}

	public int width()
	{
		return this.n;
	}

	public double get(int m, int n)
	{
		return this.elements[m][n];
	}

	public void set(int m, int n, double element)
	{
		this.elements[m][n] = element;
	}

	public Vector toVector(int i)
	{
		return new Vector(this.elements[i]);
	}

	public static Matrix add(Matrix m1, Matrix m2) throws InvalidParameterException
	{
		Matrix result = new Matrix(m1.height(), m1.width());

		if(m1.equalSize(m2))
		{
			for(int i = 0; i < m1.height(); i++)
			{
				for(int j = 0; j < m1.width(); j++)
				{
					double element = m1.get(i, j) + m2.get(i, j);
					result.set(i, j, element);
				}
			}

			return result;
		}
		else
		{
			throw new InvalidParameterException("Cannot add Matrices with different sizes!");
		}
	}

	public static Matrix subtract(Matrix m1, Matrix m2) throws InvalidParameterException
	{
		Matrix result = new Matrix(m1.height(), m1.width());

		if(m1.equalSize(m2))
		{
			for(int i = 0; i < m1.height(); i++)
			{
				for(int j = 0; j < m1.width(); j++)
				{
					double element = m1.get(i, j) - m2.get(i, j);
					result.set(i, j, element);
				}
			}
			return result;
		}
		else
		{
			throw new InvalidParameterException("Cannot subtract Matrices with different sizes!");
		}
	}

	public static Matrix multiply(Matrix m, double s)
	{
		Matrix result = new Matrix(m.height(), m.width());

		for(int i = 0; i < m.height(); i++)
		{
			for(int j = 0; j < m.width(); j++)
			{
				result.set(i, j, (m.get(i, j) * s));
			}
		}
		return result;
	}

	public static Matrix multiply(Matrix m1, Matrix m2) throws InvalidParameterException
	{
		Matrix result = new Matrix(m1.height(), m2.width());

		if(m1.width() == m2.height())
		{
			for(int i = 0; i < m1.height(); i++)
			{
				for(int j = 0; j < m2.width(); j++)
				{
					double sum = 0;
					for(int k = 0; k < m1.width(); k++)
					{
						sum += m1.get(i, k) * m2.get(k, j);
					}

					result.set(i, j, sum);
				}
			}
			return result;
		}
		else
		{
			throw new InvalidParameterException("Matrix resolutions do not match");
		}
	}

	public static Matrix identityMatrix(int m)
	{
		Matrix identityMatrix = new Matrix(m, m);

		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < m; j++)
			{
				if(j == i)
				{
					identityMatrix.set(i, j, 1);
				}
				else
				{
					identityMatrix.set(i, j, 0);
				}
			}
		}
		return identityMatrix;
	}

	public boolean equalSize(Matrix matrix)
	{
		if(this.height() == matrix.height())
		{
			return this.width() == matrix.width();
		}
		return false;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		for(int i = 0; i < this.height(); i++)
		{
			for(int j = 0; j < this.width(); j++)
			{
				builder.append(this.get(i, j));
				builder.append("\t");
			}
			builder.append("\n");
		}

		return builder.toString();
	}
}
