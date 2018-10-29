package RayTracer.Objects;

import RayTracer.Factories.VectorFactory;
import RayTracer.Hit.HitObject;
import RayTracer.Hit.Hittable;
import RayTracer.Hit.Ray;
import Math.Vector;

import java.util.List;

public class Mesh extends Object
{
	private Cube boundingBox;
	private List<Polygon> elements;

	public Mesh(List<Polygon> elements)
	{
		this.elements = elements;
		this.boundingBox = this.calculateBoundingBox();
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
	public HitObject hit(Ray r)
	{
		HitObject hit = null;

		//return this.boundingBox.hit(r);
		/*if(this.boundingBox.hit(r) != null)									// only check all faces if ray hits bounding box
		{
			*/
		double distance = 0;
		for(Hittable element: this.elements)
		{
			HitObject tempHit = element.hit(r);

			if(tempHit != null)
			{
				System.out.println("hit something");
				if(tempHit.getDistance() < distance || distance == 0)
				{
					hit = tempHit;
					distance = hit.getDistance();
				}
			}
		}
		//}
		return hit;
	}
}
