package RayTracer.Objects;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;

import Math.Vector;

import java.awt.*;
import java.security.InvalidParameterException;

public class Polygon extends Plane
{
	private Vector[] limits;

	public Polygon(Vector[] limits)
	{
		if(limits.length >= 3)
		{
			this.limits = limits;
			this.point = this.limits[0];
			this.normal = Vector.crossProduct(Vector.subtract(this.limits[0], this.limits[1]), Vector.subtract(this.limits[0], this.limits[2]));
			this.setColor(new Color(new Float(Math.abs((this.normal.get(0)/7) % 1)), new Float(Math.abs((this.normal.get(1)/7) % 1)),new Float(Math.abs((this.normal.get(2)/7) % 1))));
		}
		else
		{
			throw new InvalidParameterException("Cannot create a polygon with less than 3 limit points, given " + limits.length + " points");
		}
	}

	public Polygon(Vector[] limits, Color color)
	{
		this(limits);
		this.setColor(color);
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

			int nextIndex = (i + 1) % this.limits.length;

			Vector segment = Vector.subtract(this.limits[nextIndex], this.limits[i]);
			Vector diff = Vector.subtract(testPoint, this.limits[i]);

			Vector norm = Vector.crossProduct(this.normal, segment);
			norm.normalize();
			double dot = Vector.dotProduct(diff, norm);

			if(Double.compare(dot, 0.0) < 0)
			{
				return false;
			}
		}
		return true;
	}
}
