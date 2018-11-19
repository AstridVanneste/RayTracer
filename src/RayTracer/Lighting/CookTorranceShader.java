package RayTracer.Lighting;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import Math.Vector;
import RayTracer.Scene.Objects.Entity;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import Util.Color;

public class CookTorranceShader extends Shader
{
	private double m;

	public CookTorranceShader(Entity entity)
	{
		super(entity);
	}

	@Override
	public Color getLight(World world, Ray ray, Tracer tracer, HitObject hit)
	{
		return null;
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
