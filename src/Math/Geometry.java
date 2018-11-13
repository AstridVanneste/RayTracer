package Math;

import RayTracer.Factories.VectorFactory;

import java.security.InvalidParameterException;

public class Geometry
{
	public static double angle(Vector vec1, Vector vec2)
	{
		double dot = Vector.dotProduct(vec1, vec2);
		double mag1 = vec1.length();
		double mag2 = vec2.length();

		double cosAngle = dot / (mag1 * mag2);

		return Math.acos(cosAngle);
	}

	public static double distance(Vector vec1, Vector vec2) throws InvalidParameterException
	{
		if(!VectorFactory.isPoint(vec1))
		{
			throw new InvalidParameterException("First vector parameter is not a point");
		}
		if(!VectorFactory.isPoint(vec2))
		{
			throw new InvalidParameterException("Second vector parameter is not a point");
		}

		double x, y, z;

		x = vec1.get(0) - vec2.get(0);
		y = vec1.get(1) - vec2.get(1);
		z = vec1.get(2) - vec2.get(2);

		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
}
