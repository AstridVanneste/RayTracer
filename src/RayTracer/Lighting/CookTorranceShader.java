package RayTracer.Lighting;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import Math.Vector;
import Math.Geometry;
import RayTracer.Scene.Objects.Entity;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import Util.Color;

public class CookTorranceShader extends Shader
{
	private double m;
	private double eta;

	public CookTorranceShader(Entity entity, double m, double eta)
	{
		super(entity);
		this.m = m;			// TODO boundaries for m
		this.eta = eta;

	}

	@Override
	public Color getLight(World world, Ray ray, Tracer tracer, HitObject hit)
	{


		return null;
	}

	public Color getLight(Light light, Ray ray, Tracer tracer, HitObject hit)
	{
		Vector lightDir = Vector.subtract(light.getPosition(), hit.getHitpoint());
		Ray lightRay = new Ray(light.getPosition(), lightDir);

		Vector facetNormal = this.calcFacetNormal(ray, lightRay);
		Vector surfaceNormal = hit.getNormal();
		Vector viewDir = ray.getDir();

		double phi = Geometry.angle(lightDir, surfaceNormal);
		double theta = Geometry.angle(viewDir, surfaceNormal);
		double delta = this.calcDelta(phi, theta);

		return Color.WHITE;
	}

	private double calcFresnelCoefficient(double phi)
	{
		double c = Math.cos(phi);
		double g = Math.sqrt(Math.pow(this.eta, 2) + Math.pow(c, 2) - 1);
		double fraction1 = 1/2 * (Math.pow(g - c, 2)/Math.pow(g + c, 2));
		double fraction2 = 1 + Math.pow((c * (g + c) - 1)/(c * (g - c) + 1), 2);

		return fraction1 * fraction2;
	}

	private double calcGeometryFactor(Vector surfaceNormal, Vector facetNormal, Vector viewDir, Vector lightDir)
	{
		double Gm = this.calcMaskGeometryFactor(surfaceNormal, facetNormal, lightDir);
		double Gs = this.calcShadeGeometryFactor(surfaceNormal, facetNormal, viewDir, lightDir);

		return Math.min(Math.min(1.0, Gm), Gs);
	}

	// TODO can be more efficient by combining with calc of mask geometry factor
	private double calcShadeGeometryFactor(Vector surfaceNormal, Vector facetNormal, Vector viewDir, Vector lightDir)
	{
		double numerator = 2 * Vector.dotProduct(surfaceNormal, facetNormal) * Vector.dotProduct(surfaceNormal, viewDir);
		double denominator = Vector.dotProduct(facetNormal, lightDir);
		return numerator/denominator;
	}

	private double calcMaskGeometryFactor(Vector surfaceNormal, Vector facetNormal, Vector lightDir)
	{
		double numerator = 2 * Vector.dotProduct(surfaceNormal, facetNormal) * Vector.dotProduct(surfaceNormal, lightDir);
		double denominator = Vector.dotProduct(facetNormal, lightDir);

		return numerator/denominator;
	}

	private Vector calcFacetNormal(Ray ray, Ray light)
	{
		return Vector.add(light.getDir(), ray.getDir());
	}

	private double calcDelta(double phi, double theta)
	{
		return (theta - phi)/2;
	}

	private double calcDistribution(double delta)
	{
		double exponent = - Math.pow(Math.tan(delta)/this.m, 2);
		double denominator = 4 * Math.pow(this.m, 2) * Math.pow(Math.cos(delta), 4);

		return Math.exp(exponent)/denominator;
	}
}
