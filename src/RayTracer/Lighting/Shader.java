package RayTracer.Lighting;

import Math.Vector;
import RayTracer.Factories.VectorFactory;
import RayTracer.Hit.Ray;
import RayTracer.Scene.World;

import java.awt.*;
import java.security.InvalidParameterException;

public class Shader
{
	private Vector normal;
	private double m;

	public Shader(Vector normal, double m)
	{
		if(!VectorFactory.isVector(normal))
		{
			throw new InvalidParameterException("Normal parameter is not a vector");
		}

		this.normal = normal;
		this.m = m;	//TODO check boundaries
	}

	private Color getAmbientComponent(Light light)
	{
		Color lightColor = light.getColor();

		int r, g, b;

		r = (int) (lightColor.getRed() * light.getAmbient());
		g = (int) (lightColor.getGreen() * light.getAmbient());
		b = (int) (lightColor.getBlue() * light.getAmbient());

		return new Color(r, g, b);
	}


	public Color getLighterComponent(World world, Ray r, Vector hitpoint)
	{
		Color component = Color.BLACK;
		for(Light light: world.getLights())
		{
			component = this.getLighterComponent(light, r, hitpoint);
		}

		return component;
	}

	private Color getLighterComponent(Light light, Ray r, Vector hitpoint)
	{
		return this.getAmbientComponent(light);
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
