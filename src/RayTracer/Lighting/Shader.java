package RayTracer.Lighting;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Scene.Objects.Entity;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import Util.Color;
import Math.Vector;
import Math.Geometry;
import Math.Compare;

abstract public class Shader
{

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

		Vector lightDir = Vector.subtract(hit.getHitpoint(), light.getPosition());
		Ray lightRay = new Ray(light.getPosition(), lightDir);

		HitObject lightHit = tracer.trace(lightRay, 0);

		if(lightHit != null)
		{
			double hitDistance = Geometry.distance(hit.getHitpoint(), lightHit.getHitpoint());
			double lightHitDistance = Geometry.distance(lightHit.getHitpoint(), light.getPosition());

			if(hitDistance > 1e-12 && lightHitDistance < distance)
			{
				return true;
			}
		}
		return false;
	}

	protected abstract Color getLighterComponent(Light light, Ray r, HitObject hit);

	protected abstract Color getAmbientComponent(Light light);
}
