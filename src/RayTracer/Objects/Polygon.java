package RayTracer.Objects;

import RayTracer.HitObject;
import RayTracer.Ray;

import Math.Vector;

public class Polygon extends Object
{
	private Plane plane;
	private Vector[] limits;

	public Polygon(Plane plane, Vector[] limits)
	{
		this.plane = plane;
		this.limits = limits;
	}

	@Override
	public HitObject hit(Ray r)
	{
		HitObject planeHit = this.plane.hit(r);
		if(planeHit != null)
		{
			if (this.isOn(planeHit.getHitpoint()))
			{
				return planeHit;
			}
		}
		return null;
	}

	public boolean isOn(Vector testPoint)
	{
		for(int i =0; i < this.limits.length; i++)
		{
			Vector current = this.limits[i];
			Vector next = this.limits[(i + 1) % this.limits.length];

			Vector segment = Vector.subtract(next, current);
			Vector normal = Vector.crossProduct(this.plane.getNormal(), segment);

			Vector diff = Vector.subtract(testPoint, current);

			double dot = Vector.dotProduct(diff, normal);

			if(dot <= 0)
			{
				return false;
			}
		}
		return true;
	}
}
