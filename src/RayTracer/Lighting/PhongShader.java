package RayTracer.Lighting;

import Math.Vector;
import Math.Geometry;
import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import RayTracer.Scene.Objects.Entity;
import Util.Color;

public class PhongShader extends Shader
{
	public PhongShader(Entity entity)
	{
		super(entity);
	}

	@Override
	public Color getLight(World world, Ray r, Tracer tracer, HitObject hit)
	{
		Color component = new Color(Color.BLACK);
		for(Light light: world.getLights())
		{
			if(!this.masked(light, tracer, hit))
			{
				component.add(this.getLighterComponent(light, r, hit));
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

	private Color getAmbientComponent(Light light)
	{
		Color lightColor = new Color(light.getColor());

		lightColor.scale(light.getAmbientStrength());

		return lightColor;
	}

	private Color getDiffuseComponent(Light light, HitObject hit)
	{
		Vector lightDir = Vector.subtract(light.getPosition(), hit.getHitpoint());

		double fraction = Vector.dotProduct(lightDir, hit.getNormal());

		Color lightColor = new Color(light.getColor());

		lightColor.scale(fraction);

		return lightColor;
	}

	private Color getSpecularComponent(Light light, Ray r, HitObject hit)
	{
		Vector lightDir = Vector.subtract(light.getPosition(), hit.getHitpoint());

		Vector viewDir = r.getDir();
		Vector reflectDir = Geometry.reflect(lightDir, hit.getNormal());

		double spec = Math.pow(Math.max(Vector.dotProduct(viewDir, reflectDir), 0.0), 256);

		Color color = new Color(light.getColor());

		color.scale(light.getSpecularStrength());
		color.scale(spec);

		return color;
	}


	private Color getLighterComponent(Light light, Ray r, HitObject hit)
	{
		Color color = new Color(Color.BLACK);
		color.add(this.getAmbientComponent(light));
		color.add(this.getDiffuseComponent(light, hit));
		color.add(this.getSpecularComponent(light, r, hit));
		return color;
	}


}
