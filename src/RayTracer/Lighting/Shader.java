package RayTracer.Lighting;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Scene.Objects.Entity;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import Util.Color;
import Math.Vector;
import Math.Geometry;

abstract public class Shader
{
	protected Entity entity;

	public Shader(Entity entity)
	{
		this.entity = entity;
	}

	public Color getLight(World world, Ray r, Tracer tracer, HitObject hit)
	{
		Color component = new Color(Color.BLACK);
		for(Light light: world.getLights())
		{
			if(light.isEnableDiffuse() ||  light.isEnableSpecular())
			{
				if (!this.masked(light, tracer, hit))
				{
					component.add(this.getLighterComponent(light, r, hit));
				}
				else
				{
					component.add(this.getAmbientComponent(light));
				}
			}
			else
			{
				component.add(this.getAmbientComponent(light));
			}
		}
		return component;
	}

	private boolean masked(Light light, Tracer tracer, HitObject hit)
	{
		double distance = Geometry.distance(light.getPosition(), hit.getHitpoint());

		Ray lightRay = new Ray(light.getPosition(), Vector.subtract(hit.getHitpoint(), light.getPosition()));

		HitObject lightHit = tracer.trace(lightRay, this.entity, 0);

		if(lightHit != null)
		{
			double lightHitDistance = Geometry.distance(light.getPosition(), lightHit.getHitpoint());

			if(lightHitDistance < distance)
			{
				return true;
			}
		}

		return false;
	}

	protected abstract Color getLighterComponent(Light light, Ray r, HitObject hit);

	protected abstract Color getAmbientComponent(Light light);
}
