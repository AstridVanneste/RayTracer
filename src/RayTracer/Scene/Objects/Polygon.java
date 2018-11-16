package RayTracer.Scene.Objects;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;

import Math.Vector;
import Math.Compare;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import Util.Color;

public class Polygon extends Plane
{
	private Vector[] limits;
	private Vector[] segmentNormals;

	public Polygon(Vector[] limits)
	{
		super(Vector.crossProduct(Vector.subtract(limits[0], limits[1]), Vector.subtract(limits[0], limits[2])), limits[0]);
		this.limits = limits;
		this.setColor(new Color((int) (this.normal.get(0) * 255), (int) (this.normal.get(1) * 255),(int) (this.normal.get(2) * 255)));

		this.segmentNormals = new Vector[limits.length];

		for(int i = 0; i < limits.length; i++)
		{
			int nextIndex = (i + 1) % this.limits.length;

			Vector segment = Vector.subtract(this.limits[nextIndex], this.limits[i]);
			this.segmentNormals[i] = Vector.crossProduct(this.normal, segment);
			this.segmentNormals[i].normalize(false);
		}
	}

	public Polygon(Vector[] limits, Color color)
	{
		this(limits);
		this.setColor(color);
	}

	public Polygon(Polygon polygon)
	{
		this(polygon.limits);
	}

	public Vector[] getLimits()
	{
		return this.limits;
	}

	@Override
	public HitObject internalHit(Ray r, Tracer tracer, World world)
	{
		HitObject planeHit = super.internalHit(r, tracer, world);
		if(planeHit != null)
		{
			if (this.isInside(planeHit.getHitpoint()))
			{
				return planeHit;
			}
		}
		return null;
	}

	@Override
	public boolean isInside(Vector testPoint)
	{
		for(int i = 0; i < this.limits.length; i++)
		{
			if(testPoint.equals(this.limits[i]))
			{
				return true;
			}

			Vector diff = Vector.subtract(testPoint, this.limits[i]);
			double dot = Vector.dotProduct(diff, this.segmentNormals[i]);

			if(Compare.compare(dot, 0.0) < 0)
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		builder.append("Polygon -> ");
		builder.append("LIMITS:\t");
		for(int i = 0; i < this.limits.length; i++)
		{
			builder.append(this.limits[i]);
			builder.append("\t");
		}

		return builder.toString();
	}
}
