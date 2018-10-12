package RayTracer.Objects;

import RayTracer.Factories.VectorFactory;
import RayTracer.HitObject;
import RayTracer.Ray;
import Math.Vector;

import java.awt.*;
import java.security.InvalidParameterException;

public class Plane implements Hittable
{
	protected Vector normal;
	protected Vector point;

	protected Plane()
	{

	}

	public Plane(Vector normal, Vector point) throws InvalidParameterException
	{
		if(VectorFactory.isVector(normal))
		{
			this.normal = normal;
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

		if(Double.compare(k, 0) <= 0)
		{
			Vector hitpoint = r.getPoint(k);
			Color color = this.sampleColor(hitpoint);

			return new HitObject(hitpoint, k, color);
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

			if(product == 0)
			{
				return true;
			}
		}

		return false;
	}

	public Color sampleColor(Vector point)
	{
		float r = Math.abs((float)((point.get(0)) / 100) % 1);
		float g = Math.abs((float)((point.get(1)) / 100) % 1);
		float b = Math.abs((float)((point.get(2)) / 100) % 1);

		Color color = new Color(r, g, b);
		return Color.RED;
	}
}
