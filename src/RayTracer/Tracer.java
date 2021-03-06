package RayTracer;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Hittable;
import RayTracer.Hit.Ray;

public interface Tracer
{
	public HitObject trace(Ray r, int traceLevel);

	public HitObject trace(Ray r, Hittable excluded, int traceLevel);
}
