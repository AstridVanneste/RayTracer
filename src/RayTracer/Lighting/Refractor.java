package RayTracer.Lighting;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Settings;
import RayTracer.Tracer;
import Util.Color;
import Math.Vector;
import Math.Geometry;

public class Refractor
{
	private double refractivity;
	private double refractionIndex;

	public Refractor(double refractivity, double refractionIndex)
	{
		this.refractivity = refractivity;
		this.refractionIndex = refractionIndex;
	}

	public Refractor(Material material)
	{
		this.refractivity = material.getRefractivity();
		this.refractionIndex = (material.getRefractionIndex()[0] + material.getRefractionIndex()[1] + material.getRefractionIndex()[2])/3;
	}

	public Color calculateRefraction(Ray r, Tracer tracer, HitObject hit)
	{

		if(this.refractivity > 0.1)
		{
			Vector normal = hit.getNormal();
			normal.normalize();

			Vector rayDir = r.getDir();
			rayDir.normalize();

			double refractionIndex = this.refractionIndex;
			Vector refractionOrigin;
			if(hit.isInsideHit())
			{
				refractionOrigin = Vector.add(hit.getHitpoint(), Vector.multiply(normal, 1e-10));
			}
			else
			{
				refractionIndex = 1/refractionIndex;
				refractionOrigin = Vector.add(hit.getHitpoint(), Vector.multiply(normal, -1e-10));
			}

			Vector refractionDir = this.getRefractionDirection(normal, rayDir, refractionIndex);

			Ray refractRay = new Ray(refractionOrigin, refractionDir);

			HitObject refractHit = tracer.trace(refractRay, hit.getTraceLevel() - 1);

			if (refractHit != null)
			{
				double distance = Geometry.distance(hit.getHitpoint(), refractHit.getHitpoint());
				if (distance > 1e-10)
				{
					Color color = new Color(refractHit.getColor());
					//System.out.println("1. " + color);
					color.scale(this.refractivity);
					//System.out.println("2. " + color);
					return color;
				}
			}
			else
			{
				Color color = new Color(Settings.BACKGROUND_COLOR);
				color.scale(this.refractivity);
				return color;
			}
		}
		return new Color(Color.BLACK);
	}

	public Vector getRefractionDirection(Vector normal, Vector rayDir, double refractionIndex)
	{

		double cos = this.snellsLaw(normal, rayDir, refractionIndex);

		Vector term1 = Vector.multiply(rayDir, this.refractionIndex);
		Vector term2 = Vector.multiply(normal, this.refractionIndex * Vector.dotProduct(normal, rayDir) - cos);

		Vector t = Vector.add(term1, term2);
		t.normalize();

		return t;
	}

	public double snellsLaw(Vector normal, Vector rayDir, double refractionIndex)
	{
		double dot = Vector.dotProduct(normal, rayDir);
		double term = Math.pow(refractionIndex, 2) * (1 - Math.pow(dot, 2));
		double a = 1 - term;
		double cos = Math.sqrt(a);
		return cos;
	}
}
