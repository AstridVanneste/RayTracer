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
		if(Vector.isVector(normal))
		{
			this.normal = normal;
		}
		else
		{
			throw new InvalidParameterException("Normal parameter is not vector");
		}
		if(Vector.isPoint(point))
		{
			this.point = point;
		}
		else
		{
			throw new InvalidParameterException("Point parameter is not point");
		}
	}

	@Override
	public HitObject hit(Ray r)
	{
		double numerator = Vector.dotProduct(this.normal, this.point) - Vector.dotProduct(this.normal, r.getEye());
		double denominator = Vector.dotProduct(this.normal, r.getDir());

		double k = numerator/denominator;

		if(k > 0)
		{
			Vector hitpoint = r.getPoint(k);
			Vector color = new Vector(4);

			return new HitObject(hitpoint, k, color);
		}
		else
		{
			return null;
		}
	}

	public boolean isOn(Vector p)
	{
		if(Vector.isPoint(p))
		{
			double product = Vector.dotProduct(Vector.subtract(p, this.point), this.normal);

			if(product == 0)
			{
				return true;
			}
		}

		return false;
	}
}
