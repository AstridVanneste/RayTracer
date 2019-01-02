package RayTracer.Scene.Objects;

import RayTracer.Factories.VectorFactory;
import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import Math.Vector;
import Math.Compare;
import Math.Geometry;
import Util.Color;
import org.json.JSONObject;

import java.util.ArrayList;

public class TaperedCylinder extends Entity
{
	private class JSON
	{
		public static final String S = "s";
	}

	private Plane base;
	private Plane top;

	private double s;

	public TaperedCylinder(JSONObject json, int ID)
	{
		super(json, ID);
		this.base = new Plane(VectorFactory.createVector(0.0, -1.0, 0.0), VectorFactory.createPointVector(0.0, 0.0, 0.0));
		this.top = new Plane(VectorFactory.createVector(0.0, 1.0, 0.0), VectorFactory.createPointVector(0.0, 1.0, 0.0));

		this.s = json.getDouble(JSON.S);
	}

	@Override
	public HitObject internalHit(Ray r, Tracer tracer, World world, int traceLevel)
	{
		ArrayList<HitObject> hits = new ArrayList<>();

		hits.add(this.baseHit(r, tracer, world, traceLevel));
		hits.add(this.topHit(r, tracer, world, traceLevel));
		hits.add(this.wallHit(r, traceLevel));

		HitObject hit = null;
		for(HitObject tmpHit: hits)
		{
			if(tmpHit != null)
			{
				if(hit != null)
				{
					if (Geometry.distance(hit.getHitpoint(), r.getEye()) > Geometry.distance(tmpHit.getHitpoint(), r.getEye()))
					{
						hit = tmpHit;
					}
				}
				else
				{
					hit = tmpHit;
				}
			}
		}

		return hit;
	}

	private HitObject baseHit(Ray r, Tracer tracer, World world, int traceLevel)
	{
		HitObject baseHit = this.base.hit(r, tracer, world, traceLevel);

		if(baseHit != null)
		{
			Vector hitpoint = baseHit.getHitpoint();

			if(hitpoint.get(0) * hitpoint.get(0) + hitpoint.get(2) * hitpoint.get(2) < 1)
			{
				//System.out.println("basehit");
				baseHit.setColor(Color.GREEN);
				return baseHit;
			}
		}
		return null;
	}

	private HitObject topHit(Ray r, Tracer tracer, World world, int traceLevel)
	{
		HitObject baseHit = this.top.hit(r, tracer, world, traceLevel);

		if(baseHit != null)
		{
			Vector hitpoint = baseHit.getHitpoint();

			if( hitpoint.get(0) * hitpoint.get(0) + hitpoint.get(2) * hitpoint.get(2) < this.s * this.s)
			{
				//System.out.println("tophit");
				return baseHit;
			}
		}

		return null;
	}

	private HitObject wallHit(Ray r, int traceLevel)
	{
		Vector rayDir = r.getDir();
		rayDir.normalize();
		Vector eye = r.getEye();

		double d = (this.s - 1) * rayDir.get(1);
		double F = 1 + (this.s - 1) * eye.get(1);

		double A = rayDir.get(0) * rayDir.get(0) + rayDir.get(2) * rayDir.get(2) - d * d;
		double B = 2 * (eye.get(0) * rayDir.get(0) + eye.get(2) * rayDir.get(2)  - F * d);
		double C = eye.get(0) * eye.get(0) + eye.get(2) * eye.get(2) - F * F;

		double discriminant = B * B - 4 * A * C;

		double k = 0.0;

		if(Compare.compare(discriminant, 0.0) < 0)
		{
			return null;
		}
		if(Compare.compare(discriminant, 0.0) == 0)
		{
			k = -B/(2*A);
		}
		else if(Compare.compare(discriminant, 0.0) > 0)
		{
			double k1 = (-B + Math.sqrt(discriminant))/(2*A);
			double k2 = (-B - Math.sqrt(discriminant))/(2*A);

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

		if(0 <= hitpoint.get(1) && hitpoint.get(1) <= 1)
		{
			double y = -(this.s - 1) * (1 + (this.s - 1) * hitpoint.get(1));

			Vector normal = VectorFactory.createVector(hitpoint.get(0), y, hitpoint.get(2));

			return new HitObject(this, hitpoint, this.color, normal, k, traceLevel);
		}

		return null;
	}
}
