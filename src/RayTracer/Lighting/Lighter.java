package RayTracer.Lighting;

import Math.Vector;
import RayTracer.Factories.VectorFactory;

import java.security.InvalidParameterException;

public class Lighter
{
	private Vector normal;

	public Lighter(Vector normal)
	{
		if(!VectorFactory.isVector(normal))
		{
			throw new InvalidParameterException("Normal parameter is not a vector");
		}

		this.normal = normal;
	}


}
