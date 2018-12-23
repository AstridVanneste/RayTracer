package RayTracer.Scene.Objects;

import RayTracer.Factories.TextureFactory;
import RayTracer.Hit.HitObject;
import RayTracer.Hit.Hittable;
import RayTracer.Hit.Ray;
import RayTracer.Lighting.LightManager;
import RayTracer.Scene.Textures.Texture;
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
		public static final String COLOR = "color";
		public static final String MATERIAL = "material";
		public static final String TEXTURE = "texture";
	}

	private int ID;
	private LightManager lighting;
	protected Color color;
	private boolean transform;
	private Transformation transformation;
	private Texture texture;


	// TODO fix ID's
	public Entity()
	{
		this.ID = 0;
		this.color = Color.LIGHT_GRAY;
		this.transform = false;
		this.transformation = new Transformation(Matrix.identityMatrix(4), Matrix.identityMatrix(4));
		this.lighting = new LightManager();		//TODO assign normal
	}

	public Entity(Color color)
	{
		this.ID = 0;
		this.color = color;
		this.transform = false;
		this.transformation = new Transformation(Matrix.identityMatrix(4), Matrix.identityMatrix(4));
		this.lighting = new LightManager();		//TODO assign normal

	}

	public Entity(Color color, Transformation transformation)
	{
		this.ID = 0;
		this.color = color;
		this.transform = true;
		this.transformation = transformation;
		this.lighting = new LightManager();		//TODO assign normal
	}

	public Entity(JSONObject jsonObject, int ID)
	{
		this();

		this.ID = ID;
		JSONArray c = jsonObject.getJSONArray(JSON.COLOR);
		this.color = new Color(c);
		this.lighting = new LightManager(jsonObject.getString(JSON.MATERIAL));

		if(jsonObject.has(JSON.TEXTURE))
		{
			this.texture = TextureFactory.get(jsonObject.getJSONObject(JSON.TEXTURE));
		}
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
	public int getID()
	{
		return ID;
	}

	@Override
	public HitObject hit(Ray r, Tracer tracer, World world, int traceLevel)
	{
		// RAY TRANSFORMATION
		if(this.transform)
		{
			r = new Ray(r);
			r.inverseTransform(this.transformation);
		}

		// CALCULATE HIT
		HitObject hit = this.internalHit(r, tracer, world, traceLevel);

		if(hit != null)
		{
			// TRANSFORM HIT INFORMATION
			if(this.transform)
			{
				hit.transform(this.transformation);
			}

			// SAMPLING TEXTURE
			if(this.texture != null)
			{
				hit.setColor(this.texture.sample(hit.getHitpoint()));
			}
		}
		return hit;
	}

	abstract public HitObject internalHit(Ray r, Tracer tracer, World world, int traceLevel);

	public Color calculateColor(Tracer tracer, World world, Ray ray, HitObject hit)
	{
		return this.lighting.calculateColor(tracer, world, ray, hit);
	}
}
