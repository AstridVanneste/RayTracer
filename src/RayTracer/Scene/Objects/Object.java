package RayTracer.Scene.Objects;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Hittable;
import RayTracer.Hit.Ray;
import RayTracer.Lighting.LightManager;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import RayTracer.Transformation;
import Math.*;
import Util.Color;

public abstract class Object implements Hittable
{
	private LightManager lighting;
	private Color color;
	protected boolean transform;
	protected Transformation transformation;

	public Object()
	{
		this.color = Color.LIGHT_GRAY;
		this.transform = false;
		this.transformation = new Transformation(Matrix.identityMatrix(4), Matrix.identityMatrix(4));
		this.lighting = new LightManager(this.color, new Vector(4));		//TODO assign normal
	}

	public Object(Color color)
	{
		this.color = color;
		this.transform = false;
		this.transformation = new Transformation(Matrix.identityMatrix(4), Matrix.identityMatrix(4));
	}

	public Object(Color color, Transformation transformation)
	{
		this.color = color;
		this.transform = true;
		this.transformation = transformation;
	}

	protected Color getColor()
	{
		return this.color;
	}

	protected void  setColor(Color color)
	{
		this.color = color;
	}

	public void setTransformation(Transformation transformation)
	{
		this.transform = true;
		this.transformation = transformation;
	}


	public HitObject hit(Ray r, Tracer tracer, World world)
	{
		if(this.transform)
		{
			r.inverseTransform(this.transformation);
		}
		HitObject hit = this.internalHit(r,tracer, world);

		// LIGHTING
		if(hit != null)
		{
			hit.setColor(this.lighting.illuminate(tracer, world, r, hit, hit.getColor()));
		}

		// TRANSFORM HITPOINT
		if(this.transform)
		{
			hit.setHitpoint(this.transformation.transform(hit.getHitpoint()));
		}

		return hit;
	}

	abstract public HitObject internalHit(Ray r, Tracer tracer, World world);
}
