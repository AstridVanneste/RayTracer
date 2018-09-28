package Math;

import java.security.InvalidParameterException;

public class Vector
{
	private int size;
	private double[] elements;

	public Vector(int size)
	{
		this.size = size;
		this.elements = new double[size];
	}

	public Vector(double[] elements)
	{
		this.elements = elements;
	}

	public void set(int index, double element) throws IndexOutOfBoundsException
	{
		if(index < this.size())
		{
			this.elements[index] = element;
		}
		else
		{
			throw new IndexOutOfBoundsException("Cannot set element " + index + " of Vector of size " + this.size());
		}

	}

	public double get(int index) throws IndexOutOfBoundsException
	{
		if(index < this.size())
		{
			return this.elements[index];
		}
		else
		{
			throw new IndexOutOfBoundsException("Cannot get element " + index + " of Vector of size " + this.size());
		}
	}

	public int size()
	{
		return this.size;
	}

	public Vector add(Vector vec) throws InvalidParameterException
	{
		Vector result = new Vector(this.size());

		if(this.size() == vec.size())
		{
			for(int i = 0; i < this.size(); i++)
			{
				result.set(i, this.get(i) + vec.get(i));
			}
			return result;
		}
		else
		{
			throw new InvalidParameterException("Cannot add Vectors of different sizes!");
		}
	}

	public Vector multiply(double s)
	{
		Vector result = new Vector(this.size());
		for(int i = 0; i < this.size(); i++)
		{
			result.set(i, this.get(i) * s);
		}
		return result;
	}

	public double dotProduct(Vector vec) throws InvalidParameterException
	{
		double result = 0;
		if(this.size() == vec.size())
		{
			for(int i = 0; i < this.size(); i++)
			{
				result += this.get(i) * vec.get(i);
			}
			return result;
		}
		else
		{
			throw new InvalidParameterException("Cannot perform dot product on two Vectors with different sizes!");
		}
	}

	public Vector crossProduct(Vector vec)
	{
		Vector result = new Vector(this.size());

		if(this.size() == vec.size())
		{
			// ONLY IMPLEMENT 3D CROSS PRODUCT
			if(this.size() == 3)
			{
				for(int i = 0; i < this.size(); i++)
				{
					double element = (this.get((i + 1)%3) * vec.get((i + 2) % 3)) - (this.get((i + 2) % 3) * vec.get((i + 1) % 3));
					result.set(i, element);
				}
				return result;
			}
			else
			{
				throw new InvalidParameterException("Cross product only implemented for 3D vectors!");
			}
		}
		else
		{
			throw new InvalidParameterException("Cannot perform cross product on Vectors with different sizes");
		}
	}
}
