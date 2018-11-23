package Math;

import RayTracer.Factories.VectorFactory;

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
		this.size = elements.length;
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

	public double[] toArray()
	{
		return this.elements;
	}

	public void normalize(boolean point)
	{
		if(point)
		{
			this.elements[this.elements.length - 1] = 1;
		}
		else
		{
			this.elements = Vector.divide(this, this.length()).elements;
			this.elements[this.elements.length - 1] = 0;
		}
	}

	public double length()
	{
		double squareSum = 0;
		for(int i = 0; i < this.elements.length; i++)
		{
			squareSum += Math.pow(this.elements[i], 2);
		}

		return Math.sqrt(squareSum);
	}

	public static Vector add(Vector vec1, Vector vec2) throws InvalidParameterException
	{
		if(vec1 == null)
		{
			throw new IllegalArgumentException("Parameter 1 cannot be null");
		}
		if(vec2 == null)
		{
			throw new IllegalArgumentException("Parameter 2 cannot be null");
		}

		Vector result = new Vector(vec1.size());

		if(vec1.size() == vec2.size())
		{
			for(int i = 0; i < vec1.size(); i++)
			{
				result.set(i, vec1.get(i) + vec2.get(i));
			}
			result.normalize(VectorFactory.isPoint(vec1) || VectorFactory.isPoint(vec2));
			return result;
		}
		else
		{
			throw new InvalidParameterException("Cannot add Vectors of different sizes!");
		}
	}

	public static Vector subtract(Vector vec1, Vector vec2) throws InvalidParameterException
	{
		if(vec1 == null)
		{
			throw new IllegalArgumentException("Parameter 1 cannot be null");
		}
		if(vec2 == null)
		{
			throw new IllegalArgumentException("Parameter 2 cannot be null");
		}
		Vector result = new Vector(vec1.size);

		if(vec1.size() == vec2.size())
		{
			for(int i = 0; i < vec1.size(); i++)
			{
				result.set(i, vec1.get(i) - vec2.get(i));
			}
			result.normalize(Utils.XOR(VectorFactory.isPoint(vec1), VectorFactory.isPoint(vec2)));

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
		//result.normalize(VectorFactory.isPoint(vec));
		return result;
	}

	public static Vector divide(Vector vec, double s)
	{
		return Vector.multiply(vec, 1/s);
	}

	public static double dotProduct(Vector vec1, Vector vec2) throws InvalidParameterException
	{
		double result = 0;
		if(vec1.size() == vec2.size())
		{
			for(int i = 0; i < vec1.size() - 1; i++)
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
			// ONLY IMPLEMENT 3D CROSS PRODUCT
			if(vec1.size() == 3 || vec1.size() == 4)
			{
				for(int i = 0; i < 3; i++)
				{
					double element = (vec1.get((i + 1)%3) * vec2.get((i + 2) % 3)) - (vec1.get((i + 2) % 3) * vec2.get((i + 1) % 3));
					result.set(i, element);
				}
				result.normalize(Utils.XOR(VectorFactory.isPoint(vec1), VectorFactory.isPoint(vec2)));
				return result;
			}
			else
			{
				throw new InvalidParameterException("Cross product only implemented for 3D and 4D vectors!");
			}
		}
		else
		{
			throw new InvalidParameterException("Cannot perform cross product on Vectors with different sizes");
		}
	}

	public static Vector invert(Vector vec)
	{
		Vector inverted = new Vector(vec.size);

		for(int i = 0; i < vec.size; i++)
		{
			inverted.elements[i] = -vec.elements[i];
		}

		return inverted;
	}

	@Override
	public boolean equals(Object o)
	{
		if(o == this)
		{
			return true;
		}
		if(o instanceof Vector)
		{
			Vector v = (Vector) o;

			if(this.size == v.size)
			{
				for(int i = 0; i < this.size; i++)
				{
					if(this.elements[i] != v.elements[i])
					{
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		builder.append("(");

		for(int i = 0; i < this.size; i++)
		{
			builder.append(this.elements[i]);
			builder.append(" ");
		}
		builder.append(")");

		return builder.toString();
	}
}
