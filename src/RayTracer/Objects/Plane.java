package RayTracer.Objects;

import RayTracer.Factories.VectorFactory;
import RayTracer.Hit.HitObject;
import RayTracer.Hit.Hittable;
import RayTracer.Hit.Ray;
import Math.Vector;

import java.awt.*;
import java.security.InvalidParameterException;

public class Plane extends Object
{
	protected Vector normal;
	protected Vector point;

	private double dot;

	protected Plane()
	{
		super();
	}

	public Plane(Vector normal, Vector point) throws InvalidParameterException
	{
		super();
		if(VectorFactory.isVector(normal))
		{
			this.normal = normal;
			this.normal.normalize(false);
		}
		else
		{
			throw new InvalidParameterException("Normal parameter is not vector");
		}
		if(VectorFactory.isPoint(point))
		{
			this.point = point;
		}
		else
		{
			throw new InvalidParameterException("Point parameter is not point");
		}

		this.dot = Vector.dotProduct(normal, point);


	}

	public Plane(Vector normal, Vector point, Color color)
	{
		this(normal, point);
		this.setColor(color);
	}

	@Override
	protected HitObject internalHit(Ray r)
	{
		double numerator = this.dot - Vector.dotProduct(this.normal, r.getEye());
		double denominator = Vector.dotProduct(this.normal, r.getDir());

		if(Double.compare(denominator, 0.0) == 0)
		{
			return null;			// if dot product of plane normal and ray direction is 0 then you are looking straight at a flat plane
		}

		double k = numerator/denominator;

		if(Double.compare(k, 0.0) > 0)
		{
			Vector hitpoint = r.getPoint(k);

			return new HitObject(hitpoint, k, this.getColor());
		}
		else
		{
			return null;
		}
	}

	public boolean isInside(Vector p)
	{
		if(VectorFactory.isPoint(p))
		{
			double product = Vector.dotProduct(Vector.subtract(p, this.point), this.normal);

			if(Double.compare(product, 0.0) == 0)
			{
				return true;
			}
		}

		return false;
	}
}
