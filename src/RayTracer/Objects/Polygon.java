package RayTracer.Objects;

import RayTracer.HitObject;
import RayTracer.Ray;

import Math.Vector;

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
		}
		else
		{
			throw new InvalidParameterException("Cannot create a polygon with less than 3 limit points, given " + limits.length + " points");
		}
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

			double dot = Vector.dotProduct(diff, norm);

			if(Double.compare(dot, 0.0f) < 0)
			{
				if(i != 0)
				{
					System.out.println("failed on side " + i + " -> " + nextIndex);
				}

				return false;
			}
		}
		return true;
	}
}
