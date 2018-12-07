package RayTracer.Lighting;

import RayTracer.Factories.VectorFactory;
import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import Util.Color;
import Math.Vector;
import Math.Geometry;

import java.util.List;

public class Reflector
{
	public Color calculateReflection(World world, Ray r, Tracer tracer, HitObject hit)
	{
		Vector reflectionDir = Geometry.reflect(r.getDir(), hit.getNormal());

		Ray reflection = new Ray(hit.getHitpoint(), reflectionDir);

		return tracer.trace(r).getColor();
	}
}
