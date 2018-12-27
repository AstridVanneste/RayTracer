package RayTracer.Scene.Objects;

import RayTracer.Factories.TransformationFactory;
import RayTracer.Factories.VectorFactory;
import RayTracer.Hit.HitObject;
import RayTracer.Hit.Hittable;
import RayTracer.Hit.Ray;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import RayTracer.Transformation;
import Util.Color;
import org.json.JSONObject;
import Math.Geometry;

public class Cube extends Entity
{
	private static final int NUMBER_SIDES = 6;

	private Polygon sides[];

	public Cube(Polygon sides[])
	{
		if(sides.length == NUMBER_SIDES)
		{
			this.sides = sides;
		}
	}

	public Cube()
	{
		this.createUnitCube();
	}

	public Cube(Polygon sides[], Color color)
	{
		this(sides);
		this.setColor(color);
	}

	public Cube(JSONObject jsonObject, int ID)
	{
		super(jsonObject, ID);
		this.createUnitCube();
		this.setColor(this.color);
	}

	@Override
	public void setColor(Color color)
	{
		super.setColor(color);

		for(int i = 0; i < NUMBER_SIDES; i++)
		{
			this.sides[i].setColor(color);
		}
	}

	@Override
	public HitObject internalHit(Ray r, Tracer tracer, World world, int traceLevel)
	{
		HitObject hit = null;

		double distance = 0;
		for(Hittable element: this.sides)
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

	private void createUnitCube()
	{
		Quad top = new Quad();
		top.setTransformation(TransformationFactory.translationTransformation(0, 1, 0));

		Quad bottom = new Quad();
		bottom.setTransformation(TransformationFactory.translationTransformation(0, -1, 0));

		Quad left = new Quad();
		Transformation transformation1 = TransformationFactory.rotationTransformation(VectorFactory.createVector(0, 0, 1), 3.14/2);
		transformation1.add(TransformationFactory.translationTransformation(-1, 0, 0));
		left.setTransformation(transformation1);

		Quad right = new Quad();
		Transformation transformation2 = TransformationFactory.rotationTransformation(VectorFactory.createVector(0, 0, 1), 3.14/2);
		transformation2.add(TransformationFactory.translationTransformation(1, 0, 0));
		right.setTransformation(transformation2);

		Quad front = new Quad();
		Transformation transformation3 = TransformationFactory.rotationTransformation(VectorFactory.createVector(1, 0, 0), 3.14/2);
		transformation3.add(TransformationFactory.translationTransformation(0, 0, 1));
		front.setTransformation(transformation3);

		Quad back = new Quad();
		Transformation transformation4 = TransformationFactory.rotationTransformation(VectorFactory.createVector(1, 0, 0), 3.14/2);
		transformation4.add(TransformationFactory.translationTransformation(0, 0, -1));
		back.setTransformation(transformation4);

		this.sides = new Polygon[NUMBER_SIDES];
		this.sides[0] = bottom;
		this.sides[1] = top;
		this.sides[2] = left;
		this.sides[3] = right;
		this.sides[4] = front;
		this.sides[5] = back;
	}
}
