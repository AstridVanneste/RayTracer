package RayTracer.Objects;

import RayTracer.HitObject;
import RayTracer.Ray;
import Math.Vector;

import java.security.InvalidParameterException;

public class Plane extends Object
{
	private Vector normal;
	private Vector point;


	public Plane(Vector normal, Vector point) throws InvalidParameterException
	{
		if(normal.size() == 4)
		{
			this.normal = normal;
		}
		else
		{
			throw new InvalidParameterException("Normal vector size should be 4 but is " + normal.size());
		}
		if(point.size() == 4)
		{
			this.point = point;
		}
		else
		{
			throw new InvalidParameterException("Point vector size should be 4 but is " + point.size());
		}
	}

	@Override
	public HitObject hit(Ray r)
	{
		double numerator = Vector.dotProduct(this.normal, this.point) - Vector.dotProduct(this.normal, r.getEye());
		double denominator = Vector.dotProduct(this.normal, r.getDir());

		double k = numerator/denominator;

		return null;
	}
}
