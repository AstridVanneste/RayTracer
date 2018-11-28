package RayTracer.Scene.Objects;

import RayTracer.Factories.TransformationFactory;
import RayTracer.Factories.VectorFactory;
import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import RayTracer.Scene.World;
import RayTracer.Tracer;
import RayTracer.Transformation;
import Util.Color;
import org.json.JSONObject;
import Math.Vector;

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
		Quad top = new Quad();
		top.setTransformation(TransformationFactory.translationTransformation(0, 1, 0));

		Quad bottom = new Quad();
		bottom.setTransformation(TransformationFactory.translationTransformation(0, -1, 0));

		Quad left = new Quad();
		Transformation transformation = TransformationFactory.rotationTransformation(VectorFactory.createVector(0, 0, 1), 3.14/2);
		transformation.add(TransformationFactory.translationTransformation(-1, 0, 0));
		left.setTransformation(transformation);

		Quad right = new Quad();
		transformation = TransformationFactory.rotationTransformation(VectorFactory.createVector(0, 0, 1), 3.14/2);
		transformation.add(TransformationFactory.translationTransformation(1, 0, 0));
		right.setTransformation(transformation);

		Quad front = new Quad();
		transformation = TransformationFactory.rotationTransformation(VectorFactory.createVector(1, 0, 0), 3.14/2);
		transformation.add(TransformationFactory.translationTransformation(0, 0, 1));
		front.setTransformation(transformation);

		Quad back = new Quad();
		transformation = TransformationFactory.rotationTransformation(VectorFactory.createVector(1, 0, 0), 3.14/2);
		transformation.add(TransformationFactory.translationTransformation(0, 0, -1));
		back.setTransformation(transformation);

		this.sides = new Polygon[NUMBER_SIDES];
		this.sides[0] = bottom;
		this.sides[1] = top;
		this.sides[2] = left;
		this.sides[3] = right;
		this.sides[4] = front;
		this.sides[5] = back;
	}

	public Cube(Polygon sides[], Color color)
	{
		this(sides);
		this.setColor(color);
	}

	public Cube(JSONObject jsonObject)
	{
		super(jsonObject);
		// TODO complete
	}

	@Override
	public HitObject internalHit(Ray r, Tracer tracer, World world)
	{
		HitObject hit = null;
		for(int i = 0; i < NUMBER_SIDES; i++)
		{
			HitObject currentHit = this.sides[i].internalHit(r, tracer, world);
			if(currentHit != null)
			{
				if (hit == null)
				{
					hit = currentHit;
				} else if (hit.getDistance() > currentHit.getDistance())
				{
					hit = currentHit;
				}
			}
		}

		return hit;
	}
}
