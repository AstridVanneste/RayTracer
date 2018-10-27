package RayTracer.Objects;

import RayTracer.Factories.VectorFactory;
import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import Math.Vector;

import java.awt.*;
import java.security.InvalidParameterException;

public class Sphere extends Object
{
	private Vector center;
	private double radius;

	public Sphere(Vector center, double radius) throws InvalidParameterException
	{
		super();

		if(!VectorFactory.isPoint(center))
		{
			throw new InvalidParameterException("Center parameter is not a point");
		}
		if(radius < 0)
		{
			throw new InvalidParameterException("Radius of sphere cannot be negative");
		}

		this.center = center;
		this.radius = radius;
	}

	@Override
	public HitObject hit(Ray r)
	{
		double A = Vector.dotProduct(r.getDir(), r.getDir());
		double B = Vector.dotProduct(r.getEye(), r.getDir());
		double C = Vector.dotProduct(r.getEye(), r.getEye()) - 1;

		double discriminant =  Math.pow(B, 2) - A * C;
		System.out.println("A = " + A);
		System.out.println("B = " + B);
		System.out.println("C = " + C);
		System.out.println("discriminant = " + discriminant);

		double k = 0;
		if(Double.compare(discriminant, 0) < 0)						// NO HITPOINT
		{
			System.out.println("NO HITPOINT");
			return null;
		}
		else if(Double.compare(discriminant, 0) == 0)				// 1 HITPOINT
		{
			System.out.println("1 HITPOINT");
			k = -B/A;
		}
		else if(Double.compare(discriminant, 0) > 0)				// 2 HITPOINTS
		{
			System.out.println("2 HITPOINTS");
			double k1 = (-B + Math.sqrt(discriminant))/(A);
			double k2 = (-B - Math.sqrt(discriminant))/(A);

			// hits behind eye
			boolean compareK1 = Double.compare(k1, 0) < 0;
			boolean compareK2 = Double.compare(k2, 0) < 0;
			if(compareK1 && compareK2)
			{
				return null;
			}
			else if(compareK1)
			{
				k = k2;
			}
			else if(compareK2)
			{
				k = k1;
			}
			else
			{
				k = Math.min(k1, k2);
			}
		}

		Vector hitpoint = r.getPoint(k);
		return new HitObject(hitpoint, k, Color.RED);
	}
}