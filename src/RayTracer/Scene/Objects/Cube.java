package RayTracer.Scene.Objects;

import Math.Vector;
import RayTracer.Factories.VectorFactory;
import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import Util.Color;
import org.json.JSONObject;

public class Cube extends Entity
{
	private Vector min;
	private Vector max;

	public Cube(JSONObject jsonObject, int ID)
	{
		super(jsonObject, ID);
		double[] m = {-1.0, -0.5, -1.0};
		this.min = new Vector(m);

		double[] M = {1.0, 1.0, 1.0};
		this.max = new Vector(M);
	}

	@Override
	public HitObject internalHit(Ray r, Tracer tracer, World world, int traceLevel)
	{
		double tmin = (this.min.get(0) - r.getEye().get(0))/r.getDir().get(0);
		double tmax = (this.max.get(0) - r.getEye().get(0))/r.getDir().get(0);

		if(tmin > tmax)
		{
			double tmp = tmin;
			tmin = tmax;
			tmax = tmp;
		}

		double tymin = (this.min.get(1) - r.getEye().get(1))/r.getDir().get(1);
		double tymax = (this.max.get(1) - r.getEye().get(1))/r.getDir().get(1);

		if(tymin > tymax)
		{
			double tmp = tymin;
			tymin = tymax;
			tymax = tmp;
		}

		if((tymax < tmin) || (tmax < tymin))
		{
			return null;
		}

		if(tmin < tymin)
		{
			tmin = tymin;
		}

		if(tmax > tymax)
		{
			tmax = tymax;
		}

		double tzmin = (this.min.get(2) - r.getEye().get(2))/r.getDir().get(2);
		double tzmax = (this.max.get(2) - r.getEye().get(2))/r.getDir().get(2);

		if(tzmin > tzmax)
		{
			double tmp = tzmin;
			tzmin = tzmax;
			tzmax = tmp;
		}

		if((tzmax < tmin) || (tmax < tzmin))
		{
			return null;
		}

		if(tmin < tzmin)
		{
			tmin = tzmin;
		}

		Vector hitpoint = r.getPoint(tmin);
		Vector normal = VectorFactory.createVector(1.0/2.0, 1.0/2.0, 1.0/2.0);

		normal.set(0, hitpoint.get(0) == this.min.get(0) ? -normal.get(0) : normal.get(0));
		normal.set(1, hitpoint.get(1) == this.min.get(1) ? -normal.get(1) : normal.get(1));
		normal.set(2, hitpoint.get(2) == this.min.get(2) ? -normal.get(2) : normal.get(2));

		return new HitObject(this, hitpoint, new Color(Color.BLACK), normal, tmin, traceLevel);
	}


}
