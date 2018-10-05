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
	}

	@Override
	public HitObject hit(Ray r)
	{
		return null;
	}
}
