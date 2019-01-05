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

	private Cube boundingBox;
	private List<Polygon> elements;

	public Mesh(List<Polygon> elements)
	{
		this.elements = elements;
		this.boundingBox = this.calculateBoundingBox();
	}

	public Mesh(Mesh m)
	{
		this.elements = new ArrayList<>();
		for(Polygon polygon: m.elements)
		{
			this.elements.add(new Polygon(polygon));
		}
		this.boundingBox = m.boundingBox;
	}

	public Mesh(JSONObject jsonObject, int ID) throws IOException
	{
		super(jsonObject, ID);

		String path = OBJ_LOC + jsonObject.getString(JSON.FILENAME);

		this.elements = OBJReader.parseFaces(new BufferedReader(new FileReader(path)));
	}

	private Cube calculateBoundingBox()
	{
		Vector min = new Vector(3);
		Vector max = new Vector(3);

		for(Polygon element: this.elements)
		{
			Vector[] limits = element.getLimits();

			for(Vector vector: limits)
			{
				max = checkMax(max, vector);
				min = checkMin(min, vector);
			}
		}

		return composeBoundingBox(min, max);
	}

	private Cube composeBoundingBox(Vector min, Vector max)
	{
		Cube box = new Cube(new Polygon[6]);

		Vector ltf = VectorFactory.createPointVector(min.get(0), max.get(1), max.get(2));	// front left top point
		Vector lbf = VectorFactory.createPointVector(min.get(0), min.get(1), max.get(2));	// front left bottom point
		Vector rtf = VectorFactory.createPointVector(max.get(0), max.get(1), max.get(2));	// front right top point
		Vector rbf = VectorFactory.createPointVector(max.get(0), min.get(1), max.get(2));	// front right bottom point
		Vector ltb = VectorFactory.createPointVector(min.get(0), max.get(1), min.get(2));	// back left top point
		Vector lbb = VectorFactory.createPointVector(min.get(0), min.get(1), min.get(2));	// back left bottom point
		Vector rtb = VectorFactory.createPointVector(max.get(0), max.get(1), min.get(2));	// back right top point
		Vector rbb = VectorFactory.createPointVector(max.get(0), min.get(1), min.get(2));	// back right bottom point

		Vector[] limitsTop = {ltf, rtf, rtb, ltb};
		Polygon top = new Polygon(limitsTop);

		Vector[] limitsBottom = {lbf, rbf, lbb, rbb};
		Polygon bottom = new Polygon(limitsBottom);

		Vector[] limitsFront = {lbf, rbf, rtf, ltf};
		Polygon front = new Polygon(limitsFront);

		Vector[] limitsBack = {lbb, rbb, rtb, ltb};
		Polygon back = new Polygon(limitsBack);

		Vector[] limitsLeft = {lbf, ltf, ltb, lbb};
		Polygon left = new Polygon(limitsLeft);

		Vector[] limitsRight = {rbf, rtf, rtb, rbb};
		Polygon right = new Polygon(limitsRight);

		Polygon[] sides = {top, bottom, front, back, left, right};

		return new Cube(sides);
	}

	private Vector checkMax(Vector max, Vector check)
	{
		if(max.get(0) < check.get(0))
		{
			max.set(0, check.get(0));
		}
		if(max.get(1) < check.get(0))
		{
			max.set(1, check.get(1));
		}
		if(max.get(2) < check.get(2))
		{
			max.set(2, check.get(2));
		}
		return max;
	}

	private Vector checkMin(Vector min, Vector check)
	{
		if(min.get(0) > check.get(0))
		{
			min.set(0, check.get(0));
		}
		if(min.get(1) > check.get(0))
		{
			min.set(1, check.get(1));
		}
		if(min.get(2) > check.get(2))
		{
			min.set(2, check.get(2));
		}
		return min;
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
