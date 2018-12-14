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
	private double intensity;

	private Entity entity;

	public Reflector(double intensity, Entity entity)
	{
		this.intensity = intensity;
		this.entity = entity;
	}

	public Reflector(Material material, Entity entity)
	{
		this(material.getReflectivity(), entity);
	}

	public Color calculateReflection(Ray r, Tracer tracer, HitObject hit)
	{
		if(this.intensity > 0.1)
		{
			Vector reflectionDir = Geometry.reflect(r.getDir(), hit.getNormal());

			Ray reflection = new Ray(hit.getHitpoint(), reflectionDir);

			HitObject reflectionHit = tracer.trace(reflection,this.entity, hit.getTraceLevel() - 1);

			if (reflectionHit != null)
			{
				double distance = Geometry.distance(hit.getHitpoint(), reflectionHit.getHitpoint());
				if (distance > 1e-13)
				{
					Color color = new Color(reflectionHit.getColor());
					color.scale(this.intensity);
					return color;
				}
			}
		}

		return new Color(Color.BLACK);
	}
}
