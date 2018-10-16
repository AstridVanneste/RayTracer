package RayTracer.Objects;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Hittable;
import RayTracer.Hit.Ray;

import java.awt.*;

public class Cube extends Object
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

	public Cube(Polygon sides[], Color color)
	{
		this(sides);
		this.setColor(color);
	}

	@Override
	public HitObject hit(Ray r)
	{
		HitObject hit = null;
		for(int i = 0; i < NUMBER_SIDES; i++)
		{
			HitObject currentHit = this.sides[i].hit(r);
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