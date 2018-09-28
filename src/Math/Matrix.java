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

	public Matrix add(Matrix matrix) throws InvalidParameterException
	{
		Matrix result = new Matrix(this.height(), this.width());

		if(this.equalSize(matrix))
		{
			for(int i = 0; i < this.height(); i++)
			{
				for(int j = 0; j < this.width(); j++)
				{
					double element = this.get(i, j) + matrix.get(i, j);
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

	public Matrix multiply(double s)
	{
		Matrix result = new Matrix(this.height(), this.width());

		for(int i = 0; i < this.height(); i++)
		{
			for(int j = 0; j < this.width(); j++)
			{
				result.set(i, j, this.get(i, j) * s);
			}
		}
		return result;
	}

	public Matrix multiply(Matrix m) throws InvalidParameterException
	{
		Matrix result = new Matrix(this.height(), m.width());

		if(this.width() == m.height())
		{
			for(int i = 0; i < this.height(); i++)
			{
				for(int j = 0; j < m.width(); j++)
				{
					double sum = 0;
					for(int k = 0; k < this.width(); k++)
					{
						sum += this.get(i, k) * m.get(k, j);
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

	public boolean equalSize(Matrix matrix)
	{
		if(this.height() == matrix.height())
		{
			if(this.width() == matrix.width())
			{
				return true;
			}
		}
		return false;
	}
}
