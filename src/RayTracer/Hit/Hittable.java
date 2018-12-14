package RayTracer.Hit;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import RayTracer.Transformation;

public interface Hittable
{
	public HitObject hit(Ray r, Tracer tracer, World world, int traceLevel);

	public int getID();
}
