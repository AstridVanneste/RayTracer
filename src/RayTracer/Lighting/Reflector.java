package RayTracer.Lighting;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Scene.Material;
import RayTracer.Scene.Objects.Entity;
import RayTracer.Tracer;
import Util.Color;
import Math.Vector;
import Math.Geometry;

public class Reflector
{
	private Entity entity;
	private double intenstity;

	public Reflector(Entity entity, double intenstity)
	{
		this.entity = entity;
		this.intenstity = intenstity;
	}

	public Reflector(Entity entity, Material material)
	{
		this(entity, material.getReflectivity());
	}

	public Color calculateReflection(Ray r, Tracer tracer, HitObject hit)
	{
		Vector reflectionDir = Geometry.reflect(r.getDir(), hit.getNormal());

		Ray reflection = new Ray(hit.getHitpoint(), reflectionDir);

		HitObject reflectionHit = tracer.trace(reflection,this.entity,  hit.getTraceLevel() - 1);

		if (reflectionHit != null)
		{
			Color color = new Color(reflectionHit.getColor());
			color.scale(this.intenstity);
			return color;
		}

		return new Color(Color.BLACK);
	}
}
