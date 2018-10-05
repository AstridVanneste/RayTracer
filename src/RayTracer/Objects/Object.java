package RayTracer.Objects;

import RayTracer.HitObject;
import RayTracer.Ray;

public abstract class Object
{
	public abstract HitObject hit(Ray r);
}
