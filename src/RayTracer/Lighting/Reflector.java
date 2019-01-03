package RayTracer.Lighting;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Settings;
import RayTracer.Tracer;
import Util.Color;
import Math.Vector;
import Math.Geometry;

public class Reflector
{
	private double reflectivity;

	public Reflector(double reflectivity)
	{
		this.reflectivity = reflectivity;
	}

	public Reflector(Material material)
	{
		this(material.getReflectivity());
	}

	public Color calculateReflection(Ray r, Tracer tracer, HitObject hit)
	{
		if(this.reflectivity > 0.1)
		{
			Vector reflectionOrigin = Vector.add(hit.getHitpoint(), Vector.multiply(hit.getNormal(), 1e-10));

			Vector reflectionDir = Geometry.reflect(r.getDir(), hit.getNormal());

			Ray reflection = new Ray(reflectionOrigin, reflectionDir, hit.getObject().getID());

			HitObject reflectionHit = tracer.trace(reflection, hit.getTraceLevel() - 1);

			if (reflectionHit != null)
			{
				double distance = Geometry.distance(hit.getHitpoint(), reflectionHit.getHitpoint());

				if (distance > 1e-10)
				{
					Color color = new Color(reflectionHit.getColor());
					color.scale(this.reflectivity);
					return color;
				}
			}
			else
			{
				Color color = new Color(Settings.BACKGROUND_COLOR);
				color.scale(this.reflectivity);
				return color;
			}
		}

		return new Color(Color.BLACK);
	}

	public double getReflectivity()
	{
		return this.reflectivity;
	}
}
