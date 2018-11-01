package RayTracer.Objects;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;

import Math.Vector;

import java.awt.*;
import java.security.InvalidParameterException;

public class Polygon extends Plane
{
	private Vector[] limits;
	private Vector[] segmentNormals;

	public Polygon(Vector[] limits)
	{
		super(Vector.crossProduct(Vector.subtract(limits[0], limits[1]), Vector.subtract(limits[0], limits[2])), limits[0]);
		this.limits = limits;
		this.setColor(new Color(new Float(Math.abs((this.normal.get(0)/7) % 1)), new Float(Math.abs((this.normal.get(1)/7) % 1)),new Float(Math.abs((this.normal.get(2)/7) % 1))));

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

	public Vector[] getLimits()
	{
		return this.limits;
	}

	@Override
	public HitObject hit(Ray r)
	{
		HitObject planeHit = super.hit(r);
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

			if(Double.compare(dot, 0.0) < 0)
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
