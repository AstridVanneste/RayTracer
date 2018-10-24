package RayTracer.Objects;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Hittable;
import RayTracer.Hit.Ray;
import Math.Vector;

import java.util.List;

public class Mesh extends Object
{
	private Cube boundingBox;
	private List<Hittable> elements;

	public Mesh(List<Hittable> elements)
	{
		this.elements = elements;
		this.boundingBox = this.calculateBoundingBox();
	}

	private Cube calculateBoundingBox()
	{
		Vector min = new Vector(3);
		Vector max = new Vector(3);

		Cube box = new Cube(new Polygon[6]);

		return box;
	}

	@Override
	public HitObject hit(Ray r)
	{
		HitObject hit = null;
		if(this.boundingBox.hit(r) != null)
		{
			double distance = 0;
			for(Hittable element: this.elements)
			{
				HitObject tempHit = element.hit(r);

				if(tempHit != null)
				{
					if(tempHit.getDistance() > distance || distance == 0)
					{
						hit = tempHit;
						distance = hit.getDistance();
					}
				}
			}
		}
		return hit;
	}
}
