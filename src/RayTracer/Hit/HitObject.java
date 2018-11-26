package RayTracer.Hit;

import Math.Vector;
import RayTracer.Factories.VectorFactory;
import RayTracer.RayTracer;
import Util.Color;

import java.security.InvalidParameterException;

public class HitObject
{
	private Vector hitpoint;
	private double distance;
	private Color color;
	private Vector normal;

	public HitObject(Vector hitpoint, double distance, Color color, Vector normal)
	{
		this.hitpoint = hitpoint;
		this.distance = distance;
		this.color = color;
		this.normal = normal;
	}

	public Vector getHitpoint()
	{
		return this.hitpoint;
	}

	public void setHitpoint(Vector hitpoint) throws InvalidParameterException
	{
		if(!VectorFactory.isPoint(hitpoint))
		{
			System.out.println(hitpoint);
			throw new InvalidParameterException("Hitpoint parameter is not a point");
		}
		this.hitpoint = hitpoint;
	}

	public double getDistance()
	{
		return this.distance;
	}

	public Color getColor()
	{
		return this.color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public Vector getNormal()
	{
		return this.normal;
	}
}
