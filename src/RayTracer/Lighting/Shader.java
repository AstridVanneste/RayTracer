package RayTracer.Lighting;

import Math.Vector;
import Math.Geometry;
import RayTracer.Factories.VectorFactory;
import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import RayTracer.Scene.Objects.Object;
import Util.Color;

import java.security.InvalidParameterException;

public class Shader
{
	private Object object;

	private Vector normal;
	private double m;

	public Shader(Vector normal, double m, Object object)
	{
		if(!VectorFactory.isVector(normal))
		{
			throw new InvalidParameterException("Normal parameter is not a vector");
		}

		this.object = object;
		this.normal = normal;
		this.m = m;	//TODO check boundaries
	}

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

		HitObject lightHit = tracer.trace(lightRay, this.object, 0);

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

		//System.out.println(spec);

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

	private Vector calcFacetNormal(Ray ray, Ray light)
	{
		return Vector.add(light.getDir(), ray.getDir());
	}

	private double calcDelta(double phi, double theta)
	{
		return (theta - phi)/2;
	}

	private double calcFraction(double delta)
	{
		double exponent = - Math.pow(Math.tan(delta)/this.m, 2);
		double denominator = 4 * Math.pow(this.m, 2) * Math.pow(Math.cos(delta), 4);

		return Math.exp(exponent)/denominator;
	}
}
