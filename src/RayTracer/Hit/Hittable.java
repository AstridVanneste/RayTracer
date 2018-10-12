package RayTracer.Hit;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;

public interface Hittable
{
	public HitObject hit(Ray r);
}
