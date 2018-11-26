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
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Entity implements Hittable
{
	private class JSON
	{
		public static final String COLOR = "colour";
	}

	private LightManager lighting;
	private Color color;
	protected boolean transform;
	protected Transformation transformation;

	public Entity()
	{
		this.color = Color.LIGHT_GRAY;
		this.transform = false;
		this.transformation = new Transformation(Matrix.identityMatrix(4), Matrix.identityMatrix(4));
		this.lighting = new LightManager(this);		//TODO assign normal
	}

	public Entity(Color color)
	{
		this.color = color;
		this.transform = false;
		this.transformation = new Transformation(Matrix.identityMatrix(4), Matrix.identityMatrix(4));
		this.lighting = new LightManager(this);		//TODO assign normal

	}

	public Entity(Color color, Transformation transformation)
	{
		this.color = color;
		this.transform = true;
		this.transformation = transformation;
		this.lighting = new LightManager(this);		//TODO assign normal
	}

	public Entity(JSONObject jsonObject)
	{
		this();

		JSONArray c = jsonObject.getJSONArray(JSON.COLOR);
		this.color = new Color(c);

	}

	protected Color getColor()
	{
		return this.color;
	}

	public void  setColor(Color color)
	{
		this.color = color;
	}

	public void setTransformation(Transformation transformation)
	{
		this.transform = true;
		this.transformation = transformation;
	}


	@Override
	public HitObject hit(Ray r, Tracer tracer, World world, int traceLevel)
	{
		if(this.transform)
		{
			r = new Ray(r);
			r.inverseTransform(this.transformation);
		}
		HitObject hit = this.internalHit(r,tracer, world);

		if(hit != null)
		{
			// TRANSFORM HITPOINT
			if(this.transform)
			{
				Vector hitpoint = this.transformation.transform(hit.getHitpoint());
				hitpoint.normalize(true);
				hit.setHitpoint(hitpoint);
			}

			// LIGHTING
			if(traceLevel != 0)
			{
				hit.setColor(this.lighting.illuminate(tracer, world, r, hit, hit.getColor()));
			}
		}
		return hit;
	}

	abstract public HitObject internalHit(Ray r, Tracer tracer, World world);
}
