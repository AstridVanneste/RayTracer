package RayTracer.Objects;

import RayTracer.HitObject;
import RayTracer.Ray;

public interface Hittable
{
	public HitObject hit(Ray r);
}
