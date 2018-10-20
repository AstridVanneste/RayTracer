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
			this.normal.normalize();
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
	}

	public Plane(Vector normal, Vector point, Color color)
	{
		this(normal, point);
		this.setColor(color);
	}

	public Vector getNormal()
	{
		return this.normal;
	}

	public Vector getPoint()
	{
		return this.point;
	}

	@Override
	public HitObject hit(Ray r)
	{
		double numerator = Vector.dotProduct(this.normal, this.point) - Vector.dotProduct(this.normal, r.getEye());
		double denominator = Vector.dotProduct(this.normal, r.getDir());

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
