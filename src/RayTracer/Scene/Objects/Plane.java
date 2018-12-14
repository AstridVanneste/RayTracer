package RayTracer.Scene.Objects;

import RayTracer.Factories.VectorFactory;
import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import Util.Color;
import Math.Vector;
import Math.Compare;
import org.json.JSONObject;

import java.security.InvalidParameterException;

public class Plane extends Entity
{
	protected Vector normal;
	protected Vector point;

	private double dot;

	protected Plane()
	{
		super();
	}

	public Plane(Vector normal, Vector point) throws InvalidParameterException
	{
		super();
		if(VectorFactory.isVector(normal))
		{
			this.normal = normal;
			this.normal.normalize();
		}
		else
		{
			throw new InvalidParameterException("Normal parameter is not vector");
		}
		if(VectorFactory.isPoint(point))
		{
			this.point = point;
		}
		else
		{
			throw new InvalidParameterException("Point parameter is not point");
		}

		this.calcDot();
	}

	public void calcDot()
	{
		this.dot = Vector.dotProduct(normal, point);
	}

	public Plane(Vector normal, Vector point, Color color)
	{
		this(normal, point);
		this.setColor(color);
	}

	public Plane(JSONObject jsonObject, int ID)
	{
		super(jsonObject, ID);
		// TODO complete
	}

	@Override
	public HitObject internalHit(Ray r, Tracer tracer, World world, int traceLevel)
	{
		double numerator = this.dot - Vector.dotProduct(this.normal, r.getEye());
		double denominator = Vector.dotProduct(this.normal, r.getDir());

		if(Compare.compare(denominator, 0.0) == 0)
		{
			return null;			// if dot product of plane normal and ray direction is 0 then you are looking straight at a flat plane
		}

		double k = numerator/denominator;

		if(Compare.compare(k, 0.0) > 0)
		{
			Vector hitpoint = r.getPoint(k);

			return new HitObject(this, hitpoint, this.getColor(), this.normal, traceLevel);
		}
		else
		{
			return null;
		}
	}

	public boolean isInside(Vector p)
	{
		if(VectorFactory.isPoint(p))
		{
			double product = Vector.dotProduct(Vector.subtract(p, this.point), this.normal);

			if(Compare.compare(product, 0.0) == 0)
			{
				return true;
			}
		}

		return false;
	}
}
