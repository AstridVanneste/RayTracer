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

	public static Vector add(Vector vec1, Vector vec2) throws InvalidParameterException
	{
		Vector result = new Vector(vec1.size());

		if(vec1.size() == vec2.size())
		{
			for(int i = 0; i < vec1.size(); i++)
			{
				result.set(i, vec1.get(i) + vec2.get(i));
			}
			return result;
		}
		else
		{
			throw new InvalidParameterException("Cannot add Vectors of different sizes!");
		}
	}

	public static Vector subtract(Vector vec1, Vector vec2) throws InvalidParameterException
	{
		Vector result = new Vector(vec1.size);

		if(vec1.size() == vec2.size())
		{
			for(int i = 0; i < vec1.size(); i++)
			{
				result.set(i, vec1.get(i) - vec2.get(i));
			}
			return result;
		}
		else
		{
			throw new InvalidParameterException("Cannot subtract Vectors of different sizes!");
		}
	}

	public static Vector multiply(Vector vec, double s)
	{
		Vector result = new Vector(vec.size());
		for(int i = 0; i < vec.size(); i++)
		{
			result.set(i, vec.get(i) * s);
		}
		return result;
	}

	public static Vector divide(Vector vec, double s)
	{
		s = 1/s;

		return Vector.multiply(vec, s);
	}

	public static double dotProduct(Vector vec1, Vector vec2) throws InvalidParameterException
	{
		double result = 0;
		if(vec1.size() == vec2.size())
		{
			for(int i = 0; i < vec1.size(); i++)
			{
				result += vec1.get(i) * vec2.get(i);
			}
			return result;
		}
		else
		{
			throw new InvalidParameterException("Cannot perform dot product on two Vectors with different sizes!");
		}
	}

	public static Vector crossProduct(Vector vec1, Vector vec2)
	{
		Vector result = new Vector(vec1.size());

		if(vec1.size() == vec2.size())
		{
			// TODO implement for 4D vectors (ignoring the last element)
			// ONLY IMPLEMENT 3D CROSS PRODUCT
			if(vec1.size() == 3)
			{
				for(int i = 0; i < vec1.size(); i++)
				{
					double element = (vec1.get((i + 1)%3) * vec2.get((i + 2) % 3)) - (vec1.get((i + 2) % 3) * vec2.get((i + 1) % 3));
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

	public static boolean isPoint(Vector v)
	{
		if(v.size != 4)
		{
			return false;
		}
		if(v.get(3) != 1)
		{
			return false;
		}
		return true;
	}

	public static boolean isVector(Vector v)
	{
		if(v.size != 4)
		{
			return false;
		}
		if(v.get(3) != 0)
		{
			return false;
		}
		return true;
	}

	public static Vector createPoint(double x, double y, double z)
	{
		Vector point = new Vector(4);
		point.set(0, x);
		point.set(1, y);
		point.set(2, z);
		point.set(3, 1);

		return point;
	}

	// TODO create point/vector factory shit that also checks if it's points or vectors
}
