package RayTracer.Scene.Objects;

import RayTracer.Factories.TransformationFactory;
import RayTracer.Factories.VectorFactory;
import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import Math.Vector;
import Math.Compare;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import RayTracer.Transformation;
import Util.Color;
import org.json.JSONObject;

import java.security.InvalidParameterException;

public class Sphere extends Entity
{
	private class JSON
	{
		public static final String CENTER = "center";
		public static final String RADIUS = "radius";
	}

	public Sphere() throws InvalidParameterException
	{
		super();
	}

	public Sphere(Vector center, double radius)
	{
		this.setPosition(center, radius);
	}

	public Sphere(JSONObject jsonObject, int ID)
	{
		super(jsonObject, ID);
		Vector center = VectorFactory.createPointVector(jsonObject.getJSONArray(JSON.CENTER));
		double radius = jsonObject.getDouble(JSON.RADIUS);

		this.setPosition(center, radius);
	}

	public void setPosition(Vector center, double radius)
	{
		Transformation transformation = TransformationFactory.scalingTransformation(radius, radius, radius);
		transformation.add(TransformationFactory.translationTransformation(center));
		this.setTransformation(transformation);
	}

	@Override
	public HitObject internalHit(Ray r, Tracer tracer, World world, int traceLevel)
	{
		double A = Vector.dotProduct(r.getDir(), r.getDir());
		double B = Vector.dotProduct(r.getDir(), r.getEye());
		double C = Vector.dotProduct(r.getEye(), r.getEye()) - 1.0;

		double discriminant =  Math.pow(B, 2.0) - (A * C);

		double k = 0.0;
		if(Compare.compare(discriminant, 0.0) < 0)					// NO HITPOINT
		{
			//System.out.println("NO HITPOINT");
			return null;
		}
		else if(Compare.compare(discriminant, 0.0) == 0)			// 1 HITPOINT
		{
			//System.out.println("1 HITPOINT");
			k = -B/A;
		}
		else if(Compare.compare(discriminant, 0.0) > 0)				// 2 HITPOINTS
		{
			//System.out.println("2 HITPOINTS");
			double k1 = (-B + Math.sqrt(discriminant))/(A);
			double k2 = (-B - Math.sqrt(discriminant))/(A);

			// hits behind eye
			boolean compareK1 = Double.compare(k1, 0.0) < 0;
			boolean compareK2 = Double.compare(k2, 0.0) < 0;

			if(compareK1 && compareK2)
			{
				return null;
			}
			else if(compareK1)
			{
				k = k2;
			}
			else if(compareK2)
			{
				k = k1;
			}
			else
			{
				k = Math.min(k1, k2);
			}
		}

		Vector hitpoint = r.getPoint(k);
		return new HitObject(this, hitpoint, k, this.color, hitpoint,k, traceLevel);
	}
}
