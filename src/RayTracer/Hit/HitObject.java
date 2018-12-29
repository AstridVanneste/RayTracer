package RayTracer.Hit;

import Math.Vector;
import RayTracer.Factories.VectorFactory;
import RayTracer.Transformation;
import RayTracer.Scene.Objects.Entity;
import Util.Color;

import java.security.InvalidParameterException;

public class HitObject
{
	private Entity object;
	private Vector hitpoint;
	private Color color;
	private Vector normal;
	private double k;
	private int traceLevel;

	public HitObject(Entity object, Vector hitpoint, Color color, Vector normal, double k, int traceLevel) throws InvalidParameterException
	{
		if(!VectorFactory.isPoint(hitpoint))
		{
			throw new InvalidParameterException("Hitpoint parameter is not a point");
		}
		this.object = object;
		this.hitpoint = new Vector(hitpoint);
		this.color = color;
		this.normal = new Vector(normal);
		this.normal.normalize();
		this.k = k;
		this.traceLevel = traceLevel;
	}

	public HitObject(HitObject hit)
	{
		this(hit.object, hit.hitpoint, hit.color, hit.normal, hit.k, hit.traceLevel);
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

	public void transform(Transformation transformation)
	{
		this.hitpoint = transformation.transform(this.hitpoint);
		this.normal = transformation.transform(this.normal);
		this.normal.normalize();
	}


	public void setObject(Entity object)
	{
		this.object = object;
	}

	public double getK()
	{
		return this.k;
	}
}
