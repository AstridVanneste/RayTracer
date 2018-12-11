package RayTracer.Lighting;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Scene.Material;
import RayTracer.Tracer;
import Util.Color;
import Math.Vector;
import Math.Geometry;

public class Reflector
{
	private double intenstity;

	public Reflector(double intenstity)
	{
		this.intenstity = intenstity;
	}

	public Reflector(Material material)
	{
		this.intenstity = material.getReflectivity();
	}

	public Color calculateReflection(Ray r, Tracer tracer, HitObject hit)
	{
		Vector reflectionDir = Geometry.reflect(r.getDir(), hit.getNormal());

		Ray reflection = new Ray(hit.getHitpoint(), reflectionDir);

		HitObject reflectionHit = tracer.trace(reflection, hit.getTraceLevel() - 1);

		if (reflectionHit != null)
		{
			Color color = new Color(reflectionHit.getColor());
			color.scale(this.intenstity);
			return color;
		}

		return new Color(Color.BLACK);
	}
}
