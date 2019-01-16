package RayTracer.Scene.Objects;

import RayTracer.Factories.VectorFactory;
import RayTracer.Hit.HitObject;
import RayTracer.Hit.Hittable;
import RayTracer.Hit.Ray;
import Math.Vector;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import Util.Color;
import Math.Geometry;
import Util.OBJReader;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mesh extends Entity
{
	private static final String OBJ_LOC = "res/OBJ/";

	private class JSON
	{
		public static final String FILENAME = "filename";
	}

	private List<Polygon> elements;

	public Mesh(List<Polygon> elements)
	{
		this.elements = elements;
	}

	public Mesh(Mesh m)
	{
		this.elements = new ArrayList<>();
		for(Polygon polygon: m.elements)
		{
			this.elements.add(new Polygon(polygon));
		}
	}

	public Mesh(JSONObject jsonObject, int ID) throws IOException
	{
		super(jsonObject, ID);

		String path = OBJ_LOC + jsonObject.getString(JSON.FILENAME);

		this.elements = OBJReader.parseFaces(new BufferedReader(new FileReader(path)));
	}

	@Override
	public HitObject internalHit(Ray r, Tracer tracer, World world, int traceLevel)
	{
		HitObject hit = null;

		double distance = 0;
		for(Hittable element: this.elements)
		{
			HitObject tempHit = element.hit(r, tracer, world, traceLevel);

			if (tempHit != null)
			{
				if (Geometry.distance(r.getEye(), tempHit.getHitpoint()) < distance || distance == 0)
				{
					hit = tempHit;
					distance = Geometry.distance(r.getEye(), tempHit.getHitpoint());
				}
			}
		}
		return hit;
	}

	@Override
	public void setColor(Color color)
	{
		super.setColor(color);
		for(Entity entity : this.elements)
		{
			entity.setColor(color);
		}
	}
}
