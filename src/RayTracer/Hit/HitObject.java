package RayTracer.Hit;

import Math.Vector;
import RayTracer.Factories.VectorFactory;
import RayTracer.RayTracer;
import RayTracer.Scene.Objects.Entity;
import Util.Color;

import java.security.InvalidParameterException;

public class HitObject
{
	private Entity object;
	private Vector hitpoint;
	private double distance;
	private Color color;
	private Vector normal;
	private int traceLevel;

	public HitObject(Entity object, Vector hitpoint, double distance, Color color, Vector normal, int traceLevel) throws InvalidParameterException
	{
		if(!VectorFactory.isPoint(hitpoint))
		{
			throw new InvalidParameterException("Hitpoint parameter is not a point");
		}
		this.object = object;
		this.hitpoint = new Vector(hitpoint);
		this.distance = distance;
		this.color = color;
		this.normal = new Vector(normal);
		this.normal.normalize();
		this.traceLevel = traceLevel;
	}

	public HitObject(HitObject hit)
	{
		this(hit.object, hit.hitpoint, hit.distance, hit.color, hit.normal, hit.traceLevel);
	}

	public Vector getHitpoint()
	{
		return this.hitpoint;
	}

	public void setHitpoint(Vector hitpoint) throws InvalidParameterException
	{
		if(!VectorFactory.isPoint(hitpoint))
		{
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

	public int getTraceLevel()
	{
		return this.traceLevel;
	}

	public Entity getObject()
	{
		return this.object;
	}
}
